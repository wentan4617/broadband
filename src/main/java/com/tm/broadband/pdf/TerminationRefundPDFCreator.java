package com.tm.broadband.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

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
import com.tm.broadband.model.TerminationRefund;
import com.tm.broadband.model.User;
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
	private CustomerOrder co;
	private TerminationRefund etr;
	private User u;
	
	private String pdf_resources_path = TMUtils.createPath("broadband" + File.separator + "pdf_resources" + File.separator);

	private BaseColor titleBorderColor = new BaseColor(230,230,230);
	private BaseColor titleBGColor = new BaseColor(92,184,92);
	
	private Integer globalBorderWidth = 0;
	
	public TerminationRefundPDFCreator(){}
	
	public TerminationRefundPDFCreator(CompanyDetail cd
			,CustomerOrder co
			,TerminationRefund etr
			,User u) {
		this.cd = cd;
		this.co = co;
		this.etr = etr;
		this.u = u;
	}

	public String create() throws DocumentException, MalformedURLException, IOException{
        Document document = new Document(PageSize.A4);
        
		// Output PDF Path, e.g.: early_termination_charge_300001.pdf
		String outputFile = TMUtils.createPath(
				"broadband" 
				+ File.separator
				+ "customers" + File.separator + this.getCo().getId()
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
        
        document.add(createTerminationRefundDetail());
        
        document.add(createExecutorDetail());
        
        

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
        addEmptyCol(headerTable, 200F, 1);
        
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
	
	
	// BEGIN TERMINATION REFUND DETAIL
	private PdfPTable createTerminationRefundDetail(){
        PdfPTable refundDetailTable = new PdfPTable(16);
        refundDetailTable.setWidthPercentage(96);
        addCol(refundDetailTable, "TERMINATION REFUND DETAIL").colspan(16).bgColor(titleBGColor).font(ITextFont.arial_bold_white_10).paddingV(4F).paddingTo("l", 10F).o();
        addEmptyCol(refundDetailTable, 10F, 16);
        addCol(refundDetailTable, "REMAINING DAYS REFUND ACCORDING TO: ").colspan(6).font(ITextFont.arial_bold_8).paddingV(4F).paddingTo("l", 10F).o();
        addCol(refundDetailTable, this.etr.getProduct_name()).colspan(10).font(ITextFont.arial_normal_8).paddingV(4F).paddingTo("l", 10F).o();
        addEmptyCol(refundDetailTable, 6F, 16);
        addLineCol(refundDetailTable, 16, titleBorderColor, 1F);
        addEmptyCol(refundDetailTable, 6F, 16);
        addCol(refundDetailTable, "Last Date Of Month").colspan(3).font(ITextFont.arial_bold_8).alignH("c").o();
        addCol(refundDetailTable, "Terminated Date").colspan(3).font(ITextFont.arial_bold_8).alignH("c").o();
        addCol(refundDetailTable, "Day(s) Between").colspan(3).font(ITextFont.arial_bold_8).alignH("c").o();
        addCol(refundDetailTable, "Bank Account No").colspan(3).font(ITextFont.arial_bold_8).alignH("c").o();
        addCol(refundDetailTable, "Account Name").colspan(2).font(ITextFont.arial_bold_8).alignH("c").o();
        addCol(refundDetailTable, "Amount").colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        
        addEmptyCol(refundDetailTable, 10F, 16);
        addLineCol(refundDetailTable, 16, titleBorderColor, 1F);
        addEmptyCol(refundDetailTable, 6F, 16);

        addCol(refundDetailTable, TMUtils.retrieveMonthAbbrWithDate(TMUtils.getLastDateOfMonth(this.getEtr().getTermination_date()))).colspan(3).font(ITextFont.arial_normal_7).alignH("c").o();
        addCol(refundDetailTable, TMUtils.retrieveMonthAbbrWithDate(this.getEtr().getTermination_date())).colspan(3).font(ITextFont.arial_normal_7).alignH("c").o();
        addCol(refundDetailTable, String.valueOf(this.getEtr().getDays_between_end_last())).colspan(3).font(ITextFont.arial_normal_7).alignH("c").o();
        addCol(refundDetailTable, this.getEtr().getRefund_bank_account_number()).colspan(3).font(ITextFont.arial_normal_7).alignH("c").o();
        addCol(refundDetailTable, this.getEtr().getRefund_bank_account_name()).colspan(2).font(ITextFont.arial_normal_7).alignH("c").o();
        addCol(refundDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(this.getEtr().getRefund_amount()))).colspan(2).font(ITextFont.arial_normal_7).alignH("r").o();

        addEmptyCol(refundDetailTable, 300F, 16);
		return refundDetailTable;
	}
    // END TERMINATION REFUND DETAIL
	
	
	// BEGIN EXECUTOR DETAIL
	private PdfPTable createExecutorDetail(){
        PdfPTable executorDetailTable = new PdfPTable(15);
        executorDetailTable.setWidthPercentage(96);

        addCol(executorDetailTable, "Executor Id:").colspan(12).font(ITextFont.arial_bold_9).alignH("r").o();
        addCol(executorDetailTable, String.valueOf(this.getU().getId())).colspan(3).font(ITextFont.arial_bold_9).alignH("r").o();
        addCol(executorDetailTable, "Executor Role:").colspan(12).font(ITextFont.arial_bold_9).alignH("r").o();
        addCol(executorDetailTable, this.getU().getUser_role()).colspan(3).font(ITextFont.arial_bold_9).alignH("r").o();
        addCol(executorDetailTable, "Executed Date:").colspan(12).font(ITextFont.arial_bold_9).alignH("r").o();
        addCol(executorDetailTable, TMUtils.retrieveMonthAbbrWithDate(new Date())).colspan(3).font(ITextFont.arial_bold_9).alignH("r").o();
        
		return executorDetailTable;
	}
    // END EXECUTOR DETAIL

	
	
	private void pageHeader(PdfWriter writer) throws MalformedURLException, IOException, DocumentException{
		Integer colspan = 15;
		PdfPTable headerTable = newTable().columns(colspan).totalWidth(510F).o();
		
        // logo & start
        File invoiceLogoFile = new File(pdf_resources_path + "invoice_company_logo.png");
        Image logo = null;
		if(invoiceLogoFile.exists()){
			logo = Image.getInstance(pdf_resources_path + "invoice_company_logo.png");
        } else {
    		logo = Image.getInstance("pdf"+File.separator+"img"+File.separator+"logo_top_final.png");
        }
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
        addCol(headerTable, this.getCo().getCustomer_id().toString()).colspan(2).font(ITextFont.arial_bold_8).alignH("r").o();
        addEmptyCol(headerTable, 4F, colspan-4);
        addCol(headerTable, "Refund No: ").colspan(2).font(ITextFont.arial_bold_8).o();
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

	public TerminationRefund getEtr() {
		return etr;
	}

	public void setEtr(TerminationRefund etr) {
		this.etr = etr;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public CustomerOrder getCo() {
		return co;
	}

	public void setCo(CustomerOrder co) {
		this.co = co;
	}

	
}