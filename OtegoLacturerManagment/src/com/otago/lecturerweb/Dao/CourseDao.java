package com.otago.lecturerweb.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otago.lecturercommon.entity.CourseMapping;
import com.otago.lecturercommon.reposiory.CourseMappingRepository;

@Service
public class CourseDao {

    @Autowired
    private CourseMappingRepository courseMappingRepository;

    public List<CourseMapping> getUserCourses(int userId) {
        return courseMappingRepository.findByUserIdAndActiveTrueAndCourseActiveTrueAndCourseProgramActiveTrue(userId);
    }
}
