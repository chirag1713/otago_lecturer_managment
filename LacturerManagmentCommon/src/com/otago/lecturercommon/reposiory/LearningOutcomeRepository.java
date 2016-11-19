package com.otago.lecturercommon.reposiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otago.lecturercommon.entity.LearningOutcome;

public interface LearningOutcomeRepository extends JpaRepository<LearningOutcome, Integer> {

    public List<LearningOutcome> findByCourseId(int courseId);

}
