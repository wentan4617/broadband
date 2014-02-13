package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tm.broadband.util.TMUtils;

public class CustomerOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String order_serial;
	private Customer customer;

	private Double order_total_price;
	private Date order_create_date;
	private String order_status;
	private Date order_using_start;

	private String order_create_date_str;
	private String order_using_start_str;

	// one order may be get more details
	private List<CustomerOrderDetail> customerOrderDetails;

	public String getOrder_create_date_str() {
		order_create_date_str = TMUtils.dateFormatYYYYMMDDHHMMSS(this.getOrder_create_date());
		return order_create_date_str;
	}

	public void setOrder_create_date_str(String order_create_date_str) {
		this.order_create_date_str = order_create_date_str;
	}

	public String getOrder_using_start_str() {
		order_using_start_str = TMUtils.dateFormatYYYYMMDDHHMMSS(this.getOrder_using_start());
		return order_using_start_str;
	}

	public void setOrder_using_start_str(String order_using_start_str) {
		this.order_using_start_str = order_using_start_str;
	}

	public CustomerOrder() {
		// TODO Auto-generated constructor stub
	}

	public List<CustomerOrderDetail> getCustomerOrderDetails() {
		return customerOrderDetails;
	}

	public void setCustomerOrderDetails(
			List<CustomerOrderDetail> customerOrderDetails) {
		this.customerOrderDetails = customerOrderDetails;
	}

	public Date getOrder_using_start() {
		return order_using_start;
	}

	public void setOrder_using_start(Date order_using_start) {
		this.order_using_start = order_using_start;
	}

	public String getOrder_serial() {
		return order_serial;
	}

	public void setOrder_serial(String order_serial) {
		this.order_serial = order_serial;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
