package br.eti.hmagalhaes.bomclima.model.entity;

public class TemperaturaRealizada extends MedicaoRealizada implements TemperaturaMensuravel {

	private float temperaturaEmCelcius;

	public float getTemperaturaEmCelcius() {
		return temperaturaEmCelcius;
	}

	public void setTemperaturaEmCelcius(float temperaturaEmCelcius) {
		this.temperaturaEmCelcius = temperaturaEmCelcius;
	}
}
