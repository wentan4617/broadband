package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * TABLE MAPPING PROPERTIES
 */

/*
 * END TABLE MAPPING PROPERTIES
 */

/*
 * RELATED PROPERTIES
 */

/*
 * END RELATED PROPERTIES
 */

public class BillingFileUpload implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	private Integer id;
	private Date upload_date;
	private String upload_path;
	private String upload_file_name;
	private Boolean inserted_database;
	private Integer upload_by;
	private String billing_type;
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

	public BillingFileUpload() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getUpload_date() {
		return upload_date;
	}

	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}

	public String getUpload_path() {
		return upload_path;
	}

	public void setUpload_path(String upload_path) {
		this.upload_path = upload_path;
	}

	public String getUpload_file_name() {
		return upload_file_name;
	}

	public void setUpload_file_name(String upload_file_name) {
		this.upload_file_name = upload_file_name;
	}

	public Boolean getInserted_database() {
		return inserted_database;
	}

	public void setInserted_database(Boolean inserted_database) {
		this.inserted_database = inserted_database;
	}

	public Integer getUpload_by() {
		return upload_by;
	}

	public void setUpload_by(Integer upload_by) {
		this.upload_by = upload_by;
	}

	public String getBilling_type() {
		return billing_type;
	}

	public void setBilling_type(String billing_type) {
		this.billing_type = billing_type;
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
