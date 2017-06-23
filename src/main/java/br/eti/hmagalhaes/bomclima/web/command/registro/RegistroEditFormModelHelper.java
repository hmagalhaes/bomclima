package br.eti.hmagalhaes.bomclima.web.command.registro;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.eti.hmagalhaes.bomclima.model.entity.IntensidadeEvento;
import br.eti.hmagalhaes.bomclima.model.entity.Registro;
import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;

@ApplicationScoped
class RegistroEditFormModelHelper {

	final ConfigFacade configFacade;

	@Inject
	public RegistroEditFormModelHelper(final ConfigFacade configFacade) {
		this.configFacade = configFacade;
	}

	public void provideOkModel(final HttpServletRequest request, final Registro registro) {
		final String datePattern = configFacade.getString(ConfigKey.VIEW_DATE_PATTERN);
		final String floatPattern = configFacade.getString(ConfigKey.VIEW_FLOAT_PATTERN);

		request.setAttribute("registro", registro);
		request.setAttribute("floatPattern", floatPattern);
		request.setAttribute("datePattern", datePattern);
		request.setAttribute("datePatternHint", datePattern.replace("yyyy", "AAAA").toUpperCase());
		request.setAttribute("intensidadeList", IntensidadeEvento.values());
	}

	public void provideErrorModel(final HttpServletRequest request, final Registro registro, final String errorMsg) {
		provideOkModel(request, registro);

		request.setAttribute("error", errorMsg);
	}
}
