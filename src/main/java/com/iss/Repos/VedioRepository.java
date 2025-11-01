package com.iss.Repos;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iss.Dto.UserVideoDetailDisplayDto;
import com.iss.Dto.VideoListDisplayDto;
import com.iss.models.Vedio;

public interface VedioRepository extends JpaRepository<Vedio, Integer> {
	
	@Query("""
	           SELECT new com.iss.Dto.VideoListDisplayDto(
	               v.id,
	               v.vediotitle
	           )
	           FROM Vedio v
	           WHERE v.course.id = :courseId
	           """)
	Page<VideoListDisplayDto> findVideosByCourseId(@Param("courseId") int courseId, Pageable pageable);
	
	@Query("""
	           SELECT new com.iss.Dto.UserVideoDetailDisplayDto(
	               v.id,
	               v.vediotitle,
	               v.vediodescription,
	               v.vedioprice
	           )
	           FROM Vedio v
	           WHERE v.id = :videoId
	           """)
	    Optional<UserVideoDetailDisplayDto> findPublicVideoDetailsById(@Param("videoId") int videoId);
}
