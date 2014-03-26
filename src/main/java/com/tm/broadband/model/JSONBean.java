package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JSONBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap = new HashMap<String, String>();
	private T model;
	private boolean hasErrors;
	private String url;
	
	public JSONBean() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isHasErrors() {
		hasErrors = !errorMap.isEmpty();
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
