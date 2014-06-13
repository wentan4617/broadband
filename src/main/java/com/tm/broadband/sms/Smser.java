package com.tm.broadband.sms;


public interface Smser {
	public void sendSMSByAsynchronousMode(final String cellphone, final String content);
	public void sendSMSBySynchronizationMode(final String cellphone, final String content);
}
