package com.tm.broadband.model;

import java.io.Serializable;

public class CallChargeRate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String customer_type;
	private String number_type;
	private String charge_way;
	private String dial_destination;
	private Double cost_per_minute;
	private Double cost_per_second;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getNumber_type() {
		return number_type;
	}

	public void setNumber_type(String number_type) {
		this.number_type = number_type;
	}

	public String getCharge_way() {
		return charge_way;
	}

	public void setCharge_way(String charge_way) {
		this.charge_way = charge_way;
	}

	public String getDial_destination() {
		return dial_destination;
	}

	public void setDial_destination(String dial_destination) {
		this.dial_destination = dial_destination;
	}

	public Double getCost_per_minute() {
		return cost_per_minute;
	}

	public void setCost_per_minute(Double cost_per_minute) {
		this.cost_per_minute = cost_per_minute;
	}

	public Double getCost_per_second() {
		return cost_per_second;
	}

	public void setCost_per_second(Double cost_per_second) {
		this.cost_per_second = cost_per_second;
	}

}
