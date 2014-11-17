package com.tm.broadband.model;

import java.io.Serializable;

public class PlanIntroductions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * BEGIN TABLE MAPPING PROPERTIES
	 */

	private String adsl_title;
	private String vdsl_title;
	private String ufb_title;
	private String adsl_content;
	private String vdsl_content;
	private String ufb_content;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * BEGIN RELATED PROPERTIES
	 */

	/*
	 * END RELATED PROPERTIES
	 */

	public String getAdsl_title() {
		return adsl_title;
	}
	public void setAdsl_title(String adsl_title) {
		this.adsl_title = adsl_title;
	}
	public String getVdsl_title() {
		return vdsl_title;
	}
	public void setVdsl_title(String vdsl_title) {
		this.vdsl_title = vdsl_title;
	}
	public String getUfb_title() {
		return ufb_title;
	}
	public void setUfb_title(String ufb_title) {
		this.ufb_title = ufb_title;
	}
	public String getAdsl_content() {
		return adsl_content;
	}
	public void setAdsl_content(String adsl_content) {
		this.adsl_content = adsl_content;
	}
	public String getVdsl_content() {
		return vdsl_content;
	}
	public void setVdsl_content(String vdsl_content) {
		this.vdsl_content = vdsl_content;
	}
	public String getUfb_content() {
		return ufb_content;
	}
	public void setUfb_content(String ufb_content) {
		this.ufb_content = ufb_content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
