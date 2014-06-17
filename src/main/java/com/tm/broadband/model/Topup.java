package com.tm.broadband.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.validator.mark.TopupValidatedMark;

public class Topup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	@NotEmpty(groups = { TopupValidatedMark.class })
	private String topup_name;
	@NotNull(groups = { TopupValidatedMark.class })
	private Double topup_fee;
	@NotNull(groups = { TopupValidatedMark.class })
	private Long topup_data_flow;
	private String topup_status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopup_name() {
		return topup_name;
	}

	public void setTopup_name(String topup_name) {
		this.topup_name = topup_name;
	}

	public Double getTopup_fee() {
		return topup_fee;
	}

	public void setTopup_fee(Double topup_fee) {
		this.topup_fee = topup_fee;
	}

	public Long getTopup_data_flow() {
		return topup_data_flow;
	}

	public void setTopup_data_flow(Long topup_data_flow) {
		this.topup_data_flow = topup_data_flow;
	}

	public String getTopup_status() {
		return topup_status;
	}

	public void setTopup_status(String topup_status) {
		this.topup_status = topup_status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
