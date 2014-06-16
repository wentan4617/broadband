package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.BillingFileUploadMapper;
import com.tm.broadband.mapper.CallChargeRateMapper;
import com.tm.broadband.mapper.CallInternationalRateMapper;
import com.tm.broadband.mapper.CustomerCallRecordMapper;
import com.tm.broadband.mapper.EarlyTerminationChargeMapper;
import com.tm.broadband.mapper.EarlyTerminationChargeParameterMapper;
import com.tm.broadband.mapper.EarlyTerminationRefundMapper;
import com.tm.broadband.model.BillingFileUpload;
import com.tm.broadband.model.CallChargeRate;
import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.model.EarlyTerminationChargeParameter;
import com.tm.broadband.model.EarlyTerminationRefund;
import com.tm.broadband.model.Page;

@Service
public class BillingService {
	
	private BillingFileUploadMapper billingFileUploadMapper;
	private CustomerCallRecordMapper customerCallRecordMapper;
	private CallChargeRateMapper callChargeRateMapper;
	private CallInternationalRateMapper callInternationalRateMapper;
	private EarlyTerminationChargeMapper earlyTerminationChargeMapper;
	private EarlyTerminationChargeParameterMapper earlyTerminationChargeParameterMapper;
	private EarlyTerminationRefundMapper earlyTerminationRefundMapper;

	@Autowired
	public BillingService(BillingFileUploadMapper billingFileUploadMapper
			,CustomerCallRecordMapper customerCallRecordMapper
			,CallChargeRateMapper callChargeRateMapper
			,CallInternationalRateMapper callInternationalRateMapper
			,EarlyTerminationChargeMapper earlyTerminationChargeMapper
			,EarlyTerminationChargeParameterMapper earlyTerminationChargeParameterMapper
			,EarlyTerminationRefundMapper earlyTerminationRefundMapper) {
		this.billingFileUploadMapper = billingFileUploadMapper;
		this.customerCallRecordMapper = customerCallRecordMapper;
		this.callChargeRateMapper = callChargeRateMapper;
		this.callInternationalRateMapper = callInternationalRateMapper;
		this.earlyTerminationChargeMapper = earlyTerminationChargeMapper;
		this.earlyTerminationChargeParameterMapper = earlyTerminationChargeParameterMapper;
		this.earlyTerminationRefundMapper = earlyTerminationRefundMapper;
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

	// BEGIN EarlyTerminationCharge
	@Transactional
	public Page<EarlyTerminationCharge> queryEarlyTerminationChargesByPage(Page<EarlyTerminationCharge> page) {
		page.setTotalRecord(this.earlyTerminationChargeMapper.selectEarlyTerminationChargesSum(page));
		page.setResults(this.earlyTerminationChargeMapper.selectEarlyTerminationChargesByPage(page));
		return page;
	}

	@Transactional 
	public List<EarlyTerminationCharge> queryEarlyTerminationCharge(EarlyTerminationCharge etc){
		return this.earlyTerminationChargeMapper.selectEarlyTerminationCharge(etc);
	}

	@Transactional
	public void removeEarlyTerminationCharge(int id) {
		this.earlyTerminationChargeMapper.deleteEarlyTerminationChargeById(id);
	}

	@Transactional
	public void editEarlyTerminationCharge(EarlyTerminationCharge etc) {
		this.earlyTerminationChargeMapper.updateEarlyTerminationCharge(etc);
	}
	@Transactional
	public String selectEarlyTerminationChargePDFPathById(int id){
		return this.earlyTerminationChargeMapper.selectEarlyTerminationChargePDFPathById(id);
	}
	// END EarlyTerminationCharge

	// BEGIN EarlyTerminationChargeParameter
	@Transactional 
	public EarlyTerminationChargeParameter queryEarlyTerminationChargeParameter(){
		return this.earlyTerminationChargeParameterMapper.selectEarlyTerminationChargeParameter();
	}

	@Transactional
	public void removeEarlyTerminationChargeParameter() {
		this.earlyTerminationChargeParameterMapper.deleteEarlyTerminationChargeParameter();
	}

	@Transactional
	public void editEarlyTerminationChargeParameter(EarlyTerminationChargeParameter etcp) {
		this.earlyTerminationChargeParameterMapper.updateEarlyTerminationChargeParameter(etcp);
	}
	// END EarlyTerminationChargeParameter

	// BEGIN EarlyTerminationCharge
	@Transactional
	public Page<EarlyTerminationRefund> queryEarlyTerminationRefundsByPage(Page<EarlyTerminationRefund> page) {
		page.setTotalRecord(this.earlyTerminationRefundMapper.selectEarlyTerminationRefundsSum(page));
		page.setResults(this.earlyTerminationRefundMapper.selectEarlyTerminationRefundsByPage(page));
		return page;
	}

	@Transactional 
	public List<EarlyTerminationRefund> queryEarlyTerminationRefund(EarlyTerminationRefund etc){
		return this.earlyTerminationRefundMapper.selectEarlyTerminationRefund(etc);
	}

	@Transactional
	public void removeEarlyTerminationRefund(int id) {
		this.earlyTerminationRefundMapper.deleteEarlyTerminationRefundById(id);
	}

	@Transactional
	public void editEarlyTerminationRefund(EarlyTerminationRefund etc) {
		this.earlyTerminationRefundMapper.updateEarlyTerminationRefund(etc);
	}
	@Transactional
	public String selectEarlyTerminationRefundPDFPathById(int id){
		return this.earlyTerminationRefundMapper.selectEarlyTerminationRefundPDFPathById(id);
	}
	// END EarlyTerminationCharge

}
