package com.tm.broadband.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.itext.ITextFont;
import com.tm.broadband.util.itext.ITextUtils;

/** 
* Generates Order PDF
* 
* @author DONG CHEN
*/ 
public class ApplicationPDFCreator extends ITextUtils {
	
	private CustomerOrder co;
	
	private BaseColor titleBGColor = new BaseColor(220,221,221);
	private BaseColor titleBorderColor = new BaseColor(159,159,159);
	private BaseColor tableBorderColor = new BaseColor(188,189,192);
	private Float tableBorderWidth = 4F;
	
	private String pdf_resources_path = TMUtils.createPath("broadband" + File.separator + "pdf_resources" + File.separator);

	// BEGIN Temporary Variables
	// BEGIN Currency Related Variables
	private Double totalPrice = 0d;
	private Double beforeGSTPrice = 0d;
	private Double gst = 0d;
	// 15% GST as default
	private String gstRate = "1.15";
	private String gstRate2 = "0.15";
	private String personalGST = "15%";
	private String businessGST = "15%";
	// END Currency Related Variables
	
	// BEGIN Order Detail Differentiations Variables
	List<CustomerOrderDetail> codPlans = new ArrayList<CustomerOrderDetail>();
	List<CustomerOrderDetail> codAddOns = new ArrayList<CustomerOrderDetail>();
	// END Order Detail Differentiations Variables
	// END Temporary Variables
	
	public ApplicationPDFCreator(){}
	
	public ApplicationPDFCreator(CustomerOrder co){
		this.co = co;
	}
	
