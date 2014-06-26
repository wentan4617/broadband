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
import com.tm.broadband.mapper.TerminationRefundMapper;
import com.tm.broadband.mapper.VoucherBannedListMapper;
import com.tm.broadband.mapper.VoucherFileUploadMapper;
import com.tm.broadband.mapper.VoucherMapper;
import com.tm.broadband.model.BillingFileUpload;
import com.tm.broadband.model.CallChargeRate;
import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.model.EarlyTerminationChargeParameter;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.TerminationRefund;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.model.VoucherBannedList;
import com.tm.broadband.model.VoucherFileUpload;

@Service
public class BillingService {
	
	private BillingFileUploadMapper billingFileUploadMapper;
	private CustomerCallRecordMapper customerCallRecordMapper;
	private CallChargeRateMapper callChargeRateMapper;
	private CallInternationalRateMapper callInternationalRateMapper;
	private EarlyTerminationChargeMapper earlyTerminationChargeMapper;
	private EarlyTerminationChargeParameterMapper earlyTerminationChargeParameterMapper;
	private TerminationRefundMapper terminationRefundMapper;
	private VoucherMapper voucherMapper;
	private VoucherFileUploadMapper voucherFileUploadMapper;
	private VoucherBannedListMapper voucherBannedListMapper;

	@Autowired
	public BillingService(BillingFileUploadMapper billingFileUploadMapper
			,CustomerCallRecordMapper customerCallRecordMapper
			,CallChargeRateMapper callChargeRateMapper
			,CallInternationalRateMapper callInternationalRateMapper
			,EarlyTerminationChargeMapper earlyTerminationChargeMapper
			,EarlyTerminationChargeParameterMapper earlyTerminationChargeParameterMapper
			,TerminationRefundMapper terminationRefundMapper
			,VoucherMapper voucherMapper
			,VoucherFileUploadMapper voucherFileUploadMapper
			,VoucherBannedListMapper voucherBannedListMapper) {
		this.billingFileUploadMapper = billingFileUploadMapper;
		this.customerCallRecordMapper = customerCallRecordMapper;
		this.callChargeRateMapper = callChargeRateMapper;
		this.callInternationalRateMapper = callInternationalRateMapper;
		this.earlyTerminationChargeMapper = earlyTerminationChargeMapper;
		this.earlyTerminationChargeParameterMapper = earlyTerminationChargeParameterMapper;
		this.terminationRefundMapper = terminationRefundMapper;
		this.voucherMapper = voucherMapper;
		this.voucherFileUploadMapper = voucherFileUploadMapper;
		this.voucherBannedListMapper = voucherBannedListMapper;
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
		return this.callInternationalRateMapper.selectCallInternationalRates(cir);
	}

	@Transactional
	public void removeCallInternationalRate(CallInternationalRate cir) {
		this.callInternationalRateMapper.deleteCallInternationalRate(cir);
	}

	@Transactional
	public void editCallInternationalRate(CallInternationalRate cir) {
		this.callInternationalRateMapper.updateCallInternationalRate(cir);
	}
	
