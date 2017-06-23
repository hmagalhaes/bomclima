package br.eti.hmagalhaes.bomclima.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.model.dao.exception.NoRecordAffectedException;
import br.eti.hmagalhaes.bomclima.model.dao.exception.NonUniqueResultException;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.JDBCConnectionManager;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.Query;

/**
 * DAO genérico para fonte de dados JDBC.
 * 
 * @param <T> Tipo da entidade.
 * @param <K> Tipo do ID da entidade.
 */
public abstract class GenericJDBCDAO<T, K> {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final JDBCConnectionManager connManager;

	public GenericJDBCDAO(final JDBCConnectionManager connManager) {
		this.connManager = connManager;
	}

	public void insert(final T entity) {
		assertEntityNotNull(entity);

		final Query query = prepareInsertQuery(entity);
		try (final Connection conn = getConnection();
				final PreparedStatement statement = prepareStatement(conn, query)) {

			final int affected = statement.executeUpdate();
			if (affected == 0) {
				throw new RuntimeException("Insert falou. Nenhum registro afetado.");
			}
			logger.debug("Insert concluído com sucesso");
		} catch (SQLException ex) {
			throw new RuntimeException("Problema com o acesso ao banco", ex);
		}
	}

	protected abstract Query prepareInsertQuery(T entity);

	public void update(final T entity) throws NonUniqueResultException, NoRecordAffectedException {
		assertEntityNotNull(entity);

		final Query query = prepareUpdateQuery(entity);
		try (final Connection conn = getConnection();
				final PreparedStatement statement = prepareStatement(conn, query)) {

			final int affected = statement.executeUpdate();
			if (affected == 0) {
				throw new NoRecordAffectedException("Insert falou. Nenhum registro afetado.");
			}
			if (affected > 1) {
				throw new NonUniqueResultException("Insert falou. Mais de um registro registro afetado.");
			}
			logger.debug("Update concluído com sucesso");
		} catch (SQLException ex) {
			throw new RuntimeException("Problema com o acesso ao banco", ex);
		}
	}

	protected abstract Query prepareUpdateQuery(T entity);

	public void removeById(final K id) throws NonUniqueResultException {
		assertIdNotNull(id);

		final Query query = prepareDeleteByIdQuery(id);
		try (final Connection conn = getConnection();
				final PreparedStatement statement = prepareStatement(conn, query)) {

			final int affected = statement.executeUpdate();
			if (affected > 1) {
				throw new NonUniqueResultException("Delete por ID falhou. Vários registros afetados => " + affected);
			}
			logger.debug("Delete concluído com sucesso => registros afetados: " + affected);
		} catch (SQLException ex) {
			throw new RuntimeException("Problema com o acesso ao banco", ex);
		}
	}

	protected abstract Query prepareDeleteByIdQuery(K id);

	public T findById(final K id) throws NonUniqueResultException {
		assertIdNotNull(id);

		final Query query = prepareGetByIdQuery(id);
		try (final Connection conn = getConnection();
				final PreparedStatement ps = prepareStatement(conn, query);
				final ResultSet rs = ps.executeQuery()) {

			if (!rs.next()) {
				logger.debug("Select por ID não encontrou nada => ID: " + id);
				return null;
			}

			final T entity = mapEntity(rs);
			if (rs.next()) {
				throw new NonUniqueResultException(
						"Falha no select por ID. Mais de um registro encontrado => ID: " + id);
			}

			logger.debug("Select por ID OK");
			return entity;
		} catch (SQLException ex) {
			throw new RuntimeException("Problema com o acesso ao banco", ex);
		}
	}

	protected abstract Query prepareGetByIdQuery(K id);

	public List<T> findAll() {
		final Query query = prepareFindAllQuery();
		try (final Connection conn = getConnection();
				final PreparedStatement statement = prepareStatement(conn, query)) {

			return loadListResult(statement);
		} catch (SQLException ex) {
			throw new RuntimeException("Problema com o acesso ao banco", ex);
		}
	}

	protected abstract Query prepareFindAllQuery();

	protected List<T> loadListResult(final PreparedStatement ps) throws SQLException {
		try (ResultSet rs = ps.executeQuery()) {
			final List<T> entityList = new ArrayList<>();
			while (rs.next()) {
				entityList.add(mapEntity(rs));
			}
			logger.debug("Registros retornados => " + entityList.size());
			return entityList;
		}
	}

	protected PreparedStatement prepareStatement(final Connection conn, final Query query) throws SQLException {

		if (!query.hasParams()) {
			return conn.prepareStatement(query.getSql());
		}

		final PreparedStatement ps = conn.prepareStatement(query.getSql());
		final Object[] params = query.getParams();

		for (int i = 0; i < params.length; i++) {
			final Object param = params[i];
			final int paramIndex = i + 1;

			if (param == null) {
				ps.setNull(paramIndex, Types.NULL);
			} else if (param instanceof Number) {
				if (param instanceof Float || param instanceof Double) {
					ps.setDouble(paramIndex, ((Number) param).doubleValue());
				} else {
					ps.setLong(paramIndex, ((Number) param).longValue());
				}
			} else if (param instanceof String) {
				ps.setString(paramIndex, (String) param);
			} else if (param instanceof Date) {
				java.sql.Date sqlDate = new java.sql.Date(((Date) param).getTime());
				ps.setDate(paramIndex, sqlDate);
			} else if (param instanceof Boolean) {
				ps.setBoolean(paramIndex, (Boolean) param);
			} else {
				throw new IllegalArgumentException("Tipo de campo não mapeado => " + param.getClass());
			}
		}
		return ps;
	}

	protected abstract T mapEntity(ResultSet rs) throws SQLException;

	protected Connection getConnection() throws SQLException {
		return connManager.getConnection();
	}

	protected T getUniqueEntity(final PreparedStatement ps) throws SQLException {
		try (ResultSet rs = ps.executeQuery()) {
			if (!rs.next()) {
				return null;
			}
			final T entity = mapEntity(rs);
			if (rs.next()) {
				throw new NonUniqueResultException(
						"Mais de uma entidade encontrada com os dados procurados => " + entity);
			}
			return entity;
		}
	}

	private void assertEntityNotNull(final T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entidade não pode ser null");
		}
	}

	private void assertIdNotNull(final K id) {
		if (id == null) {
			throw new IllegalArgumentException("ID da entidade não pode ser null");
		}
	}
}
