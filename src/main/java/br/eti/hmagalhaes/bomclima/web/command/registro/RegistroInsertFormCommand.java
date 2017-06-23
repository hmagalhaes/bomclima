package br.eti.hmagalhaes.bomclima.web.command.registro;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.model.entity.TipoRegistro;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;

@ApplicationScoped
public class RegistroInsertFormCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final RegistroInsertFormModelHelper modelHelper;
	private final RegistroRequestDataLoader dataLoader;

	@Inject
	public RegistroInsertFormCommand(final RegistroInsertFormModelHelper modelHelper,
			final RegistroRequestDataLoader dataLoader) {
		this.modelHelper = modelHelper;
		this.dataLoader = dataLoader;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando formulário de inclusão de registro");

		final TipoRegistro tipoRegistro = dataLoader.getTipoRegistro(request);

		if (tipoRegistro == null) {
			final String url = URLHelper.buildErrorServiceURL(request.getServletContext(),
					ActionMapping.REGISTER_INSERT_GATEWAY, "Tipo de registro inválido");
			logger.info("Tipo de registro inválido. Voltando à escolha => " + url);
			response.sendRedirect(url);
			return;
		}

		modelHelper.provideOkModel(request, tipoRegistro);

		logger.info("Chamando a view => " + ViewMapping.REGISTER_INSERT);

		RequestUtils.requestForward(request, response, ViewMapping.REGISTER_INSERT);
	}
}
