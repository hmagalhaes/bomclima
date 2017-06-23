package br.eti.hmagalhaes.bomclima.web.command.usuario;

import static org.apache.commons.collections4.CollectionUtils.size;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.model.dao.UsuarioDAO;
import br.eti.hmagalhaes.bomclima.model.dto.UsuarioSearchParams;
import br.eti.hmagalhaes.bomclima.model.entity.Usuario;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;

@ApplicationScoped
public class UsuarioSearchCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final UsuarioDAO usuarioDAO;

	@Inject
	public UsuarioSearchCommand(final UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando serviço de busca de usuários");

		final UsuarioSearchParams params = buildParams(request);
		final List<Usuario> userList = usuarioDAO.find(params);

		logger.info("Usuários carregados, chamando a view => usuários: " + size(userList) + ", view: "
				+ ViewMapping.USER_SEARCH);

		request.setAttribute("usuarioList", userList);
		request.setAttribute("searchParams", params);

		RequestUtils.requestForward(request, response, ViewMapping.USER_SEARCH);
	}

	private UsuarioSearchParams buildParams(final HttpServletRequest request) {
		final Integer id = RequestUtils.getIntParam(request, "id");
		final String login = request.getParameter("login");
		final String nome = request.getParameter("nome");
		final String email = request.getParameter("email");
		final Boolean ativo = RequestUtils.getBooleanParam(request, "ativo");

		final UsuarioSearchParams params = new UsuarioSearchParams(id, login, nome, email, ativo);
		logger.debug("Parâmetros => " + params);
		return params;
	}
}
