package br.eti.hmagalhaes.bomclima.web.command.registro;

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

import br.eti.hmagalhaes.bomclima.model.dao.RegistroDAO;
import br.eti.hmagalhaes.bomclima.model.entity.Registro;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;

@ApplicationScoped
public class RegistroDelFormCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final RegistroDAO registroDAO;
	private final RegistroEditFormModelHelper modelHelper;

	@Inject
	public RegistroDelFormCommand(final RegistroDAO registroDAO, final RegistroEditFormModelHelper modelHelper) {
		this.registroDAO = registroDAO;
		this.modelHelper = modelHelper;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando formulário de confirmação de remoção");

		final Registro registro = findRegistro(request);
		if (registro == null) {
			sendRedirectBecauseOfRegisterNotFound(request, response);
			return;
		}

		modelHelper.provideOkModel(request, registro);

		logger.info("Dados carregados, direcionando à view => " + ViewMapping.DELETE_CONFIRM_FORM);
		RequestUtils.requestForward(request, response, ViewMapping.DELETE_CONFIRM_FORM);
	}

	private void sendRedirectBecauseOfRegisterNotFound(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		final ServletContext ctx = request.getServletContext();
		final String url = URLHelper.buildErrorServiceURL(ctx, ActionMapping.REGISTER_SEARCH,
				"Registro não encontrado. Selecione um registro da lista e tente novamente.");

		logger.info("Registro não encontrado. Direcionando à tela de escolha => " + url);

		response.sendRedirect(url);
	}

	private Registro findRegistro(final HttpServletRequest request) {
		final String value = request.getParameter("id");
		if (isBlank(value)) {
			return null;
		}
		final long id = Long.parseLong(value);
		return registroDAO.findById(id);
	}
}
