package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iss.Dto.VideoCreateDto;
import com.iss.Dto.VideoDetailDto;
import com.iss.Dto.VideoDto;
import com.iss.Dto.VideoListDisplayDto;
import com.iss.Dto.VideoUpdateDto;
import com.iss.Mappers.VideoMapper;
import com.iss.Repos.CourseRepository;
import com.iss.Repos.VedioRepository;
import com.iss.models.Course;
import com.iss.models.Vedio;

@Service
public class VedioService {

    private final VedioRepository repos;
    private final CourseRepository courseRepos;

    public VedioService(VedioRepository repos, CourseRepository courseRepos) {
        this.repos = repos;
        this.courseRepos = courseRepos;
    }

    @Transactional
    public VideoDetailDto add(VideoCreateDto videoCreateDto) {
        try {
            Vedio video = this.repos.save(this.buildVideo(videoCreateDto));
            return this.buildVideoDetailDto(video);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding Vedio", ex);
        }
    }
    private Vedio buildVideo(VideoCreateDto videoCreateDto) throws NotFoundException
    {
    	Course c = this.courseRepos.findById(videoCreateDto.getCourseId()).orElseThrow(()->  new NotFoundException());
    	return Vedio.builder()
    					.vediodescription(videoCreateDto.getVideoDescription())
    					.vediotitle(videoCreateDto.getVideoTitle())
    					.vedioprice(videoCreateDto.getVideoPrice())
    					.videourl(videoCreateDto.getVideoUrl())
    					.course(c)
    					.build();
    }
    private VideoDetailDto buildVideoDetailDto(Vedio video)
	{
    	return VideoDetailDto.builder()
    					.id(video.getId())
    					.videoDescription(video.getVediodescription())
    					.videoPrice(video.getVedioprice())
    					.videoTitle(video.getVediotitle())
    					.videoUrl(video.getVideourl())
    					.build();
	}
    @Transactional
    public List<VideoDto> add(List<Vedio> vedios) {
        try {
            return VideoMapper.Instance.toDtoList(repos.saveAll(vedios));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding multiple Vedios", ex);
        }
    }

    public List<VideoDto> findAll() {
        try {
            return VideoMapper.Instance.toDtoList(repos.findAll());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching all Vedios", ex);
        }
    }
    @Cacheable(value = "video", key = "#id")
    @Transactional(readOnly = true)
    public VideoDetailDto find(int id) {
        try {
            Optional<Vedio> vedioOpt = repos.findById(id);
            if (vedioOpt.isPresent()) {
                return this.buildVideoDetailDto(vedioOpt.get());
            } 
                return null; 

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching Vedio with id " + id, ex);
        }
    }
    public Page<VideoListDisplayDto> findByCourseId(int courseId, Pageable pageable) {
        try {
            Page<VideoListDisplayDto> optionallist = repos.findVideosByCourseId(courseId, pageable);
            return optionallist;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching Vedios by Course ID", ex);
        }
    }

    @Caching(
            put = { @CachePut(value = "video", key = "#videoDto.id") }
        )
    @Transactional
    public VideoDetailDto update(VideoUpdateDto videoDto) {
        try {
            Vedio existingVedio = repos.findById(videoDto.getId()).orElseThrow(()->new NotFoundException());
            this.updateVideoDetails(existingVedio, videoDto);
            Vedio newVedio = this.repos.save(existingVedio);
            return this.buildVideoDetailDto(newVedio);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error updating Vedio with id " + videoDto.getId(), ex);
        }
    }
    private void updateVideoDetails(Vedio video,VideoUpdateDto videoDto)
    {
    	video.setVediodescription(videoDto.getVideoDescription());
    	video.setVedioprice(videoDto.getVideoPrice());
    	video.setVediotitle(videoDto.getVideoTitle());
    	video.setVideourl(videoDto.getVideoUrl());
    }
    @Caching(
            evict = {
                @CacheEvict(value = "video", key = "#id"),
            }
        )
        @Transactional
    public void delete(int id) {
        try {
            if (repos.existsById(id)) {
                repos.deleteById(id);
            } else {
                throw new RuntimeException("Vedio with id " + id + " not found, delete failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error deleting Vedio with id " + id, ex);
        }
    }
}
