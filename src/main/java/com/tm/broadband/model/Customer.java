package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerLoginValidatedMark;
import com.tm.broadband.validator.mark.CustomerOrderValidatedMark;

/**
 * customer information, mapping tm_customer
 * 
 * @author Cook1fan
 * 
 */
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class, CustomerLoginValidatedMark.class })
	@Length(min = 6, max = 20, groups = { CustomerOrderValidatedMark.class })
	
	private String login_name;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class, CustomerLoginValidatedMark.class  })
	@Length(min = 6, max = 20, groups = { CustomerOrderValidatedMark.class })
	
	private String password;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class })
	@Length(min = 6, max = 20, groups = { CustomerOrderValidatedMark.class })
	
	private String ck_password;
	private String old_password;
	private String user_name;

	@NotEmpty(groups = { CustomerOrderValidatedMark.class })
	private String first_name;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class })
	private String last_name;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class })
	private String address;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class })
	@Email(groups = { CustomerOrderValidatedMark.class })
	private String email;
	private String phone;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class })
	private String cellphone;
	private String status;
	private String invoice_post;
	private Date register_date;
	private Date active_date;
	
	private String register_date_str;
	private String active_date_str;
	private Double balance;

	// one customer may be get more orders
	private List<CustomerOrder> customerOrders;
	private String svlan;
	private String cvlan;
	private String order_broadband_type;
	
	
	private String id_ck;
	private String login_name_ck;
	private String phone_ck;
	private String cellphone_ck;
	private String svlan_ck;
	private String cvlan_ck;
	private String email_ck;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrder_broadband_type() {
		return order_broadband_type;
	}

	public void setOrder_broadband_type(String order_broadband_type) {
		this.order_broadband_type = order_broadband_type;
	}

	public String getId_ck() {
		return id_ck;
	}

	public void setId_ck(String id_ck) {
		this.id_ck = id_ck;
	}

	public String getLogin_name_ck() {
		return login_name_ck;
	}

	public void setLogin_name_ck(String login_name_ck) {
		this.login_name_ck = login_name_ck;
	}

	public String getPhone_ck() {
		return phone_ck;
	}

	public void setPhone_ck(String phone_ck) {
		this.phone_ck = phone_ck;
	}

	public String getCellphone_ck() {
		return cellphone_ck;
	}

	public void setCellphone_ck(String cellphone_ck) {
		this.cellphone_ck = cellphone_ck;
	}

	public String getSvlan_ck() {
		return svlan_ck;
	}

	public void setSvlan_ck(String svlan_ck) {
		this.svlan_ck = svlan_ck;
	}

	public String getCvlan_ck() {
		return cvlan_ck;
	}

	public void setCvlan_ck(String cvlan_ck) {
		this.cvlan_ck = cvlan_ck;
	}

	public String getEmail_ck() {
		return email_ck;
	}

	public void setEmail_ck(String email_ck) {
		this.email_ck = email_ck;
	}

	public String getSvlan() {
		return svlan;
	}

	public void setSvlan(String svlan) {
		this.svlan = svlan;
	}

	public String getCvlan() {
		return cvlan;
	}

	public void setCvlan(String cvlan) {
		this.cvlan = cvlan;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getActive_date_str() {
		active_date_str = TMUtils.dateFormatYYYYMMDDHHMMSS(this.getActive_date());
		return active_date_str;
	}

	public void setActive_date_str(String active_date_str) {
		this.active_date_str = active_date_str;
	}

	public String getRegister_date_str() {
		register_date_str = TMUtils.dateFormatYYYYMMDDHHMMSS(this.getRegister_date());
		return register_date_str;
	}

	public void setRegister_date_str(String register_date_str) {
		this.register_date_str = register_date_str;
	}

	public Customer() {
	}

	public List<CustomerOrder> getCustomerOrders() {
		return customerOrders;
	}

	public void setCustomerOrders(List<CustomerOrder> customerOrders) {
		this.customerOrders = customerOrders;
	}

	public Date getActive_date() {
		return active_date;
	}

	public void setActive_date(Date active_date) {
		this.active_date = active_date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInvoice_post() {
		return invoice_post;
	}

	public void setInvoice_post(String invoice_post) {
		this.invoice_post = invoice_post;
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

	public String getCk_password() {
		return ck_password;
	}

	public void setCk_password(String ck_password) {
		this.ck_password = ck_password;
	}

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Date getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}

}
