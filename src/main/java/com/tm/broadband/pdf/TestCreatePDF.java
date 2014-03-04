package com.tm.broadband.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import com.itextpdf.text.DocumentException;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.util.TMUtils;

public class TestCreatePDF {

	public static void main(String[] args) throws DocumentException, MalformedURLException, IOException {
		// INVOICE
		CustomerInvoice customerInvoice = new CustomerInvoice();
		customerInvoice.setInvoice_serial("TMBSSS201402111205");
		customerInvoice.setId(060001);
		
		// CUSTOMER
		Customer customer = new Customer();
		customer.setId(070001);
		customer.setLogin_name("SSSSSSSSSSSSSSSSSSSS");
		customer.setFirst_name("Yi Fang");
		customer.setLast_name("Xiong");
		customer.setAddress("Pt England Road, Point England, Auckland 1072");
		
		// ORDER
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrder_create_date(new Date());
		
		
		// STORE MODEL
		customerInvoice.setCustomer(customer);
		customerInvoice.setCustomerOrder(customerOrder);
//		
//		String filePath = TMUtils.createPath(
//				"broadband"
//				+File.separator+"customers"
//				+File.separator+customer.getId()
//				+File.separator+customerInvoice.getId()+".pdf");
//		new InvoicePDFCreator().create( filePath);
		System.out.println(System.getProperty("user.dir")+File.separator);
	}
}
