package br.eti.hmagalhaes.bomclima.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;

@WebServlet("/")
@SuppressWarnings("serial")
public class IndexController extends HttpServlet {

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException, ServletException {

		final String url = URLHelper.buildServiceURL(getServletContext(), ActionMapping.LOGIN_FORM);
		response.sendRedirect(url);
	}
}
