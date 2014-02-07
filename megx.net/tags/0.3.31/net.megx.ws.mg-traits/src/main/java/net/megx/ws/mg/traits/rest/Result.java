package net.megx.ws.mg.traits.rest;

public class Result<T> {
	private boolean error;
	private String message;
	private String reason;
	
	private T data;
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean success) {
		this.error = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Result(boolean error, String message, String reason) {
		super();
		this.error = error;
		this.message = message;
		this.reason = reason;
	}
	
	public Result() {}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Result(T data) {
		super();
		this.data = data;
	}
}
