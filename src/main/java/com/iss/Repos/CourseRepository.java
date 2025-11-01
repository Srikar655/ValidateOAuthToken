package com.iss.Repos;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.iss.Dto.CourseListDisplayDto;
import com.iss.Dto.UserCourseDisplayDto;
import com.iss.models.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	@Query("SELECT c.coursethumbnail FROM Course c WHERE c.id = :id")
	Optional<byte[]> findCoursethumbnailById(@Param("id") int id);

	@Query("SELECT c FROM Course c where c.courseCategory=:category")
	Optional<List<Course>> getAllCategoryRelatedCourses(@Param("category")String category);
	
	@Query("SELECT c.courseprice FROM Course c where c.id=:courseId")
	Optional<Double> getCoursePriceByCourseId(@Param("courseId")int courseId);
	
	@Query("""
			SELECT new com.iss.Dto.CourseListDisplayDto(
			    c.id,
			    c.coursename,
			    c.courseprice,
			    c.courseCategory.id,
			    c.coursethumbnail
			)
			FROM Course c
			WHERE (:categoryIds IS NULL OR c.courseCategory.id IN :categoryIds)
			AND (:searchTerm IS NULL OR LOWER(c.coursename) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
			""")
			Page<CourseListDisplayDto> findAllCourseSummaries(
			    @Param("categoryIds") List<Integer> categoryIds,
			    @Param("searchTerm") String searchTerm,
			    Pageable pageable
			);
	
	@Query("""
		       SELECT new com.iss.Dto.UserCourseDisplayDto(
		           c.id,                                      
		           c.coursename,                              
		           c.courseprice,                             
		           c.courseTrailer,                           
		           c.coursethumbnail,
		           c.courseDescription,                      
		           c.courseCategory.id                       
		       )
		       FROM Course c
		       WHERE c.id = :courseId
		       """)
	Optional<UserCourseDisplayDto> findByCourseIdForUserDisplay(@Param("courseId") int courseId);
	@Query("SELECT c.courseFeatures FROM Course c WHERE c.id = :courseId")
	List<String> findCourseFeaturesById(@Param("courseId") int courseId);

	
}
