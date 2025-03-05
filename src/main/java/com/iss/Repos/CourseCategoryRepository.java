package com.iss.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.models.CourseCategory;
@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Integer> {

}
