package com.tm.broadband.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tm.broadband.model.CustomerOrderOnsite;
import com.tm.broadband.model.CustomerOrderOnsiteDetail;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.util.itext.ITextFont;
import com.tm.broadband.util.itext.ITextUtils;

/** 
* Generates Ordering PDF
* 
* @author DONG CHEN
*/ 
public class OnsitePDFCreator extends ITextUtils {
	
	private CustomerOrderOnsite coo;
	
	private BaseColor titleBGColor = new BaseColor(240,210,210);
	private BaseColor titleBorderColor = new BaseColor(159,159,159);
	
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
	
	// BEGIN Order Onsite Detail
	List<CustomerOrderOnsiteDetail> coods = new ArrayList<CustomerOrderOnsiteDetail>();
	// END Order Onsite Detail
	// END Temporary Variables
	
	public OnsitePDFCreator(){}
	
	public OnsitePDFCreator(CustomerOrderOnsite coo){
		this.coo = coo;
	}
	
	public Map<String, Object> create() throws DocumentException, IOException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// DIFFERENTIATES ORDER DETAILS
		if(this.getCoo().getCoods().size()>0){
			List<CustomerOrderOnsiteDetail> cods = this.getCoo().getCoods();
			for (CustomerOrderOnsiteDetail  cod: cods) {
				coods.add(cod);
			}
		}
		
        Document document = new Document(PageSize.A4);
        
		// Output PDF Path, e.g.: onsite_form_600089.pdf
		String outputFile = TMUtils.createPath("broadband" + File.separator
				+ "customers" + File.separator + this.getCoo().getCustomer_id() + File.separator
				+ "onsite" + File.separator + "onsite_form_" + this.getCoo().getOrder_id() + "_" + this.getCoo().getId()
				+ ".pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        
        // OPEN DOCUMENT
        document.open();
        
    	// company_logo
        File companyLogoFile = new File(pdf_resources_path + "common_company_logo.png");
		if(companyLogoFile.exists()){
	    	addImage(writer, pdf_resources_path + "common_company_logo.png", 108.75F, 51.00000000000001F, 32F, 772F);
        } else {
	    	addImage(writer, "pdf"+File.separator+"img"+File.separator+"company_logo.png", 108.75F, 51.00000000000001F, 32F, 772F);
        }
        
    	
        setGlobalBorderWidth(0);
    	
        // set order PDF title table
		document.add(createOrderPDFTitleTable());
    	
        // set customer(personal or business) basic info table
		document.add(createCustomerOrderBasicInfoTable());
		
        // set customer(personal or business) info table
		document.add(createCustomerInfoTable(writer, document));
		
        // set order detail table
		document.add(createOrderDetailTable());

        // set onsite description table
		document.add(createOnsiteDescription());

        // set onsite checklist table
		document.add(createOnsiteChecklist());
		
		// set FIRST PAGE bottom texts
		// Bottom
		addText(writer, "Customer Signature ____________________", ITextFont.arial_bold_10, 40F, 46F);
		addText(writer, "Technician Signature ____________________", ITextFont.arial_bold_10, 264F, 46F);
		addText(writer, "Print from www.cyberpark.co.nz", ITextFont.arial_normal_8, 190F, 26F);
		addText(writer, "0800 2 CYBER (29237)", ITextFont.arial_normal_8, 360F, 26F);
    	// two-dimensional_code
        File twoDimensionalCodeFile = new File(pdf_resources_path + "two_dimensional_code.png");
		if(twoDimensionalCodeFile.exists()){
	    	addImage(writer, pdf_resources_path + "two_dimensional_code.png", 82.50000000000001F, 82.50000000000001F, 486F, 24F);
        } else {
        	addImage(writer, "pdf"+File.separator+"img"+File.separator+"two-dimensional_code.png", 82.50000000000001F, 82.50000000000001F, 486F, 24F);
        }
        
		// CLOSE DOCUMENT
        document.close();
		// CLOSE WRITER
        writer.close();

        // BEGIN If Merge PDF Second Part
//        mergePDF(outputFile, inputFile, term);
        // END If Merge PDF Second Part
        
        map.put("totalPrice", totalPrice);
        map.put("path", outputFile);
        
        return map;
	}
	
