package br.eti.hmagalhaes.bomclima.web.command.registro;

import static org.apache.commons.collections4.CollectionUtils.size;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.model.dao.RegistroDAO;
import br.eti.hmagalhaes.bomclima.model.dto.RegistroSearchParams;
import br.eti.hmagalhaes.bomclima.model.entity.Registro;
import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;
import br.eti.hmagalhaes.bomclima.util.DateUtils;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;

@ApplicationScoped
public class RegistroSearchCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final RegistroDAO registroDAO;
	private final String datePattern;

	@Inject
	public RegistroSearchCommand(final RegistroDAO registroDAO, final ConfigFacade configFacade) {
		this.registroDAO = registroDAO;
		this.datePattern = configFacade.getString(ConfigKey.VIEW_DATE_PATTERN);
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando listagem de registros");

		final String errorMsg = validate(request);
		if (errorMsg != null) {
			logger.info("Parâmetros inválidos => " + errorMsg);

			request.setAttribute("error", errorMsg);
			request.setAttribute("datePattern", datePattern);

			RequestUtils.requestForward(request, response, ViewMapping.REGISTER_SEARCH);

			return;
		}

		final RegistroSearchParams params = buildParams(request);
		final List<Registro> userList = registroDAO.find(params);

		logger.info("Registros carregados, chamando a view. => registros: " + size(userList) + ", view: "
				+ ViewMapping.REGISTER_SEARCH);

		request.setAttribute("datePattern", datePattern);
		request.setAttribute("registroList", userList);
		request.setAttribute("searchParams", params);

		RequestUtils.requestForward(request, response, ViewMapping.REGISTER_SEARCH);
	}

	private boolean isBadDate(final HttpServletRequest request, final String param) {
		final String date = request.getParameter(param);
		if (isBlank(date)) {
			return false;
		}
		return DateUtils.isBadDate(date, datePattern);
	}

	private String validate(final HttpServletRequest request) {
		if (isBadDate(request, "dataRegistro1") || isBadDate(request, "dataRegistro2")) {
			final String pattern = datePattern.replace("yyyy", "AAAA").toUpperCase();
			return "Datas inválidas. Utilize o formato " + pattern;
		}
		return null;
	}

	private RegistroSearchParams buildParams(final HttpServletRequest request) {
		final Date dataRegistro1 = RequestUtils.getDateParam(request, "dataRegistro1", datePattern);
		final Date dataRegistro2 = RequestUtils.getDateParam(request, "dataRegistro2", datePattern);
		final String cidade = request.getParameter("cidade");
		final String uf = request.getParameter("uf");

		final RegistroSearchParams params = new RegistroSearchParams(dataRegistro1, dataRegistro2, cidade, uf);
		logger.debug("Parâmetros => " + params);
		return params;
	}
}
