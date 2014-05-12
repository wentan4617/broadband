package com.tm.broadband.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerInvoiceDetail;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.itext.ITextFont;
import com.tm.broadband.util.itext.ITextUtils;

/** 
* Generates Invoice PDF
* 
* @author DONG CHEN
*/ 
public class InvoicePDFCreator extends ITextUtils {

	private CompanyDetail companyDetail;
	private CustomerInvoice currentCustomerInvoice;
    private CustomerInvoice lastCustomerInvoice;
    private Customer customer;

	private BaseColor titleBGColor = new BaseColor(92,184,92);
	private BaseColor totleChequeAmountBGColor = new BaseColor(110,110,110);
	
	public InvoicePDFCreator(){}
	
	public InvoicePDFCreator(CompanyDetail companyDetail
			,CustomerInvoice currentCustomerInvoice
			,Customer customer) {
		this.companyDetail = companyDetail;
		this.currentCustomerInvoice = currentCustomerInvoice;
		this.lastCustomerInvoice = currentCustomerInvoice.getLastCustomerInvoice();
		this.customer = customer;
	}
	
	public PdfPTable createCustomerBasicInfo(){
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(102);
		// add common header
        addEmptyCol(headerTable, 180F, 1);
        addCol(headerTable, this.getCustomer().getFirst_name()+" "+this.getCustomer().getLast_name()).font(ITextFont.arial_normal_10).border(0).paddingTo("l", 50F).paddingTo("b", 10F).o();
        addCol(headerTable, this.getCustomer().getAddress()).font(ITextFont.arial_normal_10).border(0).paddingTo("l", 50F).o();
        addEmptyCol(headerTable, 40F, 1);
		return headerTable;
	}

