package br.eti.hmagalhaes.bomclima.web.util.validator;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

import br.eti.hmagalhaes.bomclima.model.entity.Cidade;
import br.eti.hmagalhaes.bomclima.model.entity.IntensidadeMensuravel;
import br.eti.hmagalhaes.bomclima.model.entity.MedicaoPrevista;
import br.eti.hmagalhaes.bomclima.model.entity.MedicaoRealizada;
import br.eti.hmagalhaes.bomclima.model.entity.Registro;
import br.eti.hmagalhaes.bomclima.model.entity.UF;
import br.eti.hmagalhaes.bomclima.model.entity.Usuario;
import br.eti.hmagalhaes.bomclima.util.DateUtils;

@ApplicationScoped
public class RegistroValidator {

	private static final String NO_ERROR = null;

	/**
	 * @return Mensagem de erro, ou {@code null} se não houver erro.
	 */
	public String validate(final Registro registro) {
		if (registro == null) {
			return "Os dados do registro não foram informados";
		}

		if (registro.getId() < 0) {
			return "O ID do registro é inválido";
		}

		if (registro.getDataRegistro() == null) {
			return "Data do registro não foi informada";
		}

		final Usuario registrante = registro.getRegistrante();
		if (registrante == null || registrante.getId() <= 0) {
			return "O registrante não foi definido";
		}

		final Cidade cidade = registro.getCidade();
		if (cidade == null || isBlank(cidade.getCidade())) {
			return "A cidade de registro não pode ficar em branco";
		}

		final UF uf = cidade.getUf();
		if (uf == null || isBlank(uf.getUf())) {
			return "A UF de registro não pode ficar em branco";
		}

		if (uf.getUf().length() != 2) {
			return "A UF parece inválida";
		}

		if (registro.getTipoRegistro() == null) {
			return "Tipo de registro inválido";
		}

		final String intensityError = validateIntensity(registro);
		if (intensityError != null) {
			return intensityError;
		}

		final String effectiveError = validateEffectiveMeasure(registro);
		if (effectiveError != null) {
			return effectiveError;
		}

		final String predictionError = validatePrediction(registro);
		if (predictionError != null) {
			return predictionError;
		}

		final String temperatureError = validateTemperature(registro);
		if (temperatureError != null) {
			return temperatureError;
		}

		return NO_ERROR;
	}

	private String validateTemperature(final Registro registro) {
		// desnecessário validar
		return NO_ERROR;
	}

	private String validatePrediction(Registro registro) {
		if (!(registro instanceof MedicaoPrevista)) {
			return NO_ERROR;
		}

		final MedicaoPrevista medicao = (MedicaoPrevista) registro;

		if (medicao.getDataPrevista() == null) {
			return "A data prevista para o evento deve ser informada";
		}

		final float probabilidade = medicao.getProbabilidadePercentual();
		if (probabilidade < 0 || probabilidade > 100) {
			return "Probabilidade inválida. Ela deve variar entre 0 e 100%";
		}

		return NO_ERROR;
	}

	private String validateEffectiveMeasure(final Registro registro) {
		if (!(registro instanceof MedicaoRealizada)) {
			return NO_ERROR;
		}

		final MedicaoRealizada medicao = (MedicaoRealizada) registro;

		final Date dataRealizacao = medicao.getDataRealizacao();
		if (dataRealizacao == null) {
			return "A data de realização da medição deve ser informada";
		}

		final Date tomorrow = DateUtils.tomorrowFirstSecond();
		if (!dataRealizacao.before(tomorrow)) {
			return "A data de realização não pode ser futura";
		}

		return NO_ERROR;
	}

	private String validateIntensity(Registro registro) {
		if (!(registro instanceof IntensidadeMensuravel)) {
			return NO_ERROR;
		}

		final IntensidadeMensuravel mensuravel = (IntensidadeMensuravel) registro;
		if (mensuravel.getIntensidade() == null) {
			return "A intensidade do evento deve ser informada";
		}

		return NO_ERROR;
	}
}
