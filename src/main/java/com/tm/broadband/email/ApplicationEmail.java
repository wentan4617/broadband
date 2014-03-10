package com.tm.broadband.email;

import java.io.Serializable;

public class ApplicationEmail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** �ռ��� **/
	private String addressee;
	/** ���͸� **/
	private String cc;
	/** �ʼ����� **/
	private String subject;
	/** �ʼ����� **/
	private String content;
	// attach path
	private String attachPath;
	// attach name
	private String attachName;
	// from
	private String from;
	// reply to
	private String replyTo;

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

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

}
