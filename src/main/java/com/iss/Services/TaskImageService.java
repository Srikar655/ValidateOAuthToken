package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iss.Dto.TaskImageDto;
import com.iss.Mappers.TaskImageMapper;
import com.iss.Repos.TaskImageReps;
import com.iss.models.TaskImages;

@Service
public class TaskImageService {

    private final TaskImageReps repos;

    public TaskImageService(TaskImageReps repos) {
        this.repos = repos;
    }

    public TaskImageDto add(TaskImages image) {
        try {
            TaskImages savedImage = repos.save(image);
            return TaskImageMapper.Instance.toDto(savedImage);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding the task image", ex);
        }
    }

    public TaskImageDto find(int id) {
        try {
            Optional<TaskImages> imageOpt = repos.findById(id);
            if (imageOpt.isPresent()) {
                return TaskImageMapper.Instance.toDto(imageOpt.get());
            } else {
                throw new RuntimeException("Task image with id " + id + " not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the task image with id " + id, ex);
        }
    }

    public List<TaskImageDto> findAll() {
        try {
            List<TaskImages> imageList = repos.findAll();
            return TaskImageMapper.Instance.toDtoList(imageList);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all task images", ex);
        }
    }

    public List<TaskImageDto> findByTaskId(int id) {
        try {
            List<TaskImages> images = repos.findByTaskId(id);
            return TaskImageMapper.Instance.toDtoList(images);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching task images by task id " + id, ex);
        }
    }

    public void delete(int id) {
        try {
            if (repos.existsById(id)) {
                repos.deleteById(id);
            } else {
                throw new RuntimeException("Task image with id " + id + " not found, delete failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting the task image with id " + id, ex);
        }
    }

    public TaskImageDto save(TaskImages taskImage) {
        try {
            TaskImages savedImage = repos.save(taskImage);
            return TaskImageMapper.Instance.toDto(savedImage);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error saving the task image", ex);
        }
    }
}
