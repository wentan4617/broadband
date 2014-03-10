package com.tm.broadband.service;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.email.Mailer;

@Service
public class ApplicationMailer implements Mailer {

	private JavaMailSender mailSender;
	private TaskExecutor taskExecutor;
	private SimpleMailMessage preConfiguredMessage;

	@Autowired
	public ApplicationMailer(JavaMailSender mailSender,
			TaskExecutor taskExecutor, SimpleMailMessage preConfiguredMessage) {
		this.mailSender = mailSender;
		this.taskExecutor = taskExecutor;
		this.preConfiguredMessage = preConfiguredMessage;
	}

	public ApplicationMailer() {
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

		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage mime = new MimeMessage(session);
		MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");

		helper.setFrom("kanny87929@gmail.com");// ������
		helper.setTo(InternetAddress.parse(email.getAddressee()));// �ռ���
		// helper.setBcc("administrator@chinaptp.com");//����
		helper.setReplyTo("kanny87929@gmail.com");// �ظ���
		helper.setSubject(email.getSubject());// �ʼ�����
		helper.setText(email.getContent(), true);// true��ʾ�趨html��ʽ
		
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
