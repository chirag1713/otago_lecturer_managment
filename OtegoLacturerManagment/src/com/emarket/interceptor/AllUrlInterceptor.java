/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emarket.interceptor;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.emarket.utill.EmarketUtill;

/**
 *
 * @author omm
 */
@Component
public class AllUrlInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private EmarketUtill emarketUtill;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        checkAndUpdateSessionId(request, response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        super.postHandle(request, response, handler, modelAndView);
    }

    private void checkAndUpdateSessionId(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = emarketUtill.validateSessionId(request);
        if (!flag) {
            String sessionId = emarketUtill.generateSessionId();
            emarketUtill.createSession(sessionId, null, request, response);
        }
    }
}