package com.iss.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.iss.models.TaskProgress;
import com.iss.models.UsersTaskSolution;
@Repository
public interface UserTaskSolutionRepos extends JpaRepository<UsersTaskSolution, Integer> {
	@Query("SELECT uts FROM UsersTaskSolution uts WHERE uts.usertask.taskProgress = :taskProgress")
	Optional<Page<UsersTaskSolution>> getSubmittedSolutions(@Param("taskProgress") TaskProgress taskProgress, Pageable pageable);

	@Query("SELECT uts.solutionimages FROM UsersTaskSolution uts WHERE uts.id = :userSolutionId")
	Optional<List<byte[]>> findSolutionImagesById(int userSolutionId);
}
