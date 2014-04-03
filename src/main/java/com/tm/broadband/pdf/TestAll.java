package com.tm.broadband.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.service.SmserService;
import com.tm.broadband.util.TMUtils;

public class TestAll {
	

	@Autowired
	private static SmserService smserService;

	public static void main(String[] args) throws DocumentException, IOException {

		/*
		 * Test TMUtil.mailAtValueRetriever() method begin
		 */

//		 Notification noti = new Notification();
//		 noti.setTitle(" We are kind to notice that your new invoice #@<invoice_id> has just generated, to find out more details please check the attached PDF file in the attachment. Please keep an eye on the invoice’s due date. If you have any questions about the invoice, please don’t hastate to contact us: @<company_telephone> Kind regards, @<company_name>: @<company_domain>");
//		 noti.setContent(" We are kind to notice that your new invoice #@<invoice_id> has just generated, to find out more details please check the attached PDF file in the attachment. Please keep an eye on the invoice’s due date. If you have any questions about the invoice, please don’t hastate to contact us: @<company_telephone> Kind regards, @<company_name>: @<company_domain>");
//		 CompanyDetail company = new CompanyDetail();
//		 company.setTelephone("01212131212");
//		 company.setName("Total Mobile Solution");
//		 company.setDomain("www.cyberpark.co.nz");
//		 Customer cus = new Customer();
//		 cus.setLogin_name("Kanny");
//		 CustomerInvoice inv = new CustomerInvoice();
//		 inv.setId(70004);
//		 System.out.println("----------Before Begin----------");
//		 System.out.println(noti.getTitle());
//		 System.out.println(noti.getContent());
//		 System.out.println("----------Before End----------");
//		 TMUtils.mailAtValueRetriever(noti, cus, inv, company);
//		 System.out.println("----------After Begin----------");
//		 System.out.println(noti.getTitle());
//		 System.out.println(noti.getContent());
//		 System.out.println("----------After End----------");

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
//		
//		Calendar c = Calendar.getInstance(Locale.CHINA);
//		System.out.println(c.get(Calendar.YEAR));
//		CompanyDetail company = new CompanyDetail();
//		company.setName("Total Mobile Solution");
//		Customer customer = new Customer();
//		customer.setCellphone("02102387392");
//		customer.setFirst_name("Dong");
//		customer.setLast_name("Chen");
//		Notification notification = new Notification();
//		notification.setContent("Dear @<customer_first_name> @<customer_last_name>, This is a reminder that your next payment for @<company_name> Service is coming up. We have attached a copy of your invoice in PDF format and sent it to your email. Thank you for choosing @<company_name>!!");
//		TMUtils.mailAtValueRetriever(notification, customer, company);
//		smserService.sendSMSByAsynchronousMode(customer, notification);
		

//		// month * unit
//		int nextInvoiceMonth = 5;
//		int nextInvoiceDay = 0;
//		Calendar calNextInvoiceDay = Calendar.getInstance();
//		calNextInvoiceDay.setTime(new Date());
//		calNextInvoiceDay.add(Calendar.MONTH, nextInvoiceMonth);
//		calNextInvoiceDay.add(Calendar.DAY_OF_MONTH, nextInvoiceDay);
//		
//		System.out.println(TMUtils.dateFormatYYYYMMDD(calNextInvoiceDay.getTime()));
//		System.out.println(System.getProperty("user.dir")+File.separator);

		Customer c = new Customer();
		CustomerOrder co = new CustomerOrder();
		CustomerOrderDetail cod = new CustomerOrderDetail();
		List<CustomerOrderDetail> cods = new ArrayList<CustomerOrderDetail>();
		
		// CUSTOMER type
		c.setCustomer_type("personal");
		// ORDER Broadband Type
		co.setOrder_broadband_type("transition");
		// Necessary if broadband type is transition
		co.setTransition_provider_name("Telecom");
		co.setTransition_account_holder_name("David Li");
		co.setTransition_account_number("1234 4321 1234 4321");
		co.setTransition_porting_number("9876 6789 9876 6789");
		
		// set customer
		c.setId(60089);
		c.setTitle("Mr");
		c.setLogin_name("steven1989930");
		c.setFirst_name("Dong");
		c.setLast_name("Chen");
		c.setEmail("davidli@gmail.com");
		c.setCellphone("021 1234567");
		c.setPhone("021 1234567");
		c.setAddress("7 Skeates Ave, Mt roskill, Auckland");
		c.setBirth(TMUtils.parseDateYYYYMMDD("1970-04-01"));
		c.setDriver_licence("5a. DM670646     5b. 241");
		c.setPassport("G4041765");
		c.setCountry("New Zealand");

		// set order detail
		// SET PLAN DETAIL
		cod.setDetail_name("ADSL Naked 150 GB Plan");
		cod.setDetail_type("plan-term");
		cod.setDetail_price(89.0d);
		cods.add(cod);
		// SET ADD ON DETAIL
		cod = new CustomerOrderDetail();
		cod.setDetail_name("Broadband New Connection");
		cod.setDetail_type("new-connection");
		cod.setDetail_price(99.0d);
		cod.setDetail_unit(1);
		cods.add(cod);
		
		// set order
		co.setId(60005);
		co.setOrder_create_date(new Date());
		co.setCustomerOrderDetails(cods);
		
		// call OrderPDFCreator
		OrderPDFCreator oPDFCreator = new OrderPDFCreator();
		oPDFCreator.setCustomer(c);
		oPDFCreator.setCustomerOrder(co);
		
		// create order PDF
		oPDFCreator.create();
		
	}
	

}
