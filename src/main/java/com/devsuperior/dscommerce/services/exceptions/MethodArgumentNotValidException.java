package com.devsuperior.dscommerce.services.exceptions;

public class MethodArgumentNotValidException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MethodArgumentNotValidException(String msg) {
		super(msg);
	}

	public Object getBindingResult() {
		return null;
	}
}
