package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class TerminationRefund implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * BEGIN TABLE MAPPING PROPERTIES
	 */
	private Integer id;
	private Integer customer_id;
	private Integer order_id;
	private Double product_monthly_price;
	private Date create_date;
	private Date last_date_of_month;
	private Date termination_date;
	private String refund_bank_account_number;
	private String refund_bank_account_name;
	private Double refund_amount;
	private Integer execute_by;
	private String refund_pdf_path;
	private String product_name;
	private String status;
	private Integer days_between_end_last;
	/*
	 * END TABLE MAPPING PROPERTIES
	 */
	

	/*
	 * BEGIN RELATED PROPERTIES
	 */
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	private String create_date_str;
	private String last_date_of_month_str;
	private String termination_date_str;
	
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

	public Double getProduct_monthly_price() {
		return product_monthly_price;
	}

	public void setProduct_monthly_price(Double product_monthly_price) {
		this.product_monthly_price = product_monthly_price;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getTermination_date() {
		return termination_date;
	}

	public void setTermination_date(Date termination_date) {
		this.setTermination_date_str(TMUtils.dateFormatYYYYMMDD(termination_date));
		this.termination_date = termination_date;
	}

	public String getRefund_bank_account_number() {
		return refund_bank_account_number;
	}

	public void setRefund_bank_account_number(String refund_bank_account_number) {
		this.refund_bank_account_number = refund_bank_account_number;
	}

	public String getRefund_bank_account_name() {
		return refund_bank_account_name;
	}

	public void setRefund_bank_account_name(String refund_bank_account_name) {
		this.refund_bank_account_name = refund_bank_account_name;
	}

	public Double getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(Double refund_amount) {
		this.refund_amount = refund_amount;
	}

	public Integer getExecute_by() {
		return execute_by;
	}

	public void setExecute_by(Integer execute_by) {
		this.execute_by = execute_by;
	}

	public String getRefund_pdf_path() {
		return refund_pdf_path;
	}

	public void setRefund_pdf_path(String refund_pdf_path) {
		this.refund_pdf_path = refund_pdf_path;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getCreate_date_str() {
		this.setCreate_date_str(TMUtils.dateFormatYYYYMMDD(this.getCreate_date()));
		return create_date_str;
	}

	public void setCreate_date_str(String create_date_str) {
		this.create_date_str = create_date_str;
	}

	public String getTermination_date_str() {
		return termination_date_str;
	}

	public void setTermination_date_str(String termination_date_str) {
		this.termination_date_str = termination_date_str;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDays_between_end_last() {
		return days_between_end_last;
	}

	public void setDays_between_end_last(Integer days_between_end_last) {
		this.days_between_end_last = days_between_end_last;
	}

	public Date getLast_date_of_month() {
		return last_date_of_month;
	}

	public void setLast_date_of_month(Date last_date_of_month) {
		this.last_date_of_month = last_date_of_month;
	}

	public String getLast_date_of_month_str() {
		this.setLast_date_of_month_str(TMUtils.dateFormatYYYYMMDD(this.getLast_date_of_month()));
		return last_date_of_month_str;
	}

	public void setLast_date_of_month_str(String last_date_of_month_str) {
		this.last_date_of_month_str = last_date_of_month_str;
	}

}
