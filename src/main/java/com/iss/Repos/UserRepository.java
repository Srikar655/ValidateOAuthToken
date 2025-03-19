package com.iss.Repos;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.models.Role;
import com.iss.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

	Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);

	    @Query("SELECT u.roles FROM User u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail2")
	    Optional<List<Role>> findRolesByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);

}
