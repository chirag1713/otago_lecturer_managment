package com.emarket.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emarket.emarketcommon.entity.UserRegistration;
import com.emarket.emarketcommon.reposiory.UserRegistrationRepositoy;

@Component
public class AuthDao {

    @Autowired
    private UserRegistrationRepositoy registrationRepositoy;

    public void saveUserRegDetails(UserRegistration userRegistration) {
        registrationRepositoy.save(userRegistration);
    }

}
