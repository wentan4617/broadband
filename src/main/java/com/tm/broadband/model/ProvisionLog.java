package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

public class ProvisionLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private User user;
	private Date process_datetime;
	private String order_sort;
	private CustomerOrder order_id_customer;
	//private ChorusOrder order_id_chorus;
	private String process_way;
	private String process_memo;

	public ProvisionLog() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public CustomerOrder getOrder_id_customer() {
		return order_id_customer;
	}

	public void setOrder_id_customer(CustomerOrder order_id_customer) {
		this.order_id_customer = order_id_customer;
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

}
