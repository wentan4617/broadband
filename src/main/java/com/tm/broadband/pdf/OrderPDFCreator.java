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
	private Font arial_normal_10;
	private Font arial_colored_normal_11;
	private Font arial_colored_bold_11;
	private Font arial_bold_10;
	private Font arial_bold_12;
	private Font arial_bold_23;
	private Font arial_brown_bold_10;
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
			BaseFont bf_arial_brown__bold_10 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_brown_bold_10 = new Font(bf_arial_brown__bold_10, 10, Font.BOLD, new BaseColor(60,118,61));
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
				if(cod.getDetail_type().contains("plan-term")){
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
	
	public PdfPTable createCustomerOrderBasicInfoTable(){
		
        PdfPTable customerBasicInfoTable = new PdfPTable(14);
        customerBasicInfoTable.setWidthPercentage(102);
    	
    	// BEGIN ORDER CONFIRMATION AREA START PADDING TOP
        addEmptyRow(customerBasicInfoTable, 10F, 14);
    	// END ORDER CONFIRMATION AREA START PADDING TOP
        
        // BEGIN CUSTOMER BASIC INFORMATION
        PdfPCell customerBasicInfoCell = new PdfPCell(new Phrase(this.getCustomer().getTitle()+" "+this.getCustomer().getFirst_name()+" "+this.getCustomer().getLast_name(), arial_colored_normal_11));
        customerBasicInfoCell.setColspan(10);
        customerBasicInfoCell.setBorder(0);
        customerBasicInfoCell.setIndent(10F);
        customerBasicInfoTable.addCell(customerBasicInfoCell);
        customerBasicInfoCell = new PdfPCell(new Phrase("No.", arial_colored_bold_11));
        customerBasicInfoCell.setColspan(2);
        customerBasicInfoCell.setBorder(0);
        customerBasicInfoTable.addCell(customerBasicInfoCell);
        customerBasicInfoCell = new PdfPCell(new Phrase(String.valueOf(this.getCustomerOrder().getId()), arial_colored_normal_11));
        customerBasicInfoCell.setColspan(2);
        customerBasicInfoCell.setBorder(0);
        customerBasicInfoTable.addCell(customerBasicInfoCell);
        // END CUSTOMER BASIC INFORMATION
        
        return customerBasicInfoTable;
	}
	
    public PdfPTable createCustomerInfoTable(PdfWriter writer) throws MalformedURLException, IOException, DocumentException {
        
        PdfPTable customerInfoTable = new PdfPTable(10);
        customerInfoTable.setWidthPercentage(102);
    	
    	// BEGIN CUSTOMER INFO AREA START PADDING TOP
        addEmptyRow(customerInfoTable, 0F, 10);
    	// END CUSTOMER INFO AREA START PADDING TOP

        if(this.getCustomer().getCustomer_type().equals("personal")){
            // BEGIN TITLE BAR
        	addTitleBar(customerInfoTable, "PERSONAL INFORMATION", arial_bold_12, titleBGColor, titleBorderColor, 10);
            // END TITLE BAR
        } else if(this.getCustomer().getCustomer_type().equals("business")){
            // BEGIN TITLE BAR
        	addTitleBar(customerInfoTable, "BUSINESS INFORMATION", arial_bold_12, titleBGColor, titleBorderColor, 10);
            // END TITLE BAR
        }

    	// BEGIN DISTANCE BETWEEN CUSTOMER TABLE TITLE AND ROW
        addEmptyRow(customerInfoTable, 16F, 10);
    	// END DISTANCE BETWEEN CUSTOMER TABLE TITLE AND ROW
        
        // BEGIN PARAMETERS
        Float infoRowPaddingTop = 2F;
        // END PARAMETERS
        
        // BEGIN CUSTOMER(PERSONAL OR BUSINESS) INFO ROWS
        if(this.getCustomer().getCustomer_type().equals("personal")){
        	
            // BEGIN PERSONAL INFO ROWS
            addCustomerRow(customerInfoTable, "Phone", this.getCustomer().getPhone(), infoRowPaddingTop);
            addCustomerRow(customerInfoTable, "Mobile", this.getCustomer().getCellphone(), infoRowPaddingTop);
            addCustomerRow(customerInfoTable, "Email", this.getCustomer().getEmail(), infoRowPaddingTop);
            addCustomerRow(customerInfoTable, "Date of Birth", TMUtils.dateFormatYYYYMMDD(this.getCustomer().getBirth()), infoRowPaddingTop);
            addCustomerRow(customerInfoTable, "Driver License No.", this.getCustomer().getDriver_licence(), infoRowPaddingTop);
            addCustomerRow(customerInfoTable, "Passport No.", this.getCustomer().getPassport(), infoRowPaddingTop);
            addCustomerRow(customerInfoTable, "Passport Country or Origin", this.getCustomer().getCountry(), infoRowPaddingTop);
            // END PERSONAL INFO ROWS
            
        } else if(this.getCustomer().getCustomer_type().equals("business")){
            // BEGIN BUSINESS CUSTOMER
        	
        	

            // END BUSINESS CUSTOMER
        }
        // END CUSTOMER(PERSONAL OR BUSINESS) INFO ROWS

    	// BEGIN CUSTOMER INFO AREA ENDING PADDING BOTTOM
        addEmptyRow(customerInfoTable, 26F, 10);
    	// END CUSTOMER INFO AREA ENDING PADDING BOTTOM
        
        return customerInfoTable;
    }
    
    public PdfPTable createOrderDetailTable(){
        PdfPTable orderDetailTable = new PdfPTable(10);
        orderDetailTable.setWidthPercentage(102);
        
        // BEGIN TITLE BAR
        addTitleBar(orderDetailTable, "ORDER DETAIL LIST", arial_bold_12, titleBGColor, titleBorderColor, 10);
        // END TITLE BAR
        
    	// BEGIN DISTANCE TO TOP
        addEmptyRow(orderDetailTable, 16F, 10);
    	// END DISTANCE TO TOP
        
        // BEGIN INITIAL PARAMETERS
        Float titelIndent = 8F;
        // END INITIAL PARAMETERS

        // BEGIN DETAIL TABLE TITLE
        addCell(orderDetailTable, "Service / Product", 6, titelIndent, arial_bold_10);
        addCell(orderDetailTable, "Unit Price", 2, titelIndent, arial_bold_10);
        addCell(orderDetailTable, "Qty", 1, titelIndent, arial_bold_10);
        addCell(orderDetailTable, "Subtotal", 1, titelIndent, arial_bold_10);
        // END DETAIL TABLE TITLE
        
        // BEGIN DETAIL CONTENT
        Float contentPaddingTop = 4F;
        if(this.getCodPlans().size()>0){
            for (CustomerOrderDetail cod : this.getCodPlans()) {
            	// BEGIN First col 4
                PdfPCell orderDetailContentCell = new PdfPCell(new Phrase(cod.getDetail_name(),arial_normal_10));
                orderDetailContentCell.setColspan(6);
                orderDetailContentCell.setBorder(0);
                orderDetailContentCell.setIndent(titelIndent);
                orderDetailContentCell.setPaddingTop(contentPaddingTop);
                orderDetailTable.addCell(orderDetailContentCell);
            	// END First col 4
            	// BEGIN Second col 2
                orderDetailContentCell = new PdfPCell(new Phrase(TMUtils.fillDecimal(String.valueOf(cod.getDetail_price())),arial_normal_10));
                orderDetailContentCell.setColspan(2);
                orderDetailContentCell.setBorder(0);
                orderDetailContentCell.setIndent(titelIndent);
                orderDetailContentCell.setPaddingTop(contentPaddingTop);
                orderDetailTable.addCell(orderDetailContentCell);
            	// END Second col 2
            	// BEGIN Third col 2
//                orderDetailContentCell = new PdfPCell(new Phrase(""),arial_normal_10));
//                orderDetailTitleCell.setColspan(2);
//                orderDetailTitleCell.setBorder(0);
//                orderDetailTitleCell.setIndent(titelIndent);
//                orderDetailTitleCell.setPaddingTop(contentPaddingTop);
//                orderDetailTable.addCell(orderDetailTitleCell);
            	// END Third col 2
            	// BEGIN Fourth col 2
                orderDetailContentCell = new PdfPCell(new Phrase(String.valueOf(cod.getDetail_unit()==null?0:cod.getDetail_unit()),arial_normal_10));
                orderDetailContentCell.setColspan(1);
                orderDetailContentCell.setBorder(0);
                orderDetailContentCell.setIndent(titelIndent);
                orderDetailContentCell.setPaddingTop(contentPaddingTop);
                orderDetailTable.addCell(orderDetailContentCell);
            	// END Fourth col 2
            	// BEGIN Fifth col 2
                BigDecimal price = new BigDecimal(cod.getDetail_price());
                BigDecimal unit = new BigDecimal(cod.getDetail_unit()==null?0:cod.getDetail_unit());
                
                // Increase totalPrice
                totalPrice += price.multiply(unit).doubleValue();
                orderDetailContentCell = new PdfPCell(new Phrase(TMUtils.fillDecimal(String.valueOf(price.multiply(unit))),arial_normal_10));
                orderDetailContentCell.setColspan(1);
                orderDetailContentCell.setBorder(0);
                orderDetailContentCell.setIndent(titelIndent);
                orderDetailContentCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                orderDetailContentCell.setPaddingTop(contentPaddingTop);
                orderDetailTable.addCell(orderDetailContentCell);
            	// END Fifth col 2
    		}
        }
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
        // BEGIN assigning Area
        beforeGSTPrice += bdBeforeGSTPrice.doubleValue();
        gst15 += bdGST15.doubleValue();
        // END assigning Area
        // End DETAIL CONTENT
        
        /**
         * BEGIN Detail Total Price
         */
        // BEGIN separator
        PdfPCell orderDetailTotalCell = new PdfPCell(new Phrase(""));
        orderDetailTotalCell.setColspan(7);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setFixedHeight(10F);
        orderDetailTable.addCell(orderDetailTotalCell);
        orderDetailTotalCell = new PdfPCell(new Phrase(""));
        orderDetailTotalCell.setColspan(3);
        orderDetailTotalCell.setBorder(PdfPCell.BOTTOM);
        orderDetailTotalCell.setFixedHeight(10F);
        orderDetailTable.addCell(orderDetailTotalCell);
        orderDetailTotalCell = new PdfPCell(new Phrase(""));
        orderDetailTotalCell.setColspan(10);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setFixedHeight(10F);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END separator
        /**
         * BEGIN First Row
         */
        // BEGIN First Col 7
        orderDetailTotalCell = new PdfPCell(new Phrase(""));
        orderDetailTotalCell.setColspan(7);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setPaddingTop(contentPaddingTop);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END First Col 7
        // BEGIN Second Col 2
        orderDetailTotalCell = new PdfPCell(new Phrase("Total before GST", arial_bold_12));
        orderDetailTotalCell.setColspan(2);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        orderDetailTotalCell.setPaddingTop(contentPaddingTop);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END Second Col 2
        // BEGIN Third Col 1
        orderDetailTotalCell = new PdfPCell(new Phrase(TMUtils.fillDecimal(String.valueOf(beforeGSTPrice)), arial_normal_10));
        orderDetailTotalCell.setColspan(1);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        orderDetailTotalCell.setPaddingTop(contentPaddingTop);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END Third Col 1
        /**
         * END First Row
         */
        /**
         * BEGIN Second Row
         */
        // BEGIN First Col 7
        orderDetailTotalCell = new PdfPCell(new Phrase(""));
        orderDetailTotalCell.setColspan(7);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setPaddingTop(contentPaddingTop);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END First Col 7
        // BEGIN Second Col 2
        orderDetailTotalCell = new PdfPCell(new Phrase("GST at 15%", arial_bold_12));
        orderDetailTotalCell.setColspan(2);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        orderDetailTotalCell.setPaddingTop(contentPaddingTop);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END Second Col 2
        // BEGIN Third Col 1
        orderDetailTotalCell = new PdfPCell(new Phrase(TMUtils.fillDecimal(String.valueOf(gst15)), arial_normal_10));
        orderDetailTotalCell.setColspan(1);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        orderDetailTotalCell.setPaddingTop(contentPaddingTop);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END Third Col 1
        /**
         * END Second Row
         */
        /**
         * BEGIN Third Row
         */
        // BEGIN First Col 7
        orderDetailTotalCell = new PdfPCell(new Phrase(""));
        orderDetailTotalCell.setColspan(7);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setPaddingTop(contentPaddingTop);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END First Col 7
        // BEGIN Second Col 2
        orderDetailTotalCell = new PdfPCell(new Phrase("Order Total", arial_bold_12));
        orderDetailTotalCell.setColspan(2);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        orderDetailTotalCell.setPaddingTop(contentPaddingTop);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END Second Col 2
        // BEGIN Third Col 1
        orderDetailTotalCell = new PdfPCell(new Phrase(TMUtils.fillDecimal(String.valueOf(totalPrice)), arial_brown_bold_10));
        orderDetailTotalCell.setColspan(1);
        orderDetailTotalCell.setBorder(0);
        orderDetailTotalCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        orderDetailTotalCell.setPaddingTop(contentPaddingTop);
        orderDetailTable.addCell(orderDetailTotalCell);
        // END Third Col 1
        /**
         * END Third Row
         */
        /**
         * END Detail Total Price
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
	 * @param table					PdfPTable object
	 * @param label					row label
	 * @param content				row content
	 * @param rowPaddingTop			distance to top
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

	/**
	 * 
	 * @param table
	 * @param title
	 * @param colspan
	 * @param indent
	 * @param font
	 */
	// ENCAPSULATE DETAIL CELL TITLE
	public void addCell(PdfPTable table, String title, Integer colspan, Float indent, Font font){
        PdfPCell cell = new PdfPCell(new Phrase(title, font));
        cell.setColspan(colspan);
        cell.setBorder(0);
        cell.setIndent(indent);
        table.addCell(cell);
	}
	
	
}
