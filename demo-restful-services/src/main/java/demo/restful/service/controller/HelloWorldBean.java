package demo.restful.service.controller;

public class HelloWorldBean {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HelloWorldBean(String message) {
		this.message = message;
	}

	public String toString() {
		return String.format("HelloWorldBean [message=%s]", message);
	}
}
