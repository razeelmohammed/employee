package com.codingtest.helper;

public class StandardResponse {
	private String alert;
	private Object responseObject;
	private int statusCode;

	public StandardResponse(int statusCode, String alert, String message) {
		this.statusCode = statusCode;
		this.alert = alert;
	}

	public StandardResponse(int statusCode, String alert, String message, Object responseObject) {
		this.statusCode = statusCode;
		this.alert = alert;
		this.responseObject = responseObject;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
