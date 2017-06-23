package br.eti.hmagalhaes.bomclima.web.command.usuario;

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
public class UsuarioInsertFormCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando carga do formulário de inclusão de usuário");

		logger.info("Chamando view => " + ViewMapping.USER_INSERT);

		RequestUtils.requestForward(request, response, ViewMapping.USER_INSERT);
	}
}
