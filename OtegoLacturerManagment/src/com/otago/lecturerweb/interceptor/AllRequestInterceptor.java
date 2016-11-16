/*
 * To change this template, choose Tools | Templates
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otago.lecturerweb.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.otago.lecturercommon.entity.User;
import com.otago.lecturerweb.utill.CommonUtil;

/**
 *
 * @author omm
 */
@Component
public class AllRequestInterceptor extends HandlerInterceptorAdapter {
	private static org.apache.log4j.Logger log = Logger
			.getLogger(AllRequestInterceptor.class);

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private ApplicationContext context;

	private String[] publicUrl = new String[] { "/login", "/system" };

	public boolean isPublicUrl(String url) {
		for (String string : publicUrl) {
			if (url.startsWith(string)) {
				return true;
			}
		}
		return false;
	}

	public boolean isApiUrl(String url) {
		return false;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String currentUrl = request.getRequestURI();
		if (currentUrl.startsWith("/cache")
				|| currentUrl.startsWith("/property")) {
			return true;
		} else {
			checkAndUpdateSessionId(request, response);
			String redirectUrl = checkAuthAndUser(request);
			// TODO Redirect Call
			if (redirectUrl != null) {
				response.sendRedirect(redirectUrl);
				return false;
			}
			/*
			 * if (request.getMethod().toUpperCase().equals("GET")) {
			 * updateUrlPreviousUrl(request); }
			 */
			// return true;
		}
		// // TODO put ip or secret key validation to protect rest APIs like
		// if (!this.commonUtil.checkRequestPerMinute(request.getRemoteAddr()))
		// {
		// commonUtil.writeErrorResponseForPostRequest(request, response,
		// "ERROR_REQLIMIT",
		// String.valueOf(AfroConstant.MAX_REQUEST_FROMIP_PERMINUTE));
		// return false;
		// } else {
		// String url = commonUtil.getUrl(request);
		// String sessionId = commonUtil.checkAndSetSessionId(request,
		// response);
		// if (!isPublicUrl(url)) {
		// if (!commonUtil.isUserExistInSession(sessionId)) {
		// response.sendRedirect(commonUtil.getLoginUrl(request));
		// return false;
		// }
		// }
		// }
		// if (request.getMethod().equals("GET")) {
		// request.setAttribute(AfroConstant.PARAM_RETURNURL,
		// commonUtil.getReturnUrl(request));
		// }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	private void checkAndUpdateSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = commonUtil.validateSessionId(request);
		if (!flag) {
			String sessionId = commonUtil.generateSessionId();
			commonUtil.createSession(sessionId, null, request, response);
		}
	}

	private String checkAuthAndUser(HttpServletRequest request)
			throws IOException {
		String[] urls = new String[] { "/user/myprofile",
				"/user/transactiondetails" };
		boolean isAuthReq = false;
		String redirectUrl = null;
		try {

			String currentUrl = request.getRequestURI().replaceFirst(
					request.getContextPath(), "");

			if (request.getParameter("auth") != null
					&& request.getParameter("auth").equals("true")) {
				isAuthReq = true;
			} else {
				for (String url : urls) {
					if (currentUrl.startsWith(url)) {
						isAuthReq = true;
						break;
					}
				}
			}
			if (isAuthReq) {
				User user = commonUtil.getUser(request);
				if (user == null) {
					String sessionId = commonUtil.getSessionId(request);
					try {
						boolean flag = commonUtil.processUserLogin(sessionId,
								request);
						if (!flag) {
							// temp set unauthorizeduser, later it will redirect
							// for login page
							// redirectUrl = BackLinkUtil.HOME_PAGE_URL;
							redirectUrl = "/";
							// redirectUrl =
							// commonUtil.getLoginRedirect(sessionId,
							// Platform.TXN_VIA_WEB, request);
						}
					} catch (Exception e) {
						log.error("Error while processing login", e);
						redirectUrl = "/";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return redirectUrl;
	}
}
