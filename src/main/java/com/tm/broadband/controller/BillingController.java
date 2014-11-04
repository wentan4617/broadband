package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tm.broadband.email.ApplicationEmail;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.model.EarlyTerminationChargeParameter;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.StatisticBilling;
import com.tm.broadband.model.TerminationRefund;
import com.tm.broadband.model.User;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.model.VoucherFileUpload;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.MailerService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.MailRetriever;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.VoucherRecordUtility;

@Controller
public class BillingController {

	private BillingService billingService;
	private SystemService systemService;
	private CRMService crmService;
	private MailerService mailerService;
	
	@Autowired
	public BillingController(BillingService billingService
			,SystemService systemService
			,CRMService crmService
			,MailerService mailerService) {
		this.billingService = billingService;
		this.systemService = systemService;
		this.crmService = crmService;
		this.mailerService = mailerService;
	}
	
	// BEGIN EarlyTerminationCharge
	@RequestMapping("/broadband-user/billing/early-termination-charge/view/{pageNo}/{status}")
	public String toEarlyTerminationCharge(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status) {

		Page<EarlyTerminationCharge> page = new Page<EarlyTerminationCharge>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "ORDER BY create_date DESC");
		page.getParams().put("status", status);
		page = this.billingService.queryEarlyTerminationChargesByPage(page);
		
		EarlyTerminationChargeParameter etcp = this.billingService.queryEarlyTerminationChargeParameter();
		
		model.addAttribute("etcp", etcp);
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");
		model.addAttribute("users", this.systemService.queryUser(new User()));

		// BEGIN QUERY SUM BY STATUS
		Page<EarlyTerminationCharge> pageStatusSum = new Page<EarlyTerminationCharge>();
		pageStatusSum.getParams().put("status", "unpaid");
		model.addAttribute("unpaidSum", this.billingService.queryEarlyTerminationChargesSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("status", "paid");
		model.addAttribute("paidSum", this.billingService.queryEarlyTerminationChargesSumByPage(pageStatusSum));
		// END QUERY SUM BY STATUS
		
		return "broadband-user/billing/early-termination-charge-view";
	}
	
	// BEGIN EarlyTerminationChargeStatusEdit
	@RequestMapping("/broadband-user/billing/early-termination-charge/edit/status")
	public String doEarlyTerminationChargeStatusEdit(Model model
			, @RequestParam("termination_id") int termination_id
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("detailStatus") String detailStatus) {
		
		EarlyTerminationCharge etc = new EarlyTerminationCharge();
		etc.setStatus(detailStatus);
		etc.getParams().put("id", termination_id);
		this.billingService.editEarlyTerminationCharge(etc);
		
		return "redirect:/broadband-user/billing/early-termination-charge/view/" + pageNo + "/" + status;
	}
	
	// download invoice PDF directly
	@RequestMapping(value = "/broadband-user/early-termination-charge/pdf/download/{early_termination_id}")
    public ResponseEntity<byte[]> downloadInvoicePDF(Model model
    		,@PathVariable(value = "early_termination_id") int early_termination_id) throws IOException {
		String filePath = this.billingService.queryEarlyTerminationChargePDFPathById(early_termination_id);
		
		// get file path
        Path path = Paths.get(filePath);
        byte[] contents = null;
        // transfer file contents to bytes
        contents = Files.readAllBytes( path );
        
        HttpHeaders headers = new HttpHeaders();
        // set spring framework media type
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        // get file name with file's suffix
        String filename = URLEncoder.encode(filePath.substring(filePath.lastIndexOf(File.separator)+1, filePath.indexOf("."))+".pdf", "UTF-8");
        headers.setContentDispositionFormData( filename, filename );
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>( contents, headers, HttpStatus.OK );
        return response;
    }
	
	// BEGIN TerminationRefund
	@RequestMapping("/broadband-user/billing/termination-refund/view/{pageNo}/{status}")
	public String toTerminationRefund(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status) {

		Page<TerminationRefund> page = new Page<TerminationRefund>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "ORDER BY create_date DESC");
		page.getParams().put("status", status);
		page = this.billingService.queryTerminationRefundsByPage(page);
		
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");
		model.addAttribute("users", this.systemService.queryUser(new User()));

		// BEGIN QUERY SUM BY STATUS
		Page<TerminationRefund> pageStatusSum = new Page<TerminationRefund>();
		pageStatusSum.getParams().put("status", "unpaid");
		model.addAttribute("unpaidSum", this.billingService.queryTerminationRefundsSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("status", "paid");
		model.addAttribute("paidSum", this.billingService.queryTerminationRefundsSumByPage(pageStatusSum));
		// END QUERY SUM BY STATUS
		
		return "broadband-user/billing/termination-refund-view";
	}
	
	// BEGIN EarlyTerminationChargeStatusEdit
	@RequestMapping("/broadband-user/billing/termination-refund/edit/status")
	public String doTerminationRefundStatusEdit(Model model
			, @RequestParam("termination_id") int termination_id
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("detailStatus") String detailStatus) {
		
		TerminationRefund tr = new TerminationRefund();
		tr.setStatus(detailStatus);
		tr.getParams().put("id", termination_id);
		this.billingService.editTerminationRefund(tr);
		
		return "redirect:/broadband-user/billing/termination-refund/view/" + pageNo + "/" + status;
	}
	
	// download refund PDF directly
	@RequestMapping(value = "/broadband-user/termination-refund/pdf/download/{termination_refund_id}")
    public ResponseEntity<byte[]> downloadRefundPDF(Model model
    		,@PathVariable(value = "termination_refund_id") int termination_refund_id) throws IOException {
		String filePath = this.billingService.queryTerminationRefundPDFPathById(termination_refund_id);
		
		// get file path
        Path path = Paths.get(filePath);
        byte[] contents = null;
        // transfer file contents to bytes
        contents = Files.readAllBytes( path );
        
        HttpHeaders headers = new HttpHeaders();
        // set spring framework media type
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        // get file name with file's suffix
        String filename = URLEncoder.encode(filePath.substring(filePath.lastIndexOf(File.separator)+1, filePath.indexOf("."))+".pdf", "UTF-8");
        headers.setContentDispositionFormData( filename, filename );
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>( contents, headers, HttpStatus.OK );
        return response;
    }
	
