package com.iss.Repos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iss.Dto.UserCourseDisplayDto;
import com.iss.models.SubscriptionStatus;
import com.iss.models.UserCourse;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
	
	@Query("""
		       SELECT new com.iss.Dto.UserCourseDisplayDto(
		           uc.id,
		           uc.course.id,
		           uc.course.coursename,
		           uc.course.courseprice,
		           uc.course.courseTrailer,
		           uc.course.coursethumbnail,
		           uc.course.courseDescription,
		           uc.subscriptionStatus,
		           uc.course.courseCategory.id
		       )
		       FROM UserCourse uc
		       WHERE uc.course.id = :courseId AND uc.user.email = :email
		       """)
		Optional<UserCourseDisplayDto> findByCourseIdAndEmail(
		        @Param("courseId") int courseId,
		        @Param("email") String email);
	
	boolean existsByCourseIdAndUserEmailAndSubscriptionStatus(
	        int courseId, 
	        String userEmail, 
	        SubscriptionStatus subscriptionStatus
	    );




}
