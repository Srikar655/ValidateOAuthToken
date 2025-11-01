package com.iss.Repos;

import com.iss.models.PaymentStatus;
import com.iss.models.UserVedio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVideosRepository extends JpaRepository<UserVedio, Integer> {
	@Query("""
	           SELECT uv
	           FROM UserVedio uv
	           WHERE uv.usercourse.id = :userCourseId AND uv.vedio.id = :videoId
	           """)
	    Optional<UserVedio> findByUserCourseIdAndVedioId(
	            @Param("userCourseId") int userCourseId,
	            @Param("videoId") int videoId
	    );
    
    @Query("SELECT uv.vedio.vedioprice FROM UserVedio uv where uv.id=:userVideoId")
	Optional<Double> getUserVideoPriceById(@Param("userVideoId")int userVideoId);
    
    @Query(value = "SELECT TOP 1 * FROM user_vedio uv WHERE uv.id > :userVideoPosition AND uv.usercourse_id = :userCourseId ORDER BY uv.id ASC", nativeQuery = true)
    Optional<UserVedio> getNextUserVideo(@Param("userVideoPosition") int userVideoPosition, @Param("userCourseId") int userCourseId);

    boolean existsByIdAndPaymentStatus(
	        int id, 
	        PaymentStatus paymentStatus
	    );



}
