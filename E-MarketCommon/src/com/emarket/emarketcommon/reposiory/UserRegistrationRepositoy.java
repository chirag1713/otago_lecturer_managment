package com.emarket.emarketcommon.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emarket.emarketcommon.entity.UserRegistration;

public interface UserRegistrationRepositoy extends JpaRepository<UserRegistration, Integer> {

}
