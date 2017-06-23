package br.eti.hmagalhaes.bomclima.web.command.usuario;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.model.dao.UsuarioDAO;
import br.eti.hmagalhaes.bomclima.model.entity.Usuario;
import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;
import br.eti.hmagalhaes.bomclima.web.util.security.CryptoHelper;
import br.eti.hmagalhaes.bomclima.web.util.validator.UsuarioValidator;

@ApplicationScoped
public class UsuarioPasswordPostCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final UsuarioDAO usuarioDAO;
	private final UsuarioValidator usuarioValidator;
	private final ConfigFacade configFacade;
	private final CryptoHelper cryptoHelper;

	@Inject
	public UsuarioPasswordPostCommand(final UsuarioDAO usuarioDAO, final UsuarioValidator usuarioValidator,
			final ConfigFacade configFacade, final CryptoHelper cryptoHelper) {
		this.usuarioDAO = usuarioDAO;
		this.usuarioValidator = usuarioValidator;
		this.configFacade = configFacade;
		this.cryptoHelper = cryptoHelper;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando alteração de senha");

		final Usuario usuario = getUsuario(request);

		final String errorMsg = validate(usuario);
		if (errorMsg != null) {
			logger.info("Dados de usuário inválidos. Redirecionando para o formulário. => " + errorMsg);
			forwardToForm(request, response, usuario, errorMsg);
			return;
		}

		logger.debug("Dados de usuário carregados, invocando persistência.");
		usuarioDAO.update(usuario);

		final String listURL = URLHelper.buildServiceURL(request.getServletContext(), ActionMapping.USER_SEARCH);
		logger.info("Senha alterada. Redirecionando para listagem => " + listURL);
		response.sendRedirect(listURL);
	}

	private void forwardToForm(final HttpServletRequest request, final HttpServletResponse response,
			final Usuario usuario, final String errorMsg) throws ServletException, IOException {

		usuario.setSenha(null);

		request.setAttribute("usuario", usuario);
		request.setAttribute("error", errorMsg);

		RequestUtils.requestForward(request, response, ViewMapping.USER_PASSWORD_EDIT);
	}

	private String validate(final Usuario usuario) {
		final String msg = usuarioValidator.validate(usuario);
		if (msg != null) {
			return msg;
		}

		final int smallestPass = configFacade.getInt(ConfigKey.MIN_ALLOWED_PASSWORD);
		if (usuario.getSenha().length() < smallestPass) {
			return String.format("A senha deve ter no mínimo %d caracteres", smallestPass);
		}

		return null;
	}

	private Usuario getUsuario(final HttpServletRequest request) {
		final int id = getId(request);
		if (id <= 0) {
			return null;
		}

		final Usuario usuario = usuarioDAO.findById(id);
		if (usuario == null) {
			return null;
		}

		final String senha = request.getParameter("senha");
		final String cryptedSenha = isBlank(senha) ? null : cryptoHelper.encrypt(senha);

		usuario.setSenha(cryptedSenha);

		return usuario;
	}

	private int getId(final HttpServletRequest request) {
		final String value = request.getParameter("id");
		if (isBlank(value) || !isNumeric(value)) {
			return -1;
		}
		return Integer.parseInt(value);
	}
}
