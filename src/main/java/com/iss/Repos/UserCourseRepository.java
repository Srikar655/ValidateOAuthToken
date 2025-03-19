package com.iss.Repos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.models.UserCourse;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
	
	@Query("SELECT uc FROM UserCourse uc WHERE uc.course.id = :courseId")
	Optional<UserCourse> findByCourseId(int courseId);


}
