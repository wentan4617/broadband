package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class CustomerOrderOnsite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	
	private Integer id;
	private Integer customer_id;
	private Integer order_id;
	private String title;
	private String first_name;
	private String last_name;
	private String mobile;
	private String email;
	private String address;
	private String plan_name;
	private Double data_flow;
	private String plan_terms;
	private String onsite_type;
	private String onsite_status;
	private Date onsite_date;
	private Double onsite_charge;
	private String customer_type;
	private String org_name;
	private String org_type;
	private String org_trading_name;
	private String org_register_no;
	private String holder_name;
	private String holder_job_title;
	private String holder_phone;
	private String holder_email;
	private String onsite_description;
	private String pdf_path;

	
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String onsite_date_str;
	
	List<CustomerOrderOnsiteDetail> coods;

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
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public Double getData_flow() {
		return data_flow;
	}
	public void setData_flow(Double data_flow) {
		this.data_flow = data_flow;
	}
	public String getPlan_terms() {
		return plan_terms;
	}
	public void setPlan_terms(String plan_terms) {
		this.plan_terms = plan_terms;
	}
	public String getOnsite_type() {
		return onsite_type;
	}
	public void setOnsite_type(String onsite_type) {
		this.onsite_type = onsite_type;
	}
	public String getOnsite_status() {
		return onsite_status;
	}
	public void setOnsite_status(String onsite_status) {
		this.onsite_status = onsite_status;
	}
	public Date getOnsite_date() {
		return onsite_date;
	}
	public void setOnsite_date(Date onsite_date) {
		this.setOnsite_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(onsite_date));
		this.onsite_date = onsite_date;
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
	public Double getOnsite_charge() {
		return onsite_charge;
	}
	public void setOnsite_charge(Double onsite_charge) {
		this.onsite_charge = onsite_charge;
	}
	public String getCustomer_type() {
		return customer_type;
	}
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	public List<CustomerOrderOnsiteDetail> getCoods() {
		return coods;
	}
	public void setCoods(List<CustomerOrderOnsiteDetail> coods) {
		this.coods = coods;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_type() {
		return org_type;
	}
	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}
	public String getOrg_trading_name() {
		return org_trading_name;
	}
	public void setOrg_trading_name(String org_trading_name) {
		this.org_trading_name = org_trading_name;
	}
	public String getOrg_register_no() {
		return org_register_no;
	}
	public void setOrg_register_no(String org_register_no) {
		this.org_register_no = org_register_no;
	}
	public String getHolder_name() {
		return holder_name;
	}
	public void setHolder_name(String holder_name) {
		this.holder_name = holder_name;
	}
	public String getHolder_job_title() {
		return holder_job_title;
	}
	public void setHolder_job_title(String holder_job_title) {
		this.holder_job_title = holder_job_title;
	}
	public String getHolder_phone() {
		return holder_phone;
	}
	public void setHolder_phone(String holder_phone) {
		this.holder_phone = holder_phone;
	}
	public String getHolder_email() {
		return holder_email;
	}
	public void setHolder_email(String holder_email) {
		this.holder_email = holder_email;
	}
	public String getOnsite_description() {
		return onsite_description;
	}
	public void setOnsite_description(String onsite_description) {
		this.onsite_description = onsite_description;
	}
	public String getPdf_path() {
		return pdf_path;
	}
	public void setPdf_path(String pdf_path) {
		this.pdf_path = pdf_path;
	}
	public String getOnsite_date_str() {
		return onsite_date_str;
	}
	public void setOnsite_date_str(String onsite_date_str) {
		this.onsite_date_str = onsite_date_str;
	}

}
