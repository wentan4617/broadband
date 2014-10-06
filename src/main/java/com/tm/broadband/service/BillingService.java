package com.tm.broadband.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.BillingFileUploadMapper;
import com.tm.broadband.mapper.CallChargeRateMapper;
import com.tm.broadband.mapper.CallInternationalRateMapper;
import com.tm.broadband.mapper.CustomerCallRecordMapper;
import com.tm.broadband.mapper.CustomerCallingRecordCallplusMapper;
import com.tm.broadband.mapper.CustomerInvoiceMapper;
import com.tm.broadband.mapper.CustomerTransactionMapper;
import com.tm.broadband.mapper.EarlyTerminationChargeMapper;
import com.tm.broadband.mapper.EarlyTerminationChargeParameterMapper;
import com.tm.broadband.mapper.TerminationRefundMapper;
import com.tm.broadband.mapper.VOSVoIPRateMapper;
import com.tm.broadband.mapper.VoucherBannedListMapper;
import com.tm.broadband.mapper.VoucherFileUploadMapper;
import com.tm.broadband.mapper.VoucherMapper;
import com.tm.broadband.model.BillingFileUpload;
import com.tm.broadband.model.CallChargeRate;
import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.CustomerCallingRecordCallplus;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.model.EarlyTerminationChargeParameter;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.TerminationRefund;
import com.tm.broadband.model.VOSVoIPRate;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.model.VoucherBannedList;
import com.tm.broadband.model.VoucherFileUpload;
import com.tm.broadband.util.CallInternationalRateUtility;
import com.tm.broadband.util.CallingRecordUtility;
import com.tm.broadband.util.CallingRecordUtility_CallPlus;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.VOSVoIPRatesUtility;
import com.tm.broadband.util.test.Console;

/**
 * Billing service
 * 
 * @author Don Chen
 * 
 */
@Service
public class BillingService {
	
	private BillingFileUploadMapper billingFileUploadMapper;
	private CustomerCallRecordMapper customerCallRecordMapper;
	private CallChargeRateMapper callChargeRateMapper;
	private CallInternationalRateMapper callInternationalRateMapper;
	private VOSVoIPRateMapper vosVoIPRateMapper;
	private EarlyTerminationChargeMapper earlyTerminationChargeMapper;
	private EarlyTerminationChargeParameterMapper earlyTerminationChargeParameterMapper;
	private TerminationRefundMapper terminationRefundMapper;
	private VoucherMapper voucherMapper;
	private VoucherFileUploadMapper voucherFileUploadMapper;
	private VoucherBannedListMapper voucherBannedListMapper;
	private CustomerCallingRecordCallplusMapper customerCallingRecordCallplusMapper;
	private CustomerTransactionMapper ctMapper;
	private CustomerInvoiceMapper ciMapper;

