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
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.util.ITextUtils;
import com.tm.broadband.util.TMUtils;

public class OrderPDFCreator extends ITextUtils {
	private Customer customer;
	private CustomerOrder customerOrder;
	private Font arial_normal_7;
	private Font arial_normal_10;
	private Font arial_colored_normal_11;
	private Font arial_colored_bold_11;
	private Font arial_bold_10;
	private Font arial_bold_12;
	private Font arial_bold_23;
	private BaseColor titleBGColor = new BaseColor(220,221,221);
	private BaseColor titleBorderColor = new BaseColor(159,159,159);

	// BEGIN Temporary Variables
	// BEGIN Currency Related Variables
	private Double totalPrice = 0d;
	private Double beforeGSTPrice = 0d;
	private Double gst15 = 0d;
	// 15% GST as default
	private String gstRate = "1.15";
	// END Currency Related Variables
	
	// BEGIN Order Detail Differentiations Variables
	List<CustomerOrderDetail> codPlans = new ArrayList<CustomerOrderDetail>();
	List<CustomerOrderDetail> codAddOns = new ArrayList<CustomerOrderDetail>();
	// END Order Detail Differentiations Variables
	// END Temporary Variables
	
	
	public OrderPDFCreator(){
		try {
			BaseFont bf_arial_normal_7 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_normal_7 = new Font(bf_arial_normal_7, 7, Font.NORMAL);
			BaseFont bf_arial_normal_10 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_normal_10 = new Font(bf_arial_normal_10, 10, Font.NORMAL);
			BaseFont bf_arial_colored_normal_11 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_colored_normal_11 = new Font(bf_arial_colored_normal_11, 11, Font.NORMAL, new BaseColor(61,184,185));
			BaseFont bf_arial_colored_bold_11 = BaseFont.createFont("pdf"+File.separator+"font-family/arialbd.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_colored_bold_11 = new Font(bf_arial_colored_bold_11, 11, Font.NORMAL, new BaseColor(61,184,185));
			BaseFont bf_arial_bold_10 = BaseFont.createFont("pdf"+File.separator+"font-family/arialbd.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_bold_10 = new Font(bf_arial_bold_10, 10, Font.NORMAL);
			BaseFont bf_arial_bold_12 = BaseFont.createFont("pdf"+File.separator+"font-family/arialbd.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_bold_12 = new Font(bf_arial_bold_12, 12);
			BaseFont bf_arial_bold_23 = BaseFont.createFont("pdf"+File.separator+"font-family/arialbd.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_bold_23 = new Font(bf_arial_bold_23, 23, Font.NORMAL, new BaseColor(61,184,185));
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}

	}
	
	public OrderPDFCreator(Customer customer, CustomerOrder customerOrder){
		this();
		this.setCustomer(customer);
		this.setCustomerOrder(customerOrder);
	}
	
	public void create() throws DocumentException, IOException{
		
		// DIFFERENTIATES ORDER DETAILS
		if(this.getCustomerOrder().getCustomerOrderDetails().size()>0){
			List<CustomerOrderDetail> cods = this.getCustomerOrder().getCustomerOrderDetails();
			for (CustomerOrderDetail  cod: cods) {
				if(cod.getDetail_type()!= null && cod.getDetail_type().contains("plan-term")){
					// SAVE PLAN
					codPlans.add(cod);
				} else {
					// SAVE ADD ON
					codAddOns.add(cod);
				}
			}
		}
		
        Document document = new Document();
		
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
        
		// Output PDF Path
		String outputFile = TMUtils.createPath("broadband" + File.separator
				+ "customers" + File.separator + customer.getId()
				+ File.separator + "Order-" + customerOrder.getId()
				+ ".pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        
        // OPEN DOCUMENT
        document.open();
        
    	// company_logo
    	addImage(writer, "pdf"+File.separator+"img"+File.separator+"company_logo.png", 108.75F, 51.00000000000001F, 32F, 762F);
    	
    	// two-dimensional_code
    	addImage(writer, "pdf"+File.separator+"img"+File.separator+"two-dimensional_code.png", 52.50000000000001F, 52.50000000000001F, 506F, 24F);

        // set order PDF title table
		document.add(createOrderPDFTitleTable());
    	
        // set customer(personal or business) basic info table
		document.add(createCustomerOrderBasicInfoTable());
		
        // set customer(personal or business) info table
		document.add(createCustomerInfoTable(writer));
		
		// if order broadband type is transition
		if(this.getCustomerOrder().getOrder_broadband_type()!=null && this.getCustomerOrder().getOrder_broadband_type().equals("transition")){
			document.add(createTransitionInfoTable());
		}
		
		// set customer agreement
		document.add(createCustomerAgreementTable());
        
        // set order detail table
		document.add(createOrderDetailTable());
		
		// second page
        document.newPage();
        
        // term_condition
        addImage(writer, "pdf"+File.separator+"img"+File.separator+"term_and_condition.jpg", 596F, 840F, 0F, 0F);
        
		// CLOSE DOCUMENT
        document.close();
		// CLOSE WRITER
        writer.close();

        // BEGIN If Merge PDF Second Part
//        mergePDF(outputFile, inputFile, term);
        // END If Merge PDF Second Part
	}
	
	public PdfPTable createOrderPDFTitleTable(){
		
        PdfPTable orderPDFTitleTable = new PdfPTable(14);
        orderPDFTitleTable.setWidthPercentage(102);
    	
    	// BEGIN ORDER CONFIRMATION AREA START PADDING TOP
        addEmptyRow(orderPDFTitleTable, 1F, 14);
    	// END ORDER CONFIRMATION AREA START PADDING TOP

        // BEGIN ORDER CONFIRMATION TITLE
        addPDFTitle(orderPDFTitleTable, "BROADBAND APPLICATION", arial_bold_23, 4F, 0, PdfPCell.ALIGN_RIGHT, 14);
        addPDFTitle(orderPDFTitleTable, "CONFIRMATION", arial_bold_23, 22F, PdfPCell.BOTTOM, PdfPCell.ALIGN_RIGHT, 14);
        // END ORDER CONFIRMATION TITLE
        
		return orderPDFTitleTable;
	}
	
	/**
	 * CUSTOMER BASIC 
	 * @return
	 */
	public PdfPTable createCustomerOrderBasicInfoTable(){
		
        PdfPTable customerBasicInfoTable = new PdfPTable(14);
        customerBasicInfoTable.setWidthPercentage(102);
    	
    	// BEGIN CUSTOMER BASIC INFO START PADDING TOP
        addEmptyRow(customerBasicInfoTable, 10F, 14);
    	// END CUSTOMER BASIC INFO START PADDING TOP
        
        // BEGIN CUSTOMER BASIC INFORMATION
        addCol(customerBasicInfoTable, (this.getCustomer().getTitle()!=null?this.getCustomer().getTitle()+" ":"")+this.getCustomer().getFirst_name()+" "+this.getCustomer().getLast_name(), 10, 10F, arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, "No.", 2, 0F, arial_colored_bold_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, String.valueOf(this.getCustomerOrder().getId()), 2, 0F, arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, this.getCustomer().getAddress(), 10, 10F, arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, "Order Date", 2, 0F, arial_colored_bold_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, TMUtils.dateFormatYYYYMMDD(this.getCustomerOrder().getOrder_create_date()), 2, 0F, arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        // END CUSTOMER BASIC INFORMATION
    	
    	// BEGIN CUSTOMER BASIC INFO START PADDING BOTTOM
        addEmptyRow(customerBasicInfoTable, 10F, 14);
    	// END CUSTOMER BASIC INFO START PADDING BOTTOM

        
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
    public PdfPTable createCustomerInfoTable(PdfWriter writer) throws MalformedURLException, IOException, DocumentException {
        
        PdfPTable customerInfoTable = new PdfPTable(10);
        customerInfoTable.setWidthPercentage(102);
        
        // BEGIN CUSTOMER INFO TITLE BAR
        if(this.getCustomer().getCustomer_type()!=null && this.getCustomer().getCustomer_type().equals("personal")){
        	addTitleBar(customerInfoTable, "PERSONAL INFORMATION", arial_bold_12, titleBGColor, titleBorderColor, 10);
        } else if(this.getCustomer().getCustomer_type().equals("business")){
        	addTitleBar(customerInfoTable, "BUSINESS INFORMATION", arial_bold_12, titleBGColor, titleBorderColor, 10);
        }
        // END CUSTOMER INFO TITLE BAR

    	// BEGIN DISTANCE BETWEEN CUSTOMER TABLE TITLE AND ROW
        addEmptyRow(customerInfoTable, 10F, 10);
    	// END DISTANCE BETWEEN CUSTOMER TABLE TITLE AND ROW

        // BEGIN PARAMETERS
        Float labelIndent = 8F;
        Float contentIndent = 30F;
        Float rowPaddingTop = 3F;
        Float rowPaddingBottom = 0F;
        // END PARAMETERS
        
        // BEGIN CUSTOMER(PERSONAL OR BUSINESS) INFO ROWS
        if(this.getCustomer().getCustomer_type().equals("personal")){
            
        	
            // BEGIN PERSONAL INFO ROWS
        	addCol(customerInfoTable, "Phone", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getPhone(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Mobile", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getCellphone(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Email", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getEmail(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Date of Birth", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, TMUtils.dateFormatYYYYMMDD(this.getCustomer().getBirth()), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Driver License No.", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getDriver_licence(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Passport No.", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getPassport(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Passport Country or Origin", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCustomer().getCountry(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
            // END PERSONAL INFO ROWS
            
        } else if(this.getCustomer().getCustomer_type().equals("business")){
            // BEGIN BUSINESS CUSTOMER
        	
        	

            // END BUSINESS CUSTOMER
        }
        // END CUSTOMER(PERSONAL OR BUSINESS) INFO ROWS

    	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
        addEmptyRow(customerInfoTable, 10F, 10);
    	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
        
        // BEGIN CUSTOMER INFO AREA ENDING LINE SEPARATOR
        addLineRow(customerInfoTable, 10, new BaseColor(193,193,193), 1F);
        // END CUSTOMER INFO AREA ENDING LINE SEPARATOR
        
        return customerInfoTable;
    }
	
    /**
     * TRANSITION INFO
     * @return
     */
	public PdfPTable createTransitionInfoTable(){
		
        PdfPTable orderPDFTitleTable = new PdfPTable(10);
        orderPDFTitleTable.setWidthPercentage(102);

    	// BEGIN DISTANCE BETWEEN TRANSITION TABLE TITLE AND ROW
        addEmptyRow(orderPDFTitleTable, 10F, 10);
    	// END DISTANCE BETWEEN TRANSITION TABLE TITLE AND ROW
        
        // BEGIN PARAMETERS
        Float labelIndent = 8F;
        Float contentIndent = 30F;
        Float rowPaddingTop = 3F;
        Float rowPaddingBottom = 0F;
        // END PARAMETERS

        // BEGIN TRANSITION INFO ROWS
    	addCol(orderPDFTitleTable, "Transition", 10, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Your Current Provider Name", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCustomerOrder().getTransition_provider_name(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Account Holder Name", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCustomerOrder().getTransition_account_holder_name(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Your Current Account Number", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCustomerOrder().getTransition_account_number(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, "Your Porting Number", 3, labelIndent, arial_bold_10, rowPaddingTop, rowPaddingBottom, null);
    	addCol(orderPDFTitleTable, this.getCustomerOrder().getTransition_porting_number(), 7, contentIndent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        // END TRANSITION INFO ROWS
        
        return orderPDFTitleTable;
	}
	
	/**
	 * CUSTOMER FAVOR
	 * @return
	 */
	public PdfPTable createCustomerAgreementTable(){
		
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(102);

    	// BEGIN DISTANCE BETWEEN CUSTOMER AGREEMENT TABLE TITLE AND ROW
        addEmptyRow(table, 10F, 10);
    	// END DISTANCE BETWEEN CUSTOMER AGREEMENT TABLE TITLE AND ROW
        
        // BEGIN PARAMETERS
        Float indent = 6F;
        Float rowPaddingTop = 0F;
        Float rowPaddingBottom = 0F;
        // END PARAMETERS
        
        addCol(table, "I’d like to be kept informed, by various means including electronic messages, of our special offers, deals and", 10, indent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);
        addCol(table, "important information on products and services.", 10, indent, arial_normal_10, rowPaddingTop, rowPaddingBottom, null);

    	// BEGIN CUSTOMER AGREEMENT AREA ENDING PADDING BOTTOM
        addEmptyRow(table, 20F, 10);
    	// END CUSTOMER AGREEMENT AREA ENDING PADDING BOTTOM
        
		return table;
	}
    
    public PdfPTable createOrderDetailTable(){
        PdfPTable orderDetailTable = new PdfPTable(10);
        orderDetailTable.setWidthPercentage(102);
        
        // BEGIN TITLE BAR
        addTitleBar(orderDetailTable, "ORDER DETAIL LIST", arial_bold_12, titleBGColor, titleBorderColor, 10);
        // END TITLE BAR
        
    	// BEGIN DISTANCE TO TOP
        addEmptyRow(orderDetailTable, 6F, 10);
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
        addCol(orderDetailTable, "PLAN", 5, firstColIndent, arial_bold_10, titlePaddingTop, 0F, PdfPCell.ALIGN_LEFT);
        addCol(orderDetailTable, "Data", 1, 0F, arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Term", 1, 0F, arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Monthly", 1, 0F, arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, "Qty", 1, 0F, arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, "Subtotal", 1, 0F, arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT);
        addColBottomBorder(orderDetailTable, " ", 7, 0F, arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_LEFT, borderColor);
        addColBottomBorder(orderDetailTable, "(incl GST)", 1, 14F, arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
        addColBottomBorder(orderDetailTable, " ", 1, 0F, arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);
        addColBottomBorder(orderDetailTable, "(incl GST)", 1, 14F, arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
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
                addCol(orderDetailTable, cod.getDetail_name(), 5, firstColIndent, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_LEFT);
                addCol(orderDetailTable, cod.getDetail_data_flow()+"GB", 1, 0F, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER);
                addCol(orderDetailTable, cod.getDetail_term_period()+" months", 1, 0F, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER);
                addCol(orderDetailTable, String.valueOf(TMUtils.fillDecimal(String.valueOf(cod.getDetail_price()))), 1, 0F, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT);
                addCol(orderDetailTable, String.valueOf(cod.getDetail_unit()), 1, 0F, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT);
                addCol(orderDetailTable, String.valueOf(TMUtils.fillDecimal(String.valueOf(price.multiply(unit).doubleValue()))), 1, 0F, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT);
                // END PLAN ROWS
            	
    		}
        }
        // END PLAN ROW COLUMN
        /**
         * END PLAN LIST
         */
        
        // BEGIN PLAN LIST ENDING LINE SEPARATOR
        addLineRow(orderDetailTable, 10, new BaseColor(159,159,159), 2F);
        // END PLAN LIST ENDING LINE SEPARATOR

        /**
         * BEGIN ADD ON LIST
         */
        
    	// BEGIN DISTANCE TO TOP
        addEmptyRow(orderDetailTable, 16F, 10);
    	// END DISTANCE TO TOP
        
        // BEGIN INITIAL PARAMETERS
        contentPaddingTop = 2F;
        contentPaddingBottom = 6F;
        // END INITIAL PARAMETERS
        
        // BEGIN ADD ON ROW HEADER
        addCol(orderDetailTable, "SERVICE / PRODUCT", 7, firstColIndent, arial_bold_10, titlePaddingTop, titlePaddingBottom, null);
        addCol(orderDetailTable, "Price", 1, 0F, arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Qty", 1, 0F, arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Subtotal", 1, 0F, arial_bold_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT);
        addColBottomBorder(orderDetailTable, " ", 7, 0F, arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_LEFT, borderColor);
        addColBottomBorder(orderDetailTable, "(incl GST)", 1, 0F, arial_normal_7, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
        addColBottomBorder(orderDetailTable, " ", 1, 0F, arial_normal_10, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);
        addColBottomBorder(orderDetailTable, "(incl GST)", 1, 14F, arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
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
                addColBottomBorder(orderDetailTable, cod.getDetail_name(), 5, firstColIndent, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_LEFT, borderColor);
                addColBottomBorder(orderDetailTable, " ", 2, 0F, null, 0F, 0F, null, borderColor);
                addColBottomBorder(orderDetailTable, String.valueOf(TMUtils.fillDecimal(String.valueOf(cod.getDetail_price()))), 1, 0F, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
                addColBottomBorder(orderDetailTable, String.valueOf(cod.getDetail_unit()), 1, 0F, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
                addColBottomBorder(orderDetailTable, String.valueOf(TMUtils.fillDecimal(String.valueOf(price.multiply(unit).doubleValue()))), 1, 0F, arial_normal_10, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);
                // END ADD ON ROWS
            	
    		}
        }
        /**
         * END ADD ON LIST
         */
        
        this.customerOrder.setOrder_total_price(totalPrice);
        // BEGIN transform before tax price and tax
        BigDecimal bdGSTRate = new BigDecimal(this.getGstRate());
        BigDecimal bdTotalPrice = new BigDecimal(totalPrice.toString());
        BigDecimal bdBeforeGSTPrice = new BigDecimal(beforeGSTPrice.toString());
        BigDecimal bdGST15 = new BigDecimal(gst15.toString());
        // BEGIN Calculation Area
        
        // totalPrice divide GSTRate equals to beforeGSTPrice
        bdBeforeGSTPrice = new BigDecimal(bdTotalPrice.divide(bdGSTRate,2, BigDecimal.ROUND_DOWN).toString());
        
        // totalPrice subtract beforeGSTPrice equals to GST15
        bdGST15 = new BigDecimal(bdTotalPrice.subtract(bdBeforeGSTPrice).toString());
        
        // END Calculation Area
        // BEGIN ASSIGNING
        beforeGSTPrice += bdBeforeGSTPrice.doubleValue();
        gst15 += bdGST15.doubleValue();
        // END ASSIGNING
        /**
         * END DETAIL
         */
        
        /**
         * BEGIN DETAIL TOTAL PRICE
         */
        
    	// BEGIN DISTANCE BETWEEN TOP AND SEPARATOR
        addEmptyRow(orderDetailTable, 20F, 10);
    	// END DISTANCE BETWEEN TOP AND SEPARATOR
        
    	// BEGIN SEPARATOR
        addEmptyRow(orderDetailTable, 1F, 7);
        addLineRow(orderDetailTable, 3, borderColor, 1F);
    	// END SEPARATOR
        
        // BEGIN TOTAL BEFORE GST
        addEmptyRow(orderDetailTable, 7);
        addCol(orderDetailTable, "Total before GST", 2, 0F, arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, TMUtils.fillDecimal(String.valueOf(beforeGSTPrice)), 1, 0F, arial_normal_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        // END TOTAL BEFORE GST
        
        // BEGIN GST AT 15%
        addEmptyRow(orderDetailTable, 7);
        addCol(orderDetailTable, "GST at 15%", 2, 0F, arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, TMUtils.fillDecimal(String.valueOf(gst15)), 1, 0F, arial_normal_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        // END GST AT 15%
        
        // BEGIN ORDER TOTAL
        addEmptyRow(orderDetailTable, 7);
        addCol(orderDetailTable, "Order Total", 2, 0F, arial_bold_12, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, TMUtils.fillDecimal(String.valueOf(totalPrice)), 1, 0F, arial_bold_10, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        // END ORDER TOTAL
 
        /**
         * END DETAIL TOTAL PRICE
         */
        return orderDetailTable;
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
    
	public String getGstRate() {
		return gstRate;
	}

	public void setGstRate(String gstRate) {
		this.gstRate = gstRate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public List<CustomerOrderDetail> getCodPlans() {
		return codPlans;
	}

	public void setCodPlans(List<CustomerOrderDetail> codPlans) {
		this.codPlans = codPlans;
	}

	public List<CustomerOrderDetail> getCodAddOns() {
		return codAddOns;
	}

	public void setCodAddOns(List<CustomerOrderDetail> codAddOns) {
		this.codAddOns = codAddOns;
	}

	/**
	 * 
	 * @param table
	 * @param label
	 * @param content
	 * @param rowPaddingTop
	 */
	// ENCAPSULATE CUSTOMER INFO ROW
	public void addCustomerRow(PdfPTable table, String label, String content, Float rowPaddingTop){
        // BEGIN Row
        PdfPCell cell = new PdfPCell(new Phrase(label, arial_bold_10));
        cell.setColspan(3);
        cell.setBorder(0);
        cell.setIndent(8F);
        cell.setPaddingTop(rowPaddingTop);
        table.addCell(cell);
        
        // CONTENT
        cell = new PdfPCell(new Phrase(content, arial_normal_10));
        cell.setColspan(7);
        cell.setBorder(0);
        cell.setIndent(8F);
        cell.setPaddingTop(rowPaddingTop);
        table.addCell(cell);
        // END Row
	}
	
	
}
