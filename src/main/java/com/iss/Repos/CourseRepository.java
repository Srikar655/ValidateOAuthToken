package com.iss.Repos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.iss.models.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	@Query("SELECT c.coursethumbnail FROM Course c WHERE c.id = :id")
	Optional<byte[]> findCoursethumbnailById(@Param("id") int id);

	@Query("SELECT c FROM Course c where c.courseCategory=:category")
	Optional<List<Course>> getAllCategoryRelatedCourses(@Param("category")String category);
	@Query("SELECT c.courseprice FROM Course c where c.id=:courseId")
	Optional<Double> getCoursePriceByCourseId(@Param("courseId")int courseId);
}
