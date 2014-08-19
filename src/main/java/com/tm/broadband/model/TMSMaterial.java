package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TMSMaterial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	
	private Integer id;
	private String name;
	private String material_group;
	private String material_type;
	private String material_category;
	private String description;
	private Double wholesale_price;
	private Double selling_price;
	private Double earned_price;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */
	

	/*
	 * TABLE RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();
	
	/*
	 * END TABLE RELATED PROPERTIES
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
	public String getMaterial_group() {
		return material_group;
	}
	public void setMaterial_group(String material_group) {
		this.material_group = material_group;
	}
	public String getMaterial_type() {
		return material_type;
	}
	public void setMaterial_type(String material_type) {
		this.material_type = material_type;
	}
	public String getMaterial_category() {
		return material_category;
	}
	public void setMaterial_category(String material_category) {
		this.material_category = material_category;
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
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
