package br.eti.hmagalhaes.bomclima.model.dao;

import java.util.List;

import br.eti.hmagalhaes.bomclima.model.dao.exception.NonUniqueResultException;
import br.eti.hmagalhaes.bomclima.model.dto.UsuarioSearchParams;
import br.eti.hmagalhaes.bomclima.model.entity.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario, Integer> {

	/**
	 * @throws NonUniqueResultException Quando mais de um registro for
	 *             encontrado com o mesmo login.
	 */
	Usuario findByLogin(String login) throws NonUniqueResultException;

	List<Usuario> find(UsuarioSearchParams params);
}
