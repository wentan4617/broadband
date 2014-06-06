package com.tm.broadband.model;

import java.io.Serializable;

public class CallInternationalRate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String area_prefix;
	private String rate_type;
	private String area_name;
	private Double rate_cost;

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
}
