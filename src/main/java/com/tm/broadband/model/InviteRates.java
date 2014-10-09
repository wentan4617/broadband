package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.validator.mark.PromotionCodeValidatedMark;

public class InviteRates implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Double inviter_user_rate;
	private Double inviter_customer_rate;
	private Double invitee_rate;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();
	private boolean is_user = false;
	private boolean is_customer = false;
	@NotEmpty(groups = { PromotionCodeValidatedMark.class })
	@Length(max = 10, groups = { PromotionCodeValidatedMark.class })
	private String promotion_code;
	
	/*
	 * END RELATED PROPERTIES
	 */

	public Double getInviter_user_rate() {
		return inviter_user_rate;
	}

	public void setInviter_user_rate(Double inviter_user_rate) {
		this.inviter_user_rate = inviter_user_rate;
	}

	public Double getInviter_customer_rate() {
		return inviter_customer_rate;
	}

	public void setInviter_customer_rate(Double inviter_customer_rate) {
		this.inviter_customer_rate = inviter_customer_rate;
	}

	public Double getInvitee_rate() {
		return invitee_rate;
	}

	public void setInvitee_rate(Double invitee_rate) {
		this.invitee_rate = invitee_rate;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isIs_user() {
		return is_user;
	}

	public void setIs_user(boolean is_user) {
		this.is_user = is_user;
	}

	public boolean isIs_customer() {
		return is_customer;
	}

	public void setIs_customer(boolean is_customer) {
		this.is_customer = is_customer;
	}

	public String getPromotion_code() {
		return promotion_code;
	}

	public void setPromotion_code(String promotion_code) {
		this.promotion_code = promotion_code;
	}

}
