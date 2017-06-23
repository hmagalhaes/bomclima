package br.eti.hmagalhaes.bomclima.web.util;

import static org.apache.commons.lang3.StringUtils.prependIfMissing;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;

import br.eti.hmagalhaes.bomclima.web.util.constant.ActionMapping;

public class URLHelper {

	public static String buildURL(final ServletContext context, final String path) {
		return context.getContextPath() + prependIfMissing(path, "/");
	}

	public static String buildServiceURL(final ServletContext context, final ActionMapping action) {
		return context.getContextPath() + "/services?op=" + action.getOperation();
	}

	public static String buildSuccessServiceURL(final ServletContext context, final ActionMapping action,
			final String successMsg) {
		return buildServiceURLWithParams(context, action, "success", successMsg);
	}
	
	public static String buildErrorServiceURL(final ServletContext context, final ActionMapping action,
			final String errorMsg) {
		return buildServiceURLWithParams(context, action, "error", errorMsg);
	}

	private static String buildServiceURLWithParams(final ServletContext context, final ActionMapping action,
			String paramName, final String paramValue) {

		final String url = buildServiceURL(context, action);
		if (StringUtils.isBlank(paramValue)) {
			return url;
		}
		return url + "&" + paramName + "=" + encodeURL(paramValue);
	}

	private static String encodeURL(final String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
