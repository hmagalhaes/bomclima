package br.eti.hmagalhaes.bomclima.util;

import static org.apache.commons.lang3.StringUtils.prependIfMissing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class IOUtil {

	public static Properties propertiesFromResource(final String resource) {
		final String fixedResource = prependIfMissing(resource, "/");
		try (InputStream is = IOUtil.class.getResourceAsStream(fixedResource)) {
			assertResourceFound(fixedResource, is);

			final Properties props = new Properties();
			props.load(is);
			return props;
		} catch (IOException ex) {
			throw new RuntimeException("Falha ao tentar ler recurso => " + fixedResource, ex);
		}
	}

	public static String getStringFromResource(final String resource) {
		final String fixedResource = prependIfMissing(resource, "/");
		try (InputStream is = IOUtil.class.getResourceAsStream(fixedResource)) {
			assertResourceFound(fixedResource, is);

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

				final StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line).append('\n');
				}
				return sb.toString();
			}
		} catch (IOException ex) {
			throw new RuntimeException("Falha ao tentar ler recurso => " + fixedResource, ex);
		}
	}

	private static void assertResourceFound(final String fixedResource, final InputStream is) {
		if (is == null) {
			throw new IllegalArgumentException("Recurso não encontrado => " + fixedResource);
		}
	}
}
