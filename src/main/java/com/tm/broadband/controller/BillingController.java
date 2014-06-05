package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tm.broadband.model.BillingFileUpload;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.User;
import com.tm.broadband.service.BillingService;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.CallingRecordUltility;
import com.tm.broadband.util.TMUtils;

@Controller
public class BillingController {

	private BillingService billingService;
	private SystemService systemService;
	
	@Autowired
	public BillingController(BillingService billingService,
			SystemService systemService) {
		this.billingService = billingService;
		this.systemService = systemService;
	}

//	@RequestMapping("/broadband-user/billing/billing-file-upload")
//	public String toBillingFileUpload(Model model) {
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH);
//		
//		model.addAttribute("cur_year", year);
//		model.addAttribute("cur_month", month);
//		
//		return "broadband-user/billing/billing-file-upload";
//	}

	@RequestMapping("/broadband-user/billing/call-billing-record/view/{pageNo}/{status}")
	public String toBillingFileUpload(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("status") String status) {

		Page<BillingFileUpload> page = new Page<BillingFileUpload>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("orderby", "ORDER BY statement_date DESC");
		switch (status) {
		case "inserted":
			page.getParams().put("inserted_database", 1);
			break;
		case "notInserted":
			page.getParams().put("inserted_database", 0);
			break;
		}
		page = this.billingService.queryBillingFileUploadsByPage(page);
		
		model.addAttribute("page", page);
		model.addAttribute("status", status);
		model.addAttribute(status + "Active", "active");

		// BEGIN QUERY SUM BY INSERTED_DATABASE
		Page<BillingFileUpload> pageStatusSum = new Page<BillingFileUpload>();
		pageStatusSum.getParams().put("inserted_database", 1);
		model.addAttribute("insertedSum", this.billingService.queryBillingFileUploadsSumByPage(pageStatusSum));
		pageStatusSum.getParams().put("inserted_database", 0);
		model.addAttribute("notInsertedSum", this.billingService.queryBillingFileUploadsSumByPage(pageStatusSum));
		// END QUERY SUM BY INSERTED_DATABASE
		
		model.addAttribute("users", this.systemService.queryUser(new User()));
		
		
		return "broadband-user/billing/call-billing-view";
	}
	
	// Upload Call Billing Record CSV
	@RequestMapping(value = "/broadband-user/billing/call-billing-record/csv/upload", method = RequestMethod.POST)
	public String uploadBillingFileCSV(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("billing_type") String billing_type
			, @RequestParam("call_billing_record_csv_file") MultipartFile call_billing_record_csv_file
			, HttpServletRequest req) {
		
		Date uploadDate = new Date();
		String filaName = call_billing_record_csv_file.getOriginalFilename();

		if(!call_billing_record_csv_file.isEmpty()){
			String save_path = TMUtils.createPath("broadband" + File.separator
					+ "call_billing_record" + File.separator + filaName);
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
			bfu.setUpload_file_name(filaName);	// upload_file_name
			User user = (User) req.getSession().getAttribute("userSession");
			bfu.setUpload_by(user.getId());													// user_id
			bfu.setStatement_date(CallingRecordUltility.statementDate(save_path));			// statement_date
			this.billingService.createBillingFileUpload(bfu);
		}
		
		return "redirect:/broadband-user/billing/call-billing-record/view/" + pageNo + "/" + status;
	}
	
	// Download Call Billing Record CSV
	@RequestMapping(value = "/broadband-user/billing/call-billing-record/csv/download/{billingFileId}")
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
	@RequestMapping(value = "/broadband-user/billing/call-billing-record/csv/delete", method = RequestMethod.POST)
	public String uploadBillingFileCSV(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("billingFileId") Integer billingFileId
			, @RequestParam("filePath") String filePath
			, HttpServletRequest req) {
		
		this.billingService.removeBillingFileUpload(billingFileId);
		
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		
		return "redirect:/broadband-user/billing/call-billing-record/view/" + pageNo + "/" + status;
	}
	
	// Insert Call Billing Record CSV into database
	@RequestMapping(value = "/broadband-user/billing/call-billing-record/csv/insert", method = RequestMethod.POST)
	public String insertBillingFileCSVIntoDatabase(Model model
			, @RequestParam("pageNo") int pageNo
			, @RequestParam("status") String status
			, @RequestParam("billingFileId") String billingFileId
			, @RequestParam("statementDate") String statementDate
			, @RequestParam("filePath") String filePath
			, HttpServletRequest req) {
		
		CustomerCallRecord ccrTemp = new CustomerCallRecord();
		ccrTemp.getParams().put("statement_date", TMUtils.parseDateYYYYMMDD(statementDate));
		this.billingService.removeCustomerCallRecord(ccrTemp);
		
		BillingFileUpload bfu = new BillingFileUpload();
		bfu.getParams().put("id", billingFileId);
		bfu.setInserted_database(true);					// assign inserted_database to true which is 1
		bfu.setInsert_date(new Timestamp(System.currentTimeMillis()));
		this.billingService.editBillingFileUpload(bfu);
		
		// Get All data from the CSV file
		List<CustomerCallRecord> ccrs = CallingRecordUltility.ccrs(filePath);
		
		// Iteratively insert into database
		for (CustomerCallRecord ccr : ccrs) {
			ccr.setUpload_date(new Date());
			this.billingService.createCustomerCallRecord(ccr);
		}
		
		
		return "redirect:/broadband-user/billing/call-billing-record/view/" + pageNo + "/" + status;
	}
}
