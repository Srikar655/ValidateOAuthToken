package com.iss.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.models.UserVedio;
@Repository
public interface UserVideosRepository extends JpaRepository<UserVedio, Integer> {

}
