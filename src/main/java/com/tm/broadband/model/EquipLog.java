package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class EquipLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private Integer equip_id;
	private String oper_type;
	private Date log_date;
	private String log_desc;
	private Integer user_id;
	
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String log_date_str;

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
	public Integer getEquip_id() {
		return equip_id;
	}
	public void setEquip_id(Integer equip_id) {
		this.equip_id = equip_id;
	}
	public String getOper_type() {
		return oper_type;
	}
	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}
	public Date getLog_date() {
		return log_date;
	}
	public void setLog_date(Date log_date) {
		this.setLog_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(log_date));
		this.log_date = log_date;
	}
	public String getLog_desc() {
		return log_desc;
	}
	public void setLog_desc(String log_desc) {
		this.log_desc = log_desc;
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
	public String getLog_date_str() {
		return log_date_str;
	}
	public void setLog_date_str(String log_date_str) {
		this.log_date_str = log_date_str;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

}
