package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class CustomerChorusBroadbandASIDRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Date statement_date;
	private String record_type;
	private String clear_service_id;
	private Date date_from;
	private Date date_to;
	private Date charge_date_time;
	private Integer duration;
	private String oot_id;
	private String billing_description;
	private Double amount_excl;
	private Double amount_incl;
	private String juristiction;
	private String phone_called;
	private Date upload_date;
	private Integer invoice_id;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	
	/*
	 * RELATED PROPERTIES
	 */

	private String statement_date_str;
	
	private String callType;
	
	private String formated_duration;
	
	private String charge_date_time_str;

	Map<String, Object> params = new HashMap<String, Object>();

	/*
	 * END RELATED PROPERTIES
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setStatement_date(Date statement_date) {
		this.setStatement_date_str(TMUtils.dateFormatYYYYMMDD(statement_date));
		this.statement_date = statement_date;
	}

	public Date getStatement_date() {
		return statement_date;
	}

	public String getRecord_type() {
		return record_type;
	}

	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}

	public String getClear_service_id() {
		return clear_service_id;
	}

	public void setClear_service_id(String clear_service_id) {
		this.clear_service_id = clear_service_id;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public Date getCharge_date_time() {
		return charge_date_time;
	}

	public void setCharge_date_time(Date charge_date_time) {
		this.charge_date_time = charge_date_time;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getOot_id() {
		return oot_id;
	}

	public void setOot_id(String oot_id) {
		this.oot_id = oot_id;
	}

	public String getBilling_description() {
		return billing_description;
	}

	public void setBilling_description(String billing_description) {
		this.billing_description = billing_description;
	}

	public Double getAmount_excl() {
		return amount_excl;
	}

	public void setAmount_excl(Double amount_excl) {
		this.amount_excl = amount_excl;
	}

	public Double getAmount_incl() {
		return amount_incl;
	}

	public void setAmount_incl(Double amount_incl) {
		this.amount_incl = amount_incl;
	}

	public String getPhone_called() {
		return phone_called;
	}

	public void setPhone_called(String phone_called) {
		this.phone_called = phone_called;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Date getUpload_date() {
		return upload_date;
	}

	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}

	public String getCharge_date_time_str() {
		this.setCharge_date_time_str(TMUtils.dateFormatYYYYMMDDHHMMSS(this.getCharge_date_time()));
		return charge_date_time_str;
	}

	public void setCharge_date_time_str(String charge_date_time_str) {
		this.charge_date_time_str = charge_date_time_str;
	}

	public String getFormated_duration() {
		return formated_duration;
	}

	public void setFormated_duration(String formated_duration) {
		this.formated_duration = formated_duration;
	}

	public String getJuristiction() {
		return juristiction;
	}

	public void setJuristiction(String juristiction) {
		this.juristiction = juristiction;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public Integer getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getStatement_date_str() {
		return statement_date_str;
	}

	public void setStatement_date_str(String statement_date_str) {
		this.statement_date_str = statement_date_str;
	}

}
