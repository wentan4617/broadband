package com.tm.broadband.model;

import java.io.Serializable;

public class SEO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String description;
	private String keywords;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
