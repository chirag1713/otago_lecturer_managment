package com.emarket.utill;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.emarket.cacheprovider.CacheProvider;
import com.emarket.emarketcommon.entity.UserRegistration;
import com.emarket.emarketcommon.pojo.Constants;

public class EmarketUtill {
    @Autowired
    CacheProvider cacheProvider;

    public String getSessionId(HttpServletRequest request) {
        String sessionId = (String) request.getAttribute(Constants.COOKIE_SESSION_ID);
        if (sessionId == null) {
            sessionId = request.getParameter(Constants.PARAM_SJVTSESSIONID);
        }
        if (sessionId == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(Constants.COOKIE_SESSION_ID) && cookie.getValue() != null) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;
    }

    public boolean validateSessionId(HttpServletRequest request) {
        String sessionId = getSessionId(request);
        return validateSessionId(sessionId);
    }

    public boolean validateSessionId(String sessionId) {
        boolean flag = false;
        try {
            UUID.fromString(sessionId);
            flag = cacheProvider.isSessionExist(sessionId);
        } catch (Exception e) {
            //DO NOTHING FLAG WILL REMAIN FALSE
        }
        return flag;
    }

    public String generateSessionId() {
        return UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
    }

    public void createSession(String sessionId, UserRegistration user, int platformId, HttpServletRequest request, HttpServletResponse response) {
        createSession(sessionId, user, request, response);
    }

    public void createSession(String sessionId, UserRegistration user, HttpServletRequest request, HttpServletResponse response) {
//        if (platformId==Platform.TXN_VIA_WEB) {
        setCookie(Constants.COOKIE_SESSION_ID, sessionId, -1, request, response);
//        }
        request.setAttribute(Constants.COOKIE_SESSION_ID, sessionId);
        cacheProvider.createSession(sessionId, request.getRemoteAddr(), user);
    }

    public void setCookie(String name, String data, int maxage, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (name != null && !name.isEmpty() && data != null) {
                Cookie cookie = new Cookie(name, data);
                cookie.setMaxAge((maxage > 0) ? maxage : Integer.MAX_VALUE);
                cookie.setPath("/");
                cookie.setHttpOnly(false);
                cookie.setSecure(false);
                response.addCookie(cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
