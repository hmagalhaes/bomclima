package br.eti.hmagalhaes.bomclima.model.dao.exception;

@SuppressWarnings("serial")
public class NonUniqueResultException extends RuntimeException {
	
	public NonUniqueResultException(String msg) {
		super(msg);
	}
}
