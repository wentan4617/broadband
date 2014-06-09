package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

import com.tm.broadband.util.TMUtils;

public class ManualManipulationRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/*
 * TABLE MAPPING PROPERTIES
 */

	private Integer id;
	private String manipulation_name;
	private String manipulation_type;
	private Date manipulation_time;
	private String admin_name;
	private Integer admin_id;

/*
 * END TABLE MAPPING PROPERTIES
 */

/*
 * RELATED PROPERTIES
 */
	
	private String manipulation_time_str;

/*
 * END RELATED PROPERTIES
 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getManipulation_name() {
		return manipulation_name;
	}

	public void setManipulation_name(String manipulation_name) {
		this.manipulation_name = manipulation_name;
	}

	public String getManipulation_type() {
		return manipulation_type;
	}

	public void setManipulation_type(String manipulation_type) {
		this.manipulation_type = manipulation_type;
	}

	public Date getManipulation_time() {
		return manipulation_time;
	}

	public void setManipulation_time(Date manipulation_time) {
		this.manipulation_time = manipulation_time;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public Integer getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}

	public String getManipulation_time_str() {
		this.setManipulation_time_str(TMUtils.dateFormatYYYYMMDDHHMMSS(this.getManipulation_time()));
		return manipulation_time_str;
	}

	public void setManipulation_time_str(String manipulation_time_str) {
		this.manipulation_time_str = manipulation_time_str;
	}

}
