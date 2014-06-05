package com.tm.broadband.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.tm.broadband.mapper.CustomerCallRecordMapper;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerInvoiceDetail;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.Notification;
import com.tm.broadband.model.RegisterCustomer;
import com.tm.broadband.pdf.InvoicePDFCreator;

public class TMUtils {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat date1Format = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat date2Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static SimpleDateFormat date3Format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private static DecimalFormat numberFormat = new DecimalFormat("0.00");
	private static DecimalFormat timeFormat = new DecimalFormat("00.00");
	
	public TMUtils() {
	}
	
	public static String generateRandomString(int range) {
		String str = "";
		String[] pwds = new String[] { "1", "5", "9", "8", "7", "6", "4", "3",
				"2", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a",
				"s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v",
				"b", "n", "m", "0" };
		Random random = new Random();
		int i = 0;
		while (i < range) {
			str += pwds[random.nextInt(36)];
			i++;
		}
		return str;
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
	
	public static Date parseDate2YYYYMMDD(String dateStr) {
		if (dateStr != null && !"".equals(dateStr))
			try {
				return date2Format.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static Date parseDate1YYYYMMDD(String dateStr) {
		if (dateStr != null && !"".equals(dateStr))
			try {
				return date1Format.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static Date parseDate3YYYYMMDD(String dateStr) {
		if (dateStr != null && !"".equals(dateStr))
			try {
				return date3Format.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static void setJSONErrorMap(JSONBean<?> json, BindingResult result) {
		List<FieldError> fields = result.getFieldErrors();
		for (FieldError field: fields) {
			System.out.println(field.getField() + ": " + field.getDefaultMessage());
			json.getErrorMap().put(field.getField(), field.getDefaultMessage());
		}
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
	public static String fillDecimalPeriod(String sum){
		return numberFormat.format(Double.parseDouble(sum));
	}
	
	// e.g.:to the nearest whole number, for example rounding of 8.88 is 8.89 so change it to 8.89 and then fill 0.0 to 0.00,
	// if 8.88 is 8.8 then fill one 0 behind and finally 8.8 become 8.80
	public static BigDecimal fillDecimalPeriod(BigDecimal bigDecimal){
		return new BigDecimal(numberFormat.format(bigDecimal));
	}
	
	// e.g.:to the nearest whole number, for example rounding of 8.88 is 8.89 so change it to 8.89 and then fill 0.0 to 0.00,
	// if 8.88 is 8.8 then fill one 0 behind and finally 8.8 become 8.80
	public static String fillDecimalColon(String sum){
		sum = timeFormat.format(Double.parseDouble(sum));
		return sum.replace(".", ":");
	}
	
	/*
	 * mail at value retriever methods begin
	 */
	public static void mailAtValueRetriever(Notification noti, ContactUs contactUs){
		// title begin
		// retrieve contact us details begin
		if(noti.getTitle() != null){
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_id>", String.valueOf(preventNull(contactUs.getId()))));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_first_name>", preventNull(contactUs.getFirst_name())));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_last_name>", preventNull(contactUs.getLast_name())));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_email>", preventNull(contactUs.getEmail())));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_cellphone>", preventNull(contactUs.getCellphone())));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_phone>", preventNull(contactUs.getPhone())));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_status>", preventNull(contactUs.getStatus())));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_content>", preventNull(contactUs.getContent())));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_submit_date>", preventNull(contactUs.getSubmit_date_str())));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_respond_date>", preventNull(contactUs.getRespond_date_str())));
			noti.setTitle(noti.getTitle().replaceAll("@<contact_us_respond_content>", preventNull(contactUs.getRespond_content())));
		}
		// retrieve contact us details end
		// title end
		// content begin
		// retrieve contact us details begin
		if(noti.getContent() != null){
			noti.setContent(noti.getContent().replaceAll("@<contact_us_id>", String.valueOf(preventNull(contactUs.getId()))));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_first_name>", preventNull(contactUs.getFirst_name())));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_last_name>", preventNull(contactUs.getLast_name())));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_email>", preventNull(contactUs.getEmail())));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_cellphone>", preventNull(contactUs.getCellphone())));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_phone>", preventNull(contactUs.getPhone())));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_status>", preventNull(contactUs.getStatus())));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_content>", preventNull(contactUs.getContent())));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_submit_date>", preventNull(contactUs.getSubmit_date_str())));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_respond_date>", preventNull(contactUs.getRespond_date_str())));
			noti.setContent(noti.getContent().replaceAll("@<contact_us_respond_content>", preventNull(contactUs.getRespond_content())));
		}
		// retrieve contact us details end
		// content end
	}
	
	/*
	 * mail at value retriever methods begin
	 */
	public static void mailAtValueRetriever(Notification noti, Customer cus){
		// title begin
		// retrieve customer details begin
		if(noti.getTitle() != null){
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
		}
		// retrieve customer details end
		// title end
		// content begin
		// retrieve customer details begin
		if(noti.getContent() != null){
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
		}
		// retrieve customer details end
		// content end
	}
	public static void mailAtValueRetriever(Notification noti, CompanyDetail company){
		// title begin
		// retrieve company details begin
		if(noti.getTitle() != null){
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
			noti.setTitle(noti.getTitle().replaceAll("@<tc_business_retails>", preventNull(company.getTc_business_retails())));
			noti.setTitle(noti.getTitle().replaceAll("@<tc_business_wifi>", preventNull(company.getTc_business_wifi())));
			noti.setTitle(noti.getTitle().replaceAll("@<tc_personal>", preventNull(company.getTc_personal())));
			noti.setTitle(noti.getTitle().replaceAll("@<tc_ufb>", preventNull(company.getTc_ufb())));
		}
		// retrieve company details end
		// title end
		
		// content begin
		// retrieve company details begin
		if(noti.getContent() != null){
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
			noti.setContent(noti.getContent().replaceAll("@<tc_business_retails>", preventNull(company.getTc_business_retails())));
			noti.setContent(noti.getContent().replaceAll("@<tc_business_wifi>", preventNull(company.getTc_business_wifi())));
			noti.setContent(noti.getContent().replaceAll("@<tc_personal>", preventNull(company.getTc_personal())));
			noti.setContent(noti.getContent().replaceAll("@<tc_ufb>", preventNull(company.getTc_ufb())));
		}
		// retrieve company details end
		// content end
	}
	public static void mailAtValueRetriever(Notification noti, CustomerInvoice inv){
		// title begin
		// retrieve invoice details begin
		if(noti.getTitle() != null){
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
		}
		// retrieve invoice details end
		// title end
		
		// content begin
		// retrieve invoice details begin
		if(noti.getContent() != null){
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
		}
		// retrieve invoice details end
		// content end
	}
	
	public static void mailAtValueRetriever(Notification noti, CustomerOrder order){
		// title begin
		// retrieve order begin
		if(noti.getTitle() != null){
			noti.setTitle(noti.getTitle().replaceAll("@<order_id>", String.valueOf(preventNull(order.getId()))));
			noti.setTitle(noti.getTitle().replaceAll("@<order_due_date_str>", String.valueOf(preventNull(order.getOrder_due_str()))));
			noti.setTitle(noti.getTitle().replaceAll("@<order_rfs_date_str>", String.valueOf(preventNull(order.getRfs_date_str()))));
		}
		// retrieve order end
		// title end
		
		// content begin
		// retrieve order begin
		if(noti.getContent() != null){
			noti.setContent(noti.getContent().replaceAll("@<order_id>", String.valueOf(preventNull(order.getId()))));
			noti.setContent(noti.getContent().replaceAll("@<order_due_date_str>", String.valueOf(preventNull(order.getOrder_due_str()))));
			noti.setContent(noti.getContent().replaceAll("@<order_rfs_date_str>", String.valueOf(preventNull(order.getRfs_date_str()))));
		}
		// retrieve order end
		// content end
	}
	
	public static void mailAtValueRetriever(Notification noti, List<CustomerOrderDetail> cods){
		
		boolean firstTitlePSTN = true;
		boolean firstTitleHardware = true;
		boolean firstContentPSTN = true;
		boolean firstContentHardware = true;
		
		for (CustomerOrderDetail cod : cods) {
			// title begin
			// retrieve order begin
			if(noti.getTitle() != null){
				if("pstn".equals(cod.getDetail_type()) || "voip".equals(cod.getDetail_type())){
					if(firstTitlePSTN){
						noti.setTitle(noti.getTitle().replaceAll("@<order_detail_number>", String.valueOf(", Your Number: "+preventNull(cod.getPstn_number())+"@<order_detail_number>")));
						firstTitlePSTN = false;
					} else {
						noti.setTitle(noti.getTitle().replaceAll("@<order_detail_number>", String.valueOf("; "+preventNull(cod.getPstn_number())+"@<order_detail_number>")));
					}
				}
				if("hardware-router".equals(cod.getDetail_type())){
					if(firstTitleHardware){
						noti.setTitle(noti.getTitle().replaceAll("@<order_detail_name>", String.valueOf(", Your Modem: "+preventNull(cod.getDetail_name())+"@<order_detail_name>")));
						firstTitleHardware = false;
					} else {
						noti.setTitle(noti.getTitle().replaceAll("@<order_detail_name>", String.valueOf("; "+preventNull(cod.getDetail_name())+"@<order_detail_name>")));
					}
				}
			}
			// retrieve order end
			// title end
			
			// content begin
			// retrieve order begin
			if(noti.getContent() != null){
				if("pstn".equals(cod.getDetail_type()) || "voip".equals(cod.getDetail_type())){
					if(firstContentPSTN){
						noti.setContent(noti.getContent().replaceAll("@<order_detail_number>", String.valueOf(", Your Number: "+preventNull(cod.getPstn_number())+"@<order_detail_number>")));
						firstContentPSTN = false;
					} else {
						noti.setContent(noti.getContent().replaceAll("@<order_detail_number>", String.valueOf("; "+preventNull(cod.getPstn_number())+"@<order_detail_number>")));
					}
				}
				if("hardware-router".equals(cod.getDetail_type())){
					if(firstContentHardware){
						noti.setContent(noti.getContent().replaceAll("@<order_detail_name>", String.valueOf(", Your Modem: "+preventNull(cod.getDetail_name())+"@<order_detail_name>")));
						firstContentHardware = false;
					} else {
						noti.setContent(noti.getContent().replaceAll("@<order_detail_name>", String.valueOf("; "+preventNull(cod.getDetail_name())+"@<order_detail_name>")));
					}
				}
			}
			// retrieve order end
			// content end
		}
		noti.setTitle(noti.getTitle().replaceAll("; @<order_detail_number>", ""));
		noti.setTitle(noti.getTitle().replaceAll("; @<order_detail_name>", ""));
		noti.setContent(noti.getContent().replaceAll("; @<order_detail_number>", ""));
		noti.setContent(noti.getContent().replaceAll("; @<order_detail_name>", ""));
		noti.setTitle(noti.getTitle().replaceAll("@<order_detail_number>", ""));
		noti.setTitle(noti.getTitle().replaceAll("@<order_detail_name>", ""));
		noti.setContent(noti.getContent().replaceAll("@<order_detail_number>", ""));
		noti.setContent(noti.getContent().replaceAll("@<order_detail_name>", ""));
	}
	
	public static void mailAtValueRetriever(Notification noti, ContactUs contactUs, CompanyDetail company){
		if(contactUs!=null){
			mailAtValueRetriever(noti,contactUs);
		}
		if(company!=null){
			mailAtValueRetriever(noti,company);
		}
	}
	
	public static void mailAtValueRetriever(Notification noti, Customer cus, CompanyDetail company){
		if(cus!=null){
			mailAtValueRetriever(noti,cus);
		}
		if(company!=null){
			mailAtValueRetriever(noti,company);
		}
	}
	
	public static void mailAtValueRetriever(Notification noti, Customer cus, CustomerOrder order, CompanyDetail company){
		if(cus!=null){
			mailAtValueRetriever(noti,cus);
		}
		if(company!=null){
			mailAtValueRetriever(noti,company);
		}
		if(order!=null){
			mailAtValueRetriever(noti,order);
		}
	}
	
	public static void mailAtValueRetriever(Notification noti, Customer cus, CustomerInvoice inv, CompanyDetail company){
		if(cus!=null){
			mailAtValueRetriever(noti,cus);
		}
		if(inv!=null){
			mailAtValueRetriever(noti,inv);
		}
		if(company!=null){
			mailAtValueRetriever(noti,company);
		}
	}
	
	public static void mailAtValueRetriever(Notification noti, Customer cus, CustomerOrder order, List<CustomerOrderDetail> cods, CompanyDetail company){
		if(cus!=null){
			mailAtValueRetriever(noti,cus);
		}
		if(company!=null){
			mailAtValueRetriever(noti,company);
		}
		if(order!=null){
			mailAtValueRetriever(noti,order);
		}
		if(cods.size()>0){
			mailAtValueRetriever(noti,cods);
		}
	}
	
	public static void mailAtValueRetriever(Notification noti, Customer cus, CustomerOrder order, CustomerInvoice inv, CompanyDetail company){
		if(cus!=null){
			mailAtValueRetriever(noti,cus);
		}
		if(order!=null){
			mailAtValueRetriever(noti,order);
		}
		if(inv!=null){
			mailAtValueRetriever(noti,inv);
		}
		if(company!=null){
			mailAtValueRetriever(noti,company);
		}
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

	/**
	 * Methods from Calendar BEGIN
	 * @param registerCustomers
	 */
	public static void thisWeekDateForRegisterStatistic(List<RegisterCustomer> registerCustomers) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY); // Monday as first day
		
		RegisterCustomer statistic = new RegisterCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Mon. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = new RegisterCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Tues. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = new RegisterCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Wed. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = new RegisterCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Thur. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = new RegisterCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Fri. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = new RegisterCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Sat. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);

		statistic = new RegisterCustomer();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		statistic.setRegisterDate(cal.getTime());
		statistic.setRegisterWeekDate_str("Sun. "+TMUtils.dateFormatYYYYMMDD(statistic.getRegisterDate()));
		statistic.setRegisterCount(0);
		registerCustomers.add(statistic);
	}
	
	public static void thisMonthDateForRegisterStatistic(int year, int month, List<RegisterCustomer> registerCustomers) {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.set(Calendar.DAY_OF_MONTH, 1); // set date
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		RegisterCustomer statistic = null;
		for (int i = 0; i < maxDay; i++) {
			statistic = new RegisterCustomer();
			c.set(Calendar.DAY_OF_MONTH, i+1);
			statistic.setRegisterDate(c.getTime());
			statistic.setRegisterCount(0);
			registerCustomers.add(statistic);
		}
	}
	/**
	 * Methods from Calendar END
	 */
	
	// Remove all non-numeric characters, if 0 is first char then remove it
	public static String formatPhoneNumber(String str){
		if(str!=null && !"".equals(str)){
			String pattern = "\\D+";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
			if(str.indexOf("0") == 0){
				str = str.substring(1, str.length());
			}
		}
		return str;
	}
	
	public static void printResultErrors(BindingResult result) {
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError error : errors)
				System.out.println(error.getObjectName() + ", " + error.getDefaultMessage());
		}
	}
	
	// BEGIN CustomerCallRecord OPERATION
	public static BigDecimal ccrOperation(String pstn_number, List<CustomerInvoiceDetail> cids
			, InvoicePDFCreator invoicePDF, BigDecimal bigPayableAmount
			, CustomerCallRecordMapper customerCallRecordMapper){
		
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.set(Calendar.MONDAY, cal.get(Calendar.MONDAY)-1);
		
		
		CustomerCallRecord ccrTemp = new CustomerCallRecord();
		ccrTemp.getParams().put("where", "query_last_call_records");
		ccrTemp.getParams().put("orderby", "ORDER BY charge_date_time ASC");
		ccrTemp.getParams().put("last_month", cal.getTime());
		ccrTemp.getParams().put("record_type", "T1");
		// PRODUCTION MODE CHANGE TO pstn_number
		ccrTemp.getParams().put("clear_service_id", //96272424
				TMUtils.formatPhoneNumber(pstn_number)
		);
		List<CustomerCallRecord> ccrsTemp = customerCallRecordMapper.selectCustomerCallRecord(ccrTemp);
		List<CustomerCallRecord> ccrs = new ArrayList<CustomerCallRecord>();

		BigDecimal bigTotalAmountIncl = new BigDecimal(0d);
		// ITERATIVELY ADD ALL CALL CHARGES
		for (CustomerCallRecord ccr : ccrsTemp) {
			

			// BEGIN Rate Operation
			boolean is0900 = false; boolean isFax = false;
			boolean isMobile = ccr.getBilling_description().toUpperCase().contains("MOBILE")
					|| ccr.getBilling_description().toUpperCase().contains("OTH NETWRK");
			boolean isInternational = false; boolean isNational = false; boolean isBusinessLocal = false;

			Double costPerMinute = 1d;
			if(is0900){} if(isFax){}
			if(isMobile){ costPerMinute = 0.19d ; }
			if(isInternational){} if(isNational){} if(isBusinessLocal){}
			
			if(is0900 || isFax || isMobile || isInternational || isNational || isBusinessLocal){
				
				// COST PER MINUTE
				BigDecimal bigCostPerMinute = new BigDecimal(costPerMinute);
				
				// DURATION/SECONDS
				BigDecimal bigDuration = new BigDecimal(ccr.getDuration());
				
				// 60 SECONDS/MINUTE
				BigDecimal bigDuration60 = new BigDecimal(60d);
				
				// FINAL DURATION/TOTAL MINUTE
				BigDecimal bigFinalDuration = new BigDecimal(bigDuration.divide(bigDuration60, 2, BigDecimal.ROUND_DOWN).doubleValue());
				ccr.setAmount_incl(bigFinalDuration.multiply(bigCostPerMinute).doubleValue());
				bigTotalAmountIncl = bigTotalAmountIncl.add(bigFinalDuration.multiply(bigCostPerMinute));
			} else {
				BigDecimal bigOriginalCost = new BigDecimal(ccr.getAmount_incl());
				bigTotalAmountIncl = bigTotalAmountIncl.add(bigOriginalCost);
			}
			
			// END Rate Operation
			
			
			// FORMAT DURATION(second) TO TIME STYLE
			ccr.setFormated_duration(TMUtils.fillDecimalColon(String.valueOf((double)ccr.getDuration() / 60)));
			
			// ADD FINAL CCR
			ccrs.add(ccr);
		}
		
		bigTotalAmountIncl = fillDecimalPeriod(bigTotalAmountIncl);
		
		CustomerInvoiceDetail cid = new CustomerInvoiceDetail();
		cid.setInvoice_detail_name("Call Charge : ( " + pstn_number + " )");
		cid.setInvoice_detail_price(bigTotalAmountIncl.doubleValue());
		cid.setInvoice_detail_unit(1);
		
		cids.add(cid);
		
		invoicePDF.setCcrs(ccrs);
		
		// ADD TOTAL CALL FEE INTO INVOICE
		return bigPayableAmount.add(bigTotalAmountIncl);
	}
	// END CustomerCallRecord OPERATION
	
	

}
