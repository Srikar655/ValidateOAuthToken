package com.iss.Repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iss.models.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {

	Page<Tasks> findByVideoId(int vedioId, Pageable pageable);
	
}
