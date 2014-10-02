package com.tm.broadband.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.tm.broadband.validator.mark.HardwareValidatedMark;

public class Hardware implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	private Integer id;
	@NotEmpty(groups = { HardwareValidatedMark.class })
	private String hardware_name;
	// @NotEmpty(groups = { HardwareValidatedMark.class})
	private String hardware_desc;
	// @NotEmpty(groups = { HardwareValidatedMark.class })
	private String hardware_class;
	@NotEmpty(groups = { HardwareValidatedMark.class })
	private String hardware_type;
	@NotEmpty(groups = { HardwareValidatedMark.class })
	private String hardware_status;
	@NotNull(groups = { HardwareValidatedMark.class })
	private Double hardware_price;
	@NotNull(groups = { HardwareValidatedMark.class })
	private Double hardware_cost;
	private String img1;
	private String img2;
	private String img3;
	private Boolean router_adsl;
	private Boolean router_vdsl;
	private Boolean router_ufb;
	private Boolean support_pstn;
	private Boolean support_voip;

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

	public void setHardware_name(String hardware_name) {
		this.hardware_name = hardware_name;
	}

	public String getHardware_name() {
		return hardware_name;
	}

	public String getHardware_desc() {
		return hardware_desc;
	}

	public void setHardware_desc(String hardware_desc) {
		this.hardware_desc = hardware_desc;
	}

	public String getHardware_type() {
		return hardware_type;
	}

	public void setHardware_type(String hardware_type) {
		this.hardware_type = hardware_type;
	}

	public String getHardware_status() {
		return hardware_status;
	}

	public void setHardware_status(String hardware_status) {
		this.hardware_status = hardware_status;
	}

	public Double getHardware_price() {
		return hardware_price;
	}

	public void setHardware_price(Double hardware_price) {
		this.hardware_price = hardware_price;
	}

	public Double getHardware_cost() {
		return hardware_cost;
	}

	public void setHardware_cost(Double hardware_cost) {
		this.hardware_cost = hardware_cost;
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

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getHardware_class() {
		return hardware_class;
	}

	public void setHardware_class(String hardware_class) {
		this.hardware_class = hardware_class;
	}

	public Boolean getRouter_adsl() {
		return router_adsl;
	}

	public void setRouter_adsl(Boolean router_adsl) {
		this.router_adsl = router_adsl;
	}

	public Boolean getRouter_vdsl() {
		return router_vdsl;
	}

	public void setRouter_vdsl(Boolean router_vdsl) {
		this.router_vdsl = router_vdsl;
	}

	public Boolean getRouter_ufb() {
		return router_ufb;
	}

	public void setRouter_ufb(Boolean router_ufb) {
		this.router_ufb = router_ufb;
	}

	public Boolean getSupport_pstn() {
		return support_pstn;
	}

	public void setSupport_pstn(Boolean support_pstn) {
		this.support_pstn = support_pstn;
	}

	public Boolean getSupport_voip() {
		return support_voip;
	}

	public void setSupport_voip(Boolean support_voip) {
		this.support_voip = support_voip;
	}

}
