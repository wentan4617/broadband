package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class EarlyTerminationCharge implements Serializable {

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
	private Date create_date;
	private Date service_given_date;
	private Date termination_date;
	private Date legal_termination_date;
	private Date due_date;
	private Double overdue_extra_charge;
	private Double charge_amount;
	private Double total_payable_amount;
	private Integer months_between_begin_end;
	private Integer execute_by;
	private String invoice_pdf_path;
	private String status;
	/*
	 * END TABLE MAPPING PROPERTIES
	 */
	

	/*
	 * BEGIN RELATED PROPERTIES
	 */
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	private String create_date_str;
	private String service_given_date_str;
	private String termination_date_str;
	private String legal_termination_date_str;
	private String due_date_str;
	
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

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getService_given_date() {
		return service_given_date;
	}

	public void setService_given_date(Date service_given_date) {
		this.service_given_date = service_given_date;
	}

	public Date getTermination_date() {
		return termination_date;
	}

	public void setTermination_date(Date termination_date) {
		this.termination_date = termination_date;
	}

	public Date getLegal_termination_date() {
		return legal_termination_date;
	}

	public void setLegal_termination_date(Date legal_termination_date) {
		this.legal_termination_date = legal_termination_date;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public Double getOverdue_extra_charge() {
		return overdue_extra_charge;
	}

	public void setOverdue_extra_charge(Double overdue_extra_charge) {
		this.overdue_extra_charge = overdue_extra_charge;
	}

	public Double getCharge_amount() {
		return charge_amount;
	}

	public void setCharge_amount(Double charge_amount) {
		this.charge_amount = charge_amount;
	}

	public Integer getExecute_by() {
		return execute_by;
	}

	public void setExecute_by(Integer execute_by) {
		this.execute_by = execute_by;
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

	public String getService_given_date_str() {
		this.setService_given_date_str(TMUtils.dateFormatYYYYMMDD(this.getService_given_date()));
		return service_given_date_str;
	}

	public void setService_given_date_str(String service_given_date_str) {
		this.service_given_date_str = service_given_date_str;
	}

	public String getTermination_date_str() {
		this.setTermination_date_str(TMUtils.dateFormatYYYYMMDD(this.getTermination_date()));
		return termination_date_str;
	}

	public void setTermination_date_str(String termination_date_str) {
		this.termination_date_str = termination_date_str;
	}

	public String getLegal_termination_date_str() {
		this.setLegal_termination_date_str(TMUtils.dateFormatYYYYMMDD(this.getLegal_termination_date()));
		return legal_termination_date_str;
	}

	public void setLegal_termination_date_str(String legal_termination_date_str) {
		this.legal_termination_date_str = legal_termination_date_str;
	}

	public String getDue_date_str() {
		this.setDue_date_str(TMUtils.dateFormatYYYYMMDD(this.getDue_date()));
		return due_date_str;
	}

	public void setDue_date_str(String due_date_str) {
		this.due_date_str = due_date_str;
	}

	public Double getTotal_payable_amount() {
		return total_payable_amount;
	}

	public void setTotal_payable_amount(Double total_payable_amount) {
		this.total_payable_amount = total_payable_amount;
	}

	public Integer getMonths_between_begin_end() {
		return months_between_begin_end;
	}

	public void setMonths_between_begin_end(Integer months_between_begin_end) {
		this.months_between_begin_end = months_between_begin_end;
	}

	public String getInvoice_pdf_path() {
		return invoice_pdf_path;
	}

	public void setInvoice_pdf_path(String invoice_pdf_path) {
		this.invoice_pdf_path = invoice_pdf_path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
