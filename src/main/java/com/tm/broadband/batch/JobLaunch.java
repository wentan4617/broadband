package com.tm.broadband.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JobLaunch {

	public JobLaunch() {
		// TODO Auto-generated constructor stub
	}
	

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/application-config.xml");
		
		//JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		//Job job = (Job) context.getBean("vosJob");
		//Job job = (Job) context.getBean("ratesJob");
		//Job job = (Job) context.getBean("dataExportJob");
		//Job job = (Job) context.getBean("dataImportJob");
		//Job job = (Job) context.getBean("dataExportImportJob");
		Job job = (Job) context.getBean("importRadiusRadacctJob");
		
		Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
		//parameters.put("vos2009.file.name", new JobParameter("classpath:data/vos2009_2013-11-4.csv"));
		//parameters.put("vos3000.file.name", new JobParameter("classpath:data/vos3000_2013-11-4.csv"));
		
		//parameters.put("profit_datetime", new JobParameter("2013-11-4"));
		//parameters.put("rates.file.name", new JobParameter("classpath:data/rates.csv"));
		
		try {
			
			parameters.put("run_datetime", new JobParameter(new Date()));
			JobExecution result = launcher.run(job, new JobParameters(parameters));
			System.out.println(result.toString());
			
			/*parameters.put("run_datetime", new JobParameter(new Date()));
			parameters.put("record_date", new JobParameter("2014-01-01"));
			parameters.put("tableName", new JobParameter("e_cdr_20140101"));
			result = launcher.run(job, new JobParameters(parameters));
			System.out.println(result.toString());*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void csvJob() {
		/*ApplicationContext context = new ClassPathXmlApplicationContext("spring/csvJob.xml");
		
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("cvsJob");
		
		JdbcTelecomDao jdbcTelecomDao = (JdbcTelecomDao)context.getBean("jdbcTelecomDao");
		jdbcTelecomDao.deleteTelecomHistory("2013", "06");
		jdbcTelecomDao.setYear("2013");
		jdbcTelecomDao.setMonth("06");
		
		JdbcCallPlusDao jdbcCallPlusDao = (JdbcCallPlusDao)context.getBean("jdbcCallPlusDao");
		jdbcCallPlusDao.deleteTelecomHistory("2013", "06");
		jdbcCallPlusDao.setYear("2013");
		jdbcCallPlusDao.setMonth("06");
		
		JdbcP0800Dao jdbcP0800Dao = (JdbcP0800Dao)context.getBean("jdbcP0800Dao");
		jdbcP0800Dao.deleteP0800History("2013", "06");
		jdbcP0800Dao.setYear("2013");
		jdbcP0800Dao.setMonth("06");
		
		JdbcVoipDao jdbcVoipDao = (JdbcVoipDao)context.getBean("jdbcVoipDao");
		jdbcVoipDao.deleteVoipHistory("2013", "06");
		jdbcVoipDao.setYear("2013");
		jdbcVoipDao.setMonth("06");
		
		Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
		parameters.put("callplus.file.name", new JobParameter("classpath:data/CALLPLUS201306.csv"));
		parameters.put("telecom.file.name", new JobParameter("classpath:data/TELECOM201306.csv"));
		parameters.put("p0800.file.name", new JobParameter("classpath:data/0800201306.csv"));
		parameters.put("voip.file.name", new JobParameter("classpath:data/VOIP201306.csv"));

		try {
			 运行Job 
			JobExecution result = launcher.run(job, new JobParameters(parameters));
			 处理结束，控制台打印处理结果 
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
