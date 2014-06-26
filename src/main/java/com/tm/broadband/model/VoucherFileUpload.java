package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class VoucherFileUpload implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private String file_name;
	private String file_path;
	private String status;
	private Integer inserted_by;
	private Integer upload_by;
	private Date inserted_date;
	private Date upload_date;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String inserted_date_str;
	private String upload_date_str;
	
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
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getInserted_by() {
		return inserted_by;
	}
	public void setInserted_by(Integer inserted_by) {
		this.inserted_by = inserted_by;
	}
	public Integer getUpload_by() {
		return upload_by;
	}
	public void setUpload_by(Integer upload_by) {
		this.upload_by = upload_by;
	}
	public Date getInserted_date() {
		return inserted_date;
	}
	public void setInserted_date(Date inserted_date) {
		this.inserted_date = inserted_date;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public String getInserted_date_str() {
		this.setInserted_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(this.getInserted_date()));
		return inserted_date_str;
	}
	public void setInserted_date_str(String inserted_date_str) {
		this.inserted_date_str = inserted_date_str;
	}
	public String getUpload_date_str() {
		this.setUpload_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(this.getUpload_date()));
		return upload_date_str;
	}
	public void setUpload_date_str(String upload_date_str) {
		this.upload_date_str = upload_date_str;
	}

}
