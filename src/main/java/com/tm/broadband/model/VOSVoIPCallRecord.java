package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class VOSVoIPCallRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private Date call_start;
	private String ori_number;
	private String dest_number;
	private Double call_length;
	private Integer charge_length;
	private Double call_fee;
	private Double call_cost;
	private String call_type;
	private Integer area_prefix;
	private Integer invoice_id;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String call_start_str;

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
	public Date getCall_start() {
		return call_start;
	}
	public void setCall_start(Date call_start) {
		this.setCall_start_str(TMUtils.dateFormatYYYYMMDD(call_start));
		this.call_start = call_start;
	}
	public String getOri_number() {
		return ori_number;
	}
	public void setOri_number(String ori_number) {
		this.ori_number = ori_number;
	}
	public String getDest_number() {
		return dest_number;
	}
	public void setDest_number(String dest_number) {
		this.dest_number = dest_number;
	}
	public Double getCall_length() {
		return call_length;
	}
	public void setCall_length(Double call_length) {
		this.call_length = call_length;
	}
	public Integer getCharge_length() {
		return charge_length;
	}
	public void setCharge_length(Integer charge_length) {
		this.charge_length = charge_length;
	}
	public Double getCall_fee() {
		return call_fee;
	}
	public void setCall_fee(Double call_fee) {
		this.call_fee = call_fee;
	}
	public Double getCall_cost() {
		return call_cost;
	}
	public void setCall_cost(Double call_cost) {
		this.call_cost = call_cost;
	}
	public String getCall_type() {
		return call_type;
	}
	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}
	public Integer getArea_prefix() {
		return area_prefix;
	}
	public void setArea_prefix(Integer area_prefix) {
		this.area_prefix = area_prefix;
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
	public String getCall_start_str() {
		return call_start_str;
	}
	public void setCall_start_str(String call_start_str) {
		this.call_start_str = call_start_str;
	}
	public Integer getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
	}

}
