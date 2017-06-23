package br.eti.hmagalhaes.bomclima.model.dao.jdbc;

import static br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.JDBCUtil.isNullColumn;
import static br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.JDBCUtil.toSQLDate;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.eti.hmagalhaes.bomclima.model.dao.RegistroDAO;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.JDBCConnectionManager;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.JDBCUtil;
import br.eti.hmagalhaes.bomclima.model.dao.jdbc.util.Query;
import br.eti.hmagalhaes.bomclima.model.dto.RegistroSearchParams;
import br.eti.hmagalhaes.bomclima.model.entity.Cidade;
import br.eti.hmagalhaes.bomclima.model.entity.IntensidadeEvento;
import br.eti.hmagalhaes.bomclima.model.entity.IntensidadeMensuravel;
import br.eti.hmagalhaes.bomclima.model.entity.MedicaoPrevista;
import br.eti.hmagalhaes.bomclima.model.entity.MedicaoRealizada;
import br.eti.hmagalhaes.bomclima.model.entity.Registro;
import br.eti.hmagalhaes.bomclima.model.entity.TemperaturaMensuravel;
import br.eti.hmagalhaes.bomclima.model.entity.TipoRegistro;
import br.eti.hmagalhaes.bomclima.model.entity.UF;
import br.eti.hmagalhaes.bomclima.model.entity.Usuario;
import br.eti.hmagalhaes.bomclima.util.builder.RegistroBuilder;

public class RegistroJDBCDAO extends GenericJDBCDAO<Registro, Long> implements RegistroDAO {

	private static final String INSERT_SQL = "insert into registro (data_registro, registrante_id, cidade, uf"
			+ ", tipo_registro_id, data_prevista, data_realizacao, probabilidade_percentual_previsao"
			+ ", intensidade_evento, temperatura_em_celcius) values (?,?,?,?,?,?,?,?,?,?)";

	private static final String UPDATE_SQL = "update registro set cidade = ?, uf = ?, data_prevista = ?"
			+ ", data_realizacao = ?, probabilidade_percentual_previsao = ?, intensidade_evento = ?"
			+ ", temperatura_em_celcius = ?, ultimo_editor_id = ?, data_ultima_edicao = ? where id = ?";
	private static final String BASE_SELECT = "select registro.id, registro.cidade, registro.uf, registro.tipo_registro_id"
			+ ", registro.data_registro, registro.data_ultima_edicao, registro.data_realizacao, registro.data_prevista"
			+ ", registro.probabilidade_percentual_previsao, registro.intensidade_evento, registro.temperatura_em_celcius"
			+ ", registrante.id regId, registrante.login regLogin, registrante.nome regNome"
			+ ", ultimoEditor.id editId, ultimoEditor.login editLogin, ultimoEditor.nome editNome"
			+ ", tipo.id tipoId, tipo.descricao tipoDescricao"
			+ " from registro inner join usuario as registrante on registro.registrante_id = registrante.id"
			+ " inner join tipo_registro tipo on registro.tipo_registro_id = tipo.id"
			+ " left join usuario as ultimoEditor on registro.ultimo_editor_id = ultimoEditor.id";
	private static final String SELECT_BY_ID = BASE_SELECT + " where registro.id=?";
	private static final String SELECT_SEARCH = BASE_SELECT + " where (? is null or registro.data_registro >= ?)"
			+ " and (? is null or registro.data_registro <= ?) and (? is null or lower(registro.cidade) like lower(?))"
			+ " and (? is null or lower(registro.uf) = lower(?))";

	@Inject
	public RegistroJDBCDAO(final JDBCConnectionManager connManager) {
		super(connManager);
	}

	@Override
	protected Query prepareInsertQuery(final Registro registro) {
		final Date dataRegistro = JDBCUtil.toSQLDate(registro.getDataRegistro());
		final String intensidadeEvento = getIntensidadeEvento(registro);
		final Float temperaturaEmCelcius = getTemperatura(registro);
		final Cidade cidade = registro.getCidade();
		final UF uf = cidade.getUf();
		Float probabilidadePercentual = null;
		Date dataPrevista = null;
		Date dataRealizacao = null;

		if (registro instanceof MedicaoPrevista) {
			final MedicaoPrevista medicaoPrevista = (MedicaoPrevista) registro;
			dataPrevista = toSQLDate(medicaoPrevista.getDataPrevista());
			probabilidadePercentual = medicaoPrevista.getProbabilidadePercentual();
		} else {
			final MedicaoRealizada medicao = (MedicaoRealizada) registro;
			dataRealizacao = toSQLDate(medicao.getDataRealizacao());
		}

		final Object[] args = { dataRegistro, registro.getRegistrante().getId(), cidade.getCidade(), uf.getUf(),
				registro.getTipoRegistro().getId(), dataPrevista, dataRealizacao, probabilidadePercentual,
				intensidadeEvento, temperaturaEmCelcius };

		return new Query(INSERT_SQL, args);
	}