	// BEGIN InvoiceView
	@RequestMapping("/broadband-user/billing/invoice/view/{customer_type}/{pageNo}/{status}/{yearOrMonth}")
	public String toInvoice(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status
			, @PathVariable("customer_type") String customer_type
			, @PathVariable("yearOrMonth") String yearOrMonth) {
		
		Date year = null;
		Date yearMonth = null;
		Calendar cal = Calendar.getInstance();
		if(yearOrMonth.length()==4) {
			cal.set(Calendar.YEAR, Integer.parseInt(yearOrMonth));
			year = cal.getTime();
		} else if(yearOrMonth.contains("-")) {
			cal.set(Calendar.MONTH, Integer.parseInt(yearOrMonth.substring(yearOrMonth.indexOf("-")+1, yearOrMonth.length()))-1);
			yearMonth = cal.getTime();
		}

		Page<CustomerInvoice> pageCis = new Page<CustomerInvoice>();
		if(!"prepayment".equals(status)){
			pageCis.getParams().put("status", "bad_debit".equals(status) ? "bad-debit" : status);
		}
		pageCis.setPageNo(pageNo);
		pageCis.setPageSize(30);
		if(year!=null){
			pageCis.getParams().put("create_date_year", year);
		}
		if(yearMonth!=null){
			pageCis.getParams().put("create_date_month", yearMonth);
		}
		pageCis.getParams().put("orderby", "ORDER BY create_date DESC");
		pageCis.getParams().put("customer_type", customer_type);
		
//		if("orderNoInvoice".equals(status)){
//			Page<CustomerOrder> pageCos = new Page<CustomerOrder>();
//			pageCos.setPageNo(pageNo);
//			pageCos.setPageSize(30);
//			pageCos.getParams().put("where", "query_no_invoice");
//			pageCos.getParams().put("orderby", "ORDER BY order_create_date DESC");
//			model.addAttribute("pageCos", this.crmService.queryCustomerOrdersByPage(pageCos));
//			
//			pageCos = null;
//		} else
			if("unpaid".equals(status)){
			pageCis.getParams().put("where", "non_pending");
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("overdue".equals(status)){
			pageCis.getParams().put("where", "non_pending");
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("prepayment".equals(status)){
			pageCis.getParams().put("prepayment", true);
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("bad_debit".equals(status)){
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("not_pay_off".equals(status)){
			pageCis.getParams().put("where", "non_pending");
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("pending".equals(status)) {
			pageCis = new Page<CustomerInvoice>();
			pageCis.getParams().put("where", "pending");
			pageCis.getParams().put("payment_status", status);
			pageCis.getParams().put("status11", "unpaid");
			pageCis.getParams().put("status12", "not_pay_off");
			pageCis.getParams().put("status13", "overdue");
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("void".equals(status)){
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("paid".equals(status)){
			pageCis.getParams().put("non_prepayment", true);
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
		}
		pageCis = null;
		
		model.addAttribute(
			yearOrMonth.contains("-") ? "yearMonth" : 
			yearOrMonth.length()==4 ? "year" : "all", yearOrMonth);
		model.addAttribute("customer_type", customer_type);
		model.addAttribute("status", status);
		if("all".equals(yearOrMonth)){
			model.addAttribute("allActive", "active");
			model.addAttribute(status + "Active", "active");
		} else {
			model.addAttribute(status + "Active", "active");
		}
		model.addAttribute("users", this.systemService.queryUser(new User()));
		

		// BEGIN QUERY SUM BY STATUS
		Page<CustomerInvoice> pageStatusSum = new Page<CustomerInvoice>();
		if(year!=null){
			pageStatusSum.getParams().put("create_date_year", year);
		}
		if(yearMonth!=null){
			pageStatusSum.getParams().put("create_date_month", yearMonth);
		}
		pageStatusSum.getParams().put("where", "pending");
		pageStatusSum.getParams().put("customer_type", customer_type);
		pageStatusSum.getParams().put("payment_status", "pending");
		pageStatusSum.getParams().put("status11", "unpaid");
		pageStatusSum.getParams().put("status12", "not_pay_off");
		pageStatusSum.getParams().put("status13", "overdue");
		Integer sumPending = this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum);
		model.addAttribute("pendingSum", sumPending);
		pageStatusSum = null;
		
		pageStatusSum = new Page<CustomerInvoice>();
		if(year!=null){
			pageStatusSum.getParams().put("create_date_year", year);
		}
		if(yearMonth!=null){
			pageStatusSum.getParams().put("create_date_month", yearMonth);
		}
		pageStatusSum.getParams().put("customer_type", customer_type);
		pageStatusSum.getParams().put("prepayment", true);
		Integer sumPrepayment = this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum);
		model.addAttribute("prepaymentSum", sumPrepayment);
		
		pageStatusSum.getParams().remove("prepayment");
		pageStatusSum.getParams().put("non_prepayment", true);
		pageStatusSum.getParams().put("status", "paid");
		Integer sumPaid = this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum);
		model.addAttribute("paidSum", sumPaid);

		pageStatusSum.getParams().remove("non_prepayment");
		pageStatusSum.getParams().put("status", "void");
		Integer sumVoid = this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum);
		model.addAttribute("voidSum", sumVoid);

		pageStatusSum.getParams().put("status", "bad-debit");
		Integer sumBadDebit = this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum);
		model.addAttribute("badDebitSum", sumBadDebit);

		pageStatusSum.getParams().put("where", "non_pending");
		pageStatusSum.getParams().put("status", "unpaid");
		Integer sumUnpaid = this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum);
		model.addAttribute("unpaidSum", sumUnpaid);

		pageStatusSum.getParams().put("status", "overdue");
		Integer sumOverdue = this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum);
		model.addAttribute("overdueSum", sumOverdue);
		
		pageStatusSum.getParams().put("status", "not_pay_off");
		Integer sumNotPayOff = this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum);
		model.addAttribute("notPayOffSum", sumNotPayOff);
		pageStatusSum = null;
		
		model.addAttribute("allSum", sumNotPayOff+sumOverdue+sumUnpaid+sumBadDebit+sumVoid+sumPaid+sumPrepayment+sumPending);
		
//		Page<CustomerOrder> pageStatusCoSum = new Page<CustomerOrder>();
//		pageStatusCoSum.getParams().put("where", "query_no_invoice");
//		pageStatusCoSum.getParams().put("customer_type", customer_type);
//		model.addAttribute("orderNoInvoiceSum", this.crmService.queryCustomerOrdersSumByPage(pageStatusCoSum));
		// END QUERY SUM BY STATUS
		
