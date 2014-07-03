package com.tm.broadband.quartz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tm.broadband.model.NetworkUsage;
import com.tm.broadband.model.TempDataUsage;
import com.tm.broadband.service.DataService;

public class RadiusDataUsageImportCalculator {
	
	private DataService dataService;
	private JobLauncher launcher;
	private Job importJob;

	@Autowired
	public RadiusDataUsageImportCalculator(DataService dataService, 
			JobLauncher launcher, 
			@Qualifier("importRadiusRadacctJob") Job importJob) {
		this.dataService = dataService;
		this.launcher = launcher;
		this.importJob = importJob;
	}
	
	public void doRadiusDataImportCalculator(){
		
		Long maxcount = this.dataService.queryMaxCount();
		System.out.println("maxcount: " + maxcount);
		
		if (maxcount == null) {
			maxcount = 1l;
		} else {
			++maxcount;
		}
		
		// import data from cyberpark radius
		Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
		
		Date date = new Date();
		parameters.put("run_datetime", new JobParameter(date));
		parameters.put("usage_date", new JobParameter(date));
		parameters.put("maxcount", new JobParameter(maxcount));
		
		JobExecution result = null;
		
		try {
			result = launcher.run(importJob, new JobParameters(parameters));
			System.out.println(result.getStatus().toString());
		} catch (JobExecutionAlreadyRunningException 
				| JobRestartException 
				| JobInstanceAlreadyCompleteException 
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}
		
		List<NetworkUsage> usages = new ArrayList<NetworkUsage>();
		
		List<TempDataUsage> currentTemps = this.dataService.queryDataUsageRecent(maxcount);
		
		Map<String, TempDataUsage> currentTempMap = new HashMap<String, TempDataUsage>();
		for (TempDataUsage currentTemp : currentTemps) {
			currentTempMap.put(currentTemp.getVlan(), currentTemp);
		}
		
		List<TempDataUsage> recentTemps = this.dataService.queryDataUsageRecent(--maxcount);
		
		if (recentTemps != null && recentTemps.size() > 0) {
			for (TempDataUsage recentTemp : recentTemps) {
				if (currentTempMap.containsKey(recentTemp.getVlan())) {
					TempDataUsage currentTemp = currentTempMap.get(recentTemp.getVlan());
					
					NetworkUsage usage = new NetworkUsage();
					usage.setVlan(currentTemp.getVlan());
					usage.setUpload(currentTemp.getUpload() - recentTemp.getUpload());
					usage.setDownload(currentTemp.getDownload() - recentTemp.getDownload());
					usage.setAccounting_date(date);
					usages.add(usage);
				}
			}
		} else {
			for (TempDataUsage currentTemp : currentTemps) {
				NetworkUsage usage = new NetworkUsage();
				usage.setVlan(currentTemp.getVlan());
				usage.setUpload(currentTemp.getUpload());
				usage.setDownload(currentTemp.getDownload());
				usage.setAccounting_date(date);
				usages.add(usage);
			}
		}

		this.dataService.insertUsage(usages);
		/*TempDateUsage temp = new TempDateUsage();
		temp.setMaxcount(maxcount);
		temp.setUsage_date(new Date());
		
		this.dataService.insertTempDateUsage(temp);*/
	}
	
	/*public void doRadiusDataImportCalculator(){
		//System.out.println("runing" + new Date());
		// delete tm_radacct recode
		this.dataService.removeRadacct();
		
		// import data from cyberpark radius
		Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
		
		Date date = new Date();
		parameters.put("run_datetime", new JobParameter(date));
		
		JobExecution result = null;
		
		try {
			result = launcher.run(importJob, new JobParameters(parameters));
			System.out.println(result.getStatus().toString());
		} catch (JobExecutionAlreadyRunningException 
				| JobRestartException 
				| JobInstanceAlreadyCompleteException 
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		NetworkUsage usage = new NetworkUsage();
		usage.getParams().put("year", cal.get(Calendar.YEAR));
		usage.getParams().put("month", cal.get(Calendar.MONTH) + 1);
		usage.getParams().put("last_month", cal.get(Calendar.MONTH));
		
		dataService.calculatorUsage(usage);
	}*/

}
