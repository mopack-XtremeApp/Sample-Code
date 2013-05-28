package com.assignment.security.controller.bo;

/**
 * Encapsulate HTTP Operation result.
 * @author Moti Prajapati
 *
 */
public class HttpOperationResult {

	private String operation;

	private String message;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
