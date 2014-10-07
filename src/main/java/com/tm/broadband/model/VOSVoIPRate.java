package com.tm.broadband.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VOSVoIPRate implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * BEGIN TABLE MAPPING PROPERTIES
	 */

	private String area_prefix;
	private String rate_type;
	private String area_name;
	private Double billed_rate;
	private Double billed_cycle;
	private Double billed_per_min;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * BEGIN RELATED PROPERTIES
	 */

	private String letter;
	private List<VOSVoIPRate> vrs = new ArrayList<VOSVoIPRate>();
	private Map<String, Object> params = new HashMap<String, Object>();

	/*
	 * END RELATED PROPERTIES
	 */

	public VOSVoIPRate() {
	}

	public VOSVoIPRate(String letter) {
		this.setLetter(letter);
	}

	public String getArea_prefix() {
		return area_prefix;
	}

	public void setArea_prefix(String area_prefix) {
		this.area_prefix = area_prefix;
	}

	public String getRate_type() {
		return rate_type;
	}

	public void setRate_type(String rate_type) {
		this.rate_type = rate_type;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public Double getBilled_rate() {
		return billed_rate;
	}

	public void setBilled_rate(Double billed_rate) {
		this.billed_rate = billed_rate;
	}

	public Double getBilled_cycle() {
		return billed_cycle;
	}

	public void setBilled_cycle(Double billed_cycle) {
		this.billed_cycle = billed_cycle;
	}

	public Double getBilled_per_min() {
		return billed_per_min;
	}

	public void setBilled_per_min(Double billed_per_min) {
		this.billed_per_min = billed_per_min;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public List<VOSVoIPRate> getVrs() {
		return vrs;
	}

	public void setVrs(List<VOSVoIPRate> vrs) {
		this.vrs = vrs;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
