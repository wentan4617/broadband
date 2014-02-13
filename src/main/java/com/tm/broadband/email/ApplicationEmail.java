package com.tm.broadband.email;

import java.io.Serializable;

public class ApplicationEmail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 收件人 **/
	private String addressee;
	/** 抄送给 **/
	private String cc;
	/** 邮件主题 **/
	private String subject;
	/** 邮件内容 **/
	private String content;

	public ApplicationEmail() {
		// TODO Auto-generated constructor stub
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

}
