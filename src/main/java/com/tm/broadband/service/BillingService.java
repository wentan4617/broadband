package com.tm.broadband.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.broadband.mapper.BillingFileUploadMapper;

@Service
public class BillingService {
	
	private BillingFileUploadMapper billingFileUploadMapper;

	@Autowired
	public BillingService(BillingFileUploadMapper billingFileUploadMapper) {
		this.billingFileUploadMapper = billingFileUploadMapper;
	}
	
	public BillingService(){}

}
