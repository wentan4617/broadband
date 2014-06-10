package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String content;
	private String type;
	private String sort;

	/*
	 * BEGIN RELATED PROPERTIES
	 */
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	/*
	 * END RELATED PROPERTIES
	 */

	public Notification(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
