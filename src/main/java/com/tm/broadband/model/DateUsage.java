package com.tm.broadband.model;

import java.io.Serializable;

public class DateUsage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String date;
	private NetworkUsage usage;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public NetworkUsage getUsage() {
		return usage;
	}

	public void setUsage(NetworkUsage usage) {
		this.usage = usage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DateUsage() {
		// TODO Auto-generated constructor stub
	}

}