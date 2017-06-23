package br.eti.hmagalhaes.bomclima.util.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.eti.hmagalhaes.bomclima.model.dao.RegistroDAO;
import br.eti.hmagalhaes.bomclima.model.dao.UsuarioDAO;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.RegistroJDBCDAO;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.UsuarioDAOJDBC;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.JDBCConnectionManager;

/**
 * Factory de DAOs para o acesso a fonte de dados JDBC.
 */
@ApplicationScoped
public class JDBCDAOFactory implements DAOFactory {

	private final JDBCConnectionManager connManager;

	@Inject
	public JDBCDAOFactory(final JDBCConnectionManager connManager) {
		this.connManager = connManager;
	}

	@Override
	public UsuarioDAO createUsuarioDAO() {
		return new UsuarioDAOJDBC(connManager);
	}

	@Override
	public RegistroDAO createRegistroDAO() {
		return new RegistroJDBCDAO(connManager);
	}
}
