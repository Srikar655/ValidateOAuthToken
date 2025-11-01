package com.iss.Services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.iss.Dto.UserTaskSolutionDto;
import com.iss.Dto.UserTaskSolutionImagesDto;
import com.iss.Mappers.UserTaskSolutionMapper;
import com.iss.Repos.UserTaskSolutionRepos;
import com.iss.models.UsersTaskSolution;
import com.iss.models.CorrectionStatus;
import com.iss.models.SubmissionType;
import com.iss.models.TaskProgress;
import com.iss.models.UserTask;



@Service
public class UserTaskSolutionService {
	private final UserTaskSolutionRepos repos;
	private final UserTaskService userTaskService;
	private FileStorageService fileStorageService;
	public UserTaskSolutionService(UserTaskSolutionRepos repos,UserTaskService userTaskService,FileStorageService fileStorageService)
	{
		this.repos=repos;
		this.userTaskService=userTaskService;
		this.fileStorageService = fileStorageService;
	}
	@Transactional 
    public UserTaskSolutionDto add(CreateUsersTaskSolutionDto createDto, List<MultipartFile> files, String email) throws DuplicateKeyException,IllegalArgumentException,NotFoundException,RuntimeException {
        try {
            UserTask userTask = userTaskService.findAndGetUserTask(createDto.getUserTaskId());
            if (userTask == null) {
                throw new NotFoundException();
            }
            
            List<UsersTaskSolution> existingSolutions = repos.findByUsertask(userTask);

            boolean hasActiveSubmission = existingSolutions.stream()
                .anyMatch(solution -> 
                    solution.getCorrectionStatus() == CorrectionStatus.PENDING || 
                    solution.getCorrectionStatus() == CorrectionStatus.CORRECT
                );
            
            if (hasActiveSubmission) {
                throw new DuplicateKeyException("An active solution (Pending or Reviewed) already exists for this task. You cannot submit again.");
            }
            SubmissionType submissionType = userTask.getTask().getSubmissionType();
            
            userTask.setTaskProgress(TaskProgress.SUBMITED);
            this.userTaskService.update(userTask);

            UsersTaskSolution newUserTaskSolution = UsersTaskSolution.builder()
                    .correctionStatus(CorrectionStatus.PENDING)
                    .usertask(userTask)
                    .email(email)
                    .description(createDto.getDescription())
                    .submittedAt(new Timestamp(System.currentTimeMillis()))
                    .build();

            String submissionContent = "";

            if (submissionType.equals(SubmissionType.IMAGE_UPLOAD)) {
                if (files == null || files.isEmpty()) {
                    throw new IllegalArgumentException("Image submission requires at least one file.");
                }
                submissionContent = files.stream()
                                         .map(fileStorageService::store)
                                         .collect(Collectors.joining(";")); 
                newUserTaskSolution.setSolutionimages(submissionContent);

            } else if (submissionType.equals(SubmissionType.TEXT_BLOCK)) {
                if (createDto.getSubmissionText() == null || createDto.getSubmissionText().isBlank()) {
                     throw new IllegalArgumentException("Text submission cannot be empty.");
                }
                submissionContent = createDto.getSubmissionText();
                newUserTaskSolution.setSubmissionText(submissionContent);
                
            } else if (submissionType.equals(SubmissionType.URL_LINK)) {
                if (createDto.getSubmissionUrl() == null || createDto.getSubmissionUrl().isBlank()) {
                     throw new IllegalArgumentException("URL submission cannot be empty.");
                }
                submissionContent = createDto.getSubmissionUrl();
                newUserTaskSolution.setSubmissionUrl(submissionContent);
            }

            newUserTaskSolution = repos.save(newUserTaskSolution);

            return UserTaskSolutionDto.builder()
            							.id(newUserTaskSolution.getId())
            							.solutionimages(newUserTaskSolution.getSolutionimages())
            							.submissionText(newUserTaskSolution.getSubmissionText())
            							.submissionUrl(newUserTaskSolution.getSubmissionUrl())
            							.submittedAt(newUserTaskSolution.getSubmittedAt())
            							.userTaskId(userTask.getId())
            							.description(newUserTaskSolution.getDescription())
            							.correctionStatus(newUserTaskSolution.getCorrectionStatus())
            							.email(email)
            							.build();

        }catch (DuplicateKeyException | IllegalArgumentException | NotFoundException e) {
            throw e;
        } catch (Exception ex) {
            
            throw new RuntimeException("Failed to submit solution.", ex);
        }
    }
	public List<UserTaskSolutionDto> findAll()
	{
		try
		{
			return UserTaskSolutionMapper.Instance.toDtoList(repos.findAll());
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<UserTaskSolutionDto> findAllSubmittedSolutions(Pageable pageable)
	{
		try
		{
			Optional<Page<UsersTaskSolution>> optionalSubmittedUserTaskSolutions=repos.getSubmittedSolutions(TaskProgress.SUBMITED,pageable);
			if(optionalSubmittedUserTaskSolutions.isPresent())
			{
				List<UserTaskSolutionDto> dtolist= UserTaskSolutionMapper.Instance.toDtoList(optionalSubmittedUserTaskSolutions.get().getContent());
				//dtolist.forEach(us->System.out.println(us.getUsertask().getTask().getTask()));
				return dtolist;
			}
			return null;
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public UserTaskSolutionDto find(int id)
	{
		try
		{
			return UserTaskSolutionMapper.Instance.toDto(repos.findById(id).get());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public void delete(int id)
	{
		try
		{
			repos.deleteById(id);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void updateSuccessReview(UsersTaskSolution userTaskSolution) 
	{
		try {
			Optional<UsersTaskSolution> optionalUserTaskSolution=repos.findById(userTaskSolution.getId());
				if(optionalUserTaskSolution.isPresent())
				{
					UsersTaskSolution taskSolution=optionalUserTaskSolution.get();
					taskSolution.setReviewedAt(new Timestamp(System.currentTimeMillis()));
					taskSolution.setAudioAdvice(userTaskSolution.getAudioAdvice());
					CorrectionStatus correctionStatus=userTaskSolution.getCorrectionStatus();
					taskSolution.setCorrectionStatus(correctionStatus);
					repos.save(taskSolution);
					if(correctionStatus.equals(CorrectionStatus.CORRECT))
					{
						UserTask userTask=taskSolution.getUsertask();
						userTask.setTaskProgress(TaskProgress.REVIEWED);
						this.userTaskService.update(userTask);
					}
				}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public UserTaskSolutionImagesDto findSolutionImages(int userSolutionId) {
		try {
			Optional<List<byte[]>> optionallist=repos.findSolutionImagesById(userSolutionId);
			if(optionallist.isPresent())
			{
				return UserTaskSolutionImagesDto.builder().solutionimages(optionallist.get()).build();
			}
			return null;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
