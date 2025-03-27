package com.iss.Repos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iss.models.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {
	@Query(value = "SELECT * FROM tasks WHERE video_id = :vedioId", nativeQuery = true)
	Optional<Page<Tasks>> findByVideoId(int vedioId, Pageable pageable);
	
}
