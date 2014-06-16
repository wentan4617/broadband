package com.tm.broadband.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
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
	
	@RequestMapping(value = "/broadband-user/provision/customer/order/detail/set", method = RequestMethod.POST)
	public CustomerOrderDetail orderDetailSet(Model model,
			@RequestParam("order_id") int order_id,
			@RequestParam("hardware_post") int hardware_post,
			@RequestParam("detail_id") int detail_id,
			@RequestParam("is_post") int is_post,
			@RequestParam("comment") String comment,
			@RequestParam("trackcode") String trackcode) {
		
		CustomerOrderDetail cod = new CustomerOrderDetail();
		cod.setIs_post(is_post);
		cod.setHardware_comment(comment);
		cod.setTrack_code(trackcode);
		cod.getParams().put("id", detail_id);
		
		CustomerOrder co = new CustomerOrder();
		if (cod.getIs_post() == 1) {
			co.setHardware_post(hardware_post - 1);
		} else {
			co.setHardware_post(hardware_post + 1);
		}
		co.getParams().put("id", order_id);

		this.provisionService.setHardwarePost(co, cod);
		
		cod.setCustomerOrder(co);
		
		return cod;
	}

}
