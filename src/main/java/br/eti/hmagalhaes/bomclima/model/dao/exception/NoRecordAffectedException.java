package br.eti.hmagalhaes.bomclima.model.dao.exception;

@SuppressWarnings("serial")
public class NoRecordAffectedException extends RuntimeException {

	public NoRecordAffectedException(String msg) {
		super(msg);
	}
}
