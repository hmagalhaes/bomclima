package br.eti.hmagalhaes.bomclima.model.entity;

import java.util.Date;

public abstract class MedicaoRealizada extends Registro {

	private Date dataRealizacao;

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}
}
