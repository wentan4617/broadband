package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerCredit;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.model.User;
import com.tm.broadband.pdf.CreditPDFCreator;
import com.tm.broadband.pdf.OrderPDFCreator;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.service.PlanService;
import com.tm.broadband.service.SaleService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerCreditValidatedMark;

@Controller
@SessionAttributes("orderPlan")
public class SaleController {
	
	private PlanService planService;
	private CRMService crmService;
	private SaleService saleService;

	@Autowired
	public SaleController(PlanService planService, CRMService crmService
			, SaleService saleService) {
		this.planService = planService;
		this.crmService = crmService;
		this.saleService = saleService;
	}
	
	@RequestMapping("/broadband-user/sale/online/ordering/plans")
	public String plans(Model model) {
		
		List<Plan> plans = null;
		
		Plan plan = new Plan();
		plan.getParams().put("plan_group", "plan-term");
		plan.getParams().put("plan_status", "selling");
		plan.getParams().put("plan_sort", "CLOTHING");
		plan.getParams().put("orderby", "order by data_flow");
		
		plans = this.planService.queryPlansBySome(plan);
		
		model.addAttribute("plans", plans);
		
		Hardware hardware = new Hardware();
		hardware.getParams().put("hardware_status", "selling");
		List<Hardware> hardwares = this.planService.queryHardwaresBySome(hardware);
		model.addAttribute("hardwares", hardwares);
		
		return "broadband-user/sale/online-ordering";
	}
	
