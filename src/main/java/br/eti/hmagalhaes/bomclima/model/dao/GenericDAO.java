package br.eti.hmagalhaes.bomclima.model.dao;

import java.util.List;

import br.eti.hmagalhaes.bomclima.model.dao.exception.NoRecordAffectedException;
import br.eti.hmagalhaes.bomclima.model.dao.exception.NonUniqueResultException;

public interface GenericDAO<T, K> {

	void insert(T entity);

	/**
	 * @throws NonUniqueResultException Quando mais de um registro for afetado
	 *             pelo update.
	 * @throws NoRecordAffectedException Quando nenhum registro é afetado pelo
	 *             update.
	 */
	void update(T entity) throws NonUniqueResultException, NoRecordAffectedException;

	/**
	 * @throws NonUniqueResultException Quando mais de um registro for afetado
	 *             pelo delete.
	 */
	void removeById(K id) throws NonUniqueResultException;

	/**
	 * @throws NonUniqueResultException Quando mais de um registro for
	 *             encontrado com o mesmo ID.
	 */
	T findById(K id) throws NonUniqueResultException;

	List<T> findAll();
}
