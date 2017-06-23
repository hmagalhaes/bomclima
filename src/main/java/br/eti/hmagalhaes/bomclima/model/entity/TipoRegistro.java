package br.eti.hmagalhaes.bomclima.model.entity;

public enum TipoRegistro {

	CHUVA_PREVISTA((byte) 1, "Previsão de Chuva"),
	CHUVA_REALIZADA((byte) 2, "Registro de Chuva"),
	TEMPERATURA_PREVISTA((byte) 3, "Previsão de Temperatura"),
	TEMPERATURA_REALIZADA((byte) 4, "Registro de Temperatura");

	final byte id;
	final String descricao;

	TipoRegistro(final byte id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public byte getId() {
		return id;
	}
	
	public String getName() {
		return name();
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoRegistro byId(final byte id) {
		for (TipoRegistro tipo : TipoRegistro.values()) {
			if (tipo.getId() == id) {
				return tipo;
			}
		}
		return null;
	}
}
