package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

/**
 * customer invoice, mapping tm_customer_invoice
 * 
 * @author Cook1fan
 * 
 */
public class CustomerInvoice implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String invoice_serial;
	private Customer cusomter;
	private CustomerOrder order;
	private Date create_date;
	private Date due_date;
	private Double amount_payable;
	private Double amount_paid;
	private Double balance;
	private String status;
	private String memo;

	public CustomerInvoice() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public String getInvoice_serial() {
		return invoice_serial;
	}

	public void setInvoice_serial(String invoice_serial) {
		this.invoice_serial = invoice_serial;
	}

	public Customer getCusomter() {
		return cusomter;
	}

	public void setCusomter(Customer cusomter) {
		this.cusomter = cusomter;
	}

	public CustomerOrder getOrder() {
		return order;
	}

	public void setOrder(CustomerOrder order) {
		this.order = order;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public Double getAmount_payable() {
		return amount_payable;
	}

	public void setAmount_payable(Double amount_payable) {
		this.amount_payable = amount_payable;
	}

	public Double getAmount_paid() {
		return amount_paid;
	}

	public void setAmount_paid(Double amount_paid) {
		this.amount_paid = amount_paid;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
