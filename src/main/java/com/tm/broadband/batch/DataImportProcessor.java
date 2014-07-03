package com.tm.broadband.batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import com.tm.broadband.batch.domain.RadiusRadacct;
import com.tm.broadband.model.Radacct;
import com.tm.broadband.model.TempDataUsage;

public class DataImportProcessor implements ItemProcessor<RadiusRadacct, TempDataUsage> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat date2Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private Date usage_date;
	private Long maxcount;

	public DataImportProcessor() {
	}

	public TempDataUsage process(RadiusRadacct rr) {
		
		TempDataUsage temp = new TempDataUsage();
		
		/*//private Long radacctid;
		acct.setRadacctid(rr.getRadacctid());
		//private String username;
		acct.setUsername(rr.getUsername());
		//private String nasipaddress;
		acct.setNasipaddress(rr.getNasipaddress());
		//private String nasportid;
		acct.setNasportid(rr.getNasportid());
		//private Date acctstarttime;
		acct.setAcctstarttime(rr.getAcctstarttime());
		//private Date acctstoptime;
		acct.setAcctstoptime(rr.getAcctstoptime());
		//private Integer acctsessiontime;
		acct.setAcctsessiontime(rr.getAcctsessiontime());
		//private String connectinfo_start;
		acct.setConnectinfo_start(rr.getConnectinfo_start());
		//private Long acctinputoctets;
		acct.setAcctinputoctets(rr.getAcctinputoctets());
		//private Long acctouputoctets;
		acct.setAcctoutputoctets(rr.getAcctoutputoctets());
		//private String calledstationid;
		acct.setCalledstationid(rr.getCalledstationid());
		//private String acctterminatecause;
		acct.setAcctterminatecause(rr.getAcctterminatecause());
		//private Date _accttime;
		acct.set_accttime(rr.get_accttime());*/
		
		temp.setVlan(rr.getConnectinfo_start());
		temp.setUpload(rr.getAcctinputoctets());
		temp.setDownload(rr.getAcctoutputoctets());
		temp.setUsage_date(usage_date);
		temp.setMaxcount(maxcount);
		
		return temp;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public SimpleDateFormat getDate2Format() {
		return date2Format;
	}

	public void setDate2Format(SimpleDateFormat date2Format) {
		this.date2Format = date2Format;
	}

	public Date getUsage_date() {
		return usage_date;
	}

	public void setUsage_date(Date usage_date) {
		this.usage_date = usage_date;
	}

	public Long getMaxcount() {
		return maxcount;
	}

	public void setMaxcount(Long maxcount) {
		this.maxcount = maxcount;
	}
	
	

}
