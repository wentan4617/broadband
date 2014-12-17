package com.tm.broadband.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.validator.mark.CustomerCreditValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

public class CustomerCredit {

	/*
	 * TABLE MAPPING PROPERTIES
	 */
	private Integer id;
	private Integer customer_id;
	private String card_type;
	@NotEmpty(groups = { CustomerCreditValidatedMark.class})
	private String holder_name;
	@NotEmpty(groups = { CustomerCreditValidatedMark.class})
	@Length(min = 16, max = 16, groups = { CustomerValidatedMark.class})
	private String card_number;
	@NotEmpty(groups = { CustomerCreditValidatedMark.class})
	private String security_code;
	private String expiry_date;
	private String order_ids;
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String expiry_month;
	private String expiry_year;
	
	
	private Customer customer = new Customer();
	private Map<String, Object> params = new HashMap<String, Object>();

	/*
	 * END RELATED PROPERTIES
	 */

	public CustomerCredit() {

	}

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

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getHolder_name() {
		return holder_name;
	}

	public void setHolder_name(String holder_name) {
		this.holder_name = holder_name;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getExpiry_month() {
		return expiry_month;
	}

	public void setExpiry_month(String expiry_month) {
		this.expiry_month = expiry_month;
	}

	public String getExpiry_year() {
		return expiry_year;
	}

	public void setExpiry_year(String expiry_year) {
		this.expiry_year = expiry_year;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getOrder_ids() {
		return order_ids;
	}

	public void setOrder_ids(String order_ids) {
		this.order_ids = order_ids;
	}

}
