package com.iss.Repos;

import com.iss.Dto.UserTaskListDisplayDto;
import com.iss.models.PaymentStatus;
import com.iss.models.UserTask;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UserTaskRepsitory extends JpaRepository<UserTask, Integer> {

	@Query("""
	           SELECT new com.iss.Dto.UserTaskListDisplayDto(
	               ut.id,
	               ut.task.id,
	               ut.task.task,
	               ut.task.taskLevel,
	               ut.task.points,
	               ut.taskProgress,
	               ut.accessStatus
	           )
	           FROM UserTask ut
	           WHERE ut.uservedio.id = :userVedioId
	           """)
	    List<UserTaskListDisplayDto> findUserTasksByUserVedioId(@Param("userVedioId") int userVedioId);
	
	 @Query("SELECT ut.task.taskprice FROM UserTask ut where ut.id=:userTaskId")
		Optional<Double> getUserTaskPriceById(@Param("userTaskId")int userTaskId);
	 
	 boolean existsByIdAndPaymentStatus(
		        int id, 
		        PaymentStatus paymentStatus
		    );
}
