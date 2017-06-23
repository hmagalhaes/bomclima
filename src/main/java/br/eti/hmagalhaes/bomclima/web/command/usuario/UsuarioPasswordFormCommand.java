package br.eti.hmagalhaes.bomclima.web.command.usuario;

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
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;

@ApplicationScoped
public class UsuarioPasswordFormCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final UsuarioDAO usuarioDAO;

	@Inject
	public UsuarioPasswordFormCommand(final UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando formulário de alteração de senha");

		final int userId = RequestUtils.getRequiredIntParam(request, "id");
		final Usuario usuario = usuarioDAO.findById(userId);
		if (usuario == null) {
			throw new IllegalArgumentException("Usuário inexistente => ID: " + userId);
		}
		
		usuario.setSenha(null);
		request.setAttribute("usuario", usuario);

		logger.info("Dados carregados, chamando view => " + ViewMapping.USER_PASSWORD_EDIT);

		RequestUtils.requestForward(request, response, ViewMapping.USER_PASSWORD_EDIT);
	}
}
