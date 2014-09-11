package com.tm.broadband.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.broadband.model.User;

public class UserSessionFilter implements Filter {

	public UserSessionFilter() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		User user = (User)req.getSession().getAttribute("userSession");
		
		String url = req.getRequestURL().toString();
		System.out.println("UserSessionFilter: " + url);
		System.out.println("userSession: " + user);
		
		if (user != null) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/broadband-user");
			return;
		}

	}

	public void init(FilterConfig arg0) throws ServletException {}

}
