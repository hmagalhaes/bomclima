package br.eti.hmagalhaes.bomclima.web.command.usuario;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.trimToNull;

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
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;
import br.eti.hmagalhaes.bomclima.web.util.validator.UsuarioValidator;

@ApplicationScoped
public class UsuarioEditPostCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final UsuarioDAO usuarioDAO;
	private final UsuarioValidator usuarioValidator;

	@Inject
	public UsuarioEditPostCommand(final UsuarioDAO usuarioDAO, final UsuarioValidator usuarioValidator) {
		this.usuarioDAO = usuarioDAO;
		this.usuarioValidator = usuarioValidator;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando serviço de alteração de usuário");

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
		logger.info("Usuário alterado. Redirecionando para listagem => " + listURL);
		response.sendRedirect(listURL);
	}

	private void forwardToForm(final HttpServletRequest request, final HttpServletResponse response,
			final Usuario usuario, final String errorMsg) throws ServletException, IOException {

		request.setAttribute("usuario", usuario);
		request.setAttribute("error", errorMsg);

		RequestUtils.requestForward(request, response, ViewMapping.USER_EDIT);
	}

	private String validate(final Usuario usuario) {
		final String msg = usuarioValidator.validate(usuario);
		if (msg != null) {
			return msg;
		}

		final Usuario existingLogin = usuarioDAO.findByLogin(usuario.getLogin());
		if (existingLogin != null && existingLogin.getId() != usuario.getId()) {
			return "Este login está indisponível";
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

		final boolean ativo = isAtivo(request);
		final String nome = trimToNull(request.getParameter("nome"));
		final String login = trimToNull(request.getParameter("login"));

		usuario.setId(id);
		usuario.setAtivo(ativo);
		usuario.setLogin(login);
		usuario.setNome(nome);
		return usuario;
	}

	private int getId(final HttpServletRequest request) {
		final String value = request.getParameter("id");
		if (isBlank(value) || !isNumeric(value)) {
			return -1;
		}
		return Integer.parseInt(value);
	}

	private boolean isAtivo(final HttpServletRequest request) {
		final String value = request.getParameter("ativo");
		return isBlank(value) ? false : Boolean.parseBoolean(value);
	}
}
