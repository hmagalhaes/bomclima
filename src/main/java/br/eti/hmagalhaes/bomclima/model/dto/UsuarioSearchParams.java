package br.eti.hmagalhaes.bomclima.model.dto;

public class UsuarioSearchParams {

	private final Integer id;
	private final String login;
	private final String nome;
	private final String email;
	private final Boolean ativo;

	public UsuarioSearchParams(Integer id, String login, String nome, String email, Boolean ativo) {
		this.id = id;
		this.login = login;
		this.nome = nome;
		this.email = email;
		this.ativo = ativo;
	}

	public Integer getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	@Override
	public String toString() {
		return "UsuarioSearchParams [id=" + id + ", login=" + login + ", nome=" + nome + ", email=" + email + ", ativo="
				+ ativo + "]";
	}
}
