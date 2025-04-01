package com.iss.Repos;

import com.iss.models.UserTask;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UserTaskRepsitory extends JpaRepository<UserTask, Integer> {

	@Query(value = "SELECT * FROM user_task WHERE uservedio_id = :userVedioId", nativeQuery = true)
    Optional<Page<UserTask>> findByUservedio_Id(int userVedioId, Pageable pageable);
	
	 @Query("SELECT ut.task.taskprice FROM UserTask ut where ut.id=:userTaskId")
		Optional<Double> getUserTaskPriceById(@Param("userTaskId")int userTaskId);
}
