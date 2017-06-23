package br.eti.hmagalhaes.bomclima.web.util;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.eti.hmagalhaes.bomclima.util.DateUtils;
import br.eti.hmagalhaes.bomclima.util.NumberUtils;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;

public class RequestUtils {

	public static void requestForward(final HttpServletRequest request, final HttpServletResponse response,
			final ViewMapping viewMapping) throws ServletException, IOException {

		final RequestDispatcher dispatcher = request.getRequestDispatcher(viewMapping.getView());
		if (dispatcher == null) {
			throw new IllegalStateException("View indisponível na aplicação => " + viewMapping.getView());
		}
		dispatcher.forward(request, response);
	}

	public static Integer getIntParam(final HttpServletRequest request, final String param) {
		final String value = request.getParameter(param);
		return isBlank(value) ? null : Integer.valueOf(value);
	}

	public static int getRequiredIntParam(final HttpServletRequest request, final String param) {
		final String value = request.getParameter(param);
		try {
			return Integer.parseInt(value);
		} catch (RuntimeException ex) {
			throw new IllegalArgumentException("Parâmetro " + param + " não é um int válido => " + value, ex);
		}
	}

	public static Boolean getBooleanParam(final HttpServletRequest request, final String param) {
		final String value = request.getParameter(param);
		return isBlank(value) ? null : Boolean.valueOf(value);
	}

	public static Date getDateParam(final HttpServletRequest request, final String param, final String datePattern) {
		final String value = request.getParameter(param);
		return isBlank(value) ? null : DateUtils.parseDate(value, datePattern);
	}

	public static Date getDateParamIgnoringErrors(final HttpServletRequest request, final String param,
			final String datePattern) {
		try {
			return getDateParam(request, param, datePattern);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	public static Float getFloatParamIgnoringErrors(final HttpServletRequest request, final String param,
			final String floatPattern) {
		final String value = request.getParameter(param);
		if (isBlank(value)) {
			return null;
		}
		try {
			return NumberUtils.parseFloat(value, floatPattern);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	public static byte getRequiredByteParam(final HttpServletRequest request, final String param) {
		final String value = request.getParameter(param);
		try {
			return Byte.parseByte(value);
		} catch (RuntimeException ex) {
			throw new IllegalArgumentException("Parâmetro " + param + " não é um byte válido => " + value, ex);
		}
	}
}
