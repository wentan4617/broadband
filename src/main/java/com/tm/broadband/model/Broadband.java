package com.tm.broadband.model;

/**
 * one class of status
 * @author Cook1fan
 *
 */
public class Broadband {
	
	
	private boolean adsl_available;
	private boolean vdsl_available;
	private boolean ufb_available;
	
	private String href;
	private String type;
	private String scheduled;

	public Broadband() {
		// TODO Auto-generated constructor stub
	}

	public boolean isAdsl_available() {
		return adsl_available;
	}

	public void setAdsl_available(boolean adsl_available) {
		this.adsl_available = adsl_available;
	}

	public boolean isVdsl_available() {
		return vdsl_available;
	}

	public void setVdsl_available(boolean vdsl_available) {
		this.vdsl_available = vdsl_available;
	}

	public boolean isUfb_available() {
		return ufb_available;
	}

	public void setUfb_available(boolean ufb_available) {
		this.ufb_available = ufb_available;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScheduled() {
		return scheduled;
	}

	public void setScheduled(String scheduled) {
		this.scheduled = scheduled;
	}
	
	

}