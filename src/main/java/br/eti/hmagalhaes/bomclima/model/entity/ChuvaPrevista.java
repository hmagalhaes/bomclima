package br.eti.hmagalhaes.bomclima.model.entity;

public class ChuvaPrevista extends MedicaoPrevista implements IntensidadeMensuravel {

	private IntensidadeEvento intensidade;

	public IntensidadeEvento getIntensidade() {
		return intensidade;
	}

	public void setIntensidade(IntensidadeEvento intensidade) {
		this.intensidade = intensidade;
	}
}
