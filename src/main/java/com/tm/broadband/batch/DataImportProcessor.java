package com.tm.broadband.batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import com.tm.broadband.batch.domain.RadiusRadacct;
import com.tm.broadband.model.Radacct;

public class DataImportProcessor implements ItemProcessor<RadiusRadacct, Radacct> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat date2Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public DataImportProcessor() {
	}

	public Radacct process(RadiusRadacct rr) {
		
		System.out.println(rr.getRadacctid());
		Radacct acct = new Radacct();
		
		//private Long radacctid;
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
		//private Long acctinputoctets;
		acct.setAcctinputoctets(rr.getAcctinputoctets());
		//private Long acctouputoctets;
		acct.setAcctoutputoctets(rr.getAcctoutputoctets());
		//private String calledstationid;
		acct.setCalledstationid(rr.getCalledstationid());
		//private String acctterminatecause;
		acct.setAcctterminatecause(rr.getAcctterminatecause());
		//private Date _accttime;
		acct.set_accttime(rr.get_accttime());
		
		return acct;
	}

}
