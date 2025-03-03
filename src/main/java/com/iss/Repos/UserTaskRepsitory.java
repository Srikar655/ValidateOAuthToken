package com.iss.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.models.UserTask;

@Repository
public interface UserTaskRepsitory extends JpaRepository<UserTask,Integer>{

}