	@Override
	protected Query prepareUpdateQuery(final Registro registro) {
		final String intensidadeEvento = getIntensidadeEvento(registro);
		final Float temperaturaEmCelcius = getTemperatura(registro);
		final Integer editorId = getUltimoEditorId(registro);
		final Date dataUltimaEdicao = toSQLDate(registro.getDataUltimaEdicao());
		final Cidade cidade = registro.getCidade();
		final UF uf = cidade.getUf();
		Float probabilidadePercentual = null;
		Date dataPrevista = null;
		Date dataRealizacao = null;

		if (registro instanceof MedicaoPrevista) {
			final MedicaoPrevista medicao = (MedicaoPrevista) registro;
			dataPrevista = toSQLDate(medicao.getDataPrevista());
			probabilidadePercentual = medicao.getProbabilidadePercentual();
		} else {
			final MedicaoRealizada medicao = (MedicaoRealizada) registro;
			dataRealizacao = toSQLDate(medicao.getDataRealizacao());
		}

		final Object[] args = { cidade.getCidade(), uf.getUf(), dataPrevista, dataRealizacao, probabilidadePercentual,
				intensidadeEvento, temperaturaEmCelcius, editorId, dataUltimaEdicao, registro.getId() };

		return new Query(UPDATE_SQL, args);
	}

	private Integer getUltimoEditorId(final Registro registro) {
		final Usuario usuario = registro.getUltimoEditor();
		return usuario == null ? null : usuario.getId();
	}

	private Float getTemperatura(final Registro registro) {
		if (!(registro instanceof TemperaturaMensuravel)) {
			return null;
		}
		return ((TemperaturaMensuravel) registro).getTemperaturaEmCelcius();
	}

	private String getIntensidadeEvento(final Registro registro) {
		if (!(registro instanceof IntensidadeMensuravel)) {
			return null;
		}
		final IntensidadeEvento intensidade = ((IntensidadeMensuravel) registro).getIntensidade();
		return intensidade == null ? null : intensidade.name();
	}

	@Override
	protected Query prepareDeleteByIdQuery(final Long id) {
		return new Query("delete from registro where id = ?", id);
	}

	@Override
	protected Query prepareGetByIdQuery(final Long id) {
		return new Query(SELECT_BY_ID, id);
	}

	@Override
	protected Query prepareFindAllQuery() {
		return new Query(BASE_SELECT);
	}

	@Override
	protected Registro mapEntity(final ResultSet rs) throws SQLException {
		final TipoRegistro tipoRegistro = mapTipoRegistro(rs);
		final Usuario ultimoEditor = mapUsuario(rs, "editId", "editLogin", "editNome");
		final Usuario registrante = mapUsuario(rs, "regId", "regLogin", "regNome");
		final Cidade cidade = mapCidade(rs);

		final Registro registro = RegistroBuilder.buildByTipo(tipoRegistro);
		registro.setId(rs.getLong("id"));
		registro.setCidade(cidade);
		registro.setDataRegistro(rs.getDate("data_registro"));
		registro.setRegistrante(registrante);
		registro.setDataUltimaEdicao(rs.getDate("data_ultima_edicao"));
		registro.setUltimoEditor(ultimoEditor);
		registro.setTipoRegistro(tipoRegistro);

		if (registro instanceof MedicaoPrevista) {
			final MedicaoPrevista previsao = (MedicaoPrevista) registro;
			previsao.setDataPrevista(rs.getDate("data_prevista"));
			previsao.setProbabilidadePercentual(rs.getFloat("probabilidade_percentual_previsao"));
		} else {
			final MedicaoRealizada realizacao = (MedicaoRealizada) registro;
			realizacao.setDataRealizacao(rs.getDate("data_realizacao"));
		}

		if (registro instanceof IntensidadeMensuravel) {
			final String intensidadeCode = rs.getString("intensidade_evento");
			final IntensidadeEvento intensidade = IntensidadeEvento.valueOf(intensidadeCode);
			final IntensidadeMensuravel mensuravel = (IntensidadeMensuravel) registro;
			mensuravel.setIntensidade(intensidade);
		}

		if (registro instanceof TemperaturaMensuravel) {
			final TemperaturaMensuravel mensuravel = (TemperaturaMensuravel) registro;
			mensuravel.setTemperaturaEmCelcius(rs.getFloat("temperatura_em_celcius"));
		}

		return registro;
	}

	private TipoRegistro mapTipoRegistro(final ResultSet rs) throws SQLException {
		final byte tipoId = rs.getByte("tipo_registro_id");
		return TipoRegistro.byId(tipoId);
	}

	private Cidade mapCidade(final ResultSet rs) throws SQLException {
		final UF uf = new UF();
		uf.setUf(rs.getString("uf"));

		final Cidade cidade = new Cidade();
		cidade.setCidade(rs.getString("cidade"));
		cidade.setUf(uf);
		return cidade;
	}

	private Usuario mapUsuario(final ResultSet rs, final String idColumn, final String loginColumn,
			final String nomeColumn) throws SQLException {

		if (isNullColumn(rs, idColumn)) {
			return null;
		}

		final Usuario usuario = new Usuario();
		usuario.setId(rs.getInt(idColumn));
		usuario.setLogin(rs.getString(loginColumn));
		usuario.setNome(rs.getString(nomeColumn));
		return usuario;
	}

	@Override
	public List<Registro> find(final RegistroSearchParams params) {
		final String cidade = trimToNull(params.getCidade());
		final String uf = trimToNull(params.getUf());
		final Date data1 = params.getDataRegistro1();
		final Date data2 = params.getDataRegistro2();

		final Object[] args = { data1, data1, data2, data2, cidade, cidade, uf, uf };
		final Query query = new Query(SELECT_SEARCH, args);

		try (final Connection conn = getConnection();
				final PreparedStatement statement = prepareStatement(conn, query)) {
			return loadListResult(statement);
		} catch (SQLException ex) {
			throw new RuntimeException("Erro na procura de registros", ex);
		}
	}
}
