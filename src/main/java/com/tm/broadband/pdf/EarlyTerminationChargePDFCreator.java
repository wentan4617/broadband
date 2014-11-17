package com.tm.broadband.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

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
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.itext.ITextFont;
import com.tm.broadband.util.itext.ITextUtils;

/** 
* Generates Invoice PDF
* 
* @author DONG CHEN
*/ 
public class EarlyTerminationChargePDFCreator extends ITextUtils {

	private CompanyDetail companyDetail;
	private CustomerOrder co;
	private EarlyTerminationCharge etc;

	private BaseColor titleBorderColor = new BaseColor(230,230,230);
	private BaseColor titleBGColor = new BaseColor(92,184,92);
	private BaseColor totleChequeAmountBGColor = new BaseColor(110,110,110);
	
	private String pdf_resources_path = TMUtils.createPath("broadband" + File.separator + "pdf_resources" + File.separator);

	private Integer globalBorderWidth = 0;
	
	public EarlyTerminationChargePDFCreator(){}
	
	public EarlyTerminationChargePDFCreator(CompanyDetail companyDetail
			,CustomerOrder co
			,EarlyTerminationCharge etc) {
		this.companyDetail = companyDetail;
		this.co = co;
		this.etc = etc;
	}

	public String create() throws DocumentException, MalformedURLException, IOException{
        Document document = new Document(PageSize.A4);
        
		// Output PDF Path, e.g.: early_termination_charge_60089.pdf
		String outputFile = TMUtils.createPath(
				"broadband" 
				+ File.separator
				+ "customers" + File.separator + this.co.getCustomer_id()
				+ File.separator + "early_termination_charge_" + this.getEtc().getId() + ".pdf");
		
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
        
        document.add(createEarlyTerminationChargeDetail());
        
        
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
	        addCol(paymentSlipTable, this.getCo().getCustomer_id().toString()).font(ITextFont.arial_normal_6).bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).paddingTo("t", 6F).indent(14F).o();
	        addCol(paymentSlipTable, this.etc.getId().toString()).font(ITextFont.arial_normal_7).bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).paddingTo("t", 6F).indent(14F).o();
	        addCol(paymentSlipTable, TMUtils.retrieveMonthAbbrWithDate(this.etc.getDue_date())).font(ITextFont.arial_normal_7).bgColor(new BaseColor(234,234,234)).paddingTo("t", 6F).indent(14F).o();
	        addCol(paymentSlipTable, " ").bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).o();
	        addCol(paymentSlipTable, " ").bgColor(new BaseColor(234,234,234)).borderColor(BaseColor.WHITE).border("r", 1F).o();
	        addCol(paymentSlipTable, " ").bgColor(new BaseColor(234,234,234)).o();
	        
	        // SEPARATOR BEGIN
	        addEmptyCol(paymentSlipTable, 2F, 5);
	        // SEPARATOR END
	
			
	        // SECOND SECTION
	        addCol(paymentSlipTable, "Paying By Direct Credit").colspan(2).font(ITextFont.arial_normal_8).indent(4F).o();
	        addCol(paymentSlipTable, "Total amount due before").rowspan(2).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).paddingTo("t", 8F).indent(14F).o();
	        addCol(paymentSlipTable, this.getEtc().getDue_date_str()).rowspan(2).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).paddingTo("t", 8F).indent(32F).o();
	        // input box begin
	        addCol(paymentSlipTable, TMUtils.fillDecimalPeriod(String.valueOf(this.etc.getCharge_amount()))).rowspan(2).font(ITextFont.arial_normal_8).bgColor(BaseColor.WHITE).borderColor(totleChequeAmountBGColor).borderZoom(8F).alignH("r").o();
	        // input box end
	        addCol(paymentSlipTable, "Bank: "+this.getCompanyDetail().getBank_name()).colspan(2).font(ITextFont.arial_normal_8).indent(4F).o();
	        addCol(paymentSlipTable, "Name of Account: "+this.getCompanyDetail().getBank_account_name()).colspan(2).font(ITextFont.arial_normal_8).indent(4F).o();
	        addCol(paymentSlipTable, "Total amount due after").rowspan(2).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).paddingTo("t", 8F).indent(14F).o();
	        addCol(paymentSlipTable, this.getEtc().getDue_date_str()).rowspan(2).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).paddingTo("t", 8F).indent(32F).o();
	        // input box begin
	        addCol(paymentSlipTable, TMUtils.fillDecimalPeriod(String.valueOf(this.etc.getTotal_payable_amount()))).rowspan(2).font(ITextFont.arial_normal_8).bgColor(BaseColor.WHITE).borderColor(totleChequeAmountBGColor).borderZoom(8F).alignH("r").o();
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
			addCol(paymentSlipTable, this.getCompanyDetail().getName()+"   " + this.getCompanyDetail().getBilling_address()).colspan(3).font(ITextFont.arial_normal_white_8).bgColor(totleChequeAmountBGColor).indent(4F).o();
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

		// CLOSE DOCUMENT
        document.close();
		return outputFile;
	}
	
	// BEGIN CUSTOMER BASIC INFO
	private PdfPTable createCustomerBasicInfo(){
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(102);
		// add common header
        addEmptyCol(headerTable, 140F, 1);
        
        String customerName = null;
//        String customerTitle = null;
        if("business".equals(this.getCo().getCustomer_type())){
        	customerName = this.getCo().getOrg_name();
//        	customerTitle = "BUSINESS";
        } else {
        	customerName = this.getCo().getTitle() != null ? this.getCo().getTitle().toUpperCase()+" " : "";
        	customerName += this.getCo().getFirst_name()+" "+this.getCo().getLast_name();
//        	customerTitle = "PERSONAL";
        }
//        addCol(headerTable, customerTitle + " USER").font(ITextFont.arial_bold_12).border(0).paddingTo("l", 50F).paddingTo("b", 10F).o();
        addCol(headerTable, customerName.trim()).font(ITextFont.arial_bold_9).paddingTo("l", 30F).paddingTo("b", 10F).border(0).o();
        String addressArr[] = this.getCo().getAddress().split(",");
        for (String address : addressArr) {
            addCol(headerTable, address.trim()).font(ITextFont.arial_bold_8).paddingTo("l", 30F).border(0).o();
		}
        addEmptyCol(headerTable, 40F, 1);
		return headerTable;
	}
	// END CUSTOMER BASIC INFO
	
	
	// BEGIN EARLY TERMINATION CHARGE DETAIL
	private PdfPTable createEarlyTerminationChargeDetail(){
        PdfPTable chargeDetailTable = new PdfPTable(15);
        chargeDetailTable.setWidthPercentage(96);
        addCol(chargeDetailTable, "Recent Invoice Detail").colspan(15).bgColor(titleBGColor).font(ITextFont.arial_bold_white_10).paddingV(4F).paddingTo("l", 10F).o();
        addEmptyCol(chargeDetailTable, 10F, 15);
        addCol(chargeDetailTable, "Service Given Date").colspan(3).font(ITextFont.arial_bold_8).alignH("c").o();
        addCol(chargeDetailTable, "Service Terminated Date").colspan(3).font(ITextFont.arial_bold_8).alignH("c").o();
        addCol(chargeDetailTable, "Description").colspan(3).font(ITextFont.arial_bold_8).alignH("c").o();
        addCol(chargeDetailTable, "Charge Amount").colspan(3).font(ITextFont.arial_bold_8).alignH("r").o();
        addCol(chargeDetailTable, "Total Payable Amount").colspan(3).font(ITextFont.arial_bold_8).alignH("r").o();
        addEmptyCol(chargeDetailTable, 6F, 15);
        addLineCol(chargeDetailTable, 15, titleBorderColor, 1F);
        addEmptyCol(chargeDetailTable, 6F, 15);
        addCol(chargeDetailTable, TMUtils.retrieveMonthAbbrWithDate(this.getEtc().getService_given_date())).colspan(3).font(ITextFont.arial_normal_8).alignH("c").o();
        addCol(chargeDetailTable, TMUtils.retrieveMonthAbbrWithDate(this.getEtc().getTermination_date())).colspan(3).font(ITextFont.arial_normal_8).alignH("c").o();
        addCol(chargeDetailTable, "early termination detail").colspan(3).font(ITextFont.arial_normal_8).alignH("c").o();
        addCol(chargeDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(this.getEtc().getCharge_amount()))).colspan(3).font(ITextFont.arial_bold_8).alignH("r").o();
        addCol(chargeDetailTable, "").colspan(3).font(ITextFont.arial_bold_8).alignH("r").o();
        
        addEmptyCol(chargeDetailTable, 10F, 15);
        addLineCol(chargeDetailTable, 15, titleBorderColor, 1F);
        addEmptyCol(chargeDetailTable, 6F, 15);
        addCol(chargeDetailTable, "Total amount due defore " + TMUtils.retrieveMonthAbbrWithDate(this.getEtc().getDue_date()) + " :").colspan(12).font(ITextFont.arial_normal_8).alignH("r").o();
        addCol(chargeDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(this.getEtc().getCharge_amount())) + " (inc GST)").colspan(3).font(ITextFont.arial_bold_8).alignH("r").o();
        addCol(chargeDetailTable, "Total amount due after " + TMUtils.retrieveMonthAbbrWithDate(this.getEtc().getDue_date()) + " :").colspan(12).font(ITextFont.arial_normal_8).alignH("r").o();
        addCol(chargeDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(this.getEtc().getTotal_payable_amount())) + " (inc GST)").colspan(3).font(ITextFont.arial_bold_8).alignH("r").o();
        
        addEmptyCol(chargeDetailTable, 10F, 15);
        addCol(chargeDetailTable, "Plan Detail").colspan(15).bgColor(titleBGColor).font(ITextFont.arial_bold_white_10).paddingV(4F).paddingTo("l", 10F).o();
        addEmptyCol(chargeDetailTable, 10F, 15);
        addCol(chargeDetailTable, "Plan End Date").colspan(3).font(ITextFont.arial_bold_8).alignH("c").o();
        addCol(chargeDetailTable, "").colspan(12).o();
        addEmptyCol(chargeDetailTable, 6F, 15);
        addLineCol(chargeDetailTable, 15, titleBorderColor, 1F);
        addEmptyCol(chargeDetailTable, 6F, 15);
        addCol(chargeDetailTable, TMUtils.retrieveMonthAbbrWithDate(this.getEtc().getLegal_termination_date())).colspan(3).font(ITextFont.arial_normal_8).alignH("c").o();
        addCol(chargeDetailTable, "").colspan(12).o();
        
		return chargeDetailTable;
	}
    // END EARLY TERMINATION CHARGE DETAIL

	
	
	private void pageHeader(PdfWriter writer) throws MalformedURLException, IOException, DocumentException{
		Integer colspan = 15;
		PdfPTable headerTable = newTable().columns(colspan).totalWidth(510F).o();
		
        // logo & start
		Image logo = null;
        File invoiceLogoFile = new File(pdf_resources_path + "invoice_company_logo.png");
		if(invoiceLogoFile.exists()){
			logo = Image.getInstance(pdf_resources_path + "invoice_company_logo.png");
        } else {
    		logo = Image.getInstance("pdf"+File.separator+"img"+File.separator+"logo_top_final.png");
        }
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
        addCol(headerTable, this.getCompanyDetail().getBilling_address()).colspan(colspan).font(ITextFont.arial_normal_8).alignH("r").o();
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
        addCol(headerTable, this.getCo().getCustomer_id().toString()).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        addEmptyCol(headerTable, 4F, colspan-4);
        addCol(headerTable, "Invoice No: ").colspan(2).font(ITextFont.arial_bold_8).o();
        addCol(headerTable, this.getEtc().getId().toString()).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        addEmptyCol(headerTable, 4F, colspan-4);
        addCol(headerTable, "Date: ").colspan(2).font(ITextFont.arial_bold_8).o();
        addCol(headerTable, TMUtils.retrieveMonthAbbrWithDate(this.getEtc().getCreate_date())).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
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
	

	public CompanyDetail getCompanyDetail() {
		return companyDetail;
	}

	public void setCompanyDetail(CompanyDetail companyDetail) {
		this.companyDetail = companyDetail;
	}

	public CustomerOrder getCo() {
		return co;
	}

	public void setCo(CustomerOrder co) {
		this.co = co;
	}

	public EarlyTerminationCharge getEtc() {
		return etc;
	}

	public void setEtc(EarlyTerminationCharge etc) {
		this.etc = etc;
	}

	
}