package com.tm.broadband.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.model.Organization;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.itext.ITextFont;
import com.tm.broadband.util.itext.ITextUtils;

/** 
* Generates Ordering PDF
* 
* @author DONG CHEN
*/ 
public class OrderingPDFCreator extends ITextUtils {
	private Customer customer;
	private Organization org;
	private CustomerOrder customerOrder;
	
	private BaseColor titleBGColor = new BaseColor(220,221,221);
	private BaseColor titleBorderColor = new BaseColor(159,159,159);

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
	
	public OrderingPDFCreator(){}
	
	public OrderingPDFCreator(Customer customer, CustomerOrder customerOrder, Organization org){
		this.setCustomer(customer);
		this.setCustomerOrder(customerOrder);
		this.setOrg(org);
	}
	
	public String create() throws DocumentException, IOException{
		
		// DIFFERENTIATES ORDER DETAILS
		if(this.getCustomerOrder().getCustomerOrderDetails().size()>0){
			List<CustomerOrderDetail> cods = this.getCustomerOrder().getCustomerOrderDetails();
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
        
		// Output PDF Path, e.g.: ordering_form_600089.pdf
		String outputFile = TMUtils.createPath("broadband" + File.separator
				+ "customers" + File.separator + this.customer.getId()
				+ File.separator + "ordering_form_" + this.customerOrder.getId()
				+ ".pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        
        // OPEN DOCUMENT
        document.open();
        
    	// company_logo
    	addImage(writer, "pdf"+File.separator+"img"+File.separator+"company_logo.png", 108.75F, 51.00000000000001F, 32F, 762F);
    	
        setGlobalBorderWidth(0);
    	
        // set order PDF title table
		document.add(createOrderPDFTitleTable());
    	
        // set customer(personal or business) basic info table
		document.add(createCustomerOrderBasicInfoTable());
		
        // set customer(personal or business) info table
		document.add(createCustomerInfoTable(writer, document));
		
		// if order broadband type is transition
		if(this.getCustomerOrder().getOrder_broadband_type()!=null && this.getCustomerOrder().getOrder_broadband_type().equals("transition")){
			document.add(createTransitionInfoTable());
		}
		
		// set customer agreement
		document.add(createCustomerAgreementTable());
        
        // set order detail table
		document.add(createOrderDetailTable());
		
		// set FIRST PAGE bottom texts
		// Bottom
		addText(writer, "Print from www.cyberpark.co.nz", ITextFont.arial_normal_10, 190F, 26F);
		addText(writer, "0800 2 CYBER (29237)", ITextFont.arial_normal_10, 360F, 26F);
    	// two-dimensional_code
    	addImage(writer, "pdf"+File.separator+"img"+File.separator+"two-dimensional_code.png", 82.50000000000001F, 82.50000000000001F, 486F, 24F);
        
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
        addEmptyCol(orderPDFTitleTable, 20F, 14);
    	// END ORDER CONFIRMATION AREA PADDING TOP

        // BEGIN ORDER CONFIRMATION TITLE
        addPDFTitle(orderPDFTitleTable, "ORDERING FORM", ITextFont.arial_colored_bold_23, 22F, PdfPCell.BOTTOM, PdfPCell.ALIGN_RIGHT, 14);
//        addPDFTitle(orderPDFTitleTable, "CONFIRMATION", ITextFont.arial_colored_bold_23, 22F, PdfPCell.BOTTOM, PdfPCell.ALIGN_RIGHT, 14);
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
        if(this.getCustomer().getCustomer_type().equals("personal")){
        	
        	// BEGIN PERSONAL BASIC INFORMATION
            addCol(customerBasicInfoTable, (this.getCustomer().getTitle()!=null?this.getCustomer().getTitle()+" ":"")+this.getCustomer().getFirst_name()+" "+this.getCustomer().getLast_name(), 10, 10F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
            // END PERSONAL BASIC INFORMATION
            
        } else if(this.getCustomer().getCustomer_type().equals("business")){
        	
        	// BEGIN BUSINESS BASIC INFORMATION
            addCol(customerBasicInfoTable, this.getOrg().getOrg_name(), 10, 10F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        	// END BUSINESS BASIC INFORMATION
            
        }
        addCol(customerBasicInfoTable, "No.", 2, 0F, ITextFont.arial_colored_bold_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, String.valueOf(this.getCustomerOrder().getId()), 2, 0F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, this.getCustomer().getAddress(), 10, 10F, ITextFont.arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, "Order Date", 2, 0F, ITextFont.arial_colored_bold_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, TMUtils.dateFormatYYYYMMDD(this.getCustomerOrder().getOrder_create_date()), 2, 0F, ITextFont.arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
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
        if(this.getCustomer().getCustomer_type()!=null && this.getCustomer().getCustomer_type().equals("personal")){
        	addTitleBar(customerInfoTable, "PERSONAL INFORMATION", ITextFont.arial_bold_12, titleBGColor, titleBorderColor, 10, 4F);
        } else if(this.getCustomer().getCustomer_type().equals("business")){
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
        if(this.getCustomer().getCustomer_type().equals("personal")){
        	
            // BEGIN PERSONAL INFO ROWS
        	addCol(customerInfoTable, "Phone", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getPhone(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Mobile", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getCellphone(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Email", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getEmail(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Date of Birth", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, TMUtils.dateFormatYYYYMMDD(this.getCustomer().getBirth()), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Driver License No.", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getDriver_licence(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Passport No.", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getPassport(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Passport Country or Origin", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getCountry(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
            // END PERSONAL INFO ROWS
            
        } else if(this.getCustomer().getCustomer_type().equals("business")){
        	
            // BEGIN BUSINESS INFO ROWS
        	addCol(customerInfoTable, "Organization Name", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getOrg().getOrg_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Organization Type", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getOrg().getOrg_type(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Trading Name", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getOrg().getOrg_trading_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Registration No.", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getOrg().getOrg_register_no(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Date Incoporated", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, TMUtils.dateFormatYYYYMMDD(this.getOrg().getOrg_incoporate_date()), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);

        	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
            addEmptyCol(customerInfoTable, 8F, 10);
        	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
            
        	// BEGIN HOLDER INFO ROWS
        	addTitleBar(customerInfoTable, "ACCOUNT HOLDER INFORMATION", ITextFont.arial_bold_10, titleBGColor, titleBorderColor, 10, 0F);

        	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
            addEmptyCol(customerInfoTable, 2F, 10);
        	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
            
        	addCol(customerInfoTable, "Full name", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getOrg().getHolder_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Job title", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getOrg().getHolder_job_title(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Phone", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getOrg().getHolder_phone(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Email", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getOrg().getHolder_email(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
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
    	addCol(orderPDFTitleTable, this.getCustomerOrder().getTransition_provider_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Account Holder Name", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCustomerOrder().getTransition_account_holder_name(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Your Current Account Number", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCustomerOrder().getTransition_account_number(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Your Porting Number", 3, labelIndent, ITextFont.arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCustomerOrder().getTransition_porting_number(), 7, contentIndent, ITextFont.arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
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
        addColBottomBorder(orderDetailTable, this.getCustomer().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 14F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
        addColBottomBorder(orderDetailTable, " ", 1, 0F, ITextFont.arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);
        addColBottomBorder(orderDetailTable, this.getCustomer().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 14F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
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
        addColBottomBorder(orderDetailTable, this.getCustomer().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 0F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
        addColBottomBorder(orderDetailTable, " ", 1, 0F, ITextFont.arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);
        addColBottomBorder(orderDetailTable, this.getCustomer().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 14F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
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
        
        this.customerOrder.setOrder_total_price(totalPrice);
        // BEGIN transform before tax price and tax
        BigDecimal bdGSTRate = new BigDecimal(this.getGstRate());
        BigDecimal bdGSTRate2 = new BigDecimal(this.gstRate2);
        BigDecimal bdTotalPrice = new BigDecimal(totalPrice.toString());
        BigDecimal bdBeforeGSTPrice = new BigDecimal(beforeGSTPrice.toString());
        BigDecimal bdGST = new BigDecimal(gst.toString());
        // BEGIN Calculation Area
        
        if(this.getCustomer().getCustomer_type().equals("business")){
            
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
        
        PdfPTable innerTable = newTable().columns(27).widthPercentage(100F).o();
        addCol(innerTable, "Pay By Bank Deposit:").colspan(7).font(ITextFont.arial_normal_8).o();
        addCol(innerTable, "Please put your Ordering No.").colspan(9).font(ITextFont.arial_bold_8).o();
        addCol(innerTable, String.valueOf(this.getCustomerOrder().getId())).colspan(3).font(ITextFont.arial_colored_normal_8).o();
        addCol(innerTable, "in the reference").colspan(8).font(ITextFont.arial_bold_8).o();
        
        addCol(innerTable, "Bank:").colspan(7).font(ITextFont.arial_normal_8).o();
        addCol(innerTable, "ANZ").colspan(20).font(ITextFont.arial_bold_8).o();
        
        addCol(innerTable, "Name of Account:").colspan(7).font(ITextFont.arial_normal_8).o();
        addCol(innerTable, "Cyberpark Limited").colspan(20).font(ITextFont.arial_bold_8).o();
        
        addCol(innerTable, "Account Number:").colspan(7).font(ITextFont.arial_normal_8).o();
        addCol(innerTable, "06-0709-0444426-00").colspan(20).font(ITextFont.arial_bold_8).o();
        addCol(innerTable, "Your ordering form will be hold for 7 days.\r\n( 5 working days include 2 weekend days )").colspan(27).paddingV(10F).font(ITextFont.arial_bold_red_10).o();
        
//        addCol(innerTable, "Cheques payable to:").colspan(2).font(ITextFont.arial_normal_8).o();
//        addCol(innerTable, "Please write your Ordering No. on the back of your cheque").colspan(6).font(ITextFont.arial_bold_8).o();
//        
//        addCol(innerTable, "Post to:").colspan(2).indent(14F).font(ITextFont.arial_normal_8).o();
//        addCol(innerTable, "Cyberpark Limited").colspan(6).font(ITextFont.arial_bold_8).o();
//        
//        addCol(innerTable, "").colspan(2).o();
//        addCol(innerTable, "P.O.Box 41547 St Lukes, Auckland").colspan(6).font(ITextFont.arial_bold_8).o();
        
        addTableInCol(orderDetailTable, innerTable).rowspan(3).colspan(7).border(0).o();
        
//        addCol(orderDetailTable, "asdad ").rowspan(3).colspan(7).border(0).o();
        
        // BEGIN TOTAL BEFORE GST
        addCol(orderDetailTable, "Total before GST", 2, 0F, ITextFont.arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(beforeGSTPrice)), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        // END TOTAL BEFORE GST
        
        if(this.getCustomer().getCustomer_type().equals("business")){
            
            // BEGIN GST
            addCol(orderDetailTable, "GST at " + this.businessGST, 2, 0F, ITextFont.arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(gst)), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            // END GST
        } else {
            
            // BEGIN GST
            addCol(orderDetailTable, "GST at " + this.personalGST, 2, 0F, ITextFont.arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(gst)), 1, 0F, ITextFont.arial_normal_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            // END GST
        }
        
        // BEGIN ORDER TOTAL
        addCol(orderDetailTable, "Order Total", 2, 0F, ITextFont.arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(totalPrice)), 1, 0F, ITextFont.arial_bold_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        // END ORDER TOTAL
 
        /**
         * END DETAIL TOTAL PRICE
         */
        return orderDetailTable;
    }

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Organization getOrg() {
		return org;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}
	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
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
