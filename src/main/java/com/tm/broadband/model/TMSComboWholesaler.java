package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TMSComboWholesaler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	
	private Integer id;
	private String name;
	private Integer wholesaler_id;
	private Double wholesale_price;
	private Double selling_price;
	private Double earned_price;
	private Boolean is_official_combo;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */


	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();

	/*
	 * END RELATED PROPERTIES
	 */
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getWholesaler_id() {
		return wholesaler_id;
	}
	public void setWholesaler_id(Integer wholesaler_id) {
		this.wholesaler_id = wholesaler_id;
	}
	public Double getWholesale_price() {
		return wholesale_price;
	}
	public void setWholesale_price(Double wholesale_price) {
		this.wholesale_price = wholesale_price;
	}
	public Double getSelling_price() {
		return selling_price;
	}
	public void setSelling_price(Double selling_price) {
		this.selling_price = selling_price;
	}
	public Double getEarned_price() {
		return earned_price;
	}
	public void setEarned_price(Double earned_price) {
		this.earned_price = earned_price;
	}
	public Boolean getIs_official_combo() {
		return is_official_combo;
	}
	public void setIs_official_combo(Boolean is_official_combo) {
		this.is_official_combo = is_official_combo;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
