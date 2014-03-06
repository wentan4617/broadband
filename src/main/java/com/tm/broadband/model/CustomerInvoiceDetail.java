package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * customer invoice detail, mapping tm_customer_invoice_detail
 * 
 * @author Cook1fan
 * 
 */
public class CustomerInvoiceDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	private Integer id;
	private CustomerInvoice customerInvoice;
	private String invoice_plan_name;
	private String invoice_plan_desc;
	private String invoice_plan_price;
	private String invoice_plan_new_connection_fee;
	private String invoice_unit;
	private String invoice_hardware_name;
	private String invoice_hardware_price;
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

	public CustomerInvoiceDetail() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CustomerInvoice getCustomerInvoice() {
		return customerInvoice;
	}

	public void setCustomerInvoice(CustomerInvoice customerInvoice) {
		this.customerInvoice = customerInvoice;
	}

	public String getInvoice_plan_name() {
		return invoice_plan_name;
	}

	public void setInvoice_plan_name(String invoice_plan_name) {
		this.invoice_plan_name = invoice_plan_name;
	}

	public String getInvoice_plan_desc() {
		return invoice_plan_desc;
	}

	public void setInvoice_plan_desc(String invoice_plan_desc) {
		this.invoice_plan_desc = invoice_plan_desc;
	}

	public String getInvoice_plan_price() {
		return invoice_plan_price;
	}

	public void setInvoice_plan_price(String invoice_plan_price) {
		this.invoice_plan_price = invoice_plan_price;
	}

	public String getInvoice_plan_new_connection_fee() {
		return invoice_plan_new_connection_fee;
	}

	public void setInvoice_plan_new_connection_fee(
			String invoice_plan_new_connection_fee) {
		this.invoice_plan_new_connection_fee = invoice_plan_new_connection_fee;
	}

	public String getInvoice_unit() {
		return invoice_unit;
	}

	public void setInvoice_unit(String invoice_unit) {
		this.invoice_unit = invoice_unit;
	}

	public String getInvoice_hardware_name() {
		return invoice_hardware_name;
	}

	public void setInvoice_hardware_name(String invoice_hardware_name) {
		this.invoice_hardware_name = invoice_hardware_name;
	}

	public String getInvoice_hardware_price() {
		return invoice_hardware_price;
	}

	public void setInvoice_hardware_price(String invoice_hardware_price) {
		this.invoice_hardware_price = invoice_hardware_price;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
