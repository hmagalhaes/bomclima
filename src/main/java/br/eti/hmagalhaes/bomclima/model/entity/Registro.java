package br.eti.hmagalhaes.bomclima.model.entity;

import java.util.Date;

public abstract class Registro {

	private long id;
	private Date dataRegistro;
	private Usuario registrante;
	private Date dataUltimaEdicao;
	private Usuario ultimoEditor;
	private Cidade cidade;
	private TipoRegistro tipoRegistro;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Usuario getRegistrante() {
		return registrante;
	}

	public void setRegistrante(Usuario registrante) {
		this.registrante = registrante;
	}

	public Date getDataUltimaEdicao() {
		return dataUltimaEdicao;
	}

	public void setDataUltimaEdicao(Date dataUltimaEdicao) {
		this.dataUltimaEdicao = dataUltimaEdicao;
	}

	public Usuario getUltimoEditor() {
		return ultimoEditor;
	}

	public void setUltimoEditor(Usuario ultimoEditor) {
		this.ultimoEditor = ultimoEditor;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
}
