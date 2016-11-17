package com.otago.lecturerweb.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.otago.lecturercommon.entity.User;
import com.otago.lecturercommon.pojo.OtagoResponse;
import com.otago.lecturerweb.Dao.AuthDao;
import com.otago.lecturerweb.cache.ApplicationCache;
import com.otago.lecturerweb.utill.AuthValidationUtill;
import com.otago.lecturerweb.utill.CommonUtil;
import com.otago.lecturerweb.utill.JsonUtil;

@Controller
@RequestMapping(value = "auth")
public class AuthController {

    private static Logger logger = Logger.getLogger(AuthController.class);

    @Autowired
    private JsonUtil jsonUtil;

    @Autowired
    private AuthValidationUtill authValidationUtill;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ApplicationCache applicationCache;

    @Autowired
    private AuthDao authDao;

    @RequestMapping(value = "/register")
    @ResponseBody
    public String doSignup(@RequestParam(value = "data", required = false) String data, HttpServletRequest request, HttpServletResponse response) {
        if (logger.isDebugEnabled()) {
            logger.debug("request : " + request);
        }
        Map<String, Object> dataMap = jsonUtil.fromJson(data, Map.class);
        String responseStr;
        OtagoResponse aisResponse = new OtagoResponse();
        String sessionId = commonUtil.getSessionId(request);
        try {
            String mobile = (String) dataMap.get("mobile");
            if (mobile == null || mobile.isEmpty()) {
                throw new Exception("Invalid mobile.");
            }
            if (!authValidationUtill.mobileValidate(mobile)) {
                throw new Exception("Invalid mobileNo.");
            }
            if (!authDao.isMobileAvailableForSignUp(mobile)) {
                throw new Exception("MobileNo. already exist.");
            }

            String email = (String) dataMap.get("email");
            if (email == null || email.isEmpty()) {
                throw new Exception("Invalid email.");
            }
            if (!authValidationUtill.emailValidate(email)) {
                throw new Exception("Invalid email");
            }
            if (!authDao.isEmailAvailableForSignUp(email)) {
                throw new Exception("Email already exist.");
            }

            String fullName = (String) dataMap.get("fullName");
            if (fullName == null || fullName.isEmpty()) {
                throw new Exception("Invalid email.");
            }

            String password = (String) dataMap.get("password");
            if (password == null || password.isEmpty()) {
                throw new Exception("Invalid password.");
            }
            if (!authValidationUtill.passwordValidate(password)) {
                throw new Exception("Invalid Password.");
            }

            //  boolean emailActive = (Boolean) dataMap.get("emailActive");
            // create user in system
            User user = authDao.createNormalUser(email, mobile, fullName, password);
            if (user == null || user.getId() == 0) {
                throw new Exception("USER not created.");
            }

            //put userId in authObj as signUp done
            applicationCache.putSessionValue(sessionId, ApplicationCache.KEY_SESSION_USER, user);

            aisResponse.setSTATUS(OtagoResponse.STATUS_SUCCESS);
            aisResponse.setDATA(user);
            aisResponse.setMESSAGE("Sign-up successfully done.");
        } catch (Exception ex) {
            logger.error("Exception in auth-page", ex);
        } finally {
            responseStr = jsonUtil.toJson(aisResponse);
            if (logger.isDebugEnabled()) {
                logger.debug("response : " + response);
            }
        }
        return responseStr;
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public String doLogin(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "password", required = false) String password, HttpServletRequest request) {
        if (logger.isDebugEnabled()) {
            logger.debug("login-doLogin, emailId:" + email);
        }
        OtagoResponse aisResponse = new OtagoResponse();

        String response = null;
        String sessionId = commonUtil.getSessionId(request);
        try {
            if (email == null || email.isEmpty()) {
                throw new Exception("Invalid emailId.");
            }
            if (password == null || password.isEmpty()) {
                throw new Exception("Invalid password.");
            }
            User user = authValidationUtill.checkUser(sessionId, email, password);
            aisResponse.setSTATUS(OtagoResponse.STATUS_SUCCESS);
            aisResponse.setDATA(user);
            aisResponse.setMESSAGE("successfully");
            applicationCache.putSessionValue(sessionId, ApplicationCache.KEY_SESSION_USER, user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            response = jsonUtil.toJson(aisResponse);

            if (logger.isDebugEnabled()) {
                logger.debug("response : " + response);
            }
        }
        return response;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logOut(HttpServletRequest request) {
        OtagoResponse aisResponse = new OtagoResponse();
        String response;
        try {
            String sessionId = commonUtil.getSessionId(request);
            Map<String, String> jedisSessionMap = applicationCache.getSessionObj(sessionId);
            applicationCache.destroySession(sessionId);
            if (logger.isDebugEnabled()) {
                logger.debug("Logout, jedisSessionMap:" + jedisSessionMap);
            }
            aisResponse.setSTATUS(OtagoResponse.STATUS_SUCCESS);
            aisResponse.setMESSAGE("Logout successfull");
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            response = jsonUtil.toJson(aisResponse);
        }
        return response;
    }
}