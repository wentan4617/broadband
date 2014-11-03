package com.tm.broadband.pdf;

import java.io.IOException;
import java.text.ParseException;
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

	public static void main(String[] args) throws DocumentException, IOException, ParseException {

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

		/**
		 * BEGIN TEST InvoicePDFCreator
		 */
		
//		CompanyDetail cd = new CompanyDetail();
//		Organization org = new Organization();
//		Customer c = new Customer();
//		CustomerInvoice ci = new CustomerInvoice();
//		CustomerInvoice lci = new CustomerInvoice();
//		CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
//		
//		cd.setName("CyberPark");
//		cd.setAddress("945A New North Rd, Mt Albert, Auckland");
//		cd.setTelephone("02102387392");
//		cd.setFax("0211387649");
//		cd.setDomain("www.cyberpark.co.nz");
//		cd.setGst_registration_number("098 762 778 12");
//		cd.setBank_name("ASB");
//		cd.setBank_account_name("CyberPark");
//		cd.setBank_account_number("3908 8987 6776 5654");
//
////		// set org
//		org.setOrg_name("CyberPark");
//		org.setOrg_type("NZ Incoporated Company");
//		org.setOrg_trading_name("NZ Limited");
//		org.setOrg_register_no("NZ19876542");
//		org.setOrg_incoporate_date(new Date());
//		org.setHolder_name("Steve");
//		org.setHolder_job_title("Manager");
//		org.setHolder_phone("0210210213");
//		org.setHolder_email("Steve@gmail.com");
//		
//		c.setId(600098);
//		c.setFirst_name("DONG");
//		c.setLast_name("CHEN");
//		c.setAddress("7 Skeates Ave, Mt Roskill, Auckland 1041");
//		c.setCustomer_type("business");
//		
////		lci.setAmount_paid(0d);
////		lci.setAmount_payable(89d);
////		lci.setBalance(89d);
////		lci.setCreate_date(new Date());
////		lci.setPaid_date(new Date());
////		lci.setPaid_type("MASTER CARD");
//		
//		ci.setId(700098);
//		ci.setCreate_date(new Date());
//		ci.setAmount_paid(0d);
//		ci.setAmount_payable(89d);
//		ci.setFinal_payable_amount(89d);
//		ci.setBalance(89d);
//		ci.setPaid_date(new Date());
//		ci.setPaid_type("VISA CARD");
//		ci.setDue_date(new Date());
////		ci.setLastCustomerInvoice(lci);
//		
//		List<CustomerInvoiceDetail> cids = new ArrayList<CustomerInvoiceDetail>();
//		cid.setInvoice_detail_name("ADSL Naked 150 GB Plan");
//		cid.setInvoice_detail_price(89.0d);
//		cid.setInvoice_detail_unit(3);
//		cids.add(cid);
//		cid = new CustomerInvoiceDetail();
//		cid.setInvoice_detail_name("discount $30");
//		cid.setInvoice_detail_discount(30d);
//		cid.setInvoice_detail_unit(1);
//		cids.add(cid);
//		ci.setCustomerInvoiceDetails(cids);
//		
//		InvoicePDFCreator ipdfc = new InvoicePDFCreator();
//		
//		List<CustomerCallRecord> ccrs = new ArrayList<CustomerCallRecord>();
//		Double payPerMinute = 0.0133333333333333;
//		
//		int count = 42;
//		while(count >= 0){
//			
//			CustomerCallRecord ccr = new CustomerCallRecord();
//			ccr.setClear_service_id("96272424");
//			ccr.setCharge_date_time(TMUtils.parseDate2YYYYMMDD("2014-04-29 11:18:00"));
//			Double duration = Double.parseDouble(TMUtils.fillDecimalTime(String.valueOf(TMUtils.bigDivide(60d, 60d))));
//			ccr.setFormated_duration(TMUtils.timeFormat.format(Double.parseDouble(String.valueOf(duration))).replace(".", ":"));
//			ccr.setBilling_description("HAMILTON");
//			ccr.setAmount_incl(0.1);
//			ccr.setAmount_incl(60d * payPerMinute);
//			ccr.setPhone_called("7-847 6989");
//			ccrs.add(ccr);
//			
//			count --;
//		}
//		
//		ipdfc.setCompanyDetail(cd);
//		ipdfc.setCurrentCustomerInvoice(ci);
//		ipdfc.setCustomer(c);
//		ipdfc.setOrg(org);
//		ipdfc.setCcrs(ccrs);
//		System.out.println(ipdfc.create().get("filePath"));
		
		/**
		 * END TEST InvoicePDFCreator
		 */
		
		
		/**
		 * BEGIN TEST OrderPDFCreator
		 */
//		Customer c = new Customer();
//		Organization org = new Organization();
//		CustomerOrder co = new CustomerOrder();
//		CustomerOrderDetail cod = new CustomerOrderDetail();
//		List<CustomerOrderDetail> cods = new ArrayList<CustomerOrderDetail>();
//		
//		// CUSTOMER type
//		c.setCustomer_type("business");
//		// ORDER Broadband Type
//		// Necessary if broadband type is transition
//		co.setOrder_broadband_type("transition");
//		co.setTransition_provider_name("Telecom");
//		co.setTransition_account_holder_name("David Li");
//		co.setTransition_account_number("1234 4321 1234 4321");
//		co.setTransition_porting_number("9876 6789 9876 6789");
//		
//		// set customer
//		c.setId(60089);
//		c.setTitle("Mr");
//		c.setLogin_name("steven1989930");
//		c.setFirst_name("Dong");
//		c.setLast_name("Chen");
//		c.setEmail("davidli@gmail.com");
//		c.setCellphone("021 1234567");
//		c.setPhone("021 1234567");
//		c.setAddress("7 Skeates Ave, Mt roskill, Auckland");
//		c.setBirth(TMUtils.parseDateYYYYMMDD("1970-04-01"));
//		c.setDriver_licence("5a. DM670646     5b. 241");
//		c.setPassport("G4041765");
//		c.setCountry("New Zealand");
//		
//		// set org
//		org.setOrg_name("CyberPark");
//		org.setOrg_type("NZ Incoporated Company");
//		org.setOrg_trading_name("NZ Limited");
//		org.setOrg_register_no("NZ19876542");
//		org.setOrg_incoporate_date(new Date());
//		org.setHolder_name("Steve");
//		org.setHolder_job_title("Manager");
//		org.setHolder_phone("0210210213");
//		org.setHolder_email("Steve@gmail.com");
//
//		// set order detail
//		// SET PLAN DETAIL
//		cod.setDetail_name("ADSL Naked 150 GB Plan");
//		cod.setDetail_type("plan-term");
//		cod.setDetail_price(89.0d);
//		cod.setDetail_data_flow(100L);
//		cod.setDetail_term_period(24);
//		cod.setDetail_unit(3);
//		cods.add(cod);
//		// SET ADD ON DETAIL
//		cod = new CustomerOrderDetail();
//		cod.setDetail_name("Broadband New Connection");
//		cod.setDetail_type("new-connection");
//		cod.setDetail_price(99.0d);
//		cod.setDetail_unit(1);
//		cods.add(cod);
//		cod = new CustomerOrderDetail();
//		cod.setDetail_name("TP - LINK 150Mbps Wireless N ADSL2+ Modem Router");
//		cod.setDetail_type("hardware-router");
//		cod.setDetail_price(49.0d);
//		cod.setDetail_unit(2);
//		cods.add(cod);
//		// SET DISCOUNT ON DETAIL
//		cod = new CustomerOrderDetail();
//		cod.setDetail_name("Plan Discount");
//		cod.setDetail_desc("3% off the total price of plan");
//		cod.setDetail_type("discount");
//		cod.setDetail_price(16.008d);
//		cod.setDetail_unit(1);
//		cods.add(cod);
//		
//		// set order
//		co.setId(60005);
//		co.setOrder_create_date(new Date());
//		co.setCustomerOrderDetails(cods);
//		
//		// call OrderPDFCreator
//		OrderingPDFCreator oPDFCreator = new OrderingPDFCreator();
//		oPDFCreator.setCustomer(c);
//		oPDFCreator.setOrg(org);
//		oPDFCreator.setCustomerOrder(co);
//		
//		// create order PDF
//		System.out.println(oPDFCreator.create());
		/**
		 * END TEST OrderPDFCreator
		 */

		/**
		 * BEGIN TEST CreditPDFCreator
		 */
//		Customer c = new Customer();
//		c.setCustomer_type("personal");
//		CustomerCredit cc = new CustomerCredit();
//		cc.setCard_type("MASTERCARD");
//		CustomerOrder co = new CustomerOrder();
//		Organization org = new Organization();
//		org.setOrg_name("CyberPark");
//		
//		// set customer
//		c.setId(60089);
//		c.setFirst_name("Dong");
//		c.setLast_name("Chen");
//		c.setAddress("7 Skeates Ave, Mt roskill, Auckland 1041");
//		c.setCellphone("021 1234567");
//		c.setPhone("021 1234567");
//		c.setEmail("davidli@gmail.com");
//		
//		co.setId(60005);
//		
//		// set customer credit
//		cc.setHolder_name("COOK");
//		cc.setCard_number("9999 8888 7777 6666");
//		cc.setSecurity_code("214");
//		cc.setExpiry_date("2016-09-08");
//		
//		// call OrderPDFCreator
//		CreditPDFCreator cPDFCreator = new CreditPDFCreator();
//		cc.setCustomer(c);
//		cPDFCreator.setCc(cc);
//		cPDFCreator.setCo(co);
//		cPDFCreator.setOrg(org);
//		
//		// create order PDF
//		cPDFCreator.create();
		/**
		 * END TEST CreditPDFCreator
		 */
		
//		// BEGIN CREDIT CARD MONTH
//		List<String> yearArrs = new ArrayList<String>();
//		for (int i = 0; i <= 99; i++) {
//			if(i<10){
//				yearArrs.add(String.format("%02d",i));
//			} else {
//				yearArrs.add(String.format("%d",i));
//			}
//		}
//		// END CREDIT CARD MONTH
//
//		// BEGIN CREDIT CARD YEAR
//		List<String> monthArrs = new ArrayList<String>();
//		for (int i = 1; i <= 12; i++) {
//			if(i<10){
//				monthArrs.add(String.format("%02d",i));
//			} else {
//				monthArrs.add(String.format("%d",i));
//			}
//		}
//		// END CREDIT CARD YEAR
//		
//		System.out.println("20"+"08"+"-"+"01");
//
//	}

//		EarlyTerminationChargePDFCreator etcPDFCreator = new EarlyTerminationChargePDFCreator();
		
//		CompanyDetail cd = new CompanyDetail();
//		Customer c = new Customer();
//		Organization org = new Organization();
//		CustomerOrder co = new CustomerOrder();
////		EarlyTerminationCharge etc = new EarlyTerminationCharge();
//		
//		cd.setName("CyberPark Limited");
//		cd.setBilling_address("PO Box 41547, St Lukes, Auckland 1346, New Zealand");
//		cd.setTelephone("0800 2 CYBER (29237)");
//		cd.setDomain("www.cyberpark.co.nz");
//		cd.setGst_registration_number("113-460-148");
//		cd.setBank_name("ANZ");
//		cd.setBank_account_name("CyberPark Limited");
//		cd.setBank_account_number("06-0709-0444426-00");
//		
//		c.setId(600000);
//		c.setCustomer_type("personal");
//		c.setTitle("Mr");
//		c.setFirst_name("DONG");
//		c.setLast_name("CHEN");
//		c.setAddress("898 New North Road, Mount Albert, Auckland 1025");
//		
//		org.setOrg_name("Triniti of Silver NZ Ltd");
//		
//		co.setId(700000);
//		co.setOrder_using_start(TMUtils.parseDateYYYYMMDD("2014-01-10"));
		
//		etc.setId(200000);
//		etc.setCustomer_id(c.getId());
//		etc.setOrder_id(co.getId());
//		etc.setCreate_date(new Date());
//		etc.setService_given_date(co.getOrder_using_start());
//		etc.setTermination_date(TMUtils.parseDateYYYYMMDD("2014-07-11"));
//		Map<String, Object> map = TMUtils.earlyTerminationDatesCalculation(etc.getService_given_date(), etc.getTermination_date());
//		System.out.println(map.get("charge_amount"));
//		System.out.println(map.get("legal_termination_date"));
//		etc.setLegal_termination_date((Date) map.get("legal_termination_date"));
//		etc.setDue_date(TMUtils.getInvoiceDueDate(new Date(), 15));
//		etc.setCharge_amount((Double) map.get("charge_amount"));
//		etc.setMonths_between_begin_end((Integer) map.get("months_between_begin_end"));
//		etc.setExecute_by(13);
//		etc.setTotal_payable_amount(etc.getCharge_amount() + 0d);

//		etcPDFCreator.setCustomer(c);
//		etcPDFCreator.setEtc(etc);
//		etcPDFCreator.setCompanyDetail(cd);
//		etcPDFCreator.setOrg(org);
//		
//		
//		System.out.println(etcPDFCreator.create());
//		System.out.println("Due Date: " + etc.getDue_date_str());
//		System.out.println("Legal Termination Date: " + etc.getLegal_termination_date_str());
//		System.out.println("Charge Amount: " + etc.getCharge_amount());
//		System.out.println("Months Between Begin End: " + etc.getMonths_between_begin_end());
		
		
		
//		Map<String, Object> map = TMUtils.terminationRefundCalculations(TMUtils.parseDateYYYYMMDD("2014-06-10"), 109d);
//		
//		TerminationRefund tr = new TerminationRefund();
//		tr.setId(300000);
//		tr.setCreate_date(new Date());
//		tr.setTermination_date(TMUtils.parseDateYYYYMMDD("2014-06-10"));
//		tr.setProduct_name("ADSL + PSTN - Office Advisor $109 + GST, 150GB");
//		tr.setProduct_monthly_price(109d);
//		tr.setRefund_amount((Double) map.get("refund_amount"));
//		tr.setRefund_bank_account_number("05-12345-09876-009");
//		tr.setRefund_bank_account_name("COOK");
//		tr.setDays_between_end_last((Integer) map.get("remaining_days"));
//		
//		User u = new User();
//		u.setId(10);
//		u.setUser_role("accountant");
//		
//		TerminationRefundPDFCreator trPDFCreator = new TerminationRefundPDFCreator();
//		trPDFCreator.setCd(cd);
//		trPDFCreator.setEtr(tr);
//		trPDFCreator.setOrg(org);
//		trPDFCreator.setC(c);
//		trPDFCreator.setU(u);
////		System.out.println(trPDFCreator.create());

		
		/**
		 * BEGIN TEST ReceiptPDFCreator
		 */
		Customer c = new Customer();
		CustomerOrder co = new CustomerOrder();
		CustomerOrderDetail cod = new CustomerOrderDetail();
		List<CustomerOrderDetail> cods = new ArrayList<CustomerOrderDetail>();
		
		// CUSTOMER type
		co.setCustomer_type("business");
		// ORDER Broadband Type
		// Necessary if broadband type is transition
		co.setOrder_broadband_type("transition");
		co.setTransition_provider_name("Telecom");
		co.setTransition_account_holder_name("David Li");
		co.setTransition_account_number("1234 4321 1234 4321");
		co.setTransition_porting_number("9876 6789 9876 6789");
		
		// set customer
		c.setId(600089);
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
		
		// set org
		co.setOrg_name("CyberPark");
		co.setOrg_type("NZ Incoporated Company");
		co.setOrg_trading_name("NZ Limited");
		co.setOrg_register_no("NZ19876542");
		co.setOrg_incoporate_date(new Date());
		co.setHolder_name("Steve");
		co.setHolder_job_title("Manager");
		co.setHolder_phone("0210210213");
		co.setHolder_email("Steve@gmail.com");

		// set order detail
		// SET PLAN DETAIL
		cod.setDetail_name("ADSL Naked 150 GB Plan");
		cod.setDetail_type("plan-term");
		cod.setDetail_price(89.0d);
		cod.setDetail_data_flow(100L);
		cod.setDetail_term_period(24);
		cod.setDetail_unit(3);
		cods.add(cod);
		// SET ADD ON DETAIL
//		cod = new CustomerOrderDetail();
//		cod.setDetail_name("Broadband New Connection");
//		cod.setDetail_type("new-connection");
//		cod.setDetail_price(99.0d);
//		cod.setDetail_unit(1);
//		cods.add(cod);
		cod = new CustomerOrderDetail();
		cod.setDetail_name("TP - LINK 150Mbps Wireless N ADSL2+ Modem Router");
		cod.setDetail_type("hardware-router");
		cod.setDetail_price(49.0d);
		cod.setDetail_unit(2);
		cods.add(cod);
		// SET DISCOUNT ON DETAIL
		cod = new CustomerOrderDetail();
		cod.setDetail_name("Plan Discount");
		cod.setDetail_desc("3% off the total price of plan");
		cod.setDetail_type("discount");
		cod.setDetail_price(16.008d);
		cod.setDetail_unit(1);
		cods.add(cod);
		
		// set order
		co.setId(60005);
		co.setOrder_create_date(new Date());
		co.setCustomerOrderDetails(cods);
		
		// call OrderPDFCreator
		ReceiptPDFCreator oPDFCreator = new ReceiptPDFCreator();
//		oPDFCreator.setCo(co);
		
		// create order PDF
		System.out.println(oPDFCreator.create());
		/**
		 * END TEST ReceiptPDFCreator
		 */
		
	}

}
