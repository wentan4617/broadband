package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Organization implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer customer_id;
	private String org_name;
	private String org_type;
	private String org_trading_name;
	private String org_register_no;
	private Date org_incoporate_date;
	private Integer org_trading_months;
	private String holder_name;
	private String holder_job_title;
	private String holder_phone;
	private String holder_email;

	/*
	 * BEGIN RELATED PROPERTIES
	 */
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	/*
	 * END RELATED PROPERTIES
	 */

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOrg_type() {
		return org_type;
	}

	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}

	public String getOrg_trading_name() {
		return org_trading_name;
	}

	public void setOrg_trading_name(String org_trading_name) {
		this.org_trading_name = org_trading_name;
	}

	public String getOrg_register_no() {
		return org_register_no;
	}

	public void setOrg_register_no(String org_register_no) {
		this.org_register_no = org_register_no;
	}

	public Date getOrg_incoporate_date() {
		return org_incoporate_date;
	}

	public void setOrg_incoporate_date(Date org_incoporate_date) {
		this.org_incoporate_date = org_incoporate_date;
	}

	public Integer getOrg_trading_months() {
		return org_trading_months;
	}

	public void setOrg_trading_months(Integer org_trading_months) {
		this.org_trading_months = org_trading_months;
	}

	public String getHolder_name() {
		return holder_name;
	}

	public void setHolder_name(String holder_name) {
		this.holder_name = holder_name;
	}

	public String getHolder_job_title() {
		return holder_job_title;
	}

	public void setHolder_job_title(String holder_job_title) {
		this.holder_job_title = holder_job_title;
	}

	public String getHolder_phone() {
		return holder_phone;
	}

	public void setHolder_phone(String holder_phone) {
		this.holder_phone = holder_phone;
	}

	public String getHolder_email() {
		return holder_email;
	}

	public void setHolder_email(String holder_email) {
		this.holder_email = holder_email;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
