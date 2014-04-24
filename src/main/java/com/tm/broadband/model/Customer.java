package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.ChangePasswordValidatedMark;
import com.tm.broadband.validator.mark.CustomerForgottenPasswordValidatedMark;
import com.tm.broadband.validator.mark.CustomerLoginValidatedMark;
import com.tm.broadband.validator.mark.CustomerOrganizationValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

/**
 * customer information, mapping tm_customer
 * 
 * @author Cook1fan
 * 
 */
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	@NotEmpty(groups = { CustomerLoginValidatedMark.class, CustomerForgottenPasswordValidatedMark.class})
	@Length(min = 6, max = 40, groups = { CustomerLoginValidatedMark.class, CustomerForgottenPasswordValidatedMark.class})
	private String login_name;
	@NotEmpty(groups = { CustomerLoginValidatedMark.class, ChangePasswordValidatedMark.class})
	@Length(min = 6, max = 20, groups = { CustomerLoginValidatedMark.class, ChangePasswordValidatedMark.class})
	private String password;
	private String user_name;
	@NotEmpty(groups = { CustomerValidatedMark.class })
	@Length(min = 1, max = 20, groups = { CustomerValidatedMark.class })
	private String first_name;
	@NotEmpty(groups = { CustomerValidatedMark.class })
	@Length(min = 1, max = 20, groups = { CustomerValidatedMark.class })
	private String last_name;
	@NotEmpty(groups = { CustomerValidatedMark.class, CustomerOrganizationValidatedMark.class })
	@Length(min = 1, max = 500, groups = { CustomerValidatedMark.class, CustomerOrganizationValidatedMark.class })
	private String address;
	@NotEmpty(groups = { CustomerValidatedMark.class, CustomerOrganizationValidatedMark.class})
	@Email(groups = { CustomerValidatedMark.class, CustomerOrganizationValidatedMark.class})
	@Length(min = 1, max = 40, groups = { CustomerValidatedMark.class, CustomerOrganizationValidatedMark.class})
	private String email;
	//@Length(min = 1, max = 20, groups = { CustomerValidatedMark.class })
	private String phone;
	@NotEmpty(groups = { CustomerValidatedMark.class, CustomerOrganizationValidatedMark.class})
	@Length(min = 10, max = 11, groups = { CustomerValidatedMark.class, CustomerOrganizationValidatedMark.class})
	private String cellphone;
	private String status;
	private String invoice_post;
	private Date register_date;
	private Date active_date;
	private Double balance;
	private String title;
	private Date birth;
	private String driver_licence;
	private String passport;
	private String country;
	private String company_name;
	private String customer_type;
	
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private Map<String, Object> params = new HashMap<String, Object>();
	private String ck_password;
	@NotEmpty(groups = { ChangePasswordValidatedMark.class})
	@Length(min = 6, max = 20, groups = { ChangePasswordValidatedMark.class})
	private String old_password;

	private String register_date_str;
	private String active_date_str;
	private String birth_str;

	// one customer may be get more orders
	@Valid
	private Organization organization = new Organization();
	private CustomerOrder customerOrder = new CustomerOrder();
	private CustomerInvoice customerInvoice = new CustomerInvoice();
	private List<CustomerOrder> customerOrders;
	

	private String id_ck;
	private String login_name_ck;
	private String phone_ck;
	private String cellphone_ck;
	private String svlan_ck;
	private String cvlan_ck;
	private String email_ck;
	
	private String type;

	/*
	 * END RELATED PROPERTIES
	 */

	public Customer() {
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

	public String getInvoice_post() {
		return invoice_post;
	}

	public void setInvoice_post(String invoice_post) {
		this.invoice_post = invoice_post;
	}

	public Date getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}

	public Date getActive_date() {
		return active_date;
	}

	public void setActive_date(Date active_date) {
		this.active_date = active_date;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
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

	public String getRegister_date_str() {
		register_date_str = TMUtils.dateFormatYYYYMMDDHHMMSS(this.getRegister_date());
		return register_date_str;
	}

	public void setRegister_date_str(String register_date_str) {
		this.register_date_str = register_date_str;
	}

	public String getActive_date_str() {
		active_date_str = TMUtils.dateFormatYYYYMMDDHHMMSS(this.getActive_date());
		return active_date_str;
	}

	public void setActive_date_str(String active_date_str) {
		this.active_date_str = active_date_str;
	}

	public List<CustomerOrder> getCustomerOrders() {
		return customerOrders;
	}

	public void setCustomerOrders(List<CustomerOrder> customerOrders) {
		this.customerOrders = customerOrders;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public CustomerInvoice getCustomerInvoice() {
		return customerInvoice;
	}

	public void setCustomerInvoice(CustomerInvoice customerInvoice) {
		this.customerInvoice = customerInvoice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.setBirth_str(TMUtils.dateFormatYYYYMMDD(birth));
		this.birth = birth;
	}

	public String getDriver_licence() {
		return driver_licence;
	}

	public void setDriver_licence(String driver_licence) {
		this.driver_licence = driver_licence;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getBirth_str() {
		return birth_str;
	}

	public void setBirth_str(String birth_str) {
		this.birth_str = birth_str;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
