package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NetworkUsage implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Long id;
	private String vlan;
	private Long upload;
	private Long download;
	private Date accounting_date;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();
	private Long total_upload;
	private Long total_download;
	private Long total_usage;

	/*
	 * END RELATED PROPERTIES
	 */

	public NetworkUsage() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVlan() {
		return vlan;
	}

	public void setVlan(String vlan) {
		this.vlan = vlan;
	}

	public Long getUpload() {
		return upload;
	}

	public void setUpload(Long upload) {
		this.upload = upload;
	}

	public Long getDownload() {
		return download;
	}

	public void setDownload(Long download) {
		this.download = download;
	}

	public Date getAccounting_date() {
		return accounting_date;
	}

	public void setAccounting_date(Date accounting_date) {
		this.accounting_date = accounting_date;
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

	public Long getTotal_upload() {
		return total_upload;
	}

	public void setTotal_upload(Long total_upload) {
		this.total_upload = total_upload;
	}

	public Long getTotal_download() {
		return total_download;
	}

	public void setTotal_download(Long total_download) {
		this.total_download = total_download;
	}

	public Long getTotal_usage() {
		return total_usage;
	}

	public void setTotal_usage(Long total_usage) {
		this.total_usage = total_usage;
	}

}
