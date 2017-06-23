package br.eti.hmagalhaes.bomclima.web.controller;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.util.factory.CDIHelper;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.command.login.LoginFormCommand;
import br.eti.hmagalhaes.bomclima.web.command.login.LoginPostCommand;
import br.eti.hmagalhaes.bomclima.web.command.login.LogoutCommand;
import br.eti.hmagalhaes.bomclima.web.command.registro.RegistroDelFormCommand;
import br.eti.hmagalhaes.bomclima.web.command.registro.RegistroDelPostCommand;
import br.eti.hmagalhaes.bomclima.web.command.registro.RegistroEditFormCommand;
import br.eti.hmagalhaes.bomclima.web.command.registro.RegistroEditPostCommand;
import br.eti.hmagalhaes.bomclima.web.command.registro.RegistroInsertFormCommand;
import br.eti.hmagalhaes.bomclima.web.command.registro.RegistroInsertGatewayCommand;
import br.eti.hmagalhaes.bomclima.web.command.registro.RegistroInsertPostCommand;
import br.eti.hmagalhaes.bomclima.web.command.registro.RegistroSearchCommand;
import br.eti.hmagalhaes.bomclima.web.command.usuario.UsuarioEditFormCommand;
import br.eti.hmagalhaes.bomclima.web.command.usuario.UsuarioEditPostCommand;
import br.eti.hmagalhaes.bomclima.web.command.usuario.UsuarioInsertFormCommand;
import br.eti.hmagalhaes.bomclima.web.command.usuario.UsuarioInsertPostCommand;
import br.eti.hmagalhaes.bomclima.web.command.usuario.UsuarioPasswordFormCommand;
import br.eti.hmagalhaes.bomclima.web.command.usuario.UsuarioPasswordPostCommand;
import br.eti.hmagalhaes.bomclima.web.command.usuario.UsuarioSearchCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.URLHelper;
import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;
import br.eti.hmagalhaes.bomclima.web.util.security.AuthorizationManager;

@WebServlet("/services/*")
@SuppressWarnings("serial")
public class FrontController extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<ActionMapping, ServletCommand> commandMap;
	private AuthorizationManager authorizationManager;

	@Inject
	public void setAuthorizationManager(AuthorizationManager authorizationManager) {
		this.authorizationManager = authorizationManager;
	}

	@Inject
	public void setManagedBeanHelper(final CDIHelper beanHelper) {
		logger.info("Inicializando front controller");

		commandMap = new HashMap<ActionMapping, ServletCommand>(18);

		registerCommand(ActionMapping.LOGIN_FORM, beanHelper.getBean(LoginFormCommand.class));
		registerCommand(ActionMapping.LOGIN_POST, beanHelper.getBean(LoginPostCommand.class));
		registerCommand(ActionMapping.LOGOUT, beanHelper.getBean(LogoutCommand.class));
		registerCommand(ActionMapping.USER_SEARCH, beanHelper.getBean(UsuarioSearchCommand.class));
		registerCommand(ActionMapping.USER_INSERT_POST, beanHelper.getBean(UsuarioInsertPostCommand.class));
		registerCommand(ActionMapping.USER_INSERT_FORM, beanHelper.getBean(UsuarioInsertFormCommand.class));
		registerCommand(ActionMapping.USER_PASSWORD_FORM, beanHelper.getBean(UsuarioPasswordFormCommand.class));
		registerCommand(ActionMapping.USER_PASSWORD_POST, beanHelper.getBean(UsuarioPasswordPostCommand.class));
		registerCommand(ActionMapping.USER_EDIT_FORM, beanHelper.getBean(UsuarioEditFormCommand.class));
		registerCommand(ActionMapping.USER_EDIT_POST, beanHelper.getBean(UsuarioEditPostCommand.class));
		registerCommand(ActionMapping.REGISTER_SEARCH, beanHelper.getBean(RegistroSearchCommand.class));
		registerCommand(ActionMapping.REGISTER_INSERT_GATEWAY, beanHelper.getBean(RegistroInsertGatewayCommand.class));
		registerCommand(ActionMapping.REGISTER_INSERT_FORM, beanHelper.getBean(RegistroInsertFormCommand.class));
		registerCommand(ActionMapping.REGISTER_INSERT_POST, beanHelper.getBean(RegistroInsertPostCommand.class));
		registerCommand(ActionMapping.REGISTER_EDIT_FORM, beanHelper.getBean(RegistroEditFormCommand.class));
		registerCommand(ActionMapping.REGISTER_EDIT_POST, beanHelper.getBean(RegistroEditPostCommand.class));
		registerCommand(ActionMapping.REGISTER_DEL_FORM, beanHelper.getBean(RegistroDelFormCommand.class));
		registerCommand(ActionMapping.REGISTER_DEL_POST, beanHelper.getBean(RegistroDelPostCommand.class));

		logger.info("Front controller inicializado => comandos mapeados: " + commandMap.size());
	}

	private void registerCommand(final ActionMapping mapping, final ServletCommand command) {
		logger.debug("Registrando comando => method: {}, operation: {}, command: {}", mapping.getMethod(),
				mapping.getOperation(), command);
		commandMap.put(mapping, command);
	}

	@Override
	protected void service(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String method = request.getMethod();
		final String operation = trimToEmpty(request.getParameter("op"));
		logger.debug("Servindo request => " + method + " " + operation);

		final ActionMapping mapping = ActionMapping.fromMethodAndOperation(method, operation);
		if (mapping == null) {
			throw new ServletException("Ação não mapeada => " + method + " " + operation);
		}

		final ServletCommand command = commandMap.get(mapping);
		if (command == null) {
			throw new ServletException("Comando não encontrado para esta operação => " + method + " " + operation);
		}

		if (!authorizationManager.isAuthorized(mapping, request)) {
			logger.info("Usuário não autorizado, redirecionando para o login");
			sendLoginRedirect(response);
			return;
		}

		try {
			logger.debug("Executando comando => " + command);
			command.run(request, response);
		} catch (RuntimeException ex) {
			logger.error("Erro durante execução de comando => " + method + " " + operation, ex);
			RequestUtils.requestForward(request, response, ViewMapping.ERROR);
		}
	}

	private void sendLoginRedirect(final HttpServletResponse response) throws IOException {
		String url = URLHelper.buildServiceURL(getServletContext(), ActionMapping.LOGIN_FORM);
		response.sendRedirect(url);
	}
}
