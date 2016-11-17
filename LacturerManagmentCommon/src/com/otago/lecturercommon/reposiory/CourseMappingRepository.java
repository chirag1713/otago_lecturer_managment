package com.otago.lecturercommon.reposiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otago.lecturercommon.entity.CourseMapping;

public interface CourseMappingRepository extends JpaRepository<CourseMapping, Integer> {
    public List<CourseMapping> findByUserIdAndActiveTrueAndCourseActiveTrueAndCourseProgramActiveTrue(int userId);

}
