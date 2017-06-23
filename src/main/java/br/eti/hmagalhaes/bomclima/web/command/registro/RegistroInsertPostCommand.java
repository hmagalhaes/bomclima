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
public class RegistroInsertPostCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final RegistroDAO registroDAO;
	private final RegistroValidator registroValidator;
	private final RegistroInsertFormModelHelper modelHelper;
	private final RegistroRequestDataLoader requestDataLoader;

	@Inject
	public RegistroInsertPostCommand(final RegistroDAO registroDAO, final RegistroValidator registroValidator,
			final RegistroInsertFormModelHelper modelHelper, final RegistroRequestDataLoader requestDataLoader) {
		this.registroDAO = registroDAO;
		this.registroValidator = registroValidator;
		this.modelHelper = modelHelper;
		this.requestDataLoader = requestDataLoader;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando inclusão de registro");

		final TipoRegistro tipoRegistro = requestDataLoader.getTipoRegistro(request);
		if (tipoRegistro == null) {
			redirectForBadRegisterType(request, response);
			return;
		}

		final Registro registro = loadRegister(request, tipoRegistro);

		final String errorMsg = registroValidator.validate(registro);
		if (errorMsg != null) {
			logger.info("Dados de registro inválidos. Redirecionando para o formulário. => " + errorMsg);
			modelHelper.provideErrorModel(request, tipoRegistro, registro, errorMsg);
			RequestUtils.requestForward(request, response, ViewMapping.REGISTER_INSERT);
			return;
		}

		logger.info("Dados carregados, realizando persistência");
		registroDAO.insert(registro);

		final String listURL = getSuccessURL(request);
		logger.info("Registro incluído. Redirecionando para listagem => " + listURL);
		response.sendRedirect(listURL);
	}

	private Registro loadRegister(final HttpServletRequest request, final TipoRegistro tipoRegistro) {
		final Usuario currentUser = SessionUtils.getCurrentUser(request.getSession(false));

		final Registro registro = requestDataLoader.loadRegister(request, tipoRegistro);
		registro.setId(0);
		registro.setDataRegistro(new Date());
		registro.setRegistrante(currentUser);
		return registro;
	}

	private String getSuccessURL(final HttpServletRequest request) {
		final ServletContext ctx = request.getServletContext();
		return URLHelper.buildSuccessServiceURL(ctx, ActionMapping.REGISTER_SEARCH, "Registro incluído com sucesso");
	}

	private void redirectForBadRegisterType(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {

		final ServletContext ctx = request.getServletContext();
		final String url = URLHelper.buildErrorServiceURL(ctx, ActionMapping.REGISTER_INSERT_GATEWAY,
				"O tipo de registro não foi definido. Tente reiniciar a inclusão.");

		logger.info("Tipo de registro inválido ou não definido. Voltando à escolha => " + url);

		response.sendRedirect(url);
	}
}
