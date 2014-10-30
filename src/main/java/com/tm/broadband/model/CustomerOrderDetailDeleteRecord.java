package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

public class CustomerOrderDetailDeleteRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer order_id;
	private Integer detail_id;
	private String detail_name;
	private String detail_type;
	private Integer executor_id;
	private String delete_reason;
	private Date executed_date;
	private Boolean is_recoverable;

	/*
	 * RELATED PROPERTIES
	 */
	
	private String executed_date_str;
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	/*
	 * END RELATED PROPERTIES
	 */
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getDetail_name() {
		return detail_name;
	}
	public void setDetail_name(String detail_name) {
		this.detail_name = detail_name;
	}
	public String getDetail_type() {
		return detail_type;
	}
	public void setDetail_type(String detail_type) {
		this.detail_type = detail_type;
	}
	public Integer getExecutor_id() {
		return executor_id;
	}
	public void setExecutor_id(Integer executor_id) {
		this.executor_id = executor_id;
	}
	public String getDelete_reason() {
		return delete_reason;
	}
	public void setDelete_reason(String delete_reason) {
		this.delete_reason = delete_reason;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Date getExecuted_date() {
		return executed_date;
	}
	public void setExecuted_date(Date date) {
		this.setExecuted_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(date));
		this.executed_date = date;
	}
	public String getExecuted_date_str() {
		return executed_date_str;
	}
	public void setExecuted_date_str(String executed_date_str) {
		this.executed_date_str = executed_date_str;
	}
	public Boolean getIs_recoverable() {
		return is_recoverable;
	}
	public void setIs_recoverable(Boolean is_recoverable) {
		this.is_recoverable = is_recoverable;
	}
	public Integer getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(Integer detail_id) {
		this.detail_id = detail_id;
	}


}
