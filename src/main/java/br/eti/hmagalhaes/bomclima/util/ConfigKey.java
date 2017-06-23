package br.eti.hmagalhaes.bomclima.util;

/**
 * Mapeamento de configurações da aplicação.
 */
public enum ConfigKey {

	JDBC_STRING("BOMCLIMA_JDBC_STRING"),
	JDBC_DRIVER_CLASS("BOMCLIMA_JDBC_DRIVER_CLASS"),
	DATA_SOURCE_TYPE("BOMCLIMA_DATA_SOURCE_TYPE"),
	DATA_MIDDLEWARE_TYPE("BOMCLIMA_DATA_MIDDLEWARE_TYPE"),
	MIN_ALLOWED_PASSWORD("BOMCLIMA_MIN_ALLOWED_PASSWORD"),
	VIEW_DATE_PATTERN("BOMCLIMA_VIEW_DATE_PATTERN"),
	VIEW_FLOAT_PATTERN("BOMCLIMA_VIEW_FLOAT_PATTERN"),
	PASSWORD_HASH_ALGORITHM("BOMCLIMA_PASSWORD_HASH_ALGORITHM"),
	VIEW_LOCALE("BOMCLIMA_VIEW_LOCALE");

	private final String key;

	ConfigKey(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return key;
	}
}
