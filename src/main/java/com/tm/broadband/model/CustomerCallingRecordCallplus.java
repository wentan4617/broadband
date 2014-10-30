package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class CustomerCallingRecordCallplus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	
	private Integer id;
	private Date date;
	private Integer length;
	private String description;
	private Double charged_fee;
	private String original_number;
	private String destination_number;
	private String type;
	private Integer invoice_id;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	
	/*
	 * RELATED PROPERTIES
	 */
	
	private String date_str;
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.setDate_str(TMUtils.dateFormatYYYYMMDD(date));
		this.date = date;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCharged_fee() {
		return charged_fee;
	}

	public void setCharged_fee(Double charged_fee) {
		this.charged_fee = charged_fee;
	}

	public String getOriginal_number() {
		return original_number;
	}
	
	public void setOriginal_number(String original_number) {
		this.original_number = original_number;
	}

	public String getDestination_number() {
		return destination_number;
	}

	public void setDestination_number(String destination_number) {
		this.destination_number = destination_number;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getDate_str() {
		return date_str;
	}

	public void setDate_str(String date_str) {
		this.date_str = date_str;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public Integer getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
	}
	

}
