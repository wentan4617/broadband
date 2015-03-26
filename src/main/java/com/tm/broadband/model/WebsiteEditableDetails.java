package com.tm.broadband.model;

import java.io.Serializable;

public class WebsiteEditableDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * BEGIN TABLE MAPPING PROPERTIES
	 */

	private String company_name;
	private String company_name_ltd;
	private String company_hot_line_no;
	private String company_hot_line_no_alphabet;
	private String company_address;
	private String company_email;
	private String website_year;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * BEGIN RELATED PROPERTIES
	 */

	/*
	 * END RELATED PROPERTIES
	 */

	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_name_ltd() {
		return company_name_ltd;
	}
	public void setCompany_name_ltd(String company_name_ltd) {
		this.company_name_ltd = company_name_ltd;
	}
	public String getCompany_hot_line_no() {
		return company_hot_line_no;
	}
	public void setCompany_hot_line_no(String company_hot_line_no) {
		this.company_hot_line_no = company_hot_line_no;
	}
	public String getCompany_hot_line_no_alphabet() {
		return company_hot_line_no_alphabet;
	}
	public void setCompany_hot_line_no_alphabet(String company_hot_line_no_alphabet) {
		this.company_hot_line_no_alphabet = company_hot_line_no_alphabet;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getCompany_email() {
		return company_email;
	}
	public void setCompany_email(String company_email) {
		this.company_email = company_email;
	}
	public String getWebsite_year() {
		return website_year;
	}
	public void setWebsite_year(String website_year) {
		this.website_year = website_year;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
