package com.etr.exceptions;

public class InvalidInterchangeNameException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String interchangeName = "";

	public String getInterchangeName() {
		return interchangeName;
	}

	public void setInterchangeName(String interchangeName) {
		this.interchangeName = interchangeName;
	}

	public InvalidInterchangeNameException(String message) {
		super(message);
	}

	public InvalidInterchangeNameException(String interchangeName, String message) {
		super(message);
		this.interchangeName = interchangeName;
	}

}
