package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

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
	private Date statement_date;
	private Date insert_date;
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String upload_date_str;
	private String statement_date_str;
	private String insert_date_str;
	
	
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

	public String getUpload_date_str() {
		this.setUpload_date_str(TMUtils.dateFormatYYYYMMDD(this.getUpload_date()));
		return upload_date_str;
	}

	public void setUpload_date_str(String upload_date_str) {
		this.upload_date_str = upload_date_str;
	}

	public Date getStatement_date() {
		return statement_date;
	}

	public void setStatement_date(Date statement_date) {
		this.statement_date = statement_date;
	}

	public String getStatement_date_str() {
		this.setStatement_date_str(TMUtils.dateFormatYYYYMMDD(this.getStatement_date()));
		return statement_date_str;
	}

	public void setStatement_date_str(String statement_date_str) {
		this.statement_date_str = statement_date_str;
	}

	public Date getInsert_date() {
		return insert_date;
	}

	public void setInsert_date(Date insert_date) {
		this.insert_date = insert_date;
	}

	public String getInsert_date_str() {
		this.setInsert_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(this.getInsert_date()));
		return insert_date_str;
	}

	public void setInsert_date_str(String insert_date_str) {
		this.insert_date_str = insert_date_str;
	}

}
