package com.tm.broadband.model;

import java.io.Serializable;

public class CompanyDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	
	private String name;
	private String address;
	private String telephone;
	private String fax;
	private String domain;
	private String gst_registration_number;
	private String bank_name;
	private String bank_account_name;
	private String bank_account_number;
	private String term_contracts;
	
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

}
