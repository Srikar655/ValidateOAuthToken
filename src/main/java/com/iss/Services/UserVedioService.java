package com.iss.Services;

import com.iss.Dto.UserVedioDto;
import com.iss.Dto.UserVideoDetailDisplayDto;
import com.iss.Mappers.UserVedioMapper;
import com.iss.Repos.UserVideosRepository;
import com.iss.Repos.VedioRepository;
import com.iss.models.AccessStatus;
import com.iss.models.PaymentStatus;
import com.iss.models.UserVedio;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import com.iss.models.Vedio;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserVedioService {

    private final UserVideosRepository repos;
    private final VedioRepository videoRepos;

    public UserVedioService(UserVideosRepository repos,VedioRepository videoRepos) {
        this.repos = repos;
        this.videoRepos = videoRepos;
    }
    
    @CacheEvict(value = "userVideoDetails", key = "#userVedio.userCourse.id + '_' + #userVedio.vedio.id")
    @Transactional
    public UserVedioDto add(UserVedio user) {
        try {
            UserVedio savedUserVedio = repos.save(user);
            return UserVedioMapper.Instance.toDto(savedUserVedio);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding the UserVedio", ex);
        }
    }
    @Caching(
            put = {
                @CachePut(value = "userVideo", key = "#userVedio.id"),
                @CachePut(value = "userVideoDetails", key = "#userVedio.userCourse.id + '_' + #userVedio.vedio.id")
            }
        )
        @Transactional
        public UserVedioDto update(UserVedio userVedio) throws NotFoundException {
             if (!repos.existsById(userVedio.getId())) {
                throw new NotFoundException();
            }
            UserVedio updated = repos.save(userVedio);
            return UserVedioMapper.Instance.toDto(updated);
        }

    public List<UserVedioDto> findAll(Pageable pageable) {
        try {
            Page<UserVedio> page = repos.findAll(pageable);
            return UserVedioMapper.Instance.toDtoList(page.getContent());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching paginated UserVedios", ex);
        }
    }

    public List<UserVedioDto> findAll() {
        try {
            List<UserVedio> userVedios = repos.findAll();
            return UserVedioMapper.Instance.toDtoList(userVedios);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all UserVedios", ex);
        }
    }
    @Cacheable(value = "userVideo", key = "#id")
    @Transactional(readOnly = true)
    public UserVedioDto find(int id) {
        try {
            Optional<UserVedio> userVedioOpt = repos.findById(id);
            return userVedioOpt.map(UserVedioMapper.Instance::toDto)
                    .orElseThrow(() -> new RuntimeException("UserVedio with id " + id + " not found"));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching the UserVedio with id " + id, ex);
        }
    }
    @Cacheable(value = "userVideoDetails", key = "#userCourseId + '_' + #videoId", unless = "#result == null")
    @Transactional(readOnly = true)
    public UserVideoDetailDisplayDto checkAccessAndGetVideoDetails(Integer userCourseId,int videoId) {
        try {
        	if (userCourseId != null && userCourseId > 0) {
            	Optional<UserVedio> userVideoOptional = repos.findByUserCourseIdAndVedioId(userCourseId,videoId);
            
            	UserVideoDetailDisplayDto dto = userVideoOptional
                        .map(this::buildUserVideoDetailDisplayDto)
                        .orElse(null);
            	return dto;
            }
            
            return this.videoRepos.findPublicVideoDetailsById(videoId).orElse(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching UserVedios by Usercourse ID", ex);
        }
    }
    @Caching(
            evict = {
                @CacheEvict(value = "userVideo", key = "#id"),
                @CacheEvict(value = "userVideoDetails", key = "#result.userCourse.id + '_' + #result.vedio.id")
            }
        )
        @Transactional
    public void delete(int id) {
        try {
            if (repos.existsById(id)) {
                repos.deleteById(id);
            } else {
                throw new RuntimeException("UserVedio with id " + id + " not found, delete failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting the UserVedio with id " + id, ex);
        }
    }

    public double getUserVideoPrice(int userVideoId) {
        try {
            Optional<Double> optionalPrice = repos.getUserVideoPriceById(userVideoId);
            return optionalPrice.orElse(0.0);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching price for UserVedio with id " + userVideoId, ex);
        }
    }
    private UserVideoDetailDisplayDto buildUserVideoDetailDisplayDto(UserVedio userVideo)
    {
    	UserVideoDetailDisplayDto dto;
    	Vedio video = userVideo.getVedio();
 
        dto = new UserVideoDetailDisplayDto(userVideo.getId(),video.getId(),video.getVediotitle(),video.getVediodescription(),video.getVedioprice(),userVideo.getAccessStatus(),userVideo.getPaymentStatus());
        if (userVideo.getAccessStatus().equals(AccessStatus.UNLOCKED) && userVideo.getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
            dto.setVideoUrl(video.getVideourl());
        }
        else
        {
        	dto.setVideoUrl(null);
        }
        return dto;
    	
    	
    }
}
