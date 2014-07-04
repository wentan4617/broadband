package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

public class TempDataUsage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String vlan;
	private Date usage_date;
	private Long upload;
	private Long download;
	private Long maxcount;
	
	public TempDataUsage() {
		// TODO Auto-generated constructor stub
	}

	public String getVlan() {
		return vlan;
	}

	public void setVlan(String vlan) {
		this.vlan = vlan;
	}

	public Date getUsage_date() {
		return usage_date;
	}

	public void setUsage_date(Date usage_date) {
		this.usage_date = usage_date;
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

	public Long getMaxcount() {
		return maxcount;
	}

	public void setMaxcount(Long maxcount) {
		this.maxcount = maxcount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
