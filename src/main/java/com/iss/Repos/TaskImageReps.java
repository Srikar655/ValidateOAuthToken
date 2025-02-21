package com.iss.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iss.models.TaskImages;

public interface TaskImageReps extends JpaRepository<TaskImages,Integer> {
	@Query(value = "SELECT * FROM task_images WHERE task_id = :id", nativeQuery = true)
    List<TaskImages> findByTaskId(@Param("id") int id);
}
