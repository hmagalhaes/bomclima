package br.eti.hmagalhaes.bomclima.util.builder;

import br.eti.hmagalhaes.bomclima.model.entity.ChuvaPrevista;
import br.eti.hmagalhaes.bomclima.model.entity.ChuvaRealizada;
import br.eti.hmagalhaes.bomclima.model.entity.Registro;
import br.eti.hmagalhaes.bomclima.model.entity.TemperaturaPrevista;
import br.eti.hmagalhaes.bomclima.model.entity.TemperaturaRealizada;
import br.eti.hmagalhaes.bomclima.model.entity.TipoRegistro;

public class RegistroBuilder {

	public static Registro buildByTipo(final TipoRegistro tipo) {
		switch (tipo) {
			case CHUVA_PREVISTA:
				return new ChuvaPrevista();
			case CHUVA_REALIZADA:
				return new ChuvaRealizada();
			case TEMPERATURA_PREVISTA:
				return new TemperaturaPrevista();
			case TEMPERATURA_REALIZADA:
				return new TemperaturaRealizada();
			default:
				throw new IllegalArgumentException("Tipo de registro inválido => " + tipo);
		}
	}
}
