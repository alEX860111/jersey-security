package com.example.domain;

public final class Message {

	public String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static Message create(String message) {
		Message res = new Message();
		res.setMessage(message);
		return res;
	}

}
