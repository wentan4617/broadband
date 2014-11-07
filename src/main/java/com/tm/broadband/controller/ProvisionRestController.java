package com.tm.broadband.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Provision;
import com.tm.broadband.model.ProvisionLog;
import com.tm.broadband.model.User;
import com.tm.broadband.service.ProvisionService;

@RestController
public class ProvisionRestController {

	private ProvisionService provisionService;
	
	@Autowired
	public ProvisionRestController(ProvisionService provisionService) {
		this.provisionService = provisionService;
	}
	
	@RequestMapping(value = "/broadband-user/provision/query/loading")
	public Provision provisionQueryLoading() {
		Provision provision = this.provisionService.queryOrdersSumAll();
		return provision;
	}
	
	@RequestMapping(value = "/broadband-user/provision/orders/loading/{pageNo}/{order_status}")
	public Page<CustomerOrder> provisionOrdersLoading(
			@PathVariable(value = "pageNo") int pageNo
			, @PathVariable(value = "order_status") String order_status) {

		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		page.getParams().put("where", "query_order_status");
		page.getParams().put("orderby", "order by co.id desc");
		page.getParams().put("status", "active");
		page.getParams().put("order_status", order_status);
		
		this.provisionService.queryCustomerOrdersByPage(page);
		
		return page;
	}
	
	@RequestMapping(value = "/broadband-user/provision/order/{orderid}")
	public CustomerOrder queryCustomerOrderWithCustomer(@PathVariable(value = "orderid") int orderid) {
		CustomerOrder customerOrder = this.provisionService.queryCustomerOrderWithCustomerWithDetails(orderid);
		return customerOrder;
	}
	
	@RequestMapping(value = "/broadband-user/provision/orders/status", method = RequestMethod.POST)
	public JSONBean<String> changeCustomerOrderStatus(HttpSession session
			, @RequestParam(value = "checkbox_orders") String order_ids
			, @RequestParam(value = "process_way") String process_way
			, @RequestParam(value = "change_order_status") String change_order_status) {
		
		JSONBean<String> json = new JSONBean<String>();

		if ("".equals(order_ids)) {
			json.getErrorMap().put("alert-error", "Please choose one order at least.");
			return json;
		}
		
		User userSession = (User) session.getAttribute("userSession");
		
		List<CustomerOrder> list = new ArrayList<CustomerOrder>();
		
		String[] array = order_ids.split(",");
		
		for (String order_id : array) {
			
			CustomerOrder customerOrder = new CustomerOrder();
			customerOrder.setOrder_status(change_order_status);
			customerOrder.getParams().put("id", Integer.parseInt(order_id));
			
			ProvisionLog log = new ProvisionLog();
			log.setUser_id(userSession.getId());
			log.setProcess_datetime(new Date(System.currentTimeMillis()));
			log.setOrder_sort("customer-order");
			log.setOrder_id_customer(Integer.parseInt(order_id));
			log.setProcess_way(process_way);
			
			customerOrder.setTempProvsionLog(log);
			
			list.add(customerOrder);
		}
		
		this.provisionService.changeCustomerOrderStatus(list);
		
		json.getSuccessMap().put("alert-success", array.length + " orders are transformed status (" + process_way + ").");
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/provision/customer/order/detail/set", method = RequestMethod.POST)
	public CustomerOrderDetail orderDetailSet(
			@RequestParam("order_id") int order_id
			, @RequestParam("hardware_post") int hardware_post
			, @RequestParam("detail_id") int detail_id
			, @RequestParam("is_post") int is_post
			, @RequestParam("comment") String comment
			, @RequestParam("trackcode") String trackcode) {
		
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