	public String create() throws DocumentException, IOException{
		
		// DIFFERENTIATES ORDER DETAILS
		if(this.getCo().getCustomerOrderDetails().size()>0){
			List<CustomerOrderDetail> cods = this.getCo().getCustomerOrderDetails();
			for (CustomerOrderDetail  cod: cods) {
				if(cod.getDetail_type()!= null && cod.getDetail_type().contains("plan-term")){
					// SAVE PLAN
					codPlans.add(cod);
				} else {
					if(!cod.getDetail_type().contains("discount")){
						// SAVE ADD ON NOT INCLUDE DISCOUNT
						codAddOns.add(cod);
					}
				}
			}
		}
		
        Document document = new Document(PageSize.A4);
		
        // BEGIN If Merge PDF First Part
//		// final PDF
//		String outputFile = TMUtils.createPath("broadband" + File.separator
//				+ "customers" + File.separator + customer.getId()
//				+ File.separator + "Order-" + customerOrder.getId()
//				+ ".pdf");
		
//		// temp PDF
//        String inputFile = TMUtils.createPath("broadband" + File.separator
//				+ "customers" + File.separator + "useless" + File.separator 
//				+ "Order-" + customerOrder.getId()
//				+ ".pdf");
        
//		// term&condition PDF
//        String term = TMUtils.createPath("broadband" + File.separator
//				+ "customers" + File.separator + "pdf" + File.separator 
//				+ "term_and_condition.pdf");

//      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(inputFile));
        // END If Merge PDF First Part
        
		// Output PDF Path, e.g.: application_600089.pdf
		String outputFile = TMUtils.createPath("broadband" + File.separator
				+ "customers" + File.separator + this.getCo().getCustomer_id()
				+ File.separator + "application_" + this.getCo().getId()
				+ ".pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        
        // OPEN DOCUMENT
        document.open();
        
    	// company_logo
        File companyLogoFile = new File(pdf_resources_path + "common_company_logo.png");
		if(companyLogoFile.exists()){
	    	addImage(writer, pdf_resources_path + "common_company_logo.png", 108.75F, 51.00000000000001F, 32F, 762F);
        } else {
        	addImage(writer, "pdf"+File.separator+"img"+File.separator+"company_logo.png", 108.75F, 51.00000000000001F, 32F, 762F);
        }
    	
        // set order PDF title table
		document.add(createOrderPDFTitleTable());
    	
        // set customer(personal or business) basic info table
		document.add(createCustomerOrderBasicInfoTable());
		
        // set customer(personal or business) info table
		document.add(createCustomerInfoTable(writer, document));
		
		// if order broadband type is transition
		if(this.getCo().getOrder_broadband_type()!=null && this.getCo().getOrder_broadband_type().equals("transition")){
			document.add(createTransitionInfoTable());
		}
		
		// set customer agreement
		document.add(createCustomerAgreementTable());
        
        // set order detail table
		document.add(createOrderDetailTable());
		
		// set FIRST PAGE bottom texts
		addText(writer, "Print from www.cyberpark.co.nz", ITextFont.arial_normal_10, 190F, 26F);
		addText(writer, "0800 2 CYBER (29237)", ITextFont.arial_normal_10, 360F, 26F);
    	// two-dimensional_code
        File twoDimensionalCodeFile = new File(pdf_resources_path + "two_dimensional_code.png");
		if(twoDimensionalCodeFile.exists()){
	    	addImage(writer, pdf_resources_path + "two_dimensional_code.png", 82.50000000000001F, 82.50000000000001F, 486F, 24F);
        } else {
        	addImage(writer, "pdf"+File.separator+"img"+File.separator+"two-dimensional_code.png", 82.50000000000001F, 82.50000000000001F, 486F, 24F);
        }
		
		// second page
        document.newPage();
        
    	// company_logo_empty_bar
        File companyLogoEmptyBarFile = new File(pdf_resources_path + "company_lg_customer_service_bar.png");
		if(companyLogoEmptyBarFile.exists()){
	    	addImage(writer, pdf_resources_path + "company_lg_customer_service_bar.png", 266.2500000000001F, 40.50000000000001F, 18F, 774F);
        } else {
        	addImage(writer, "pdf"+File.separator+"img"+File.separator+"company_logo_empty_bar.png", 266.2500000000001F, 40.50000000000001F, 18F, 774F);
        }
    	
    	// company_logo SMALLER
		if(companyLogoFile.exists()){
	    	addImage(writer, pdf_resources_path + "common_company_logo.png", 51.75000000000001F, 23.25000000000001F, 490F, 46F);
        } else {
        	addImage(writer, "pdf"+File.separator+"img"+File.separator+"company_logo.png", 51.75000000000001F, 23.25000000000001F, 490F, 46F);
        }
        
        // set term and authorization table
		document.add(createTermAndAuthorisationTable(writer));
		
		// set SECOND PAGE bottom texts
		addText(writer, "*Authorised & Powered by", ITextFont.arial_normal_8, 390F, 54F);
		addText(writer, "2014", ITextFont.arial_normal_10, 550F, 54F);
        
		// CLOSE DOCUMENT
        document.close();
		// CLOSE WRITER
        writer.close();

        // BEGIN If Merge PDF Second Part
//        mergePDF(outputFile, inputFile, term);
        // END If Merge PDF Second Part
        
        return outputFile;
	}
	
	public PdfPTable createOrderPDFTitleTable(){
		
        PdfPTable orderPDFTitleTable = newTable().columns(14).widthPercentage(102F).o();
    	
    	// BEGIN ORDER CONFIRMATION AREA PADDING TOP
        addEmptyCol(orderPDFTitleTable, 1F, 14);
    	// END ORDER CONFIRMATION AREA PADDING TOP

        // BEGIN ORDER CONFIRMATION TITLE
        addPDFTitle(orderPDFTitleTable, "BROADBAND APPLICATION", ITextFont.arial_colored_bold_23, 4F, 0, PdfPCell.ALIGN_RIGHT, 14);
        addPDFTitle(orderPDFTitleTable, "CONFIRMATION", ITextFont.arial_colored_bold_23, 22F, PdfPCell.BOTTOM, PdfPCell.ALIGN_RIGHT, 14);
        // END ORDER CONFIRMATION TITLE
        
		return orderPDFTitleTable;
	}
	
	/**
	 * CUSTOMER BASIC 
	 * @return
	 */
	public PdfPTable createCustomerOrderBasicInfoTable(){
		
        PdfPTable customerBasicInfoTable = newTable().columns(14).widthPercentage(102F).o();
    	
    	// BEGIN CUSTOMER BASIC INFO PADDING TOP
        addEmptyCol(customerBasicInfoTable, 6F, 14);
    	// END CUSTOMER BASIC INFO PADDING TOP
        
        // BEGIN CUSTOMER BASIC INFORMATION
        if(this.getCo().getCustomer_type().equals("personal")){
        	
        	// BEGIN PERSONAL BASIC INFORMATION
            addCol(customerBasicInfoTable, (this.getCo().getTitle()!=null?this.getCo().getTitle()+" ":"")+this.getCo().getFirst_name()+" "+this.getCo().getLast_name(), 10, 10F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
            // END PERSONAL BASIC INFORMATION
            
        } else if(this.getCo().getCustomer_type().equals("business")){
        	
        	// BEGIN BUSINESS BASIC INFORMATION
            addCol(customerBasicInfoTable, this.getCo().getOrg_name(), 10, 10F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        	// END BUSINESS BASIC INFORMATION
            
        }
        addCol(customerBasicInfoTable, "No.", 2, 0F, ITextFont.arial_colored_bold_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, String.valueOf(this.getCo().getId()), 2, 0F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, this.getCo().getAddress(), 10, 10F, ITextFont.arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, "Order Date", 2, 0F, ITextFont.arial_colored_bold_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, TMUtils.dateFormatYYYYMMDD(this.getCo().getOrder_create_date()), 2, 0F, ITextFont.arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        // END CUSTOMER BASIC INFORMATION
    	
    	// BEGIN CUSTOMER BASIC INFO PADDING BOTTOM
        addEmptyCol(customerBasicInfoTable, 6F, 14);
    	// END CUSTOMER BASIC INFO PADDING BOTTOM

        
        return customerBasicInfoTable;
	}
	
	/**
	 * CUSTOMER DETAIL INFO
	 * @param writer
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
    public PdfPTable createCustomerInfoTable(PdfWriter writer, Document doc) throws MalformedURLException, IOException, DocumentException {
        
        PdfPTable customerInfoTable = newTable().columns(10).widthPercentage(102F).o();
        
        // BEGIN CUSTOMER INFO TITLE BAR
        if(this.getCo().getCustomer_type()!=null && this.getCo().getCustomer_type().equals("personal")){
        	addTitleBar(customerInfoTable, "PERSONAL INFORMATION", ITextFont.arial_bold_12, titleBGColor, titleBorderColor, 10, 4F);
        } else if(this.getCo().getCustomer_type().equals("business")){
        	addTitleBar(customerInfoTable, "BUSINESS INFORMATION", ITextFont.arial_bold_12, titleBGColor, titleBorderColor, 10, 4F);
        }
        // END CUSTOMER INFO TITLE BAR
    	
    	// BEGIN CUSTOMER INFO PADDING TOP
        addEmptyCol(customerInfoTable, 2F, 10);
    	// END CUSTOMER INFO PADDING TOP

        // BEGIN PARAMETERS
        Float labelIndent = 8F;
        Float contentIndent = 30F;
        Float rowPaddingTop = 3F;
        Float rowPaddingBottom = 0F;
        // END PARAMETERS
        
        // BEGIN CUSTOMER(PERSONAL OR BUSINESS) INFO ROWS
        if(this.getCo().getCustomer_type().equals("personal")){
        	
            // BEGIN PERSONAL INFO ROWS
        	addCol(customerInfoTable, "Phone", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getPhone(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Mobile", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getMobile(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Email", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getEmail(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
            // END PERSONAL INFO ROWS
            
        } else if(this.getCo().getCustomer_type().equals("business")){
        	
            // BEGIN BUSINESS INFO ROWS
        	addCol(customerInfoTable, "Organization Name", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getOrg_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Organization Type", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getOrg_type(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Trading Name", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getOrg_trading_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Registration No.", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getOrg_register_no(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Date Incoporated", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, TMUtils.dateFormatYYYYMMDD(this.getCo().getOrg_incoporate_date()), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);

        	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
            addEmptyCol(customerInfoTable, 8F, 10);
        	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
            
        	// BEGIN HOLDER INFO ROWS
        	addTitleBar(customerInfoTable, "ACCOUNT HOLDER INFORMATION", ITextFont.arial_bold_10, titleBGColor, titleBorderColor, 10, 0F);

        	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
            addEmptyCol(customerInfoTable, 2F, 10);
        	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
            
        	addCol(customerInfoTable, "Full name", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getHolder_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Job title", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getHolder_job_title(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Phone", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getHolder_phone(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Email", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCo().getHolder_email(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	// BEGIN HOLDER INFO ROWS
            
            // END BUSINESS INFO ROWS
            
        }
        // END CUSTOMER(PERSONAL OR BUSINESS) INFO ROWS

    	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
        addEmptyCol(customerInfoTable, 8F, 10);
    	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
        
        return customerInfoTable;
    }
	
    /**
     * TRANSITION INFO
     * @return
     */
	public PdfPTable createTransitionInfoTable(){
		
        PdfPTable orderPDFTitleTable = newTable().columns(10).widthPercentage(102F).o();
        
        // BEGIN PARAMETERS
        Float labelIndent = 8F;
        Float contentIndent = 30F;
        Float rowPaddingTop = 3F;
        Float rowPaddingBottom = 0F;
        // END PARAMETERS

        // BEGIN TRANSITION INFO ROWS
    	addTitleBar(orderPDFTitleTable, "TRANSITION", ITextFont.arial_bold_10, titleBGColor, titleBorderColor, 10, 0F);
    	
    	// BEGIN TRANSITION PADDING TOP
        addEmptyCol(orderPDFTitleTable, 2F, 10);
    	// END TRANSITION PADDING TOP
        
    	addCol(orderPDFTitleTable, "Your Current Provider Name", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCo().getTransition_provider_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Account Holder Name", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCo().getTransition_account_holder_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Your Current Account Number", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCo().getTransition_account_number(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Your Porting Number", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCo().getTransition_porting_number(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        // END TRANSITION INFO ROWS

    	// BEGIN DISTANCE BETWEEN CUSTOMER AGREEMENT TABLE TITLE AND ROW
        addEmptyCol(orderPDFTitleTable, 6F, 10);
    	// END DISTANCE BETWEEN CUSTOMER AGREEMENT TABLE TITLE AND ROW
        
        return orderPDFTitleTable;
	}
	
	/**
	 * CUSTOMER FAVOR
	 * @return
	 */
	public PdfPTable createCustomerAgreementTable(){
		
        PdfPTable table = newTable().columns(10).widthPercentage(102F).o();
    	
    	// BEGIN CUSTOMER AGREEMENT PADDING TOP
        addEmptyCol(table, 10F, 10);
    	// END CUSTOMER AGREEMENT PADDING TOP
        
        // BEGIN PARAMETERS
        Float indent = 6F;
        Float rowPaddingTop = 0F;
        Float rowPaddingBottom = 0F;
        // END PARAMETERS
        
        addCol(table, "I’d like to be kept informed, by various means including electronic messages, of our special offers, deals and", 10, indent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        addCol(table, "important information on products and services.", 10, indent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);

    	// BEGIN CUSTOMER AGREEMENT AREA ENDING PADDING BOTTOM
        addEmptyCol(table, 10F, 10);
    	// END CUSTOMER AGREEMENT AREA ENDING PADDING BOTTOM
        
		return table;
	}
    
    public PdfPTable createOrderDetailTable(){
        PdfPTable orderDetailTable = newTable().columns(10).widthPercentage(102F).o();
        
        // BEGIN TITLE BAR
        addTitleBar(orderDetailTable, "ORDER DETAIL LIST", ITextFont.arial_bold_12, titleBGColor, titleBorderColor, 10, 4F);
        // END TITLE BAR
        
    	// BEGIN DISTANCE TO TOP
        addEmptyCol(orderDetailTable, 6F, 10);
    	// END DISTANCE TO TOP

        /**
         * BEGIN DETAIL
         */
        // BEGIN INITIAL PARAMETERS
        Float firstColIndent = 8F;
        Float titlePaddingTop = 2F;
        Float titlePaddingBottom = 2F;
        Float contentPaddingTop = 6F;
        Float contentPaddingBottom = 14F;
        BaseColor borderColor = new BaseColor(159,159,159);
        // END INITIAL PARAMETERS

        /**
         * BEGIN PLAN LIST
         */
        // BEGIN PLAN ROW HEADER
        addCol(orderDetailTable, "PLAN", 5, firstColIndent, ITextFont.arial_bold_10, titlePaddingTop, 0F, PdfPCell.ALIGN_LEFT);
        addCol(orderDetailTable, "Data", 1, 0F, ITextFont.arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Term", 1, 0F, ITextFont.arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Monthly", 1, 0F, ITextFont.arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, "Qty", 1, 0F, ITextFont.arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, "Subtotal", 1, 0F, ITextFont.arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT);
        addColBottomBorder(orderDetailTable, " ", 7, 0F, ITextFont.arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_LEFT, borderColor);
        addColBottomBorder(orderDetailTable, this.getCo().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 14F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
        addColBottomBorder(orderDetailTable, " ", 1, 0F, ITextFont.arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);
        addColBottomBorder(orderDetailTable, this.getCo().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 14F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
        // END PLAN ROW HEADER

        // BEGIN PLAN ROW COLUMN
        if(this.getCodPlans().size()>0){
            for (CustomerOrderDetail cod : this.getCodPlans()) {
            	
            	// BEGIN PREVENT NULL POINTER EXCEPTION
            	if(cod.getDetail_price()==null){
                	cod.setDetail_price(0d);
            	}
            	if(cod.getDetail_unit()==null){
                	cod.setDetail_unit(0);
            	}
            	// END PREVENT NULL POINTER EXCEPTION
            	
                BigDecimal price = new BigDecimal(cod.getDetail_price());
                BigDecimal unit = new BigDecimal(cod.getDetail_unit());
                
                // INCREASE TOTAL PRICE
                totalPrice += price.multiply(unit).doubleValue();

                // BEGIN PLAN ROWS
                addCol(orderDetailTable, cod.getDetail_name(), 5, firstColIndent, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_LEFT);
                addCol(orderDetailTable, cod.getDetail_data_flow() < 0 ? "Ultimate" : cod.getDetail_data_flow()+"GB", 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER);
                addCol(orderDetailTable, cod.getDetail_term_period()+" months", 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER);
                addCol(orderDetailTable, String.valueOf(TMUtils.fillDecimalPeriod(String.valueOf(cod.getDetail_price()))), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT);
                addCol(orderDetailTable, String.valueOf(cod.getDetail_unit()), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT);
                addCol(orderDetailTable, String.valueOf(TMUtils.fillDecimalPeriod(String.valueOf(price.multiply(unit).doubleValue()))), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT);
                // END PLAN ROWS
            	
    		}
        }
        // END PLAN ROW COLUMN
        /**
         * END PLAN LIST
         */
        
        // BEGIN PLAN LIST ENDING LINE SEPARATOR
        addLineCol(orderDetailTable, 10, new BaseColor(159,159,159), 2F);
        // END PLAN LIST ENDING LINE SEPARATOR

        /**
         * BEGIN ADD ON LIST
         */
        
    	// BEGIN DISTANCE TO TOP
        addEmptyCol(orderDetailTable, 4F, 10);
    	// END DISTANCE TO TOP
        
        // BEGIN INITIAL PARAMETERS
        contentPaddingTop = 2F;
        contentPaddingBottom = 6F;
        // END INITIAL PARAMETERS
        
        // BEGIN ADD ON ROW HEADER
        addCol(orderDetailTable, "SERVICE / PRODUCT", 7, firstColIndent, ITextFont.arial_bold_10, titlePaddingTop, titlePaddingBottom, null);
        addCol(orderDetailTable, "Price", 1, 0F, ITextFont.arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Qty", 1, 0F, ITextFont.arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Subtotal", 1, 0F, ITextFont.arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT);
        addColBottomBorder(orderDetailTable, " ", 7, 0F, ITextFont.arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_LEFT, borderColor);
        addColBottomBorder(orderDetailTable, this.getCo().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 0F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
        addColBottomBorder(orderDetailTable, " ", 1, 0F, ITextFont.arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);
        addColBottomBorder(orderDetailTable, this.getCo().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 14F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
        // END ADD ON ROW HEADER
        
        if(this.getCodAddOns().size()>0){
            for (CustomerOrderDetail cod : this.getCodAddOns()) {
            	
            	// BEGIN PREVENT NULL POINTER EXCEPTION
            	if(cod.getDetail_price()==null){
                	cod.setDetail_price(0d);
            	}
            	if(cod.getDetail_unit()==null){
                	cod.setDetail_unit(0);
            	}
            	// END PREVENT NULL POINTER EXCEPTION
            	
                BigDecimal price = new BigDecimal(cod.getDetail_price());
                BigDecimal unit = new BigDecimal(cod.getDetail_unit());
                
                // INCREASE TOTAL PRICE
                totalPrice += price.multiply(unit).doubleValue();

                // BEGIN ADD ON ROWS
                addColBottomBorder(orderDetailTable, cod.getDetail_name(), 5, firstColIndent, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_LEFT, borderColor);
                addColBottomBorder(orderDetailTable, " ", 2, 0F, null, 0F, 0F, null, borderColor);
                addColBottomBorder(orderDetailTable, String.valueOf(TMUtils.fillDecimalPeriod(String.valueOf(cod.getDetail_price()))), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
                addColBottomBorder(orderDetailTable, String.valueOf(cod.getDetail_unit()), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
                addColBottomBorder(orderDetailTable, String.valueOf(TMUtils.fillDecimalPeriod(String.valueOf(price.multiply(unit).doubleValue()))), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);
                // END ADD ON ROWS
            	
    		}
        }
        /**
         * END ADD ON LIST
         */
        
        this.getCo().setOrder_total_price(totalPrice);
        // BEGIN transform before tax price and tax
        BigDecimal bdGSTRate = new BigDecimal(this.getGstRate());
        BigDecimal bdGSTRate2 = new BigDecimal(this.gstRate2);
        BigDecimal bdTotalPrice = new BigDecimal(totalPrice.toString());
        BigDecimal bdBeforeGSTPrice = new BigDecimal(beforeGSTPrice.toString());
        BigDecimal bdGST = new BigDecimal(gst.toString());
        // BEGIN Calculation Area
        
        if(this.getCo().getCustomer_type().equals("business")){
            
            // If business then totalPrice equals to beforeGSTPrice
            bdBeforeGSTPrice = new BigDecimal(totalPrice.toString());
            
            // If business then totalPrice multiply GSTRate equals to totalPrice
            totalPrice = new BigDecimal(bdTotalPrice.multiply(bdGSTRate).toString()).doubleValue();
            
            // If business then totalPrice multiply gstRate2 equals to gst
            bdGST = new BigDecimal(bdTotalPrice.multiply(bdGSTRate2).toString());
            
        } else {
            
            // totalPrice divide GSTRate equals to beforeGSTPrice
            bdBeforeGSTPrice = new BigDecimal(bdTotalPrice.divide(bdGSTRate,2, BigDecimal.ROUND_DOWN).toString());
            
            // totalPrice subtract beforeGSTPrice equals to GST15
            bdGST = new BigDecimal(bdTotalPrice.subtract(bdBeforeGSTPrice).toString());
        }
        
        // END Calculation Area
        // BEGIN ASSIGNING
        beforeGSTPrice += bdBeforeGSTPrice.doubleValue();
        gst += bdGST.doubleValue();
        // END ASSIGNING
        /**
         * END DETAIL
         */
        
        /**
         * BEGIN DETAIL TOTAL PRICE
         */
        
    	// BEGIN DISTANCE BETWEEN TOP AND SEPARATOR
        addEmptyCol(orderDetailTable, 20F, 10);
    	// END DISTANCE BETWEEN TOP AND SEPARATOR
        
    	// BEGIN SEPARATOR
        addEmptyCol(orderDetailTable, 1F, 7);
        addLineCol(orderDetailTable, 3, borderColor, 1F);
    	// END SEPARATOR
        
        // BEGIN TOTAL BEFORE GST
        addEmptyCol(orderDetailTable, 7);
        addCol(orderDetailTable, "Total before GST", 2, 0F, ITextFont.arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(beforeGSTPrice)), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        // END TOTAL BEFORE GST
        
        if(this.getCo().getCustomer_type().equals("business")){
            
            // BEGIN GST
            addEmptyCol(orderDetailTable, 7);
            addCol(orderDetailTable, "GST at " + this.businessGST, 2, 0F, ITextFont.arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(gst)), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            // END GST
        } else {
            
            // BEGIN GST
            addEmptyCol(orderDetailTable, 7);
            addCol(orderDetailTable, "GST at " + this.personalGST, 2, 0F, ITextFont.arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(gst)), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            // END GST
        }
        
        // BEGIN ORDER TOTAL
        addEmptyCol(orderDetailTable, 7);
        addCol(orderDetailTable, "Order Total", 2, 0F, ITextFont.arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(totalPrice)), 1, 0F, ITextFont.arial_bold_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        // END ORDER TOTAL
 
        /**
         * END DETAIL TOTAL PRICE
         */
        return orderDetailTable;
    }
    
    public PdfPTable createTermAndAuthorisationTable(PdfWriter writer){
        
        // BEGIN INITIAL PARAMETERS
        Integer colSize = 61;
        // END INITIAL PARAMETERS
    	
        PdfPTable termAndAuthorisationTable = newTable().columns(colSize).widthPercentage(108F).o();
//        		new PdfPTable(colSize);
//        termAndAuthorisationTable.setWidthPercentage(108F);


        // BEGIN TERM AND AUTHORIZATION ROWS
		addCol(termAndAuthorisationTable, "").colspan(colSize).border(0).fixedHeight(10F).o();
		addCol(termAndAuthorisationTable, "").colspan(colSize/2+1).border(0).o();
        addCol(termAndAuthorisationTable, "including the factors that affect your connection speed. Any control we have over data speed is limited to our network and we make no representations in relation to your speed when connecting to servers outside the CyberPark network.\n"
+"2.2 CyberPark Broadband is not guaranteed to be fault free or continuous.\n"
+"2.3 CyberPark prioritises certain types of traffic such as web and email. Other types of traffic such as peer to peer (P2P) are deprioritised.\n"
+"2.4 The quality of the data you send or receive via CyberPark broadband may be affected by various factors including the "
+"configuration of our network, the use of the internet, or the configuration of the devices used in sending and/or receiving such data.\n", colSize/2, 0F, ITextFont.arial_normal_4, 0F, 0F, null);
        
        
        addCol(termAndAuthorisationTable, "BUSINESSLINE TERMS AND CONDITIONS\n"
+"If you are a CyberPark Businessline customer, the terms and conditions set out below will govern your use of the CyberPark "
+"Businessline services, and the contractual relationship between us in addition to the CyberPark General Terms and Conditions.\n"
+"If you receive other services from us, other terms may also apply to you. These terms may be found on our Website.\n"
+"1. INSTALLATION AND MAINTENANCE\n"
+"1.1 If required, we will arrange a time with you to install your CyberPark Businessline and you will make sure that we can access "
+"your property at this time.\n"
+"1.2 Additional charges may be incurred depending on the status of your current connection and you will be responsible for the "
+"payment of such charges.\n"
+"2. EQUIPMENT\n"
+"2.1 You will ensure that any equipment you connect to your CyberPark Businessline has a Telepermit printed on it or has been "
+"authorized for use by CyberPark. This helps make sure the equipment does not damage the network but does not mean that it will "
+"always remain compatible with the CyberPark network. If equipment which you use in connection with your CyberPark Businessline "
+"does not work properly, you will need to contact the equipment supplier or manufacturer, and not CyberPark, about the problem.\n"
+"3. YOUR TELEPHONE NUMBER AND PORTABILITY\n"
+"3.1 Any telephone number we or another telecommunication provider assigns to you will not be your property. You may port the "
+"number to another telecommunications service provider by contacting the provider to whom you wish to port such number.\n"
+"CyberPark will comply with its obligations under the Local and Mobile Number Portability Terms in relation to porting. You will be "
+"liable for any costs associated with transferring your service to another provider.\n"
+"3.2 We may change any number we have allocated and will give you reasonable notice of this. We will not be liable for any costs "
+"you incur as a result of such change. If you would like to change your telephone number please call CyberPark on 0800 00 19 19.\n"
+"4. OTHER SUPPLIERS AND OTHER SUPPLIER PRODUCTS\n"
+"4.1 You are responsible for your relationship with any other supplier providing telecommunications services to you. For example, if "
+"you have a CyberPark Businessline but you have your internet services with another supplier, you must notify both us and the "
+"other supplier if you change your address.\n"
+"4.2 If you use the product of another service provider on the CyberPark network (for example, a teleconferencing service from "
+"another provider on your CyberPark businessline) you may be charged for use of such product at a fee greater than that charged "
+"by the third party provider to its customers.\n"
+"5. YOUR RESPONSIBILITIES\n"
+"5.1 You agree that you will pay for repairing or replacing any part of the CyberPark network which is lost, stolen or damaged by "
+"you or anyone for whom you are responsible.\n"
+"5.2 You will make sure that any wiring or equipment at your premises, such as alarm systems, do not interfere with any part of the "
+"CyberPark network.\n"
+"5.3 You agree to make sure that every person for whom you are responsible (eg employees, contractors etc) also meets the "
+"responsibilities set out in this clause.\n"
+"6. 0900 CALLS AND LOCAL CALLS\n"
+"6.1 0900 calls are blocked by default on your CyberPark Businessline. However, in the event a 0900 call is made from your "
+"CyberPark Businessline you agree to pay:\n"
+"• Call charges for 0900 calls made from your telephone through the CyberPark network or charged to you, no matter who makes them\n", colSize/2, 0F, ITextFont.arial_normal_4, 0F, 0F, null);
        addEmptyCol(termAndAuthorisationTable, 1);
        addCol(termAndAuthorisationTable, "3. SUPPORT\n"
+"3.1 While CyberPark will do everything it can to help you with any problems you experience with your CyberPark broadband, In "
+"some circumstances, CyberPark may not be able to provide support in relation to your particular operating system. Further, "
+"CyberPark does not have any obligation to provide support to you where:\n"
+"• The fault is with your computer, communications equipment, your wireless LAN network, your software, your phone line (unless "
+"provided by us) or any other;\n"
+"• part of the Internet not forming part of our network;\n"
+"• you use the service incorrectly;\n"
+"• an event occurs which is beyond our reasonable control.\n"
+"4. CHANGING YOUR ADDRESS OR PHONE LINE\n"
+"4.1 If you change your address or phone line, you may be able to take your services with you. You will need to call us at least 14 "
+"days in advance of the move so we can discuss how to minimise service disruption.\n"
+"4.2 Should your phone line become permanently disconnected for any reason, we will no longer be able to provide Broadband to "
+"you and your disconnection will be taken to be a termination of your agreement with CyberPark for the provision of CyberPark "
+"Broadband. Should you wish to enter into a new agreement for Broadband an installation charge and term contract may apply.\n"
+"5. BILLING\n"
+"5.1 Charges for Broadband usage will include:\n"
+"• Any applicable one-off installation and/or modem charges\n"
+"• your CyberPark Broadband monthly fee (which will be charged in advance)\n"
+"• any cancellation fee (which will be charged in arrears)\n"
+"• any excess usage charges (which are billed one month in arrears)\n"
+"5.2 The Broadband service is an \"always on\" connection and whether you use the service or not, you will still be charged the full "
+"monthly fee. You cannot claim a refund for any unused portion of your data allowance in a specific billing period and you cannot "
+"accumulate this unused amount for the following billing period.\n"
+"5.3 Payment for CyberPark Broadband is by direct debit or credit card only.\n"
+"6. SERVICES AND USAGE\n"
+"6.1 In the event that we cancel your Broadband, a reconnection fee may apply if you wish to reinstate your services.\n"
+"6.2 If you are on a plan with a specified data allowance you will be charged for any data in excess of that allowance at the rates "
+"set out on our website at http://www.cyberpark.co.nz or http://www.jadootvnz.com.\n"
+"6.3 If you are on an CyberPark Broadband flat rate plan which provides for a fixed data allowance and no overage, where you "
+"exceed your monthly data allowance your connection speed will be reduced to a maximum of dial up speed once you reach your "
+"monthly data allowance.\n"
+"6.4 Your CyberPark Broadband Services have free virus and spam filtering on them which are designed to prevent virus or spam "
+"from being forwarded to the recipient. Despite this, CyberPark cannot guarantee that spam or viruses will not reach the recipient. \n"
+"Some malicious software (eg viruses, adware, etc) can lead to unexpected use of bandwidth and this in turn can use up your "
+"monthly data allowance. You are responsible for all data usage that occurs under your account whether or not the usage is caused "
+"by malicious software. It is your responsibility to make sure you have sufficient security to meet your needs and CyberPark "
+"recommends you install current anti-virus software on your computer.\n"
+"7. MODEMS\n"
+"7.1 If in the unlikely event that you decide to leave the CyberPark Work network within twelve months, you will have to return the "
+"CyberPark Single Port Modem. If the modem is not returned, you will be charged an amount to recover the cost of this device and "
+"this amount will be advised to you on the CyberPark website from time to time.", colSize/2, 0F, ITextFont.arial_normal_4, 0F, 0F, null);
        
        
        addCol(termAndAuthorisationTable, "• Any charitable donations promised during those calls\n"
+"• Any other amounts charged to you by the 0900 service providers. Your agreement to make these payments also applies for the "
+"benefit of the 0900 service providers and may be enforced by them.\n"
+"6.2 Local calling is free with your CyberPark Businessline provided you are on our LLU network (Jadoo CyberPark Biz plans) and "
+"you do not dial the local access code when making a local call. If you dial the local access code you will be charged at the "
+"relevant CyberPark Toll rate.\n"
+"7. DIRECTORY LISTINGS\n"
+"7.1 We will provide your details for the White Pages or its equivalent and for directory assistance by default. You agree that in "
+"relation to directory assistance services our liability is limited to seeking to rectify any error as soon as practicable.\n"
+"7.2 If you do not want your details to be listed in the White Pages or directory assistance, or you require special types of listings "
+"please contact CyberPark on 0800 899 892. Any arrangement you make in relation to such listings however, will be an "
+"agreement between you and the directory services provider.\n", colSize/2, 0F, ITextFont.arial_normal_4, 0F, 0F, null);
        addEmptyCol(termAndAuthorisationTable, 1);
        addCol(termAndAuthorisationTable, "", colSize/2, 0F, ITextFont.arial_normal_4, 0F, 0F, null);
        
        
        addCol(termAndAuthorisationTable, "8. WIRING AND MAINTENANCE\n"
+"8.1 CyberPark's wiring and maintenance service is an optional service which covers the cost of fixing, during normal fault service "
+"hours, most faults in the telephone sockets and wiring inside your premises which are covered by the service. This service covers:\n"
+"• Telephone sockets and wiring which meet and are installed to our specifications.\n"
+"• Wiring inside your premises, but not external wiring to another building on your premises or from your premises to the network.\n"
+"• The service does not cover equipment attached to sockets such as extension cables or socket adapters\n"
+"8.2 Moving, adding or changing sockets which are not faulty is not covered by this service. If we need to replace a socket to fix a "
+"fault we will replace it with an item from our product range which is similar. If you have a replacement item you would like us to "
+"install instead we will install it providing it meets with our requirements.\n"
+"8.2 CyberPark's wiring and maintenance service does not cover you:\n"
+"• In the event that you do not meet your responsibilities relating to our network and equipment supplied by us set out in "
+"CyberPark’s terms and conditions.\n"
+"• If the fault is caused by deliberate or malicious actions.\n"
+"• If the fault is caused by other wiring or equipment in your premises.\n"
+"9. FAULTS\n"
+"9.1 If you experience a phone line fault please contact us on 0800 899 892 and we will make every effort to restore your service.\n"
+"9.2 If the fault was caused by you or people for whom you are responsible, you may be charged a fault diagnosis fee.\n"
+"9.3 Call diversion is available for faults that last longer than 24 hours.\n"
+"9.4 You may be liable to pay the relevant call out fee if you cancel a booking for a technician to visit your premises.\n"
+"10. ANCILLIARY SERVICES\n"
+"10.1 CyberPark supports call waiting, call minder and caller display services. If you switch to CyberPark, services which are "
+"provided by your previous telecommunications service provider may not be provided by CyberPark and will not be automatically "
+"brought across from that provider. You will need to choose which of the available CyberPark services you wish to receive.\n"
+"10.2 Some CyberPark Plans will offer a call forwarding service. All calls that are forwarded under this service will incur call charges "
+"in the same way as if you had made an outbound phone call to the number being forwarded to. e.g. if you forward your number to "
+"a mobile phone, standard landline to mobile charges will apply.\n"
+"11. CREDIT RESTRICTIONS\n"
+"11.1 In the event of non-payment we reserve the right to place a temporary disconnect on your phone line. You will still be able to "
+"receive calls and place calls to emergency services, but you will not be able to dial out. The line will continue to be charged at the "
+"normal monthly rate and a reconnection fee will apply.\n"
+"11.2 In the event of continued non-payment we reserve the right to disconnect your phone where you will not be able to receive or "
+"place calls, including calls to emergency phone numbers. The line will continue to be charged at the normal monthly rate and a "
+"reconnection fee will apply.\n"
+"11.3 If you have a medical condition or other special circumstance for which you require your telephone connection, it is your "
+"responsibility to let us know so we do not disconnect your phone line.\n"
+"12. CALLER ID\n"
+"If your CyberPark Plan includes our Caller ID product with your Businessline, we will provide this service on the landline you "
+"specify. For most incoming calls, Caller ID will display the phone number of the person calling you however your telephone and/or "
+"wall socket need to be compatible in order to receive the Caller ID Service. Some incoming calls will not display the number for "
+"example where a call is made from a line which has an unlisted number or from a pay phone or another service provider’s "
+"network. If you intend to collect or use any calling information provided by the Caller ID product you must comply with the Privacy "
+"Act 1993. You agree that you intend to use Caller ID to help identify the calling party, and to call back a phone number displayed "
+"or stored on telephone. You will ensure that everyone using your phone, or the numbers displayed on your display screen, "
+"understands and meets these responsibilities.\n"
+"13. TOLLS\n"
+"13.1 A toll call is any call you make that is not a local call (e.g. national, international and calls to mobile phones). All CyberPark "
+"toll calls are subject to a one minute minimum charge. Calls longer than one minute duration are rounded up to the next minute.\n"
+"13.2 You are responsible for any charges incurred on your CyberPark account for toll calls. This includes unintentional or "
+"unauthorised dialed numbers.\n"
+"13.3 Some malicious software or viruses can lead to toll calls being made automatically on your line. It is your responsibility to "
+"make sure you have sufficient security to meet your needs.\n"
+"13.4 You must be an CyberPark Businessline customer to sign up for an CyberPark Tolls plan.\n"
+"13.5 There is a one monthly billing cycle minimum term on all CyberPark business plans.\n"
+"13. 6 Your Businessline to mobile calling plan allows for a specified number of minutes to be used for landline to New Zealand "
+"mobile calls and these must be used within one monthly billing period. Once your specified numbers of minutes have been used, "
+"standard retail rates apply. Unused minutes cannot be carried over into the next billing period.\n"
+"BUSINESS BROADBAND TERMS AND CONDITIONS\n"
+"If you are a business CyberPark Broadband customer, the terms and conditions set out below will govern your use of the "
+"CyberPark broadband services, and the contractual relationship between us in addition to the CyberPark Business General Terms "
+"and Conditions. If you receive other services from us, other terms may also apply to you. These terms may be found on our "
+"website at http://www.cyberpark.co.nz or http://www.jadootvnz.com.\n"
+"1. AVAILABILITY AND CONNECTION OF SERVICES\n"
+"1.1 Only one Broadband account can be used on any one phone line. Services are not available in all areas or on all line types.\n"
+"1.2 In some instances, broadband will not be available in your area for technical reasons such as the distance of your premises "
+"from an exchange. Further, you may be placed on a waiting list for Broadband, for example if there is limited capacity at your "
+"exchange. We will endeavour to contact you as soon as space becomes available.\n"
+"1.3 If you are a new CyberPark customer you will need a standard broadband connection to receive CyberPark Broadband.\n"
+"1.4 You may require connection and wiring services at your premises if you have numerous jack points or any device which also "
+"utilizes the telephone line such as a monitored alarm or Sky TV. This will incur a charge as an accredited technician will need to "
+"visit your premises in order to get your broadband working.\n"
+"2. SPEED AND QUALITY OF SERVICE\n"
+"2.1 Any reference to the data speeds refers to the maximum possible connection speed. Your actual speed depends on a number "
+"of factors and we cannot guarantee that you will achieve a maximum connection speed. See our website for further details.", colSize/2, 0F, ITextFont.arial_normal_4, 0F, 0F, null);
        addEmptyCol(termAndAuthorisationTable, 1);

        // BEGIN INITIAL PARAMETERS
        Float innerColIndent = 10F;
        Integer innerColSize = 13;
        // END INITIAL PARAMETERS

		// BEGIN INNER TABLE
        PdfPTable termAndAuthorisationInnerTable = new PdfPTable(innerColSize);
        termAndAuthorisationInnerTable.setWidthPercentage(100);

    	// BEGIN TERM AND AUTHORIZATION PADDING TOP
        addEmptyCol(termAndAuthorisationInnerTable, 8F, innerColSize);
    	// END TERM AND AUTHORIZATION PADDING TOP
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addCol(termAndAuthorisationInnerTable, "Authorisation", innerColSize, innerColIndent, ITextFont.arial_bold_12, 0F, 0F, null);
        // END TERM AND AUTHORIZATION INNER ROWS

    	// BEGIN TERM AND AUTHORIZATION PADDING BETWEEN TITLE AND CONTENT
        addEmptyCol(termAndAuthorisationInnerTable, 8F, innerColSize);
    	// END TERM AND AUTHORIZATION PADDING BETWEEN TITLE AND CONTENT
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addCol(termAndAuthorisationInnerTable, "I understand and agree to the following: ", innerColSize, innerColIndent, ITextFont.arial_normal_10, 0F, 0F, null);
        // END TERM AND AUTHORIZATION INNER ROWS

    	// BEGIN TERM AND AUTHORIZATION PADDING BETWEEN ROW
        addEmptyCol(termAndAuthorisationInnerTable, 8F, innerColSize);
    	// END TERM AND AUTHORIZATION PADDING BETWEEN ROW
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addCol(termAndAuthorisationInnerTable, "I am over 18 and authorised to make changes to the account specified\n"
+"above. A credit check will be carried out by CyberPark Ltd. who may\n"
+"choose to decline my application and are not required to specify a\n"
+"reason. I acknowledge that I am changing my service provider and that\n"
+"I may retain obligations to my previous provider which are my\n"
+"responsibility to discharge. These may include but not be limited to\n"
+"disconnection or porting fees.\n"
+"I acknowledge that some service’s may not be supported by CyberPark\n"
+"Ltd. Any information about me may be accessed and corrected by me\n"
+"pursuant to the Privacy Act 1993.\n"
+"If the Customer terminates this agreement prior to the expiry date of\n"
+"this agreement, or if the agreement is cancelled by CyberPark Limited\n"
+"prior to the expiry date due to the default or breach of the Customer,\n"
+"then the Customer shall pay to CyberPark Limited:\n", innerColSize, innerColIndent, ITextFont.arial_normal_8, 0F, 0F, null);
        // END TERM AND AUTHORIZATION INNER ROWS

    	// BEGIN TERM AND AUTHORIZATION PADDING BETWEEN ROW
        addEmptyCol(termAndAuthorisationInnerTable, 8F, innerColSize);
    	// END TERM AND AUTHORIZATION PADDING BETWEEN ROW
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addCol(termAndAuthorisationInnerTable, "(a) the standard early termination fee of $199; and", innerColSize, innerColIndent, ITextFont.arial_normal_8, 0F, 0F, null);
        // END TERM AND AUTHORIZATION INNER ROWS

    	// BEGIN TERM AND AUTHORIZATION PADDING BETWEEN ROW
        addEmptyCol(termAndAuthorisationInnerTable, 8F, innerColSize);
    	// END TERM AND AUTHORIZATION PADDING BETWEEN ROW
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addCol(termAndAuthorisationInnerTable, "(b) the standard retail price (as at the date of this agreement) of any\n"
+"equipment and/or gift received by the Customer under this agreement.", innerColSize, innerColIndent, ITextFont.arial_normal_8, 0F, 0F, null);
        // END TERM AND AUTHORIZATION INNER ROWS

    	// BEGIN TERM AND AUTHORIZATION PADDING BETWEEN ROW
        addEmptyCol(termAndAuthorisationInnerTable, 8F, innerColSize);
    	// END TERM AND AUTHORIZATION PADDING BETWEEN ROW
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addCol(termAndAuthorisationInnerTable, "I declare that I have read and accept the Terms and Conditions\n"
+"(www.cyberpark.co.nz) as well as the specific terms relating to this\n"
+"offer applicable to my connection.", innerColSize, innerColIndent, ITextFont.arial_normal_8, 0F, 0F, null);
        // END TERM AND AUTHORIZATION INNER ROWS

    	// BEGIN TERM AND AUTHORIZATION PADDING BETWEEN ROW
        addEmptyCol(termAndAuthorisationInnerTable, 8F, innerColSize);
    	// END TERM AND AUTHORIZATION PADDING BETWEEN ROW
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addCol(termAndAuthorisationInnerTable, "My first payment will be for any one-off cost as set out above plus any\n"
+"monthly amounts as set out above and any additional usage costs, The\n"
+"second and subsequent payments will be for the monthly amount set\n"
+"out above plus any additional usage costs, plus any amount\n"
+"outstanding. I will be billed on a monthly basis and the due date for bill\n"
+"payments will be the date set out in those bills. I may pay those bills by\n"
+"direct debit. Credit card, or by the methods set out in those bills.", innerColSize, innerColIndent, ITextFont.arial_normal_8, 0F, 0F, null);
        // END TERM AND AUTHORIZATION INNER ROWS

    	// BEGIN TERM AND AUTHORIZATION PADDING BETWEEN ROW
        addEmptyCol(termAndAuthorisationInnerTable, 40F, innerColSize);
    	// END TERM AND AUTHORIZATION PADDING BETWEEN ROW
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addCol(termAndAuthorisationInnerTable, "Signed & agreed by customer ", innerColSize/2+1, innerColIndent, ITextFont.arial_normal_10, 0F, 0F, null);
        // END TERM AND AUTHORIZATION INNER ROWS
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addColBottomBorder(termAndAuthorisationInnerTable, "", innerColSize/2-1, 0F, ITextFont.arial_normal_8, 0F, 0F, null, tableBorderColor);
        // END TERM AND AUTHORIZATION INNER ROWS
        addEmptyCol(termAndAuthorisationInnerTable, 1);

    	// BEGIN TERM AND AUTHORIZATION PADDING BETWEEN ROW
        addEmptyCol(termAndAuthorisationInnerTable, 12F, innerColSize);
    	// END TERM AND AUTHORIZATION PADDING BETWEEN ROW
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addCol(termAndAuthorisationInnerTable, "Date ", 2, innerColIndent, ITextFont.arial_normal_10, 0F, 0F, null);
        // END TERM AND AUTHORIZATION INNER ROWS
        
        // BEGIN TERM AND AUTHORIZATION INNER ROWS
        addColBottomBorder(termAndAuthorisationInnerTable, TMUtils.dateFormatYYYYMMDD(new Date()), 10, 0F, ITextFont.arial_bold_10, 0F, 0F, null, tableBorderColor);
        // END TERM AND AUTHORIZATION INNER ROWS
        addEmptyCol(termAndAuthorisationInnerTable, 1);
        
        // BEGIN PAYMENT DETAILS TABLE IN COL FIRST
    	addTableInCol(termAndAuthorisationTable, termAndAuthorisationInnerTable, colSize/2, tableBorderColor, tableBorderWidth);
        // END PAYMENT DETAILS TABLE IN COL FIRST
        // END TERM AND AUTHORIZATION ROWS
        
        
        
		return termAndAuthorisationTable;
    }

	public CustomerOrder getCo() {
		return co;
	}

	public void setCo(CustomerOrder co) {
		this.co = co;
	}

	public String getGstRate() {
		return gstRate;
	}
	public List<CustomerOrderDetail> getCodPlans() {
		return codPlans;
	}
	public List<CustomerOrderDetail> getCodAddOns() {
		return codAddOns;
	}

    // BEGIN If Merge PDF Third Part
//    public void mergePDF(String outputFile, String inputFile, String term) throws DocumentException, IOException{
//    	String[] files = {inputFile, term }; 
//    	Document document = new Document();
//    	PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputFile));
//    	document.open();
//    	PdfReader reader = null; 
//    	int n; 
//    	for (int i = 0; i < files.length; i++) {
//    	  reader = new PdfReader(files[i]);
//    	  n = reader.getNumberOfPages();
//    	  for (int page = 0; page < n; ) {
//    	    copy.addPage(copy.getImportedPage(reader, ++page));
//    	  } 
//    	}
//    	copy.close();
//    	reader.close();
//    	document.close();
//    }
    // END If Merge PDF Third Part
    
	
}
