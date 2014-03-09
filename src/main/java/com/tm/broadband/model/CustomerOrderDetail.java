package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CustomerOrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private CustomerOrder customerOrder;
	private String detail_name;
	private String detail_desc;
	private Double detail_price;
	private Long detail_data_flow;
	private String detail_plan_status;
	private String detail_plan_type;
	private String detail_plan_sort;
	private String detail_plan_group;
	private Double detail_plan_new_connection_fee;
	private Long detail_topup_data_flow;
	private Double detail_topup_fee;
	private String detail_plan_memo;
	private Integer detail_unit;
	private String detail_type;
	private Integer detail_is_next_pay;

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

	public CustomerOrderDetail() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}


	public Long getDetail_data_flow() {
		return detail_data_flow;
	}

	public void setDetail_data_flow(Long detail_data_flow) {
		this.detail_data_flow = detail_data_flow;
	}

	public String getDetail_plan_status() {
		return detail_plan_status;
	}

	public void setDetail_plan_status(String detail_plan_status) {
		this.detail_plan_status = detail_plan_status;
	}

	public String getDetail_plan_type() {
		return detail_plan_type;
	}

	public void setDetail_plan_type(String detail_plan_type) {
		this.detail_plan_type = detail_plan_type;
	}

	public String getDetail_plan_sort() {
		return detail_plan_sort;
	}

	public void setDetail_plan_sort(String detail_plan_sort) {
		this.detail_plan_sort = detail_plan_sort;
	}

	public String getDetail_plan_group() {
		return detail_plan_group;
	}

	public void setDetail_plan_group(String detail_plan_group) {
		this.detail_plan_group = detail_plan_group;
	}

	public Double getDetail_plan_new_connection_fee() {
		return detail_plan_new_connection_fee;
	}

	public void setDetail_plan_new_connection_fee(
			Double detail_plan_new_connection_fee) {
		this.detail_plan_new_connection_fee = detail_plan_new_connection_fee;
	}

	public Long getDetail_topup_data_flow() {
		return detail_topup_data_flow;
	}

	public void setDetail_topup_data_flow(Long detail_topup_data_flow) {
		this.detail_topup_data_flow = detail_topup_data_flow;
	}

	public Double getDetail_topup_fee() {
		return detail_topup_fee;
	}

	public void setDetail_topup_fee(Double detail_topup_fee) {
		this.detail_topup_fee = detail_topup_fee;
	}

	public String getDetail_plan_memo() {
		return detail_plan_memo;
	}

	public void setDetail_plan_memo(String detail_plan_memo) {
		this.detail_plan_memo = detail_plan_memo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getDetail_unit() {
		return detail_unit;
	}

	public void setDetail_unit(Integer detail_unit) {
		this.detail_unit = detail_unit;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getDetail_type() {
		return detail_type;
	}

	public void setDetail_type(String detail_type) {
		this.detail_type = detail_type;
	}

	public Integer getDetail_is_next_pay() {
		return detail_is_next_pay;
	}

	public void setDetail_is_next_pay(Integer detail_is_next_pay) {
		this.detail_is_next_pay = detail_is_next_pay;
	}

	public String getDetail_name() {
		return detail_name;
	}

	public void setDetail_name(String detail_name) {
		this.detail_name = detail_name;
	}

	public String getDetail_desc() {
		return detail_desc;
	}

	public void setDetail_desc(String detail_desc) {
		this.detail_desc = detail_desc;
	}

	public Double getDetail_price() {
		return detail_price;
	}

	public void setDetail_price(Double detail_price) {
		this.detail_price = detail_price;
	}

}