	public String create() throws DocumentException, MalformedURLException, IOException{
        Document document = new Document(PageSize.A4);
        
		// Output PDF Path, e.g.: application_60089.pdf
		String outputFile = TMUtils.createPath("broadband" + File.separator
				+ "customers" + File.separator + this.customer.getId()
				+ File.separator + "invoice_" + this.getCurrentCustomerInvoice().getId()
				+ ".pdf");
		
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();
        
        /*
         *
         *  FIRST PAGE CONTENTS BEGIN
         * 
         */
        document.add(createCustomerBasicInfo());
        
        /*
         * Recent Transactions Table begin
         */
        // non empty table start with 4 columns
        PdfPTable transactionTable = newTable(4, 98F);
        PdfPCell transactionTitleCell = newCell("Recent transactions", ITextFont.arial_bold_white_10, 0);
        transactionTitleCell.setPaddingBottom(4);
        transactionTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        transactionTitleCell.setBackgroundColor(titleBGColor);
        transactionTitleCell.setColspan(4);
        transactionTable.addCell(transactionTitleCell);
        
        // transaction columns
        PdfPCell transactionColumnsCell = newCell("Date", ITextFont.arial_bold_8, 0);
        transactionColumnsCell.setPaddingTop(6);
        transactionColumnsCell.setIndent(10);
        transactionTable.addCell(transactionColumnsCell);
        transactionColumnsCell = newCell("Description", ITextFont.arial_bold_8, 0);
        transactionColumnsCell.setPaddingTop(6);
        transactionColumnsCell.setColspan(2);
        transactionTable.addCell(transactionColumnsCell);
        transactionColumnsCell = newCell("Amount", ITextFont.arial_bold_8, 0);
        transactionColumnsCell.setPaddingTop(6);
        transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        transactionTable.addCell(transactionColumnsCell);

        // account filtering of last invoice begin
        Double lastAmountPayable = this.getLastCustomerInvoice()!=null && this.getLastCustomerInvoice().getAmount_payable()!=null ? this.getLastCustomerInvoice().getAmount_payable() : 0.0;
        Double lastAmountPaid = this.getLastCustomerInvoice()!=null && this.getLastCustomerInvoice().getAmount_paid()!=null ? this.getLastCustomerInvoice().getAmount_paid() : 0.0;
        Double lastBalance = this.getLastCustomerInvoice()!=null && this.getLastCustomerInvoice().getBalance()!=null ? this.getLastCustomerInvoice().getBalance() : 0.0;
//        if(lastAmountPayable==null){
//        	lastAmountPayable = 0.0;
//        }
//        if(lastAmountPaid==null){
//        	lastAmountPaid = 0.0;
//        }
//        if(lastBalance==null){
//        	lastBalance = 0.0;
//        }
        // account filtering of last invoice end
        
        // if last invoice isn't null then go into <if statement>, otherwise only Opening Balance appears
        if(this.getLastCustomerInvoice()!=null){
            // LAST INVOICE'S SITUATION
            transactionColumnsCell = newCell(this.getLastCustomerInvoice().getCreate_date_str(), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setIndent(10);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell("Previous Invoice Total", ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setColspan(2);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell("$ " + TMUtils.fillDecimal(String.valueOf(lastAmountPayable)), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);
            
            // CURRENT INVOICE'S SITUATION
            transactionColumnsCell = newCell(this.getLastCustomerInvoice().getPaid_date_str(), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setIndent(10);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell(this.getLastCustomerInvoice().getPaid_type(), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setColspan(2);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell("$ -" + TMUtils.fillDecimal(String.valueOf(lastAmountPaid)), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);

            // SEPARATOR ROW
            transactionColumnsCell = newCell("________________", ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setColspan(4);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);

            // TOTAL AMOUNT ROW
            transactionColumnsCell = newCell("Opening Balance", ITextFont.arial_bold_10, 0);
            transactionColumnsCell.setColspan(3);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell("$ "+ TMUtils.fillDecimal(String.valueOf(lastBalance)), ITextFont.arial_bold_10, 0);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);
            PdfPCell lastCell = new PdfPCell(new Phrase(" "));
            lastCell.setBorder(0);
            lastCell.setColspan(4);
            transactionTable.addCell(lastCell);
            document.add(transactionTable);
        } else {
            // CURRENT INVOICE'S SITUATION
            transactionColumnsCell = newCell(this.getCurrentCustomerInvoice().getCreate_date_str(), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setIndent(10);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell("Current Invoice Total", ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setColspan(2);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell("$ " + TMUtils.fillDecimal(String.valueOf(this.getCurrentCustomerInvoice().getAmount_payable())), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);
            
            // CURRENT INVOICE'S SITUATION
            transactionColumnsCell = newCell(this.getCurrentCustomerInvoice().getPaid_date_str(), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setIndent(10);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell(this.getCurrentCustomerInvoice().getPaid_type(), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setColspan(2);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell("$ -" + TMUtils.fillDecimal(String.valueOf(this.getCurrentCustomerInvoice().getAmount_paid())), ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);
            
            // SEPARATOR ROW
            transactionColumnsCell = newCell("________________", ITextFont.arial_normal_8, 0);
            transactionColumnsCell.setColspan(4);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);

            // TOTAL AMOUNT ROW
            transactionColumnsCell = newCell("Opening Balance", ITextFont.arial_bold_10, 0);
            transactionColumnsCell.setColspan(3);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);
            transactionColumnsCell = newCell("$ "+ TMUtils.fillDecimal(String.valueOf(this.getCurrentCustomerInvoice().getBalance())), ITextFont.arial_bold_10, 0);
            transactionColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            transactionTable.addCell(transactionColumnsCell);
            PdfPCell lastCell = new PdfPCell(new Phrase(" "));
            lastCell.setBorder(0);
            lastCell.setColspan(4);
            transactionTable.addCell(lastCell);
            document.add(transactionTable);
        }
        
        /*
         * THIS INVOICE SUMMARY TABLE BEGIN
         */
        PdfPTable invoiceSummaryTable = new PdfPTable(4);
        // page's width percentage
        invoiceSummaryTable.setWidthPercentage(98);
        PdfPCell invoiceSummaryTitleCell = newCell("This Invoice Summary", ITextFont.arial_bold_white_10, 0);
        invoiceSummaryTitleCell.setPaddingBottom(4);
        invoiceSummaryTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        invoiceSummaryTitleCell.setBackgroundColor(titleBGColor);
        invoiceSummaryTitleCell.setColspan(4);
        invoiceSummaryTable.addCell(invoiceSummaryTitleCell);
        
        // INVOICE SUMMARY COLUMNS
        PdfPCell invoiceSummaryColumnsCell = newCell("Net charges", ITextFont.arial_normal_8, 0);
        invoiceSummaryColumnsCell.setPaddingTop(10);
        invoiceSummaryColumnsCell.setIndent(10);
        invoiceSummaryColumnsCell.setColspan(3);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        // this invoice detail
        Double currentInvoiceDetailTotalPrice = 0.0;
        List<CustomerInvoiceDetail> listInvoiceDetails = this.getCurrentCustomerInvoice().getCustomerInvoiceDetails();
        Iterator<CustomerInvoiceDetail> iterInvoiceDetails = listInvoiceDetails.iterator();
    	// get invoice detail(s) from invoice
        while(iterInvoiceDetails.hasNext()){
        	CustomerInvoiceDetail customerInvoiceDetail = iterInvoiceDetails.next();
        	// if both empty
        	Double subTotal = 0.0;
        	// if price and unit are not null
        	if(customerInvoiceDetail.getInvoice_detail_price() != null && customerInvoiceDetail.getInvoice_detail_unit() != null){
        		// price * unit
        		subTotal = (customerInvoiceDetail.getInvoice_detail_price()*customerInvoiceDetail.getInvoice_detail_unit());
        	}
        	// if price empty
        	if(customerInvoiceDetail.getInvoice_detail_price() == null && customerInvoiceDetail.getInvoice_detail_unit() != null){
        		// price == null then sub total = 0
        		subTotal = 0.0;
        	}
        	// if unit empty
        	if(customerInvoiceDetail.getInvoice_detail_price() != null && customerInvoiceDetail.getInvoice_detail_unit() == null){
        		// price * 1
        		subTotal = (customerInvoiceDetail.getInvoice_detail_price()*1);
        	}
        	if(customerInvoiceDetail.getInvoice_detail_discount() == null){
            	currentInvoiceDetailTotalPrice+=subTotal;
        	} else {
        		currentInvoiceDetailTotalPrice-=customerInvoiceDetail.getInvoice_detail_discount()*customerInvoiceDetail.getInvoice_detail_unit();
        	}
        }
        // current invoice
        // this invoice amount is consist of (current invoice total amount + last invoice balance - current customer invoice paid fees)
        // preventing subtraction inaccuracy
        BigDecimal bigLastBalance = new BigDecimal(String.valueOf(lastBalance));
        BigDecimal bigAmountPaid = new BigDecimal(String.valueOf(this.getCurrentCustomerInvoice().getAmount_paid()));
        Double thisInvoiceTotalAmount = currentInvoiceDetailTotalPrice + bigLastBalance.subtract(bigAmountPaid).doubleValue();
        Double beforeTaxAmount = 0.0;
        Double taxAmount = 0.0;
        
        if(thisInvoiceTotalAmount > 0){
        	// personal plan include GST
            beforeTaxAmount = thisInvoiceTotalAmount/1.15;
            
            // include GST
            taxAmount = thisInvoiceTotalAmount-thisInvoiceTotalAmount/1.15;
        } else {
        	beforeTaxAmount = thisInvoiceTotalAmount;
        	taxAmount = thisInvoiceTotalAmount;
        }
        
        invoiceSummaryColumnsCell = newCell("$ " + TMUtils.fillDecimal(String.valueOf(beforeTaxAmount)), ITextFont.arial_normal_8, 0);
        invoiceSummaryColumnsCell.setPaddingTop(10);
        invoiceSummaryColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        
        // INVOICE SUMMARY FIRST ROW
        invoiceSummaryColumnsCell = newCell("GST at 15%", ITextFont.arial_normal_8, 0);
        invoiceSummaryColumnsCell.setIndent(10);
        invoiceSummaryColumnsCell.setColspan(3);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        invoiceSummaryColumnsCell = newCell("$ " + TMUtils.fillDecimal(String.valueOf(taxAmount)), ITextFont.arial_normal_8, 0);
        invoiceSummaryColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        
        // INVOICE SUMMARY SECOND ROW
        invoiceSummaryColumnsCell = newCell("Total charges (please see Invoice Details page)", ITextFont.arial_normal_8, 0);
        invoiceSummaryColumnsCell.setIndent(10);
        invoiceSummaryColumnsCell.setColspan(3);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        // include tax fee
        invoiceSummaryColumnsCell = newCell("$ " + TMUtils.fillDecimal(String.valueOf(thisInvoiceTotalAmount)), ITextFont.arial_normal_8, 0);
        invoiceSummaryColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        invoiceSummaryColumnsCell = newCell(" ", ITextFont.arial_normal_8, 0);
        invoiceSummaryColumnsCell.setColspan(4);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        
        // INVOICE SUMMARY SEPARATOR ROW
        invoiceSummaryColumnsCell = newCell("________________", ITextFont.arial_normal_8, 0);
        invoiceSummaryColumnsCell.setColspan(4);
        invoiceSummaryColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);

        // INVOICE SUMMARY INVOICE TOTAL DUE ROW
        invoiceSummaryColumnsCell = newCell("Invoice total due on", ITextFont.arial_normal_10, 0);
        invoiceSummaryColumnsCell.setColspan(2);
        invoiceSummaryColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        invoiceSummaryColumnsCell = newCell(this.getCurrentCustomerInvoice().getDue_date_str(), ITextFont.arial_bold_10, 0);
        invoiceSummaryColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        invoiceSummaryColumnsCell = newCell("$ " + TMUtils.fillDecimal(String.valueOf(thisInvoiceTotalAmount)), ITextFont.arial_bold_10, 0);
        invoiceSummaryColumnsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceSummaryTable.addCell(invoiceSummaryColumnsCell);
        document.add(invoiceSummaryTable);
        /*
         * THIS INVOICE SUMMARY TABLE END
         */
        
        /*
         * PAYMENT SLIP TABLE BEGIN
         */
        PdfPTable paymentSlipTable = new PdfPTable(5);
        paymentSlipTable.setTotalWidth(535);
        PdfPCell paymentSlipCell = newCell(" ", ITextFont.arial_normal_10, 0);
        paymentSlipCell.setColspan(5);
        Image img = Image.getInstance("pdf"+File.separator+"img"+File.separator+"scissor_separator.png");
        img.setWidthPercentage(100);
        paymentSlipCell.setImage(img);
        paymentSlipTable.addCell(paymentSlipCell);
        
        // cartoon img
		Image cartoon = Image.getInstance("pdf"+File.separator+"img"+File.separator+"cartoon_done.png");
		cartoon.scaleAbsolute(80f, 40.5f);
		cartoon.setAbsolutePosition(50, 165);
		writer.getDirectContent().addImage(cartoon);

        // WHITE TITLE
        paymentSlipCell = newCell("", ITextFont.arial_bold_8, 0);
        paymentSlipCell.setRowspan(4);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("Payment Slip", ITextFont.arial_bold_10, 0);
        paymentSlipCell.setPaddingTop(6);
        paymentSlipCell.setRowspan(4);
        paymentSlipTable.addCell(paymentSlipCell);
        
        // LIGHT GRAY TITLE
        paymentSlipCell = newCell(" ", ITextFont.arial_bold_8, 0, new BaseColor(234,234,234));
        paymentSlipCell.setBorderColorRight(BaseColor.WHITE);
        paymentSlipCell.setBorderWidthRight(1);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_bold_8, 0, new BaseColor(234,234,234));
        paymentSlipCell.setBorderColorRight(BaseColor.WHITE);
        paymentSlipCell.setBorderWidthRight(1);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_bold_8, 0, new BaseColor(234,234,234));
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("Customer ID", ITextFont.arial_bold_8, 0, new BaseColor(234,234,234));
        paymentSlipCell.setBorderColorRight(BaseColor.WHITE);
        paymentSlipCell.setBorderWidthRight(1);
        paymentSlipCell.setIndent(14);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("Invoice Number", ITextFont.arial_bold_8, 0, new BaseColor(234,234,234));
        paymentSlipCell.setBorderColorRight(BaseColor.WHITE);
        paymentSlipCell.setBorderWidthRight(1);
        paymentSlipCell.setIndent(14);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("Due Date", ITextFont.arial_bold_8, 0, new BaseColor(234,234,234));
        paymentSlipCell.setBorderColorRight(BaseColor.WHITE);
        paymentSlipCell.setIndent(14);
        paymentSlipTable.addCell(paymentSlipCell);

        // LIGHT GRAY VALUE
        paymentSlipCell = newCell(this.getCustomer().getId().toString(), ITextFont.arial_normal_6, 0, new BaseColor(234,234,234));
        paymentSlipCell.setBorderColorRight(BaseColor.WHITE);
        paymentSlipCell.setBorderWidthRight(1);
        paymentSlipCell.setIndent(14);
        paymentSlipCell.setPaddingTop(6);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(this.getCurrentCustomerInvoice().getId().toString(), ITextFont.arial_normal_7, 0, new BaseColor(234,234,234));
        paymentSlipCell.setBorderColorRight(BaseColor.WHITE);
        paymentSlipCell.setBorderWidthRight(1);
        paymentSlipCell.setIndent(14);
        paymentSlipCell.setPaddingTop(6);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(this.getCurrentCustomerInvoice().getDue_date_str(), ITextFont.arial_normal_7, 0, new BaseColor(234,234,234));
        paymentSlipCell.setIndent(14);
        paymentSlipCell.setPaddingTop(6);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_bold_8, 0, new BaseColor(234,234,234));
        paymentSlipCell.setBorderColorRight(BaseColor.WHITE);
        paymentSlipCell.setBorderWidthRight(1);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_bold_8, 0, new BaseColor(234,234,234));
        paymentSlipCell.setBorderColorRight(BaseColor.WHITE);
        paymentSlipCell.setBorderWidthRight(1);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_bold_8, 0, new BaseColor(234,234,234));
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell.setColspan(0);
        
