package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.model.EarlyTerminationChargeParameter;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.TerminationRefund;
import com.tm.broadband.model.User;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.SystemService;

@Controller
public class BillingController {

	private BillingService billingService;
	private SystemService systemService;
	private CRMService crmService;
	
	@Autowired
	public BillingController(BillingService billingService
			,SystemService systemService
			,CRMService crmService) {
		this.billingService = billingService;
		this.systemService = systemService;
		this.crmService = crmService;
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
		String filePath = this.billingService.selectEarlyTerminationChargePDFPathById(early_termination_id);
		
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
		String filePath = this.billingService.selectTerminationRefundPDFPathById(termination_refund_id);
		
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
		pageCis.setPageNo(pageNo);
		pageCis.setPageSize(30);
		pageCis.getParams().put("orderby", "ORDER BY create_date DESC");
		System.out.println("status: "+status);
		
		if(!"orderNoInvoice".equals(status) && !"pending".equals(status)){
			pageCis.getParams().put("status", status);
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
		} else if("pending".equals(status)) {
			pageCis.getParams().put("payment_status", status);
			pageCis = this.crmService.queryCustomerInvoicesByPage(pageCis);
			model.addAttribute("pageCis", pageCis);
		} else {
			Page<CustomerOrder> pageCos = new Page<CustomerOrder>();
			pageCos.getParams().put("where", "query_no_invoice");
			pageCos.getParams().put("orderby", "ORDER BY order_create_date DESC");
			model.addAttribute("pageCos", this.crmService.queryCustomerOrdersByPage(pageCos));
		}
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");
		model.addAttribute("users", this.systemService.queryUser(new User()));
		

		// BEGIN QUERY SUM BY STATUS
		Page<CustomerInvoice> pageStatusSum = new Page<CustomerInvoice>();
		pageStatusSum.getParams().put("status", "paid");
		model.addAttribute("paidSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("status", "not_pay_off");
		model.addAttribute("notPayOffSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("status", "unpaid");
		model.addAttribute("unpaidSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("payment_status", "pending");
		model.addAttribute("pendingSum", this.crmService.queryCustomerInvoicesSumByPage(pageStatusSum));
		
		Page<CustomerOrder> pageStatusCoSum = new Page<CustomerOrder>();
		pageStatusCoSum.getParams().put("where", "query_no_invoice");
		model.addAttribute("orderNoInvoiceSum", this.crmService.queryCustomerOrdersSumByPage(pageStatusCoSum));
		// END QUERY SUM BY STATUS
		
		return "broadband-user/billing/invoice-view";
	}
}
