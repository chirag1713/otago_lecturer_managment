package com.otago.lecturercommon.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otago.lecturercommon.entity.Program;

public interface ProgramRepository extends JpaRepository<Program, Integer> {

}
