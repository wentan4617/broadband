package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer customer_id;
	private Integer user_id;
	private String cellphone;
	private String email;
	private String first_name;
	private String last_name;
	private String description;
	private String publish_type;
	private String protected_viewer;
	private String not_yet_viewer;
	private String viewed_viewer;
	private Date create_date;
	private boolean existing_customer;

	/*
	 * RELATED PROPERTIES
	 */
	
	private boolean mentioned;
	
	private String[] useridArray;
	
	private String keyword;
	
	/*
	 * END RELATED PROPERTIES
	 */



	public boolean isMentioned() {
		return mentioned;
	}

	public void setMentioned(boolean mentioned) {
		this.mentioned = mentioned;
	}

	public String[] getUseridArray() {
		return useridArray;
	}

	public void setUseridArray(String[] useridArray) {
		this.useridArray = useridArray;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublish_type() {
		return publish_type;
	}

	public void setPublish_type(String publish_type) {
		this.publish_type = publish_type;
	}

	public String getProtected_viewer() {
		return protected_viewer;
	}

	public void setProtected_viewer(String protected_viewer) {
		this.protected_viewer = protected_viewer;
	}

	public String getNot_yet_viewer() {
		return not_yet_viewer;
	}

	public void setNot_yet_viewer(String not_yet_viewer) {
		this.not_yet_viewer = not_yet_viewer;
	}

	public String getViewed_viewer() {
		return viewed_viewer;
	}

	public void setViewed_viewer(String viewed_viewer) {
		this.viewed_viewer = viewed_viewer;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public boolean isExisting_customer() {
		return existing_customer;
	}

	public void setExisting_customer(boolean existing_customer) {
		this.existing_customer = existing_customer;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
