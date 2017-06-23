package br.eti.hmagalhaes.bomclima.web.context;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.JDBCConnectionManager;
import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;
import br.eti.hmagalhaes.bomclima.util.Constants;
import br.eti.hmagalhaes.bomclima.util.IOUtil;

/**
 * Inicializa o H2BD caso seja esta a fonte de dados. É criada a estrutura do
 * banco e incluídos os dados de teste.
 */
@WebListener
public class H2DBInitServletContextListener implements ServletContextListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ConfigFacade configFacade;
	private JDBCConnectionManager jdbcConnectionManager;

	@Inject
	public void setJDBCConnectionManager(JDBCConnectionManager jdbcConnectionManager) {
		this.jdbcConnectionManager = jdbcConnectionManager;
	}

	@Inject
	public void setConfigFacade(ConfigFacade configFacade) {
		this.configFacade = configFacade;
	}

	@Override
	public void contextInitialized(final ServletContextEvent event) {
		final String dataSourceType = configFacade.getString(ConfigKey.DATA_SOURCE_TYPE);
		if (!Constants.DATA_SOURCE_TYPE_H2DB.equalsIgnoreCase(dataSourceType)) {
			logger.info("H2DB não está sendo usado. Ignorando inicialização.");
			return;
		}

		logger.info("H2DB está sendo usado. Prosseguindo com inicialização.");

		try (final Connection conn = jdbcConnectionManager.getConnection()) {
			final String sql = IOUtil.getStringFromResource("h2db-init.sql");
			conn.createStatement().execute(sql);
		} catch (SQLException ex) {
			throw new RuntimeException("Impossível iniciar H2DB", ex);
		}

		logger.info("H2DB iniciado");
	}

	@Override
	public void contextDestroyed(final ServletContextEvent event) {
	}
}
