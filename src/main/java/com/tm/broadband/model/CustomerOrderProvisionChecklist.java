package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CustomerOrderProvisionChecklist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * TABLE MAPPING PROPERTIES
	 */
	
	private Integer id;
	private Integer order_id;
	private String payment_form;
	private Boolean has_pstn;
	private Boolean has_fax;
	private Boolean has_voip;
	private Integer pstn_count;
	private Integer fax_count;
	private Integer voip_count;
	private Boolean has_alarm;
	private Boolean has_emergency;
	private Boolean has_cctv;
	private Boolean has_eftpos;
	private Boolean has_smart_bundle;
	private Boolean has_call_restrict;
	private Boolean has_call_waiting;
	private Boolean has_faxability;
	private Boolean has_call_display;
	private Boolean has_wire_maintenance;
	private Boolean has_static_ip;
	private Boolean has_dial_wrap;
	private Integer smart_bundle_count;
	private Integer call_restrict_count;
	private Integer call_waiting_count;
	private Integer faxability_count;
	private Integer call_display_count;
	private Integer wire_maintenance_count;
	private Integer static_ip_count;
	private Integer dial_wrap_count;
	private Boolean has_router_post;
	private Integer router_post_count;
	private Boolean has_svcv_lan;
	private Boolean has_service_given;
	private Boolean has_pstn_nca;
	private Integer pstn_nca_count;
	
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

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
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getPayment_form() {
		return payment_form;
	}
	public void setPayment_form(String payment_form) {
		this.payment_form = payment_form;
	}
	public Boolean getHas_pstn() {
		return has_pstn;
	}
	public void setHas_pstn(Boolean has_pstn) {
		this.has_pstn = has_pstn;
	}
	public Boolean getHas_fax() {
		return has_fax;
	}
	public void setHas_fax(Boolean has_fax) {
		this.has_fax = has_fax;
	}
	public Boolean getHas_voip() {
		return has_voip;
	}
	public void setHas_voip(Boolean has_voip) {
		this.has_voip = has_voip;
	}
	public Integer getPstn_count() {
		return pstn_count;
	}
	public void setPstn_count(Integer pstn_count) {
		this.pstn_count = pstn_count;
	}
	public Integer getFax_count() {
		return fax_count;
	}
	public void setFax_count(Integer fax_count) {
		this.fax_count = fax_count;
	}
	public Integer getVoip_count() {
		return voip_count;
	}
	public void setVoip_count(Integer voip_count) {
		this.voip_count = voip_count;
	}
	public Boolean getHas_alarm() {
		return has_alarm;
	}
	public void setHas_alarm(Boolean has_alarm) {
		this.has_alarm = has_alarm;
	}
	public Boolean getHas_emergency() {
		return has_emergency;
	}
	public void setHas_emergency(Boolean has_emergency) {
		this.has_emergency = has_emergency;
	}
	public Boolean getHas_cctv() {
		return has_cctv;
	}
	public void setHas_cctv(Boolean has_cctv) {
		this.has_cctv = has_cctv;
	}
	public Boolean getHas_eftpos() {
		return has_eftpos;
	}
	public void setHas_eftpos(Boolean has_eftpos) {
		this.has_eftpos = has_eftpos;
	}
	public Boolean getHas_smart_bundle() {
		return has_smart_bundle;
	}
	public void setHas_smart_bundle(Boolean has_smart_bundle) {
		this.has_smart_bundle = has_smart_bundle;
	}
	public Boolean getHas_call_restrict() {
		return has_call_restrict;
	}
	public void setHas_call_restrict(Boolean has_call_restrict) {
		this.has_call_restrict = has_call_restrict;
	}
	public Boolean getHas_call_waiting() {
		return has_call_waiting;
	}
	public void setHas_call_waiting(Boolean has_call_waiting) {
		this.has_call_waiting = has_call_waiting;
	}
	public Boolean getHas_faxability() {
		return has_faxability;
	}
	public void setHas_faxability(Boolean has_faxability) {
		this.has_faxability = has_faxability;
	}
	public Boolean getHas_call_display() {
		return has_call_display;
	}
	public void setHas_call_display(Boolean has_call_display) {
		this.has_call_display = has_call_display;
	}
	public Boolean getHas_wire_maintenance() {
		return has_wire_maintenance;
	}
	public void setHas_wire_maintenance(Boolean has_wire_maintenance) {
		this.has_wire_maintenance = has_wire_maintenance;
	}
	public Boolean getHas_static_ip() {
		return has_static_ip;
	}
	public void setHas_static_ip(Boolean has_static_ip) {
		this.has_static_ip = has_static_ip;
	}
	public Boolean getHas_dial_wrap() {
		return has_dial_wrap;
	}
	public void setHas_dial_wrap(Boolean has_dial_wrap) {
		this.has_dial_wrap = has_dial_wrap;
	}
	public Integer getSmart_bundle_count() {
		return smart_bundle_count;
	}
	public void setSmart_bundle_count(Integer smart_bundle_count) {
		this.smart_bundle_count = smart_bundle_count;
	}
	public Integer getCall_restrict_count() {
		return call_restrict_count;
	}
	public void setCall_restrict_count(Integer call_restrict_count) {
		this.call_restrict_count = call_restrict_count;
	}
	public Integer getCall_waiting_count() {
		return call_waiting_count;
	}
	public void setCall_waiting_count(Integer call_waiting_count) {
		this.call_waiting_count = call_waiting_count;
	}
	public Integer getFaxability_count() {
		return faxability_count;
	}
	public void setFaxability_count(Integer faxability_count) {
		this.faxability_count = faxability_count;
	}
	public Integer getCall_display_count() {
		return call_display_count;
	}
	public void setCall_display_count(Integer call_display_count) {
		this.call_display_count = call_display_count;
	}
	public Integer getWire_maintenance_count() {
		return wire_maintenance_count;
	}
	public void setWire_maintenance_count(Integer wire_maintenance_count) {
		this.wire_maintenance_count = wire_maintenance_count;
	}
	public Integer getStatic_ip_count() {
		return static_ip_count;
	}
	public void setStatic_ip_count(Integer static_ip_count) {
		this.static_ip_count = static_ip_count;
	}
	public Integer getDial_wrap_count() {
		return dial_wrap_count;
	}
	public void setDial_wrap_count(Integer dial_wrap_count) {
		this.dial_wrap_count = dial_wrap_count;
	}
	public Boolean getHas_router_post() {
		return has_router_post;
	}
	public void setHas_router_post(Boolean has_router_post) {
		this.has_router_post = has_router_post;
	}
	public Integer getRouter_post_count() {
		return router_post_count;
	}
	public void setRouter_post_count(Integer router_post_count) {
		this.router_post_count = router_post_count;
	}
	public Boolean getHas_svcv_lan() {
		return has_svcv_lan;
	}
	public void setHas_svcv_lan(Boolean has_svcv_lan) {
		this.has_svcv_lan = has_svcv_lan;
	}
	public Boolean getHas_service_given() {
		return has_service_given;
	}
	public void setHas_service_given(Boolean has_service_given) {
		this.has_service_given = has_service_given;
	}
	public Boolean getHas_pstn_nca() {
		return has_pstn_nca;
	}
	public void setHas_pstn_nca(Boolean has_pstn_nca) {
		this.has_pstn_nca = has_pstn_nca;
	}
	public Integer getPstn_nca_count() {
		return pstn_nca_count;
	}
	public void setPstn_nca_count(Integer pstn_nca_count) {
		this.pstn_nca_count = pstn_nca_count;
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


}
