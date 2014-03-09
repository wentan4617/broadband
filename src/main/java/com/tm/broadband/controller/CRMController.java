package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerTransaction;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.util.TMUtils;

@Controller
public class CRMController {

	private CRMService crmService;

	@Autowired
	public CRMController(CRMService crmService) {
		this.crmService = crmService;
	}

	@RequestMapping("/broadband-user/crm/customer/view/{pageNo}")
	public String customerView(Model model, @PathVariable("pageNo") int pageNo) {
		
		Page<Customer> page = new Page<Customer>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by c.status");
		this.crmService.queryCustomersByPage(page);
		model.addAttribute("page", page);
		model.addAttribute("customerQuery", new Customer());
		return "broadband-user/crm/customer-view";
	}

	@RequestMapping("/broadband-user/crm/customer/query/{pageNo}")
	public String customerQuery(Model model, 
			@PathVariable("pageNo") int pageNo,
			@ModelAttribute("customerQuery") Customer customer, RedirectAttributes attr) {
		
		Page<Customer> page = new Page<Customer>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by c.status");
		page.getParams().put("id", customer.getId());
		page.getParams().put("login_name", customer.getLogin_name());
		page.getParams().put("phone", customer.getPhone());
		page.getParams().put("cellphone", customer.getCellphone());
		page.getParams().put("email", customer.getEmail());
		page.getParams().put("svlan", customer.getCustomerOrder().getSvlan());
		page.getParams().put("cvlan", customer.getCustomerOrder().getCvlan());
		
		this.crmService.queryCustomersByPage(page);
		model.addAttribute("page", page);
		return "broadband-user/crm/customer-view";
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/edit/{id}")
	public String toCustomerEdit(Model model,
			@PathVariable(value = "id") int id) {
		
		model.addAttribute("panelheading", "Customer Edit");
		model.addAttribute("action", "/broadband-user/plan/edit");
		
		Customer customer = this.crmService.queryCustomerByIdWithCustomerOrder(id);
		
		model.addAttribute("customer", customer);
		
		return "broadband-user/crm/customer";
	}
	
	
	@RequestMapping(value = "/broadband-user/crm/transaction/view/{pageNo}/{customerId}")
	@ResponseBody
	public Page<CustomerTransaction> transactionPage(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "customerId") int customerId) {
		
		Page<CustomerTransaction> transactionPage = new Page<CustomerTransaction>();
		transactionPage.setPageNo(pageNo);
		transactionPage.setPageSize(30);
		transactionPage.getParams().put("orderby", "order by transaction_date desc");
		transactionPage.getParams().put("customer_id", customerId);
		this.crmService.queryCustomerTransactionsByPage(transactionPage);

		return transactionPage;
	}
	
