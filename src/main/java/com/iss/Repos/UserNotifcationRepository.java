package com.iss.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iss.models.UserNotifications;

public interface UserNotifcationRepository extends JpaRepository<UserNotifications, Integer> {
	@Query("SELECT un FROM UserNotifications un WHERE un.isRead = false AND un.user.email = :usermail")
	Optional<List<UserNotifications>> findByUserId(@Param("usermail") String usermail);

}
