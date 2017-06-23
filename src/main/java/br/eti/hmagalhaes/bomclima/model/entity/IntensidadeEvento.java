package br.eti.hmagalhaes.bomclima.model.entity;

public enum IntensidadeEvento {

	FRACO("Fraco"), MEDIO("Médio"), FORTE("Forte");

	private final String descricao;

	IntensidadeEvento(final String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getCode() {
		return name();
	}

	public static IntensidadeEvento fromName(String value) {
		try {
			return IntensidadeEvento.valueOf(value);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}
}
