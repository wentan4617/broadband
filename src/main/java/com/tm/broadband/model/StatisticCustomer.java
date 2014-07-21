package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

import com.tm.broadband.util.TMUtils;

public class StatisticCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date registerDate;
	private Integer registerCount;
	private String registerWeekDate_str;
	private String registerMonthDate_str;

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
		this.setRegisterMonthDate_str(TMUtils.dateFormatYYYYMMDD(this.getRegisterDate()));
	}

	public Integer getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRegisterWeekDate_str() {
		return registerWeekDate_str;
	}

	public void setRegisterWeekDate_str(String registerWeekDate_str) {
		this.registerWeekDate_str = registerWeekDate_str;
	}

	public String getRegisterMonthDate_str() {
		return registerMonthDate_str;
	}

	public void setRegisterMonthDate_str(String registerMonthDate_str) {
		this.registerMonthDate_str = registerMonthDate_str;
	}

}
