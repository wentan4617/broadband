package com.tm.broadband.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.Broadband;
import com.tm.broadband.model.Customer;
import com.tm.broadband.util.broadband.BroadbandCapability;

@RestController
public class BroadbandRestController {

	public BroadbandRestController() {
	}
	
	@RequestMapping("/plans/address/check/{address}")
	public Broadband plansCheckAddress(HttpSession session
			, @PathVariable("address") String address) {
		
		System.out.println("customerReg check address");
		Customer customerReg = (Customer) session.getAttribute("customerReg");
		if (customerReg == null) {
			System.out.println("this is new customerReg, 1");
			customerReg = new Customer();
			customerReg.setNewOrder(false);
			customerReg.setSelect_customer_type("personal");
			customerReg.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
			customerReg.getCustomerOrder().setPromotion(false);
			session.setAttribute("customerReg", customerReg);
			return returnBroadband(address, customerReg);
		} else {
			System.out.println("this is old customerReg, new order: " + customerReg.getNewOrder());
			return returnBroadband(address, customerReg);
		}
	}
	
	@RequestMapping("/sale/plans/address/check/{address}")
	public Broadband checkAddressSale(HttpSession session
			, @PathVariable("address") String address) {
		
		System.out.println("customerRegSale check address");
		Customer customerRegSale = (Customer) session.getAttribute("customerRegSale");
		if (customerRegSale == null) {
			System.out.println("this is new customerRegSale, 1");
			customerRegSale = new Customer();
			customerRegSale.setNewOrder(false);
			customerRegSale.setSelect_customer_type("personal");
			customerRegSale.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
			customerRegSale.getCustomerOrder().setPromotion(false);
			session.setAttribute("customerRegSale", customerRegSale);
			return returnBroadband(address, customerRegSale);
		} else {
			System.out.println("this is old customerReg, new order: " + customerRegSale.getNewOrder());
			return returnBroadband(address, customerRegSale);
		}
	}
	
	@RequestMapping("/crm/plans/address/check/{address}")
	public Broadband checkAddressAdmin(HttpSession session
			, @PathVariable("address") String address) {
		
		System.out.println("customerRegAdmin check address");
		Customer customerRegAdmin = (Customer) session.getAttribute("customerRegAdmin");
		if (customerRegAdmin == null) {
			System.out.println("this is new customerRegAdmin, 1");
			customerRegAdmin = new Customer();
			customerRegAdmin.setNewOrder(false);
			customerRegAdmin.setSelect_customer_type("personal");
			customerRegAdmin.getCustomerOrder().setOrder_broadband_type("transition");//new-connection
			customerRegAdmin.getCustomerOrder().setPromotion(false);
			session.setAttribute("customerRegAdmin", customerRegAdmin);
			return returnBroadband(address, customerRegAdmin);
		} else {
			System.out.println("this is old customerReg, new order: " + customerRegAdmin.getNewOrder());
			return returnBroadband(address, customerRegAdmin);
		}
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
		
		if ("plan-topup".equals(customer.getSelect_plan_group())) {
			broadband.setVdsl_available(false);
			broadband.setUfb_available(false);
		}
		
		return broadband;
	}

}
