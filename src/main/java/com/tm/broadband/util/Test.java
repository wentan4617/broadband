package com.tm.broadband.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.email.Mailer;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/application-config.xml");
		Mailer mailer = (Mailer) context.getBean("applicationMailer");
		ApplicationEmail email = new ApplicationEmail();
		email.setAddressee("32582048@qq.com");
		email.setSubject("测试邮件有一份");
		email.setContent("这个是内容html内容");
		/*try {
			mailer.sendMailBySynchronizationMode(email);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		mailer.sendMailByAsynchronousMode(email);
	}

	public static void test1() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		String str = "TM" + "Cook1fan".toUpperCase()
				+ String.valueOf(cal.get(Calendar.YEAR))
				+ String.valueOf(cal.get(Calendar.MONTH) + 1)
				+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
				+ String.valueOf(cal.get(Calendar.HOUR_OF_DAY))
				+ String.valueOf(cal.get(Calendar.MINUTE));

		System.out.println(str);
	}

}
