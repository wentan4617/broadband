package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TMSMaterialGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	
	private Integer id;
	private String group_name;

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
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
