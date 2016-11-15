package com.emarket.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emarket.Dao.AuthDao;
import com.emarket.emarketcommon.entity.UserRegistration;
import com.emarket.emarketcommon.pojo.EMarketResponse;
import com.emarket.utill.EmarketUtill;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "auth")
public class AuthController {

    @Autowired
    private AuthDao authDao;

    @Autowired
    private EmarketUtill emarketUtill;

    @RequestMapping(value = "/userregistration")
    public String userRegistration(HttpServletRequest request) {
        return "UserRegistration";
    }

    @RequestMapping(value = "/signup")
    @ResponseBody
    public String userSignUp(@RequestParam(value = "data", required = true) String data, HttpServletRequest request) {
        Gson gson = new Gson();
        EMarketResponse eMarketResponse = new EMarketResponse();
        String response;

        String sessionId = emarketUtill.getSessionId(request);

        try {
            Map<String, Object> dataMap = gson.fromJson(data, Map.class);

            String userName = (String) dataMap.get("userName");
            if (userName == null || userName.isEmpty()) {
                throw new Exception("Blank UserName.");
            }
            String emailId = (String) dataMap.get("emailId");
            if (emailId == null || emailId.isEmpty()) {
                throw new Exception("Blank emailId.");
            }
            String password = (String) dataMap.get("password");
            if (password == null || password.isEmpty()) {
                throw new Exception("Blank password.");
            }
            String mobile = (String) dataMap.get("mobile");
            if (mobile == null || mobile.isEmpty()) {
                throw new Exception("Blank mobile.");
            }
            UserRegistration registration = new UserRegistration();
            registration.setUserName(userName);
            registration.setEmailId(emailId);
            registration.setPassord(password);
            registration.setMobile(mobile);
            authDao.saveUserRegDetails(registration);
            eMarketResponse.setSTATUS(EMarketResponse.STATUS_SUCCESS);
            eMarketResponse.setMESSAGE("User Details Save Successfully..");

        } catch (Exception e) {
            e.printStackTrace();
            eMarketResponse.setSTATUS(EMarketResponse.STATUS_ERROR);
            eMarketResponse.setMESSAGE(e.getMessage());
        }
        response = gson.toJson(eMarketResponse);
        return response;
    }
}
