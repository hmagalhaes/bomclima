package br.eti.hmagalhaes.bomclima.web.command.usuario;

import static org.apache.commons.lang3.StringUtils.isBlank;
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
import br.eti.hmagalhaes.bomclima.web.util.security.CryptoHelper;
import br.eti.hmagalhaes.bomclima.web.util.validator.UsuarioValidator;

@ApplicationScoped
public class UsuarioInsertPostCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final UsuarioDAO usuarioDAO;
	private final UsuarioValidator usuarioValidator;
	private final CryptoHelper cryptoHelper;

	@Inject
	public UsuarioInsertPostCommand(final UsuarioDAO usuarioDAO, final UsuarioValidator usuarioValidator,
			final CryptoHelper cryptoHelper) {
		this.usuarioDAO = usuarioDAO;
		this.usuarioValidator = usuarioValidator;
		this.cryptoHelper = cryptoHelper;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando serviço de inclusão de usuário");

		final Usuario usuario = getUsuario(request);

		final String errorMsg = validate(usuario);
		if (errorMsg != null) {
			logger.info("Dados de usuário inválidos. Redirecionando para o formulário. => " + errorMsg);
			forwardToForm(request, response, usuario, errorMsg);
			return;
		}

		logger.debug("Dados de usuário carregados, invocando persistência.");
		usuarioDAO.insert(usuario);

		final String listURL = URLHelper.buildServiceURL(request.getServletContext(), ActionMapping.USER_SEARCH);
		logger.info("Usuário incluído. Redirecionando para listagem => " + listURL);
		response.sendRedirect(listURL);
	}

	private void forwardToForm(final HttpServletRequest request, final HttpServletResponse response,
			final Usuario usuario, final String errorMsg) throws ServletException, IOException {

		request.setAttribute("usuario", usuario);
		request.setAttribute("error", errorMsg);

		RequestUtils.requestForward(request, response, ViewMapping.USER_INSERT);
	}

	private String validate(final Usuario usuario) {
		final String msg = usuarioValidator.validate(usuario);
		if (msg != null) {
			return msg;
		}

		final Usuario existing = usuarioDAO.findByLogin(usuario.getLogin());
		if (existing != null) {
			return "Login já existente";
		}

		return null;
	}

	private Usuario getUsuario(final HttpServletRequest request) {
		final String login = trimToNull(request.getParameter("login"));
		final String senha = trimToNull(request.getParameter("senha"));
		final String nome = trimToNull(request.getParameter("nome"));

		final String cryptedPass = isBlank(senha) ? null : cryptoHelper.encrypt(senha);

		final Usuario usuario = new Usuario();
		usuario.setId(0);
		usuario.setAtivo(true);
		usuario.setLogin(login);
		usuario.setNome(nome);
		usuario.setSenha(cryptedPass);
		return usuario;
	}
}
