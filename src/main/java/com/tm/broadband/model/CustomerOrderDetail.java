package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class CustomerOrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private Integer order_id;
	private String detail_name;
	private String detail_desc;
	private Double detail_price;
	private Long detail_data_flow;
	private String detail_plan_status;
	private String detail_plan_type;
	private String detail_plan_sort;
	private String detail_plan_group;
	private String detail_plan_class;
	private Double detail_plan_new_connection_fee;
	private Integer detail_term_period;
	private Long detail_topup_data_flow;
	private Double detail_topup_fee;
	private String detail_plan_memo;
	private Integer detail_unit;
	private Integer detail_calling_minute;
	
	private String detail_type;
	private Integer detail_is_next_pay;
	private Date detail_expired;
	private Integer is_post;
	private String hardware_comment;
	private String track_code;
	private String pstn_number;
	private Integer user_id;
	private String voip_password;
	private Date voip_assign_date;
	private Boolean is_nca;
	private Integer to_nca_by_who;


	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();
	private CustomerOrder customerOrder;
	private String detail_expired_str;
	private String voip_assign_date_str;
	private Boolean monthly; 
	
	
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

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Integer getIs_post() {
		return is_post;
	}

	public void setIs_post(Integer is_post) {
		this.is_post = is_post;
	}

	public String getHardware_comment() {
		return hardware_comment;
	}

	public void setHardware_comment(String hardware_comment) {
		this.hardware_comment = hardware_comment;
	}

	public String getTrack_code() {
		return track_code;
	}

	public void setTrack_code(String track_code) {
		this.track_code = track_code;
	}

	public String getPstn_number() {
		return pstn_number;
	}

	public void setPstn_number(String pstn_number) {
		this.pstn_number = pstn_number;
	}

	public Date getDetail_expired() {
		return detail_expired;
	}

	public void setDetail_expired(Date detail_expired) {
		this.detail_expired = detail_expired;
	}

	public String getDetail_expired_str() {
		detail_expired_str = TMUtils.dateFormatYYYYMMDD(this.getDetail_expired());
		return detail_expired_str;
	}

	public void setDetail_expired_str(String detail_expired_str) {
		this.detail_expired_str = detail_expired_str;
	}

	public Integer getDetail_term_period() {
		return detail_term_period;
	}

	public void setDetail_term_period(Integer detail_term_period) {
		this.detail_term_period = detail_term_period;
	}

	public String getDetail_plan_class() {
		return detail_plan_class;
	}

	public void setDetail_plan_class(String detail_plan_class) {
		this.detail_plan_class = detail_plan_class;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getDetail_calling_minute() {
		return detail_calling_minute;
	}

	public void setDetail_calling_minute(Integer detail_calling_minute) {
		this.detail_calling_minute = detail_calling_minute;
	}

	public String getVoip_password() {
		return voip_password;
	}

	public void setVoip_password(String voip_password) {
		this.voip_password = voip_password;
	}

	public Date getVoip_assign_date() {
		return voip_assign_date;
	}

	public void setVoip_assign_date(Date voip_assign_date) {
		this.voip_assign_date = voip_assign_date;
	}

	public String getVoip_assign_date_str() {
		return TMUtils.dateFormatYYYYMMDD(this.getVoip_assign_date());
	}

	public void setVoip_assign_date_str(String voip_assign_date_str) {
		this.voip_assign_date_str = voip_assign_date_str;
	}

	public Boolean getMonthly() {
		return monthly;
	}

	public void setMonthly(Boolean monthly) {
		this.monthly = monthly;
	}

	public Boolean getIs_nca() {
		return is_nca;
	}

	public void setIs_nca(Boolean is_nca) {
		this.is_nca = is_nca;
	}

	public Integer getTo_nca_by_who() {
		return to_nca_by_who;
	}

	public void setTo_nca_by_who(Integer to_nca_by_who) {
		this.to_nca_by_who = to_nca_by_who;
	}
	
	
	
}