	public PdfPTable createOrderPDFTitleTable(){
		
        PdfPTable orderPDFTitleTable = newTable().columns(14).widthPercentage(102F).o();
    	
    	// BEGIN ORDER CONFIRMATION AREA PADDING TOP
        addEmptyCol(orderPDFTitleTable, 10F, 14);
    	// END ORDER CONFIRMATION AREA PADDING TOP

        // BEGIN ORDER CONFIRMATION TITLE
        addPDFTitle(orderPDFTitleTable, "DISPATCH LIST ( "+this.getCoo().getOnsite_type()+" )", ITextFont.arial_coral_bold_18, 22F, PdfPCell.BOTTOM, PdfPCell.ALIGN_RIGHT, 14);
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
        addEmptyCol(customerBasicInfoTable, 14F, 14);
    	// END CUSTOMER BASIC INFO PADDING TOP

        addCol(customerBasicInfoTable, "", 9, 10F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, "Customer Id", 2, 0F, ITextFont.arial_colored_bold_11, 0F, 4F, PdfPCell.ALIGN_RIGHT);
        addCol(customerBasicInfoTable, String.valueOf(this.getCoo().getCustomer_id()), 3, 10F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        
        // BEGIN CUSTOMER BASIC INFORMATION
        if(this.getCoo().getCustomer_type().equals("personal")){
        	
        	// BEGIN PERSONAL BASIC INFORMATION
            addCol(customerBasicInfoTable, (this.getCoo().getTitle()!=null?this.getCoo().getTitle()+" ":"")+this.getCoo().getFirst_name()+" "+this.getCoo().getLast_name(), 9, 10F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
            // END PERSONAL BASIC INFORMATION
            
        } else if(this.getCoo().getCustomer_type().equals("business")){
        	
        	// BEGIN BUSINESS BASIC INFORMATION
            addCol(customerBasicInfoTable, this.getCoo().getOrg_name(), 9, 10F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        	// END BUSINESS BASIC INFORMATION
            
        }
        addCol(customerBasicInfoTable, "Order Id", 2, 0F, ITextFont.arial_colored_bold_11, 0F, 4F, PdfPCell.ALIGN_RIGHT);
        addCol(customerBasicInfoTable, String.valueOf(this.getCoo().getOrder_id()), 3, 10F, ITextFont.arial_colored_normal_11, 0F, 4F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, this.getCoo().getAddress(), 9, 10F, ITextFont.arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
        addCol(customerBasicInfoTable, "Onsite Date", 2, 0F, ITextFont.arial_colored_bold_11, 0F, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(customerBasicInfoTable, TMUtils.retrieveMonthAbbrWithDate(this.getCoo().getOnsite_date()), 3, 10F, ITextFont.arial_colored_normal_11, 0F, 0F, PdfPCell.ALIGN_LEFT);
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

    	// BEGIN CUSTOMER INFO PADDING TOP
        addEmptyCol(customerInfoTable, 10F, 10);
    	// END CUSTOMER INFO PADDING TOP
        
        // BEGIN CUSTOMER INFO TITLE BAR
        if(this.getCoo().getCustomer_type()!=null && this.getCoo().getCustomer_type().equals("personal")){
        	addTitleBar(customerInfoTable, "PERSONAL INFORMATION", ITextFont.arial_bold_12, titleBGColor, titleBorderColor, 10, 4F);
        } else if(this.getCoo().getCustomer_type().equals("business")){
        	addTitleBar(customerInfoTable, "BUSINESS INFORMATION", ITextFont.arial_bold_12, titleBGColor, titleBorderColor, 10, 4F);
        }
        // END CUSTOMER INFO TITLE BAR
    	
    	// BEGIN CUSTOMER INFO PADDING TOP
        addEmptyCol(customerInfoTable, 4F, 10);
    	// END CUSTOMER INFO PADDING TOP

        // BEGIN PARAMETERS
        Float labelIndent = 8F;
        Float contentIndent = 30F;
        Float rowPaddingTop = 3F;
        Float rowPaddingBottom = 0F;
        // END PARAMETERS
        
        // BEGIN CUSTOMER(PERSONAL OR BUSINESS) INFO ROWS
        if(this.getCoo().getCustomer_type().equals("personal")){
        	
            // BEGIN PERSONAL INFO ROWS
        	addCol(customerInfoTable, "Mobile", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getMobile(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Email", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getEmail(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
            // END PERSONAL INFO ROWS
            
        } else if(this.getCoo().getCustomer_type().equals("business")){
        	
            // BEGIN BUSINESS INFO ROWS
        	addCol(customerInfoTable, "Organization Name", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getOrg_name(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Organization Type", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getOrg_type(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Trading Name", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getOrg_trading_name(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Registration No.", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getOrg_register_no(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Date Incoporated", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, TMUtils.retrieveMonthAbbrWithDate(this.getCoo().getOnsite_date()), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);

        	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
            addEmptyCol(customerInfoTable, 10F, 10);
        	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
            
        	// BEGIN HOLDER INFO ROWS
        	addTitleBar(customerInfoTable, "ACCOUNT HOLDER", ITextFont.arial_bold_8, titleBGColor, titleBorderColor, 10, 2F);

        	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
            addEmptyCol(customerInfoTable, 2F, 10);
        	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
            
        	addCol(customerInfoTable, "Full name", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getHolder_name(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Job title", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getHolder_job_title(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Phone", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getHolder_phone(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, "Email", 3, labelIndent, ITextFont.arial_bold_8, rowPaddingTop, rowPaddingBottom, null);
        	addCol(customerInfoTable, this.getCoo().getHolder_email(), 7, contentIndent, ITextFont.arial_normal_8, rowPaddingTop, rowPaddingBottom, null);
        	// BEGIN HOLDER INFO ROWS
            
            // END BUSINESS INFO ROWS
            
        }
        // END CUSTOMER(PERSONAL OR BUSINESS) INFO ROWS

    	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
        addEmptyCol(customerInfoTable, 8F, 10);
    	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
        
        return customerInfoTable;
    }
    
    public PdfPTable createOrderDetailTable(){
        PdfPTable orderDetailTable = newTable().columns(10).widthPercentage(102F).o();

    	// BEGIN DISTANCE TO TOP
        addEmptyCol(orderDetailTable, 10F, 10);
    	// END DISTANCE TO TOP
        
        // BEGIN TITLE BAR
        addTitleBar(orderDetailTable, "ONSITE DETAIL LIST", ITextFont.arial_bold_12, titleBGColor, titleBorderColor, 10, 4F);
        // END TITLE BAR
        
    	// BEGIN DISTANCE TO TOP
        addEmptyCol(orderDetailTable, 6F, 10);
    	// END DISTANCE TO TOP

        /**
         * BEGIN DETAIL
         */
        // BEGIN INITIAL PARAMETERS
        Float firstColIndent = 8F;
        Float titlePaddingTop = 0F;
        Float titlePaddingBottom = 2F;
        Float contentPaddingTop = 6F;
        Float contentPaddingBottom = 6F;
        BaseColor borderColor = new BaseColor(159,159,159);
        // END INITIAL PARAMETERS

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
        addCol(orderDetailTable, "SERVICE / PRODUCT", 7, firstColIndent, ITextFont.arial_bold_8, titlePaddingTop, titlePaddingBottom, null);
        addCol(orderDetailTable, "Price", 1, 0F, ITextFont.arial_bold_8, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Qty", 1, 0F, ITextFont.arial_bold_8, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER);
        addCol(orderDetailTable, "Subtotal", 1, 0F, ITextFont.arial_bold_8, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT);
        addColBottomBorder(orderDetailTable, " ", 7, 0F, ITextFont.arial_normal_8, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_LEFT, borderColor);
        addColBottomBorder(orderDetailTable, this.getCoo().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 0F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
        addColBottomBorder(orderDetailTable, " ", 1, 0F, ITextFont.arial_normal_8, titlePaddingTop, titlePaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);
        addColBottomBorder(orderDetailTable, this.getCoo().getCustomer_type().equals("business") ? "(plus GST)" : "(incl GST)", 1, 14F, ITextFont.arial_normal_7, titlePaddingTop, titlePaddingBottom, null, borderColor);
        // END ADD ON ROW HEADER
        
        if(this.getCoods().size()>0){
        	int count = 0;
            for (CustomerOrderOnsiteDetail cood : this.getCoods()) {
            	count++;
            	
            	// BEGIN PREVENT NULL POINTER EXCEPTION
            	if(cood.getPrice()==null){
                	cood.setPrice(0d);
            	}
            	if(cood.getUnit()==null){
                	cood.setUnit(0);
            	}
            	// END PREVENT NULL POINTER EXCEPTION
            	
                BigDecimal price = new BigDecimal(cood.getPrice());
                BigDecimal unit = new BigDecimal(cood.getUnit());
                
                // INCREASE TOTAL PRICE
                totalPrice += price.multiply(unit).doubleValue();

                // BEGIN ADD ON ROWS
                if(count==this.getCoods().size()){
	                addCol(orderDetailTable, cood.getName(), 5, firstColIndent, ITextFont.arial_normal_8, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_LEFT);
	                addCol(orderDetailTable, " ", 2, 0F, null, 0F, 0F, null);
	                addCol(orderDetailTable, String.valueOf(TMUtils.fillDecimalPeriod(String.valueOf(cood.getPrice()))), 1, 0F, ITextFont.arial_normal_8, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER);
	                addCol(orderDetailTable, String.valueOf(cood.getUnit()), 1, 0F, ITextFont.arial_normal_8, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER);
                    addCol(orderDetailTable, String.valueOf(cood.getPrice()!=null && cood.getPrice()<=0 ? "FREE" : TMUtils.fillDecimalPeriod(String.valueOf(price.multiply(unit).doubleValue()))), 1, 0F, cood.getPrice()!=null && cood.getPrice()<=0 ? ITextFont.arial_bold_italic_10 : ITextFont.arial_normal_8, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT);
                } else {
	                addColBottomBorder(orderDetailTable, cood.getName(), 5, firstColIndent, ITextFont.arial_normal_8, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_LEFT, borderColor);
	                addColBottomBorder(orderDetailTable, " ", 2, 0F, null, 0F, 0F, null, borderColor);
	                addColBottomBorder(orderDetailTable, String.valueOf(TMUtils.fillDecimalPeriod(String.valueOf(cood.getPrice()))), 1, 0F, ITextFont.arial_normal_8, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
	                addColBottomBorder(orderDetailTable, String.valueOf(cood.getUnit()), 1, 0F, ITextFont.arial_normal_8, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_CENTER, borderColor);
                    addColBottomBorder(orderDetailTable, String.valueOf(cood.getPrice()!=null && cood.getPrice()<=0 ? "FREE" : TMUtils.fillDecimalPeriod(String.valueOf(price.multiply(unit).doubleValue()))), 1, 0F, cood.getPrice()!=null && cood.getPrice()<=0 ? ITextFont.arial_bold_italic_10 : ITextFont.arial_normal_8, contentPaddingTop, contentPaddingBottom, PdfPCell.ALIGN_RIGHT, borderColor);	
                }
                // END ADD ON ROWS
                
    		}
        }
        /**
         * END ADD ON LIST
         */
        
        this.getCoo().setOnsite_charge(totalPrice);
        // BEGIN transform before tax price and tax
        BigDecimal bdGSTRate = new BigDecimal(this.getGstRate());
        BigDecimal bdGSTRate2 = new BigDecimal(this.gstRate2);
        BigDecimal bdTotalPrice = new BigDecimal(totalPrice.toString());
        BigDecimal bdBeforeGSTPrice = new BigDecimal(beforeGSTPrice.toString());
        BigDecimal bdGST = new BigDecimal(gst.toString());
        // BEGIN Calculation Area
        
        if(this.getCoo().getCustomer_type().equals("business")){
            
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
        addEmptyCol(orderDetailTable, 4F, 10);
    	// END DISTANCE BETWEEN TOP AND SEPARATOR
        
    	// BEGIN SEPARATOR
        addEmptyCol(orderDetailTable, 1F, 7);
        addLineCol(orderDetailTable, 3, borderColor, 1F);
    	// END SEPARATOR
        
        // BEGIN TOTAL BEFORE GST
        addCol(orderDetailTable, "Total before GST", 9, 0F, ITextFont.arial_bold_9, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(beforeGSTPrice)), 1, 0F, ITextFont.arial_normal_9, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        // END TOTAL BEFORE GST
        
        if(this.getCoo().getCustomer_type().equals("business")){
            
            // BEGIN GST
            addCol(orderDetailTable, "GST at " + this.businessGST, 9, 0F, ITextFont.arial_bold_9, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(gst)), 1, 0F, ITextFont.arial_normal_9, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            // END GST
        } else {
            
            // BEGIN GST
            addCol(orderDetailTable, "GST at " + this.personalGST, 9, 0F, ITextFont.arial_bold_9, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(gst)), 1, 0F, ITextFont.arial_normal_9, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
            // END GST
        }
        
        // BEGIN ONSITE TOTAL
        addCol(orderDetailTable, "Total Charge", 9, 0F, ITextFont.arial_bold_9, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        addCol(orderDetailTable, TMUtils.fillDecimalPeriod(String.valueOf(totalPrice)), 1, 0F, ITextFont.arial_bold_9, contentPaddingTop, 0F, PdfPCell.ALIGN_RIGHT);
        // END ORDER TOTAL
 
        /**
         * END DETAIL TOTAL PRICE
         */
        return orderDetailTable;
    }
	
	/**
	 * DESCRIPTION
	 * @return
	 */
	public PdfPTable createOnsiteDescription(){
		
        PdfPTable onsiteDescriptionTable = newTable().columns(14).widthPercentage(98F).o();
    	
    	// BEGIN CUSTOMER BASIC INFO PADDING TOP
        addEmptyCol(onsiteDescriptionTable, 24F, 14);
    	// END CUSTOMER BASIC INFO PADDING TOP

        addCol(onsiteDescriptionTable, "Description").colspan(2).font(ITextFont.arial_coral_bold_11).paddingTo("b", 4f).borderColor(new BaseColor(240,128,128)).border("b", 1F).o();
        addCol(onsiteDescriptionTable, "").colspan(12).o();
        addCol(onsiteDescriptionTable, this.getCoo().getOnsite_description()).colspan(14).font(ITextFont.arial_normal_8).paddingTo("b", 4f).indent(20F).fixedHeight(100F).o();
        
        return onsiteDescriptionTable;
	}
	
	/**
	 * ONSITE CHECKLIST
	 * @return
	 */
	public PdfPTable createOnsiteChecklist(){
		
        PdfPTable onsiteChecklistTable = newTable().columns(14).widthPercentage(98F).o();
    	
    	// BEGIN ONSITE CHECKLIST INFO PADDING TOP
        addEmptyCol(onsiteChecklistTable, 14F, 14);
    	// END ONSITE CHECKLIST INFO PADDING TOP

        addCol(onsiteChecklistTable, "Checklist").colspan(2).font(ITextFont.arial_coral_bold_11).paddingTo("b", 4f).borderColor(new BaseColor(240,128,128)).border("b", 1F).o();
        addCol(onsiteChecklistTable, "").colspan(12).o();
        addCol(onsiteChecklistTable, this.getCoo().getOnsite_description()).colspan(14).font(ITextFont.arial_normal_8).paddingTo("b", 4f).indent(20F).o();

        addEmptyCol(onsiteChecklistTable, 4F, 14);
        
        addCol(onsiteChecklistTable, "1、 Check Cable Connection").colspan(14).font(ITextFont.arial_bold_8).border(1F).paddingV(4F).o();
        addCol(onsiteChecklistTable, "1. Check Jackpot, Filter, ADSL Connection (   )").colspan(7).font(ITextFont.arial_normal_7).border(1F).paddingV(2F).o();
        addCol(onsiteChecklistTable, "2. Check Phoneline Connection / VoIP Connection (   )").colspan(7).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();

        addCol(onsiteChecklistTable, "2、 Check Router Configuration").colspan(14).font(ITextFont.arial_bold_8).border(1F).paddingV(4F).borderWidth("t", 0F).o();
        addCol(onsiteChecklistTable, "1、 WAN (   )").colspan(2).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();
        addCol(onsiteChecklistTable, "2、Wifi (   )").colspan(2).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();
        addCol(onsiteChecklistTable, "3、 VoIP (   )").colspan(2).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();
        addCol(onsiteChecklistTable, "4、 Remote Support (   )").colspan(2).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();
        addCol(onsiteChecklistTable, "5、 Check Light (   )").colspan(6).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();

        addCol(onsiteChecklistTable, "3、 Check VoIP Number On VOS (Optional)").colspan(14).font(ITextFont.arial_bold_8).border(1F).paddingV(4F).borderWidth("t", 0F).o();
        addCol(onsiteChecklistTable, "1、 Check Balance (   )").colspan(3).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();
        addCol(onsiteChecklistTable, "2、 Check CallRate (   )").colspan(3).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();
        addCol(onsiteChecklistTable, "3、 Check Advaned Setting (   )").colspan(3).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();
        addCol(onsiteChecklistTable, "4、 Check Prefix (   )").colspan(5).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();

        addCol(onsiteChecklistTable, "4、 Check Test").colspan(14).font(ITextFont.arial_bold_8).border(1F).paddingV(4F).borderWidth("t", 0F).o();
        addCol(onsiteChecklistTable, "1、 Test Phone / VoIP / Wifi / Wired Speed (   )").colspan(14).font(ITextFont.arial_normal_7).border(1F).paddingV(3F).o();
        
        return onsiteChecklistTable;
	}
	

	public String getGstRate() {
		return gstRate;
	}

	public List<CustomerOrderOnsiteDetail> getCoods() {
		return coods;
	}

	public void setCoods(List<CustomerOrderOnsiteDetail> coods) {
		this.coods = coods;
	}

	public CustomerOrderOnsite getCoo() {
		return coo;
	}

	public void setCoo(CustomerOrderOnsite coo) {
		this.coo = coo;
	}
	
	
}
