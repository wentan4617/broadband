package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.BillingFileUpload;
import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.CustomerCallingRecordCallplus;
import com.tm.broadband.model.ManualManipulationRecord;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.CallInternationalRateUtility;
import com.tm.broadband.util.CallingRecordUtility;
import com.tm.broadband.util.CallingRecordUtility_CallPlus;
import com.tm.broadband.util.TMUtils;

/**
 * system controller
 * 
 * @author Cook1fan
 * 
 */

@Controller
public class ManualManipulationController {

	private SystemService systemService;
	private BillingService billingService;
	private CRMService crmService;

	@Autowired
	public ManualManipulationController(SystemService systemService
			,BillingService billingService
			,CRMService crmService) {
		this.systemService = systemService;
		this.billingService = billingService;
		this.crmService = crmService;
	}

	// BEGIN ManualManipulationRecord
	@RequestMapping(value = "/broadband-user/manual-manipulation/manual-manipulation-record/view/{pageNo}/{manipulation_type}")
	public String manualManipulationRecordView(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "manipulation_type") String manipulation_type) {

		Page<ManualManipulationRecord> page = new Page<ManualManipulationRecord>();
		page.setPageNo(pageNo);
		page.setPageSize(50);
		page.getParams().put("orderby", "ORDER BY manipulation_time DESC");
		page.getParams().put("manipulation_type", manipulation_type);
		this.systemService.queryManualManipulationRecordsByPage(page);
		model.addAttribute("page", page);

		return "broadband-user/manual-manipulation/manual-termed-invoice-view";
	}

	@RequestMapping(value = "/broadband-user/manual-manipulation/manual-manipulation-record/create/{pageNo}", method = RequestMethod.POST)
	public String doManualManipulationRecordCreate(
			@PathVariable(value = "pageNo") int pageNo,
			@ModelAttribute("manualManipulationRecord") ManualManipulationRecord mmr,
			HttpServletRequest req,
			RedirectAttributes attr) {
		
		try {
			this.crmService.createTermPlanInvoice();
		} catch (ParseException e) { e.printStackTrace(); }
		
		// RECORDING MANIPULATOR'S DETAILS
		User user = (User) req.getSession().getAttribute("userSession");
		mmr.setAdmin_id(user.getId());
		mmr.setAdmin_name(user.getUser_name());
		mmr.setManipulation_time(new Date());
		mmr.setManipulation_name("Manually Generate Termed Invoices");
		this.systemService.createManualManipulationRecord(mmr);
		
		attr.addFlashAttribute("success", "Create Manual Manipulation Record is successful.");

		return "redirect:/broadband-user/manual-manipulation/manual-manipulation-record/view/" + pageNo + "/" + mmr.getManipulation_type();
	}
	// END ManualManipulationRecord
	
	// BEGIN CallInternationalRate
	@RequestMapping("/broadband-user/manual-manipulation/call-international-rate/view/{pageNo}")
	public String toCallInternationalRate(Model model
			, @PathVariable("pageNo") int pageNo) {

		Page<CallInternationalRate> page = new Page<CallInternationalRate>();
		page.setPageNo(pageNo);
		page.setPageSize(50);
		page.getParams().put("orderby", "ORDER BY rate_type");
		page = this.billingService.queryCallInternationalRatesByPage(page);
		
		model.addAttribute("page", page);		
		
		return "broadband-user/manual-manipulation/call-international-rate-view";
	}
	
	@RequestMapping("/broadband-user/manual-manipulation/call-billing-record/view/{pageNo}/{status}/{billing_type}")
	public String toBillingFileUpload(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status
			, @PathVariable("billing_type") String billing_type) {

		Page<BillingFileUpload> page = new Page<BillingFileUpload>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "ORDER BY upload_date DESC");
		switch (status) {
		case "inserted":
			page.getParams().put("inserted_database", true);
			break;
		case "notInserted":
			page.getParams().put("inserted_database", false);
			break;
		}
		switch (billing_type) {
		case "all":
			break;
		default:
			page.getParams().put("billing_type", billing_type);
			break;
		}
		page = this.billingService.queryBillingFileUploadsByPage(page);
		
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute("billing_type", billing_type);
		model.addAttribute("all".equals(billing_type) ? (status + "Active") : (billing_type + "InsertedActive"), "active");

		// BEGIN QUERY SUM BY INSERTED_DATABASE
		Page<BillingFileUpload> pageStatusSum = new Page<BillingFileUpload>();
		pageStatusSum.getParams().put("inserted_database", true);
		model.addAttribute("insertedSum", this.billingService.queryBillingFileUploadsSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("inserted_database", false);
		model.addAttribute("notInsertedSum", this.billingService.queryBillingFileUploadsSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("billing_type", "chorus");
		pageStatusSum.getParams().put("inserted_database", true);
		model.addAttribute("chorusInsertedSum", this.billingService.queryBillingFileUploadsSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("billing_type", "callplus");
		pageStatusSum.getParams().put("inserted_database", true);
		model.addAttribute("callplusInsertedSum", this.billingService.queryBillingFileUploadsSumByPage(pageStatusSum));
		// END QUERY SUM BY INSERTED_DATABASE
		
		model.addAttribute("users", this.systemService.queryUser(new User()));
		
		
		return "broadband-user/manual-manipulation/call-billing-view";
	}
	
	// Upload Call Billing Record CSV
	@RequestMapping(value = "/broadband-user/manual-manipulation/call-billing-record/csv/upload", method = RequestMethod.POST)
	public String uploadBillingFileCSV(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("billingType") String billingType
			, @RequestParam("billing_type") String billing_type
			, @RequestParam("call_billing_record_csv_file") MultipartFile call_billing_record_csv_file
			, HttpServletRequest req) {
		
		Date uploadDate = new Date();
		String fileName = call_billing_record_csv_file.getOriginalFilename();

		if(!call_billing_record_csv_file.isEmpty()){
			String save_path = TMUtils.createPath("broadband" + File.separator
					+ "call_billing_record" + File.separator + fileName);
			try {
				call_billing_record_csv_file.transferTo(new File(save_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			BillingFileUpload bfu = new BillingFileUpload();
			bfu.setUpload_path(save_path);													// upload_path
			bfu.setBilling_type(billing_type);												// billing_type
			bfu.setInserted_database(false);												// inserted_database
			bfu.setUpload_date(uploadDate);													// upload_date
			bfu.setUpload_file_name(fileName);	// upload_file_name
			User user = (User) req.getSession().getAttribute("userSession");
			bfu.setUpload_by(user.getId());													// user_id
			bfu.setStatement_date("chorus".equals(billing_type) ? CallingRecordUtility.statementDate(save_path) : null);			// statement_date
			this.billingService.createBillingFileUpload(bfu);
		}
		
		return "redirect:/broadband-user/manual-manipulation/call-billing-record/view/" + pageNo + "/" + status + "/" + billingType;
	}
	
	// Download Call Billing Record CSV
	@RequestMapping(value = "/broadband-user/manual-manipulation/call-billing-record/csv/download/{billingFileId}")
    public ResponseEntity<byte[]> downloadBillingFileCSV(Model model
    		,@PathVariable(value = "billingFileId") int billingFileId) throws IOException {
		String filePath = this.billingService.queryBillingFilePathById(billingFileId);
		
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
	@RequestMapping(value = "/broadband-user/manual-manipulation/call-billing-record/csv/delete", method = RequestMethod.POST)
	public String deleteBillingFileCSV(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("billingFileId") Integer billingFileId
			, @RequestParam("filePath") String filePath
			, @RequestParam("billingType") String billingType
			, @RequestParam("billing_type") String billing_type
			, HttpServletRequest req) {
		
		this.billingService.removeBillingFileUpload(billingFileId);
		
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		
		return "redirect:/broadband-user/manual-manipulation/call-billing-record/view/" + pageNo + "/" + status + "/" + billingType;
	}
	
	// Insert Call Billing Record CSV into database
	@RequestMapping(value = "/broadband-user/manual-manipulation/call-billing-record/csv/insert", method = RequestMethod.POST)
	public String insertBillingFileCSVIntoDatabase(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("billingFileId") String billingFileId
			, @RequestParam("statementDate") String statementDate
			, @RequestParam("filePath") String filePath
			, @RequestParam("billing_type") String billing_type
			, HttpServletRequest req) {

		BillingFileUpload bfu = new BillingFileUpload();
		bfu.getParams().put("id", billingFileId);
		bfu.setInserted_database(true);					// assign inserted_database to true which is 1
		bfu.setInsert_date(new Timestamp(System.currentTimeMillis()));
		this.billingService.editBillingFileUpload(bfu);
		
		if("chorus".equals(billing_type)){
			CustomerCallRecord ccrTemp = new CustomerCallRecord();
			ccrTemp.getParams().put("statement_date", TMUtils.parseDateYYYYMMDD(statementDate));
			this.billingService.removeCustomerCallRecord(ccrTemp);
			
			// Get All data from the CSV file
			List<CustomerCallRecord> ccrs = CallingRecordUtility.ccrs(filePath);
			
			// Iteratively insert into database
			for (CustomerCallRecord ccr : ccrs) {
				ccr.setUpload_date(new Date());
				this.billingService.createCustomerCallRecord(ccr);
			}
		} else if("callplus".equals(billing_type)){
			List<CustomerCallingRecordCallplus> ccrcs = CallingRecordUtility_CallPlus.ccrcs(filePath);
			
			for (CustomerCallingRecordCallplus ccrc : ccrcs) {
				this.billingService.createCustomerCallingRecordCallplus(ccrc);
			}
		}
		
		return "redirect:/broadband-user/manual-manipulation/call-billing-record/view/" + pageNo + "/" + status + "/" + billing_type;
	}
	
	// Insert Call International Rate CSV into database
	@RequestMapping(value = "/broadband-user/manual-manipulation/call-international-rate/csv/insert", method = RequestMethod.POST)
	public String insertCallInternationalRateCSVIntoDatabase(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("call_international_rate_csv_file") MultipartFile filePath) {

		if(!filePath.isEmpty()){
			String save_path = TMUtils.createPath("broadband" + File.separator
					+ "call_international_rate" + File.separator + filePath.getOriginalFilename());
			try {
				filePath.transferTo(new File(save_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			this.billingService.removeCallInternationalRate(null);
			
			// Get All data from the CSV file
			List<CallInternationalRate> cirs = CallInternationalRateUtility.cirs(save_path);
			
			// Iteratively insert into database
			for (CallInternationalRate cir : cirs) {
				this.billingService.createCallInternationalRate(cir);
			}
		}
		
		return "redirect:/broadband-user/manual-manipulation/call-international-rate/view/" + pageNo ;
	}
	// END CallInternationalRate
	
}
