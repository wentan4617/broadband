package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CustomerOrderChorusAddon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private Integer order_id;
	private String addon_name;
	private Double addon_price;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	
	/*
	 * RELATED PROPERTIES
	 */

	Map<String, Object> params = new HashMap<String, Object>();

	/*
	 * END RELATED PROPERTIES
	 */

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getAddon_name() {
		return addon_name;
	}
	public void setAddon_name(String addon_name) {
		this.addon_name = addon_name;
	}
	public Double getAddon_price() {
		return addon_price;
	}
	public void setAddon_price(Double addon_price) {
		this.addon_price = addon_price;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
