package com.job.exception;

public class ResourceNotFoundException extends RuntimeException{

	
	public ResourceNotFoundException() {
		super("Reasource not found");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
