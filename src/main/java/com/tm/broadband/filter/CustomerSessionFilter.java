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

import com.tm.broadband.model.Customer;

public class CustomerSessionFilter implements Filter {

	public CustomerSessionFilter() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		Customer customer = (Customer)req.getSession().getAttribute("customerSession");
		
		String url = req.getRequestURL().toString();
		System.out.println("CustomerSessionFilter: " + url);
		
		if (customer != null) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/home");
			return;
		}

	}

	public void init(FilterConfig arg0) throws ServletException {}

}
