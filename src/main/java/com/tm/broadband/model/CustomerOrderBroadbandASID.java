package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CustomerOrderBroadbandASID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private Integer order_id;
	private String broadband_asid;

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
	public String getBroadband_asid() {
		return broadband_asid;
	}
	public void setBroadband_asid(String broadband_asid) {
		this.broadband_asid = broadband_asid;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	

}
