package com.tm.broadband.pdf;

import java.util.Calendar;
import java.util.Locale;

import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.Notification;
import com.tm.broadband.util.TMUtils;

public class TestAll {

	public static void main(String[] args) {

		/*
		 * Test TMUtil.mailAtValueRetriever() method begin
		 */

		 Notification noti = new Notification();
		 noti.setTitle(" We are kind to notice that your new invoice #@<invoice_id> has just generated, to find out more details please check the attached PDF file in the attachment. Please keep an eye on the invoice’s due date. If you have any questions about the invoice, please don’t hastate to contact us: @<company_telephone> Kind regards, @<company_name>: @<company_domain>");
		 noti.setContent(" We are kind to notice that your new invoice #@<invoice_id> has just generated, to find out more details please check the attached PDF file in the attachment. Please keep an eye on the invoice’s due date. If you have any questions about the invoice, please don’t hastate to contact us: @<company_telephone> Kind regards, @<company_name>: @<company_domain>");
		 CompanyDetail company = new CompanyDetail();
		 company.setTelephone("01212131212");
		 company.setName("Total Mobile Solution");
		 company.setDomain("www.cyberpark.co.nz");
		 Customer cus = new Customer();
		 cus.setLogin_name("Kanny");
		 CustomerInvoice inv = new CustomerInvoice();
		 inv.setId(70004);
		 System.out.println("----------Before Begin----------");
		 System.out.println(noti.getTitle());
		 System.out.println(noti.getContent());
		 System.out.println("----------Before End----------");
		 TMUtils.mailAtValueRetriever(noti, cus, inv, company);
		 System.out.println("----------After Begin----------");
		 System.out.println(noti.getTitle());
		 System.out.println(noti.getContent());
		 System.out.println("----------After End----------");

		/*
		 * Test TMUtil.mailAtValueRetriever() method end
		 */
//		List<RegisterCustomer> registerCustomers = new ArrayList<RegisterCustomer>();
//		TMUtils.thisWeekDateForRegisterStatistic(registerCustomers);
//		// SimpleDateFormat("y年M月d日 E H时m分s秒",Locale.CHINA);
//
//		for (RegisterCustomer registerCustomer : registerCustomers) {
//			System.out.print(registerCustomer.getRegisterDate() + "\t");
//			System.out.println(registerCustomer.getRegisterCount());
//		}
		
//		Integer year = Calendar.YEAR;
//		Integer month = Calendar.MONTH;
//		
//		List<RegisterCustomer> registerCustomers = new ArrayList<RegisterCustomer>();
//		TMUtils.thisMonthDateForRegisterStatistic(year, month, registerCustomers);
//		for (RegisterCustomer registerCustomer : registerCustomers) {
//			System.out.print(registerCustomer.getRegisterMonthDate_str()+"\t");
//			System.out.println(registerCustomer.getRegisterCount());
//		}
		
		Calendar c = Calendar.getInstance(Locale.CHINA);
		System.out.println(c.get(Calendar.YEAR));
		
	}
	

}
