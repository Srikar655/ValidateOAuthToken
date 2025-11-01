package com.iss.Services;

import com.iss.Dto.CourseCreateDto;
import com.iss.Dto.CourseDetailDto;
import com.iss.Dto.CourseFeaturesUpdateDto;
import com.iss.Dto.CourseListDisplayDto;
import com.iss.Dto.CourseUpdateDto;
import com.iss.Repos.CourseCategoryRepository;
import com.iss.Repos.CourseRepository;
import com.iss.models.Course;
import com.iss.models.CourseCategory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class CourseService {

    private final CourseRepository repos;
    private final LocalFileStorageService localFileStorageService;
    private final CourseCategoryRepository courseCategoryRepository;

    public CourseService(CourseRepository repos, LocalFileStorageService localFileStorageService, CourseCategoryRepository courseCategoryRepository) {
        this.repos = repos;
        this.localFileStorageService = localFileStorageService;
        this.courseCategoryRepository = courseCategoryRepository;
    }

  
    @Transactional
    public CourseDetailDto add(CourseCreateDto courseCreateDto, MultipartFile courseThumbnail) throws NotFoundException {
        try {
            Course newCourse = buildCourse(courseCreateDto, courseThumbnail);
            Course savedCourse = repos.save(newCourse);
            return buildCourseDetailDto(savedCourse);
        } catch (IOException e) {
            throw new RuntimeException("Could not process thumbnail file during course creation.", e);
        }
    }


    @Caching(
        put = { @CachePut(value = "courseDetails", key = "#courseUpdateDto.id") }
    )
    @Transactional
    public CourseDetailDto update(CourseUpdateDto courseUpdateDto, MultipartFile courseThumbnail) throws NotFoundException {
        Course existingCourse = repos.findById(courseUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException());

        try {
            updateCourseDetails(existingCourse, courseUpdateDto, courseThumbnail);
        } catch (IOException e) {
            throw new RuntimeException("Could not process new thumbnail file during update.", e);
        }

        Course savedCourse = repos.save(existingCourse);
        return buildCourseDetailDto(savedCourse);
    }
    
    @Caching(
            put = { @CachePut(value = "courseDetails", key = "#courseFeaturesUpdateDto.id") }
        )
        @Transactional
        public CourseDetailDto updateCourseFeatures(CourseFeaturesUpdateDto courseFeaturesUpdateDto) throws NotFoundException {
            Course existingCourse = repos.findById(courseFeaturesUpdateDto.getId())
                    .orElseThrow(() -> new NotFoundException());

            this.updateCourseFeatures(existingCourse, courseFeaturesUpdateDto);
            
            try {
            	Course savedCourse = repos.save(existingCourse);
            	return buildCourseDetailDto(savedCourse);
            }
            catch(Exception ex)
            {
            	throw ex;
            }
            
            
        }

    @Caching(
        evict = {
            @CacheEvict(value = "courseDetails", key = "#id"),
        }
    )
    @Transactional
    public void delete(int id) throws NotFoundException {
        if (!repos.existsById(id)) {
            throw new NotFoundException();
        }
        repos.deleteById(id);
    }

    @Cacheable(value = "courseDetails", key = "#id")
    @Transactional(readOnly = true)
    public CourseDetailDto find(int id) throws NotFoundException {
        return repos.findById(id)
                .map(this::buildCourseDetailDto)
                .orElseThrow(() -> new NotFoundException());
    }





    @Transactional(readOnly = true)
    public Page<CourseListDisplayDto> findAll(List<Integer> categoryIds, String searchTerm, Pageable pageable) {
        String effectiveSearchTerm = StringUtils.hasText(searchTerm) ? searchTerm : null;
        List<Integer> effectiveCategoryIds = (categoryIds != null && !categoryIds.isEmpty()) ? categoryIds : null;
        
        return repos.findAllCourseSummaries(effectiveCategoryIds, effectiveSearchTerm, pageable);
    }

    @Transactional(readOnly = true)
    public Course findAndGetCourse(int id) throws NotFoundException {
        return repos.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }



    private Course buildCourse(CourseCreateDto courseCreateDto, MultipartFile courseThumbnail) throws IOException, NotFoundException {
        CourseCategory category = courseCategoryRepository.findById(courseCreateDto.getCourseCategoryId())
                .orElseThrow(() -> new NotFoundException());
        
        String courseThumbnailUrl = this.localFileStorageService.store(courseThumbnail);
        
        return Course.builder()
                .coursename(courseCreateDto.getCourseName())
                .courseDescription(courseCreateDto.getCourseDescription())
                .courseprice(courseCreateDto.getCoursePrice())
                .courseTrailer(courseCreateDto.getCourseTrailer())
                .coursethumbnail(courseThumbnailUrl)
                .courseCategory(category)
                .build();
    }
    
    private void updateCourseDetails(Course course, CourseUpdateDto courseUpdateDto, MultipartFile courseThumbnail) throws IOException {
        course.setCoursename(courseUpdateDto.getCourseName());
        course.setCourseDescription(courseUpdateDto.getCourseDescription());
        course.setCourseprice(courseUpdateDto.getCoursePrice());

        if (courseThumbnail != null && !courseThumbnail.isEmpty()) {
            String newThumbnailUrl = this.localFileStorageService.store(courseThumbnail);
            course.setCoursethumbnail(newThumbnailUrl);
        }
    }
    private void updateCourseFeatures(Course course, CourseFeaturesUpdateDto courseFeaturesUpdateDto) {
        course.setCourseFeatures(courseFeaturesUpdateDto.getCourseFeatures());
        course.setCourseTrailer(courseFeaturesUpdateDto.getCourseTrailer());
    }
    
    private CourseDetailDto buildCourseDetailDto(Course c) {
        return CourseDetailDto.builder()
                .id(c.getId())
                .coursename(c.getCoursename())
                .courseDescription(c.getCourseDescription())
                .courseprice(c.getCourseprice())
                .coursethumbnail(c.getCoursethumbnail())
                .courseTrailer(c.getCourseTrailer())
                .courseFeatures(c.getCourseFeatures())
                .categoryId(c.getCourseCategory().getId())
                .build();
    }
}