	@RequestMapping(value = "/broadband-user/crm/invoice/view/{pageNo}/{customerId}")
	@ResponseBody
	public Map<String, Object> InvoicePage(Model model,
			@PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "customerId") int customerId) {
		
		Page<CustomerInvoice> invoicePage = new Page<CustomerInvoice>();
		invoicePage.setPageNo(pageNo);
		invoicePage.setPageSize(12);
		invoicePage.getParams().put("orderby", "order by create_date desc");
		invoicePage.getParams().put("customer_id", customerId);
		this.crmService.queryCustomerInvoicesByPage(invoicePage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("invoicePage", invoicePage);
		map.put("transactionsList", this.crmService.queryCustomerTransactionsByCustomerId(customerId));
		return map;
	}
	
	@RequestMapping(value = "/broadband-user/crm/customer/order/save")
	@ResponseBody
	public CustomerOrder saveCustomerOrderEdit(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("cvlan_input") String cvlan_input,
			@RequestParam("svlan_input") String svlan_input,
			@RequestParam("order_using_start_input") String order_using_start_input,
			@RequestParam("order_detail_unit") Integer order_detail_unit,
			HttpServletRequest req) {

		// customer order begin
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.getParams().put("id", order_id);
		customerOrder.setId(order_id);
		customerOrder.setSvlan(svlan_input);
		customerOrder.setCvlan(cvlan_input);
		customerOrder.setOrder_using_start(TMUtils.parseDateYYYYMMDD(order_using_start_input));
		// next invoice date
		if(order_detail_unit==null){
			order_detail_unit = 1;
		}
		int nextInvoiceDay = 30 * order_detail_unit - 15;
		Calendar calnextInvoiceDay = Calendar.getInstance();
		calnextInvoiceDay.setTime(customerOrder.getOrder_using_start());
		calnextInvoiceDay.add(Calendar.DAY_OF_MONTH, nextInvoiceDay);
		// set next invoice date
		customerOrder.setNext_invoice_create_date(calnextInvoiceDay.getTime());
		customerOrder.setOrder_status("using");
		// customer order end
		
		// provision log begin
		ProvisionLog proLog = new ProvisionLog();
		// get user from session
		User user = (User) req.getSession().getAttribute("userSession");
		proLog.setUser(user);
		proLog.setOrder_id_customer(customerOrder);
		proLog.setOrder_sort("customer-order");
		proLog.setProcess_way("paid to using");
		// provision log end

		
		this.crmService.editCustomerOrder(customerOrder, proLog);
		
		// mailer
		
		return customerOrder;
	}
	@RequestMapping(value = "/broadband-user/crm/customer/order/edit")
	@ResponseBody
	public CustomerOrder toCustomerInvoiceEdit(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("cvlan_input") String cvlan_input,
			@RequestParam("svlan_input") String svlan_input,
			@RequestParam("order_using_start_input") String order_using_start_input,
			@RequestParam("order_detail_unit") Integer order_detail_unit,
			HttpServletRequest req) {
		
		// get user from session
		User user = (User) req.getSession().getAttribute("userSession");
		
		// CustomerOrder begin
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.getParams().put("id", order_id);
		customerOrder.setId(order_id);
		customerOrder.setSvlan(svlan_input);
		customerOrder.setCvlan(cvlan_input);
		customerOrder.setOrder_using_start(TMUtils.parseDateYYYYMMDD(order_using_start_input));
		
		int nextInvoiceDay = 30 * order_detail_unit - 15;
		Calendar calnextInvoiceDay = Calendar.getInstance();
		calnextInvoiceDay.setTime(customerOrder.getOrder_using_start());
		calnextInvoiceDay.add(Calendar.DAY_OF_MONTH, nextInvoiceDay);
		
		customerOrder.setNext_invoice_create_date(calnextInvoiceDay.getTime());
		
		// ProvisionLog begin
		ProvisionLog proLog = new ProvisionLog();
		proLog.setUser(user);
		proLog.setOrder_id_customer(customerOrder);
		proLog.setOrder_sort("customer-order");
		proLog.setProcess_way("paid to using");
		// ProvisionLog end
		
		this.crmService.editCustomerOrderAndCreateProvision(customerOrder, proLog);
		return customerOrder;
	}

	// create invoice PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/pdf/generate/{invoiceId}")
	public String generateInvoicePDF(Model model
    		,@PathVariable(value = "invoiceId") int invoiceId){
		System.out.println("generating invoice PDF - #"+invoiceId);
		this.crmService.createInvoicePDFByInvoiceID(invoiceId);
		// jump to an empty page and close it
		return "broadband-user/crm/new-window";
	}
	
	// download invoice PDF directly
	@RequestMapping(value = "/broadband-user/crm/customer/invoice/pdf/download/{invoiceId}")
    public ResponseEntity<byte[]> downloadInvoicePDF(Model model
    		,@PathVariable(value = "invoiceId") int invoiceId) throws IOException {
		String filePath = this.crmService.queryCustomerInvoiceFilePathById(invoiceId);
		
		// get file path
        Path path = Paths.get(filePath);
        byte[] contents = null;
        // transfer file contents to bytes
        contents = Files.readAllBytes( path );
        
        HttpHeaders headers = new HttpHeaders();
        // set spring framework media type
        headers.setContentType( MediaType.parseMediaType( "application/pdf" ) );
        // get file name with file's suffix
        String filename = "Invoice_"+URLEncoder.encode(filePath.substring(filePath.lastIndexOf(File.separator)+1, filePath.indexOf("."))+".pdf", "UTF-8");
        headers.setContentDispositionFormData( filename, filename );
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>( contents, headers, HttpStatus.OK );
        return response;
    }

}
