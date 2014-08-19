package com.tm.broadband.wholesale.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tm.broadband.model.TMSWholesaler;
import com.tm.broadband.model.User;

public class WholesalerSessionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		User user = (User)req.getSession().getAttribute("userSession");
		TMSWholesaler wholesaler = (TMSWholesaler) req.getSession().getAttribute("wholesalerSession");
		
		String url = req.getRequestURL().toString();
		System.out.println("WholesalerSessionFilter: " + url);
		
		if (user != null || wholesaler != null) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/broadband-wholesale");
			return;
		}

	}

	@Override
	public void destroy() {}

}
