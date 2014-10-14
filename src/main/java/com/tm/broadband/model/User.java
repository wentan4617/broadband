package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.validator.mark.UserLoginValidatedMark;

/**
 * mapping tm_user
 * 
 * @author Cook1fan
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	@NotEmpty(groups = { UserLoginValidatedMark.class })
	@Size(min = 2, max = 20, groups = { UserLoginValidatedMark.class })
	private String login_name;
	@NotEmpty(groups = { UserLoginValidatedMark.class })
	@Size(min = 2, max = 20, groups = { UserLoginValidatedMark.class })
	private String password;
	private String user_name;
	private String user_role;
	private String memo;
	private String auth;
	private String cellphone;
	private String email;
	private Boolean is_provision;
	private Double agent_commission_rates;
	private Double invite_commission;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();
	private String[] authArray;

	/*
	 * END RELATED PROPERTIES
	 */

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String[] getAuthArray() {
		return authArray;
	}

	public void setAuthArray(String[] authArray) {
		this.authArray = authArray;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIs_provision() {
		return is_provision;
	}

	public void setIs_provision(Boolean is_provision) {
		this.is_provision = is_provision;
	}

	public Double getAgent_commission_rates() {
		return agent_commission_rates;
	}

	public void setAgent_commission_rates(Double agent_commission_rates) {
		this.agent_commission_rates = agent_commission_rates;
	}

	public Double getInvite_commission() {
		return invite_commission;
	}

	public void setInvite_commission(Double invite_commission) {
		this.invite_commission = invite_commission;
	}


}
