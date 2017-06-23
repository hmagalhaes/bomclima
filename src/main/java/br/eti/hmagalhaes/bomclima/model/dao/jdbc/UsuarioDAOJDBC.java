package br.eti.hmagalhaes.bomclima.model.dao.jdbc;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.eti.hmagalhaes.bomclima.model.dao.UsuarioDAO;
import br.eti.hmagalhaes.bomclima.model.dao.exception.NonUniqueResultException;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.JDBCConnectionManager;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.Query;
import br.eti.hmagalhaes.bomclima.model.dto.UsuarioSearchParams;
import br.eti.hmagalhaes.bomclima.model.entity.Usuario;

public class UsuarioDAOJDBC extends GenericJDBCDAO<Usuario, Integer> implements UsuarioDAO {

	private static final String BASE_SELECT = "select id, login, senha, nome, ativo from usuario";
	private static final String SELECT_BY_LOGIN = BASE_SELECT + " where lower(login) = lower(?)";
	private static final String SELECT_BY_ID = BASE_SELECT + " where id = ?";
	private static final String SELECT_FOR_SEARCH = BASE_SELECT
			+ " where (? is null or ? = id) and (? is null or lower(?) = lower(login))"
			+ " and (? is null or lower(nome) like lower(?)) and (? is null or ? = ativo)";

	@Inject
	public UsuarioDAOJDBC(final JDBCConnectionManager connManager) {
		super(connManager);
	}

	@Override
	protected Query prepareInsertQuery(final Usuario usuario) {
		final Object[] args = { usuario.getLogin(), usuario.getSenha(), usuario.getNome(), true };
		return new Query("insert into usuario (login, senha, nome, ativo) values (?, ?, ?, ?)", args);
	}

	@Override
	protected Query prepareUpdateQuery(final Usuario usuario) {
		final Object[] args = { usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.isAtivo(),
				usuario.getId() };
		return new Query("update usuario set login = ?, senha = ?, nome = ?, ativo = ? where id = ?", args);
	}

	@Override
	protected Query prepareDeleteByIdQuery(final Integer id) {
		throw new UnsupportedOperationException("Usuários só podem ser desativados");
	}

	@Override
	protected Query prepareGetByIdQuery(final Integer id) {
		return new Query(SELECT_BY_ID, id);
	}

	@Override
	protected Query prepareFindAllQuery() {
		return new Query(BASE_SELECT);
	}

	@Override
	protected Usuario mapEntity(final ResultSet rs) throws SQLException {
		final Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("id"));
		usuario.setLogin(rs.getString("login"));
		usuario.setSenha(rs.getString("senha"));
		usuario.setNome(rs.getString("nome"));
		usuario.setAtivo(rs.getBoolean("ativo"));
		return usuario;
	}

	@Override
	public Usuario findByLogin(final String login) throws NonUniqueResultException {
		final Query query = new Query(SELECT_BY_LOGIN, login);
		try (final Connection conn = getConnection();
				final PreparedStatement statement = prepareStatement(conn, query)) {

			return getUniqueEntity(statement);
		} catch (SQLException ex) {
			throw new RuntimeException("Falha na consulta de usuário", ex);
		}
	}

	@Override
	public List<Usuario> find(final UsuarioSearchParams params) {
		final String login = trimToNull(params.getLogin());
		final String preparedNome = prepareNome(params);

		final Object[] args = { params.getId(), params.getId(), login, login, preparedNome, preparedNome,
				params.getAtivo(), params.getAtivo() };

		final Query query = new Query(SELECT_FOR_SEARCH, args);
		try (final Connection conn = getConnection();
				final PreparedStatement statement = prepareStatement(conn, query)) {

			return loadListResult(statement);
		} catch (SQLException ex) {
			throw new RuntimeException("Falha na consulta de usuário", ex);
		}
	}

	private String prepareNome(final UsuarioSearchParams params) {
		final String nome = params.getNome();
		return isBlank(nome) ? null : "%" + nome + "%";
	}
}