		return "broadband-user/billing/invoice-view";
	}
	
	// BEGIN TransactionView
	@RequestMapping("/broadband-user/billing/transaction/view/{pageNo}/{cardName}/{yearOrMonth}")
	public String toTransaction(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("cardName") String cardName
			, @PathVariable("yearOrMonth") String yearOrMonth) {
		
		Date year = null;
		Date yearMonth = null;
		Calendar cal = Calendar.getInstance();
		if(yearOrMonth.length()==4) {
			cal.set(Calendar.YEAR, Integer.parseInt(yearOrMonth));
			year = cal.getTime();
		} else if(yearOrMonth.contains("-")) {
			cal.set(Calendar.MONTH, Integer.parseInt(yearOrMonth.substring(yearOrMonth.indexOf("-")+1, yearOrMonth.length()))-1);
			yearMonth = cal.getTime();
		}
		
		System.out.println("pageNo: "+pageNo);
		System.out.println("cardName: "+cardName);
		System.out.println("yearOrMonth: "+yearOrMonth);

		Page<CustomerTransaction> page = new Page<CustomerTransaction>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		if(year!=null){
			page.getParams().put("transaction_date_year", year);
		}
		if(yearMonth!=null){
			page.getParams().put("transaction_date_month", yearMonth);
		}
		page.getParams().put("orderby", "ORDER BY transaction_date DESC");
		
		if("visa".equals(cardName)){
			page.getParams().put("card_name", "Visa");
			page = this.crmService.queryCustomerTransactionsByPage(page);
			model.addAttribute("page", page);
			
		} else if("masterCard".equals(cardName)){
			page.getParams().put("card_name", "MasterCard");
			page = this.crmService.queryCustomerTransactionsByPage(page);
			model.addAttribute("page", page);
			
		} else if("cash".equals(cardName)){
			page.getParams().put("card_name", "Cash");
			page = this.crmService.queryCustomerTransactionsByPage(page);
			model.addAttribute("page", page);
			
		} else if("a2a".equals(cardName)){
			page.getParams().put("where", "card_name2");
			page.getParams().put("card_name", "Account2Account");
			page.getParams().put("card_name2", "Account-2-Account");
			page = this.crmService.queryCustomerTransactionsByPage(page);
			model.addAttribute("page", page);
			
		} else if("ddpay".equals(cardName)){
			page.getParams().put("card_name", "DDPay");
			page = this.crmService.queryCustomerTransactionsByPage(page);
			model.addAttribute("page", page);
			
		} else if("creditCard".equals(cardName)) {
			page.getParams().put("card_name", "Credit Card");
			page = this.crmService.queryCustomerTransactionsByPage(page);
			model.addAttribute("page", page);
			
		} else if("accountCredit".equals(cardName)){
			page.getParams().put("where", "card_name3");
			page.getParams().put("card_name", "account-credit");
			page.getParams().put("card_name2", "Account Credit");
			page.getParams().put("card_name3", "Account Credit# - ");
			page = this.crmService.queryCustomerTransactionsByPage(page);
			model.addAttribute("page", page);
			
		} else if("cyberparkCredit".equals(cardName)){
			page.getParams().put("card_name", "CyberPark Credit");
			page = this.crmService.queryCustomerTransactionsByPage(page);
			model.addAttribute("page", page);
			
		} else if("voucher".equals(cardName)){
			page.getParams().put("card_name", "Voucher");
			page = this.crmService.queryCustomerTransactionsByPage(page);
			model.addAttribute("page", page);
		}
		page = null;
		
		model.addAttribute(
			yearOrMonth.contains("-") ? "yearMonth" : 
			yearOrMonth.length()==4 ? "year" : "all", yearOrMonth);
		model.addAttribute("transactionType", cardName);
		if("all".equals(yearOrMonth)){
			model.addAttribute("allActive", "active");
			model.addAttribute(cardName + "Active", "active");
		} else {
			model.addAttribute(cardName + "Active", "active");
		}
		model.addAttribute("users", this.systemService.queryUser(new User()));
		

		// BEGIN QUERY SUM BY TRANSACTION TYPE
		Page<CustomerTransaction> pageStatusSum = new Page<CustomerTransaction>();
		
		if(year!=null){
			pageStatusSum.getParams().put("transaction_date_year", year);
		}
		if(yearMonth!=null){
			pageStatusSum.getParams().put("transaction_date_month", yearMonth);
		}
		
		pageStatusSum.getParams().put("card_name", "Visa");
		Integer sumVisa = this.crmService.queryCustomerTransactionsSumByPage(pageStatusSum);
		model.addAttribute("visaSum", sumVisa);
		
		pageStatusSum.getParams().put("card_name", "MasterCard");
		Integer sumMasterCard = this.crmService.queryCustomerTransactionsSumByPage(pageStatusSum);
		model.addAttribute("masterCardSum", sumMasterCard);
		
		pageStatusSum.getParams().put("card_name", "Cash");
		Integer sumCash = this.crmService.queryCustomerTransactionsSumByPage(pageStatusSum);
		model.addAttribute("cashSum", sumCash);
		
		pageStatusSum.getParams().put("card_name", "DDPay");
		Integer sumDDPay = this.crmService.queryCustomerTransactionsSumByPage(pageStatusSum);
		model.addAttribute("ddpaySum", sumDDPay);
		
		pageStatusSum.getParams().put("card_name", "Credit Card");
		Integer sumCreditCard = this.crmService.queryCustomerTransactionsSumByPage(pageStatusSum);
		model.addAttribute("creditCardSum", sumCreditCard);
		
		pageStatusSum.getParams().put("card_name", "CyberPark Credit");
		Integer sumCyberParkCredit = this.crmService.queryCustomerTransactionsSumByPage(pageStatusSum);
		model.addAttribute("cyberparkCreditSum", sumCyberParkCredit);
		
		pageStatusSum.getParams().put("card_name", "Voucher");
		Integer sumVoucher = this.crmService.queryCustomerTransactionsSumByPage(pageStatusSum);
		model.addAttribute("voucherSum", sumVoucher);

		pageStatusSum.getParams().put("where", "card_name2");
		pageStatusSum.getParams().put("card_name", "Account2Account");
		pageStatusSum.getParams().put("card_name2", "Account-2-Account");
		Integer sumA2A = this.crmService.queryCustomerTransactionsSumByPage(pageStatusSum);
		model.addAttribute("a2aSum", sumA2A);

		pageStatusSum.getParams().put("where", "card_name3");
		pageStatusSum.getParams().put("card_name", "account-credit");
		pageStatusSum.getParams().put("card_name2", "Account Credit");
		pageStatusSum.getParams().put("card_name3", "Account Credit# - ");
		Integer sumAccountCredit = this.crmService.queryCustomerTransactionsSumByPage(pageStatusSum);
		model.addAttribute("accountCreditSum", sumAccountCredit);
		pageStatusSum = null;
		
		model.addAttribute("allSum", sumVoucher+sumCyberParkCredit+sumAccountCredit+sumCreditCard+sumDDPay+sumA2A+sumCash+sumMasterCard+sumVisa);
		
		// END QUERY SUM BY STATUS
		
		return "broadband-user/billing/transaction-view";
	}
	
	@RequestMapping("/broadband-user/billing/voucher-file-upload-record/view/{pageNo}/{status}")
	public String toVoucherFileUpload(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status) {

		Page<VoucherFileUpload> page = new Page<VoucherFileUpload>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "ORDER BY upload_date DESC");
		page.getParams().put("status", status);
		page = this.billingService.queryVoucherFileUploadsByPage(page);
		
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");

		// BEGIN QUERY SUM BY STATUS
		Page<VoucherFileUpload> pageStatusSum = new Page<VoucherFileUpload>();
		pageStatusSum.getParams().put("status", "activated");
		model.addAttribute("activatedSum", this.billingService.queryVoucherFileUploadsSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("status", "inactivated");
		model.addAttribute("inactivatedSum", this.billingService.queryVoucherFileUploadsSumByPage(pageStatusSum));
		// END QUERY SUM BY STATUS
		
		model.addAttribute("users", this.systemService.queryUser(new User()));
		
		
		return "broadband-user/billing/voucher-file-upload-view";
	}
	
	// Upload Call Billing Record CSV
	@RequestMapping(value = "/broadband-user/billing/voucher-file-upload-record/csv/upload", method = RequestMethod.POST)
	public String uploadVoucherCSVFile(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("voucher_csv_file") MultipartFile voucher_csv_file
			, HttpServletRequest req) {
		
		String fileName = voucher_csv_file.getOriginalFilename();

		if(!voucher_csv_file.isEmpty()){
			String save_path = TMUtils.createPath("broadband" + File.separator
					+ "voucher_csv_file" + File.separator + fileName);
			try {
				voucher_csv_file.transferTo(new File(save_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			User user = (User) req.getSession().getAttribute("userSession");
			VoucherFileUpload vfu = new VoucherFileUpload();
			vfu.setFile_name(fileName);
			vfu.setFile_path(save_path);
			vfu.setStatus("inactivated");
			vfu.setUpload_by(user.getId());
			vfu.setUpload_date(new Date());
			this.billingService.createVoucherFileUpload(vfu);
		}
		
		return "redirect:/broadband-user/billing/voucher-file-upload-record/view/" + pageNo + "/" + status;
	}
	
	// Download Call Billing Record CSV
	@RequestMapping(value = "/broadband-user/billing/voucher-file-upload-record/csv/download/{voucherFileId}")
    public ResponseEntity<byte[]> downloadVoucherCSV(Model model
    		,@PathVariable(value = "voucherFileId") int voucherFileId) throws IOException {
		String filePath = this.billingService.queryVoucherFileUploadCSVPathById(voucherFileId);
		
		// get file path
        Path path = Paths.get(filePath);
        byte[] contents = null;
        // transfer file contents to bytes
        contents = Files.readAllBytes( path );
        
        HttpHeaders headers = new HttpHeaders();
        // set spring framework media type
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        // get file name with file's suffix
        String filename = URLEncoder.encode(filePath.substring(filePath.lastIndexOf(File.separator)+1, filePath.indexOf("."))+".csv", "UTF-8");
        headers.setContentDispositionFormData( filename, filename );
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>( contents, headers, HttpStatus.OK );
        return response;
    }
	
	// Delete Call Billing Record CSV
	@RequestMapping(value = "/broadband-user/billing/voucher-file-upload-record/csv/delete", method = RequestMethod.POST)
	public String deleteBillingFileCSV(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("voucherFileId") Integer voucherFileId
			, @RequestParam("filePath") String filePath
			, HttpServletRequest req) {
		
		this.billingService.removeVoucherFileUploadBySerialNumber(voucherFileId);
		
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		
		return "redirect:/broadband-user/billing/voucher-file-upload-record/view/" + pageNo + "/" + status;
	}
	
	// Insert Call Billing Record CSV into database
	@RequestMapping(value = "/broadband-user/billing/voucher-file-upload-record/csv/insert", method = RequestMethod.POST)
	public String insertBillingFileCSVIntoDatabase(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("voucherFileId") String voucherFileId
			, @RequestParam("filePath") String filePath
			, HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute("userSession");
		VoucherFileUpload vfu = new VoucherFileUpload();
		vfu.getParams().put("id", voucherFileId);
		vfu.setInserted_by(user.getId());
		vfu.setInserted_date(new Date());
		vfu.setStatus("activated");
		this.billingService.editVoucherFileUpload(vfu);
		
		// Get All data from the CSV file
		List<Voucher> vs = VoucherRecordUtility.vs(filePath);
		
		// Iteratively insert into database
		for (Voucher v : vs) {
			v.setStatus("unused");
			this.billingService.createVoucher(v);
		}
		
		return "redirect:/broadband-user/billing/voucher-file-upload-record/view/" + pageNo + "/" + status;
	}
	
	@RequestMapping("/broadband-user/billing/voucher/view/{pageNo}/{status}")
	public String toVoucher(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status) {

		Page<Voucher> page = new Page<Voucher>();
		page.setPageNo(pageNo);
		page.setPageSize(500);
		page.getParams().put("orderby", "ORDER BY serial_number ASC");
		switch (status) {
		case "used":
			page.getParams().put("status", status);		
			break;
		case "unused":
			page.getParams().put("status", status);
			break;
		case "posted":
			page.getParams().put("where", "query_posted");
			break;
		case "unpost":
			page.getParams().put("where", "query_unpost");
			break;
		}
		page = this.billingService.queryVouchersByPage(page);
		
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");

		// BEGIN QUERY SUM BY STATUS
		Page<Voucher> pageStatusSum = new Page<Voucher>();
		pageStatusSum.getParams().put("status", "used");
		model.addAttribute("usedSum", this.billingService.queryVouchersSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("status", "unused");
		model.addAttribute("unusedSum", this.billingService.queryVouchersSumByPage(pageStatusSum));
		Page<Voucher> pagePostedStatusSum = new Page<Voucher>();
		pagePostedStatusSum.getParams().put("where", "query_posted");
		model.addAttribute("postedSum", this.billingService.queryVouchersSumByPage(pagePostedStatusSum));
		pagePostedStatusSum.getParams().put("where", "query_unpost");
		model.addAttribute("unpostSum", this.billingService.queryVouchersSumByPage(pagePostedStatusSum));
		// END QUERY SUM BY STATUS
		
		
		return "broadband-user/billing/voucher-view";
	}
    
	// Send Early Termination Charge PDF directly
	@RequestMapping(value = "/broadband-user/billing/early_termination_charge/pdf/send/{terminationChargeId}/{orderId}")
	public String sendEarlyTerminationChargePDF(Model model,
			@PathVariable(value = "terminationChargeId") int terminationChargeId,
			@PathVariable(value = "orderId") int orderId) {
		String filePath = this.billingService.queryEarlyTerminationChargePDFPathById(terminationChargeId);
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", orderId);
		coQuery = this.crmService.queryCustomerOrder(coQuery);
		
		Customer cQuery = new Customer();
		cQuery.getParams().put("id", coQuery.getCustomer_id());
		cQuery = this.crmService.queryCustomer(cQuery);
		
		Notification notification = this.systemService.queryNotificationBySort("early-termination-charge", "email");
		CompanyDetail company = this.systemService.queryCompanyDetail();
		
		MailRetriever.mailAtValueRetriever(notification, cQuery, coQuery, company);
		
		ApplicationEmail applicationEmail = new ApplicationEmail();
		// setting properties and sending mail to customer email address
		// recipient
		applicationEmail.setAddressee(coQuery.getEmail());
		// subject
		applicationEmail.setSubject(notification.getTitle());
		// content
		applicationEmail.setContent(notification.getContent());
		// attachment name
		applicationEmail.setAttachName("early_termination_charge_" + ("personal".equals(coQuery.getCustomer_type()) ? coQuery.getLast_name()+" "+ coQuery.getFirst_name() : coQuery.getOrg_name()!=null ? coQuery.getOrg_name() : "") + ".pdf");
		// attachment path
		applicationEmail.setAttachPath(filePath);
		
		// send mail
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		
		filePath = null;
		notification = null;
		company = null;
		coQuery = null;
		applicationEmail = null;
		
		return "broadband-user/progress-accomplished";
	}
    
	// Send Termination Refund PDF directly
	@RequestMapping(value = "/broadband-user/billing/termination_refund/pdf/send/{terminationRefundId}/{orderId}")
	public String sendTerminationRefundPDF(Model model,
			@PathVariable(value = "terminationRefundId") int terminationRefundId,
			@PathVariable(value = "orderId") int orderId) {
		String filePath = this.billingService.queryTerminationRefundPDFPathById(terminationRefundId);
		CustomerOrder coQuery = new CustomerOrder();
		coQuery.getParams().put("id", orderId);
		CustomerOrder co = this.crmService.queryCustomerOrder(coQuery);
		Notification notification = this.systemService.queryNotificationBySort("termination-refund", "email");
		CompanyDetail company = this.systemService.queryCompanyDetail();
		
		MailRetriever.mailAtValueRetriever(notification, co, company);
		
		ApplicationEmail applicationEmail = new ApplicationEmail();
		// setting properties and sending mail to customer email address
		// recipient
		applicationEmail.setAddressee(co.getEmail());
		// subject
		applicationEmail.setSubject(notification.getTitle());
		// content
		applicationEmail.setContent(notification.getContent());
		// attachment name
		applicationEmail.setAttachName("termination_refund_" + ("personal".equals(co.getCustomer_type()) ? co.getLast_name()+" "+ co.getFirst_name() : co.getOrg_name()!=null ? co.getOrg_name() : "") + ".pdf");
		// attachment path
		applicationEmail.setAttachPath(filePath);
		
		// send mail
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		
		filePath = null;
		notification = null;
		company = null;
		coQuery = null;
		co = null;
		applicationEmail = null;
		
		return "broadband-user/progress-accomplished";
	}
	
	
	/**
	 * Billing Transaction Statistics
	 */
	@RequestMapping(value = "/broadband-user/billing/chart/transaction-statistic/{yearMonth}")
	public String toChartTransactionStatistics(Model model,
			@PathVariable(value = "yearMonth") String yearMonth) {
		
		model.addAttribute("panelheading", "Customer Transaction Statistics");
		
		/**
		 * WEEK STATISTIC BEGIN
		 */
		List<StatisticBilling> weekPaidStatistics = new ArrayList<StatisticBilling>();
		TMUtils.thisWeekBillingStatistic(weekPaidStatistics);
		
		List<CustomerTransaction> weekCustomerTransactions = this.billingService.queryCustomerTransactionsByTransactionDate(
				// monday
				weekPaidStatistics.get(0).getBillingDate()
				// sunday
				,weekPaidStatistics.get(weekPaidStatistics.size()-1).getBillingDate()
			);
		for (StatisticBilling statisticBilling : weekPaidStatistics) {
			for (CustomerTransaction ct : weekCustomerTransactions) {
				if(TMUtils.dateFormatYYYYMMDD(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMMDD(ct.getTransaction_date()))){
					statisticBilling.setBillingAmount(TMUtils.bigAdd(
						statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, ct.getAmount_settlement())
					);
				}
			}
		}
		
		model.addAttribute("weekPaidStatistics", weekPaidStatistics);
		/**
		 * WEEK STATISTIC END
		 */
		
		/**
		 * MONTH STATISTIC BEGIN
		 */

		Integer year = null;
		Integer month = null;
		
		if(yearMonth.equals("0")){
			Calendar c = Calendar.getInstance(Locale.CHINA);
			// get this year
			year = c.get(Calendar.YEAR);
			// get this month
			month = c.get(Calendar.MONTH)+1;
			yearMonth = year.toString()+"-"+month.toString();
		} else {
			String[] temp = yearMonth.split("-");
			year = Integer.parseInt(temp[0]);
			month = Integer.parseInt(temp[1]);
		}
		
		List<StatisticBilling> monthBillingTransactions = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingTransactions);
		List<CustomerTransaction> monthCustomerTransactions = this.billingService.queryCustomerTransactionsByTransactionDate(
				// first date of month
				monthBillingTransactions.get(0).getBillingDate()
				// last date of month
				,monthBillingTransactions.get(monthBillingTransactions.size()-1).getBillingDate()
			);
		for (StatisticBilling statisticBilling : monthBillingTransactions) {
			for (CustomerTransaction customerTransaction : monthCustomerTransactions) {
				
				// if registerCustomer's register date(filtered monthly dates) equals to customer's register date
				if(TMUtils.dateFormatYYYYMMDD(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMMDD(customerTransaction.getTransaction_date()))){
					statisticBilling.setBillingAmount(TMUtils.bigAdd(
							statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, customerTransaction.getAmount_settlement())
						);
				}
			}
		}
		
		model.addAttribute("monthBillingTransactions", monthBillingTransactions);
		model.addAttribute("yearMonth",yearMonth);
		
		/**
		 * MONTH STATISTIC END
		 */
		
		
		return "broadband-user/billing/transaction-chart";
	}
	
	/**
	 * Billing Monthly Invoice Statistics
	 */
	@RequestMapping(value = "/broadband-user/billing/chart/invoice-statistic/{customer_type}/{yearMonth}")
	public String toChartMonthlyInvoiceStatistics(Model model,
			@PathVariable(value = "customer_type") String customer_type,
			@PathVariable(value = "yearMonth") String yearMonth) {
		
		model.addAttribute("panelheading", "Monthly Invoice Statistics");
		
		/**
		 * MONTHLY STATISTIC BEGIN
		 */

		Integer year = null;
		Integer month = null;
		
		if(yearMonth.equals("0")){
			Calendar c = Calendar.getInstance(Locale.CHINA);
			// get this year
			year = c.get(Calendar.YEAR);
			// get this month
			month = c.get(Calendar.MONTH)+1;
			yearMonth = year.toString()+"-"+month.toString();
		} else {
			String[] temp = yearMonth.split("-");
			year = Integer.parseInt(temp[0]);
			month = Integer.parseInt(temp[1]);
		}
		
		// BEGIN INVOICE AMOUNT INVOICES
		Double invoiceAmount = 0d;
		List<StatisticBilling> monthBillingInvoiceAmountInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingInvoiceAmountInvoices);
		CustomerInvoice ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingInvoiceAmountInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingInvoiceAmountInvoices.get(monthBillingInvoiceAmountInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("customer_type", customer_type);
		List<CustomerInvoice> monthCustomerInvoiceAmountInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerInvoiceAmountInvoices) {
			
			invoiceAmount = TMUtils.bigAdd(invoiceAmount, ci.getAmount_payable());
			
		}
		model.addAttribute("monthlyInvoiceAmount", invoiceAmount);
		// END INVOICE AMOUNT INVOICES
		
		// BEGIN UNPAID INVOICES
		Double unpaidAmount = 0d;
		List<StatisticBilling> monthBillingUnpaidInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingUnpaidInvoices);
		ciTemp = null;
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingUnpaidInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingUnpaidInvoices.get(monthBillingUnpaidInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("status1", "unpaid");
		ciTemp.getParams().put("status2", "not_pay_off");
		ciTemp.getParams().put("customer_type", customer_type);
		List<CustomerInvoice> monthCustomerUnpaidInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerUnpaidInvoices) {
			
			unpaidAmount = TMUtils.bigAdd(unpaidAmount, ci.getBalance());
			
		}
		model.addAttribute("monthlyUnpaidAmount", unpaidAmount);
		// END UNPAID INVOICES
		
		// BEGIN OVERDUE INVOICES
		Double overdueAmount = 0d;
		List<StatisticBilling> monthBillingOverdueInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingOverdueInvoices);
		ciTemp = null;
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingOverdueInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingOverdueInvoices.get(monthBillingOverdueInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("status", "overdue");
		ciTemp.getParams().put("customer_type", customer_type);
		List<CustomerInvoice> monthCustomerOverdueInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerOverdueInvoices) {
			
			overdueAmount = TMUtils.bigAdd(overdueAmount, ci.getBalance());
			
		}
		model.addAttribute("monthlyOverdueAmount", overdueAmount);
		// END OVERDUE INVOICES
		
		// BEGIN CREDIT INVOICES
		Double allPayableAmount = 0d;
		Double allFinalPayableAmount = 0d;
		Double allCredit = 0d;
		List<StatisticBilling> monthBillingAllInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingAllInvoices);
		ciTemp = null;
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingAllInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingAllInvoices.get(monthBillingAllInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("customer_type", customer_type);
		List<CustomerInvoice> monthCustomerAllInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerAllInvoices) {
			
			allPayableAmount = TMUtils.bigAdd(allPayableAmount, ci.getAmount_payable());
			allFinalPayableAmount = TMUtils.bigAdd(allFinalPayableAmount, ci.getFinal_payable_amount());
				
		}
		allCredit = TMUtils.bigSub(allPayableAmount, allFinalPayableAmount);
		model.addAttribute("monthlyCredit", allCredit);
		// END CREDIT INVOICES
		
		// BEGIN PAID INVOICES
		Double paidAmount = 0d;
		List<StatisticBilling> monthBillingPaidInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingPaidInvoices);
		ciTemp = null;
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingPaidInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingPaidInvoices.get(monthBillingPaidInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("status3", "paid");
		ciTemp.getParams().put("status4", "not_pay_off");
		ciTemp.getParams().put("status5", "bad-debit");
		ciTemp.getParams().put("customer_type", customer_type);
		List<CustomerInvoice> monthCustomerPaidInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerPaidInvoices) {
			
			Double totalPaidAmount = 0d;
			if("paid".equals(ci.getStatus()) && ci.getBalance()<0){
				totalPaidAmount = TMUtils.bigAdd(totalPaidAmount, TMUtils.bigAdd(ci.getAmount_paid(), ci.getBalance()));
			} else {
				totalPaidAmount = TMUtils.bigAdd(totalPaidAmount, ci.getAmount_paid());
			}
			
			paidAmount = TMUtils.bigAdd(paidAmount, totalPaidAmount);
			
		}
		model.addAttribute("monthlyPaidAmount", paidAmount);
		// END PAID INVOICES
		
		// BEGIN PREPAYMENT INVOICES
		Double prepaymentAmount = 0d;
		List<StatisticBilling> monthBillingPrepaymentInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingPrepaymentInvoices);
		ciTemp = null;
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingPrepaymentInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingPrepaymentInvoices.get(monthBillingPrepaymentInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("prepayment", true);
		ciTemp.getParams().put("customer_type", customer_type);
		List<CustomerInvoice> monthCustomerResidueInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerResidueInvoices) {
			
			prepaymentAmount = TMUtils.bigAdd(prepaymentAmount, Math.abs(ci.getBalance()));
			
		}
		model.addAttribute("monthlyPrepaymentAmount", prepaymentAmount);
		// END PREPAYMENT INVOICES
		
		// BEGIN VOID INVOICES
		Double voidBalanceAmount = 0d;
		List<StatisticBilling> monthBillingVoidInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingVoidInvoices);
		ciTemp = null;
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingVoidInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingVoidInvoices.get(monthBillingVoidInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("status", "void");
		ciTemp.getParams().put("customer_type", customer_type);
		List<CustomerInvoice> monthCustomerVoidInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerVoidInvoices) {
			
			voidBalanceAmount = TMUtils.bigAdd(voidBalanceAmount, ci.getBalance());
			
		}
		model.addAttribute("monthlyVoidBalanceAmount", voidBalanceAmount);
		// END VOID INVOICES
		
		// BEGIN BAD DEBIT INVOICES
		Double badDebitAmount = 0d;
		List<StatisticBilling> monthBadDebitInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBadDebitInvoices);
		ciTemp = null;
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBadDebitInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBadDebitInvoices.get(monthBadDebitInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("status", "bad-debit");
		ciTemp.getParams().put("customer_type", customer_type);
		List<CustomerInvoice> monthCustomerBadDebitInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerBadDebitInvoices) {
			
			badDebitAmount = TMUtils.bigAdd(badDebitAmount, ci.getBalance());
			
		}
		model.addAttribute("monthlyBadDebitAmount", badDebitAmount);
		// END BAD DEBIT INVOICES
		
		
		model.addAttribute("yearMonth",yearMonth);
		model.addAttribute("customer_type", customer_type);
		
		/**
		 * MONTHLY STATISTIC END
		 */
		
		
		return "broadband-user/billing/invoice-chart";
	}
	
	/**
	 * Billing Annually Invoice Statistics
	 */
	@RequestMapping(value = "/broadband-user/billing/chart/annual-invoice-statistic/{customer_type}/{year}")
	public String toChartAnnuallyInvoiceStatistics(Model model,
			@PathVariable(value = "customer_type") String customer_type,
			@PathVariable(value = "year") Integer year) {
		
		model.addAttribute("panelheading", "Annual Invoice Statistics");
		
		/**
		 * MONTHLY STATISTIC BEGIN
		 */
		if(year==0){
			Calendar c = Calendar.getInstance(Locale.CHINA);
			// get this year
			year = c.get(Calendar.YEAR);
		}
		
		// BEGIN INVOICE AMOUNT INVOICES
		List<StatisticBilling> annualInvoiceAmountInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisYearDateForBillingStatistic(year, annualInvoiceAmountInvoices);
		CustomerInvoice ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("month_start_date", annualInvoiceAmountInvoices.get(0).getBillingDate());
		ciQuery.getParams().put("month_end_date", annualInvoiceAmountInvoices.get(annualInvoiceAmountInvoices.size()-1).getBillingDate());
		ciQuery.getParams().put("customer_type", customer_type);
		List<CustomerInvoice> annualCustomerInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		for (StatisticBilling statisticBilling : annualInvoiceAmountInvoices) {
			for (CustomerInvoice ci : annualCustomerInvoices) {
				
				if(TMUtils.dateFormatYYYYMM(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMM(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(
						TMUtils.bigAdd(statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, ci.getAmount_payable())
					);
				}
			}
		}
		model.addAttribute("annualInvoiceAmountInvoices", annualInvoiceAmountInvoices);
		// END INVOICE AMOUNT INVOICES
		
		// BEGIN UNPAID INVOICES
		List<StatisticBilling> annualBillingUnpaidInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisYearDateForBillingStatistic(year, annualBillingUnpaidInvoices);
		ciQuery = null;
		ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("month_start_date", annualBillingUnpaidInvoices.get(0).getBillingDate());
		ciQuery.getParams().put("month_end_date", annualBillingUnpaidInvoices.get(annualBillingUnpaidInvoices.size()-1).getBillingDate());
		ciQuery.getParams().put("status1", "unpaid");
		ciQuery.getParams().put("status2", "not_pay_off");
		ciQuery.getParams().put("customer_type", customer_type);
		annualCustomerInvoices = null;
		annualCustomerInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		for (StatisticBilling statisticBilling : annualBillingUnpaidInvoices) {
			for (CustomerInvoice ci : annualCustomerInvoices) {
				
				if(TMUtils.dateFormatYYYYMM(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMM(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(
						TMUtils.bigAdd(statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, ci.getBalance())
					);
				}
			}
		}
		model.addAttribute("annualUnpaidInvoices", annualBillingUnpaidInvoices);
		// END UNPAID INVOICES
		
		// BEGIN OVERDUE INVOICES
		List<StatisticBilling> annualOverdueInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisYearDateForBillingStatistic(year, annualOverdueInvoices);
		ciQuery = null;
		ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("month_start_date", annualOverdueInvoices.get(0).getBillingDate());
		ciQuery.getParams().put("month_end_date", annualOverdueInvoices.get(annualOverdueInvoices.size()-1).getBillingDate());
		ciQuery.getParams().put("status", "overdue");
		ciQuery.getParams().put("customer_type", customer_type);
		annualCustomerInvoices = null;
		annualCustomerInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		for (StatisticBilling statisticBilling : annualOverdueInvoices) {
			for (CustomerInvoice ci : annualCustomerInvoices) {
				
				if(TMUtils.dateFormatYYYYMM(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMM(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(
						TMUtils.bigAdd(statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, ci.getBalance())
					);
				}
			}
		}
		model.addAttribute("annualOverdueInvoices", annualOverdueInvoices);
		// END OVERDUE INVOICES
		
		// BEGIN CREDIT INVOICES
		List<StatisticBilling> annualBillingCreditInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisYearDateForBillingStatistic(year, annualBillingCreditInvoices);
		ciQuery = null;
		ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("month_start_date", annualBillingCreditInvoices.get(0).getBillingDate());
		ciQuery.getParams().put("month_end_date", annualBillingCreditInvoices.get(annualBillingCreditInvoices.size()-1).getBillingDate());
		ciQuery.getParams().put("customer_type", customer_type);
		annualCustomerInvoices = null;
		annualCustomerInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		for (StatisticBilling statisticBilling : annualBillingCreditInvoices) {
			for (CustomerInvoice ci : annualCustomerInvoices) {
				
				if(TMUtils.dateFormatYYYYMM(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMM(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(
						TMUtils.bigAdd(statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, TMUtils.bigSub(ci.getAmount_payable(), ci.getFinal_payable_amount()))
					);
				}
			}
		}
		model.addAttribute("annualCreditInvoices", annualBillingCreditInvoices);
		// END CREDIT INVOICES

		// BEGIN PREPAYMENT INVOICES
		List<StatisticBilling> annualBillingPrepaymentInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisYearDateForBillingStatistic(year, annualBillingPrepaymentInvoices);
		ciQuery = null;
		ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("month_start_date", annualBillingPrepaymentInvoices.get(0).getBillingDate());
		ciQuery.getParams().put("month_end_date", annualBillingPrepaymentInvoices.get(annualBillingPrepaymentInvoices.size()-1).getBillingDate());
		ciQuery.getParams().put("prepayment", true);
		ciQuery.getParams().put("customer_type", customer_type);
		annualCustomerInvoices = null;
		annualCustomerInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		for (StatisticBilling statisticBilling : annualBillingPrepaymentInvoices) {
			for (CustomerInvoice ci : annualCustomerInvoices) {
				
				if(TMUtils.dateFormatYYYYMM(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMM(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(
						TMUtils.bigAdd(statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, Math.abs(ci.getBalance()))
					);
				}
			}
		}
		model.addAttribute("annualPrepaymentInvoices", annualBillingPrepaymentInvoices);
		// END PREPAYMENT INVOICES

		// BEGIN PAID INVOICES
		List<StatisticBilling> annualBillingPaidInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisYearDateForBillingStatistic(year, annualBillingPaidInvoices);
		ciQuery = null;
		ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("month_start_date", annualBillingPaidInvoices.get(0).getBillingDate());
		ciQuery.getParams().put("month_end_date", annualBillingPaidInvoices.get(annualBillingPaidInvoices.size()-1).getBillingDate());
		ciQuery.getParams().put("status3", "paid");
		ciQuery.getParams().put("status4", "not_pay_off");
		ciQuery.getParams().put("status5", "bad-debit");
		ciQuery.getParams().put("customer_type", customer_type);
		annualCustomerInvoices = null;
		annualCustomerInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		for (StatisticBilling statisticBilling : annualBillingPaidInvoices) {
			for (CustomerInvoice ci : annualCustomerInvoices) {
				
				Double totalPaidAmount = 0d;
				if("paid".equals(ci.getStatus()) && ci.getBalance()<0){
					totalPaidAmount = TMUtils.bigAdd(totalPaidAmount, TMUtils.bigAdd(ci.getAmount_paid(), ci.getBalance()));
				} else {
					totalPaidAmount = TMUtils.bigAdd(totalPaidAmount, ci.getAmount_paid());
				}
				
				if(TMUtils.dateFormatYYYYMM(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMM(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(
						TMUtils.bigAdd(statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, totalPaidAmount)
					);
				}
			}
		}
		model.addAttribute("annualPaidInvoices", annualBillingPaidInvoices);
		// END PAID INVOICES
		
		// BEGIN VOID INVOICES
		List<StatisticBilling> annualBillingVoidInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisYearDateForBillingStatistic(year, annualBillingVoidInvoices);
		ciQuery = null;
		ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("month_start_date", annualBillingVoidInvoices.get(0).getBillingDate());
		ciQuery.getParams().put("month_end_date", annualBillingVoidInvoices.get(annualBillingVoidInvoices.size()-1).getBillingDate());
		ciQuery.getParams().put("status", "void");
		ciQuery.getParams().put("customer_type", customer_type);
		annualCustomerInvoices = null;
		annualCustomerInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		for (StatisticBilling statisticBilling : annualBillingVoidInvoices) {
			for (CustomerInvoice ci : annualCustomerInvoices) {
				
				if(TMUtils.dateFormatYYYYMM(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMM(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(
						TMUtils.bigAdd(statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, ci.getBalance())
					);
				}
			}
		}
		model.addAttribute("annualVoidInvoices", annualBillingVoidInvoices);
		// END VOID INVOICES
		
		// BEGIN BAD DEBIT INVOICES
		List<StatisticBilling> annualBadDebitInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisYearDateForBillingStatistic(year, annualBadDebitInvoices);
		ciQuery = null;
		ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("month_start_date", annualBadDebitInvoices.get(0).getBillingDate());
		ciQuery.getParams().put("month_end_date", annualBadDebitInvoices.get(annualBadDebitInvoices.size()-1).getBillingDate());
		ciQuery.getParams().put("status", "bad-debit");
		ciQuery.getParams().put("customer_type", customer_type);
		annualCustomerInvoices = null;
		annualCustomerInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		for (StatisticBilling statisticBilling : annualBadDebitInvoices) {
			for (CustomerInvoice ci : annualCustomerInvoices) {
				
				if(TMUtils.dateFormatYYYYMM(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMM(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(
						TMUtils.bigAdd(statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, ci.getBalance())
					);
				}
			}
		}
		model.addAttribute("annualBadDebitInvoices", annualBadDebitInvoices);
		// END BAD DEBIT INVOICES
		
		
		model.addAttribute("year",year);
		model.addAttribute("customer_type", customer_type);
		
		/**
		 * ANNUALLY STATISTIC END
		 */
		
		return "broadband-user/billing/invoice-chart-annual";
	}
	
	
	/**
	 * Billing Commission Statistics
	 */
	@RequestMapping(value = "/broadband-user/agent/billing/chart/commission-statistic/{yearMonth}")
	public String toChartCommissionStatistics(Model model,
			@PathVariable(value = "yearMonth") String yearMonth,
			HttpServletRequest req) {
		
		model.addAttribute("panelheading", "Customer Commission Statistics");
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		/**
		 * WEEK STATISTIC BEGIN
		 */
		List<StatisticBilling> weekCommissionStatistics = new ArrayList<StatisticBilling>();
		TMUtils.thisWeekBillingStatistic(weekCommissionStatistics);
		
		CustomerInvoice ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("start_date", weekCommissionStatistics.get(0).getBillingDate());
		ciQuery.getParams().put("end_date", weekCommissionStatistics.get(weekCommissionStatistics.size()-1).getBillingDate());
		ciQuery.getParams().put("sale_id", user.getId());
		
		List<CustomerInvoice> weekCustomerCommissions = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		
		for (StatisticBilling statisticBilling : weekCommissionStatistics) {
			for (CustomerInvoice ci : weekCustomerCommissions) {
				if(TMUtils.dateFormatYYYYMMDD(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMMDD(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(TMUtils.bigAdd(
						statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, TMUtils.bigMultiply(ci.getAmount_paid(), TMUtils.bigDivide(user.getAgent_commission_rates(), 100)))
					);
				}
			}
		}
		
		model.addAttribute("weekCommissionStatistics", weekCommissionStatistics);
		/**
		 * WEEK STATISTIC END
		 */
		
		/**
		 * MONTH STATISTIC BEGIN
		 */

		Integer year = null;
		Integer month = null;
		
		if(yearMonth.equals("0")){
			Calendar c = Calendar.getInstance(Locale.CHINA);
			// get this year
			year = c.get(Calendar.YEAR);
			// get this month
			month = c.get(Calendar.MONTH)+1;
			yearMonth = year.toString()+"-"+month.toString();
		} else {
			String[] temp = yearMonth.split("-");
			year = Integer.parseInt(temp[0]);
			month = Integer.parseInt(temp[1]);
		}
		
		List<StatisticBilling> monthBillingCommissions = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingCommissions);

		ciQuery = null;
		ciQuery = new CustomerInvoice();
		ciQuery.getParams().put("start_date", monthBillingCommissions.get(0).getBillingDate());
		ciQuery.getParams().put("end_date", monthBillingCommissions.get(monthBillingCommissions.size()-1).getBillingDate());
		ciQuery.getParams().put("sale_id", user.getId());
		
		List<CustomerInvoice> monthCustomerTransactions = this.billingService.queryCustomerInvoicesByCreateDate(ciQuery);
		for (StatisticBilling statisticBilling : monthBillingCommissions) {
			for (CustomerInvoice ci : monthCustomerTransactions) {
				
				if(TMUtils.dateFormatYYYYMMDD(statisticBilling.getBillingDate()).equals(TMUtils.dateFormatYYYYMMDD(ci.getCreate_date()))){
					statisticBilling.setBillingAmount(TMUtils.bigAdd(
							statisticBilling.getBillingAmount()!= null ? statisticBilling.getBillingAmount() : 0d, TMUtils.bigMultiply(ci.getAmount_paid(), TMUtils.bigDivide(user.getAgent_commission_rates(), 100)))
						);
				}
			}
		}
		
		model.addAttribute("monthBillingCommissions", monthBillingCommissions);
		model.addAttribute("yearMonth",yearMonth);
		
		/**
		 * MONTH STATISTIC END
		 */
		
		
		return "broadband-user/sale/commission-chart";
	}
	

	
	/**
	 * Billing Monthly Calling Statistics
	 */
	@RequestMapping(value = "/broadband-user/billing/chart/calling-statistic/{callingType}/{yearMonth}")
	public String toChartMonthlyCallingStatistics(Model model,
			@PathVariable(value = "callingType") String callingType,
			@PathVariable(value = "yearMonth") String yearMonth) {
		
		model.addAttribute("panelheading", "Monthly Calling Statistics");
		
		/**
		 * MONTHLY STATISTIC BEGIN
		 */

		Integer year = null;
		Integer month = null;
		
		if(yearMonth.equals("0")){
			Calendar c = Calendar.getInstance(Locale.CHINA);
			// get this year
			year = c.get(Calendar.YEAR);
			// get this month
			month = c.get(Calendar.MONTH)+1;
			yearMonth = year.toString()+"-"+month.toString();
		} else {
			String[] temp = yearMonth.split("-");
			year = Integer.parseInt(temp[0]);
			month = Integer.parseInt(temp[1]);
		}
		
		// BEGIN CALLING AMOUNT
		
		Double callingBuyingAmount = 0d;
		Double callingSellingAmount = 0d;
		
		List<StatisticBilling> monthBillingCallingAmountInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingCallingAmountInvoices);
		
		Map<String, Object> resultMap = this.billingService.queryMonthlyCallingStatistics(callingType, callingBuyingAmount, callingSellingAmount, monthBillingCallingAmountInvoices);
		
		callingBuyingAmount = (Double) resultMap.get("buyingAmount");
		callingSellingAmount = (Double) resultMap.get("sellingAmount");
		
		model.addAttribute("monthlyBuyingAmount", callingBuyingAmount);
		model.addAttribute("monthlySellingAmount", callingSellingAmount);
		model.addAttribute("callingType", callingType);
		
		model.addAttribute(callingType+"Active", "active");
		// END CALLING AMOUNT
		
		
		model.addAttribute("yearMonth",yearMonth);
		
		/**
		 * MONTHLY STATISTIC END
		 */
		
		
		return "broadband-user/billing/calling-chart";
	}
	
}
