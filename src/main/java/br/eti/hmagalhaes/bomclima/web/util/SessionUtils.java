package br.eti.hmagalhaes.bomclima.web.util;

import javax.servlet.http.HttpSession;

import br.eti.hmagalhaes.bomclima.model.entity.Usuario;

public class SessionUtils {

	private static final String SESSION_VALIDATION_HASH = "sessionValidationHash";
	private static final String CURRENT_USER = "currentUser";

	public static String getSessionValidationHash(final HttpSession session) {
		if (session == null) {
			return null;
		}
		return (String) session.getAttribute(SESSION_VALIDATION_HASH);
	}

	public static void setSessionValidationHash(final HttpSession session, final String hash) {
		session.setAttribute(SESSION_VALIDATION_HASH, hash);
	}

	public static Usuario getCurrentUser(final HttpSession session) {
		if (session == null) {
			return null;
		}
		return (Usuario) session.getAttribute("currentUser");
	}

	public static void setCurrentUser(final HttpSession session, final Usuario usuario) {
		session.setAttribute(CURRENT_USER, usuario);
	}

	public static void setViewLocale(final HttpSession session, String localeCode) {
		session.setAttribute("viewLocaleCode", localeCode);
	}
}
