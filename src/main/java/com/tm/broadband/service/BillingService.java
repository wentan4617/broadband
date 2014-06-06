package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.BillingFileUploadMapper;
import com.tm.broadband.mapper.CallChargeRateMapper;
import com.tm.broadband.mapper.CallInternationalRateMapper;
import com.tm.broadband.mapper.CustomerCallRecordMapper;
import com.tm.broadband.model.BillingFileUpload;
import com.tm.broadband.model.CallChargeRate;
import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.Page;

@Service
public class BillingService {
	
	private BillingFileUploadMapper billingFileUploadMapper;
	private CustomerCallRecordMapper customerCallRecordMapper;
	private CallChargeRateMapper callChargeRateMapper;
	private CallInternationalRateMapper callInternationalRateMapper;

	@Autowired
	public BillingService(BillingFileUploadMapper billingFileUploadMapper
			,CustomerCallRecordMapper customerCallRecordMapper
			,CallChargeRateMapper callChargeRateMapper
			,CallInternationalRateMapper callInternationalRateMapper) {
		this.billingFileUploadMapper = billingFileUploadMapper;
		this.customerCallRecordMapper = customerCallRecordMapper;
		this.callChargeRateMapper = callChargeRateMapper;
		this.callInternationalRateMapper = callInternationalRateMapper;
	}
	
	public BillingService(){}

	// BEGIN CustomerCallRecord
	@Transactional
	public Page<BillingFileUpload> queryBillingFileUploadsByPage(Page<BillingFileUpload> page) {
		page.setTotalRecord(this.billingFileUploadMapper.selectBillingFileUploadsSum(page));
		page.setResults(this.billingFileUploadMapper.selectBillingFileUploadsByPage(page));
		return page;
	}

	@Transactional
	public void createBillingFileUpload(BillingFileUpload bfu) {
		this.billingFileUploadMapper.insertBillingFileUpload(bfu);
	}
	
	@Transactional
	public int queryBillingFileUploadsSumByPage(Page<BillingFileUpload> page) {
		return this.billingFileUploadMapper.selectBillingFileUploadsSum(page);
	}

	@Transactional 
	public String queryBillingFilePathById(int id){
		return this.billingFileUploadMapper.selectBillingFilePathById(id);
	}

	@Transactional
	public void removeBillingFileUpload(int id) {
		this.billingFileUploadMapper.deleteBillingFileUpload(id);
	}

	@Transactional
	public void editBillingFileUpload(BillingFileUpload bfu) {
		this.billingFileUploadMapper.updateBillingFileUpload(bfu);
	}
	// END CustomerCallRecord
	
	
	
	// BEGIN CustomerCallRecord
	@Transactional
	public void createCustomerCallRecord(CustomerCallRecord ccr) {
		this.customerCallRecordMapper.insertCustomerCallRecord(ccr);
	}

	@Transactional
	public void removeCustomerCallRecord(CustomerCallRecord ccr) {
		this.customerCallRecordMapper.deleteCustomerCallRecord(ccr);
	}
	// END CustomerCallRecord
	
	
	
	// BEGIN CallChargeRate
	@Transactional
	public Page<CallChargeRate> queryCallChargeRatesByPage(Page<CallChargeRate> page) {
		page.setTotalRecord(this.callChargeRateMapper.selectCallChargeRatesSum(page));
		page.setResults(this.callChargeRateMapper.selectCallChargeRatesByPage(page));
		return page;
	}

	@Transactional
	public void createCallChargeRate(CallChargeRate ccr) {
		this.callChargeRateMapper.insertCallChargeRate(ccr);
	}

	@Transactional 
	public List<CallChargeRate> queryCallChargeRate(CallChargeRate ccr){
		return this.callChargeRateMapper.selectCallChargeRate(ccr);
	}

	@Transactional
	public void removeCallChargeRate(int id) {
		this.callChargeRateMapper.deleteCallChargeRateById(id);
	}

	@Transactional
	public void editCallChargeRate(CallChargeRate ccr) {
		this.callChargeRateMapper.updateCallChargeRate(ccr);
	}
	// END CallChargeRate

	// BEGIN CallInternationalRate
	@Transactional
	public Page<CallInternationalRate> queryCallInternationalRatesByPage(Page<CallInternationalRate> page) {
		page.setTotalRecord(this.callInternationalRateMapper.selectCallInternationalRatesSum(page));
		page.setResults(this.callInternationalRateMapper.selectCallInternationalRatesByPage(page));
		return page;
	}

	@Transactional
	public void createCallInternationalRate(CallInternationalRate cir) {
		this.callInternationalRateMapper.insertCallInternationalRate(cir);
	}

	@Transactional 
	public List<CallInternationalRate> queryCallInternationalRate(CallInternationalRate cir){
		return this.callInternationalRateMapper.selectCallInternationalRate(cir);
	}

	@Transactional
	public void removeCallInternationalRate() {
		this.callInternationalRateMapper.deleteCallInternationalRate();
	}

	@Transactional
	public void editCallInternationalRate(CallInternationalRate cir) {
		this.callInternationalRateMapper.updateCallInternationalRate(cir);
	}
	// END CallInternationalRate

}
