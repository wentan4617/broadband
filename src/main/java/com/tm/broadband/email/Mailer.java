package com.tm.broadband.email;

import java.io.IOException;

import javax.mail.MessagingException;

public interface Mailer {
	public void sendMailByAsynchronousMode(final ApplicationEmail email);
	public void sendMailBySynchronizationMode(final ApplicationEmail email) throws MessagingException, IOException;
}
