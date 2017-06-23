package br.eti.hmagalhaes.bomclima.model.dto;

import java.util.Date;

public class RegistroSearchParams {

	private final Date dataRegistro1;
	private final Date dataRegistro2;
	private final String cidade;
	private final String uf;

	public RegistroSearchParams(final Date dataRegistro1, final Date dataRegistro2, final String cidade,
			final String uf) {
		this.dataRegistro1 = dataRegistro1;
		this.dataRegistro2 = dataRegistro2;
		this.cidade = cidade;
		this.uf = uf;
	}

	public Date getDataRegistro1() {
		return dataRegistro1;
	}

	public Date getDataRegistro2() {
		return dataRegistro2;
	}

	public String getCidade() {
		return cidade;
	}

	public String getUf() {
		return uf;
	}

	@Override
	public String toString() {
		return "RegistroSearchParams [dataRegistro1=" + dataRegistro1 + ", dataRegistro2=" + dataRegistro2 + ", cidade="
				+ cidade + ", uf=" + uf + "]";
	}
}
