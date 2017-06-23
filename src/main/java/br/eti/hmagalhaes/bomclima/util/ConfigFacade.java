package br.eti.hmagalhaes.bomclima.util;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;

/**
 * Fachada para o acesso a configurações da aplicação.
 */
@ApplicationScoped
public class ConfigFacade {

	private Map<String, String> embeddedConfig;

	private void assertNotBlankConfig(final ConfigKey key, final String prop) {
		if (StringUtils.isBlank(prop)) {
			throw new IllegalArgumentException("Config não foi definida => " + key);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> getEmbeddedConfigSource() {
		if (embeddedConfig != null) {
			return embeddedConfig;
		}
		synchronized (this) {
			final Properties props = IOUtil.propertiesFromResource("application.properties");
			embeddedConfig = Collections.unmodifiableMap((Map) props);
			return embeddedConfig;
		}
	}

	public String getNullableString(final ConfigKey configKey) {
		final String prop = System.getProperty(configKey.getKey());
		if (StringUtils.isNotBlank(prop)) {
			return prop;
		}
		return getEmbeddedConfigSource().get(configKey.getKey());
	}

	public String getString(final ConfigKey key) {
		final String config = getNullableString(key);
		assertNotBlankConfig(key, config);
		return config;
	}

	public boolean getBoolean(final ConfigKey key) {
		final String config = getNullableString(key);
		assertNotBlankConfig(key, config);
		return Boolean.parseBoolean(config);
	}

	public int getInt(final ConfigKey key) {
		final String config = getNullableString(key);
		assertNotBlankConfig(key, config);
		return Integer.parseInt(config);
	}
}