        // SEPARATOR BEGIN
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0);
        paymentSlipCell.setFixedHeight(3);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0);
        paymentSlipCell.setFixedHeight(3);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0);
        paymentSlipCell.setFixedHeight(3);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0);
        paymentSlipCell.setFixedHeight(3);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0);
        paymentSlipCell.setFixedHeight(3);
        paymentSlipTable.addCell(paymentSlipCell);
        // SEPARATOR END

		
        // SECOND SECTION
        paymentSlipCell = newCell("Paying By Direct Credit", ITextFont.arial_normal_8, 0);
        paymentSlipCell.setIndent(4);
        paymentSlipCell.setColspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("Total amount due before", ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setPaddingTop(8);
        paymentSlipCell.setIndent(14);
        paymentSlipCell.setRowspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(this.getCurrentCustomerInvoice().getDue_date_str(), ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setIndent(32);
        paymentSlipCell.setPaddingTop(8);
        paymentSlipCell.setRowspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
        // input box begin
        paymentSlipCell = newCell(TMUtils.fillDecimal(String.valueOf(thisInvoiceTotalAmount)), ITextFont.arial_normal_8, 0, BaseColor.WHITE);
        paymentSlipCell.setUseBorderPadding(true);
        paymentSlipCell.setBorderColor(totleChequeAmountBGColor);
        paymentSlipCell.setBorderWidthTop(8f);
        paymentSlipCell.setBorderWidthRight(8f);
        paymentSlipCell.setBorderWidthBottom(8f);
        paymentSlipCell.setBorderWidthLeft(8f);
        paymentSlipCell.setRowspan(2);
        paymentSlipCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        paymentSlipTable.addCell(paymentSlipCell);
        // input box end
        paymentSlipCell = newCell("Bank: "+this.getCompanyDetail().getBank_name(), ITextFont.arial_normal_8, 0);
        paymentSlipCell.setIndent(4);
        paymentSlipCell.setColspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("Name of Account: "+this.getCompanyDetail().getBank_account_name(), ITextFont.arial_normal_8, 0);
        paymentSlipCell.setIndent(4);
        paymentSlipCell.setColspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("Total amount due after", ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setPaddingTop(8);
        paymentSlipCell.setIndent(14);
        paymentSlipCell.setRowspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(this.getCurrentCustomerInvoice().getDue_date_str(), ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setIndent(32);
        paymentSlipCell.setPaddingTop(8);
        paymentSlipCell.setRowspan(2);
        paymentSlipCell.setBorderColor(totleChequeAmountBGColor);
        paymentSlipCell.setBorderWidthRight(2f);
        paymentSlipTable.addCell(paymentSlipCell);
        // input box begin
        paymentSlipCell = newCell(TMUtils.fillDecimal(String.valueOf(thisInvoiceTotalAmount)), ITextFont.arial_normal_8, 0, BaseColor.WHITE);
        paymentSlipCell.setUseBorderPadding(true);
        paymentSlipCell.setBorderColor(totleChequeAmountBGColor);
        paymentSlipCell.setBorderWidthTop(8f);
        paymentSlipCell.setBorderWidthRight(8f);
        paymentSlipCell.setBorderWidthBottom(8f);
        paymentSlipCell.setBorderWidthLeft(8f);
        paymentSlipCell.setRowspan(2);
        paymentSlipCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        paymentSlipTable.addCell(paymentSlipCell);
        // input box end
        paymentSlipCell = newCell("Account Number: "+this.getCompanyDetail().getBank_account_number(), ITextFont.arial_normal_8, 0);
        paymentSlipCell.setIndent(4);
        paymentSlipCell.setColspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
        
        // THIRD SECTION
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0);
        paymentSlipCell.setColspan(5);
        paymentSlipCell.setFixedHeight(3);
        paymentSlipTable.addCell(paymentSlipCell);
        
        // SEPARATOR BEGIN
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setFixedHeight(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setFixedHeight(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setFixedHeight(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setFixedHeight(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setFixedHeight(2);
        paymentSlipTable.addCell(paymentSlipCell);
        // SEPARATOR END
        
        // 
        paymentSlipCell = newCell("Paying by cheques", ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setIndent(4);
        paymentSlipCell.setColspan(3);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("ENCLOSED AMOUNT", ITextFont.arial_bold_white_10, 0, totleChequeAmountBGColor);
        paymentSlipCell.setPaddingTop(4);
        paymentSlipCell.setIndent(40);
        paymentSlipCell.setColspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("Please make cheques payable to Total Mobile Solution Services Ltd and", ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setIndent(4);
        paymentSlipCell.setColspan(3);
        paymentSlipTable.addCell(paymentSlipCell);
		// boxes begin
		Image sBox = Image.getInstance("pdf"+File.separator+"img"+File.separator+"box_large_white.png");
		sBox.scaleAbsolute(155.25f, 35.25f);
		sBox.setAbsolutePosition(0, 0);
		writer.getDirectContent().addImage(sBox);
        paymentSlipCell = newCell(sBox, ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setPaddingLeft(42);
        paymentSlipCell.setPaddingTop(6);
        paymentSlipCell.setRowspan(4);
        paymentSlipCell.setColspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
		// boxes end
        paymentSlipCell = newCell("write your Name and Phone Number on the back of your cheque.", ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setIndent(4);
        paymentSlipCell.setColspan(3);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setColspan(3);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("Please post it with this payment slip to", ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setIndent(4);
        paymentSlipCell.setColspan(3);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(this.getCompanyDetail().getName()+" "+this.getCompanyDetail().getAddress(), ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setIndent(4);
        paymentSlipCell.setColspan(3);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell("** PLEASE DO NOT SEND CASH", ITextFont.arial_normal_white_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setIndent(40);
        paymentSlipCell.setColspan(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setColspan(4);
        paymentSlipCell.setFixedHeight(2);
        paymentSlipTable.addCell(paymentSlipCell);
        paymentSlipCell = newCell(" ", ITextFont.arial_normal_8, 0, totleChequeAmountBGColor);
        paymentSlipCell.setColspan(1);
        paymentSlipCell.setFixedHeight(2);
        paymentSlipTable.addCell(paymentSlipCell);
        // complete the table
        paymentSlipTable.completeRow();
        // write the table to an absolute position
        PdfContentByte paymentSlipTableCanvas = writer.getDirectContent();
        paymentSlipTable.writeSelectedRows(0, -1, (PageSize.A4.getWidth()-paymentSlipTable.getTotalWidth())/2, paymentSlipTable.getTotalHeight() + 28, paymentSlipTableCanvas);
        /*
         * PAYMENT SLIP TABLE END
         */

        // FIRST PAGE'S HEADER
        PdfPTable headerTable = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Phrase(" "));
		pageHeader(writer, headerTable, cell);
        /*
         *
         *  FIRST PAGE CONTENTS END
         * 
         */

        /*
         *
         *  SECOND PAGE CONTENTS BEGIN
         * 
         */
		// start new page
        document.newPage();

        PdfPTable invoiceDetailsTable = new PdfPTable(10);
        invoiceDetailsTable.setWidthPercentage(98);
        PdfPCell invoiceDetailsTitleCell = new PdfPCell(new Phrase(" "));
        invoiceDetailsTitleCell.setBorder(0);
        invoiceDetailsTitleCell.setColspan(10);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // page's width percentage
        invoiceDetailsTitleCell = newCell("Invoice Details", ITextFont.arial_bold_white_10, 0);
        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        invoiceDetailsTitleCell.setColspan(10);
        invoiceDetailsTitleCell.setBackgroundColor(this.titleBGColor);
        invoiceDetailsTitleCell.setPaddingBottom(4);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_bold_10, 0);
        invoiceDetailsTitleCell.setFixedHeight(20);
        invoiceDetailsTitleCell.setColspan(10);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        
        // title
        invoiceDetailsTitleCell = newCell("Service / Product", ITextFont.arial_bold_8, 0);
        invoiceDetailsTitleCell.setIndent(14);
        invoiceDetailsTitleCell.setBorderWidthBottom(1);
        invoiceDetailsTitleCell.setFixedHeight(16);
        invoiceDetailsTitleCell.setColspan(4);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell("Date", ITextFont.arial_bold_8, 0);
        invoiceDetailsTitleCell.setIndent(32);
        invoiceDetailsTitleCell.setBorderWidthBottom(1);
        invoiceDetailsTitleCell.setFixedHeight(16);
        invoiceDetailsTitleCell.setColspan(2);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell("Unit Price", ITextFont.arial_bold_8, 0);
        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceDetailsTitleCell.setBorderWidthBottom(1);
        invoiceDetailsTitleCell.setFixedHeight(16);
        invoiceDetailsTitleCell.setColspan(1);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell("Discount", ITextFont.arial_bold_8, 0);
        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceDetailsTitleCell.setBorderWidthBottom(1);
        invoiceDetailsTitleCell.setFixedHeight(16);
        invoiceDetailsTitleCell.setColspan(1);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell("Qty", ITextFont.arial_bold_8, 0);
        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceDetailsTitleCell.setBorderWidthBottom(1);
        invoiceDetailsTitleCell.setFixedHeight(16);
        invoiceDetailsTitleCell.setColspan(1);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell("Subtotal", ITextFont.arial_bold_8, 0);
        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceDetailsTitleCell.setBorderWidthBottom(1);
        invoiceDetailsTitleCell.setFixedHeight(16);
        invoiceDetailsTitleCell.setColspan(1);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        
        // PRODUCT(S) BEGIN
        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_bold_8, 0);
        invoiceDetailsTitleCell.setFixedHeight(14);
        invoiceDetailsTitleCell.setPaddingTop(4);
        invoiceDetailsTitleCell.setIndent(14);
        invoiceDetailsTitleCell.setColspan(10);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // PRODUCT ITEM(S) BEGIN
        Double totalPrice = 0.0;

        listInvoiceDetails = this.getCurrentCustomerInvoice().getCustomerInvoiceDetails();
        iterInvoiceDetails = listInvoiceDetails.iterator();
    	// get invoice detail(s) from invoice
        while(iterInvoiceDetails.hasNext()){
        	CustomerInvoiceDetail customerInvoiceDetail = iterInvoiceDetails.next();
        	// if both empty
        	Double subTotal = 0.0;
        	Double price = 0.0;
        	Double discount = 0.0;
        	Integer unit = 0;
        	if(customerInvoiceDetail.getInvoice_detail_price() != null && customerInvoiceDetail.getInvoice_detail_unit() != null){
        		subTotal = (customerInvoiceDetail.getInvoice_detail_price()*customerInvoiceDetail.getInvoice_detail_unit());
        		price = customerInvoiceDetail.getInvoice_detail_price();
        		unit = customerInvoiceDetail.getInvoice_detail_unit();
        	}
        	if(customerInvoiceDetail.getInvoice_detail_discount() != null && customerInvoiceDetail.getInvoice_detail_unit() != null){
        		subTotal = (customerInvoiceDetail.getInvoice_detail_discount()*customerInvoiceDetail.getInvoice_detail_unit());
        		discount = customerInvoiceDetail.getInvoice_detail_discount();
        		unit = customerInvoiceDetail.getInvoice_detail_unit();
        	}
        	// if price empty
        	if(customerInvoiceDetail.getInvoice_detail_price() == null && customerInvoiceDetail.getInvoice_detail_unit() != null){
        		// price == null then sub total = 0
//        		subTotal = 0.0;
//        		price = 0.0;
        		unit = customerInvoiceDetail.getInvoice_detail_unit();
        	}
        	// if discount empty
        	if(customerInvoiceDetail.getInvoice_detail_discount() == null && customerInvoiceDetail.getInvoice_detail_unit() != null){
        		// price == null then sub total = 0
//        		subTotal = 0.0;
//        		price = 0.0;
        		unit = customerInvoiceDetail.getInvoice_detail_unit();
        	}
        	// if unit empty
        	if(customerInvoiceDetail.getInvoice_detail_price() != null && customerInvoiceDetail.getInvoice_detail_unit() == null){
        		// price * 1
        		subTotal = (customerInvoiceDetail.getInvoice_detail_price()*1);
        		price = customerInvoiceDetail.getInvoice_detail_price();
//        		unit = 0;
        	}
        	if(customerInvoiceDetail.getInvoice_detail_price() == null && customerInvoiceDetail.getInvoice_detail_unit() == null){
        		// if price & unit both empty
//        		subTotal = 0.0;
//        		price = 0.0;
//        		unit = 0;
        	}
			// plan name
			invoiceDetailsTitleCell = newCell(customerInvoiceDetail.getInvoice_detail_name(), ITextFont.arial_normal_7, 0);
			invoiceDetailsTitleCell.setIndent(22);
			invoiceDetailsTitleCell.setColspan(4);
			invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
			// term date
			invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
			invoiceDetailsTitleCell.setColspan(2);
			invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
			if(customerInvoiceDetail.getInvoice_detail_discount() == null){
				// plan unit price
				invoiceDetailsTitleCell = newCell(TMUtils.fillDecimal(String.valueOf(price)), ITextFont.arial_normal_7, 0);
				invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
				invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
				invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
			} else {
				// plan unit discount
				invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
				invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
				invoiceDetailsTitleCell = newCell(TMUtils.fillDecimal(String.valueOf(discount)), ITextFont.arial_normal_7, 0);
				invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
			}
			// unit
			invoiceDetailsTitleCell = newCell(unit.toString(), ITextFont.arial_normal_7, 0);
			invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
			if(customerInvoiceDetail.getInvoice_detail_discount() == null){
				// sub total
				invoiceDetailsTitleCell = newCell(TMUtils.fillDecimal(String.valueOf(subTotal)), ITextFont.arial_normal_7, 0);
				invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
				totalPrice+=subTotal;
			} else {
				// sub total
				invoiceDetailsTitleCell = newCell("-"+TMUtils.fillDecimal(String.valueOf(subTotal)), ITextFont.arial_normal_7, 0);
				invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
				totalPrice-=subTotal;
			}
        }
        // PRODUCT(S) END
        
        // #####EMTRY SPACE BEGIN
//        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
//        invoiceDetailsTitleCell.setColspan(10);
//        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // #####EMTRY SPACE END
        
        // PRODUCT BEGIN
//        invoiceDetailsTitleCell = newCell("Kanny@adsl.world-net.co.nz", ITextFont.arial_bold_8, 0);
//        invoiceDetailsTitleCell.setFixedHeight(14);
//        invoiceDetailsTitleCell.setPaddingTop(4);
//        invoiceDetailsTitleCell.setIndent(14);
//        invoiceDetailsTitleCell.setColspan(10);
//        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // PRODUCT ITEMS BEGIN
        // PRODUCT ITEM BEGIN
//        invoiceDetailsTitleCell = newCell("FS/FS 100G + 100G Internet Monthly Fee", ITextFont.arial_normal_7, 0);
//        invoiceDetailsTitleCell.setIndent(22);
//        invoiceDetailsTitleCell.setColspan(4);
//        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
//        invoiceDetailsTitleCell = newCell("2014-02-19  -  2014-03-18", ITextFont.arial_normal_7, 0);
//        invoiceDetailsTitleCell.setColspan(2);
//        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
//        invoiceDetailsTitleCell = newCell("$33.91", ITextFont.arial_normal_7, 0);
//        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
//        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
//        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
//        invoiceDetailsTitleCell = newCell("1", ITextFont.arial_normal_7, 0);
//        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
//        invoiceDetailsTitleCell = newCell("$33.91", ITextFont.arial_normal_7, 0);
//        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // PRODUCT ITEM END
        // PRODUCT ITEMS END
        // PRODUCT END
        /*
         * 
         * PRODUCTS END
         * 
         */
        
        // #####SEPARATOR BEGIN
        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
        invoiceDetailsTitleCell.setFixedHeight(8);
        invoiceDetailsTitleCell.setBorderWidthBottom(1);
        invoiceDetailsTitleCell.setColspan(10);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // #####SEPARATOR END
        
        // TOTAL BEGIN
        // #####EMTRY SPACE BEGIN
        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
        invoiceDetailsTitleCell.setColspan(10);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // #####EMTRY SPACE END
        
        // FIRST ROW BEGIN
        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
        invoiceDetailsTitleCell.setFixedHeight(14);
        invoiceDetailsTitleCell.setColspan(7);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell("Total before GST", ITextFont.arial_normal_8, 0);
        invoiceDetailsTitleCell.setColspan(2);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        Double totalBeforeGST = totalPrice/1.15;
        // fill decimal, keep 2 decimals
        invoiceDetailsTitleCell = newCell(TMUtils.fillDecimal(String.valueOf(totalBeforeGST)), ITextFont.arial_normal_8, 0);
        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // FIRST ROW END
        // SECOND ROW BEGIN
        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
        invoiceDetailsTitleCell.setFixedHeight(14);
        invoiceDetailsTitleCell.setColspan(7);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell("GST at 15%", ITextFont.arial_normal_8, 0);
        invoiceDetailsTitleCell.setColspan(2);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        Double totalGST = totalPrice-totalPrice/1.15;
        // fill decimal, keep 2 decimals
        invoiceDetailsTitleCell = newCell(TMUtils.fillDecimal(String.valueOf(totalGST)), ITextFont.arial_normal_8, 0);
        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // SECOND ROW END
        
        // SEPARATOR BEGIN
        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
        invoiceDetailsTitleCell.setColspan(6);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
        invoiceDetailsTitleCell.setColspan(4);
        invoiceDetailsTitleCell.setBorderWidthTop(1);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // SEPARATOR END
        
        // TOTAL AMOUNT BEGIN
        invoiceDetailsTitleCell = newCell(" ", ITextFont.arial_normal_7, 0);
        invoiceDetailsTitleCell.setColspan(7);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell("Invoice Total", ITextFont.arial_bold_8, 0);
        invoiceDetailsTitleCell.setColspan(2);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        invoiceDetailsTitleCell = newCell(TMUtils.fillDecimal(String.valueOf(totalPrice)), ITextFont.arial_bold_8, 0);
        invoiceDetailsTitleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceDetailsTable.addCell(invoiceDetailsTitleCell);
        // TOTAL AMOUNT END
        
        // TOTAL END
        
        
        
        // ADD TABLE 2 DOCUMENT
        document.add(invoiceDetailsTable);
        
        /*
         * 
         * 
         * 
         */
        
        
        // SECOND PAGE'S HEADER
        headerTable = new PdfPTable(1);
        cell = new PdfPCell(new Phrase(" "));
		pageHeader(writer, headerTable, cell);
        /*
         *
         *  SECOND PAGE CONTENTS END
         * 
         */

        
        /*
         *
         *  THIRD PAGE CONTENTS BEGIN
         * 
         */

        /*
         *
         *  THIRD PAGE CONTENTS END
         * 
         */
		

        // THIRD PAGE'S HEADER
//        document.newPage();
//        headerTable = new PdfPTable(1);
//		cell = new PdfPCell(new Phrase(" "));
//		pageHeader(writer, headerTable, cell);
//		
		// CLOSE DOCUMENT
        document.close();
		return outputFile;
        
		
        
	}
	
	private void pageHeader(PdfWriter writer, PdfPTable headerTable, PdfPCell cell) throws MalformedURLException, IOException, DocumentException{
		headerTable.setTotalWidth(510);
        // logo & start
		Image logo = Image.getInstance("pdf"+File.separator+"img"+File.separator+"logo_top_final.png");
		logo.scaleAbsolute(171f, 45f);
		logo.setAbsolutePosition(44, 760);
		writer.getDirectContent().addImage(logo);
		
		Phrase t1 = new Phrase("Statement / Tax Invoice", ITextFont.arial_normal_14);
		Phrase t2 = new Phrase("GST Registration Number: "+this.getCompanyDetail().getGst_registration_number(), ITextFont.lucida_sans_unicode_9);
		PdfContentByte canvas = writer.getDirectContentUnder();
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, t1, 44, 744, 0);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, t2, 44, 730, 0);
		
		// seperator
        LineSeparator UNDERLINE = new LineSeparator(3, 100, titleBGColor, Element.ALIGN_CENTER, -2);
        // adds a separator
        Phrase seperator = new Phrase();
        seperator.add(UNDERLINE);
		/*
		 * header table begin
		 */
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase(this.getCompanyDetail().getName(), ITextFont.arial_normal_8));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase(this.getCompanyDetail().getAddress(), ITextFont.arial_normal_8));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("Tel: "+this.getCompanyDetail().getTelephone()+"  /  Fax: "+this.getCompanyDetail().getFax(), ITextFont.arial_normal_8));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase(this.getCompanyDetail().getDomain(), ITextFont.arial_normal_8));
        headerTable.addCell(cell);
        cell = new PdfPCell(seperator);
		cell.setBorder(0);
        headerTable.addCell(cell);
		/*
		 * header table end
		 */      
		/*
		 * invoice basics begin
		 */
		cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(0);
        Paragraph basicsP = new Paragraph();
        Phrase date = new Phrase("Date: ", ITextFont.arial_bold_8);
        Phrase invoiceNo = new Phrase(" | Invoice No: ", ITextFont.arial_bold_8);
        Phrase loginName = new Phrase(" | Customer Id: ", ITextFont.arial_bold_8);
        Phrase dateField = new Phrase(TMUtils.dateFormatYYYYMMDD(this.getCurrentCustomerInvoice().getCreate_date()), ITextFont.arial_normal_8);
        Phrase invoiceNoField = new Phrase(this.getCurrentCustomerInvoice().getId().toString(), ITextFont.arial_normal_8);
        Phrase loginNameField = new Phrase(this.getCustomer().getId().toString(), ITextFont.arial_normal_8);
        // put paragraph into table cell
        basicsP.add(date);
        basicsP.add(dateField);
        basicsP.add(invoiceNo);
        basicsP.add(invoiceNoField);
        basicsP.add(loginName);
        basicsP.add(loginNameField);
        cell.setPaddingTop(10);
        cell.setPaddingLeft(4);
        cell.addElement(basicsP);
        headerTable.addCell(cell);
		/*
		 * invoice basics end
		 */
        // complete the table
        headerTable.completeRow();
        // write the table to an absolute position
        PdfContentByte paymentSlipTableCanvas = writer.getDirectContent();
        headerTable.writeSelectedRows(0, -1, 41, 810, paymentSlipTableCanvas);
	}
	
	
	private PdfPCell newCell(Image sBox, Font font, int border, BaseColor color) {
		PdfPCell pcell = new PdfPCell(new Phrase("", font));
		pcell.addElement(sBox);
		pcell.setBorder(border);
		pcell.setBackgroundColor(color);
		return pcell;
	}

	public PdfPCell newCell(String content, Font font, int border){
		PdfPCell pcell = new PdfPCell(new Phrase(content, font));
		pcell.setBorder(border);
		return pcell;
	}
	
	public PdfPCell newCell(String content, Font font, int border, BaseColor color){
		PdfPCell pcell = new PdfPCell(new Phrase(content, font));
		pcell.setBorder(border);
		pcell.setBackgroundColor(color);
		return pcell;
	}

	public CompanyDetail getCompanyDetail() {
		return companyDetail;
	}

	public void setCompanyDetail(CompanyDetail companyDetail) {
		this.companyDetail = companyDetail;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerInvoice getCurrentCustomerInvoice() {
		return currentCustomerInvoice;
	}

	public void setCurrentCustomerInvoice(CustomerInvoice currentCustomerInvoice) {
		this.setLastCustomerInvoice(currentCustomerInvoice.getLastCustomerInvoice());
		this.currentCustomerInvoice = currentCustomerInvoice;
	}

	public CustomerInvoice getLastCustomerInvoice() {
		return lastCustomerInvoice;
	}

	public void setLastCustomerInvoice(CustomerInvoice lastCustomerInvoice) {
		this.lastCustomerInvoice = lastCustomerInvoice;
	}

	
}