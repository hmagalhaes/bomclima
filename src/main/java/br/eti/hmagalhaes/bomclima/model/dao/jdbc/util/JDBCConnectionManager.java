package br.eti.hmagalhaes.bomclima.model.dao.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;

@ApplicationScoped
public class JDBCConnectionManager {

	private final String jdbcString;

	@Inject
	public JDBCConnectionManager(final ConfigFacade configFacade) {
		this.jdbcString = configFacade.getNullableString(ConfigKey.JDBC_STRING);

		final String driverClass = configFacade.getString(ConfigKey.JDBC_DRIVER_CLASS);
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("JDBC Driver não encontrado => " + driverClass);
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcString);
	}
}