	@Transactional
	public List<CallInternationalRate> queryCallInternationalRatesGroupBy() {
		return this.callInternationalRateMapper.selectCallInternationalRatesGroupBy();
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
	
	@Transactional
	public int queryEarlyTerminationChargesSumByPage(Page<EarlyTerminationCharge> page) {
		return this.earlyTerminationChargeMapper.selectEarlyTerminationChargesSum(page);
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

	// BEGIN TerminationRefund
	@Transactional
	public Page<TerminationRefund> queryTerminationRefundsByPage(Page<TerminationRefund> page) {
		page.setTotalRecord(this.terminationRefundMapper.selectTerminationRefundsSum(page));
		page.setResults(this.terminationRefundMapper.selectTerminationRefundsByPage(page));
		return page;
	}

	@Transactional 
	public List<TerminationRefund> queryTerminationRefund(TerminationRefund tr){
		return this.terminationRefundMapper.selectTerminationRefund(tr);
	}

	@Transactional
	public void removeTerminationRefund(int id) {
		this.terminationRefundMapper.deleteTerminationRefundById(id);
	}

	@Transactional
	public void editTerminationRefund(TerminationRefund tr) {
		this.terminationRefundMapper.updateTerminationRefund(tr);
	}
	@Transactional
	public String selectTerminationRefundPDFPathById(int id){
		return this.terminationRefundMapper.selectTerminationRefundPDFPathById(id);
	}
	
	@Transactional
	public int queryTerminationRefundsSumByPage(Page<TerminationRefund> page) {
		return this.terminationRefundMapper.selectTerminationRefundsSum(page);
	}
	// END TerminationRefund

	// BEGIN Voucher
	@Transactional
	public Page<Voucher> queryVouchersByPage(Page<Voucher> page) {
		page.setTotalRecord(this.voucherMapper.selectVouchersSum(page));
		page.setResults(this.voucherMapper.selectVouchersByPage(page));
		return page;
	}

	@Transactional 
	public List<Voucher> queryVoucher(Voucher v){
		return this.voucherMapper.selectVoucher(v);
	}
	
	@Transactional
	public void createVoucher(Voucher v){
		this.voucherMapper.insertVoucher(v);
	}

	@Transactional
	public void removeVoucherBySerialNumber(int serial_number) {
		this.voucherMapper.deleteVoucherBySerialNumber(serial_number);
	}

	@Transactional
	public void editVoucher(Voucher v) {
		this.voucherMapper.updateVoucher(v);
	}
	
	@Transactional
	public int queryVouchersSumByPage(Page<Voucher> page) {
		return this.voucherMapper.selectVouchersSum(page);
	}
	// END Voucher

	// BEGIN VoucherFileUpload
	@Transactional
	public Page<VoucherFileUpload> queryVoucherFileUploadsByPage(Page<VoucherFileUpload> page) {
		page.setTotalRecord(this.voucherFileUploadMapper.selectVoucherFileUploadsSum(page));
		page.setResults(this.voucherFileUploadMapper.selectVoucherFileUploadsByPage(page));
		return page;
	}

	@Transactional 
	public List<VoucherFileUpload> queryVoucherFileUpload(VoucherFileUpload vfu){
		return this.voucherFileUploadMapper.selectVoucherFileUpload(vfu);
	}
	
	@Transactional
	public String queryVoucherFileUploadCSVPathById(int id){
		return this.voucherFileUploadMapper.selectVoucherFilePathById(id);
	}
	
	@Transactional
	public void createVoucherFileUpload(VoucherFileUpload vfu){
		this.voucherFileUploadMapper.insertVoucherFileUpload(vfu);
	}

	@Transactional
	public void removeVoucherFileUploadBySerialNumber(int id) {
		this.voucherFileUploadMapper.deleteVoucherFileUploadById(id);
	}

	@Transactional
	public void editVoucherFileUpload(VoucherFileUpload vfu) {
		this.voucherFileUploadMapper.updateVoucherFileUpload(vfu);
	}
	
	@Transactional
	public int queryVoucherFileUploadsSumByPage(Page<VoucherFileUpload> page) {
		return this.voucherFileUploadMapper.selectVoucherFileUploadsSum(page);
	}
	// END VoucherFileUpload


	// BEGIN Voucher
	@Transactional
	public Page<VoucherBannedList> queryVoucherBannedListsByPage(Page<VoucherBannedList> page) {
		page.setTotalRecord(this.voucherBannedListMapper.selectVoucherBannedListsSum(page));
		page.setResults(this.voucherBannedListMapper.selectVoucherBannedListsByPage(page));
		return page;
	}

	@Transactional 
	public List<VoucherBannedList> queryVoucherBannedList(VoucherBannedList vbl){
		return this.voucherBannedListMapper.selectVoucherBannedList(vbl);
	}
	
	@Transactional
	public void createVoucherBannedList(VoucherBannedList vbl){
		this.voucherBannedListMapper.insertVoucherBannedList(vbl);
	}

	@Transactional
	public void removeVoucherBannedListById(int id) {
		this.voucherBannedListMapper.deleteVoucherBannedListById(id);
	}

	@Transactional
	public void editVoucherBannedList(VoucherBannedList vbl) {
		this.voucherBannedListMapper.updateVoucherBannedList(vbl);
	}
	
	@Transactional
	public int queryVoucherBannedListsSumByPage(Page<VoucherBannedList> page) {
		return this.voucherBannedListMapper.selectVoucherBannedListsSum(page);
	}
	// END Voucher
}
