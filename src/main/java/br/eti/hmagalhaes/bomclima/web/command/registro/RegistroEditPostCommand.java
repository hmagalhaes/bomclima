package br.eti.hmagalhaes.bomclima.web.command.registro;

import java.io.IOException;
import java.util.Date;

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
import br.eti.hmagalhaes.bomclima.model.entity.TipoRegistro;
import br.eti.hmagalhaes.bomclima.model.entity.Usuario;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.SessionUtils;
import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;
import br.eti.hmagalhaes.bomclima.web.util.validator.RegistroValidator;

@ApplicationScoped
public class RegistroEditPostCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final RegistroDAO registroDAO;
	private final RegistroValidator registroValidator;
	private final RegistroEditFormModelHelper modelHelper;
	private final RegistroRequestDataLoader requestDataLoader;

	@Inject
	public RegistroEditPostCommand(final RegistroDAO registroDAO, final RegistroValidator registroValidator,
			final RegistroEditFormModelHelper modelHelper, final RegistroRequestDataLoader requestDataLoader) {
		this.registroDAO = registroDAO;
		this.registroValidator = registroValidator;
		this.modelHelper = modelHelper;
		this.requestDataLoader = requestDataLoader;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando serviço de postagem de registro");

		final Registro registro = getRegistro(request);

		final String errorMsg = registroValidator.validate(registro);
		if (errorMsg != null) {
			logger.info("Dados de registro inválidos. Redirecionando para o formulário. => " + errorMsg);
			modelHelper.provideErrorModel(request, registro, errorMsg);
			RequestUtils.requestForward(request, response, ViewMapping.REGISTER_EDIT_FORM);
			return;
		}

		logger.debug("Dados de registro carregados, invocando persistência.");
		registroDAO.update(registro);

		final String listURL = getSuccessURL(request);
		logger.info("Registro alterado. Redirecionando para listagem => " + listURL);
		response.sendRedirect(listURL);
	}

	private String getSuccessURL(final HttpServletRequest request) {
		final ServletContext ctx = request.getServletContext();
		return URLHelper.buildSuccessServiceURL(ctx, ActionMapping.REGISTER_SEARCH, "Registro alterado com sucesso");
	}

	private Registro getRegistro(final HttpServletRequest request) {
		final long id = requestDataLoader.getId(request);
		if (id <= 0) {
			return null;
		}

		final Registro existingRegister = registroDAO.findById(id);
		if (existingRegister == null) {
			return null;
		}

		final TipoRegistro tipoRegistro = existingRegister.getTipoRegistro();
		final Usuario currentUser = SessionUtils.getCurrentUser(request.getSession(false));

		final Registro newRegister = requestDataLoader.loadRegister(request, tipoRegistro);
		newRegister.setDataRegistro(existingRegister.getDataRegistro());
		newRegister.setRegistrante(existingRegister.getRegistrante());
		newRegister.setDataUltimaEdicao(new Date());
		newRegister.setUltimoEditor(currentUser);
		newRegister.setTipoRegistro(tipoRegistro);
		return newRegister;
	}
}
