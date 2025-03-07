package com.iss.Repos;

import com.iss.models.UserTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTaskRepsitory extends JpaRepository<UserTask, Integer> {

    // Add the paginated findByUserVedioId method here
    Page<UserTask> findByUservedio_Id(int userVedioId, Pageable pageable);
}
