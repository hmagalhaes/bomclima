package br.eti.hmagalhaes.bomclima.model.entity;

public class ChuvaRealizada extends MedicaoRealizada implements IntensidadeMensuravel {

	private IntensidadeEvento intensidade;

	public IntensidadeEvento getIntensidade() {
		return intensidade;
	}

	public void setIntensidade(IntensidadeEvento intensidade) {
		this.intensidade = intensidade;
	}
}
