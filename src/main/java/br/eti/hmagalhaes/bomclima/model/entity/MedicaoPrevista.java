package br.eti.hmagalhaes.bomclima.model.entity;

import java.util.Date;

public abstract class MedicaoPrevista extends Registro {

	private Date dataPrevista;
	private float probabilidadePercentual;

	public Date getDataPrevista() {
		return dataPrevista;
	}

	public void setDataPrevista(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public float getProbabilidadePercentual() {
		return probabilidadePercentual;
	}

	public void setProbabilidadePercentual(float probabilidadePercentual) {
		this.probabilidadePercentual = probabilidadePercentual;
	}
}
