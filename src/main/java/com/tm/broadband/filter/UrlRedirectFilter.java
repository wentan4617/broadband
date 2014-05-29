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

public class UrlRedirectFilter implements Filter{ 
	
    /**
     * function：make http://example.com redirect to http://www.example.com
     * */
    private static final String DOMAIN = "cyberpark.co.nz";
    
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// Get request domain
		String serverName = request.getServerName();

		// Get request path
		String url = req.getRequestURI().toString();
		String queryString = (req.getQueryString() == null ? "" : "?" + req.getQueryString()); // Get params in the path

		int end = serverName.indexOf(DOMAIN);

		if (end == -1 || end == 0) { // If is example.com
			// System.out.println("UrlRedirectFilter: " + url);
			res.setStatus(301);
			res.setHeader("Location", "http://www." + DOMAIN + url + queryString);
			res.setHeader("Connection", "close");
			return;
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}