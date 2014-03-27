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

public class UserAuthFilter implements Filter {

	public UserAuthFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		match(chain, request, response);
	}

	public void match(FilterChain chain, ServletRequest request,
			ServletResponse response) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		User user = (User) req.getSession().getAttribute("userSession");
		String path = req.getRequestURL().toString().substring(req.getRequestURL().indexOf(req.getRequestURI()));
		String[] authArray = user.getAuth().split(",");
		boolean flag = false;
		for (int i = 0; i < authArray.length; i++) {
			if (path.indexOf(authArray[i]) > 0) {
				flag = true;
				break;
			}
		}
		if (flag) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/broadband-user/index");
			return;
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
