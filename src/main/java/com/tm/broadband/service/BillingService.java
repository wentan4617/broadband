package com.tm.broadband.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.BillingFileUploadMapper;
import com.tm.broadband.mapper.CallChargeRateMapper;
import com.tm.broadband.mapper.CallInternationalRateMapper;
import com.tm.broadband.mapper.CustomerCallRecordMapper;
import com.tm.broadband.mapper.CustomerCallingRecordCallplusMapper;
import com.tm.broadband.mapper.CustomerChorusBroadbandASIDRecordMapper;
import com.tm.broadband.mapper.CustomerInvoiceMapper;
import com.tm.broadband.mapper.CustomerTransactionMapper;
import com.tm.broadband.mapper.EarlyTerminationChargeMapper;
import com.tm.broadband.mapper.EarlyTerminationChargeParameterMapper;
import com.tm.broadband.mapper.NZAreaCodeListMapper;
import com.tm.broadband.mapper.TerminationRefundMapper;
import com.tm.broadband.mapper.VOSVoIPCallRecordMapper;
import com.tm.broadband.mapper.VOSVoIPRateMapper;
import com.tm.broadband.mapper.VoucherBannedListMapper;
import com.tm.broadband.mapper.VoucherFileUploadMapper;
import com.tm.broadband.mapper.VoucherMapper;
import com.tm.broadband.model.BillingFileUpload;
import com.tm.broadband.model.CallChargeRate;
import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.CustomerCallingRecordCallplus;
import com.tm.broadband.model.CustomerChorusBroadbandASIDRecord;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.model.EarlyTerminationChargeParameter;
import com.tm.broadband.model.NZAreaCodeList;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.StatisticBilling;
import com.tm.broadband.model.TerminationRefund;
import com.tm.broadband.model.VOSVoIPCallRecord;
import com.tm.broadband.model.VOSVoIPRate;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.model.VoucherBannedList;
import com.tm.broadband.model.VoucherFileUpload;
import com.tm.broadband.util.CallInternationalRateUtility;
import com.tm.broadband.util.CallingRecordUtility;
import com.tm.broadband.util.CallingRecordUtility_CallPlus;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.VOSVoIPCallRecordUtility;
import com.tm.broadband.util.VOSVoIPRatesUtility;

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
	private VOSVoIPCallRecordMapper vosVoIPCallRecordMapper;
	private NZAreaCodeListMapper nzAreaCodeListMapper;
	private CustomerChorusBroadbandASIDRecordMapper customerChorusBroadbandASIDRecordMapper;

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
			CustomerTransactionMapper ctMapper,
			VOSVoIPCallRecordMapper vosVoIPCallRecordMapper,
			NZAreaCodeListMapper nzAreaCodeListMapper,
			CustomerChorusBroadbandASIDRecordMapper customerChorusBroadbandASIDRecordMapper) {
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
		this.vosVoIPCallRecordMapper = vosVoIPCallRecordMapper;
		this.nzAreaCodeListMapper = nzAreaCodeListMapper;
		this.customerChorusBroadbandASIDRecordMapper = customerChorusBroadbandASIDRecordMapper;
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

	// BEGIN CustomerChorusBroadbandASIDRecord
	@Transactional
	public List<CustomerChorusBroadbandASIDRecord> queryCustomerChorusBroadbandASIDRecords(CustomerChorusBroadbandASIDRecord ccbasidr) {
		return this.customerChorusBroadbandASIDRecordMapper.selectCustomerChorusBroadbandASIDRecord(ccbasidr);
	}
	
	@Transactional
	public Page<CustomerChorusBroadbandASIDRecord> queryCustomerChorusBroadbandASIDRecordsByPage(Page<CustomerChorusBroadbandASIDRecord> page) {
		page.setTotalRecord(this.customerChorusBroadbandASIDRecordMapper.selectCustomerChorusBroadbandASIDRecordsSum(page));
		page.setResults(this.customerChorusBroadbandASIDRecordMapper.selectCustomerChorusBroadbandASIDRecordsByPage(page));
		return page;
	}

	@Transactional
	public void createCustomerChorusBroadbandASIDRecord(CustomerChorusBroadbandASIDRecord ccbasidr) {
		this.customerChorusBroadbandASIDRecordMapper.insertCustomerChorusBroadbandASIDRecord(ccbasidr);
	}
	
	@Transactional
	public int queryCustomerChorusBroadbandASIDRecordsSumByPage(Page<CustomerChorusBroadbandASIDRecord> page) {
		return this.customerChorusBroadbandASIDRecordMapper.selectCustomerChorusBroadbandASIDRecordsSum(page);
	}

	@Transactional
	public void removeCustomerChorusBroadbandASIDRecord(CustomerChorusBroadbandASIDRecord ccbasidr) {
		this.customerChorusBroadbandASIDRecordMapper.deleteCustomerChorusBroadbandASIDRecord(ccbasidr);
	}

	@Transactional
	public void editCustomerChorusBroadbandASIDRecord(CustomerChorusBroadbandASIDRecord ccbasidr) {
		this.customerChorusBroadbandASIDRecordMapper.updateCustomerChorusBroadbandASIDRecord(ccbasidr);
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
	
	public int queryCustomerCallRecordBySum(Page<CustomerCallRecord> page) {
		return this.customerCallRecordMapper.selectCustomerCallRecordsSum(page);
	}
	
	public List<CustomerCallRecord> queryCustomerCallRecords(CustomerCallRecord ccr) {
		return this.customerCallRecordMapper.selectCustomerCallRecord(ccr);
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

	public List<CustomerCallingRecordCallplus> queryCustomerCallingRecordCallpluss(CustomerCallingRecordCallplus ccrc) {
		return this.customerCallingRecordCallplusMapper.selectCustomerCallingRecordCallplus(ccrc);
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

	// BEGIN VOSVoIPCallRecord
	@Transactional
	public Page<VOSVoIPCallRecord> queryVOSVoIPCallRecordsByPage(Page<VOSVoIPCallRecord> page) {
		page.setTotalRecord(this.vosVoIPCallRecordMapper.selectVOSVoIPCallRecordsSum(page));
		page.setResults(this.vosVoIPCallRecordMapper.selectVOSVoIPCallRecordsByPage(page));
		return page;
	}

	@Transactional
	public void createVOSVoIPCallRecord(VOSVoIPCallRecord vosVoIPCallRecord) {
		this.vosVoIPCallRecordMapper.insertVOSVoIPCallRecord(vosVoIPCallRecord);
	}

	@Transactional 
	public List<VOSVoIPCallRecord> queryVOSVoIPCallRecord(VOSVoIPCallRecord vosVoIPCallRecord){
		return this.vosVoIPCallRecordMapper.selectVOSVoIPCallRecord(vosVoIPCallRecord);
	}

	@Transactional
	public void editVOSVoIPCallRecord(VOSVoIPCallRecord vosVoIPCallRecord) {
		this.vosVoIPCallRecordMapper.updateVOSVoIPCallRecord(vosVoIPCallRecord);
	}
	// END VOSVoIPCallRecord

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
	
	// Billing Chorus Calling Statistic
	@Transactional
	public List<CustomerCallRecord> queryCustomerCallRecordsByStatementDate(CustomerCallRecord ccr){
		return this.customerCallRecordMapper.selectCustomerCallRecordsByStatementDate(ccr);
	}
	
	// Billing NCA Calling Statistic
	@Transactional
	public List<CustomerCallingRecordCallplus> queryCustomerCallingRecordCallplussByDate(CustomerCallingRecordCallplus ccrc){
		return this.customerCallingRecordCallplusMapper.selectCustomerCallingRecordCallplussByDate(ccrc);
	}
	
	// Billing VOS Calling Statistic
	@Transactional
	public List<VOSVoIPCallRecord> queryVOSVoIPCallRecordsByCallStart(VOSVoIPCallRecord vvcr){
		return this.vosVoIPCallRecordMapper.selectVOSVoIPCallRecordsByCallStart(vvcr);
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
			
		} else if("vos-voip".equals(billing_type)){
			
			List<VOSVoIPCallRecord> vosVoIPCallRecords = VOSVoIPCallRecordUtility.vosVoIPCallRecords(filePath);
			
			for (VOSVoIPCallRecord vosVoIPCallRecord : vosVoIPCallRecords) {
				this.vosVoIPCallRecordMapper.insertVOSVoIPCallRecord(vosVoIPCallRecord);
			}
			
		} else if("chorus-broadband-asid".equals(billing_type)){
			
			CustomerChorusBroadbandASIDRecord ccbasidrDelete = new CustomerChorusBroadbandASIDRecord();
			ccbasidrDelete.getParams().put("statement_date", TMUtils.parseDateYYYYMMDD(statementDate));
			this.customerChorusBroadbandASIDRecordMapper.deleteCustomerChorusBroadbandASIDRecord(ccbasidrDelete);

			List<CustomerCallRecord> ccrs = CallingRecordUtility.ccrs(filePath);
			for (CustomerCallRecord ccr : ccrs) {
				
				if(ccr.getClear_service_id().toString().startsWith("1")
				&& "AC".equals(ccr.getRecord_type())){

					CustomerChorusBroadbandASIDRecord ccbasidr = new CustomerChorusBroadbandASIDRecord();
					ccbasidr.setStatement_date(ccr.getStatement_date());
					ccbasidr.setRecord_type(ccr.getRecord_type());
					ccbasidr.setClear_service_id(ccr.getClear_service_id());
					ccbasidr.setDate_from(ccr.getDate_from());
					ccbasidr.setDate_to(ccr.getDate_to());
					ccbasidr.setCharge_date_time(ccr.getCharge_date_time());
					ccbasidr.setDuration(ccr.getDuration());
					ccbasidr.setOot_id(ccr.getOot_id());
					ccbasidr.setBilling_description(ccr.getBilling_description());
					ccbasidr.setAmount_excl(ccr.getAmount_excl());
					ccbasidr.setAmount_incl(ccr.getAmount_incl());
					ccbasidr.setJuristiction(ccr.getJuristiction());
					ccbasidr.setPhone_called(ccr.getPhone_called());
					
					this.customerChorusBroadbandASIDRecordMapper.insertCustomerChorusBroadbandASIDRecord(ccbasidr);
					
				}
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
			this.vosVoIPRateMapper.insertVOSVoIPRate(vosVoIPRate);
		}
	}

	@Transactional
	public void insertVOSVoIPCallRecords(String save_path) {
	}
	
	@Transactional
	public List<NZAreaCodeList> queryNZAreaCodeList(NZAreaCodeList nzacl) {
		return this.nzAreaCodeListMapper.selectNZAreaCodeList(nzacl);
	}

	public Map<String, Object> queryMonthlyCallingStatistics(String callingType,
			Double callingBuyingAmount,
			Double callingSellingAmount,
			List<StatisticBilling> monthBillingCallingAmountInvoices) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();


		String callType = "";
		CallInternationalRate cir = new CallInternationalRate();
		
		if("all".equals(callingType) || "chorus".equals(callingType)){
			
			CustomerCallRecord ccrQuery = new CustomerCallRecord();
			ccrQuery.getParams().put("statement_date", monthBillingCallingAmountInvoices.get(0).getBillingDate());
			ccrQuery.getParams().put("where", "calling_only");
			ccrQuery.getParams().put("record_type", "T1");
			ccrQuery.getParams().put("billing_description", "local calls");
			List<CustomerCallRecord> monthCustomerChorusCallingAmounts = this.queryCustomerCallRecordsByStatementDate(ccrQuery);
			for (CustomerCallRecord ccr : monthCustomerChorusCallingAmounts) {
				
				callingBuyingAmount = TMUtils.bigAdd(callingBuyingAmount, ccr.getAmount_incl());
				
				switch (ccr.getJuristiction()) {
				case "O":	/* OTH NETWORK & Mobile */
					callType = "Mobile";
					cir.getParams().put("area_prefix", "2");
					break;
				case "L":	/* Local (Auckland) */
					callType = "Local";
					cir.getParams().put("area_prefix", "0");
					cir.getParams().put("rate_type", "Local");
					break;
				case "N":	/* National */
					callType = "Domestic";
					cir.getParams().put("area_prefix", "3");
					cir.getParams().put("rate_type", "Domestic");
					break;
				case "I":	/* International */
					callType = "International";
					cir.getParams().put("area_prefix", ccr.getPhone_called().substring(0, ccr.getPhone_called().indexOf("-")));
					break;
				}
				List<CallInternationalRate> cirs = this.queryCallInternationalRate(cir);
				boolean isOnRate = cirs!=null && cirs.size()>0 ? true : false;
				
				Double costPerMinute = 1d;
				if(isOnRate){ costPerMinute = cirs.get(0).getRate_cost(); }
				
				// DURATION/SECONDS
				Double duration = Double.parseDouble(TMUtils.fillDecimalTime(String.valueOf(TMUtils.bigDivide((double)ccr.getDuration(), 60d))));
				
				ccr.setCallType(TMUtils.strCapital(callType));
				
				if(isOnRate && duration>0){
					callingSellingAmount = TMUtils.bigAdd(callingSellingAmount, TMUtils.bigMultiply(duration, costPerMinute));
					
				} else {
					callingSellingAmount = TMUtils.bigAdd(callingSellingAmount, ccr.getAmount_incl());
				}
				
			}
		
		}
		if("all".equals(callingType) || "nca".equals(callingType)){
			
			CustomerCallingRecordCallplus ccrcQuery = new CustomerCallingRecordCallplus();
			ccrcQuery.getParams().put("date", monthBillingCallingAmountInvoices.get(0).getBillingDate());
			List<CustomerCallingRecordCallplus> monthCustomerNCACallingAmounts = this.queryCustomerCallingRecordCallplussByDate(ccrcQuery);
			for (CustomerCallingRecordCallplus ccrc : monthCustomerNCACallingAmounts) {
				
				callingBuyingAmount = TMUtils.bigAdd(callingBuyingAmount, ccrc.getCharged_fee());
				
				CallInternationalRate cirNCA = new CallInternationalRate();
				switch (ccrc.getType()) {
				case "M":	/* Mobile */
					callType = "Mobile";
					cirNCA.getParams().put("area_prefix", "2");
					break;
				case "MG":	/* Mobile Gsm */
					callType = "Mobile";
					cirNCA.getParams().put("area_prefix", "2");
					break;
				case "L":	/* Local (Auckland) */
					callType = "Local";
					cirNCA.getParams().put("area_prefix", "0");
					cirNCA.getParams().put("rate_type", "Local");
					break;
				case "S":	/* Domestic */
					callType = "Domestic";
					cirNCA.getParams().put("area_prefix", "3");
					cirNCA.getParams().put("rate_type", "Domestic");
					break;
				case "I":	/* International */
					callType = "International";
					String fix_mobile_country[] = "Bangladesh,Malaysia,Cambodia,Singapore,Canada,South Korea,China,Usa,Hong Kong,Vietnam,India".split(",");
					String fixMobileCountry = "";
					for (String country : fix_mobile_country) {
						if(ccrc.getDescription().contains(country)){
							fixMobileCountry = country;
						}
					}
					cirNCA.getParams().put("area_name", "".equals(fixMobileCountry) ? ccrc.getDescription() : fixMobileCountry);
					break;
				}
				
				List<CallInternationalRate> cirs = this.queryCallInternationalRate(cirNCA);
				boolean isOnRate = cirs!=null && cirs.size()>0 ? true : false;

				Double costPerMinute = 1d;
				if(isOnRate){ costPerMinute = cirs.get(0).getRate_cost(); }
				Double duration = Double.parseDouble(TMUtils.fillDecimalTime(String.valueOf(TMUtils.bigDivide((double)ccrc.getLength(), 60d))));

				if(isOnRate){
					callingSellingAmount = TMUtils.bigAdd(callingSellingAmount, TMUtils.bigMultiply(duration, costPerMinute));
					
				} else {
					callingSellingAmount = TMUtils.bigAdd(callingSellingAmount, ccrc.getCharged_fee());
				}
			
			}
		
		}
		if("all".equals(callingType) || "vos".equals(callingType)){
			
			VOSVoIPCallRecord vvcrQuery = new VOSVoIPCallRecord();
			vvcrQuery.getParams().put("call_start", monthBillingCallingAmountInvoices.get(0).getBillingDate());

			List<VOSVoIPCallRecord> monthVOSVoIPCallingAmounts = this.queryVOSVoIPCallRecordsByCallStart(vvcrQuery);
			for (VOSVoIPCallRecord vvcr : monthVOSVoIPCallingAmounts) {
				
				callingBuyingAmount = TMUtils.bigAdd(callingBuyingAmount, vvcr.getCall_fee());

				String area_prefix = String.valueOf(vvcr.getArea_prefix());

				VOSVoIPRate vvrQuery = new VOSVoIPRate();
				vvrQuery.getParams().put("area_prefix", area_prefix);
				
				List<VOSVoIPRate> vosVoIPRates = this.queryVOSVoIPRates(vvrQuery);
				
				// If Area Code Doesn't Matched, Maybe The Code Isn't Existed On The Rates Table, So Left 3 Digit And Search Again
				if(vosVoIPRates==null || vosVoIPRates.size()<=0){

					vvrQuery.getParams().put("area_prefix", area_prefix.substring(0, area_prefix.length()>=3 ? 3 : area_prefix.length()));
					vosVoIPRates = this.queryVOSVoIPRates(vvrQuery);
					
				}
				// If Area Code Doesn't Matched, Maybe The Code Isn't Existed On The Rates Table, So Left 2 Digit And Search Again
				if(vosVoIPRates==null || vosVoIPRates.size()<=0){

					vvrQuery.getParams().put("area_prefix", area_prefix.substring(0, area_prefix.length()>=2 ? 2 : area_prefix.length()));
					vosVoIPRates = this.queryVOSVoIPRates(vvrQuery);
					
				}
				
				boolean isOnRate = vosVoIPRates!=null && vosVoIPRates.size()>0 ? true : false;

				Double costPerMinute = 1d;
				if(isOnRate){ costPerMinute = vosVoIPRates.get(0).getBilled_per_min(); }
				
				// DURATION/SECONDS
				Double duration = Double.parseDouble(TMUtils.fillDecimalTime(String.valueOf(TMUtils.bigDivide((double)TMUtils.retrieveVoIPChargePerThreeMinutes(vvcr.getCall_length()), 60d))));

				if(isOnRate && duration>0){

					// If have reminder, then cut reminder and plus 1 minute, for example: 5.19 change to 6
					if(TMUtils.isReminder(String.valueOf(duration))){
						String durationStr = String.valueOf(duration);
						duration =  Double.parseDouble(durationStr.substring(0, durationStr.indexOf(".")))+1d;
					}
					
					callingSellingAmount = TMUtils.bigAdd(callingSellingAmount, TMUtils.bigMultiply(duration, costPerMinute));
					
				} else {
					callingSellingAmount = TMUtils.bigAdd(callingSellingAmount, vvcr.getCall_fee());
				}
			
			}
				
			
		}

		resultMap.put("buyingAmount", callingBuyingAmount);
		resultMap.put("sellingAmount", callingSellingAmount);
		
		return resultMap;
		
	}
	

	public Map<String, Object> queryMonthlyCallingStatistics(String billingType, String statusType){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Calendar calLastMonth = Calendar.getInstance();
		calLastMonth.add(Calendar.MONTH, -1);

		
		if("chorus".equals(billingType)){
			
			if("unmatched".equals(statusType)){
				
				CustomerCallRecord ccrQuery = new CustomerCallRecord();
				ccrQuery.getParams().put("where", "query_unmatched_records");
				ccrQuery.getParams().put("groupby", "group by clear_service_id");
				ccrQuery.getParams().put("last_month_date", calLastMonth.getTime());
				
				List<CustomerCallRecord> ccrs = this.customerCallRecordMapper.selectCustomerCallRecord(ccrQuery);
				resultMap.put("chorusUnmatched", ccrs);
				
			}
			
			if("disconnected".equals(statusType)){
				
				CustomerCallRecord ccrQuery = new CustomerCallRecord();
				ccrQuery.getParams().put("where", "query_disconnected_records");
				ccrQuery.getParams().put("groupby", "group by clear_service_id");
				ccrQuery.getParams().put("last_month_date", calLastMonth.getTime());
				
				List<CustomerCallRecord> ccrs = this.customerCallRecordMapper.selectCustomerCallRecord(ccrQuery);
				resultMap.put("chorusDisconnected", ccrs);
				
			}
			
		}
		if("nca".equals(billingType)){
			
			if("unmatched".equals(statusType)){
				
				CustomerCallingRecordCallplus ccrcQuery = new CustomerCallingRecordCallplus();
				ccrcQuery.getParams().put("where", "query_unmatched_records");
				ccrcQuery.getParams().put("groupby", "group by original_number");
				ccrcQuery.getParams().put("last_month_date", calLastMonth.getTime());
				
				List<CustomerCallingRecordCallplus> ccrcs = this.customerCallingRecordCallplusMapper.selectCustomerCallingRecordCallplus(ccrcQuery);
				resultMap.put("ncaUnmatched", ccrcs);
				
			}
			
			if("disconnected".equals(statusType)){
				
				CustomerCallingRecordCallplus ccrcQuery = new CustomerCallingRecordCallplus();
				ccrcQuery.getParams().put("where", "query_disconnected_records");
				ccrcQuery.getParams().put("groupby", "group by original_number");
				ccrcQuery.getParams().put("last_month_date", calLastMonth.getTime());
				
				List<CustomerCallingRecordCallplus> ccrcs = this.customerCallingRecordCallplusMapper.selectCustomerCallingRecordCallplus(ccrcQuery);
				resultMap.put("ncaDisconnected", ccrcs);
				
			}
			
		}
		if("vos".equals(billingType)){
			
			if("unmatched".equals(statusType)){
				
				VOSVoIPCallRecord vosQuery = new VOSVoIPCallRecord();
				vosQuery.getParams().put("where", "query_unmatched_records");
				vosQuery.getParams().put("groupby", "group by ori_number");
				vosQuery.getParams().put("last_month_date", calLastMonth.getTime());
				
				List<VOSVoIPCallRecord> voss = this.vosVoIPCallRecordMapper.selectVOSVoIPCallRecord(vosQuery);
				resultMap.put("vosUnmatched", voss);
				
			}
			
			if("disconnected".equals(statusType)){
				
				VOSVoIPCallRecord vosQuery = new VOSVoIPCallRecord();
				vosQuery.getParams().put("where", "query_disconnected_records");
				vosQuery.getParams().put("groupby", "group by ori_number");
				vosQuery.getParams().put("last_month_date", calLastMonth.getTime());
				
				List<VOSVoIPCallRecord> voss = this.vosVoIPCallRecordMapper.selectVOSVoIPCallRecord(vosQuery);
				resultMap.put("vosDisconnected", voss);
				
			}
			
		}
		if("asid".equals(billingType)){
			
			if("unmatched".equals(statusType)){
				
				CustomerChorusBroadbandASIDRecord asidQuery = new CustomerChorusBroadbandASIDRecord();
				asidQuery.getParams().put("where", "query_unmatched_records");
				asidQuery.getParams().put("groupby", "group by clear_service_id");
				asidQuery.getParams().put("last_month_date", calLastMonth.getTime());
				
				List<CustomerChorusBroadbandASIDRecord> asids = this.customerChorusBroadbandASIDRecordMapper.selectCustomerChorusBroadbandASIDRecord(asidQuery);
				resultMap.put("asidUnmatched", asids);
				
			}
			
			if("disconnected".equals(statusType)){
				
				CustomerChorusBroadbandASIDRecord asidQuery = new CustomerChorusBroadbandASIDRecord();
				asidQuery.getParams().put("where", "query_disconnected_records");
				asidQuery.getParams().put("groupby", "group by clear_service_id");
				asidQuery.getParams().put("last_month_date", calLastMonth.getTime());
				
				List<CustomerChorusBroadbandASIDRecord> asids = this.customerChorusBroadbandASIDRecordMapper.selectCustomerChorusBroadbandASIDRecord(asidQuery);
				resultMap.put("asidDisconnected", asids);
				
			}
			
		}
		
		return resultMap;
		
	}
	
}
