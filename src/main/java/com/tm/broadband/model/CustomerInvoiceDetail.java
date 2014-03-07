package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
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
	private String invoice_detail_name;
	private String invoice_detail_desc;
	private Double invoice_detail_price;
	private Date invoice_detail_date;
	private Integer invoice_detail_unit;
	private Double invoice_detail_discount;
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();

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

	public String getInvoice_detail_name() {
		return invoice_detail_name;
	}

	public void setInvoice_detail_name(String invoice_detail_name) {
		this.invoice_detail_name = invoice_detail_name;
	}

	public String getInvoice_detail_desc() {
		return invoice_detail_desc;
	}

	public void setInvoice_detail_desc(String invoice_detail_desc) {
		this.invoice_detail_desc = invoice_detail_desc;
	}

	public Double getInvoice_detail_price() {
		return invoice_detail_price;
	}

	public void setInvoice_detail_price(Double invoice_detail_price) {
		this.invoice_detail_price = invoice_detail_price;
	}

	public Date getInvoice_detail_date() {
		return invoice_detail_date;
	}

	public void setInvoice_detail_date(Date invoice_detail_date) {
		this.invoice_detail_date = invoice_detail_date;
	}

	public Integer getInvoice_detail_unit() {
		return invoice_detail_unit;
	}

	public void setInvoice_detail_unit(Integer invoice_detail_unit) {
		this.invoice_detail_unit = invoice_detail_unit;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Double getInvoice_detail_discount() {
		return invoice_detail_discount;
	}

	public void setInvoice_detail_discount(Double invoice_detail_discount) {
		this.invoice_detail_discount = invoice_detail_discount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * END RELATED PROPERTIES
	 */


}
