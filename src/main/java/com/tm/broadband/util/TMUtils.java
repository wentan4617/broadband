package com.tm.broadband.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.Notification;

public class TMUtils {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat date1Format = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat date2Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public TMUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getCustomerOrderSerial(String login_name) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
	
		String serial = "TM" 
				+ login_name.toUpperCase() 
				+ String.valueOf(cal.get(Calendar.YEAR))
				+ String.valueOf(cal.get(Calendar.MONTH) + 1)
				+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
				+ String.valueOf(cal.get(Calendar.HOUR_OF_DAY))
				+ String.valueOf(cal.get(Calendar.MINUTE));
		
		return serial;
	}
	
	public static String dateFormatYYYYMMDDHHMMSS(Date date) {
		if (date != null) 
			return date2Format.format(date);
		return "";
	}
	
	public static String dateFormatYYYYMMDD(Date date) {
		if (date != null) 
			return dateFormat.format(date);
		return "";
	}
	
	public static Date parseDateYYYYMMDD(String dateStr) {
		if (dateStr != null && !"".equals(dateStr))
			try {
				return dateFormat.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static String createPath(String path){
		String temp = System.getenv("SystemRoot");
		String realPath = temp.substring(0, temp.indexOf("\\")+1) + path;
		
        File file = new File(realPath);
        if(!file.getParentFile().exists()){
        	file.getParentFile().mkdirs();
        }
        
        return realPath;
	}
	
	// e.g.:to the nearest whole number, for example rounding of 8.88 is 8.89 so change it to 8.89 and then fill 0.0 to 0.00,
	// if 8.88 is 8.8 then fill one 0 behind and finally 8.8 become 8.80
	public static String fillDecimal(String sum){
		sum = String.valueOf(Math.round((double)Double.valueOf(sum)*100)/100.00);
		return sum.length()-sum.indexOf(".")-1 < 2 ? sum += "0" : sum;
	}
	
	/*
	 * mail at value retriever methods begin
	 */
	public static void mailAtValueRetriever(Notification noti, Customer cus){
		// title begin
		// retrieve customer details begin
		noti.setTitle(noti.getTitle().replaceAll("@<customer_id>", String.valueOf(preventNull(cus.getId()))));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_login_name>", preventNull(cus.getLogin_name())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_password>", preventNull(cus.getPassword())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_user_name>", preventNull(cus.getUser_name())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_first_name>", preventNull(cus.getFirst_name())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_last_name>", preventNull(cus.getLast_name())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_address>", preventNull(cus.getAddress())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_email>", preventNull(cus.getEmail())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_phone>", preventNull(cus.getPhone())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_cellphone>", preventNull(cus.getCellphone())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_status>", preventNull(cus.getStatus())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_invoice_post>", preventNull(cus.getInvoice_post())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_register_date>", preventNull(cus.getRegister_date_str())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_active_date>", preventNull(cus.getActive_date_str())));
		noti.setTitle(noti.getTitle().replaceAll("@<customer_balance>", String.valueOf(preventNull(cus.getBalance()))));
		// retrieve customer details end
		// title end
		// content begin
		// retrieve customer details begin
		noti.setContent(noti.getContent().replaceAll("@<customer_id>", String.valueOf(preventNull(cus.getId()))));
		noti.setContent(noti.getContent().replaceAll("@<customer_login_name>", preventNull(cus.getLogin_name())));
		noti.setContent(noti.getContent().replaceAll("@<customer_password>", preventNull(cus.getPassword())));
		noti.setContent(noti.getContent().replaceAll("@<customer_user_name>", preventNull(cus.getUser_name())));
		noti.setContent(noti.getContent().replaceAll("@<customer_first_name>", preventNull(cus.getFirst_name())));
		noti.setContent(noti.getContent().replaceAll("@<customer_last_name>", preventNull(cus.getLast_name())));
		noti.setContent(noti.getContent().replaceAll("@<customer_address>", preventNull(cus.getAddress())));
		noti.setContent(noti.getContent().replaceAll("@<customer_email>", preventNull(cus.getEmail())));
		noti.setContent(noti.getContent().replaceAll("@<customer_phone>", preventNull(cus.getPhone())));
		noti.setContent(noti.getContent().replaceAll("@<customer_cellphone>", preventNull(cus.getCellphone())));
		noti.setContent(noti.getContent().replaceAll("@<customer_status>", preventNull(cus.getStatus())));
		noti.setContent(noti.getContent().replaceAll("@<customer_invoice_post>", preventNull(cus.getInvoice_post())));
		noti.setContent(noti.getContent().replaceAll("@<customer_register_date>", preventNull(cus.getRegister_date_str())));
		noti.setContent(noti.getContent().replaceAll("@<customer_active_date>", preventNull(cus.getActive_date_str())));
		noti.setContent(noti.getContent().replaceAll("@<customer_balance>", String.valueOf(preventNull(cus.getBalance()))));
		// retrieve customer details end
		// content end
	}
	public static void mailAtValueRetriever(Notification noti, CompanyDetail company){
		// title begin
		// retrieve company details begin
		noti.setTitle(noti.getTitle().replaceAll("@<company_name>", preventNull(company.getName())));
		noti.setTitle(noti.getTitle().replaceAll("@<company_address>", preventNull(company.getAddress())));
		noti.setTitle(noti.getTitle().replaceAll("@<company_telephone>", preventNull(company.getTelephone())));
		noti.setTitle(noti.getTitle().replaceAll("@<company_fax>", preventNull(company.getFax())));
		noti.setTitle(noti.getTitle().replaceAll("@<company_domain>", preventNull(company.getDomain())));
		noti.setTitle(noti.getTitle().replaceAll("@<company_gst_registration_number>", preventNull(company.getGst_registration_number())));
		noti.setTitle(noti.getTitle().replaceAll("@<company_bank_name>", preventNull(company.getBank_name())));
		noti.setTitle(noti.getTitle().replaceAll("@<company_bank_account_name>", preventNull(company.getBank_account_name())));
		noti.setTitle(noti.getTitle().replaceAll("@<company_bank_account_number>", preventNull(company.getBank_account_number())));
		noti.setTitle(noti.getTitle().replaceAll("@<company_company_email>", preventNull(company.getCompany_email())));
		// retrieve company details end
		// title end
		
		// content begin
		// retrieve company details begin
		noti.setContent(noti.getContent().replaceAll("@<company_name>", preventNull(company.getName())));
		noti.setContent(noti.getContent().replaceAll("@<company_address>", preventNull(company.getAddress())));
		noti.setContent(noti.getContent().replaceAll("@<company_telephone>", preventNull(company.getTelephone())));
		noti.setContent(noti.getContent().replaceAll("@<company_fax>", preventNull(company.getFax())));
		noti.setContent(noti.getContent().replaceAll("@<company_domain>", preventNull(company.getDomain())));
		noti.setContent(noti.getContent().replaceAll("@<company_gst_registration_number>", preventNull(company.getGst_registration_number())));
		noti.setContent(noti.getContent().replaceAll("@<company_bank_name>", preventNull(company.getBank_name())));
		noti.setContent(noti.getContent().replaceAll("@<company_bank_account_name>", preventNull(company.getBank_account_name())));
		noti.setContent(noti.getContent().replaceAll("@<company_bank_account_number>", preventNull(company.getBank_account_number())));
		noti.setContent(noti.getContent().replaceAll("@<company_company_email>", preventNull(company.getCompany_email())));
		// retrieve company details end
		// content end
	}
	public static void mailAtValueRetriever(Notification noti, CustomerInvoice inv){
		// title begin
		// retrieve invoice details begin
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_id>", String.valueOf(preventNull(inv.getId()))));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_invoice_serial>", preventNull(inv.getInvoice_serial())));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_customer_id>", String.valueOf(preventNull(inv.getCustomer_id()))));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_create_date_str>", preventNull(inv.getCreate_date_str())));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_due_date_str>", preventNull(inv.getDue_date_str())));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_amount_payable>", String.valueOf(preventNull(inv.getAmount_payable()))));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_amount_paid>", String.valueOf(preventNull(inv.getAmount_paid()))));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_balance>", String.valueOf(preventNull(inv.getBalance()))));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_status>", preventNull(inv.getStatus())));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_memo>", preventNull(inv.getMemo())));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_paid_date>", preventNull(inv.getPaid_date_str())));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_paid_type>", preventNull(inv.getPaid_type())));
		noti.setTitle(noti.getTitle().replaceAll("@<invoice_last_invoice_id>", String.valueOf(preventNull(inv.getLast_invoice_id()))));
		// retrieve invoice details end
		// title end
		
		// content begin
		// retrieve invoice details begin
		noti.setContent(noti.getContent().replaceAll("@<invoice_id>", String.valueOf(preventNull(inv.getId()))));
		noti.setContent(noti.getContent().replaceAll("@<invoice_invoice_serial>", preventNull(inv.getInvoice_serial())));
		noti.setContent(noti.getContent().replaceAll("@<invoice_customer_id>", String.valueOf(preventNull(inv.getCustomer_id()))));
		noti.setContent(noti.getContent().replaceAll("@<invoice_create_date_str>", preventNull(inv.getCreate_date_str())));
		noti.setContent(noti.getContent().replaceAll("@<invoice_due_date_str>", preventNull(inv.getDue_date_str())));
		noti.setContent(noti.getContent().replaceAll("@<invoice_amount_payable>", String.valueOf(preventNull(inv.getAmount_payable()))));
		noti.setContent(noti.getContent().replaceAll("@<invoice_amount_paid>", String.valueOf(preventNull(inv.getAmount_paid()))));
		noti.setContent(noti.getContent().replaceAll("@<invoice_balance>", String.valueOf(preventNull(inv.getBalance()))));
		noti.setContent(noti.getContent().replaceAll("@<invoice_status>", preventNull(inv.getStatus())));
		noti.setContent(noti.getContent().replaceAll("@<invoice_memo>", preventNull(inv.getMemo())));
		noti.setContent(noti.getContent().replaceAll("@<invoice_paid_date>", preventNull(inv.getPaid_date_str())));
		noti.setContent(noti.getContent().replaceAll("@<invoice_paid_type>", preventNull(inv.getPaid_type())));
		noti.setContent(noti.getContent().replaceAll("@<invoice_last_invoice_id>", String.valueOf(preventNull(inv.getLast_invoice_id()))));
		// retrieve invoice details end
		// content end
	}
	
	public static void mailAtValueRetriever(Notification noti, Customer cus, CustomerInvoice inv, CompanyDetail company){
		mailAtValueRetriever(noti,cus);
		mailAtValueRetriever(noti,inv);
		mailAtValueRetriever(noti,company);
	}
	
	public static String preventNull(String property){
		if(property!=null){
			return property;
		}
		return "";
	}
	
	public static Double preventNull(Double property){
		if(property!=null){
			return property;
		}
		return 0.0;
	}

	public static Integer preventNull(Integer property){
		if(property!=null){
			return property;
		}
		return 0;
	}

	/*
	 * mail at value retriever methods begin
	 */

}
