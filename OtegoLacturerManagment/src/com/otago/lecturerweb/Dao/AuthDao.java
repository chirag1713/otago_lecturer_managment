package com.otago.lecturerweb.Dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otago.lecturercommon.entity.AuthUser;
import com.otago.lecturercommon.entity.Status;
import com.otago.lecturercommon.entity.User;
import com.otago.lecturercommon.reposiory.AuthUserRepository;
import com.otago.lecturercommon.reposiory.UserRepository;
import com.otago.lecturerweb.cache.CommonObjectCache;

@Service
public class AuthDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private CommonObjectCache commonObjectCache;

    public boolean isMobileAvailableForSignUp(String mobile) {
        User user = userRepository.findByMobile(mobile);
        if (user == null) {
            return true;
        }
        return false;
    }

    public boolean isAvailaleGmailUser(String email) {
        List<String> emailList = userRepository.availaleGmailUser(email);
        if (emailList == null || emailList.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isEmailAvailableForSignUp(String email) {
        if (email.contains("@gmail.com") || email.contains("@googlemail.com")) {
            return isAvailaleGmailUser(email);
        } else {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Transactional
    public User createNormalUser(String email, String mobile, String fullName, String password) {

        Date now = new Date();

        // create new user
        User user = new User();
        user.setEmail(email);
        user.setMobile(mobile);
        user.setFullName(fullName);
        user.setStatus(commonObjectCache.getStatus(Status.ACTIVE));
        user.setCreatedOn(new Date());
        user.setUpdatedOn(new Date());

        userRepository.save(user);

        // create user credential
        AuthUser authUser = new AuthUser();
        authUser.setPassword(authUserRepository.getUserPassword(password));
        authUser.setUserId(user.getId());

        authUserRepository.save(authUser);

        return user;
    }

    public User getUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public AuthUser getUserCredentialByUsernameAndPassword(int userId, String password) {
        return authUserRepository.findByUserIdAndPassword(userId, authUserRepository.getUserPassword(password));
    }

}
