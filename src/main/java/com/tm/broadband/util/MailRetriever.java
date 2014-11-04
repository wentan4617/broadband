package com.tm.broadband.util;

import java.util.List;

import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Notification;

public class MailRetriever {
	
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
			noti.setTitle(noti.getTitle().replaceAll("@<customer_md5_password>", preventNull(TMUtils.generateRandomString(3)+cus.getMd5_password()+TMUtils.generateRandomString(3))));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_user_name>", preventNull(cus.getUser_name())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_first_name>", preventNull(cus.getFirst_name())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_last_name>", preventNull(cus.getLast_name())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_address>", preventNull(cus.getAddress())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_email>", preventNull(cus.getEmail())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_phone>", preventNull(cus.getPhone())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_cellphone>", preventNull(cus.getCellphone())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_status>", preventNull(cus.getStatus())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_invoice_post>", preventNull(cus.getInvoice_post())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_register_date>", preventNull(cus.getRegister_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(cus.getRegister_date()) : cus.getRegister_date_str())));
			noti.setTitle(noti.getTitle().replaceAll("@<customer_active_date>", preventNull(cus.getActive_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(cus.getActive_date()) : cus.getActive_date_str())));
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
			noti.setContent(noti.getContent().replaceAll("@<customer_md5_password>", preventNull(TMUtils.generateRandomString(3)+cus.getMd5_password()+TMUtils.generateRandomString(3))));
			noti.setContent(noti.getContent().replaceAll("@<customer_user_name>", preventNull(cus.getUser_name())));
			noti.setContent(noti.getContent().replaceAll("@<customer_first_name>", preventNull(cus.getFirst_name())));
			noti.setContent(noti.getContent().replaceAll("@<customer_last_name>", preventNull(cus.getLast_name())));
			noti.setContent(noti.getContent().replaceAll("@<customer_address>", preventNull(cus.getAddress())));
			noti.setContent(noti.getContent().replaceAll("@<customer_email>", preventNull(cus.getEmail())));
			noti.setContent(noti.getContent().replaceAll("@<customer_phone>", preventNull(cus.getPhone())));
			noti.setContent(noti.getContent().replaceAll("@<customer_cellphone>", preventNull(cus.getCellphone())));
			noti.setContent(noti.getContent().replaceAll("@<customer_status>", preventNull(cus.getStatus())));
			noti.setContent(noti.getContent().replaceAll("@<customer_invoice_post>", preventNull(cus.getInvoice_post())));
			noti.setContent(noti.getContent().replaceAll("@<customer_register_date>", preventNull(cus.getRegister_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(cus.getRegister_date()) : cus.getRegister_date_str())));
			noti.setContent(noti.getContent().replaceAll("@<customer_active_date>", preventNull(cus.getActive_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(cus.getActive_date()) : cus.getActive_date_str())));
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
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_create_date_str>", preventNull(inv.getCreate_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(inv.getCreate_date()) : inv.getCreate_date_str())));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_due_date_str>", preventNull(inv.getDue_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(inv.getDue_date()) : inv.getDue_date_str())));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_suspend_date_str>", preventNull(inv.getSuspend_date_str())));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_disconnected_date_str>", preventNull(inv.getDisconnected_date_str())));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_amount_payable>", TMUtils.fillDecimalPeriod(String.valueOf(preventNull(inv.getAmount_payable())))));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_final_amount_payable>", TMUtils.fillDecimalPeriod(String.valueOf(preventNull(inv.getFinal_payable_amount())))));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_amount_paid>", TMUtils.fillDecimalPeriod(String.valueOf(preventNull(inv.getAmount_paid())))));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_total_credit_back>", TMUtils.fillDecimalPeriod(String.valueOf(TMUtils.bigSub(preventNull(inv.getAmount_payable()), preventNull(inv.getFinal_payable_amount()))))));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_balance>", TMUtils.fillDecimalPeriod(String.valueOf(preventNull(inv.getBalance())))));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_status>", preventNull(inv.getStatus())));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_memo>", preventNull(inv.getMemo())));
			noti.setTitle(noti.getTitle().replaceAll("@<invoice_paid_date>", preventNull(inv.getPaid_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(inv.getPaid_date()) : inv.getPaid_date_str())));
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
			noti.setContent(noti.getContent().replaceAll("@<invoice_create_date_str>", preventNull(inv.getCreate_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(inv.getCreate_date()) : inv.getCreate_date_str())));
			noti.setContent(noti.getContent().replaceAll("@<invoice_due_date_str>", preventNull(inv.getDue_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(inv.getDue_date()) : inv.getDue_date_str())));
			noti.setContent(noti.getContent().replaceAll("@<invoice_suspend_date_str>", preventNull(inv.getSuspend_date_str())));
			noti.setContent(noti.getContent().replaceAll("@<invoice_disconnected_date_str>", preventNull(inv.getDisconnected_date_str())));
			noti.setContent(noti.getContent().replaceAll("@<invoice_amount_payable>", TMUtils.fillDecimalPeriod(String.valueOf(preventNull(inv.getAmount_payable())))));
			noti.setContent(noti.getContent().replaceAll("@<invoice_final_amount_payable>", TMUtils.fillDecimalPeriod(String.valueOf(preventNull(inv.getFinal_payable_amount())))));
			noti.setContent(noti.getContent().replaceAll("@<invoice_amount_paid>", TMUtils.fillDecimalPeriod(String.valueOf(preventNull(inv.getAmount_paid())))));
			noti.setContent(noti.getContent().replaceAll("@<invoice_total_credit_back>", TMUtils.fillDecimalPeriod(String.valueOf(TMUtils.bigSub(preventNull(inv.getAmount_payable()), preventNull(inv.getFinal_payable_amount()))))));
			noti.setContent(noti.getContent().replaceAll("@<invoice_balance>", TMUtils.fillDecimalPeriod(String.valueOf(preventNull(inv.getBalance())))));
			noti.setContent(noti.getContent().replaceAll("@<invoice_status>", preventNull(inv.getStatus())));
			noti.setContent(noti.getContent().replaceAll("@<invoice_memo>", preventNull(inv.getMemo())));
			noti.setContent(noti.getContent().replaceAll("@<invoice_paid_date>", preventNull(inv.getPaid_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(inv.getPaid_date()) : inv.getPaid_date_str())));
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
			noti.setTitle(noti.getTitle().replaceAll("@<order_due_date_str>", String.valueOf(preventNull(order.getOrder_due()!=null ? TMUtils.retrieveMonthAbbrWithDate(order.getOrder_due()) : order.getOrder_due_str()))));
			noti.setTitle(noti.getTitle().replaceAll("@<order_in_service_date_str>", String.valueOf(preventNull(order.getOrder_using_start()!=null ? TMUtils.retrieveMonthAbbrWithDate(order.getOrder_using_start()) : order.getOrder_using_start_str()))));
			noti.setTitle(noti.getTitle().replaceAll("@<order_rfs_date_str>", String.valueOf(preventNull(order.getRfs_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(order.getRfs_date()) : order.getRfs_date_str()))));
			noti.setTitle(noti.getTitle().replaceAll("@<order_total_price>", String.valueOf(TMUtils.fillDecimalPeriod(preventNull(order.getOrder_total_price())))));
			if(order.getCustomer_type().equals("personal")){
				noti.setTitle(noti.getTitle().replaceAll("@<order_first_name>", String.valueOf(preventNull(order.getFirst_name()))));
				noti.setTitle(noti.getTitle().replaceAll("@<order_last_name>", String.valueOf(preventNull(order.getLast_name()))));
			} else {
				noti.setTitle(noti.getTitle().replaceAll("@<order_first_name>", String.valueOf(preventNull(order.getOrg_name()))));
				noti.setTitle(noti.getTitle().replaceAll("@<order_last_name>", String.valueOf("")));
			}
		}
		// retrieve order end
		// title end
		
		// content begin
		// retrieve order begin
		if(noti.getContent() != null){
			noti.setContent(noti.getContent().replaceAll("@<order_id>", String.valueOf(preventNull(order.getId()))));
			noti.setContent(noti.getContent().replaceAll("@<order_due_date_str>", String.valueOf(preventNull(order.getOrder_due()!=null ? TMUtils.retrieveMonthAbbrWithDate(order.getOrder_due()) : order.getOrder_due_str()))));
			noti.setContent(noti.getContent().replaceAll("@<order_in_service_date_str>", String.valueOf(preventNull(order.getOrder_using_start()!=null ? TMUtils.retrieveMonthAbbrWithDate(order.getOrder_using_start()) : order.getOrder_using_start_str()))));
			noti.setContent(noti.getContent().replaceAll("@<order_rfs_date_str>", String.valueOf(preventNull(order.getRfs_date()!=null ? TMUtils.retrieveMonthAbbrWithDate(order.getRfs_date()) : order.getRfs_date_str()))));
			noti.setContent(noti.getContent().replaceAll("@<order_total_price>", String.valueOf(TMUtils.fillDecimalPeriod(preventNull(order.getOrder_total_price())))));
			if(order.getCustomer_type().equals("personal")){
				noti.setContent(noti.getContent().replaceAll("@<order_first_name>", String.valueOf(preventNull(order.getFirst_name()))));
				noti.setContent(noti.getContent().replaceAll("@<order_last_name>", String.valueOf(preventNull(order.getLast_name()))));
			} else {
				noti.setContent(noti.getContent().replaceAll("@<order_first_name>", String.valueOf(preventNull(order.getOrg_name()))));
				noti.setContent(noti.getContent().replaceAll("@<order_last_name>", String.valueOf("")));
			}
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
					if(cod.getPstn_number()!=null && !"".equals(cod.getPstn_number().trim())){
						if(firstTitlePSTN){
							noti.setTitle(noti.getTitle().replaceAll("@<order_detail_number>", String.valueOf("Phone Number: "+preventNull(cod.getPstn_number())+"@<order_detail_number>")));
							firstTitlePSTN = false;
						} else {
							noti.setTitle(noti.getTitle().replaceAll("@<order_detail_number>", String.valueOf("; "+preventNull(cod.getPstn_number())+"@<order_detail_number>")));
						}
					}
				}
				if("hardware-router".equals(cod.getDetail_type())){
					if(firstTitleHardware){
						noti.setTitle(noti.getTitle().replaceAll("@<order_detail_name>", String.valueOf("Modem: "+preventNull(cod.getDetail_name())+"@<order_detail_name>")));
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
				if(cod.getPstn_number()!=null && !"".equals(cod.getPstn_number().trim())){
					if("pstn".equals(cod.getDetail_type()) || "voip".equals(cod.getDetail_type())){
						if(firstContentPSTN){
							noti.setContent(noti.getContent().replaceAll("@<order_detail_number>", String.valueOf("Phone Number: "+preventNull(cod.getPstn_number())+"@<order_detail_number>")));
							firstContentPSTN = false;
						} else {
							noti.setContent(noti.getContent().replaceAll("@<order_detail_number>", String.valueOf("; "+preventNull(cod.getPstn_number())+"@<order_detail_number>")));
						}
					}
				}
				if("hardware-router".equals(cod.getDetail_type())){
					if(firstContentHardware){
						noti.setContent(noti.getContent().replaceAll("@<order_detail_name>", String.valueOf("Modem Details: "+preventNull(cod.getDetail_name())+"@<order_detail_name>")));
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
	
	public static void mailAtValueRetriever(Notification noti, CustomerOrder co, CompanyDetail company){
		if(co!=null){
			mailAtValueRetriever(noti,co);
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
		if(order!=null){
			mailAtValueRetriever(noti,order);
		}
		if(company!=null){
			mailAtValueRetriever(noti,company);
		}
	}
	
	public static void mailAtValueRetriever(Notification noti, CustomerOrder co, CustomerInvoice inv, CompanyDetail company){
		if(co!=null){
			mailAtValueRetriever(noti,co);
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
			return TMUtils.useDollarSymbolInReplace(property);
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
