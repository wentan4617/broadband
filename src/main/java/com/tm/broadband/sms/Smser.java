package com.tm.broadband.sms;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.Notification;

public interface Smser {
	public void sendSMSByAsynchronousMode(final Customer customer, final Notification notification);
	public void sendSMSBySynchronizationMode(final Customer customer, final Notification notification);
}
