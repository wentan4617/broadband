
/*
 *  Copyright 2011 Ross Jourdain
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.rossjourdain.util.xero;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.test.Console;

/**
 *
 * @author ross
 */
public class App {

    public static void main(String[] args) {

        // Prepare the Xero Client
        XeroClient xeroClient = null;
        try {
            XeroClientProperties clientProperties = new XeroClientProperties();
            clientProperties.load(new FileInputStream("./xeroApi.properties"));
            xeroClient = new XeroClient(clientProperties);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Retrieve a list of Invoices
       /* try {

            ArrayOfInvoice arrayOfExistingInvoices = xeroClient.getInvoices();
            if (arrayOfExistingInvoices != null && arrayOfExistingInvoices.getInvoice() != null) {

                System.out.println("");
                for (Invoice invoice : arrayOfExistingInvoices.getInvoice()) {
                    System.out.println("Invoice: " + invoice.getInvoiceID());
                }

                // Retrieve an invoice as a PDF 
                // (can be used to retrieve json too, just change application/pdf to application/json)
                if (!arrayOfExistingInvoices.getInvoice().isEmpty()) {
                    xeroClient.getInvoiceAsPdf(arrayOfExistingInvoices.getInvoice().get(0).getInvoiceID());
                }
            }

        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }*/

        // Create an Invoice
       Invoice invoice = null;

        ArrayOfInvoice arrayOfInvoice = new ArrayOfInvoice();
        List<Invoice> invoices = arrayOfInvoice.getInvoice();
        invoice = new Invoice();

        Contact contact = new Contact();
        contact.setName("steven");
        contact.setEmailAddress("jane@smith.com");
        invoice.setContact(contact);

        // Send Invoice
        ArrayOfLineItem arrayOfLineItem = new ArrayOfLineItem();
        List<LineItem> lineItems = arrayOfLineItem.getLineItem();
        LineItem lineItem = new LineItem();
        lineItem.setAccountCode("200");
        Integer qty = 1;
        lineItem.setQuantity(new BigDecimal(qty));
        Double amnt = 12.00d;
        lineItem.setUnitAmount(new BigDecimal(amnt));
        lineItem.setDescription("Programming books");
        lineItem.setLineAmount(new BigDecimal(TMUtils.bigMultiply(1, 12)));
        lineItems.add(lineItem);
        invoice.setLineItems(arrayOfLineItem);

        invoice.setDate(Calendar.getInstance());
        Calendar due = Calendar.getInstance();
        due.set(due.get(Calendar.YEAR), due.get(Calendar.MONTH) + 1, 20);
        invoice.getLineAmountTypes().add("Inclusive");
        invoice.setDueDate(due);
        invoice.setInvoiceNumber("100073");
        invoice.setType(InvoiceType.ACCREC);
        invoice.setStatus(InvoiceStatus.AUTHORISED);
        invoices.add(invoice);
        
//		ArrayOfInvoice responseInvoices = xeroClient.getInvoicesByInvoiceNumber(invoice.getInvoiceNumber());
//		Invoice i = responseInvoices.getInvoice().get(0);
//		Console.log(i);
      
        try {
			xeroClient.postInvoices(arrayOfInvoice);
		} catch (XeroClientException | XeroClientUnexpectedException e) {
			e.printStackTrace();
		}
    
        // Create a new Contact
       /* try {

            ArrayOfContact arrayOfContact = new ArrayOfContact();
            List<Contact> contacts = arrayOfContact.getContact();


            Contact contact1 = new Contact();
            contact1.setName("John Smith");
            contact1.setEmailAddress("john@smith.com");
            contacts.add(contact1);
            xeroClient.postContacts(arrayOfContact);

        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }

        // Add a payment to an exisiting Invoice
        try {

            Invoice invoice1 = new Invoice();
            invoice1.setInvoiceNumber("INV-0038");

            Account account = new Account();
            account.setCode("090");

            Payment payment = new Payment();
            payment.setAccount(account);
            payment.setInvoice(invoice);
            payment.setAmount(new BigDecimal("20.00"));
            payment.setDate(Calendar.getInstance());

            ArrayOfPayment arrayOfPayment = new ArrayOfPayment();
            List<Payment> payments = arrayOfPayment.getPayment();
            payments.add(payment);

            xeroClient.postPayments(arrayOfPayment);

        } catch (XeroClientException ex) {
            ex.printDetails();
        } catch (XeroClientUnexpectedException ex) {
            ex.printStackTrace();
        }*/
    }
}
