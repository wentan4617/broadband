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
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.EarlyTerminationRefund;
import com.tm.broadband.model.Organization;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.itext.ITextFont;
import com.tm.broadband.util.itext.ITextUtils;

/** 
* Generates Invoice PDF
* 
* @author DONG CHEN
*/ 
public class TerminationRefundPDFCreator extends ITextUtils {

	private CompanyDetail cd;
    private Customer c;
	private Organization org;
	private EarlyTerminationRefund etr;

	private BaseColor titleBorderColor = new BaseColor(230,230,230);
	private BaseColor titleBGColor = new BaseColor(92,184,92);
	
	private Integer globalBorderWidth = 0;
	
	public TerminationRefundPDFCreator(){}
	
	public TerminationRefundPDFCreator(CompanyDetail cd
			,Customer c
			,Organization org
			,EarlyTerminationRefund etr) {
		this.cd = cd;
		this.c = c;
		this.org = org;
		this.etr = etr;
	}

	public String create() throws DocumentException, MalformedURLException, IOException{
        Document document = new Document(PageSize.A4);
        
		// Output PDF Path, e.g.: early_termination_charge_300001.pdf
		String outputFile = TMUtils.createPath(
				"broadband" 
				+ File.separator
				+ "customers" + File.separator + this.getC().getId()
				+ File.separator + "termination_refund_" + this.getEtr().getId() + ".pdf");
		
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
        
        document.add(createEarlyTerminationRefundDetail());
        
        

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
        if("business".equals(this.getC().getCustomer_type())){
        	customerName = org.getOrg_name();
//        	customerTitle = "BUSINESS";
        } else {
        	customerName = this.getC().getTitle() != null ? this.getC().getTitle()+" " : "";
        	customerName += this.getC().getFirst_name()+" "+this.getC().getLast_name();
//        	customerTitle = "PERSONAL";
        }
//        addCol(headerTable, customerTitle + " USER").font(ITextFont.arial_bold_12).border(0).paddingTo("l", 50F).paddingTo("b", 10F).o();
        addCol(headerTable, customerName.trim()).font(ITextFont.arial_bold_9).paddingTo("l", 30F).paddingTo("b", 10F).border(0).o();
        String addressArr[] = this.getC().getAddress().split(",");
        for (String address : addressArr) {
            addCol(headerTable, address.trim()).font(ITextFont.arial_bold_8).paddingTo("l", 30F).border(0).o();
		}
        addEmptyCol(headerTable, 40F, 1);
		return headerTable;
	}
	// END CUSTOMER BASIC INFO
	
	
	// BEGIN EARLY TERMINATION REFUND DETAIL
	private PdfPTable createEarlyTerminationRefundDetail(){
        PdfPTable refundDetailTable = new PdfPTable(15);
        refundDetailTable.setWidthPercentage(96);
        addCol(refundDetailTable, "TERMINATION REFUND DETAIL").colspan(15).bgColor(titleBGColor).font(ITextFont.arial_bold_white_10).paddingV(4F).paddingTo("l", 10F).o();
        addEmptyCol(refundDetailTable, 10F, 15);
        addCol(refundDetailTable, "Last Date Of Month").colspan(3).font(ITextFont.arial_bold_9).alignH("c").o();
        addCol(refundDetailTable, "Terminated Date").colspan(3).font(ITextFont.arial_bold_9).alignH("c").o();
        addCol(refundDetailTable, "Refund Bank Account Number").colspan(3).font(ITextFont.arial_bold_9).alignH("c").o();
        addCol(refundDetailTable, "Refund Bank Account Name").colspan(3).font(ITextFont.arial_bold_9).alignH("c").o();
        addCol(refundDetailTable, "Refund Amount").colspan(3).font(ITextFont.arial_bold_9).alignH("c").o();
        
        addEmptyCol(refundDetailTable, 10F, 15);
        addLineCol(refundDetailTable, 15, titleBorderColor, 1F);
        addEmptyCol(refundDetailTable, 6F, 15);

        addCol(refundDetailTable, TMUtils.retrieveMonthAbbrWithDate(TMUtils.getLastDateOfMonth(this.getEtr().getTermination_date()))).colspan(3).font(ITextFont.arial_normal_8).alignH("c").o();
        addCol(refundDetailTable, TMUtils.retrieveMonthAbbrWithDate(this.getEtr().getTermination_date())).colspan(3).font(ITextFont.arial_normal_8).alignH("c").o();
        addCol(refundDetailTable, this.getEtr().getRefund_bank_account_number()).colspan(3).font(ITextFont.arial_normal_8).alignH("c").o();
        addCol(refundDetailTable, this.getEtr().getRefund_bank_account_name()).colspan(3).font(ITextFont.arial_normal_8).alignH("c").o();
        addCol(refundDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(this.getEtr().getRefund_amount()))).colspan(3).font(ITextFont.arial_normal_8).alignH("c").o();
        
		return refundDetailTable;
	}
    // END EARLY TERMINATION REFUND DETAIL

	
	
	private void pageHeader(PdfWriter writer) throws MalformedURLException, IOException, DocumentException{
		Integer colspan = 15;
		PdfPTable headerTable = newTable().columns(colspan).totalWidth(510F).o();
		
        // logo & start
		Image logo = Image.getInstance("pdf"+File.separator+"img"+File.separator+"logo_top_final.png");
		logo.scaleAbsolute(171f, 45f);
		logo.setAbsolutePosition(44, 760);
		writer.getDirectContent().addImage(logo);
		
		Phrase t1 = new Phrase("Statement / Tax Invoice", ITextFont.arial_normal_14);
		Phrase t2 = new Phrase("GST Registration Number: "+this.getCd().getGst_registration_number(), ITextFont.lucida_sans_unicode_9);
		PdfContentByte canvas = writer.getDirectContentUnder();
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, t1, 44, 744, 0);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, t2, 44, 730, 0);
		
		/*
		 * header table begin
		 */
        addEmptyCol(headerTable, 34F, colspan);
        addCol(headerTable, this.getCd().getName()).colspan(colspan).font(ITextFont.arial_normal_8).alignH("r").o();
        addCol(headerTable, this.getCd().getBilling_address()).colspan(colspan).font(ITextFont.arial_normal_8).alignH("r").o();
        addCol(headerTable, "Tel: "+this.getCd().getTelephone()).colspan(colspan).font(ITextFont.arial_normal_8).alignH("r").o();
        addCol(headerTable, this.getCd().getDomain()).colspan(colspan).font(ITextFont.arial_normal_8).alignH("r").o();
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
        addCol(headerTable, this.getC().getId().toString()).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        addEmptyCol(headerTable, 4F, colspan-4);
        addCol(headerTable, "Invoice No: ").colspan(2).font(ITextFont.arial_bold_8).o();
        addCol(headerTable, this.getEtr().getId().toString()).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        addEmptyCol(headerTable, 4F, colspan-4);
        addCol(headerTable, "Date: ").colspan(2).font(ITextFont.arial_bold_8).o();
        addCol(headerTable, TMUtils.retrieveMonthAbbrWithDate(this.getEtr().getCreate_date())).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
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
	


	public CompanyDetail getCd() {
		return cd;
	}

	public void setCd(CompanyDetail cd) {
		this.cd = cd;
	}

	public Customer getC() {
		return c;
	}

	public void setC(Customer c) {
		this.c = c;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public EarlyTerminationRefund getEtr() {
		return etr;
	}

	public void setEtr(EarlyTerminationRefund etr) {
		this.etr = etr;
	}

	
}