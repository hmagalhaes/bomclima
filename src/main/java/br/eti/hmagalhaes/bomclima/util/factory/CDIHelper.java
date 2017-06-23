package br.eti.hmagalhaes.bomclima.util.factory;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

/**
 * Utilitário CDI.
 */
@ApplicationScoped
public class CDIHelper {

	/**
	 * Retorna um beam que satisfaça a interface informada.
	 * 
	 * @throws IllegalArgumentException caso nenhum ou mais de um bean seja
	 *             encontrado.
	 */
	public <T> T getBean(final Class<T> beanType) {
		final BeanManager beanManager = CDI.current().getBeanManager();

		final Set<Bean<?>> beanSet = beanManager.getBeans(beanType);
		if (beanSet.isEmpty()) {
			throw new IllegalArgumentException("Nenhum bean encontrado para este tipo => " + beanType);
		}
		if (beanSet.size() > 1) {
			throw new IllegalArgumentException("Mais de um bean encontrado com este tipo => " + beanType);
		}

		final Bean<?> bean = beanSet.iterator().next();
		final CreationalContext<?> ctx = beanManager.createCreationalContext(bean);

		@SuppressWarnings("unchecked")
		final T ref = (T) beanManager.getReference(bean, beanType, ctx);
		ctx.release();
		return ref;
	}
}
