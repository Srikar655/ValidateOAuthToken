package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iss.Dto.TasksDto;
import com.iss.Mappers.TasksMapper;
import com.iss.Repos.TasksRepository;
import com.iss.models.TaskImages;
import com.iss.models.Tasks;

@Service
public class TasksService {

    private final TasksRepository repos;

    public TasksService(TasksRepository repos) {
        this.repos = repos;
    }

    public TasksDto add(Tasks task) {
        try {
        	return TasksMapper.Instance.toDto(repos.save(task));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding Task", ex);
        }
    }

    public List<TasksDto> add(List<Tasks> tasks) {
        try {
            return TasksMapper.Instance.toDtoList(repos.saveAllAndFlush(tasks));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding multiple Tasks", ex);
        }
    }

    public List<TasksDto> findAll() {
        try {
            return TasksMapper.Instance.toDtoList(repos.findAll());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all Tasks", ex);
        }
    }

    public TasksDto find(int id) {
        try {
            Optional<Tasks> taskOpt = repos.findById(id);
            if (taskOpt.isPresent()) {
                return TasksMapper.Instance.toDto(taskOpt.get());
            } else {
                return null; 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching Task with id " + id, ex);
        }
    }
    @Cacheable(value = "tasks", key = "#videoId")
    public List<TasksDto> findByVideoId(int videoId, Pageable pageable) {
        try {
            Optional<Page<Tasks>> taskPageOpt = repos.findByVideoId(videoId, pageable);
            if (taskPageOpt.isPresent()) {
                return TasksMapper.Instance.toDtoList(taskPageOpt.get().getContent());
            } else {
                return null; // Return null if no tasks found
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching Tasks by Video ID", ex);
        }
    }

    public TasksDto update(Tasks task) {
        try {
            Optional<Tasks> existingTaskOpt = repos.findById(task.getId());
            if (existingTaskOpt.isPresent()) {
                Tasks existingTask = existingTaskOpt.get();
                BeanUtils.copyProperties(task, existingTask, "usertask", "taskimages"); 
                if (task.getTaskimages() != null) {
                    existingTask.getTaskimages().clear(); 
                    for (TaskImages image : task.getTaskimages()) {
                        image.setTask(existingTask); 
                        existingTask.getTaskimages().add(image);
                    }
                }
                return TasksMapper.Instance.toDto(repos.save(existingTask));
            } else {
                return null; 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error updating Task with id " + task.getId(), ex);
        }
    }

    public void delete(int id) {
        try {
            if (repos.existsById(id)) {
                repos.deleteById(id);
            } else {
                throw new RuntimeException("Task with id " + id + " not found, delete failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting Task with id " + id, ex);
        }
    }
}
