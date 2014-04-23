package com.tm.broadband.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.service.CRMService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.CustomerOrganizationValidatedMark;
import com.tm.broadband.validator.mark.CustomerValidatedMark;

@RestController
@SessionAttributes(value= {"orderCustomer", "orderCustomerOrderDetails"})
public class SaleRestController {
	
	private CRMService crmService;

	@Autowired
	public SaleRestController(CRMService crmService) {
		this.crmService = crmService;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/personal", method = RequestMethod.POST)
	public JSONBean<Customer> doOrderPersonal(Model model,
			@Validated(CustomerValidatedMark.class) @RequestBody Customer customer, BindingResult result,  
			HttpServletRequest req) {

		model.addAttribute("orderCustomer", customer);
		JSONBean<Customer> json = this.returnJsonCustomer(customer, result);
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/business", method = RequestMethod.POST)
	public JSONBean<Customer> doOrderBusiness(Model model,
			@Validated(CustomerOrganizationValidatedMark.class) @RequestBody Customer customer, BindingResult result,  
			HttpServletRequest req) {

		model.addAttribute("orderCustomer", customer);
		JSONBean<Customer> json = this.returnJsonCustomer(customer, result);
		return json;
	}
	
	private JSONBean<Customer> returnJsonCustomer(Customer customer, BindingResult result) {
		JSONBean<Customer> json = new JSONBean<Customer>();
		json.setModel(customer);
		
		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		Customer cValid = new Customer();
		cValid.getParams().put("where", "query_exist_customer_by_mobile");
		cValid.getParams().put("cellphone", customer.getCellphone());
		int count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("cellphone", "is already in use");
			return json;
		}
		
		cValid.getParams().put("where", "query_exist_customer_by_email");
		cValid.getParams().put("email", customer.getEmail());
		count = this.crmService.queryExistCustomer(cValid);

		if (count > 0) {
			json.getErrorMap().put("email", "is already in use");
			return json;
		}
		json.setUrl("/broadband-user/sale/online/ordering/order/confirm");
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/sale/online/ordering/order/details", method = RequestMethod.POST)
	public JSONBean<CustomerOrderDetail> doOrderDetails(Model model,
			@RequestBody List<CustomerOrderDetail> cods, HttpServletRequest req) {

		JSONBean<CustomerOrderDetail> json = new JSONBean<CustomerOrderDetail>();
		json.setModels(cods);
		
		for (CustomerOrderDetail customerOrderDetail : cods) {
			System.out.println(customerOrderDetail.getPstn_number());
		}
		
		System.out.println(cods.size());
		
		model.addAttribute("orderCustomerOrderDetails", cods);
		
		json.setUrl("/broadband-user/sale/online/ordering/order/");

		return json;
	}

}
