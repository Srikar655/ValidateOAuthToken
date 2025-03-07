package com.iss.Repos;

import com.iss.models.UserVedio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVideosRepository extends JpaRepository<UserVedio, Integer> {

    // Custom query to find videos by user course ID with pagination
    Page<UserVedio> findByUsercourseId(int usercourseId, Pageable pageable);
}
