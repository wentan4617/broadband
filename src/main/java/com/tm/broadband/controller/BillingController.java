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
import com.tm.broadband.model.Organization;
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
	@RequestMapping("/broadband-user/billing/invoice/view/{pageNo}/{status}")
	public String toInvoice(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status) {

		Page<CustomerInvoice> pageCis = new Page<CustomerInvoice>();
		pageCis.getParams().put("status", status);
		pageCis.setPageNo(pageNo);
		pageCis.setPageSize(30);
		pageCis.getParams().put("orderby", "ORDER BY create_date DESC");
		
		if("orderNoInvoice".equals(status)){
			Page<CustomerOrder> pageCos = new Page<CustomerOrder>();
			pageCos.setPageNo(pageNo);
			pageCos.setPageSize(30);
			pageCos.getParams().put("where", "query_no_invoice");
			pageCos.getParams().put("orderby", "ORDER BY order_create_date DESC");
			model.addAttribute("pageCos", this.crmService.queryCustomerOrdersByPage(pageCos));
			
			pageCos = null;
		} else if("unpaid".equals(status)){
			pageCis.getParams().put("where", "non_pending");
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
			pageCis.getParams().put("status1", "unpaid");
			pageCis.getParams().put("status2", "not_pay_off");
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("void".equals(status)){
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
			
		} else if("paid".equals(status)){
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
		}
		pageCis = null;
		
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");
		model.addAttribute("users", this.systemService.queryUser(new User()));
		

		// BEGIN QUERY SUM BY STATUS
		Page<CustomerInvoice> pageStatusSum = new Page<CustomerInvoice>();
		pageStatusSum.getParams().put("where", "pending");
		pageStatusSum.getParams().put("payment_status", "pending");
		pageStatusSum.getParams().put("status1", "unpaid");
		pageStatusSum.getParams().put("status2", "not_pay_off");
		model.addAttribute("pendingSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		pageStatusSum = null;
		
		pageStatusSum = new Page<CustomerInvoice>();
		pageStatusSum.getParams().put("status", "paid");
		model.addAttribute("paidSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		
		pageStatusSum.getParams().put("status", "void");
		model.addAttribute("voidSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));

		pageStatusSum.getParams().put("where", "non_pending");
		pageStatusSum.getParams().put("status", "unpaid");
		model.addAttribute("unpaidSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		
		pageStatusSum.getParams().put("status", "not_pay_off");
		model.addAttribute("notPayOffSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		pageStatusSum = null;
		
		Page<CustomerOrder> pageStatusCoSum = new Page<CustomerOrder>();
		pageStatusCoSum.getParams().put("where", "query_no_invoice");
		model.addAttribute("orderNoInvoiceSum", this.crmService.queryCustomerOrdersSumByPage(pageStatusCoSum));
		// END QUERY SUM BY STATUS
		
		return "broadband-user/billing/invoice-view";
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
	@RequestMapping(value = "/broadband-user/billing/early_termination_charge/pdf/send/{terminationChargeId}/{customerId}")
	public String sendEarlyTerminationChargePDF(Model model,
			@PathVariable(value = "terminationChargeId") int terminationChargeId,
			@PathVariable(value = "customerId") int customerId) {
		String filePath = this.billingService.queryEarlyTerminationChargePDFPathById(terminationChargeId);
		Customer customer = this.crmService.queryCustomerById(customerId);
		Notification notification = this.systemService.queryNotificationBySort("early-termination-charge", "email");
		CompanyDetail company = this.systemService.queryCompanyDetail();
		
		Organization org = this.crmService.queryOrganizationByCustomerId(customerId);
		customer.setOrganization(org);
		MailRetriever.mailAtValueRetriever(notification, customer, company);
		
		ApplicationEmail applicationEmail = new ApplicationEmail();
		// setting properties and sending mail to customer email address
		// recipient
		applicationEmail.setAddressee(customer.getEmail());
		// subject
		applicationEmail.setSubject(notification.getTitle());
		// content
		applicationEmail.setContent(notification.getContent());
		// attachment name
		applicationEmail.setAttachName("early_termination_charge_" + ("personal".equals(customer.getType()) ? customer.getLast_name()+" "+ customer.getFirst_name() : org!=null ? org.getOrg_name() : "") + ".pdf");
		// attachment path
		applicationEmail.setAttachPath(filePath);
		
		// send mail
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		
		filePath = null;
		customer = null;
		notification = null;
		company = null;
		org = null;
		applicationEmail = null;
		
		return "broadband-user/progress-accomplished";
	}
    
	// Send Termination Refund PDF directly
	@RequestMapping(value = "/broadband-user/billing/termination_refund/pdf/send/{terminationRefundId}/{customerId}")
	public String sendTerminationRefundPDF(Model model,
			@PathVariable(value = "terminationRefundId") int terminationRefundId,
			@PathVariable(value = "customerId") int customerId) {
		String filePath = this.billingService.queryTerminationRefundPDFPathById(terminationRefundId);
		Customer customer = this.crmService.queryCustomerById(customerId);
		Notification notification = this.systemService.queryNotificationBySort("termination-refund", "email");
		CompanyDetail company = this.systemService.queryCompanyDetail();
		
		Organization org = this.crmService.queryOrganizationByCustomerId(customerId);
		customer.setOrganization(org);
		MailRetriever.mailAtValueRetriever(notification, customer, company);
		
		ApplicationEmail applicationEmail = new ApplicationEmail();
		// setting properties and sending mail to customer email address
		// recipient
		applicationEmail.setAddressee(customer.getEmail());
		// subject
		applicationEmail.setSubject(notification.getTitle());
		// content
		applicationEmail.setContent(notification.getContent());
		// attachment name
		applicationEmail.setAttachName("termination_refund_" + ("personal".equals(customer.getType()) ? customer.getLast_name()+" "+ customer.getFirst_name() : org!=null ? org.getOrg_name() : "") + ".pdf");
		// attachment path
		applicationEmail.setAttachPath(filePath);
		
		// send mail
		this.mailerService.sendMailByAsynchronousMode(applicationEmail);
		
		filePath = null;
		customer = null;
		notification = null;
		company = null;
		org = null;
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
	 * Billing Invoice Statistics
	 */
	@RequestMapping(value = "/broadband-user/billing/chart/invoice-statistic/{yearMonth}")
	public String toChartInvoiceStatistics(Model model,
			@PathVariable(value = "yearMonth") String yearMonth) {
		
		model.addAttribute("panelheading", "Customer Invoice Statistics");
		
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
		
		// BEGIN UNPAID INVOICES
		Double payableAmount = 0d;
		List<StatisticBilling> monthBillingUnpaidInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingUnpaidInvoices);
		CustomerInvoice ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingUnpaidInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingUnpaidInvoices.get(monthBillingUnpaidInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("status1", "unpaid");
		ciTemp.getParams().put("status2", "not_pay_off");
		List<CustomerInvoice> monthCustomerUnpaidInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerUnpaidInvoices) {
			
			payableAmount = TMUtils.bigAdd(payableAmount, ci.getBalance());
			
		}
		model.addAttribute("monthlyUnpaidAmount", payableAmount);
		// END UNPAID INVOICES
		
		// BEGIN PAID INVOICES
		Double paidAmount = 0d;
		List<StatisticBilling> monthBillingPaidInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingPaidInvoices);
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingPaidInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingPaidInvoices.get(monthBillingPaidInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("status1", "paid");
		ciTemp.getParams().put("status2", "not_pay_off");
		List<CustomerInvoice> monthCustomerPaidInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerPaidInvoices) {
			
			paidAmount = TMUtils.bigAdd(paidAmount, ci.getAmount_paid());
			
		}
		model.addAttribute("monthlyPaidAmount", paidAmount);
		// END PAID INVOICES
		
		// BEGIN PAID INVOICES
		Double voidBalanceAmount = 0d;
		List<StatisticBilling> monthBillingVoidInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingVoidInvoices);
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingVoidInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingVoidInvoices.get(monthBillingVoidInvoices.size()-1).getBillingDate());
		ciTemp.getParams().put("status", "void");
		List<CustomerInvoice> monthCustomerVoidInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerVoidInvoices) {
			
			voidBalanceAmount = TMUtils.bigAdd(voidBalanceAmount, ci.getBalance());
			
		}
		model.addAttribute("monthlyVoidBalanceAmount", voidBalanceAmount);
		// END PAID INVOICES
		
		// BEGIN ALL INVOICES
		Double allPayableAmount = 0d;
		Double allFinalPayableAmount = 0d;
		Double allCredit = 0d;
		List<StatisticBilling> monthBillingAllInvoices = new ArrayList<StatisticBilling>();
		TMUtils.thisMonthDateForBillingStatistic(year, month, monthBillingAllInvoices);
		ciTemp = new CustomerInvoice();
		ciTemp.getParams().put("start_date", monthBillingAllInvoices.get(0).getBillingDate());
		ciTemp.getParams().put("end_date", monthBillingAllInvoices.get(monthBillingAllInvoices.size()-1).getBillingDate());
		List<CustomerInvoice> monthCustomerAllInvoices = this.billingService.queryCustomerInvoicesByCreateDate(ciTemp);
		for (CustomerInvoice ci : monthCustomerAllInvoices) {
			
			allPayableAmount = TMUtils.bigAdd(allPayableAmount, ci.getAmount_payable());
			allFinalPayableAmount = TMUtils.bigAdd(allFinalPayableAmount, ci.getFinal_payable_amount());
				
		}
		allCredit = TMUtils.bigSub(allPayableAmount, allFinalPayableAmount);
		model.addAttribute("monthlyCredit", allCredit);
		// END All INVOICES
		
		
		model.addAttribute("yearMonth",yearMonth);
		
		/**
		 * MONTHLY STATISTIC END
		 */
		
		
		return "broadband-user/billing/invoice-chart";
	}
}
