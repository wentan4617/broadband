package com.tm.broadband.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallInternationalRate implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * BEGIN TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private String area_prefix;
	private String rate_type;
	private String area_name;
	private Double rate_cost;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	

	/*
	 * BEGIN RELATED PROPERTIES
	 */
	
	private String letter;
	private List<CallInternationalRate> cirs = new ArrayList<CallInternationalRate>();
	private Map<String, Object> params = new HashMap<String, Object>();

	/*
	 * END RELATED PROPERTIES
	 */
	
	public CallInternationalRate() {

	}

	public CallInternationalRate(String letter) {
		this.setLetter(letter);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArea_prefix() {
		return area_prefix;
	}

	public void setArea_prefix(String area_prefix) {
		this.area_prefix = area_prefix;
	}

	public String getRate_type() {
		return rate_type;
	}

	public void setRate_type(String rate_type) {
		this.rate_type = rate_type;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public Double getRate_cost() {
		return rate_cost;
	}

	public void setRate_cost(Double rate_cost) {
		this.rate_cost = rate_cost;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public List<CallInternationalRate> getCirs() {
		return cirs;
	}

	public void setCirs(List<CallInternationalRate> cirs) {
		this.cirs = cirs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
