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
	
	private int id;
	private String invoice_serial;
	private Customer cusomter;
	private CustomerOrder order;
	private Date create_date;
	private Double amount_payable;
	private Double amount_paid;
	private Double balance;
	private String status;
	private String memo;

	public CustomerInvoice() {
	}

}
