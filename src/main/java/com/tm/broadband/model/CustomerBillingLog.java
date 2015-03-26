package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class CustomerBillingLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private Integer customer_id;
	private Integer order_id;
	private Integer invoice_id;
	private Integer user_id;
	private String oper_type;
	private String oper_name;
	private Date oper_date;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String oper_date_str;

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
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getOper_type() {
		return oper_type;
	}
	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}
	public String getOper_name() {
		return oper_name;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public Date getOper_date() {
		return oper_date;
	}
	public void setOper_date(Date oper_date) {
		this.setOper_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(oper_date));
		this.oper_date = oper_date;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public String getOper_date_str() {
		return oper_date_str;
	}
	public void setOper_date_str(String oper_date_str) {
		this.oper_date_str = oper_date_str;
	}


}
