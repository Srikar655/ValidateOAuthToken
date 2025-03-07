package com.iss.Services;

import com.iss.Dto.UserTaskDto;
import com.iss.Mappers.UserTaskMapper;
import com.iss.Repos.UserTaskRepsitory;
import com.iss.models.UserTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTaskService {

    private final UserTaskRepsitory repos;

    public UserTaskService(UserTaskRepsitory repos) {
        this.repos = repos;
    }

    // Add UserTask
    public UserTaskDto add(UserTask user) {
        try {
            return UserTaskMapper.Instance.toDto(repos.save(user));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Find all UserTask (Paginated)
    public List<UserTaskDto> findAll(Pageable pageable) {
        try {
            Page<UserTask> page = repos.findAll(pageable);
            return UserTaskMapper.Instance.toDtoList(page.getContent());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Find by UserVedio ID (Paginated)
    public List<UserTaskDto> findByUserVedioId(int userVedioId, Pageable pageable) {
        try {
            Page<UserTask> page = repos.findByUservedio_Id(userVedioId, pageable);
            return UserTaskMapper.Instance.toDtoList(page.getContent());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Find UserTask by ID
    public UserTaskDto find(int id) {
        try {
            return UserTaskMapper.Instance.toDto(repos.findById(id).orElse(null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Delete UserTask by ID
    public void delete(int id) {
        try {
            repos.deleteById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
