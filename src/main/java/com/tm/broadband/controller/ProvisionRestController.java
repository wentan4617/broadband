package com.tm.broadband.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.service.ProvisionService;

@RestController
public class ProvisionRestController {

	private ProvisionService provisionService;
	
	@Autowired
	public ProvisionRestController(ProvisionService provisionService) {
		this.provisionService = provisionService;
	}
	
	@RequestMapping(value = "/broadband-user/provision/customer/order/{id}")
	public CustomerOrder queryCustomerOrderWithCustomer(Model model,
			@PathVariable(value = "id") int id) {

		CustomerOrder customerOrder = this.provisionService.queryCustomerOrderById(id);
		return customerOrder;
	}

}
