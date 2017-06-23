package br.eti.hmagalhaes.bomclima.web.command.registro;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.eti.hmagalhaes.bomclima.model.entity.Cidade;
import br.eti.hmagalhaes.bomclima.model.entity.IntensidadeEvento;
import br.eti.hmagalhaes.bomclima.model.entity.IntensidadeMensuravel;
import br.eti.hmagalhaes.bomclima.model.entity.MedicaoPrevista;
import br.eti.hmagalhaes.bomclima.model.entity.MedicaoRealizada;
import br.eti.hmagalhaes.bomclima.model.entity.Registro;
import br.eti.hmagalhaes.bomclima.model.entity.TemperaturaMensuravel;
import br.eti.hmagalhaes.bomclima.model.entity.TipoRegistro;
import br.eti.hmagalhaes.bomclima.model.entity.UF;
import br.eti.hmagalhaes.bomclima.util.ConfigFacade;
import br.eti.hmagalhaes.bomclima.util.ConfigKey;
import br.eti.hmagalhaes.bomclima.util.builder.RegistroBuilder;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;

@ApplicationScoped
class RegistroRequestDataLoader {

	private final ConfigFacade configFacade;

	@Inject
	public RegistroRequestDataLoader(final ConfigFacade configFacade) {
		this.configFacade = configFacade;
	}

	public Registro loadRegister(final HttpServletRequest request, final TipoRegistro tipo) {
		final String datePattern = configFacade.getString(ConfigKey.VIEW_DATE_PATTERN);
		final String floatPattern = configFacade.getString(ConfigKey.VIEW_FLOAT_PATTERN);

		final Cidade cidade = loadCidade(request);
		final long id = getId(request);

		final Registro registro = RegistroBuilder.buildByTipo(tipo);
		registro.setId(id);
		registro.setCidade(cidade);
		registro.setTipoRegistro(tipo);

		if (registro instanceof TemperaturaMensuravel) {
			final Float temperatura = RequestUtils.getFloatParamIgnoringErrors(request, "temperaturaEmCelcius",
					floatPattern);
			final float temperaturaValue = temperatura == null ? 0 : temperatura;

			((TemperaturaMensuravel) registro).setTemperaturaEmCelcius(temperaturaValue);
		}

		if (registro instanceof IntensidadeMensuravel) {
			final String value = request.getParameter("intensidade");
			final IntensidadeEvento intensidade = IntensidadeEvento.fromName(value);

			((IntensidadeMensuravel) registro).setIntensidade(intensidade);
		}

		if (registro instanceof MedicaoPrevista) {
			final Date dataPrevista = RequestUtils.getDateParamIgnoringErrors(request, "dataPrevista", datePattern);
			final Float probabilidade = RequestUtils.getFloatParamIgnoringErrors(request, "probabilidadePercentual",
					floatPattern);
			final float probabilidadeValue = probabilidade == null ? -1 : probabilidade;

			final MedicaoPrevista medicao = (MedicaoPrevista) registro;
			medicao.setDataPrevista(dataPrevista);
			medicao.setProbabilidadePercentual(probabilidadeValue);
		}

		if (registro instanceof MedicaoRealizada) {
			final Date dataRealizacao = RequestUtils.getDateParamIgnoringErrors(request, "dataRealizacao", datePattern);

			((MedicaoRealizada) registro).setDataRealizacao(dataRealizacao);
		}

		return registro;
	}

	public TipoRegistro getTipoRegistro(final HttpServletRequest request) {
		final String value = request.getParameter("tipoId");
		if (isBlank(value)) {
			return null;
		}
		final byte id = Byte.parseByte(value);
		return TipoRegistro.byId(id);
	}

	public long getId(final HttpServletRequest request) {
		final String value = request.getParameter("id");
		if (isBlank(value) || !isNumeric(value)) {
			return -1;
		}
		return Long.parseLong(value);
	}

	private Cidade loadCidade(final HttpServletRequest request) {
		final UF uf = new UF();
		uf.setUf(request.getParameter("uf"));

		final Cidade cidade = new Cidade();
		cidade.setCidade(request.getParameter("cidade"));
		cidade.setUf(uf);
		return cidade;
	}
}
