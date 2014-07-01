package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class JSONBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap = new HashMap<String, String>();
	private Map<String, String> successMap = new HashMap<String, String>();
	private T model;
	private List<T> models;
	private boolean hasErrors;
	private String url;
	
	public JSONBean() {
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

	public List<T> getModels() {
		return models;
	}

	public void setModels(List<T> models) {
		this.models = models;
	}
	
	public Map<String, String> getSuccessMap() {
		return successMap;
	}

	public void setSuccessMap(Map<String, String> successMap) {
		this.successMap = successMap;
	}
	
	public void setJSONErrorMap(BindingResult result) {
		List<FieldError> fields = result.getFieldErrors();
		for (FieldError field: fields) {
			System.out.println(field.getField() + ": " + field.getDefaultMessage());
			this.getErrorMap().put(field.getField(), field.getDefaultMessage());
		}
	}
	
	public void setJSONErrorMap(BindingResult result, int index) {
		List<FieldError> fields = result.getFieldErrors();
		for (FieldError field: fields) {
			System.out.println(field.getField() + ": " + field.getDefaultMessage() + ", index: " + index);
			this.getErrorMap().put(field.getField() + String.valueOf(index), field.getDefaultMessage());
		}
	}
}
