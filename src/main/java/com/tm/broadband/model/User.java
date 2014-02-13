package com.tm.broadband.model;

import java.io.Serializable;

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

	private int id;
	@NotEmpty(groups = { UserLoginValidatedMark.class })
	private String login_name;
	@NotEmpty(groups = { UserLoginValidatedMark.class })
	private String password;
	private String user_name;
	private String user_role;
	private String memo;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

}
