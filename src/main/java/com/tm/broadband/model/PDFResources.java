package com.tm.broadband.model;

import java.io.Serializable;

public class PDFResources implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * BEGIN TABLE MAPPING PROPERTIES
	 */

	private String common_company_lg_path;
	private String invoice_company_lg_path;
	private String company_lg_customer_service_bar_path;
	private String two_dimensional_code_path;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * BEGIN RELATED PROPERTIES
	 */

	/*
	 * END RELATED PROPERTIES
	 */

	public String getCommon_company_lg_path() {
		return common_company_lg_path;
	}
	public void setCommon_company_lg_path(String common_company_lg_path) {
		this.common_company_lg_path = common_company_lg_path;
	}
	public String getInvoice_company_lg_path() {
		return invoice_company_lg_path;
	}
	public void setInvoice_company_lg_path(String invoice_company_lg_path) {
		this.invoice_company_lg_path = invoice_company_lg_path;
	}
	public String getCompany_lg_customer_service_bar_path() {
		return company_lg_customer_service_bar_path;
	}
	public void setCompany_lg_customer_service_bar_path(
			String company_lg_customer_service_bar_path) {
		this.company_lg_customer_service_bar_path = company_lg_customer_service_bar_path;
	}
	public String getTwo_dimensional_code_path() {
		return two_dimensional_code_path;
	}
	public void setTwo_dimensional_code_path(String two_dimensional_code_path) {
		this.two_dimensional_code_path = two_dimensional_code_path;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
