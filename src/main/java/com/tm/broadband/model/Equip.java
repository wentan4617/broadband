package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class Equip implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private String equip_name;
	private String equip_type;
	private String equip_purpose;
	private String equip_sn;
	private String equip_status;
	private Integer order_detail_id;
	private Date warehousing_date;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private String equip_name_sn;
	private Integer order_id;
	private Integer customer_id;
	private String warehousing_date_str;

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
	public String getEquip_name() {
		return equip_name;
	}
	public void setEquip_name(String equip_name) {
		this.equip_name = equip_name;
	}
	public String getEquip_type() {
		return equip_type;
	}
	public void setEquip_type(String equip_type) {
		this.equip_type = equip_type;
	}
	public String getEquip_purpose() {
		return equip_purpose;
	}
	public void setEquip_purpose(String equip_purpose) {
		this.equip_purpose = equip_purpose;
	}
	public String getEquip_sn() {
		return equip_sn;
	}
	public void setEquip_sn(String equip_sn) {
		this.equip_sn = equip_sn;
	}
	public String getEquip_status() {
		return equip_status;
	}
	public void setEquip_status(String equip_status) {
		this.equip_status = equip_status;
	}
	public Integer getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(Integer order_detail_id) {
		this.order_detail_id = order_detail_id;
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
	public Date getWarehousing_date() {
		return warehousing_date;
	}
	public void setWarehousing_date(Date warehousing_date) {
		this.setWarehousing_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(warehousing_date));
		this.warehousing_date = warehousing_date;
	}
	public String getWarehousing_date_str() {
		return warehousing_date_str;
	}
	public void setWarehousing_date_str(String warehousing_date_str) {
		this.warehousing_date_str = warehousing_date_str;
	}
	public String getEquip_name_sn() {
		return equip_name_sn;
	}
	public void setEquip_name_sn(String equip_name_sn) {
		this.equip_name_sn = equip_name_sn;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}


}
