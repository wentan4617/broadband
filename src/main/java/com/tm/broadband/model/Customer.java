package com.tm.broadband.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.tm.broadband.validator.mark.CustomerValidatedMark;


public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	@NotEmpty(groups = { CustomerLoginValidatedMark.class, CustomerForgottenPasswordValidatedMark.class })
	@Length(min = 6, max = 40, groups = { CustomerLoginValidatedMark.class, CustomerForgottenPasswordValidatedMark.class })
	private String login_name;
	@NotEmpty(groups = { CustomerLoginValidatedMark.class, ChangePasswordValidatedMark.class })
	@Length(min = 6, max = 20, groups = { CustomerLoginValidatedMark.class, ChangePasswordValidatedMark.class })
	private String password;
	private String md5_password;
	private String user_name;
	@NotEmpty(groups = { CustomerValidatedMark.class })
	@Length(min = 1, max = 20, groups = { CustomerValidatedMark.class })
	private String first_name;
	@NotEmpty(groups = { CustomerValidatedMark.class })
	@Length(min = 1, max = 20, groups = { CustomerValidatedMark.class })
	private String last_name;
	@NotEmpty(groups = { CustomerValidatedMark.class })
	@Length(min = 1, max = 500, groups = { CustomerValidatedMark.class })
	private String address;
	@NotEmpty(groups = { CustomerValidatedMark.class })
	@Email(groups = { CustomerValidatedMark.class })
	@Length(min = 1, max = 40, groups = { CustomerValidatedMark.class })
	private String email;
	// @Length(min = 1, max = 20, groups = { CustomerValidatedMark.class })
	private String phone;
	@NotEmpty(groups = { CustomerValidatedMark.class })
	@Length(min = 9, max = 11, groups = { CustomerValidatedMark.class })
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
	
	private String identity_type;
	@NotEmpty(groups = { CustomerValidatedMark.class })
	@Length(min = 1, max = 20, groups = { CustomerValidatedMark.class })
	private String identity_number;
	private String result;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	private Map<String, Object> params = new HashMap<String, Object>();
	private String ck_password;
	@NotEmpty(groups = { ChangePasswordValidatedMark.class })
	@Length(min = 6, max = 20, groups = { ChangePasswordValidatedMark.class })
	private String old_password;

	private String register_date_str;
	private String active_date_str;
	private String birth_str;

	// one customer may be get more orders
	@Valid
	private CustomerOrder customerOrder = new CustomerOrder();
	private CustomerInvoice customerInvoice = new CustomerInvoice();
	private List<CustomerOrder> customerOrders;
	private List<CustomerInvoice> customerInvoices;
	private Voucher voucher = new Voucher();
	private List<Voucher> vouchers = new ArrayList<Voucher>();
	private Broadband broadband;
	private InviteRates ir;
	private Plan plan;

	private String id_ck;
	private String login_name_ck;
	private String phone_ck;
	private String cellphone_ck;
	private String svlan_ck;
	private String cvlan_ck;
	private String email_ck;
	private String pstn_ck;
	private String voip_ck;
	private String order_id_ck;
	private String invoice_id_ck;
	private String broadband_asid_ck;

	// password forget email,phone
	private String type;
	private String code;

	// when create customer action option
	private String action;
	
	private boolean serviceAvailable;
	
	private String pstn;
	private String voip;
	private String order_id;
	private String invoice_id;
	private String broadband_asid;
	
	private Integer[] pstn_array;
	private Integer[] voip_array;
	
	private Integer select_plan_id;
	private String select_plan_type;
	private String select_customer_type;
	private String select_plan_group;
	private String language;
	private Integer currentOperateUserid;
	private Boolean newOrder;

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
		register_date_str = TMUtils.dateFormatYYYYMMDDHHMMSS(this
				.getRegister_date());
		return register_date_str;
	}

	public void setRegister_date_str(String register_date_str) {
		this.register_date_str = register_date_str;
	}

	public String getActive_date_str() {
		active_date_str = TMUtils.dateFormatYYYYMMDDHHMMSS(this
				.getActive_date());
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isServiceAvailable() {
		return serviceAvailable;
	}

	public void setServiceAvailable(boolean serviceAvailable) {
		this.serviceAvailable = serviceAvailable;
	}

	public String getIdentity_type() {
		return identity_type;
	}

	public void setIdentity_type(String identity_type) {
		this.identity_type = identity_type;
	}

	public String getIdentity_number() {
		return identity_number;
	}

	public void setIdentity_number(String identity_number) {
		this.identity_number = identity_number;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public List<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	public String getMd5_password() {
		return md5_password;
	}

	public void setMd5_password(String md5_password) {
		this.md5_password = md5_password;
	}

	public String getPstn() {
		return pstn;
	}

	public void setPstn(String pstn) {
		this.pstn = pstn;
	}

	public Integer[] getPstn_array() {
		return pstn_array;
	}

	public void setPstn_array(Integer[] pstn_array) {
		this.pstn_array = pstn_array;
	}

	public Integer[] getVoip_array() {
		return voip_array;
	}

	public void setVoip_array(Integer[] voip_array) {
		this.voip_array = voip_array;
	}

	public Integer getSelect_plan_id() {
		return select_plan_id;
	}

	public void setSelect_plan_id(Integer select_plan_id) {
		this.select_plan_id = select_plan_id;
	}

	public String getSelect_plan_type() {
		return select_plan_type;
	}

	public void setSelect_plan_type(String select_plan_type) {
		this.select_plan_type = select_plan_type;
	}

	public Broadband getBroadband() {
		return broadband;
	}

	public void setBroadband(Broadband broadband) {
		this.broadband = broadband;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPstn_ck() {
		return pstn_ck;
	}

	public void setPstn_ck(String pstn_ck) {
		this.pstn_ck = pstn_ck;
	}

	public String getOrder_id_ck() {
		return order_id_ck;
	}

	public void setOrder_id_ck(String order_id_ck) {
		this.order_id_ck = order_id_ck;
	}

	public String getInvoice_id_ck() {
		return invoice_id_ck;
	}

	public void setInvoice_id_ck(String invoice_id_ck) {
		this.invoice_id_ck = invoice_id_ck;
	}

	public String getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(String invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getSelect_customer_type() {
		return select_customer_type;
	}

	public void setSelect_customer_type(String select_customer_type) {
		this.select_customer_type = select_customer_type;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getBroadband_asid_ck() {
		return broadband_asid_ck;
	}

	public void setBroadband_asid_ck(String broadband_asid_ck) {
		this.broadband_asid_ck = broadband_asid_ck;
	}

	public String getBroadband_asid() {
		return broadband_asid;
	}

	public void setBroadband_asid(String broadband_asid) {
		this.broadband_asid = broadband_asid;
	}

	public String getSelect_plan_group() {
		return select_plan_group;
	}

	public void setSelect_plan_group(String select_plan_group) {
		this.select_plan_group = select_plan_group;
	}

	public InviteRates getIr() {
		return ir;
	}

	public void setIr(InviteRates ir) {
		this.ir = ir;
	}

	public Integer getCurrentOperateUserid() {
		return currentOperateUserid;
	}

	public void setCurrentOperateUserid(Integer currentOperateUserid) {
		this.currentOperateUserid = currentOperateUserid;
	}

	public String getVoip_ck() {
		return voip_ck;
	}

	public void setVoip_ck(String voip_ck) {
		this.voip_ck = voip_ck;
	}

	public String getVoip() {
		return voip;
	}

	public void setVoip(String voip) {
		this.voip = voip;
	}

	public Boolean getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(Boolean newOrder) {
		this.newOrder = newOrder;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<CustomerInvoice> getCustomerInvoices() {
		return customerInvoices;
	}

	public void setCustomerInvoices(List<CustomerInvoice> customerInvoices) {
		this.customerInvoices = customerInvoices;
	}
	
}
