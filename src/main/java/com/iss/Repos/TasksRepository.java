package com.iss.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iss.Dto.TaskListDisplayDto;
import com.iss.models.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {
	@Query("""
	           SELECT new com.iss.Dto.TaskListDisplayDto(
	               t.id,
	               t.task,
	               t.taskLevel,
	               t.points
	           )
	           FROM Tasks t
	           WHERE t.video.id = :videoId
	           """)
		List<TaskListDisplayDto> findTasksByVideoId(@Param("videoId") int videoId);
	
}
