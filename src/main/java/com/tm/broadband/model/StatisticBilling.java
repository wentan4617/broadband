package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

import com.tm.broadband.util.TMUtils;

public class StatisticBilling implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date billingDate;
	private Double billingAmount;
	private String billingWeekDate_str;
	private String billingMonthDate_str;
	
	public Date getBillingDate() {
		return billingDate;
	}
	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
		this.setBillingMonthDate_str(TMUtils.dateFormatYYYYMMDD(this.getBillingDate()));
	}
	public Double getBillingAmount() {
		return billingAmount;
	}
	public void setBillingAmount(Double billingAmount) {
		this.billingAmount = billingAmount;
	}
	public String getBillingWeekDate_str() {
		return billingWeekDate_str;
	}
	public void setBillingWeekDate_str(String billingWeekDate_str) {
		this.billingWeekDate_str = billingWeekDate_str;
	}
	public String getBillingMonthDate_str() {
		return billingMonthDate_str;
	}
	public void setBillingMonthDate_str(String billingMonthDate_str) {
		this.billingMonthDate_str = billingMonthDate_str;
	}
	

}
