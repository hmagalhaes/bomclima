package br.eti.hmagalhaes.bomclima.util.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.eti.hmagalhaes.bomclima.model.dao.RegistroDAO;
import br.eti.hmagalhaes.bomclima.model.dao.UsuarioDAO;
import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;
import br.eti.hmagalhaes.bomclima.util.Constants;

@ApplicationScoped
public class DAOAbstractFactory implements DAOFactory {

	private final String dataMiddlewareType;
	private final CDIHelper managedBeanHelper;

	@Inject
	public DAOAbstractFactory(final ConfigFacade configFacade, final CDIHelper managedBeanHelper) {
		this.dataMiddlewareType = configFacade.getString(ConfigKey.DATA_MIDDLEWARE_TYPE);
		this.managedBeanHelper = managedBeanHelper;
	}

	private DAOFactory getDAOFactory() {
		if (Constants.DATA_MIDDLEWARE_TYPE_JDBC.equalsIgnoreCase(dataMiddlewareType)) {
			return managedBeanHelper.getBean(JDBCDAOFactory.class);
		}
		throw new IllegalStateException("Não há suporte para o data middleware configurado => " + dataMiddlewareType);
	}

	@ApplicationScoped
	@Produces
	@Override
	public UsuarioDAO createUsuarioDAO() {
		return getDAOFactory().createUsuarioDAO();
	}

	@ApplicationScoped
	@Produces
	@Override
	public RegistroDAO createRegistroDAO() {
		return getDAOFactory().createRegistroDAO();
	}
}
