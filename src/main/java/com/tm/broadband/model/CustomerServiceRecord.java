package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class CustomerServiceRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * BEGIN TABLE MAPPING PROPERTIES
	 */
	private Integer id;
	private Integer customer_id;
	private Integer user_id;
	private String description;
	private Date create_date;
	/*
	 * END TABLE MAPPING PROPERTIES
	 */
	
	/*
	 * BEGIN RELATED PROPERTIES
	 */
	private Map<String, Object> params = new HashMap<String, Object>();
	private String create_date_str;
	/*
	 * END RELATED PROPERTIES
	 */
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public String getCreate_date_str() {
		this.setCreate_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(this.getCreate_date()));
		return create_date_str;
	}
	public void setCreate_date_str(String create_date_str) {
		this.create_date_str = create_date_str;
	}


}
