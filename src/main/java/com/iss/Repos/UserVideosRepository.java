package com.iss.Repos;

import com.iss.models.UserVedio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVideosRepository extends JpaRepository<UserVedio, Integer> {
    Page<UserVedio> findByUsercourseId(int usercourseId, Pageable pageable);
    
    @Query("SELECT uv.vedio.vedioprice FROM UserVedio uv where uv.id=:userVideoId")
	Optional<Double> getUserVideoPriceById(@Param("userVideoId")int userVideoId);

}
