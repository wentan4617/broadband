package com.tm.broadband.model;

import java.io.Serializable;

/**
 * one class of status
 * @author Cook1fan
 *
 */
public class Broadband implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	/*
	 * TABLE MAPPING PROPERTIES
	 */
	
	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * RELATED PROPERTIES
	 */
	
	private boolean adsl_available;
	private boolean vdsl_available;
	private boolean ufb_available;
	private String services_available;
	private String address;
	
	private String href;
	private String type;
	private String scheduled;
	
	/*
	 * END RELATED PROPERTIES
	 */

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServices_available() {
		return services_available;
	}

	public void setServices_available(String services_available) {
		this.services_available = services_available;
	}
	
}
