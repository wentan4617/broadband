package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TMSMaterialType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	
	private Integer id;
	private Integer group_id;
	private String type_name;

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
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
