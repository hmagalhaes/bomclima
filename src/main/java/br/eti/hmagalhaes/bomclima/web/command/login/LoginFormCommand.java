package br.eti.hmagalhaes.bomclima.web.command.login;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;

@ApplicationScoped
public class LoginFormCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Carregando formulário de login");

		logger.info("Direcionando para view => " + ViewMapping.LOGIN);
		RequestUtils.requestForward(request, response, ViewMapping.LOGIN);
	}
}
