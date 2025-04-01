package com.iss.Services;

import com.iss.Dto.UserVedioDto;
import com.iss.Mappers.UserVedioMapper;
import com.iss.Repos.UserVideosRepository;
import com.iss.models.AccessStatus;
import com.iss.models.PaymentStatus;
import com.iss.models.UserVedio;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserVedioService {

    private final UserVideosRepository repos;

    public UserVedioService(UserVideosRepository repos) {
        this.repos = repos;
    }

    public UserVedioDto add(UserVedio user) {
        try {
            UserVedio savedUserVedio = repos.save(user);
            return UserVedioMapper.Instance.toDto(savedUserVedio);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding the UserVedio", ex);
        }
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

    public List<UserVedioDto> findByUsercourseId(int usercourseId, Pageable pageable) {
        try {
            Page<UserVedio> page = repos.findByUsercourseId(usercourseId, pageable);
            return page.getContent().stream()
                .map(uservideo -> {
                	if(uservideo.getAccessStatus().equals(AccessStatus.UNLOCKED))
                	{
	                    UserVedioDto dto = UserVedioMapper.Instance.toDto(uservideo);
	                    if (uservideo.getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
	                        dto.getVedio().setVideourl(uservideo.getVedio().getVideourl());
	                    }
	                    return dto;
                	}
                	UserVedioDto dto=new UserVedioDto();
                	BeanUtils.copyProperties(uservideo, dto,"vedio","usercourse","usertask","payments");
                	return dto;
                })
                .collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching UserVedios by Usercourse ID", ex);
        }
    }

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
}
