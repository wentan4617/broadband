package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

public class ManualDefrayLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer user_id;
	private Integer invoice_id;
	private Integer customer_id;
	private Integer order_id;
	private Double eliminate_amount;
	private String eliminate_way;
	private Date eliminate_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
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

	public Double getEliminate_amount() {
		return eliminate_amount;
	}

	public void setEliminate_amount(Double eliminate_amount) {
		this.eliminate_amount = eliminate_amount;
	}

	public String getEliminate_way() {
		return eliminate_way;
	}

	public void setEliminate_way(String eliminate_way) {
		this.eliminate_way = eliminate_way;
	}

	public Date getEliminate_time() {
		return eliminate_time;
	}

	public void setEliminate_time(Date eliminate_time) {
		this.eliminate_time = eliminate_time;
	}

}
