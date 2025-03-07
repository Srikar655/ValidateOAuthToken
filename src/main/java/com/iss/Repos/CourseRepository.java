package com.iss.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.iss.models.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	@Query("SELECT c.coursethumbnail FROM Course c WHERE c.id = :id")
    byte[] findCoursethumbnailById(@Param("id") int id);

	@Query("SELECT c FROM Course c where c.courseCategory=:category")
	List<Course> getAllCategoryRelatedCourses(@Param("category")String category);
}
