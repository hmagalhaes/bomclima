package br.eti.hmagalhaes.bomclima.web.command.registro;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.model.dao.RegistroDAO;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;

@ApplicationScoped
public class RegistroDelPostCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final RegistroDAO registroDAO;
	private final RegistroRequestDataLoader dataLoader;

	@Inject
	public RegistroDelPostCommand(final RegistroDAO registroDAO, final RegistroRequestDataLoader dataLoader) {
		this.registroDAO = registroDAO;
		this.dataLoader = dataLoader;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando serviço de remoção de registro");

		final long id = dataLoader.getId(request);

		logger.debug("Removendo registro => id: " + id);

		registroDAO.removeById(id);

		final String url = getSuccessURL(request);
		logger.info("Registro removido, redirecionando à listagem => " + url);
		response.sendRedirect(url);
	}

	private String getSuccessURL(final HttpServletRequest request) {
		final ServletContext ctx = request.getServletContext();
		return URLHelper.buildSuccessServiceURL(ctx, ActionMapping.REGISTER_SEARCH, "Registro removido com sucesso");
	}
}
