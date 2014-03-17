package com.tm.broadband.model;

import java.io.Serializable;

public class Voucher implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private String voucher_code;
	private Double voucher_cost;
	private Integer voucher_used;
	private Integer voucher_customer_id;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	/*
	 * END RELATED PROPERTIES
	 */

	public Voucher() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVoucher_code() {
		return voucher_code;
	}

	public void setVoucher_code(String voucher_code) {
		this.voucher_code = voucher_code;
	}

	public Double getVoucher_cost() {
		return voucher_cost;
	}

	public void setVoucher_cost(Double voucher_cost) {
		this.voucher_cost = voucher_cost;
	}

	public Integer getVoucher_used() {
		return voucher_used;
	}

	public void setVoucher_used(Integer voucher_used) {
		this.voucher_used = voucher_used;
	}

	public Integer getVoucher_customer_id() {
		return voucher_customer_id;
	}

	public void setVoucher_customer_id(Integer voucher_customer_id) {
		this.voucher_customer_id = voucher_customer_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
