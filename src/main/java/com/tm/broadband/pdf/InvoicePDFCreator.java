package com.tm.broadband.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tm.broadband.model.CompanyDetail;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerCallRecord;
import com.tm.broadband.model.CustomerInvoice;
import com.tm.broadband.model.CustomerInvoiceDetail;
import com.tm.broadband.model.Organization;
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
	private Organization org;
	private List<CustomerCallRecord> ccrs;

	private BaseColor titleBGColor = new BaseColor(92,184,92);
	private BaseColor totleChequeAmountBGColor = new BaseColor(110,110,110);
	
	private Integer globalBorderWidth = 0;
	
	
	private Double lastBalance = 0d;
	private Double thisInvoiceTotalAmount = 0d;
	private Double beforeTaxAmount = 0d;
	private Double taxAmount = 0d;
	
	private String pstn_number = "";
	
	public InvoicePDFCreator(){}
	
	public InvoicePDFCreator(CompanyDetail companyDetail
			,CustomerInvoice currentCustomerInvoice
			,Customer customer
			,Organization org
			,List<CustomerCallRecord> ccrs) {
		this.companyDetail = companyDetail;
		this.currentCustomerInvoice = currentCustomerInvoice;
		this.lastCustomerInvoice = currentCustomerInvoice.getLastCustomerInvoice();
		this.customer = customer;
		this.org = org;
		this.ccrs = ccrs;
	}

	public Map<String, Object> create() throws DocumentException, MalformedURLException, IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		
        Document document = new Document(PageSize.A4);
        
    	this.pstn_number = ccrs != null && ccrs.size() > 0 ? ccrs.get(0).getClear_service_id() : "";
        
		// Output PDF Path, e.g.: application_60089.pdf
		String outputFile = TMUtils.createPath(
				"broadband" 
				+ File.separator
				+ "customers" + File.separator + this.customer.getId()
				+ File.separator + "invoice_" + this.getCurrentCustomerInvoice().getId() + ".pdf");
		
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();
        
        /*
         *
         *  FIRST PAGE BEGIN
         * 
         */
		super.setGlobalBorderWidth(globalBorderWidth);
		
		// BASIC INFO
        document.add(createCustomerBasicInfo());

        
        Double cidTotalPrice = 0d;
        List<CustomerInvoiceDetail> cids = this.getCurrentCustomerInvoice().getCustomerInvoiceDetails();
    	// get invoice detail(s) from invoice
        for (CustomerInvoiceDetail cid : cids) {
        	// if both empty
        	Double subTotal = 0d;
        	Double detailPrice = cid.getInvoice_detail_price();
        	Integer detailUnit = cid.getInvoice_detail_unit();
        	Double detailDiscount = cid.getInvoice_detail_discount();
        	subTotal = detailPrice!=null && detailUnit!=null ? detailPrice*detailUnit :
        				detailPrice!=null && detailUnit==null ? detailPrice : 0d;
        	cidTotalPrice = detailDiscount==null ? cidTotalPrice+=subTotal : (cidTotalPrice-=(detailDiscount*detailUnit));
		}
        

        lastBalance = this.getLastCustomerInvoice()!=null && this.getLastCustomerInvoice().getBalance()!=null ? this.getLastCustomerInvoice().getBalance() : 0d;
        BigDecimal bigLastBalance = new BigDecimal(lastBalance);
        BigDecimal bigTotalAmount = new BigDecimal(thisInvoiceTotalAmount);
        thisInvoiceTotalAmount = cidTotalPrice + bigLastBalance.add(bigTotalAmount).doubleValue();
        
        if(thisInvoiceTotalAmount > 0){
        	// personal plan include GST
            this.beforeTaxAmount = thisInvoiceTotalAmount/1.15;
            
            // include GST
            this.taxAmount = thisInvoiceTotalAmount-thisInvoiceTotalAmount/1.15;
        } else {
        	this.beforeTaxAmount = thisInvoiceTotalAmount;
        	this.taxAmount = thisInvoiceTotalAmount;
        }
        
        // RECENT TRANSACTION
        document.add(createRecentTransaction());
        
        // INVOICE SUMMARY
        document.add(createInvoiceSummary());

        
        /*
         * PAYMENT SLIP TABLE BEGIN
         */
        
        // CARTOON
		Image cartoon = Image.getInstance("pdf"+File.separator+"img"+File.separator+"cartoon_done.png");
		cartoon.scaleAbsolute(80f, 40.5f);
		cartoon.setAbsolutePosition(50, 165);
		writer.getDirectContent().addImage(cartoon);
		
		{	// createPaymentSlip
	        PdfPTable paymentSlipTable = newTable().columns(5).totalWidth(535F).o();
	        Image img = Image.getInstance("pdf"+File.separator+"img"+File.separator+"scissor_separator.png");
	        img.setWidthPercentage(100);
	        addCol(paymentSlipTable, " ").colspan(5).image(img).o();
	        
	
	        // WHITE TITLE
	        addEmptyRow(paymentSlipTable, 4);
	        
	        addCol(paymentSlipTable, "Payment Slip").rowspan(4).font(ITextFont.arial_bold_10).paddingTo("t", 6F).o();
	        
	        // LIGHT GRAY TITLE
	        addCol(paymentSlipTable, " ").bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).o();
	        addCol(paymentSlipTable, " ").bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).o();
	        addCol(paymentSlipTable, " ").bgColor(new BaseColor(234,234,234)).o();
	        
	        // LIGHT GRAY CONTENT
	        addCol(paymentSlipTable, "Customer ID").font(ITextFont.arial_bold_8).bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).indent(14F).o();
	        addCol(paymentSlipTable, "Invoice Number").font(ITextFont.arial_bold_8).bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).indent(14F).o();
	        addCol(paymentSlipTable, "Due Date").font(ITextFont.arial_bold_8).bgColor(new BaseColor(234,234,234)).indent(14F).o();
	
	        // LIGHT GRAY VALUE
	        addCol(paymentSlipTable, this.getCustomer().getId().toString()).font(ITextFont.arial_normal_6).bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).paddingTo("t", 6F).indent(14F).o();
	        addCol(paymentSlipTable, this.getCurrentCustomerInvoice().getId().toString()).font(ITextFont.arial_normal_7).bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).paddingTo("t", 6F).indent(14F).o();
	        addCol(paymentSlipTable, TMUtils.retrieveMonthAbbrWithDate(this.getCurrentCustomerInvoice().getDue_date())).font(ITextFont.arial_normal_7).bgColor(new BaseColor(234,234,234)).paddingTo("t", 6F).indent(14F).o();
	        addCol(paymentSlipTable, " ").bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).o();
	        addCol(paymentSlipTable, " ").bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).o();
	        addCol(paymentSlipTable, " ").bgColor(new BaseColor(234,234,234)).o();
	        
	        // SEPARATOR BEGIN
	        addEmptyCol(paymentSlipTable, 2F, 5);
	        // SEPARATOR END
	
			
	        // SECOND SECTION
	        addCol(paymentSlipTable, "Paying By Direct Credit").colspan(2).font(ITextFont.arial_normal_8).indent(4F).o();
	        addCol(paymentSlipTable, "Total amount due before").rowspan(2).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).paddingTo("t", 8F).indent(14F).o();
	        addCol(paymentSlipTable, this.getCurrentCustomerInvoice().getDue_date_str()).rowspan(2).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).paddingTo("t", 8F).indent(32F).o();
	        // input box begin
	        addCol(paymentSlipTable, TMUtils.fillDecimalPeriod(String.valueOf(thisInvoiceTotalAmount))).rowspan(2).font(ITextFont.arial_normal_8).bgColor(BaseColor.WHITE).borderColor(totleChequeAmountBGColor).borderZoom(8F).alignH("r").o();
	        // input box end
	        addCol(paymentSlipTable, "Bank: "+this.getCompanyDetail().getBank_name()).colspan(2).font(ITextFont.arial_normal_8).indent(4F).o();
	        addCol(paymentSlipTable, "Name of Account: "+this.getCompanyDetail().getBank_account_name()).colspan(2).font(ITextFont.arial_normal_8).indent(4F).o();
	        addCol(paymentSlipTable, "Total amount due after").rowspan(2).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).paddingTo("t", 8F).indent(14F).o();
	        addCol(paymentSlipTable, this.getCurrentCustomerInvoice().getDue_date_str()).rowspan(2).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).paddingTo("t", 8F).indent(32F).o();
	        // input box begin
	        addCol(paymentSlipTable, TMUtils.fillDecimalPeriod(String.valueOf(thisInvoiceTotalAmount))).rowspan(2).font(ITextFont.arial_normal_8).bgColor(BaseColor.WHITE).borderColor(totleChequeAmountBGColor).borderZoom(8F).alignH("r").o();
	        // input box end
	        addCol(paymentSlipTable, "Account Number: "+this.getCompanyDetail().getBank_account_number()).colspan(2).font(ITextFont.arial_normal_8).indent(4F).o();
	        
	        // THIRD SECTION
	        addEmptyCol(paymentSlipTable, 1F, 5);
	        
	        // SEPARATOR BEGIN
	        // SEPARATOR END
	        
	        addCol(paymentSlipTable, "Paying by cheques").colspan(3).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).paddingTo("t", 4F).indent(4F).o();
	        addCol(paymentSlipTable, "ENCLOSED AMOUNT").colspan(2).font(ITextFont.arial_bold_white_10).bgColor(totleChequeAmountBGColor).paddingTo("t", 4F).indent(40F).o();
	        addCol(paymentSlipTable, "Please make cheques payable to "+this.companyDetail.getName()+" and").colspan(3).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).indent(4F).o();
	        
			// BEGIN BOX
			addCol(paymentSlipTable, " ").colspan(2).rowspan(4).bgColor(BaseColor.WHITE).paddingTo("l", 42F).paddingTo("t", 6F).borderColor(totleChequeAmountBGColor).border("r", 20F).o();
			// END BOX
			
			addCol(paymentSlipTable, "write your Name and Phone Number on the back of your cheque.").colspan(3).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).indent(4F).o();
			addCol(paymentSlipTable, " ").colspan(3).bgColor(totleChequeAmountBGColor).o();
			addCol(paymentSlipTable, "Please post it with this payment slip to").colspan(3).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).indent(4F).o();
			addCol(paymentSlipTable, this.getCompanyDetail().getName()+" PO Box 41547, St Lukes, Auckland 1346, New Zealand").colspan(3).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).indent(4F).o();
			addCol(paymentSlipTable, "** PLEASE DO NOT SEND CASH").colspan(2).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).indent(40F).o();
			addEmptyCol(paymentSlipTable, 2F, 5);
	        // complete the table
	        paymentSlipTable.completeRow();
	        // write the table to an absolute position
	        PdfContentByte paymentSlipTableCanvas = writer.getDirectContent();
	        paymentSlipTable.writeSelectedRows(0, -1, (PageSize.A4.getWidth()-paymentSlipTable.getTotalWidth())/2, paymentSlipTable.getTotalHeight() + 28, paymentSlipTableCanvas);
	        /*
	         * PAYMENT SLIP TABLE END
	         */
        }

        // FIRST PAGE'S HEADER
		pageHeader(writer);
        /*
         *
         *  FIRST PAGE END
         * 
         */

        /*
         *
         *  SECOND PAGE BEGIN
         * 
         */
		// start new page
        document.newPage();
        
        // SECOND PAGE'S HEADER
		pageHeader(writer);

        // ADD TABLE 2 DOCUMENT
        document.add(createInvoiceDetails());
        /*
         *
         *  SECOND PAGE END
         * 
         */
        
        /*
         *
         *  THIRD PAGE BEGIN
         * 
         */
        
        // BEGIN CALLING RECORD
        if(isCCRS()){
     		// start new page
            document.newPage();
            // SECOND PAGE'S HEADER
    		pageHeader(writer);
            // ADD TABLE 2 DOCUMENT
            document.add(createCallRecordDetails());
        }
        // END CALLING RECORD
        
        /*
         *
         *  THIRD PAGE END
         * 
         */
		
		// CLOSE DOCUMENT
        document.close();
        
        map.put("amount_paid", this.getCurrentCustomerInvoice().getAmount_paid());
        map.put("amount_payable", thisInvoiceTotalAmount);
        map.put("balance", thisInvoiceTotalAmount - this.getCurrentCustomerInvoice().getAmount_paid());
        map.put("filePath", outputFile);
		return map;
	}
	
	// BEGIN CUSTOMER BASIC INFO
	private PdfPTable createCustomerBasicInfo(){
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(102);
		// add common header
        addEmptyCol(headerTable, 140F, 1);
        
        String customerName = null;
//        String customerTitle = null;
        if("business".equals(customer.getCustomer_type())){
        	customerName = org.getOrg_name();
//        	customerTitle = "BUSINESS";
        } else {
        	customerName = this.getCustomer().getTitle() != null ? this.getCustomer().getTitle().toUpperCase()+" " : "";
        	customerName += this.getCustomer().getFirst_name()+" "+this.getCustomer().getLast_name();
//        	customerTitle = "PERSONAL";
        }
//        addCol(headerTable, customerTitle + " USER").font(ITextFont.arial_bold_12).border(0).paddingTo("l", 50F).paddingTo("b", 10F).o();
        addCol(headerTable, customerName.trim()).font(ITextFont.arial_bold_9).paddingTo("l", 30F).paddingTo("b", 10F).border(0).o();
        String addressArr[] = this.getCustomer().getAddress().split(",");
        for (String address : addressArr) {
            addCol(headerTable, address.trim()).font(ITextFont.arial_bold_8).paddingTo("l", 30F).border(0).o();
		}
        addEmptyCol(headerTable, 40F, 1);
		return headerTable;
	}
	// END CUSTOMER BASIC INFO

    
    // current invoice
    // this invoice amount is consist of (current invoice total amount + last invoice balance - current customer invoice paid fees)
    // preventing subtraction inaccuracy
    
	// BEGIN RECENT TRANSACTION
	private PdfPTable createRecentTransaction(){
        /*
         * Recent Transactions Table begin
         */
        // non empty table start with 4 columns
		
		Integer colspan = 8;
		
        PdfPTable transactionTable = newTable().columns(colspan).widthPercentage(98F).o();
        addTitleBar(transactionTable, "Recent transactions", ITextFont.arial_bold_white_10, titleBGColor, titleBGColor, colspan, 4F);
        
        // TITLE BAR COLUMN
        addCol(transactionTable, "Date").colspan(2).font(ITextFont.arial_bold_9).paddingV(6F).indent(10F).o();
        addCol(transactionTable, "Description").colspan(colspan/2).font(ITextFont.arial_bold_9).paddingV(6F).o();
        addCol(transactionTable, "Amount").colspan(2).font(ITextFont.arial_bold_9).paddingV(6F).alignH("r").o();

        // account filtering of last invoice begin
        Double lastAmountPayable = this.getLastCustomerInvoice()!=null && this.getLastCustomerInvoice().getAmount_payable()!=null ? this.getLastCustomerInvoice().getAmount_payable() : 0.0;
        Double lastAmountPaid = this.getLastCustomerInvoice()!=null && this.getLastCustomerInvoice().getAmount_paid()!=null ? this.getLastCustomerInvoice().getAmount_paid() : 0.0;
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
        	
            // LAST INVOICE PAYABLE CASE
        	addCol(transactionTable, TMUtils.retrieveMonthAbbrWithDate(this.getLastCustomerInvoice().getCreate_date())).colspan(2).font(ITextFont.arial_normal_8).indent(10F).o();
        	addCol(transactionTable, "Previous Invoice Total").colspan(colspan/2).font(ITextFont.arial_normal_8).o();
        	addCol(transactionTable, "$ " + TMUtils.fillDecimalPeriod(String.valueOf(lastAmountPayable))).colspan(2).font(ITextFont.arial_normal_8).alignH("r").o();
            
            // LAST INVOICE PAID CASE
        	addCol(transactionTable, this.getLastCustomerInvoice().getPaid_date_str()).colspan(2).font(ITextFont.arial_normal_8).indent(10F).o();
        	addCol(transactionTable, this.getLastCustomerInvoice().getPaid_type()).colspan(colspan/2).font(ITextFont.arial_normal_8).o();
        	addCol(transactionTable, "$ -" + TMUtils.fillDecimalPeriod(String.valueOf(lastAmountPaid))).colspan(2).font(ITextFont.arial_normal_8).alignH("r").o();

            // LAST INVOICE SEPARATOR LINE
        	addEmptyCol(transactionTable, 7);
        	addCol(transactionTable, " ").border("b", 1F).o();

            // LAST INVOICE TOTAL AMOUNT
        	addCol(transactionTable, "Opening Balance").colspan(6).font(ITextFont.arial_bold_10).alignH("r").o();
        	addCol(transactionTable, "$ "+ TMUtils.fillDecimalPeriod(String.valueOf(lastAmountPayable - lastAmountPaid))).colspan(2).font(ITextFont.arial_bold_10).alignH("r").o();
        	addEmptyCol(transactionTable, 10F, colspan);
            
        } else {
        	
            // CURRENT INVOICE PAYABLE CASE
        	addCol(transactionTable, this.getCurrentCustomerInvoice().getCreate_date_str()).colspan(2).font(ITextFont.arial_normal_8).indent(10F).o();
        	addCol(transactionTable, "Current Invoice Total").colspan(colspan/2).font(ITextFont.arial_normal_8).o();
        	addCol(transactionTable, "$ " + TMUtils.fillDecimalPeriod(String.valueOf(this.getCurrentCustomerInvoice().getAmount_payable()))).colspan(2).font(ITextFont.arial_normal_8).alignH("r").o();
            
            // CURRENT INVOICE PAID CASE
        	addCol(transactionTable, this.getCurrentCustomerInvoice().getPaid_date_str()).colspan(2).font(ITextFont.arial_normal_8).indent(10F).o();
        	addCol(transactionTable, this.getCurrentCustomerInvoice().getPaid_type()).colspan(colspan/2).font(ITextFont.arial_normal_8).o();
        	addCol(transactionTable, "$ -" + TMUtils.fillDecimalPeriod(String.valueOf(this.getCurrentCustomerInvoice().getAmount_paid()))).colspan(2).font(ITextFont.arial_normal_8).indent(10F).alignH("r").o();
            
            // CURRENT INVOICE SEPARATOR LINE
        	addEmptyCol(transactionTable, 7);
        	addCol(transactionTable, " ").border("b", 1F).o();
            addEmptyCol(transactionTable, 2F, colspan);

            // CURRENT INVOICE TOTAL AMOUNT
        	addCol(transactionTable, "Opening Balance").colspan(6).font(ITextFont.arial_bold_10).alignH("r").o();
        	addCol(transactionTable, "$ "+ TMUtils.fillDecimalPeriod(String.valueOf(this.getCurrentCustomerInvoice().getAmount_paid() - this.getCurrentCustomerInvoice().getAmount_payable()))).colspan(2).font(ITextFont.arial_bold_10).alignH("r").o();
        	addEmptyCol(transactionTable, 10F, colspan);
            
        }
        
        return transactionTable;
	}
	// END RECENT TRANSACTION
	
	private PdfPTable createInvoiceSummary(){
		
		Integer colspan = 8;
		
        PdfPTable invoiceSummaryTable = newTable().columns(colspan).widthPercentage(98F).o();
        
        addTitleBar(invoiceSummaryTable, "This Invoice Summary", ITextFont.arial_bold_white_10, titleBGColor, titleBGColor, colspan, 4F);

        // INVOICE SUMMARY FIRST ROW
        addCol(invoiceSummaryTable, "Net charges").colspan(6).font(ITextFont.arial_normal_8).paddingTo("t", 10F).indent(10F).o();
        
        addCol(invoiceSummaryTable, "$ " + TMUtils.fillDecimalPeriod(String.valueOf(beforeTaxAmount))).colspan(2).font(ITextFont.arial_normal_8).paddingTo("t", 10F).alignH("r").o();

        // INVOICE SUMMARY SECOND ROW
        addCol(invoiceSummaryTable, "GST at 15%").colspan(6).font(ITextFont.arial_normal_8).indent(10F).o();
        addCol(invoiceSummaryTable, "$ " + TMUtils.fillDecimalPeriod(String.valueOf(taxAmount))).colspan(2).font(ITextFont.arial_normal_8).alignH("r").o();

        // INVOICE SUMMARY THIRD ROW
        addCol(invoiceSummaryTable, "Total charges (please see Invoice Details page)").colspan(6).font(ITextFont.arial_normal_8).indent(10F).o();
        addCol(invoiceSummaryTable, "$ " + TMUtils.fillDecimalPeriod(String.valueOf(thisInvoiceTotalAmount))).colspan(2).font(ITextFont.arial_normal_8).alignH("r").o();
        
        // Empty ROW
        addEmptyCol(invoiceSummaryTable, 30F, colspan);
        
        // INVOICE SUMMARY SEPARATOR ROW
    	addEmptyCol(invoiceSummaryTable, 7);
    	addCol(invoiceSummaryTable, " ").border("b", 1F).o();
        addEmptyCol(invoiceSummaryTable, 2F, colspan);

        // INVOICE SUMMARY INVOICE TOTAL DUE ROW
    	addCol(invoiceSummaryTable, "Invoice total due on").colspan(4).font(ITextFont.arial_normal_10).alignH("r").o();
    	addCol(invoiceSummaryTable, this.getCurrentCustomerInvoice().getDue_date_str()).colspan(2).font(ITextFont.arial_bold_10).alignH("r").o();
    	addCol(invoiceSummaryTable, "$ " + TMUtils.fillDecimalPeriod(String.valueOf(thisInvoiceTotalAmount))).colspan(2).font(ITextFont.arial_bold_10).alignH("r").o();
        
        return invoiceSummaryTable;
	}
	
	private PdfPTable createInvoiceDetails(){
        PdfPTable invoiceDetailsTable = newTable().columns(10).widthPercentage(98F).o();
        addEmptyCol(invoiceDetailsTable, 160F, 10);
        // page's width percentage
        addCol(invoiceDetailsTable, "Current Invoice Details").colspan(10).font(ITextFont.arial_bold_white_10).bgColor(titleBGColor).paddingTo("b", 4F).alignH("c").o();
        addEmptyCol(invoiceDetailsTable, 20F, 10);
        
        // title
        addCol(invoiceDetailsTable, "Service / Product").colspan(4).font(ITextFont.arial_bold_8).border("b", 1F).fixedHeight(16F).o();
        addCol(invoiceDetailsTable, "Date").colspan(2).font(ITextFont.arial_bold_8).border("b", 1F).fixedHeight(16F).indent(32F).o();
        addCol(invoiceDetailsTable, "Unit Price").font(ITextFont.arial_bold_8).border("b", 1F).fixedHeight(16F).alignH("r").o();
        addCol(invoiceDetailsTable, "Discount").font(ITextFont.arial_bold_8).border("b", 1F).fixedHeight(16F).alignH("r").o();
        addCol(invoiceDetailsTable, "Qty").font(ITextFont.arial_bold_8).border("b", 1F).fixedHeight(16F).alignH("r").o();
        addCol(invoiceDetailsTable, "Subtotal").font(ITextFont.arial_bold_8).border("b", 1F).fixedHeight(16F).alignH("r").o();
        
        // PRODUCT(S) BEGIN
        addEmptyCol(invoiceDetailsTable, 14F, 10);
        // PRODUCT ITEM(S) BEGIN
        
        Double totalPrice = 0d;
        

        List<CustomerInvoiceDetail> listInvoiceDetails = this.getCurrentCustomerInvoice().getCustomerInvoiceDetails();
        Iterator<CustomerInvoiceDetail> iterInvoiceDetails = listInvoiceDetails.iterator();
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
        	addCol(invoiceDetailsTable, customerInvoiceDetail.getInvoice_detail_name()).colspan(4).font(ITextFont.arial_normal_7).indent(22F).o();
			// term date
        	addEmptyCol(invoiceDetailsTable, 2);
			if(customerInvoiceDetail.getInvoice_detail_discount() == null){
				// plan unit price
				addCol(invoiceDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(price))).font(ITextFont.arial_normal_7).alignH("r").o();
				addEmptyCol(invoiceDetailsTable, 1);
			} else {
				// plan unit discount
				addEmptyCol(invoiceDetailsTable, 1);
				addCol(invoiceDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(discount))).font(ITextFont.arial_normal_7).alignH("r").o();
			}
			// unit
			addCol(invoiceDetailsTable, unit.toString()).font(ITextFont.arial_normal_7).alignH("r").o();
			if(customerInvoiceDetail.getInvoice_detail_discount() == null){
				// sub total
				addCol(invoiceDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(subTotal))).font(ITextFont.arial_normal_7).alignH("r").o();
				totalPrice+=subTotal;
			} else {
				// sub total
				addCol(invoiceDetailsTable, "-"+TMUtils.fillDecimalPeriod(String.valueOf(subTotal))).font(ITextFont.arial_normal_7).alignH("r").o();
				totalPrice-=subTotal;
			}
        }
        // PRODUCT(S) END
        
        // #####SEPARATOR BEGIN
        addCol(invoiceDetailsTable, " ").colspan(10).fixedHeight(8F).border("b", 1F).o();
        // #####SEPARATOR END
        
        // TOTAL BEGIN
        // #####EMTRY SPACE BEGIN
        addEmptyCol(invoiceDetailsTable, 10);
        // #####EMTRY SPACE END
        
        // FIRST ROW BEGIN
        addEmptyCol(invoiceDetailsTable, 14F, 7);
        addCol(invoiceDetailsTable, "Total before GST").colspan(2).font(ITextFont.arial_normal_8).o();
        Double totalBeforeGST = totalPrice/1.15;
        // fill decimal, keep 2 decimals
        addCol(invoiceDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(totalBeforeGST))).font(ITextFont.arial_normal_8).alignH("r").o();
        // FIRST ROW END
        // SECOND ROW BEGIN
        addEmptyCol(invoiceDetailsTable, 14F, 7);
        addCol(invoiceDetailsTable, "GST at 15%").colspan(2).font(ITextFont.arial_normal_8).o();
        Double totalGST = totalPrice-totalPrice/1.15;
        // fill decimal, keep 2 decimals
        addCol(invoiceDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(totalGST))).font(ITextFont.arial_normal_8).alignH("r").o();
        // SECOND ROW END
        
        // SEPARATOR BEGIN
        addEmptyCol(invoiceDetailsTable, 4F, 6);
        addCol(invoiceDetailsTable, " ").colspan(4).borderWidth("b", 1F).fixedHeight(4F).o();
        // SEPARATOR END
        
        // TOTAL AMOUNT BEGIN
        addEmptyCol(invoiceDetailsTable, 7);
        addCol(invoiceDetailsTable, "Invoice Total").colspan(2).font(ITextFont.arial_bold_8).o();
        addCol(invoiceDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(totalPrice))).font(ITextFont.arial_bold_8).alignH("r").o();
        // TOTAL AMOUNT END
        
        // TOTAL END
        return invoiceDetailsTable;
	}
	
	private PdfPTable createCallRecordDetails(){
        PdfPTable callRecordDetailsTable = newTable().columns(11).widthPercentage(98F).o();
        addEmptyCol(callRecordDetailsTable, 160F, 11);
        addCol(callRecordDetailsTable, "Calling details : " + this.pstn_number).colspan(11).font(ITextFont.arial_bold_12).paddingTo("b", 4F).o();
        addEmptyCol(callRecordDetailsTable, 10F, 11);
        addCol(callRecordDetailsTable, "Date").colspan(3).font(ITextFont.arial_bold_white_9).bgColor(titleBGColor).paddingTo("b", 5F).paddingTo("l", 4F).alignH("l").o();
        addCol(callRecordDetailsTable, "Phone Number").colspan(2).font(ITextFont.arial_bold_white_9).bgColor(titleBGColor).paddingTo("b", 4F).alignH("l").o();
        addCol(callRecordDetailsTable, "Destination").colspan(3).font(ITextFont.arial_bold_white_9).bgColor(titleBGColor).paddingTo("b", 4F).alignH("l").o();
        addCol(callRecordDetailsTable, "Duration").colspan(2).font(ITextFont.arial_bold_white_9).bgColor(titleBGColor).paddingTo("b", 4F).alignH("r").o();
        addCol(callRecordDetailsTable, "Sub Total").colspan(1).font(ITextFont.arial_bold_white_9).bgColor(titleBGColor).paddingTo("b", 4F).alignH("r").o();
        addEmptyCol(callRecordDetailsTable, 6F, 11);
        
        Double totalCallFee = 0d;
        for (CustomerCallRecord ccr : this.ccrs) {
            addCol(callRecordDetailsTable, TMUtils.retrieveMonthAbbrWithDate(ccr.getCharge_date_time())).colspan(3).font(ITextFont.arial_normal_8).paddingTo("r", 4F).alignH("l").o();
            addCol(callRecordDetailsTable, ccr.getPhone_called()).colspan(2).font(ITextFont.arial_normal_8).paddingTo("b", 4F).alignH("l").o();
            addCol(callRecordDetailsTable, ccr.getBilling_description()).colspan(3).font(ITextFont.arial_normal_8).paddingTo("b", 4F).alignH("l").o();
            addCol(callRecordDetailsTable, String.valueOf(ccr.getFormated_duration())).colspan(2).font(ITextFont.arial_normal_8).paddingTo("b", 4F).alignH("r").o();
            addCol(callRecordDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(ccr.getAmount_incl()))).colspan(1).font(ITextFont.arial_normal_8).paddingTo("b", 4F).alignH("r").o();
            totalCallFee += ccr.getAmount_incl();
		}
        addEmptyCol(callRecordDetailsTable, 20F, 11);
        
        // PRODUCT(S) END
        
        // #####SEPARATOR BEGIN
        addCol(callRecordDetailsTable, " ").colspan(11).fixedHeight(8F).border("b", 1F).o();
        // #####SEPARATOR END
        
        // TOTAL BEGIN
        // #####EMTRY SPACE BEGIN
        addEmptyCol(callRecordDetailsTable, 11);
        // #####EMTRY SPACE END
        
        // FIRST ROW BEGIN
        addEmptyCol(callRecordDetailsTable, 14F, 7);
        addCol(callRecordDetailsTable, "Total before GST").colspan(2).font(ITextFont.arial_normal_8).o();
        Double totalBeforeGST = totalCallFee/1.15;
        // fill decimal, keep 2 decimals
        addCol(callRecordDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(totalBeforeGST))).colspan(2).font(ITextFont.arial_normal_8).alignH("r").o();
        // FIRST ROW END
        // SECOND ROW BEGIN
        addEmptyCol(callRecordDetailsTable, 14F, 7);
        addCol(callRecordDetailsTable, "GST at 15%").colspan(2).font(ITextFont.arial_normal_8).o();
        Double totalGST = totalCallFee - totalCallFee/1.15;
        // fill decimal, keep 2 decimals
        addCol(callRecordDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(totalGST))).colspan(2).font(ITextFont.arial_normal_8).alignH("r").o();
        // SECOND ROW END
        
        // SEPARATOR BEGIN
        addEmptyCol(callRecordDetailsTable, 4F, 7);
        addCol(callRecordDetailsTable, " ").colspan(4).borderWidth("b", 1F).fixedHeight(4F).o();
        // SEPARATOR END
        
        // TOTAL AMOUNT BEGIN
        addEmptyCol(callRecordDetailsTable, 7);
        addCol(callRecordDetailsTable, "Invoice Total").colspan(2).font(ITextFont.arial_bold_8).o();
        addCol(callRecordDetailsTable, TMUtils.fillDecimalPeriod(String.valueOf(totalCallFee))).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        // TOTAL AMOUNT END
        
        
        return callRecordDetailsTable;
	}
	
	private void pageHeader(PdfWriter writer) throws MalformedURLException, IOException, DocumentException{
		Integer colspan = 15;
		PdfPTable headerTable = newTable().columns(colspan).totalWidth(510F).o();
		
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
		
		/*
		 * header table begin
		 */
        addEmptyCol(headerTable, 34F, colspan);
        addCol(headerTable, this.getCompanyDetail().getName()).colspan(colspan).font(ITextFont.arial_normal_8).alignH("r").o();
        addCol(headerTable, "PO Box 41547, St Lukes, Auckland 1346, New Zealand").colspan(colspan).font(ITextFont.arial_normal_8).alignH("r").o();
        addCol(headerTable, "Tel: "+this.getCompanyDetail().getTelephone()).colspan(colspan).font(ITextFont.arial_normal_8).alignH("r").o();
        addCol(headerTable, this.getCompanyDetail().getDomain()).colspan(colspan).font(ITextFont.arial_normal_8).alignH("r").o();
        addCol(headerTable, " ").colspan(colspan).borderColor("b", titleBGColor).borderWidth("b", 3F).o();
		/*
		 * header table end
		 */      
		/*
		 * invoice basics begin
		 */
        addEmptyCol(headerTable, 14F, colspan);
        addEmptyCol(headerTable, 4F, colspan-4);
        addCol(headerTable, "Customer Id: ").colspan(2).font(ITextFont.arial_bold_8).o();
        addCol(headerTable, this.getCustomer().getId().toString()).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        addEmptyCol(headerTable, 4F, colspan-4);
        addCol(headerTable, "Invoice No: ").colspan(2).font(ITextFont.arial_bold_8).o();
        addCol(headerTable, this.getCurrentCustomerInvoice().getId().toString()).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        addEmptyCol(headerTable, 4F, colspan-4);
        addCol(headerTable, "Date: ").colspan(2).font(ITextFont.arial_bold_8).o();
        addCol(headerTable, TMUtils.retrieveMonthAbbrWithDate(this.getCurrentCustomerInvoice().getCreate_date())).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        addEmptyCol(headerTable, colspan);

		/*
		 * invoice basics end
		 */
        // complete the table
        headerTable.completeRow();
        // write the table to an absolute position
        PdfContentByte paymentSlipTableCanvas = writer.getDirectContent();
        headerTable.writeSelectedRows(0, -1, 41, 810, paymentSlipTableCanvas);
	}
	
	private Boolean isCCRS(){
		return this.ccrs != null && this.ccrs.size() > 0;
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

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public List<CustomerCallRecord> getCcrs() {
		return ccrs;
	}

	public void setCcrs(List<CustomerCallRecord> ccrs) {
		this.ccrs = ccrs;
	}

	
}