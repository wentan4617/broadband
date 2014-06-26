package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VoucherBannedList implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private Integer customer_id;
	private Integer attempt_times;
	private Date forbad_date;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String forbad_date_str;
	
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
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getAttempt_times() {
		return attempt_times;
	}
	public void setAttempt_times(Integer attempt_times) {
		this.attempt_times = attempt_times;
	}
	public Date getForbad_date() {
		return forbad_date;
	}
	public void setForbad_date(Date forbad_date) {
		this.forbad_date = forbad_date;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public String getForbad_date_str() {
		return forbad_date_str;
	}
	public void setForbad_date_str(String forbad_date_str) {
		this.forbad_date_str = forbad_date_str;
	}

}
