package com.gez.grill.entity;

public class Result {
	private boolean success;

	private String message;

	public Result() {
	}

	public Result(boolean isSuccess, String message) {
		this.success = isSuccess;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
