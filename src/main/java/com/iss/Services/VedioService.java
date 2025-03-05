package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iss.Dto.VideoDto;
import com.iss.Mappers.VideoMapper;
import com.iss.Repos.VedioRepository;
import com.iss.models.Vedio;

@Service
public class VedioService {

    private final VedioRepository repos;

    public VedioService(VedioRepository repos) {
        this.repos = repos;
    }

    public VideoDto add(VideoDto vedio) {
        VideoMapper mapper = VideoMapper.Instance;
        return mapper.toDto(repos.save(mapper.toEntity(vedio)));
    }

    public List<VideoDto> add(List<Vedio> vedios) {
        return VideoMapper.Instance.toDtoList(repos.saveAll(vedios));
    }

    public List<VideoDto> findAll() {
        return VideoMapper.Instance.toDtoList(repos.findAll());
    }

    public VideoDto find(int id) {
        return VideoMapper.Instance.toDto(repos.findById(id).orElse(null));
    }

    public List<VideoDto> findByCourseId(int courseId, Pageable pageable) {
        List<Vedio> list = repos.findByCourseId(courseId, pageable).getContent();
        return VideoMapper.Instance.toDtoList(list);
    }

    public VideoDto update(VideoDto vedioDto) {
        try {
            Optional<Vedio> existingVedio = repos.findById(vedioDto.getId());
            if (existingVedio.isPresent()) {
                Vedio existingEntity = existingVedio.get();
                Vedio updatedEntity = VideoMapper.Instance.toEntity(vedioDto);
                
                BeanUtils.copyProperties(updatedEntity, existingEntity, "tasks", "uservedio");
                return VideoMapper.Instance.toDto(repos.save(existingEntity));
            } else {
                throw new RuntimeException("Vedio with id " + vedioDto.getId() + " not found, update failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error updating Vedio with id " + vedioDto.getId(), ex);
        }
    }

    public void delete(int id) {
        repos.deleteById(id);
    }
}
