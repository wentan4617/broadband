package com.tm.broadband.model;

import java.io.Serializable;
import java.util.ArrayList;
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
	private Integer customer_id;
	private Integer order_id;
	private Date create_date;
	private Date due_date;
	private Double final_payable_amount;
	private Double amount_payable;
	private Double amount_paid;
	private Double balance;
	private String status;
	private String memo;
	private String invoice_pdf_path;
	private Date paid_date;
	private String paid_type;
	private Integer last_invoice_id;
	private String payment_status;
	private Integer original_order_id;
	private Integer original_customer_id;
	private String invoice_type;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String suspend_date_str;
	private String disconnected_date_str;

	private Map<String, Object> params = new HashMap<String, Object>();
	private String create_date_str;
	private String due_date_str;
	private CustomerInvoice lastCustomerInvoice;
	private String paid_date_str;
	private String invoice_desc;
	// one invoice to many invoice details
	private List<CustomerInvoiceDetail> customerInvoiceDetails;

	private Customer customer;
	private CustomerOrder customerOrder;
	
	private Double commission;
	private List<CustomerTransaction> cts = new ArrayList<CustomerTransaction>();

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

	public Double getFinal_payable_amount() {
		return final_payable_amount;
	}

	public void setFinal_payable_amount(Double final_payable_amount) {
		this.final_payable_amount = final_payable_amount;
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
		if (this.getDue_date() != null) {
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

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getSuspend_date_str() {
		return suspend_date_str;
	}

	public void setSuspend_date_str(String suspend_date_str) {
		this.suspend_date_str = suspend_date_str;
	}

	public String getDisconnected_date_str() {
		return disconnected_date_str;
	}

	public void setDisconnected_date_str(String disconnected_date_str) {
		this.disconnected_date_str = disconnected_date_str;
	}

	public Integer getOriginal_order_id() {
		return original_order_id;
	}

	public void setOriginal_order_id(Integer original_order_id) {
		this.original_order_id = original_order_id;
	}

	public Integer getOriginal_customer_id() {
		return original_customer_id;
	}

	public void setOriginal_customer_id(Integer original_customer_id) {
		this.original_customer_id = original_customer_id;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public List<CustomerTransaction> getCts() {
		return cts;
	}

	public void setCts(List<CustomerTransaction> cts) {
		this.cts = cts;
	}

}
