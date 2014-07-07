package com.tm.broadband.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.RadacctMapper;
import com.tm.broadband.mapper.UsageMapper;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.NetworkUsage;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.TempDataUsage;
import com.tm.broadband.util.TMUtils;

/**
 * Data service
 * 
 * @author Cook1fan
 * 
 */
@Service
public class DataService {
	
	private UsageMapper usageMapper;
	private RadacctMapper radacctMapper;
	
	@Autowired
	public DataService(UsageMapper usageMapper, RadacctMapper radacctMapper) {
		this.usageMapper = usageMapper;
		this.radacctMapper = radacctMapper;
	}
	
	public DataService(){
		
	}
	
	@Transactional
	public void calculatorUsage(NetworkUsage u) {
		
		this.usageMapper.deleteUsage(u);
		
		Long maxcountMin = this.radacctMapper.selectMaxCountMinByDate(u.getParams().get("accounting_date").toString());
		Long maxcountMax = this.radacctMapper.selectMaxCountMaxByDate(u.getParams().get("accounting_date").toString());
		
		if (maxcountMin == null) {
			maxcountMin = 1l;
		}
		if (maxcountMax == null) {
			maxcountMax = 1l;
		}
		
		while (maxcountMin <= maxcountMax) {
			
			List<NetworkUsage> usages = new ArrayList<NetworkUsage>();
			
			List<TempDataUsage> currentTemps = this.radacctMapper.selectDataUsageRecent(maxcountMin);
			
			Map<String, TempDataUsage> currentTempMap = new HashMap<String, TempDataUsage>();
			for (TempDataUsage currentTemp : currentTemps) {
				currentTempMap.put(currentTemp.getVlan(), currentTemp);
			}
			
			
			List<TempDataUsage> recentTemps = this.radacctMapper.selectDataUsageRecent(maxcountMin-1);
			
			if (recentTemps != null && recentTemps.size() > 0) {
				for (TempDataUsage recentTemp : recentTemps) {
					if (currentTempMap.containsKey(recentTemp.getVlan())) {
						TempDataUsage currentTemp = currentTempMap.get(recentTemp.getVlan());
						
						NetworkUsage usage = new NetworkUsage();
						usage.setVlan(currentTemp.getVlan());
						usage.setUpload(currentTemp.getUpload() - recentTemp.getUpload());
						usage.setDownload(currentTemp.getDownload() - recentTemp.getDownload());
						usage.setAccounting_date(currentTemp.getUsage_date());
						usages.add(usage);
					}
				}
			} else {
				for (TempDataUsage currentTemp : currentTemps) {
					NetworkUsage usage = new NetworkUsage();
					usage.setVlan(currentTemp.getVlan());
					usage.setUpload(currentTemp.getUpload());
					usage.setDownload(currentTemp.getDownload());
					usage.setAccounting_date(currentTemp.getUsage_date());
					usages.add(usage);
				}
			}
			
			for (NetworkUsage usage: usages) {
				this.usageMapper.insertUsage(usage);
			}
			
			maxcountMin++;
			//System.out.println(maxcount += 2);
		}
	}
	
	@Transactional
	public Page<CustomerOrder> queryDataCustomersByPage(Page<CustomerOrder> page) {
		page.setTotalRecord(this.usageMapper.selectDataCustomersSum(page));
		page.setResults(this.usageMapper.selectDataCustomersByPage(page));
		return page;
	} 
	
	@Transactional
	public List<NetworkUsage> queryUsages(NetworkUsage usage) {
		return this.usageMapper.selectUsages(usage);
	}
	
	@Transactional
	public List<NetworkUsage> queryCurrentMonthUsages(NetworkUsage usage) {
		return this.usageMapper.selectCurrentMonthUsages(usage);
	}
	
	@Transactional
	public CustomerOrder queryDataCustomer(CustomerOrder co) {
		return this.usageMapper.selectDataCustomer(co);
	}
	
	@Transactional
	public void removeRadacct() {
		this.radacctMapper.deleteRadacct();
	}
	
	@Transactional
	public Long queryMaxCount() {
		return this.radacctMapper.selectMaxCount();
	}
	
	@Transactional
	public void insertTempDataUsage(TempDataUsage temp) {
		this.radacctMapper.insertTempDataUsage(temp);
	}
	
	@Transactional
	public List<TempDataUsage> queryDataUsageRecent(Long maxcount) {
		return this.radacctMapper.selectDataUsageRecent(maxcount);
	}
	
	@Transactional
	public void insertUsage(List<NetworkUsage> usages) {
		for (NetworkUsage usage: usages) {
			this.usageMapper.insertUsage(usage);
			//System.out.println("vlan: " + usage.getVlan() + ", upload: " + usage.getUpload() + ", download: " + usage.getDownload() + ", date: " + usage.getAccounting_date());
		}
	}
	
	@Transactional
	public void removeUsage(NetworkUsage usage) {
		this.usageMapper.deleteUsage(usage);
	}

}
