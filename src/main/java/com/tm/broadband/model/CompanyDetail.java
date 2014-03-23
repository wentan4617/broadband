package com.tm.broadband.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.validator.mark.CompanyDetailValidatedMark;

public class CompanyDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String name;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String address;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String telephone;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String fax;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String domain;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String gst_registration_number;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String bank_name;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String bank_account_name;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String bank_account_number;
	private String term_contracts;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String company_email;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String company_email_password;
	@NotEmpty(groups = { CompanyDetailValidatedMark.class})
	private String google_map_address;
	
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	/*
	 * END RELATED PROPERTIES
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getGst_registration_number() {
		return gst_registration_number;
	}

	public void setGst_registration_number(String gst_registration_number) {
		this.gst_registration_number = gst_registration_number;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_account_name() {
		return bank_account_name;
	}

	public void setBank_account_name(String bank_account_name) {
		this.bank_account_name = bank_account_name;
	}

	public String getBank_account_number() {
		return bank_account_number;
	}

	public void setBank_account_number(String bank_account_number) {
		this.bank_account_number = bank_account_number;
	}

	public String getTerm_contracts() {
		return term_contracts;
	}

	public void setTerm_contracts(String term_contracts) {
		this.term_contracts = term_contracts;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCompany_email() {
		return company_email;
	}

	public void setCompany_email(String company_email) {
		this.company_email = company_email;
	}

	public String getCompany_email_password() {
		return company_email_password;
	}

	public void setCompany_email_password(String company_email_password) {
		this.company_email_password = company_email_password;
	}

	public String getGoogle_map_address() {
		return google_map_address;
	}

	public void setGoogle_map_address(String google_map_address) {
		this.google_map_address = google_map_address;
	}

}
