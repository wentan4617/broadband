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
	private String detail_plan_name;
	private String detail_plan_desc;
	private Double detail_plan_price;
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

	public String getDetail_plan_name() {
		return detail_plan_name;
	}

	public void setDetail_plan_name(String detail_plan_name) {
		this.detail_plan_name = detail_plan_name;
	}

	public String getDetail_plan_desc() {
		return detail_plan_desc;
	}

	public void setDetail_plan_desc(String detail_plan_desc) {
		this.detail_plan_desc = detail_plan_desc;
	}

	public Double getDetail_plan_price() {
		return detail_plan_price;
	}

	public void setDetail_plan_price(Double detail_plan_price) {
		this.detail_plan_price = detail_plan_price;
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

}
