package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.tm.broadband.model.ManualManipulationRecord;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;
import com.tm.broadband.model.VOSVoIPRate;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.CallingRecordUtility;
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
	@RequestMapping(value = "/broadband-user/manual-manipulation/manual-manipulation-record/view/{pageNo}")
	public String manualManipulationRecordView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<ManualManipulationRecord> page = new Page<ManualManipulationRecord>();
		page.setPageNo(pageNo);
		page.setPageSize(50);
		page.getParams().put("orderby", "ORDER BY manipulation_time DESC");
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

		User user = (User) req.getSession().getAttribute("userSession");
		try {
			crmService.createDDPayInvoiceManualManipulationRecord(
				mmr,
				user
			);
		} catch (ParseException e) { e.printStackTrace(); }
		
		attr.addFlashAttribute("success", "Create Manual Manipulation Record is successful.");

		return "redirect:/broadband-user/manual-manipulation/manual-manipulation-record/view/" + pageNo;
	}

	@RequestMapping(value = "/broadband-user/manual-manipulation/manual-manipulation-record/non-ddpay/create/{pageNo}", method = RequestMethod.POST)
	public String doNonDDPayInvoiceManualManipulationRecordCreate(
			@PathVariable(value = "pageNo") int pageNo,
			@ModelAttribute("manualManipulationRecord") ManualManipulationRecord mmr,
			@RequestParam("next_invoice_create_date") String next_invoice_create_date,
			HttpServletRequest req,
			RedirectAttributes attr) {

		User user = (User) req.getSession().getAttribute("userSession");
		try {
			crmService.createNonDDPayInvoiceManualManipulationRecord(
				new SimpleDateFormat("yyyy-MM-dd").parse(next_invoice_create_date),
				mmr,
				user
			);
		} catch (ParseException e) { e.printStackTrace(); }
		
		attr.addFlashAttribute("success", "Create Manual Manipulation Record is successful.");

		return "redirect:/broadband-user/manual-manipulation/manual-manipulation-record/view/" + pageNo;
	}
	// END ManualManipulationRecord
	
	// BEGIN CallInternationalRate
	@RequestMapping("/broadband-user/manual-manipulation/pstn-calling-rate/view/{pageNo}")
	public String toCallInternationalRate(Model model
			, @PathVariable("pageNo") int pageNo) {

		Page<CallInternationalRate> page = new Page<CallInternationalRate>();
		page.setPageNo(pageNo);
		page.setPageSize(50);
		page.getParams().put("orderby", "ORDER BY rate_type");
		page = this.billingService.queryCallInternationalRatesByPage(page);
		
		model.addAttribute("page", page);		
		
		return "broadband-user/manual-manipulation/pstn-calling-rate-view";
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
		case "vosVoIP":
			page.getParams().put("billing_type", "vos-voip");
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
		pageStatusSum.getParams().put("billing_type", "vos-voip");
		pageStatusSum.getParams().put("inserted_database", true);
		model.addAttribute("vosVoIPInsertedSum", this.billingService.queryBillingFileUploadsSumByPage(pageStatusSum));
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
		
		this.billingService.insertCustomerCallRecord(billingFileId, billing_type, statementDate, filePath);
		
		if("chorus-broadband-asid".equals(billing_type)){
			billing_type = "chorus";
		}
		
		return "redirect:/broadband-user/manual-manipulation/call-billing-record/view/" + pageNo + "/" + status + "/" + billing_type;
	}
	
	// Insert PSTN Calling Rate CSV into database
	@RequestMapping(value = "/broadband-user/manual-manipulation/pstn-calling-rate/csv/insert", method = RequestMethod.POST)
	public String insertCallInternationalRateCSVIntoDatabase(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("pstn_calling_rate_csv_file") MultipartFile filePath) {

		if(!filePath.isEmpty()){
			String save_path = TMUtils.createPath("broadband" + File.separator
					+ "call_international_rate" + File.separator + filePath.getOriginalFilename());
			try {
				filePath.transferTo(new File(save_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			this.billingService.insertCallInternationalRate(save_path);
		}
		
		return "redirect:/broadband-user/manual-manipulation/pstn-calling-rate/view/" + pageNo ;
	}
	// END CallInternationalRate
	
	// BEGIN VOSVoIPRate
	@RequestMapping("/broadband-user/manual-manipulation/vos-voip-call-rate/view/{pageNo}")
	public String toVOSVoIPRate(Model model
			, @PathVariable("pageNo") int pageNo) {

		Page<VOSVoIPRate> page = new Page<VOSVoIPRate>();
		page.setPageNo(pageNo);
		page.setPageSize(50);
		page.getParams().put("orderby", "ORDER BY rate_type");
		page = this.billingService.queryVOSVoIPRatesByPage(page);
		
		model.addAttribute("page", page);		
		
		return "broadband-user/manual-manipulation/voip-calling-rate-view";
	}
	
	// Insert VOSVoIPRate CSV into database
	@RequestMapping(value = "/broadband-user/manual-manipulation/vos-voip-call-rate/csv/insert", method = RequestMethod.POST)
	public String insertVOSVoIPRateCSVIntoDatabase(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("vos_voip_call_rate_csv_file") MultipartFile filePath) {

		if(!filePath.isEmpty()){
			String save_path = TMUtils.createPath("broadband" + File.separator
					+ "vos_voip_call_rate" + File.separator + filePath.getOriginalFilename());
			try {
				filePath.transferTo(new File(save_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			System.out.println("save_path: "+save_path);
			
			this.billingService.insertVOSVoIPRates(save_path);
		}
		
		return "redirect:/broadband-user/manual-manipulation/vos-voip-call-rate/view/" + pageNo ;
	}
	// END VOSVoIPRate
	
}
