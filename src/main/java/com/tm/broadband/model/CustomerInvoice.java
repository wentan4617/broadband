package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

/**
 * customer invoice, mapping tm_customer_invoice
 * 
 * @author Cook1fan
 * 
 */
public class CustomerInvoice implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;

	private String invoice_serial;
	private Customer customer;
	private CustomerOrder customerOrder;
	private Date create_date;
	private Date due_date;
	private Double amount_payable;
	private Double amount_paid;
	private Double balance;
	private String status;
	private String memo;
	private String invoice_pdf_path;
	private Date paid_date;
	private String paid_type;
	private Integer last_invoice_id;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */
	
	private String create_date_str;
	private String due_date_str;
	private CustomerInvoice lastCustomerInvoice;
	private String paid_date_str;
	private String invoice_desc;
	private Integer customer_id;

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();
	
	// one invoice to many invoice details
	private List<CustomerInvoiceDetail> customerInvoiceDetails;

	/*
	 * END RELATED PROPERTIES
	 */

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public CustomerInvoice getLastCustomerInvoice() {
		return lastCustomerInvoice;
	}

	public void setLastCustomerInvoice(CustomerInvoice lastCustomerInvoice) {
		this.lastCustomerInvoice = lastCustomerInvoice;
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

	public String getInvoice_pdf_path() {
		return invoice_pdf_path;
	}

	public void setInvoice_pdf_path(String invoice_pdf_path) {
		this.invoice_pdf_path = invoice_pdf_path;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreate_date_str() {
		create_date_str = TMUtils.dateFormatYYYYMMDD(this.getCreate_date());
		return create_date_str;
	}

	public void setCreate_date_str(String create_date_str) {
		this.create_date_str = create_date_str;
	}

	public String getDue_date_str() {
		if(this.getDue_date() != null){
			this.setDue_date_str(TMUtils.dateFormatYYYYMMDD(this.getDue_date()));
		} else {
			this.setDue_date_str("");
		}
		return due_date_str;
	}

	public void setDue_date_str(String due_date_str) {
		this.due_date_str = due_date_str;
	}

	public Date getPaid_date() {
		return paid_date;
	}

	public void setPaid_date(Date paid_date) {
		this.paid_date = paid_date;
	}

	public String getPaid_type() {
		return paid_type;
	}

	public void setPaid_type(String paid_type) {
		this.paid_type = paid_type;
	}

	public Integer getLast_invoice_id() {
		return last_invoice_id;
	}

	public void setLast_invoice_id(Integer last_invoice_id) {
		this.last_invoice_id = last_invoice_id;
	}

	public String getPaid_date_str() {
		this.setPaid_date_str(TMUtils.dateFormatYYYYMMDD(this.getPaid_date()));
		return paid_date_str;
	}

	public void setPaid_date_str(String paid_date_str) {
		this.paid_date_str = paid_date_str;
	}

	public String getInvoice_desc() {
		return invoice_desc;
	}

	public void setInvoice_desc(String invoice_desc) {
		this.invoice_desc = invoice_desc;
	}

	public List<CustomerInvoiceDetail> getCustomerInvoiceDetails() {
		return customerInvoiceDetails;
	}

	public void setCustomerInvoiceDetails(
			List<CustomerInvoiceDetail> customerInvoiceDetails) {
		this.customerInvoiceDetails = customerInvoiceDetails;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

}
