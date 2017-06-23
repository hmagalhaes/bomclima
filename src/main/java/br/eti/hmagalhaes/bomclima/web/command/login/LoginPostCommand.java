package br.eti.hmagalhaes.bomclima.web.command.login;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.model.dao.UsuarioDAO;
import br.eti.hmagalhaes.bomclima.model.entity.Usuario;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;
import br.eti.hmagalhaes.bomclima.web.util.security.AuthenticationManager;
import br.eti.hmagalhaes.bomclima.web.util.security.CryptoHelper;

@ApplicationScoped
public class LoginPostCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final UsuarioDAO usuarioDAO;
	private final AuthenticationManager authManager;
	private final CryptoHelper cryptoHelper;

	@Inject
	public LoginPostCommand(final UsuarioDAO usuarioDAO, final AuthenticationManager authManager,
			final CryptoHelper cryptoHelper) {
		this.usuarioDAO = usuarioDAO;
		this.authManager = authManager;
		this.cryptoHelper = cryptoHelper;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando login de usuário");

		final String login = request.getParameter("login");
		if (isBlank(login)) {
			sendFormError(request, response, "Por favor, entre com seu login.");
			return;
		}

		final String password = request.getParameter("password");
		if (isBlank(password)) {
			sendFormError(request, response, "Por favor, entre com sua senha.");
			return;
		}

		final Usuario usuario = usuarioDAO.findByLogin(login);
		if (usuario == null) {
			sendFormError(request, response, "Usuário ou senha incorretos");
			return;
		}

		final String passwordHash = cryptoHelper.encrypt(password);
		if (!usuario.getSenha().equals(passwordHash)) {
			sendFormError(request, response, "Usuário ou senha incorretos");
			return;
		}

		logger.debug("Usuário identificado, seguindo com criação da sessão");

		authManager.startSession(request, usuario);

		final ServletContext ctx = request.getServletContext();
		final String url = URLHelper.buildServiceURL(ctx, ActionMapping.REGISTER_SEARCH);
		logger.info("Usuário autenticado. Redirecionando para tela inicial => " + url);

		response.sendRedirect(url);
	}

	private void sendFormError(final HttpServletRequest request, final HttpServletResponse response, final String msg)
			throws ServletException, IOException {

		logger.info("Dados de entra inválidos, voltando ao formulário => " + msg);
		request.setAttribute("error", msg);
		RequestUtils.requestForward(request, response, ViewMapping.LOGIN);
	}
}
