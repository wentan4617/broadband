package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.validator.mark.OnlinePayByVoucherValidatedMark;

public class Voucher implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	@NotEmpty(groups = { OnlinePayByVoucherValidatedMark.class })
	@Length(min = 13, max = 13, groups = { OnlinePayByVoucherValidatedMark.class })
	private String serial_number;
	@NotEmpty(groups = { OnlinePayByVoucherValidatedMark.class })
	@Length(min = 10, max = 10, groups = { OnlinePayByVoucherValidatedMark.class })
	private String card_number;
	private Double face_value;
	private String status;
	private String comment;
	private Integer customer_id;
	private String post_to;
	private Integer order_id;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();
	private Integer index;
	private String code;

	/*
	 * END RELATED PROPERTIES
	 */

	public String getCard_number() {
		return card_number;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public Double getFace_value() {
		return face_value;
	}

	public void setFace_value(Double face_value) {
		this.face_value = face_value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public String getPost_to() {
		return post_to;
	}

	public void setPost_to(String post_to) {
		this.post_to = post_to;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
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

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

}
