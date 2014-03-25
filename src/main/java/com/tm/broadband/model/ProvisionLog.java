package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProvisionLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private Integer user_id;
	private Date process_datetime;
	private String order_sort;
	private Integer order_id_customer;
	private Integer order_id_chorus;
	private String process_way;
	private String process_memo;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();
	private User user;
	private CustomerOrder customerOrder;

	// private ChorusOrder chorusOrder;

	/*
	 * END RELATED PROPERTIES
	 */

	public ProvisionLog() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getProcess_datetime() {
		return process_datetime;
	}

	public void setProcess_datetime(Date process_datetime) {
		this.process_datetime = process_datetime;
	}

	public String getOrder_sort() {
		return order_sort;
	}

	public void setOrder_sort(String order_sort) {
		this.order_sort = order_sort;
	}

	public String getProcess_way() {
		return process_way;
	}

	public void setProcess_way(String process_way) {
		this.process_way = process_way;
	}

	public String getProcess_memo() {
		return process_memo;
	}

	public void setProcess_memo(String process_memo) {
		this.process_memo = process_memo;
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

	public Integer getOrder_id_customer() {
		return order_id_customer;
	}

	public void setOrder_id_customer(Integer order_id_customer) {
		this.order_id_customer = order_id_customer;
	}

	public Integer getOrder_id_chorus() {
		return order_id_chorus;
	}

	public void setOrder_id_chorus(Integer order_id_chorus) {
		this.order_id_chorus = order_id_chorus;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

}
