package com.otago.lecturercommon.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otago.lecturercommon.entity.UserRegistration;

public interface UserRegistrationRepositoy extends JpaRepository<UserRegistration, Integer> {

}
