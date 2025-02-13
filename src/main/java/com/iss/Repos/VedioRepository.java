package com.iss.Repos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iss.models.Vedio;

public interface VedioRepository extends JpaRepository<Vedio, Integer> {
	Page<Vedio> findByCourseId(int courseId, Pageable pageable);
}
