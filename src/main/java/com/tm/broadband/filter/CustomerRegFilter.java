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
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.User;

public class CustomerRegFilter implements Filter {

	public CustomerRegFilter() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		Customer customer = (Customer)req.getSession().getAttribute("customerReg");
		System.out.println("customerReg:" + customer);
		
		String url = req.getRequestURL().toString();
		System.out.println("CustomerRegFilter: " + url);
		
		if (customer != null) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/home");
			return;
		}

	}

	public void init(FilterConfig arg0) throws ServletException {}

}
