package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import com.tm.broadband.validator.mark.CustomerOrderValidatedMark;
import com.tm.broadband.validator.mark.PlanValidatedMark;

/**
 * mapping tm_plan
 * 
 * @author Cook1fan
 * 
 */

public class Plan implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * TABLE MAPPING PROPERTIES
	 */

	@NotNull(groups = { CustomerOrderValidatedMark.class })
	private Integer id;
	@NotEmpty(groups = { PlanValidatedMark.class })
	private String plan_name;
	private String plan_desc;
	@NumberFormat
	@NotNull(groups = { PlanValidatedMark.class })
	private Double plan_price;
	private Double original_price;
	private Long data_flow;
	private Long data_flow_k;
	private String plan_status;
	private String plan_type;
	private String plan_sort;
	private String memo;
	@NotEmpty(groups = { CustomerOrderValidatedMark.class })
	private String plan_group;
	private String plan_class;
	@NumberFormat
	@NotNull(groups = { PlanValidatedMark.class })
	private Double plan_new_connection_fee;
	private Integer plan_prepay_months;
	private Integer pstn_count;
	private Double pstn_rental_amount;
	private Integer term_period;
	private String img1;
	private String img2;
	private String img3;
	private Boolean promotion;
	private Integer place_sort;
	@NumberFormat
	@NotNull(groups = { PlanValidatedMark.class })
	private Double jackpot_fee;
	@NumberFormat
	@NotNull(groups = { PlanValidatedMark.class })
	private Double transition_fee;
	private Integer voip_count;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */

	public Integer getVoip_count() {
		return voip_count;
	}

	public void setVoip_count(Integer voip_count) {
		this.voip_count = voip_count;
	}

	private Map<String, Object> params = new HashMap<String, Object>();
	private Topup topup = new Topup();
	private List<Topup> topups;
	private String[] topupArray;
	private Double discount_rate;

	/*
	 * END RELATED PROPERTIES
	 */

	public Plan() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getPlan_desc() {
		return plan_desc;
	}

	public void setPlan_desc(String plan_desc) {
		this.plan_desc = plan_desc;
	}

	public Double getPlan_price() {
		return plan_price;
	}

	public void setPlan_price(Double plan_price) {
		this.plan_price = plan_price;
	}

	public Long getData_flow() {
		return data_flow;
	}

	public void setData_flow(Long data_flow) {
		this.data_flow = data_flow;
	}

	public Long getData_flow_k() {
		return data_flow_k;
	}

	public void setData_flow_k(Long data_flow_k) {
		this.data_flow_k = data_flow_k;
	}

	public String getPlan_status() {
		return plan_status;
	}

	public void setPlan_status(String plan_status) {
		this.plan_status = plan_status;
	}

	public String getPlan_type() {
		return plan_type;
	}

	public void setPlan_type(String plan_type) {
		this.plan_type = plan_type;
	}

	public String getPlan_sort() {
		return plan_sort;
	}

	public void setPlan_sort(String plan_sort) {
		this.plan_sort = plan_sort;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPlan_group() {
		return plan_group;
	}

	public void setPlan_group(String plan_group) {
		this.plan_group = plan_group;
	}

	public Double getPlan_new_connection_fee() {
		return plan_new_connection_fee;
	}

	public void setPlan_new_connection_fee(Double plan_new_connection_fee) {
		this.plan_new_connection_fee = plan_new_connection_fee;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Topup getTopup() {
		return topup;
	}

	public void setTopup(Topup topup) {
		this.topup = topup;
	}

	public List<Topup> getTopups() {
		return topups;
	}

	public void setTopups(List<Topup> topups) {
		this.topups = topups;
	}

	public String[] getTopupArray() {
		return topupArray;
	}

	public void setTopupArray(String[] topupArray) {
		this.topupArray = topupArray;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getPlan_prepay_months() {
		return plan_prepay_months;
	}

	public void setPlan_prepay_months(Integer plan_prepay_months) {
		this.plan_prepay_months = plan_prepay_months;
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

	public String getPlan_class() {
		return plan_class;
	}

	public void setPlan_class(String plan_class) {
		this.plan_class = plan_class;
	}

	public Double getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(Double original_price) {
		this.original_price = original_price;
	}

	public Boolean getPromotion() {
		return promotion;
	}

	public void setPromotion(Boolean promotion) {
		this.promotion = promotion;
	}

	public Integer getPlace_sort() {
		return place_sort;
	}

	public void setPlace_sort(Integer place_sort) {
		this.place_sort = place_sort;
	}

	public Double getJackpot_fee() {
		return jackpot_fee;
	}

	public void setJackpot_fee(Double jackpot_fee) {
		this.jackpot_fee = jackpot_fee;
	}

	public Double getTransition_fee() {
		return transition_fee;
	}

	public void setTransition_fee(Double transition_fee) {
		this.transition_fee = transition_fee;
	}

	public Double getDiscount_rate() {
		return discount_rate;
	}

	public void setDiscount_rate(Double discount_rate) {
		this.discount_rate = discount_rate;
	}

	
}
