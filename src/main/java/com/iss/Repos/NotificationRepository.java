package com.iss.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.models.Notifications;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications,Integer> {

}
