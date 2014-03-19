package com.tm.broadband.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerOrderValidatedMark;

public class CustomerOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private String order_serial;
	private Integer customer_id;
	private Double order_total_price;
	private Date order_create_date;
	private String order_status;
	private Date order_using_start;
	private Date next_invoice_create_date;
	private String order_type;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class })
	private String order_broadband_type;
	private String svlan;
	private String cvlan;
	private String transition_provider_name;
	private String transition_account_holder_name;
	private String transition_account_number;
	private String transition_porting_number;
	private Integer hardware_post;
	private Date order_due;
	
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	private Map<String, Object> params = new HashMap<String, Object>();
	private String order_create_date_str;
	private String order_using_start_str;
	private String next_invoice_create_date_str;
	private Customer customer;
	@Valid
	private Plan plan;

	// one order may be get more details
	private List<CustomerOrderDetail> customerOrderDetails = new ArrayList<CustomerOrderDetail>();
	private ProvisionLog tempProvsionLog;
	private List<Hardware> hardwares = new ArrayList<Hardware>();

	/*
	 * END RELATED PROPERTIES
	 */

	public CustomerOrder() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrder_serial() {
		return order_serial;
	}

	public void setOrder_serial(String order_serial) {
		this.order_serial = order_serial;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getOrder_total_price() {
		return order_total_price;
	}

	public void setOrder_total_price(Double order_total_price) {
		this.order_total_price = order_total_price;
	}

	public Date getOrder_create_date() {
		return order_create_date;
	}

	public void setOrder_create_date(Date order_create_date) {
		this.order_create_date = order_create_date;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public Date getOrder_using_start() {
		return order_using_start;
	}

	public void setOrder_using_start(Date order_using_start) {
		this.order_using_start = order_using_start;
	}

	public Date getNext_invoice_create_date() {
		return next_invoice_create_date;
	}

	public void setNext_invoice_create_date(Date next_invoice_create_date) {
		this.next_invoice_create_date = next_invoice_create_date;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOrder_broadband_type() {
		return order_broadband_type;
	}

	public void setOrder_broadband_type(String order_broadband_type) {
		this.order_broadband_type = order_broadband_type;
	}

	public String getSvlan() {
		return svlan;
	}

	public void setSvlan(String svlan) {
		this.svlan = svlan;
	}

	public String getCvlan() {
		return cvlan;
	}

	public void setCvlan(String cvlan) {
		this.cvlan = cvlan;
	}

	public String getOrder_create_date_str() {
		order_create_date_str = TMUtils.dateFormatYYYYMMDD(this
				.getOrder_create_date());
		return order_create_date_str;
	}

	public void setOrder_create_date_str(String order_create_date_str) {
		this.order_create_date_str = order_create_date_str;
	}

	public String getOrder_using_start_str() {
		order_using_start_str = TMUtils.dateFormatYYYYMMDD(this
				.getOrder_using_start());
		return order_using_start_str;
	}

	public void setOrder_using_start_str(String order_using_start_str) {
		this.order_using_start_str = order_using_start_str;
	}

	public List<CustomerOrderDetail> getCustomerOrderDetails() {
		return customerOrderDetails;
	}

	public void setCustomerOrderDetails(
			List<CustomerOrderDetail> customerOrderDetails) {
		this.customerOrderDetails = customerOrderDetails;
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

	public String getNext_invoice_create_date_str() {
		this.setNext_invoice_create_date_str(TMUtils.dateFormatYYYYMMDD(this
				.getNext_invoice_create_date()));
		return next_invoice_create_date_str;
	}

	public void setNext_invoice_create_date_str(
			String next_invoice_create_date_str) {
		this.next_invoice_create_date_str = next_invoice_create_date_str;
	}

	public ProvisionLog getTempProvsionLog() {
		return tempProvsionLog;
	}

	public void setTempProvsionLog(ProvisionLog tempProvsionLog) {
		this.tempProvsionLog = tempProvsionLog;
	}

	public String getTransition_provider_name() {
		return transition_provider_name;
	}

	public void setTransition_provider_name(String transition_provider_name) {
		this.transition_provider_name = transition_provider_name;
	}

	public String getTransition_account_holder_name() {
		return transition_account_holder_name;
	}

	public void setTransition_account_holder_name(
			String transition_account_holder_name) {
		this.transition_account_holder_name = transition_account_holder_name;
	}

	public String getTransition_account_number() {
		return transition_account_number;
	}

	public void setTransition_account_number(String transition_account_number) {
		this.transition_account_number = transition_account_number;
	}

	public String getTransition_porting_number() {
		return transition_porting_number;
	}

	public void setTransition_porting_number(String transition_porting_number) {
		this.transition_porting_number = transition_porting_number;
	}

	public List<Hardware> getHardwares() {
		return hardwares;
	}

	public void setHardwares(List<Hardware> hardwares) {
		this.hardwares = hardwares;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public Integer getHardware_post() {
		return hardware_post;
	}

	public void setHardware_post(Integer hardware_post) {
		this.hardware_post = hardware_post;
	}

	public Date getOrder_due() {
		return order_due;
	}

	public void setOrder_due(Date order_due) {
		this.order_due = order_due;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	
	
	
}
