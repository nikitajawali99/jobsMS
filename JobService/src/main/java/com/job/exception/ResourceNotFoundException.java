package com.job.exception;

public class ResourceNotFoundException extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("Reasource not found");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
