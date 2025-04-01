package com.iss.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
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
        try {
            VideoMapper mapper = VideoMapper.Instance;
            return mapper.toDto(repos.save(mapper.toEntity(vedio)));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error adding Vedio", ex);
        }
    }

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

    public VideoDto find(int id) {
        try {
            Optional<Vedio> vedioOpt = repos.findById(id);
            if (vedioOpt.isPresent()) {
                return VideoMapper.Instance.toDto(vedioOpt.get());
            } 
                return null; // Return null if the Vedio is not found

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching Vedio with id " + id, ex);
        }
    }

    public List<VideoDto> findByCourseId(int courseId, Pageable pageable) {
        try {
            Optional<Page<Vedio>> optionallist = repos.findByCourseId(courseId, pageable);
            if(optionallist.isPresent())
            {
            	return VideoMapper.Instance.toDtoList(optionallist.get().getContent());
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error fetching Vedios by Course ID", ex);
        }
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
            	return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error updating Vedio with id " + vedioDto.getId(), ex);
        }
    }

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
