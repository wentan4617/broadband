package com.tm.broadband.model;

import java.io.Serializable;

/**
 * return message object
 * 
 * @author AAA
 * 
 */
public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;
	private String message;

	public ResponseMessage(String type, String message) {
		this.type = type;
		this.message = message;
	}

	public ResponseMessage() {
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
