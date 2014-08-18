package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.ContactUsValidatedMark;

public class ContactUs implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotEmpty(groups = { ContactUsValidatedMark.class })
	@Length(min = 1, max = 50, groups = { ContactUsValidatedMark.class })
	private String first_name;
	@NotEmpty(groups = { ContactUsValidatedMark.class })
	@Length(min = 1, max = 50, groups = { ContactUsValidatedMark.class })
	private String last_name;
	@NotEmpty(groups = { ContactUsValidatedMark.class })
	@Email(groups = { ContactUsValidatedMark.class })
	private String email;
	private String phone;
	private String cellphone;
	private String status;
	@NotEmpty(groups = { ContactUsValidatedMark.class })
	private String content;
	private Date submit_date;
	private Date respond_date;
	private Integer user_id;
	
	// begin related attributes
	
	private Map<String, Object> params = new HashMap<String, Object>();

	// verification code
	@NotEmpty(groups = { ContactUsValidatedMark.class })
	@Length(min = 4, max = 4, groups = { ContactUsValidatedMark.class })
	private String code;
	
	// respond content
	private String respond_content;
	
	private String submit_date_str;
	private String respond_date_str;
	
	
	// end related attributes

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(Date submit_date) {
		this.setSubmit_date_str(TMUtils.dateFormatYYYYMMDD(submit_date));
		this.submit_date = submit_date;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getRespond_content() {
		return respond_content;
	}

	public void setRespond_content(String respond_content) {
		this.respond_content = respond_content;
	}

	public Date getRespond_date() {
		return respond_date;
	}

	public void setRespond_date(Date respond_date) {
		this.setRespond_date_str(TMUtils.dateFormatYYYYMMDD(respond_date));
		this.respond_date = respond_date;
	}

	public String getSubmit_date_str() {
		return submit_date_str;
	}

	public void setSubmit_date_str(String submit_date_str) {
		this.submit_date_str = submit_date_str;
	}

	public String getRespond_date_str() {
		return respond_date_str;
	}

	public void setRespond_date_str(String respond_date_str) {
		this.respond_date_str = respond_date_str;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

}
