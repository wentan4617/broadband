package com.tm.broadband.quartz;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.tm.broadband.service.DataService;

public class RadiusDataUsageImportCalculator {
	
	private DataService dataService;
	private JobLauncher launcher;
	private Job importJob;

	@Autowired
	public RadiusDataUsageImportCalculator(DataService dataService, JobLauncher launcher, @Qualifier("importRadiusRadacctJob") Job importJob) {
		this.dataService = dataService;
		this.launcher = launcher;
		this.importJob = importJob;
	}
	
	
	public void doRadiusDataImportCalculator(){
		
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
	}

}
