package com.tm.broadband.batch.domain;

import java.io.Serializable;
import java.util.Date;

public class RadiusRadacct implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long radacctid;
	private String username;
	private String nasipaddress;
	private String nasportid;
	private Date acctstarttime;
	private Date acctstoptime;
	private Integer acctsessiontime;
	private String connectinfo_start;
	private Long acctinputoctets;
	private Long acctoutputoctets;
	private String calledstationid;
	private String acctterminatecause;
	private Date _accttime;

	public RadiusRadacct() {
		// TODO Auto-generated constructor stub
	}

	public Long getRadacctid() {
		return radacctid;
	}

	public void setRadacctid(Long radacctid) {
		this.radacctid = radacctid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNasipaddress() {
		return nasipaddress;
	}

	public void setNasipaddress(String nasipaddress) {
		this.nasipaddress = nasipaddress;
	}

	public String getNasportid() {
		return nasportid;
	}

	public void setNasportid(String nasportid) {
		this.nasportid = nasportid;
	}

	public Date getAcctstarttime() {
		return acctstarttime;
	}

	public void setAcctstarttime(Date acctstarttime) {
		this.acctstarttime = acctstarttime;
	}

	public Date getAcctstoptime() {
		return acctstoptime;
	}

	public void setAcctstoptime(Date acctstoptime) {
		this.acctstoptime = acctstoptime;
	}

	public Integer getAcctsessiontime() {
		return acctsessiontime;
	}

	public void setAcctsessiontime(Integer acctsessiontime) {
		this.acctsessiontime = acctsessiontime;
	}

	public Long getAcctinputoctets() {
		return acctinputoctets;
	}

	public void setAcctinputoctets(Long acctinputoctets) {
		this.acctinputoctets = acctinputoctets;
	}

	

	public Long getAcctoutputoctets() {
		return acctoutputoctets;
	}

	public void setAcctoutputoctets(Long acctoutputoctets) {
		this.acctoutputoctets = acctoutputoctets;
	}

	public String getCalledstationid() {
		return calledstationid;
	}

	public void setCalledstationid(String calledstationid) {
		this.calledstationid = calledstationid;
	}

	public String getAcctterminatecause() {
		return acctterminatecause;
	}

	public void setAcctterminatecause(String acctterminatecause) {
		this.acctterminatecause = acctterminatecause;
	}

	public Date get_accttime() {
		return _accttime;
	}

	public void set_accttime(Date _accttime) {
		this._accttime = _accttime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getConnectinfo_start() {
		return connectinfo_start;
	}

	public void setConnectinfo_start(String connectinfo_start) {
		this.connectinfo_start = connectinfo_start;
	}

}
