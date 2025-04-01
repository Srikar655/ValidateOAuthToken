package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iss.Dto.UserDto;
import com.iss.Mappers.UserMapper;
import com.iss.Repos.UserRepository;
import com.iss.models.User;

@Service
public class UserService {

    private final UserRepository repos;

    public UserService(UserRepository repos) {
        this.repos = repos;
    }

    public UserDto add(User user) {
        try {
            User savedUser = repos.save(user);
            return UserMapper.Instance.toDto(savedUser);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding the user", ex);
        }
    }

    public List<UserDto> findAll() {
        try {
            List<User> users = repos.findAll();
            return UserMapper.Instance.toDtoList(users);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all users", ex);
        }
    }

    public UserDto find(int id) {
        try {
            Optional<User> userOpt = repos.findById(id);
            if (userOpt.isPresent()) {
                return UserMapper.Instance.toDto(userOpt.get());
            } else {
                throw new RuntimeException("User with id " + id + " not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the user with id " + id, ex);
        }
    }

    public void delete(int id) {
        try {
            if (repos.existsById(id)) {
                repos.deleteById(id);
            } else {
                throw new RuntimeException("User with id " + id + " not found, delete failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting the user with id " + id, ex);
        }
    }
}