	@Autowired
	public BillingService(BillingFileUploadMapper billingFileUploadMapper
			,CustomerCallRecordMapper customerCallRecordMapper
			,CallChargeRateMapper callChargeRateMapper
			,CallInternationalRateMapper callInternationalRateMapper
			,VOSVoIPRateMapper vosVoIPRateMapper
			,EarlyTerminationChargeMapper earlyTerminationChargeMapper
			,EarlyTerminationChargeParameterMapper earlyTerminationChargeParameterMapper
			,TerminationRefundMapper terminationRefundMapper
			,VoucherMapper voucherMapper
			,VoucherFileUploadMapper voucherFileUploadMapper
			,VoucherBannedListMapper voucherBannedListMapper
			,CustomerCallingRecordCallplusMapper customerCallingRecordCallplusMapper,
			CustomerInvoiceMapper ciMapper,
			CustomerTransactionMapper ctMapper) {
		this.billingFileUploadMapper = billingFileUploadMapper;
		this.customerCallRecordMapper = customerCallRecordMapper;
		this.callChargeRateMapper = callChargeRateMapper;
		this.callInternationalRateMapper = callInternationalRateMapper;
		this.vosVoIPRateMapper = vosVoIPRateMapper;
		this.earlyTerminationChargeMapper = earlyTerminationChargeMapper;
		this.earlyTerminationChargeParameterMapper = earlyTerminationChargeParameterMapper;
		this.terminationRefundMapper = terminationRefundMapper;
		this.voucherMapper = voucherMapper;
		this.voucherFileUploadMapper = voucherFileUploadMapper;
		this.voucherBannedListMapper = voucherBannedListMapper;
		this.customerCallingRecordCallplusMapper = customerCallingRecordCallplusMapper;
		this.ctMapper = ctMapper;
		this.ciMapper = ciMapper;
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

	
	// BEGIN CustomerCallRecordCallplus
	@Transactional
	public void createCustomerCallingRecordCallplus(CustomerCallingRecordCallplus ccrc) {
		this.customerCallingRecordCallplusMapper.insertCustomerCallingRecordCallplus(ccrc);
	}

	@Transactional
	public void removeCustomerCallingRecordCallplus(CustomerCallingRecordCallplus ccrc) {
		this.customerCallingRecordCallplusMapper.deleteCustomerCallingRecordCallplus(ccrc);
	}
	// END CustomerCallRecordCallplus
	
	
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
	public void removeCallInternationalRate() {
		this.callInternationalRateMapper.deleteCallInternationalRate();
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

	// BEGIN VOSVoIPRates
	@Transactional
	public Page<VOSVoIPRate> queryVOSVoIPRatesByPage(Page<VOSVoIPRate> page) {
		page.setTotalRecord(this.vosVoIPRateMapper.selectVOSVoIPRatesSum(page));
		page.setResults(this.vosVoIPRateMapper.selectVOSVoIPRatesByPage(page));
		return page;
	}

	@Transactional
	public void createVOSVoIPRates(VOSVoIPRate vosVoIPRate) {
		this.vosVoIPRateMapper.insertVOSVoIPRate(vosVoIPRate);
	}

	@Transactional 
	public List<VOSVoIPRate> queryVOSVoIPRates(VOSVoIPRate vosVoIPRate){
		return this.vosVoIPRateMapper.selectVOSVoIPRate(vosVoIPRate);
	}

	@Transactional
	public void removeVOSVoIPRates() {
		this.vosVoIPRateMapper.deleteVOSVoIPRates();
	}

	@Transactional
	public void editVOSVoIPRates(VOSVoIPRate vosVoIPRate) {
		this.vosVoIPRateMapper.updateVOSVoIPRate(vosVoIPRate);
	}
	
	@Transactional
	public List<VOSVoIPRate> queryVOSVoIPRatesGroupBy() {
		return this.vosVoIPRateMapper.selectVOSVoIPRatesGroupBy();
	}
	// END VOSVoIPRates

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
	public String queryEarlyTerminationChargePDFPathById(int id){
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
	public String queryTerminationRefundPDFPathById(int id){
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
	public Voucher queryVoucher(Voucher v){
		List<Voucher> vouchers = this.voucherMapper.selectVouchers(v);
		return vouchers != null && vouchers.size() > 0 ? vouchers.get(0) : null;
	}

	@Transactional 
	public List<Voucher> queryVouchers(Voucher v){
		return this.voucherMapper.selectVouchers(v);
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
	
	/**
	 * Chart Services BEGIN
	 */
	
	// Billing Transaction Statistic
	@Transactional
	public List<CustomerTransaction> queryCustomerTransactionsByTransactionDate(Date start, Date end){
		return this.ctMapper.selectCustomerTransactionsByTransactionDate(start, end);
	}
	
	// Billing Invoice Statistic
	@Transactional
	public List<CustomerInvoice> queryCustomerInvoicesByCreateDate(CustomerInvoice ci){
		return this.ciMapper.selectCustomerInvoicesByCreateDate(ci);
	}
	
	/**
	 * Chart Services END
	 */
	
	/**
	 * insert customer call record
	 */
	@Transactional
	public void insertCustomerCallRecord(String billingFileId
			, String billing_type
			, String statementDate
			, String filePath){

		BillingFileUpload bfu = new BillingFileUpload();
		bfu.getParams().put("id", billingFileId);
		bfu.setInserted_database(true);					// assign inserted_database to true which is 1
		bfu.setInsert_date(new Timestamp(System.currentTimeMillis()));
		this.billingFileUploadMapper.updateBillingFileUpload(bfu);
		
		if("chorus".equals(billing_type)){
			CustomerCallRecord ccrTemp = new CustomerCallRecord();
			ccrTemp.getParams().put("statement_date", TMUtils.parseDateYYYYMMDD(statementDate));
			this.customerCallRecordMapper.deleteCustomerCallRecord(ccrTemp);
			
			// Get All data from the CSV file
			List<CustomerCallRecord> ccrs = CallingRecordUtility.ccrs(filePath);
			
			// Iteratively insert into database
			for (CustomerCallRecord ccr : ccrs) {
				boolean isUsefull = false;
				if(ccr.getBilling_description()!=null){
					isUsefull = true;
					switch (ccr.getBilling_description()) {
						case "Call restrict with no Directory Access nat Res": break;
						case "Caller Display Monthly Charge per line Res": break;
						case "Call waiting nat Res": break;
						case "Faxability Monthly Rental Res": break;
						case "Smart Bundle package": break;
						default: isUsefull = false;
					}
				}
				if(ccr.getRecord_type()!=null && !isUsefull){
					isUsefull = true;
					switch (ccr.getRecord_type()) {
						case "T1": break;
						case "T3": break;
						default: isUsefull = false;
					}
				}
				if(isUsefull){
					ccr.setUpload_date(new Date());
					this.customerCallRecordMapper.insertCustomerCallRecord(ccr);
				}
			}
		} else if("callplus".equals(billing_type)){
			List<CustomerCallingRecordCallplus> ccrcs = CallingRecordUtility_CallPlus.ccrcs(filePath);
			
			for (CustomerCallingRecordCallplus ccrc : ccrcs) {
				this.customerCallingRecordCallplusMapper.insertCustomerCallingRecordCallplus(ccrc);
			}
		}
	}

	@Transactional
	public void insertCallInternationalRate(String save_path) {
		
		this.callInternationalRateMapper.deleteCallInternationalRate();
		
		// Get All data from the CSV file
		List<CallInternationalRate> cirs = CallInternationalRateUtility.cirs(save_path);
		
		// Iteratively insert into database
		for (CallInternationalRate cir : cirs) {
			this.callInternationalRateMapper.insertCallInternationalRate(cir);
		}
	}

	@Transactional
	public void insertVOSVoIPRates(String save_path) {
		
		this.vosVoIPRateMapper.deleteVOSVoIPRates();
		
		// Get All data from the CSV file
		List<VOSVoIPRate> vosVoIPRates = VOSVoIPRatesUtility.cirs(save_path);
		
		// Iteratively insert into database
		for (VOSVoIPRate vosVoIPRate : vosVoIPRates) {
			Console.log(vosVoIPRate);
			this.vosVoIPRateMapper.insertVOSVoIPRate(vosVoIPRate);
		}
	}
}
