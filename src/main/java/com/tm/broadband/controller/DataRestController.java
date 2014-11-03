package com.tm.broadband.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.DateUsage;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.NetworkUsage;
import com.tm.broadband.model.Page;
import com.tm.broadband.service.DataService;
import com.tm.broadband.util.TMUtils;

@RestController
public class DataRestController {
	
	private DataService dataService;

	@Autowired
	public DataRestController(DataService dataService) {
		this.dataService = dataService;
	}
	
	@RequestMapping("/broadband-user/data/calculator-usage/{calculator_date}")
	public JSONBean<NetworkUsage> calculatorUsage(
			@PathVariable("calculator_date") String calculator_date){
		
		String[] date = calculator_date.split("-");
		int year = Integer.parseInt(date[0]);
		int month = Integer.parseInt(date[1]);
		
		JSONBean<NetworkUsage> json = new JSONBean<NetworkUsage>();
		
		NetworkUsage usage = new NetworkUsage();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		usage.getParams().put("where", "delete_usage");
		usage.getParams().put("accounting_date", TMUtils.dateFormatYYYYMMDD(cal.getTime()));
		
		dataService.calculatorUsage(usage);
		
		json.getSuccessMap().put("alert-success", "Processed.");
		
		return json;
	}
	
	
	@RequestMapping("/broadband-user/data/orders/view/{pageNo}/{calculator_date}/{status}/{orderid}")
	public Page<CustomerOrder> dataOrdersView(
			@PathVariable("pageNo") int pageNo
			, @PathVariable("calculator_date") String calculator_date
			, @PathVariable("status") String status
			, @PathVariable("orderid") int orderid){
		
		Page<CustomerOrder> page = new Page<CustomerOrder>();
		page.setPageNo(pageNo);
		page.setPageSize(30);
		
		if (!"".equals(status) && !"all".equals(status))
			page.getParams().put("order_status", status);
		
		if (orderid != 0) {
			page.getParams().put("id", orderid);
		}
		//page.getParams().put("orderby", "order by plan_status desc, plan_type");
		
		this.dataService.queryDataCustomersByPage(page);
		
		String[] date = calculator_date.split("-");
		int year = Integer.parseInt(date[0]);
		int month = Integer.parseInt(date[1]);
		
		NetworkUsage usage = new NetworkUsage();
		usage.getParams().put("currentYear", year);
		usage.getParams().put("currentMonth", month);
		
		List<NetworkUsage> usages = this.dataService.queryCurrentMonthUsages(usage);
		
		List<CustomerOrder> cos = page.getResults();
		if (cos != null && usages != null) {
			
			for (CustomerOrder co: cos) {
				String vlan = "", svlan = "", cvlan = "", type = "";
				svlan = co.getSvlan() != null ? co.getSvlan().toLowerCase() : "";
				cvlan = co.getCvlan() != null ? co.getCvlan().toLowerCase() : "";
				type = co.getCod().getDetail_plan_type();
				if (!"".equals(svlan) && !"".equals(cvlan)) {
					if ("ADSL".equals(type)) {
						vlan = "d" + svlan + "/" + String.valueOf(Integer.parseInt(cvlan) + 1600);
					} else if ("VDSL".equals(type)){
						vlan = "d" + svlan + "/" + cvlan;
					} else if ("UFB".equals(type)) {
						vlan = "u" + svlan + "/" + cvlan;
					}
				}
				
				for (NetworkUsage u : usages) {
					//System.out.println(co.getId() + "," + u.getVlan() + "," + vlan);
					if (u.getVlan() != null && !"".equals(vlan) && vlan.equals(u.getVlan().toLowerCase())) {
						co.setUsage(u);
						break;
					}
				}
			}
		}
		
		return page;
		
	}
	
	
	@RequestMapping("/broadband-user/data/orders/usage/view/{svlan}/{cvlan}/{type}/{calculator_date}")
	public List<DateUsage> doCustomerUsageView(
			@PathVariable("svlan") String svlan,
			@PathVariable("cvlan") String cvlan,
			@PathVariable("type") String type,
			@PathVariable("calculator_date") String calculator_date){
		
		Calendar c = Calendar.getInstance();

		String[] date_array = calculator_date.split("-");
		int year = Integer.parseInt(date_array[0]);
		int month = Integer.parseInt(date_array[1]);

		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		
		int days = TMUtils.judgeDay(year, month);

		List<DateUsage> dateUsages = new ArrayList<DateUsage>();
		for (int i = 0; i < days; i++) {
			c.set(Calendar.DAY_OF_MONTH, i + 1);
			Date date = c.getTime();
			
			DateUsage dateUsage = new DateUsage();
			dateUsage.setDate(TMUtils.dateFormatYYYYMMDD(date));
			dateUsages.add(dateUsage);
		}
		
		NetworkUsage u = new NetworkUsage();
		String vlan = "";
		u.getParams().put("where", "query_currentMonth");
		if ("ADSL".equals(type)) {
			vlan = "d" + svlan + "/" + String.valueOf(Integer.parseInt(cvlan) + 1600);
		} else if ("VDSL".equals(type)){
			vlan = "d" + svlan + "/" + cvlan;
		} else if ("UFB".equals(type)) {
			vlan = "u" + svlan + "/" + cvlan;
		}
		u.getParams().put("vlan", vlan);
		u.getParams().put("currentYear", year);
		u.getParams().put("currentMonth", month);
		
		List<NetworkUsage> usages = this.dataService.queryUsages(u);

		if (usages != null && usages.size() > 0) {
			for (NetworkUsage usage: usages) {
				for (DateUsage dateUsage: dateUsages) {
					//System.out.println(TMUtils.dateFormatYYYYMMDD(usage.getAccounting_date()));
					if (dateUsage.getDate().equals(TMUtils.dateFormatYYYYMMDD(usage.getAccounting_date()))) {
						if (dateUsage.getUsage() != null) {
							dateUsage.getUsage().setUpload(dateUsage.getUsage().getUpload() + usage.getUpload());
							dateUsage.getUsage().setDownload(dateUsage.getUsage().getDownload() + usage.getDownload());
						} else {
							dateUsage.setUsage(usage);
						}
						System.out.println(TMUtils.dateFormatYYYYMMDD(usage.getAccounting_date()));
						break;
					}
				}
			}
		}
		
		return dateUsages;
		
	}
	

}
