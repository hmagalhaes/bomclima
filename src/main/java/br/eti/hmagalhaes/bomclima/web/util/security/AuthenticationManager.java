package br.eti.hmagalhaes.bomclima.web.util.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.eti.hmagalhaes.bomclima.model.entity.Usuario;
import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;
import br.eti.hmagalhaes.bomclima.web.util.SessionUtils;

@ApplicationScoped
public class AuthenticationManager {

	private static final String VALID_SESSION_HASH = "5d5d1c16-0563-48d0-b84c-903e7e256ff6";
	
	private final ConfigFacade configFacade;
	
	@Inject
	public AuthenticationManager(ConfigFacade configFacade) {
		this.configFacade=configFacade;
	}

	public void startSession(final HttpServletRequest request, final Usuario usuario) {
		final String localeCode = configFacade.getString(ConfigKey.VIEW_LOCALE);
		
		final HttpSession newSession = getNewSession(request);
		SessionUtils.setCurrentUser(newSession, usuario);
		SessionUtils.setViewLocale(newSession, localeCode);
	}

	private HttpSession getNewSession(final HttpServletRequest request) {
		final HttpSession currentSession = request.getSession(false);
		if (currentSession != null) {
			currentSession.invalidate();
		}
		return request.getSession();
	}

	public void closeSession(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.invalidate();
	}

	public boolean hasValidSession(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		final String hash = SessionUtils.getSessionValidationHash(session);
		return !VALID_SESSION_HASH.equals(hash);
	}
}
