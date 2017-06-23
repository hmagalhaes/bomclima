package br.eti.hmagalhaes.bomclima.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Força o uso de UTF-8 nos requests POST.
 */
@WebFilter("*")
public class UTF8EncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;

		if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
			httpRequest.setCharacterEncoding("UTF-8");
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
