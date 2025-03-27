package com.iss.Services;


import com.iss.Dto.UserVedioDto;
import com.iss.Mappers.UserVedioMapper;
import com.iss.Repos.UserVideosRepository;
import com.iss.models.PaymentStatus;
import com.iss.models.UserVedio;
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
            return UserVedioMapper.Instance.toDto(repos.save(user));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public List<UserVedioDto> findAll(Pageable pageable) {
        try {
            Page<UserVedio> page = repos.findAll(pageable);
            return UserVedioMapper.Instance.toDtoList(page.getContent());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public List<UserVedioDto> findAll() {
        try {
            List<UserVedio> page = repos.findAll();
            return UserVedioMapper.Instance.toDtoList(page);
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
    public List<UserVedioDto> findByUsercourseId(int usercourseId, Pageable pageable) {
        try {
            Page<UserVedio> page = repos.findByUsercourseId(usercourseId, pageable);
            return page.getContent().stream()
                .map(uservideo -> {
                    UserVedioDto dto = UserVedioMapper.Instance.toDto(uservideo);
                    System.out.println("PaymentStatusIS"+uservideo.getPaymentStatus());
                    System.out.println(PaymentStatus.COMPLETED);
                    if (uservideo.getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
                    	System.out.println("VideoURL IS:"+uservideo.getVedio().getVideourl());
                        dto.getVedio().setVideourl(uservideo.getVedio().getVideourl());
                    }
                    
                    return dto; 
                })
                .collect(Collectors.toList()); 
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
	public double getUserVideoPrice(int userVideoId) {
		Optional<Double> optionalprice=repos.getUserVideoPriceById(userVideoId);
		if(optionalprice.isPresent())
		{
			return optionalprice.get();
		}
		return 0;
	}
}
