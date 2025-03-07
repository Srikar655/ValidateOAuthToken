package com.iss.Services;

import com.iss.Dto.UserVedioDto;
import com.iss.Mappers.UserVedioMapper;
import com.iss.Repos.UserVideosRepository;
import com.iss.models.UserVedio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserVedioService {
    
    private final UserVideosRepository repos;

    public UserVedioService(UserVideosRepository repos) {
        this.repos = repos;
    }

    public UserVedioDto add(UserVedio user) {
        try {
            return UserVedioMapper.Instance.toDto(repos.save(user));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<UserVedioDto> findAll(Pageable pageable) {
        try {
            List<UserVedio> page = repos.findAll(pageable).getContent();
            return page.map(UserVedioMapper.Instance::toDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public List<UserVedioDto> findAll() {
        try {
            List<UserVedio> page = repos.findAll();
            return page.map(UserVedioMapper.Instance::toDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    

    public UserVedioDto find(int id) {
        try {
            return UserVedioMapper.Instance.toDto(repos.findById(id).orElse(null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void delete(int id) {
        try {
            repos.deleteById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
