package com.iss.Repos;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iss.models.Vedio;

public interface VedioRepository extends JpaRepository<Vedio, Integer> {
	@Query(value = "SELECT * FROM vedio WHERE course_id = :courseId", nativeQuery = true)
	Optional<Page<Vedio>> findByCourseId(int courseId, Pageable pageable);
}
