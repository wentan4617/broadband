package com.tm.broadband.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerOrderValidatedMark;
import com.tm.broadband.validator.mark.CustomerOrganizationValidatedMark;
import com.tm.broadband.validator.mark.TransitionCustomerOrderValidatedMark;

public class CustomerOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	private String order_serial;
	private Integer customer_id;
	private Double order_total_price;
	private Date order_create_date;
	private String order_status;
	private Date order_using_start;
	private Date next_invoice_create_date;
	private Date next_invoice_create_date_flag;
	private String order_type;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class })
	private String order_broadband_type;
	private String svlan;
	private String cvlan;
	@NotEmpty(groups = { TransitionCustomerOrderValidatedMark.class })
	@Length(min = 1, max = 20, groups = { TransitionCustomerOrderValidatedMark.class })
	private String transition_provider_name;
	@NotEmpty(groups = { TransitionCustomerOrderValidatedMark.class })
	@Length(min = 1, max = 20, groups = { TransitionCustomerOrderValidatedMark.class })
	private String transition_account_holder_name;
	@NotEmpty(groups = { TransitionCustomerOrderValidatedMark.class })
	@Length(min = 1, max = 20, groups = { TransitionCustomerOrderValidatedMark.class })
	private String transition_account_number;
	@NotEmpty(groups = { TransitionCustomerOrderValidatedMark.class })
	@Length(min = 1, max = 20, groups = { TransitionCustomerOrderValidatedMark.class })
	private String transition_porting_number;
	private Integer hardware_post;
	private Date order_due;
	private Integer pstn_count;
	private Double pstn_rental_amount;
	private Integer term_period;
	private String pppoe_loginname;
	private String pppoe_password;
	private Integer sale_id;
	private String order_pdf_path;
	private String credit_pdf_path;
	private String signature;
	private String optional_request;
	private String broadband_asid;
	private Date rfs_date;
	private String previous_provider_invoice;
	private Integer user_id;
	private String ddpay_pdf_path;
	private Boolean is_ddpay;
	private String ordering_form_pdf_path;
	private String receipt_pdf_path;
	private Date disconnected_date;
	private String connection_date;
	private String contract;
	private Integer inviter_customer_id;
	private Integer inviter_user_id;
	private Double inviter_rate;
	private Double invitee_rate;
	
	private String title;
	private String first_name;
	private String last_name;

	@NotEmpty(groups = { CustomerOrganizationValidatedMark.class })
	@Length(min = 1, max = 49, groups = { CustomerOrganizationValidatedMark.class })
	private String org_name;
	private String org_type;
	private String org_trading_name;
	private String org_register_no;
	private Date org_incoporate_date;
	private Integer org_trading_months;
	private String holder_name;
	private String holder_job_title;
	private String holder_phone;
	private String holder_email;
	
	private String xero_invoice_status;
	private Boolean is_send_xero_invoice;

	private String address;
	private String mobile;
	private String phone;
	private String email;
	private String customer_type;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private String pstn;
	private String voip;
	private Integer invoice_id;
	
	private Map<String, Object> params = new HashMap<String, Object>();
	private String order_create_date_str;
	private String order_create_date_yyyymmddhhmmss;
	private String order_using_start_str;
	private String next_invoice_create_date_str;
	private String next_invoice_create_date_flag_str;
	private String rfs_date_str;
	private String order_due_str;
	private String disconnected_date_str;
	private Customer customer;
	@Valid
	private Plan plan;
	
	private String org_incoporate_date_str;

	// one order may be get more details
	private List<CustomerOrderDetail> customerOrderDetails = new ArrayList<CustomerOrderDetail>();
	private List<CustomerOrderDetail> addons;
	private List<CustomerOrderDetail> monthly_cods = new ArrayList<CustomerOrderDetail>();
	private List<CustomerOrderDetail> oneoff_cods = new ArrayList<CustomerOrderDetail>();
	private CustomerOrderDetail cod;
	private ProvisionLog tempProvsionLog;
	private List<Hardware> hardwares = new ArrayList<Hardware>();
	private NetworkUsage usage = new NetworkUsage();
	
	private Integer prepay_months;
	private Integer discount_price;
	private String _transition_provider_name;
	private Boolean promotion;
	private Integer hardware_id_selected;

	/*
	 * END RELATED PROPERTIES
	 */

	public String get_transition_provider_name() {
		return _transition_provider_name;
	}

	public void set_transition_provider_name(String _transition_provider_name) {
		this._transition_provider_name = _transition_provider_name;
	}

	public CustomerOrder() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrder_serial() {
		return order_serial;
	}

	public void setOrder_serial(String order_serial) {
		this.order_serial = order_serial;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getOrder_total_price() {
		return order_total_price;
	}

	public void setOrder_total_price(Double order_total_price) {
		this.order_total_price = order_total_price;
	}

	public Date getOrder_create_date() {
		return order_create_date;
	}

	public void setOrder_create_date(Date order_create_date) {
		this.setOrder_create_date_str(TMUtils.dateFormatYYYYMMDDHHMMSS(order_create_date));
		this.order_create_date = order_create_date;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public Date getOrder_using_start() {
		return order_using_start;
	}

	public void setOrder_using_start(Date order_using_start) {
		this.order_using_start = order_using_start;
	}

	public Date getNext_invoice_create_date() {
		return next_invoice_create_date;
	}

	public void setNext_invoice_create_date(Date next_invoice_create_date) {
		this.next_invoice_create_date = next_invoice_create_date;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOrder_broadband_type() {
		return order_broadband_type;
	}

	public void setOrder_broadband_type(String order_broadband_type) {
		this.order_broadband_type = order_broadband_type;
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

	public String getOrder_create_date_str() {
		return order_create_date_str;
	}

	public void setOrder_create_date_str(String order_create_date_str) {
		this.order_create_date_str = order_create_date_str;
	}

	public String getOrder_using_start_str() {
		if (this.getOrder_using_start() != null) {
			this.setOrder_using_start_str(TMUtils.dateFormatYYYYMMDD(this.getOrder_using_start()));
		}
		return order_using_start_str;
	}

	public void setOrder_using_start_str(String order_using_start_str) {
		this.order_using_start_str = order_using_start_str;
	}

	public List<CustomerOrderDetail> getCustomerOrderDetails() {
		return customerOrderDetails;
	}

	public void setCustomerOrderDetails(List<CustomerOrderDetail> customerOrderDetails) {
		this.customerOrderDetails = customerOrderDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getNext_invoice_create_date_str() {
		this.setNext_invoice_create_date_str(TMUtils.dateFormatYYYYMMDD(this.getNext_invoice_create_date()));
		return next_invoice_create_date_str;
	}

	public void setNext_invoice_create_date_str(String next_invoice_create_date_str) {
		this.next_invoice_create_date_str = next_invoice_create_date_str;
	}

	public ProvisionLog getTempProvsionLog() {
		return tempProvsionLog;
	}

	public void setTempProvsionLog(ProvisionLog tempProvsionLog) {
		this.tempProvsionLog = tempProvsionLog;
	}

	public String getTransition_provider_name() {
		return transition_provider_name;
	}

	public void setTransition_provider_name(String transition_provider_name) {
		this.transition_provider_name = transition_provider_name;
	}

	public String getTransition_account_holder_name() {
		return transition_account_holder_name;
	}

	public void setTransition_account_holder_name(
			String transition_account_holder_name) {
		this.transition_account_holder_name = transition_account_holder_name;
	}

	public String getTransition_account_number() {
		return transition_account_number;
	}

	public void setTransition_account_number(String transition_account_number) {
		this.transition_account_number = transition_account_number;
	}

	public String getTransition_porting_number() {
		return transition_porting_number;
	}

	public void setTransition_porting_number(String transition_porting_number) {
		this.transition_porting_number = transition_porting_number;
	}

	public List<Hardware> getHardwares() {
		return hardwares;
	}

	public void setHardwares(List<Hardware> hardwares) {
		this.hardwares = hardwares;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public Integer getHardware_post() {
		return hardware_post;
	}

	public void setHardware_post(Integer hardware_post) {
		this.hardware_post = hardware_post;
	}

	public Date getOrder_due() {
		return order_due;
	}

	public void setOrder_due(Date order_due) {
		this.order_due = order_due;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Integer getPstn_count() {
		return pstn_count;
	}

	public void setPstn_count(Integer pstn_count) {
		this.pstn_count = pstn_count;
	}

	public Double getPstn_rental_amount() {
		return pstn_rental_amount;
	}

	public void setPstn_rental_amount(Double pstn_rental_amount) {
		this.pstn_rental_amount = pstn_rental_amount;
	}

	public Integer getTerm_period() {
		return term_period;
	}

	public void setTerm_period(Integer term_period) {
		this.term_period = term_period;
	}

	public String getPppoe_loginname() {
		return pppoe_loginname;
	}

	public void setPppoe_loginname(String pppoe_loginname) {
		this.pppoe_loginname = pppoe_loginname;
	}

	public String getPppoe_password() {
		return pppoe_password;
	}

	public void setPppoe_password(String pppoe_password) {
		this.pppoe_password = pppoe_password;
	}

	public String getOrder_due_str() {
		if (this.getOrder_due() != null) {
			this.setOrder_due_str(TMUtils.dateFormatYYYYMMDD(this
					.getOrder_due()));
		}
		return order_due_str;
	}

	public void setOrder_due_str(String order_due_str) {
		this.order_due_str = order_due_str;
	}

	public Integer getSale_id() {
		return sale_id;
	}

	public void setSale_id(Integer sale_id) {
		this.sale_id = sale_id;
	}

	public String getOrder_pdf_path() {
		return order_pdf_path;
	}

	public void setOrder_pdf_path(String order_pdf_path) {
		this.order_pdf_path = order_pdf_path;
	}

	public String getCredit_pdf_path() {
		return credit_pdf_path;
	}

	public void setCredit_pdf_path(String credit_pdf_path) {
		this.credit_pdf_path = credit_pdf_path;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getOptional_request() {
		return optional_request;
	}

	public void setOptional_request(String optional_request) {
		this.optional_request = optional_request;
	}

	public String getBroadband_asid() {
		return broadband_asid;
	}

	public void setBroadband_asid(String broadband_asid) {
		this.broadband_asid = broadband_asid;
	}

	public Date getRfs_date() {
		return rfs_date;
	}

	public void setRfs_date(Date rfs_date) {
		this.rfs_date = rfs_date;
	}

	public String getRfs_date_str() {
		if (this.getRfs_date() != null) {
			this.setRfs_date_str(TMUtils.dateFormatYYYYMMDD(this.getRfs_date()));
		}
		return rfs_date_str;
	}

	public void setRfs_date_str(String rfs_date_str) {
		this.rfs_date_str = rfs_date_str;
	}

	public String getPrevious_provider_invoice() {
		return previous_provider_invoice;
	}

	public void setPrevious_provider_invoice(String previous_provider_invoice) {
		this.previous_provider_invoice = previous_provider_invoice;
	}

	public CustomerOrderDetail getCod() {
		return cod;
	}

	public void setCod(CustomerOrderDetail cod) {
		this.cod = cod;
	}

	public NetworkUsage getUsage() {
		return usage;
	}

	public void setUsage(NetworkUsage usage) {
		this.usage = usage;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getDdpay_pdf_path() {
		return ddpay_pdf_path;
	}

	public void setDdpay_pdf_path(String ddpay_pdf_path) {
		this.ddpay_pdf_path = ddpay_pdf_path;
	}

	public Boolean getIs_ddpay() {
		return is_ddpay;
	}

	public void setIs_ddpay(Boolean is_ddpay) {
		this.is_ddpay = is_ddpay;
	}

	public Date getNext_invoice_create_date_flag() {
		return next_invoice_create_date_flag;
	}

	public void setNext_invoice_create_date_flag(Date next_invoice_create_date_flag) {
		this.next_invoice_create_date_flag = next_invoice_create_date_flag;
	}

	public String getNext_invoice_create_date_flag_str() {
		return next_invoice_create_date_flag_str;
	}

	public void setNext_invoice_create_date_flag_str(
			String next_invoice_create_date_flag_str) {
		this.next_invoice_create_date_flag_str = next_invoice_create_date_flag_str;
	}

	public String getOrdering_form_pdf_path() {
		return ordering_form_pdf_path;
	}

	public void setOrdering_form_pdf_path(String ordering_form_pdf_path) {
		this.ordering_form_pdf_path = ordering_form_pdf_path;
	}

	public String getReceipt_pdf_path() {
		return receipt_pdf_path;
	}

	public void setReceipt_pdf_path(String receipt_pdf_path) {
		this.receipt_pdf_path = receipt_pdf_path;
	}

	public Date getDisconnected_date() {
		return disconnected_date;
	}

	public void setDisconnected_date(Date disconnected_date) {
		this.disconnected_date = disconnected_date;
	}

	public String getDisconnected_date_str() {
		this.setDisconnected_date_str(TMUtils.dateFormatYYYYMMDD(this.getDisconnected_date()));
		return disconnected_date_str;
	}

	public void setDisconnected_date_str(String disconnected_date_str) {
		this.disconnected_date_str = disconnected_date_str;
	}

	public Integer getPrepay_months() {
		return prepay_months;
	}

	public void setPrepay_months(Integer prepay_months) {
		this.prepay_months = prepay_months;
	}

	public String getConnection_date() {
		return connection_date;
	}

	public void setConnection_date(String connection_date) {
		this.connection_date = connection_date;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Integer getDiscount_price() {
		return discount_price;
	}

	public void setDiscount_price(Integer discount_price) {
		this.discount_price = discount_price;
	}

	public Boolean getPromotion() {
		return promotion;
	}

	public void setPromotion(Boolean promotion) {
		this.promotion = promotion;
	}

	public Integer getHardware_id_selected() {
		return hardware_id_selected;
	}

	public void setHardware_id_selected(Integer hardware_id_selected) {
		this.hardware_id_selected = hardware_id_selected;
	}

	public Integer getInviter_customer_id() {
		return inviter_customer_id;
	}

	public void setInviter_customer_id(Integer inviter_customer_id) {
		this.inviter_customer_id = inviter_customer_id;
	}

	public Integer getInviter_user_id() {
		return inviter_user_id;
	}

	public void setInviter_user_id(Integer inviter_user_id) {
		this.inviter_user_id = inviter_user_id;
	}

	public Double getInviter_rate() {
		return inviter_rate;
	}

	public void setInviter_rate(Double inviter_rate) {
		this.inviter_rate = inviter_rate;
	}

	public Double getInvitee_rate() {
		return invitee_rate;
	}

	public void setInvitee_rate(Double invitee_rate) {
		this.invitee_rate = invitee_rate;
	}

	public List<CustomerOrderDetail> getAddons() {
		return addons;
	}

	public void setAddons(List<CustomerOrderDetail> addons) {
		this.addons = addons;
	}

	public List<CustomerOrderDetail> getMonthly_cods() {
		return monthly_cods;
	}

	public void setMonthly_cods(List<CustomerOrderDetail> monthly_cods) {
		this.monthly_cods = monthly_cods;
	}

	public List<CustomerOrderDetail> getOneoff_cods() {
		return oneoff_cods;
	}

	public void setOneoff_cods(List<CustomerOrderDetail> oneoff_cods) {
		this.oneoff_cods = oneoff_cods;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getOrder_create_date_yyyymmddhhmmss() {
		order_create_date_yyyymmddhhmmss = TMUtils.dateFormatYYYYMMDDHHMMSS(this.getOrder_create_date());
		return order_create_date_yyyymmddhhmmss;
	}

	public void setOrder_create_date_yyyymmddhhmmss(
			String order_create_date_yyyymmddhhmmss) {
		this.order_create_date_yyyymmddhhmmss = order_create_date_yyyymmddhhmmss;
	}

	public String getXero_invoice_status() {
		return xero_invoice_status;
	}

	public void setXero_invoice_status(String xero_invoice_status) {
		this.xero_invoice_status = xero_invoice_status;
	}

	public Boolean getIs_send_xero_invoice() {
		return is_send_xero_invoice;
	}

	public void setIs_send_xero_invoice(Boolean is_send_xero_invoice) {
		this.is_send_xero_invoice = is_send_xero_invoice;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOrg_type() {
		return org_type;
	}

	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}

	public String getOrg_trading_name() {
		return org_trading_name;
	}

	public void setOrg_trading_name(String org_trading_name) {
		this.org_trading_name = org_trading_name;
	}

	public String getOrg_register_no() {
		return org_register_no;
	}

	public void setOrg_register_no(String org_register_no) {
		this.org_register_no = org_register_no;
	}

	public Date getOrg_incoporate_date() {
		return org_incoporate_date;
	}

	public void setOrg_incoporate_date(Date org_incoporate_date) {
		this.setOrg_incoporate_date_str(TMUtils.dateFormatYYYYMMDD(org_incoporate_date));
		this.org_incoporate_date = org_incoporate_date;
	}

	public Integer getOrg_trading_months() {
		return org_trading_months;
	}

	public void setOrg_trading_months(Integer org_trading_months) {
		this.org_trading_months = org_trading_months;
	}

	public String getHolder_name() {
		return holder_name;
	}

	public void setHolder_name(String holder_name) {
		this.holder_name = holder_name;
	}

	public String getHolder_job_title() {
		return holder_job_title;
	}

	public void setHolder_job_title(String holder_job_title) {
		this.holder_job_title = holder_job_title;
	}

	public String getHolder_phone() {
		return holder_phone;
	}

	public void setHolder_phone(String holder_phone) {
		this.holder_phone = holder_phone;
	}

	public String getHolder_email() {
		return holder_email;
	}

	public void setHolder_email(String holder_email) {
		this.holder_email = holder_email;
	}

	public String getOrg_incoporate_date_str() {
		return org_incoporate_date_str;
	}

	public void setOrg_incoporate_date_str(String org_incoporate_date_str) {
		this.org_incoporate_date_str = org_incoporate_date_str;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPstn() {
		return pstn;
	}

	public void setPstn(String pstn) {
		this.pstn = pstn;
	}

	public String getVoip() {
		return voip;
	}

	public void setVoip(String voip) {
		this.voip = voip;
	}

	public Integer getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(Integer invoice_id) {
		this.invoice_id = invoice_id;
	}

}
