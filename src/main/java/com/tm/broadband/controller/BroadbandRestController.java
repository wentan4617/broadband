package com.tm.broadband.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tm.broadband.model.Broadband;
import com.tm.broadband.model.Customer;
import com.tm.broadband.util.broadband.BroadbandCapability;

@RestController
@SessionAttributes(value = { "customer", "orderCustomer"})
public class BroadbandRestController {

	public BroadbandRestController() {
	}
	
	@RequestMapping("/address/check/{address}")
	public Broadband checkAddress(@PathVariable("address") String address,
			@ModelAttribute("customer") Customer customer) {
		return returnBroadband(address, customer);
	}
	
	@RequestMapping("/sale/address/check/{address}")
	public Broadband checkAddressSale(@PathVariable("address") String address,
			@ModelAttribute("orderCustomer") Customer customer) {
		return returnBroadband(address, customer);
	}
	
	private Broadband returnBroadband(String address, Customer customer) {
		String message = "";
		try {
			message = BroadbandCapability.getCapabilityResultByAddress(address);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Broadband broadband = new Broadband();
		if (message.contains("> 10")) {
			broadband.setAdsl_available(true);
		} 
		if (message.contains("> 20")) {
			broadband.setVdsl_available(true);
		} 
		if (message.contains("Business fibre available") || message.contains("Network capability:<\\/h4><ul><li>UFB fibre up to 100 Mbps")) {
			broadband.setUfb_available(true);
		} 
		broadband.setScheduled(message.substring(message.lastIndexOf(",") + 1));
		customer.setAddress(address);
		return broadband;
	}
	
	@RequestMapping(value="/do/service")
	public void doServiceAvailable(@ModelAttribute("customer") Customer customer) {
		customer.setServiceAvailable(true);
		System.out.println("do service...");
	}

}
