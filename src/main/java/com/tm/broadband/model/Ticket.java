package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tm.broadband.util.TMUtils;

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
	private String ticket_type;
	private String publish_type;
	private String protected_viewer;
	private String not_yet_viewer;
	private String not_yet_review_comment_viewer;
	private String viewed_viewer;
	private Date create_date;
	private boolean existing_customer;
	private boolean commented;

	/*
	 * RELATED PROPERTIES
	 */
	
	private boolean mentioned;
	
	private boolean notYetReview;
	
	private boolean myTicket;
	
	private Integer[] useridArray;
	
	private String keyword;
	
	private String create_date_str;
	
	private String existing_customer_str;

	private String commented_str;
	
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

	public String getTicket_type() {
		return ticket_type;
	}

	public void setTicket_type(String ticket_type) {
		this.ticket_type = ticket_type;
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

	public String getNot_yet_review_comment_viewer() {
		return not_yet_review_comment_viewer;
	}

	public void setNot_yet_review_comment_viewer(
			String not_yet_review_comment_viewer) {
		this.not_yet_review_comment_viewer = not_yet_review_comment_viewer;
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

	public boolean isCommented() {
		return commented;
	}

	public void setCommented(boolean commented) {
		this.commented = commented;
	}

	public boolean isMentioned() {
		return mentioned;
	}

	public void setMentioned(boolean mentioned) {
		this.mentioned = mentioned;
	}

	public boolean isMyTicket() {
		return myTicket;
	}

	public void setMyTicket(boolean myTicket) {
		this.myTicket = myTicket;
	}

	public Integer[] getUseridArray() {
		return useridArray;
	}

	public void setUseridArray(Integer[] useridArray) {
		this.useridArray = useridArray;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCreate_date_str() {
		this.setCreate_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(this.getCreate_date()));
		return create_date_str;
	}

	public void setCreate_date_str(String create_date_str) {
		this.create_date_str = create_date_str;
	}

	public String getExisting_customer_str() {
		return existing_customer_str;
	}

	public void setExisting_customer_str(String existing_customer_str) {
		this.existing_customer_str = existing_customer_str;
	}

	public String getCommented_str() {
		return commented_str;
	}

	public void setCommented_str(String commented_str) {
		this.commented_str = commented_str;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public boolean isNotYetReview() {
		return notYetReview;
	}

	public void setNotYetReview(boolean notYetReview) {
		this.notYetReview = notYetReview;
	}

}
