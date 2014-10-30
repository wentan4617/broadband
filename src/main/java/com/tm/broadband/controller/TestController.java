package com.tm.broadband.controller;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.broadband.model.Customer;
import com.tm.broadband.paymentexpress.GenerateRequest;
import com.tm.broadband.paymentexpress.PayConfig;
import com.tm.broadband.paymentexpress.PxPay;
import com.tm.broadband.paymentexpress.Response;
import com.tm.broadband.service.TestService;


@Controller
public class TestController {
	
	private TestService testService;
	
	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}

	@RequestMapping(value = "/test/dps")
	public String testDPS() {
		return "test/test-dps";
	}
	
	@RequestMapping(value = "/test/dps/checkout", method = RequestMethod.POST)
	public String orderingFormCheckout(HttpServletRequest req) {
		
		GenerateRequest gr = new GenerateRequest();
		
		gr.setAmountInput("1.00");
		gr.setCurrencyInput("NZD");
		gr.setTxnType("Purchase");
		
		System.out.println("/test/dps/checkout: " + req.getRequestURL().toString());
		gr.setUrlFail(req.getRequestURL().toString());
		gr.setUrlSuccess(req.getRequestURL().toString());

		String redirectUrl = PxPay.GenerateRequest(PayConfig.PxPayUserId, PayConfig.PxPayKey, gr, PayConfig.PxPayUrl);

		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/test/dps/checkout")
	public String toOrderingFormSuccess(
			@RequestParam(value = "result", required = true) String result) throws Exception {
		
		System.out.println("result: " + result);

		Response responseBean  = PxPay.ProcessResponse(PayConfig.PxPayUserId, PayConfig.PxPayKey, result, PayConfig.PxPayUrl);
		
		return "redirect:/test/dps";
	}
	
	
	@RequestMapping(value = "/test/customer/to/customerorder")
	public String customertocustomerorder(){
		
		this.testService.moveCustomerToCustomerOrder();
		return null;
	}
}
