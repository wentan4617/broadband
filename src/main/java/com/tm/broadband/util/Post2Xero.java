package com.tm.broadband.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rossjourdain.util.xero.ArrayOfInvoice;
import com.rossjourdain.util.xero.ArrayOfLineItem;
import com.rossjourdain.util.xero.ArrayOfPhone;
import com.rossjourdain.util.xero.Contact;
import com.rossjourdain.util.xero.Invoice;
import com.rossjourdain.util.xero.InvoiceStatus;
import com.rossjourdain.util.xero.InvoiceType;
import com.rossjourdain.util.xero.LineItem;
import com.rossjourdain.util.xero.Phone;
import com.rossjourdain.util.xero.PhoneTypeCodeType;
import com.rossjourdain.util.xero.XeroClient;
import com.rossjourdain.util.xero.XeroClientException;
import com.rossjourdain.util.xero.XeroClientProperties;
import com.rossjourdain.util.xero.XeroClientUnexpectedException;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;

public class Post2Xero {
	
	public static void postSingleInvoice(HttpServletRequest request
			, Customer c, CustomerOrder co, CustomerInvoice ci, String description){
		
		String name = c.getFirst_name()+" "+c.getLast_name()+"-"+ci.getOrder_id();

        // Prepare the Xero Client
        XeroClient xeroClient = null;
        try {
            XeroClientProperties clientProperties = new XeroClientProperties();
            String propertyFile = TMUtils.createPath("broadband" + File.separator + "properties" + File.separator + "xeroApi.properties");
            clientProperties.load(new FileInputStream(propertyFile));
            xeroClient = new XeroClient(clientProperties);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Create an Invoice
        ArrayOfInvoice arrayOfInvoice = new ArrayOfInvoice();
        List<Invoice> invoices = arrayOfInvoice.getInvoice();
        Invoice invoice = new Invoice();
		
		ArrayOfPhone arrayOfPhone = new ArrayOfPhone();
		List<Phone> phones = arrayOfPhone.getPhone();
		
		Phone cellPhone = new Phone();
		Phone homePhone = new Phone();
		
		cellPhone.setPhoneNumber(c.getCellphone());
		cellPhone.setPhoneType(PhoneTypeCodeType.MOBILE);
		homePhone.setPhoneNumber(c.getPhone());
		homePhone.setPhoneType(PhoneTypeCodeType.DEFAULT);
		
		phones.add(cellPhone);
		phones.add(homePhone);

        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmailAddress(c.getEmail());
        contact.setPhones(arrayOfPhone);
        invoice.setContact(contact);
        
        ArrayOfLineItem arrayOfLineItem = new ArrayOfLineItem();
        List<LineItem> lineItems = arrayOfLineItem.getLineItem();
		
        LineItem lineItem = new LineItem();
        lineItem.setAccountCode("201");
        lineItem.setQuantity(new BigDecimal(1));
        lineItem.setUnitAmount(new BigDecimal(ci.getFinal_payable_amount()));
        lineItem.setDescription(description);
        lineItem.setLineAmount(new BigDecimal(ci.getFinal_payable_amount()));
        lineItems.add(lineItem);
		
        invoice.setLineItems(arrayOfLineItem);
        Calendar create = Calendar.getInstance();
        Calendar due = Calendar.getInstance();
        create.setTime(ci.getCreate_date());
        invoice.setDate(create);
        due.setTime(ci.getDue_date());
        invoice.setDueDate(due);
        invoice.setInvoiceNumber(String.valueOf(ci.getId()));
        invoice.setType(InvoiceType.ACCREC);
        if(co.getXero_invoice_status()==null || "AUTHORISED".equals(co.getXero_invoice_status())){
            invoice.setStatus(InvoiceStatus.AUTHORISED);
        } else {
            invoice.setStatus(InvoiceStatus.DRAFT);
        }
        invoice.getLineAmountTypes().add("Inclusive");
        invoice.setReference(String.valueOf(ci.getOrder_id()));
        invoices.add(invoice);
        
        try {
			
			if(co.getIs_send_xero_invoice()==null || co.getIs_send_xero_invoice()){
				xeroClient.postInvoices(arrayOfInvoice);
			}
			
		} catch (XeroClientException | XeroClientUnexpectedException e) {
			e.printStackTrace();
		}
        
	}
	
	public static void postMultiInvoices(List<Map<String, Object>> resultMaps, String description){

        // Prepare the Xero Client
        XeroClient xeroClient = null;
        try {
            XeroClientProperties clientProperties = new XeroClientProperties();
            String propertyFile = TMUtils.createPath("broadband" + File.separator + "properties" + File.separator + "xeroApi.properties");
            clientProperties.load(new FileInputStream(propertyFile));
            xeroClient = new XeroClient(clientProperties);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Create an Invoice
        ArrayOfInvoice arrayOfInvoice = new ArrayOfInvoice();
        List<Invoice> invoices = arrayOfInvoice.getInvoice();
        
        Boolean is_send_2_xero = false;
        
        for (Map<String, Object> resultMap : resultMaps) {
        	
			Customer c = (Customer) resultMap.get("customer");
			CustomerInvoice ci = (CustomerInvoice) resultMap.get("customerInvoice");
			CustomerOrder co = (CustomerOrder) resultMap.get("customerOrder");
			
			if(co.getIs_send_xero_invoice()!=null && !co.getIs_send_xero_invoice()){
				continue;
			}
			
			is_send_2_xero = true;
			
			System.out.println();
			System.out.println("c: "+c);
			System.out.println("c.getId(): "+c.getId());
			System.out.println("ci: "+ci);
			System.out.println("ci.getId(): "+ci.getId());
			System.out.println("co: "+co);
			System.out.println("co.getId(): "+co.getId());
			System.out.println();
			
			if(ci.getFinal_payable_amount()<=0){
				continue;
			}

			String name = c.getFirst_name()+" "+c.getLast_name()+"-"+ci.getOrder_id();
	        Invoice invoice = new Invoice();
			
			ArrayOfPhone arrayOfPhone = new ArrayOfPhone();
			List<Phone> phones = arrayOfPhone.getPhone();
			
			Phone cellPhone = new Phone();
			Phone homePhone = new Phone();
			
			cellPhone.setPhoneNumber(c.getCellphone());
			cellPhone.setPhoneType(PhoneTypeCodeType.MOBILE);
			homePhone.setPhoneNumber(c.getPhone());
			homePhone.setPhoneType(PhoneTypeCodeType.DEFAULT);
			
			phones.add(cellPhone);
			phones.add(homePhone);

	        Contact contact = new Contact();
	        contact.setName(name);
	        contact.setEmailAddress(c.getEmail());
	        contact.setPhones(arrayOfPhone);
	        invoice.setContact(contact);
	        
	        ArrayOfLineItem arrayOfLineItem = new ArrayOfLineItem();
	        List<LineItem> lineItems = arrayOfLineItem.getLineItem();
			
	        LineItem lineItem = new LineItem();
	        lineItem.setAccountCode("201");
	        lineItem.setQuantity(new BigDecimal(1));
	        lineItem.setUnitAmount(new BigDecimal(ci.getFinal_payable_amount()));
	        lineItem.setDescription(description);
	        lineItem.setLineAmount(new BigDecimal(ci.getFinal_payable_amount()));
	        lineItems.add(lineItem);
			
	        invoice.setLineItems(arrayOfLineItem);
	        Calendar create = Calendar.getInstance();
	        Calendar due = Calendar.getInstance();
	        create.setTime(ci.getCreate_date());
	        invoice.setDate(create);
	        due.setTime(ci.getDue_date());
	        invoice.setDueDate(due);
	        invoice.setInvoiceNumber(String.valueOf(ci.getId()));
	        invoice.setType(InvoiceType.ACCREC);
	        if(co.getXero_invoice_status()==null || "AUTHORISED".equals(co.getXero_invoice_status())){
		        invoice.setStatus(InvoiceStatus.AUTHORISED);
	        } else {
		        invoice.setStatus(InvoiceStatus.DRAFT);
	        }
	        invoice.getLineAmountTypes().add("Inclusive");
	        invoice.setReference(String.valueOf(ci.getOrder_id()));
	        invoices.add(invoice);
			
		}
        
        try {
        	
        	if(is_send_2_xero){
    			xeroClient.postInvoices(arrayOfInvoice);
        	}
        	
		} catch (XeroClientException | XeroClientUnexpectedException e) {
			e.printStackTrace();
		}
        
	}

}
