package com.tm.broadband.controller;

import javax.servlet.http.HttpSession;

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
	
	@RequestMapping("/plans/address/check/{address}")
	public Broadband plansCheckAddress(@PathVariable("address") String address,
			HttpSession session) {
		System.out.println("customerReg check address");
		Customer customerReg = (Customer) session.getAttribute("customerReg");
		if (customerReg != null) {
			System.out.println("this is old customerReg");
			return returnBroadband(address, customerReg);
		} else {
			System.out.println("this is new customerReg");
			customerReg = new Customer();
			customerReg.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
			customerReg.setSelect_plan_id(0);
			customerReg.setSelect_plan_type("0");
			session.setAttribute("customerReg", customerReg);
			return returnBroadband(address, customerReg);
		}
	}
	
	@RequestMapping("/address/check/{address}")
	public Broadband checkAddress(@PathVariable("address") String address,
			HttpSession session) {
		System.out.println("customer check address");
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer != null) {
			return returnBroadband(address, customer);
		}
		return null;
	}
	
	@RequestMapping("/sale/plans/address/check/{address}")
	public Broadband checkAddressSale(@PathVariable("address") String address,
			HttpSession session) {
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		if (customerRegSale != null) {
			System.out.println("this is old customerReg");
			return returnBroadband(address, customerRegSale);
		} else {
			System.out.println("this is new customerReg");
			customerRegSale = new Customer();
			customerRegSale.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
			customerRegSale.setSelect_plan_id(0);
			customerRegSale.setSelect_plan_type("0");
			session.setAttribute("customerRegSale", customerRegSale);
			return returnBroadband(address, customerRegSale);
		}
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
		customer.setBroadband(broadband);
		broadband.setAddress(address);
		String services_available = "";
		if (message.contains("> 10")) {
			broadband.setAdsl_available(true);
			services_available += "ADSL";
		} 
		if (message.contains("> 20")) {
			broadband.setVdsl_available(true);
			services_available += "VDSL";
		} 
		if (message.contains("Business fibre available") || message.contains("Network capability:<\\/h4><ul><li>UFB fibre up to 100 Mbps")) {
			broadband.setUfb_available(true);
			services_available += "UFB";
		} 
		broadband.setScheduled(message.substring(message.lastIndexOf(",") + 1));
		broadband.setServices_available(services_available);
		customer.setAddress(address);
		
		customer.setServiceAvailable(false);
		
		System.out.println("customer.getSelect_plan_type(): " + customer.getSelect_plan_type());
		
		if (broadband.isAdsl_available()) { //"ADSL".equals(customer.getSelect_plan_type()) && 
			customer.setServiceAvailable(true);
		}
		if (broadband.isVdsl_available()) { // "VDSL".equals(customer.getSelect_plan_type()) && 
			customer.setServiceAvailable(true);
		}
		if (broadband.isUfb_available()) { // "UFB".equals(customer.getSelect_plan_type()) && 
			customer.setServiceAvailable(true);
		}
		if ("0".equals(customer.getSelect_plan_type())) {
			if (broadband.isAdsl_available() || broadband.isVdsl_available() || broadband.isUfb_available()) {
				customer.setServiceAvailable(true);
			}
		}
		
		return broadband;
	}
	
	@RequestMapping(value = "/do/service")
	public void doServiceAvailable(HttpSession session) {

		Customer customer = (Customer) session.getAttribute("customer");
		
		if (customer != null) {
			customer.setServiceAvailable(true);
			System.out.println("do service...");
		}

	}

}
