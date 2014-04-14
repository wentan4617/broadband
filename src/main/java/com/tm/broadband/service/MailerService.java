package com.tm.broadband.service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.email.Mailer;
import com.tm.broadband.mapper.CompanyDetailMapper;
import com.tm.broadband.model.CompanyDetail;

/** 
* Mailer Service
* 
* @author DONG CHEN & YIFAN XIONG
*/ 
@Service
public class MailerService implements Mailer {

	private JavaMailSenderImpl mailSender;
	private TaskExecutor taskExecutor;
	private SimpleMailMessage preConfiguredMessage;
	private CompanyDetailMapper companyDetailMapper;

	@Autowired
	public MailerService(JavaMailSenderImpl mailSender,
			TaskExecutor taskExecutor, SimpleMailMessage preConfiguredMessage, CompanyDetailMapper companyDetailMapper) {
		this.mailSender = mailSender;
		this.taskExecutor = taskExecutor;
		this.preConfiguredMessage = preConfiguredMessage;
		this.companyDetailMapper = companyDetailMapper;
	}

	public MailerService() {
	}

	/**
	 * Synchronization Mode Send Email
	 * 
	 * @param email
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendMailBySynchronizationMode(ApplicationEmail email)
			throws MessagingException, IOException {
		CompanyDetail companyDetail = this.companyDetailMapper.selectCompanyDetail();
		mailSender.setUsername(companyDetail.getCompany_email());
		mailSender.setPassword(companyDetail.getCompany_email_password());
		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage mime = new MimeMessage(session);
		MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");

		helper.setFrom(companyDetail.getCompany_email());// from
		helper.setTo(InternetAddress.parse(email.getAddressee()));// to
		// helper.setBcc("administrator@chinaptp.com");// 
		helper.setReplyTo(companyDetail.getCompany_email());// reply to
		helper.setSubject(email.getSubject());// subject
		helper.setText(email.getContent(), true);// content
		
		// set attach details if attachName and attachPath are both not null and not empty string
		if((email.getAttachName() != null && email.getAttachPath() != null) 
				&& (!email.getAttachName().equals("") && !email.getAttachPath().equals(""))){
			FileSystemResource file = new FileSystemResource(new File(email.getAttachPath()));
			helper.addAttachment(email.getAttachName(),file);
		}
		
		mailSender.send(mime);
	}

	/**
	 * Asynchronous Mode Send Email
	 * 
	 * @param email
	 */
	public void sendMailByAsynchronousMode(final ApplicationEmail email) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendMailBySynchronizationMode(email);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
