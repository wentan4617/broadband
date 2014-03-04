package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * customer transaction, mapping tm_customer_transaction
 * 
 * @author Cook1fan
 * 
 */
public class CustomerTransaction implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Long id;
	private Customer customer;
	private CustomerOrder customerOrder;
	private CustomerInvoice customerInvoice = new CustomerInvoice();
	private Date transaction_date;
	private Double amount;
	private String auth_code;
	private String cardholder_name;
	private String card_name;
	private String card_number;
	private String client_info;
	private String currency_input;
	private Double amount_settlement;
	private String expiry_date;
	private String dps_txn_ref;
	private String merchant_reference;
	private String response_text;
	private String success;
	private String txnMac;
	private String transaction_type;
	private String transaction_sort;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();

	/*
	 * END RELATED PROPERTIES
	 */
	public CustomerTransaction() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CustomerInvoice getCustomerInvoice() {
		return customerInvoice;
	}

	public void setCustomerInvoice(CustomerInvoice customerInvoice) {
		this.customerInvoice = customerInvoice;
	}

	public Date getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(Date transaction_date) {
		this.transaction_date = transaction_date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public String getCardholder_name() {
		return cardholder_name;
	}

	public void setCardholder_name(String cardholder_name) {
		this.cardholder_name = cardholder_name;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getClient_info() {
		return client_info;
	}

	public void setClient_info(String client_info) {
		this.client_info = client_info;
	}

	public String getCurrency_input() {
		return currency_input;
	}

	public void setCurrency_input(String currency_input) {
		this.currency_input = currency_input;
	}

	public Double getAmount_settlement() {
		return amount_settlement;
	}

	public void setAmount_settlement(Double amount_settlement) {
		this.amount_settlement = amount_settlement;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getDps_txn_ref() {
		return dps_txn_ref;
	}

	public void setDps_txn_ref(String dps_txn_ref) {
		this.dps_txn_ref = dps_txn_ref;
	}

	public String getMerchant_reference() {
		return merchant_reference;
	}

	public void setMerchant_reference(String merchant_reference) {
		this.merchant_reference = merchant_reference;
	}

	public String getResponse_text() {
		return response_text;
	}

	public void setResponse_text(String response_text) {
		this.response_text = response_text;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getTxnMac() {
		return txnMac;
	}

	public void setTxnMac(String txnMac) {
		this.txnMac = txnMac;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getTransaction_sort() {
		return transaction_sort;
	}

	public void setTransaction_sort(String transaction_sort) {
		this.transaction_sort = transaction_sort;
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

}
