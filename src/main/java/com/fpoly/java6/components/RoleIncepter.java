package com.fpoly.java6.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fpoly.java6.entities.Account;
import com.fpoly.java6.jpa.AccountJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleIncepter implements HandlerInterceptor{
	@Autowired
	private AccountJPA accountJPA;
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	
	public boolean perHandle(Object handler) throws Exception{
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("user".equals(cookie.getName())) {
					String[] userInfo = cookie.getValue().split("-");
					int role = Integer.parseInt(userInfo[1]);
				
					if (request.getRequestURI().startsWith("/admin") && role !=1) {
						response.sendError(HttpStatus.FORBIDDEN.value(),"access");
						return false;
					}
					return true;
				}
			}
		}
		response.sendError(HttpStatus.UNAUTHORIZED.value(),"User not aut");
		return false;
	}
}
