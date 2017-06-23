package br.eti.hmagalhaes.bomclima.web.util.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;

@ApplicationScoped
public class AuthorizationManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final AuthenticationManager authenticationManager;

	@Inject
	public AuthorizationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public boolean isAuthorized(final ActionMapping actionMapping, final HttpServletRequest request) {
		if (!actionMapping.isPrivateService()) {
			logger.debug("Serviço público. Ignorando autenticação => " + request.getPathInfo());
			return true;
		}

		logger.debug("Serviço privado. Verificando autenticação => " + request.getPathInfo());

		final HttpSession session = request.getSession(false);
		if (session == null) {
			logger.info("Sessão de usuário inexistente. Acesso negado");
			return false;
		}

		if (!authenticationManager.hasValidSession(request)) {
			logger.info("Sessão de usuário inválida. Acesso negado");
			return false;
		}

		logger.info("Sessão de usuário Ok.");
		return true;
	}
}
