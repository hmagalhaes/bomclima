package br.eti.hmagalhaes.bomclima.web.command.login;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;
import br.eti.hmagalhaes.bomclima.web.util.security.AuthenticationManager;

@ApplicationScoped
public class LogoutCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final AuthenticationManager authManager;
	
	@Inject
	public LogoutCommand(AuthenticationManager authManager) {
		this.authManager = authManager;
	}

	@Override
	public void run(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Iniciando logout");
		authManager.closeSession(request);

		final ServletContext ctx = request.getServletContext();
		final String url = URLHelper.buildServiceURL(ctx, ActionMapping.LOGIN_FORM);
		logger.info("Logout completo, direcionando à tela de login => " + url);
		response.sendRedirect(url);
	}
}
