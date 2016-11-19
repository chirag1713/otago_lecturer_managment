package com.otago.lecturerweb.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otago.lecturercommon.entity.CourseMapping;
import com.otago.lecturercommon.entity.LearningOutcome;
import com.otago.lecturercommon.reposiory.CourseMappingRepository;
import com.otago.lecturercommon.reposiory.LearningOutcomeRepository;

@Service
public class CourseDao {

    @Autowired
    private CourseMappingRepository courseMappingRepository;

    @Autowired
    private LearningOutcomeRepository learningOutcomeRepository;

    public List<CourseMapping> getUserCourses(int userId) {
        return courseMappingRepository.findByUserIdAndActiveTrueAndCourseActiveTrueAndCourseProgramActiveTrue(userId);
    }

    public List<LearningOutcome> getLearningOutcome(int courseId) {
        return learningOutcomeRepository.findByCourseId(courseId);
    }
}
