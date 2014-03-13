package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
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
	private Integer create_by;
	private Date last_update_date;
	private Integer last_update_by;
	private String plan_topupid_array;
	private Integer plan_prepay_months;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private Map<String, Object> params = new HashMap<String, Object>();
	private Topup topup = new Topup();
	private List<Topup> topups;
	private String[] topupArray;

	/*
	 * END RELATED PROPERTIES
	 */

	public Plan() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Long getData_flow_k() {
		return data_flow_k;
	}

	public void setData_flow_k(Long data_flow_k) {
		this.data_flow_k = data_flow_k;
	}

	public String getPlan_status() {
		return plan_status;
	}

	public void setPlan_status(String plan_status) {
		this.plan_status = plan_status;
	}

	public String getPlan_type() {
		return plan_type;
	}

	public void setPlan_type(String plan_type) {
		this.plan_type = plan_type;
	}

	public String getPlan_sort() {
		return plan_sort;
	}

	public void setPlan_sort(String plan_sort) {
		this.plan_sort = plan_sort;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	

	public Date getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}

	

	public String getPlan_topupid_array() {
		return plan_topupid_array;
	}

	public void setPlan_topupid_array(String plan_topupid_array) {
		this.plan_topupid_array = plan_topupid_array;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Topup getTopup() {
		return topup;
	}

	public void setTopup(Topup topup) {
		this.topup = topup;
	}

	public List<Topup> getTopups() {
		return topups;
	}

	public void setTopups(List<Topup> topups) {
		this.topups = topups;
	}

	public String[] getTopupArray() {
		return topupArray;
	}

	public void setTopupArray(String[] topupArray) {
		this.topupArray = topupArray;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getPlan_prepay_months() {
		return plan_prepay_months;
	}

	public void setPlan_prepay_months(Integer plan_prepay_months) {
		this.plan_prepay_months = plan_prepay_months;
	}

	public Integer getCreate_by() {
		return create_by;
	}

	public void setCreate_by(Integer create_by) {
		this.create_by = create_by;
	}

	public Integer getLast_update_by() {
		return last_update_by;
	}

	public void setLast_update_by(Integer last_update_by) {
		this.last_update_by = last_update_by;
	}

}