	@RequestMapping("/broadband-user/sale/online/ordering/order/{id}")
	public String orderPlanTerm(Model model, @PathVariable("id") int id) {
		
		Plan plan = this.planService.queryPlanById(id);
		model.addAttribute("orderPlan", plan);
		
		return "broadband-user/sale/online-ordering-customer-info";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/confirm")
	public String orderConfirm(Model model,
			@ModelAttribute("orderPlan") Plan plan, 
			HttpServletRequest req,
			RedirectAttributes attr) {
		
		Customer customer = (Customer)req.getSession().getAttribute("orderCustomer");
		List<CustomerOrderDetail> cods = (List<CustomerOrderDetail>)req.getSession().getAttribute("orderCustomerOrderDetails");
		
		customer.getCustomerOrder().setCustomerOrderDetails(new ArrayList<CustomerOrderDetail>());
		customer.getCustomerOrder().getCustomerOrderDetails().addAll(cods);
		customer.getCustomerOrder().setOrder_create_date(new Date());

		CustomerOrderDetail cod_plan = new CustomerOrderDetail();
		
		cod_plan.setDetail_name(plan.getPlan_name());
		cod_plan.setDetail_desc(plan.getPlan_desc());
		cod_plan.setDetail_price(plan.getPlan_price() == null ? 0d : plan.getPlan_price());
		cod_plan.setDetail_data_flow(plan.getData_flow());
		cod_plan.setDetail_plan_status(plan.getPlan_status());
		cod_plan.setDetail_plan_type(plan.getPlan_type());
		cod_plan.setDetail_plan_sort(plan.getPlan_sort());
		cod_plan.setDetail_plan_group(plan.getPlan_group());
		cod_plan.setDetail_plan_memo(plan.getMemo());
		cod_plan.setDetail_unit(plan.getPlan_prepay_months() == null ? 1 : plan.getPlan_prepay_months());
		cod_plan.setDetail_type(plan.getPlan_group());
		cod_plan.setDetail_term_period(plan.getTerm_period());
		
		customer.getCustomerOrder().getCustomerOrderDetails().add(0, cod_plan);
		
		if ("plan-term".equals(plan.getPlan_group())) {
			
			if ("new-connection".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
				
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
				
				CustomerOrderDetail cod_conn = new CustomerOrderDetail();
				cod_conn.setDetail_name("Installation");
				cod_conn.setDetail_price(plan.getPlan_new_connection_fee());
				cod_conn.setDetail_is_next_pay(0);
				cod_conn.setDetail_type("new-connection");
				cod_conn.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_conn);

			} else if ("transition".equals(customer.getCustomerOrder().getOrder_broadband_type())) {
					
				customer.getCustomerOrder().setOrder_total_price(plan.getPlan_price() * plan.getPlan_prepay_months());
				
				CustomerOrderDetail cod_trans = new CustomerOrderDetail();
				cod_trans.setDetail_name("Broadband Transition");
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_is_next_pay(0);
				cod_trans.setDetail_price(0d);
				cod_trans.setDetail_type("transition");
				cod_trans.setDetail_unit(1);
				
				customer.getCustomerOrder().getCustomerOrderDetails().add(cod_trans);
			}
		}
		
		if (cods != null) {
			for (CustomerOrderDetail cod : cods) {
				if ("hardware-router".equals(cod.getDetail_type())) {
					cod.setDetail_is_next_pay(0);
					cod.setIs_post(0);
					customer.getCustomerOrder().setHardware_post(customer.getCustomerOrder().getHardware_post() == null ? 1 : customer.getCustomerOrder().getHardware_post() + 1);
					customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + cod.getDetail_price());
				} else if ("pstn".equals(cod.getDetail_type()) 
						|| "voip".equals(cod.getDetail_type())){
					cod.setDetail_unit(1);
					cod.setDetail_is_next_pay(1);
					customer.getCustomerOrder().setOrder_total_price(customer.getCustomerOrder().getOrder_total_price() + cod.getDetail_price());
				}
			}
		}
		
		
		return "broadband-user/sale/online-ordering-confirm";
	}
	
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/confirm/save")
	public String orderSave(Model model, @ModelAttribute("orderPlan") Plan plan, 
			RedirectAttributes attr, SessionStatus status, HttpServletRequest req) {
		
		Customer customer = (Customer)req.getSession().getAttribute("orderCustomer");
		
		customer.setUser_name(customer.getLogin_name());
		customer.getCustomerOrder().setOrder_status("pending");
		customer.getCustomerOrder().setOrder_type(plan.getPlan_group().replace("plan", "order"));
		customer.getCustomerOrder().setSale_id(customer.getId());
		customer.getCustomerOrder().setSignature("unsigned");
		User user = (User) req.getSession().getAttribute("userSession");
		customer.getCustomerOrder().setSale_id(user.getId());
		
		
		this.crmService.saveCustomerOrder(customer, customer.getCustomerOrder());
		
		// BEGIN SET NECESSARY AND GENERATE ORDER PDF
		String orderPDFPath = null;
		try {
			orderPDFPath = new OrderPDFCreator(customer, customer.getCustomerOrder(), customer.getOrganization()).create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		CustomerOrder co = new CustomerOrder();
		co.getParams().put("id", customer.getCustomerOrder().getId());
		co.setOrder_pdf_path(orderPDFPath);
		
		this.crmService.editCustomerOrder(co);
		// END SET NECESSARY INFO AND GENERATE ORDER PDF
		
		
		status.setComplete();
		req.getSession().removeAttribute("orderCustomer");
		
		return "redirect:/broadband-user/sale/online/ordering/order/credit/" + customer.getId() + "/" + customer.getCustomerOrder().getId();
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/credit/{customer_id}/{order_id}")
	public String toCredit(Model model, @PathVariable("customer_id") Integer customer_id
			, @PathVariable("order_id") Integer order_id) {
		
		model.addAttribute("customer_id", customer_id);
		model.addAttribute("order_id", order_id);
		model.addAttribute("customerCredit", new CustomerCredit());
		return "broadband-user/sale/online-ordering-credit";
	}
	
	// DOWNLOAD ORDER PDF
	@RequestMapping(value = "/broadband-user/crm/customer/order/pdf/download/{order_id}")
    public ResponseEntity<byte[]> downloadOrderPDF(Model model
    		,@PathVariable(value = "order_id") int order_id) throws IOException {
		String filePath = this.saleService.queryCustomerOrderFilePathById(order_id);
		
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
	
	// DOWNLOAD CREDIT PDF
	@RequestMapping(value = "/broadband-user/crm/customer/order/credit/pdf/download/{order_id}")
    public ResponseEntity<byte[]> downloadCreditPDF(Model model
    		,@PathVariable(value = "order_id") int order_id) throws IOException {
		String filePath = this.saleService.queryCustomerCreditFilePathById(order_id);
		
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
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/credit/create", method = RequestMethod.POST)
	public String doCredit(Model model,
			@ModelAttribute("customerCredit") @Validated(CustomerCreditValidatedMark.class) CustomerCredit customerCredit
			,@RequestParam("customer_id") Integer customer_id
			,@RequestParam("order_id") Integer order_id
			,BindingResult result
			,RedirectAttributes attr) {
		model.addAttribute("panelheading", "Customer Credit Card Information");
		model.addAttribute("action", "/broadband-user/sale/online/ordering/order/credit/create");

		if (result.hasErrors()) {
			return "broadband-user/sale/online-ordering-credit";
		}
		
		CustomerOrder co = new CustomerOrder();
		co.setId(order_id);
		this.saleService.createCustomerCredit(customerCredit);

		// BEGIN CREDIT PDF
		CreditPDFCreator cPDFCreator = new CreditPDFCreator();
		customerCredit.setCustomer(this.crmService.queryCustomerById(customer_id));
		cPDFCreator.setCc(customerCredit);
		cPDFCreator.setCo(co);
		cPDFCreator.setOrg(this.saleService.queryOrganizationByCustomerId(customer_id));
		String creditPDFPath = null;
		try {
			creditPDFPath = cPDFCreator.create();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		co.getParams().put("id", order_id);
		co.setCredit_pdf_path(creditPDFPath);
		this.crmService.editCustomerOrder(co);
		// END CREDIT PDF
		
		return "redirect:/broadband-user/sale/online-ordering-result/" + order_id + "/" + customer_id;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online-ordering-result/{order_id}/{customer_id}")
	public String toCreditResult(Model model
			, @PathVariable("order_id") Integer order_id
			, @PathVariable("customer_id") Integer customer_id) {
		
		CustomerOrder co = new CustomerOrder();
		co.setId(order_id);
		model.addAttribute("customerOrder", co);
		
		return "broadband-user/sale/online-ordering-result";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/upload")
	public String uploadSignedPDF(Model model
			, @RequestParam("order_id") Integer order_id
			, @RequestParam("customer_id") Integer customer_id
			, @RequestParam("order_pdf_path") MultipartFile order_pdf_path
			, @RequestParam("credit_pdf_path") MultipartFile credit_pdf_path) {

		
		if(!order_pdf_path.isEmpty() && !credit_pdf_path.isEmpty()){
			String order_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "application_" + order_id
					+ ".pdf");
			String credit_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "credit_" + order_id
					+ ".pdf");
			try {
				order_pdf_path.transferTo(new File(order_path));
				credit_pdf_path.transferTo(new File(credit_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			CustomerOrder co = new CustomerOrder();
			co.getParams().put("id", order_id);
			co.setSignature("signed");
			this.crmService.editCustomerOrder(co);
		}
		
		return "redirect:/broadband-user/sale/online-ordering-upload-result";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/upload-single")
	public String uploadSinglePDF(Model model
			, @RequestParam("sale_id") Integer sale_id
			, @RequestParam("order_id") Integer order_id
			, @RequestParam("customer_id") Integer customer_id
			, @RequestParam("order_pdf_path") MultipartFile order_pdf_path
			, @RequestParam("credit_pdf_path") MultipartFile credit_pdf_path
			,HttpServletRequest req) {

		
		if(!order_pdf_path.isEmpty()){
			String order_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "application_" + order_id
					+ ".pdf");
			try {
				order_pdf_path.transferTo(new File(order_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			CustomerOrder co = new CustomerOrder();
			co.getParams().put("id", order_id);
			co.setSignature("signed");
			this.crmService.editCustomerOrder(co);
		}
		if(!credit_pdf_path.isEmpty()){
			String credit_path = TMUtils.createPath("broadband" + File.separator
					+ "customers" + File.separator + customer_id
					+ File.separator + "credit_" + order_id
					+ ".pdf");
			try {
				credit_pdf_path.transferTo(new File(credit_path));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			CustomerOrder co = new CustomerOrder();
			co.getParams().put("id", order_id);
			co.setSignature("signed");
			this.crmService.editCustomerOrder(co);
		}
		
		return "redirect:/broadband-user/sale/online/ordering/view/1/" + sale_id;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online-ordering-upload-result")
	public String toUploadResult(Model model) {
		
		return "broadband-user/sale/online-order-view";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/view/{pageNo}/{sale_id}")
	public String onlineOrderView(Model model
			, @PathVariable("pageNo") int pageNo
			, @PathVariable("sale_id") int sale_id
			,HttpServletRequest req) {

		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(pageNo);
		page.getParams().put("orderby", "order by order_create_date desc");
		page.getParams().put("where", "query_sale_id_not_null");
		User user = (User) req.getSession().getAttribute("userSession");

		Page<CustomerOrder> pageSignatureSum = new Page<CustomerOrder>();
		
		if(user.getUser_role().equals("sales")){
			page.getParams().put("sale_id", user.getId());
			sale_id = user.getId();
			pageSignatureSum.getParams().put("sale_id", user.getId());
		} else if(sale_id!=0){
			page.getParams().put("sale_id", sale_id);
			pageSignatureSum.getParams().put("sale_id", sale_id);
		}
		
		this.saleService.queryOrdersByPage(page);
		List<User> users = this.saleService.queryUsersWhoseIdExistInOrder();
		
		model.addAttribute("page", page);
		model.addAttribute("users", users);
		model.addAttribute("sale_id", sale_id);

		// BEGIN QUERY SUM BY SIGNATURE
		pageSignatureSum.getParams().put("signature", "signed");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("signedSum", pageSignatureSum.getTotalRecord());
		pageSignatureSum.getParams().put("signature", "unsigned");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("unsignedSum", pageSignatureSum.getTotalRecord());
		// END QUERY SUM BY SIGNATURE
		
		return "broadband-user/sale/online-order-view";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/view/by_sales_id")
	public String onlineOrderViewBySalesId(Model model
			,HttpServletRequest req
			,@RequestParam("sale_id") Integer sale_id) {

		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(1);
		page.getParams().put("orderby", "order by order_create_date desc");
		User user = (User) req.getSession().getAttribute("userSession");


		Page<CustomerOrder> pageSignatureSum = new Page<CustomerOrder>();
		
		if(user.getUser_role().equals("sales")){
			page.getParams().put("sale_id", user.getId());
			sale_id = user.getId();
			pageSignatureSum.getParams().put("sale_id", user.getId());
		} else if(sale_id!=0){
			page.getParams().put("sale_id", sale_id);
			pageSignatureSum.getParams().put("sale_id", sale_id);
		}
		
		this.saleService.queryOrdersByPage(page);
		model.addAttribute("page", page);
		model.addAttribute("users", this.saleService.queryUsersWhoseIdExistInOrder());
		model.addAttribute("sale_id", sale_id);

		// BEGIN QUERY SUM BY SIGNATURE
		pageSignatureSum.getParams().put("signature", "signed");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("signedSum", pageSignatureSum.getTotalRecord());
		pageSignatureSum.getParams().put("signature", "unsigned");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("unsignedSum", pageSignatureSum.getTotalRecord());
		// END QUERY SUM BY SIGNATURE
		
		return "broadband-user/sale/online-order-view";
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/view/by/{sale_id}/{signature}")
	public String onlineOrderViewBySignature(Model model
			,HttpServletRequest req
			, @PathVariable("signature") String signature
			, @PathVariable("sale_id") int sale_id) {
		
		if(signature.equals("signed")){
			model.addAttribute("signedActive", "active");
		} else if(signature.equals("unsigned")){
			model.addAttribute("unsignedActive", "active");
		}

		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(1);
		page.getParams().put("signature", signature);
		page.getParams().put("orderby", "order by order_create_date desc");
		User user = (User) req.getSession().getAttribute("userSession");


		Page<CustomerOrder> pageSignatureSum = new Page<CustomerOrder>();
		
		if(user.getUser_role().equals("sales")){
			page.getParams().put("sale_id", user.getId());
			sale_id = user.getId();
			pageSignatureSum.getParams().put("sale_id", user.getId());
		} else if(sale_id!=0){
			page.getParams().put("sale_id", sale_id);
			pageSignatureSum.getParams().put("sale_id", sale_id);
		}
		
		this.saleService.queryOrdersByPage(page);
		model.addAttribute("page", page);
		model.addAttribute("users", this.saleService.queryUsersWhoseIdExistInOrder());
		model.addAttribute("sale_id", sale_id);

		// BEGIN QUERY SUM BY SIGNATURE
		pageSignatureSum.getParams().put("signature", "signed");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("signedSum", pageSignatureSum.getTotalRecord());
		pageSignatureSum.getParams().put("signature", "unsigned");
		this.saleService.queryOrdersSumByPage(pageSignatureSum);
		model.addAttribute("unsignedSum", pageSignatureSum.getTotalRecord());
		// END QUERY SUM BY SIGNATURE
		
		return "broadband-user/sale/online-order-view";
	}
}
