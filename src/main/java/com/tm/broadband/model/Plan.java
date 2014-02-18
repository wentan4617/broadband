package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.validator.mark.PlanValidatedMark;

/**
 * mapping tm_plan
 * 
 * @author Cook1fan
 * 
 */
public class Plan implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	@NotEmpty(groups = { PlanValidatedMark.class })
	private String plan_name;
	private String plan_desc;
	private Double plan_price;
	private Long data_flow;
	private Long data_flow_k;
	private String plan_status;
	private String plan_type;
	private String plan_sort;
	private String memo;
	private String plan_group;
	private Double plan_new_connection_fee;
	private Date create_date;
	private User create_by;
	private Date last_update_date;
	private User last_update_by;

	public Plan() {

	}

	public String getPlan_group() {
		return plan_group;
	}

	public void setPlan_group(String plan_group) {
		this.plan_group = plan_group;
	}

	public Double getPlan_new_connection_fee() {
		return plan_new_connection_fee;
	}

	public void setPlan_new_connection_fee(Double plan_new_connection_fee) {
		this.plan_new_connection_fee = plan_new_connection_fee;
	}

	public Long getData_flow_k() {
		return data_flow_k;
	}

	public void setData_flow_k(Long data_flow_k) {
		this.data_flow_k = data_flow_k;
	}

	public String getPlan_sort() {
		return plan_sort;
	}

	public void setPlan_sort(String plan_sort) {
		this.plan_sort = plan_sort;
	}

	public String getPlan_type() {
		return plan_type;
	}

	public void setPlan_type(String plan_type) {
		this.plan_type = plan_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getPlan_desc() {
		return plan_desc;
	}

	public void setPlan_desc(String plan_desc) {
		this.plan_desc = plan_desc;
	}

	public Double getPlan_price() {
		return plan_price;
	}

	public void setPlan_price(Double plan_price) {
		this.plan_price = plan_price;
	}

	public Long getData_flow() {
		return data_flow;
	}

	public void setData_flow(Long data_flow) {
		this.data_flow = data_flow;
	}

	public String getPlan_status() {
		return plan_status;
	}

	public void setPlan_status(String plan_status) {
		this.plan_status = plan_status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public User getCreate_by() {
		return create_by;
	}

	public void setCreate_by(User create_by) {
		this.create_by = create_by;
	}

	public Date getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}

	public User getLast_update_by() {
		return last_update_by;
	}

	public void setLast_update_by(User last_update_by) {
		this.last_update_by = last_update_by;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
