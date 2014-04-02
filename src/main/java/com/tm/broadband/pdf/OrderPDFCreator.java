package com.tm.broadband.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tm.broadband.model.Customer;
import com.tm.broadband.model.CustomerOrder;
import com.tm.broadband.model.CustomerOrderDetail;
import com.tm.broadband.util.TMUtils;

public class OrderPDFCreator {
	private Customer customer;
	private CustomerOrder customerOrder;
	private Font arial_normal_8;
	private Font arial_normal_10;
	private Font arial_normal_20;
	private Font arial_lightblue_8;
	private Font arial_darkblue_8;
	private Font arial_white_bold_12;
	private Font arial_brown_bold_10;
	private Font verdana_bold_8;
	private Font verdana_bold_9;
	private BaseColor titleBGColor = new BaseColor(92,184,92);
	
	private Double totalPrice = 0d;
	private Double beforeGSTPrice = 0d;
	private Double gst15 = 0d;
	
	// 15% as default
	private String gstRate = "1.15";
	
	public OrderPDFCreator(){
		try {
			BaseFont bf_arial_normal_8 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_normal_8 = new Font(bf_arial_normal_8, 8, Font.NORMAL);
			BaseFont bf_arial_normal_10 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_normal_10 = new Font(bf_arial_normal_10, 10, Font.NORMAL);
			BaseFont bf_arial_normal_20 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_normal_20 = new Font(bf_arial_normal_20, 20, Font.NORMAL);
			BaseFont bf_arial_darkblue_8 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_darkblue_8 = new Font(bf_arial_darkblue_8, 8, Font.NORMAL, new BaseColor(49,112,143));
			BaseFont bf_arial_lightblue_8 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_lightblue_8 = new Font(bf_arial_lightblue_8, 8, Font.NORMAL, new BaseColor(66,139,205));
			BaseFont bf_arial_white_bold_12 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_white_bold_12 = new Font(bf_arial_white_bold_12, 12, Font.BOLD, BaseColor.WHITE);
			BaseFont bf_arial_brown__bold_10 = BaseFont.createFont("pdf"+File.separator+"font-family/Arial.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.arial_brown_bold_10 = new Font(bf_arial_brown__bold_10, 10, Font.BOLD, new BaseColor(60,118,61));
			BaseFont bf_verdana_bold_8 = BaseFont.createFont("pdf"+File.separator+"font-family/Verdana.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.verdana_bold_8 = new Font(bf_verdana_bold_8, 8, Font.BOLD);
			BaseFont bf_verdana_bold_9 = BaseFont.createFont("pdf"+File.separator+"font-family/Verdana.ttf",BaseFont.WINANSI, BaseFont.EMBEDDED);
			this.verdana_bold_9 = new Font(bf_verdana_bold_9, 9, Font.BOLD);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public OrderPDFCreator(Customer customer, CustomerOrder customerOrder){
		this();
		this.customer = customer;
		this.customerOrder = customerOrder;
	}
	
	public void create() throws DocumentException, IOException{
		
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
        
		// final PDF
		String outputFile = TMUtils.createPath("broadband" + File.separator
				+ "customers" + File.separator + customer.getId()
				+ File.separator + "Order-" + customerOrder.getId()
				+ ".pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();
        // set personal info table
        try {
			document.add(createPersonalInfoTable(writer));
		} catch (IOException e) {
			e.printStackTrace();
		}
        // set order detail table
		document.add(createOrderDetailTable());
		
        document.newPage();
		Image logo = Image.getInstance("pdf"+File.separator+"img"+File.separator+"term_and_condition.jpg");
		logo.scaleAbsolute(596f, 840f);
		logo.setAbsolutePosition(0, 0);
		writer.getDirectContent().addImage(logo);
        
		// CLOSE DOCUMENT
        document.close();
        writer.close();

        // BEGIN If Merge PDF Second Part
//        mergePDF(outputFile, inputFile, term);
        // END If Merge PDF Second Part
	}
	
    public PdfPTable createPersonalInfoTable(PdfWriter writer) throws MalformedURLException, IOException, DocumentException {
		Image logo = Image.getInstance("pdf"+File.separator+"img"+File.separator+"logo_top_final.png");
		logo.scaleAbsolute(171f, 45f);
		logo.setAbsolutePosition(41, 760);
		writer.getDirectContent().addImage(logo);
		
        PdfPTable personalInfoTable = new PdfPTable(8);
        personalInfoTable.setWidthPercentage(98);
        // set cells
        PdfPCell personalInfoTitleCell = new PdfPCell(new Phrase(""));
        personalInfoTitleCell.setColspan(8);
        personalInfoTitleCell.setBorder(0);
        personalInfoTitleCell.setFixedHeight(20F);
        personalInfoTable.addCell(personalInfoTitleCell);
        personalInfoTitleCell = new PdfPCell(new Phrase("Broadband Application Confirmation", arial_normal_20));
        personalInfoTitleCell.setColspan(8);
        personalInfoTitleCell.setBorder(PdfPCell.BOTTOM);
        personalInfoTitleCell.setPaddingBottom(6F);
        personalInfoTitleCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        personalInfoTable.addCell(personalInfoTitleCell);
        // BEGIN title seperator
        personalInfoTitleCell = new PdfPCell(new Phrase(" "));
        personalInfoTitleCell.setColspan(10);
        personalInfoTitleCell.setBorder(0);
        personalInfoTitleCell.setFixedHeight(40F);
        personalInfoTable.addCell(personalInfoTitleCell);
        // END title seperator
        // BEGIN title bar
        personalInfoTitleCell = new PdfPCell(new Phrase("Personal Information", arial_white_bold_12));
        personalInfoTitleCell.setColspan(10);
        personalInfoTitleCell.setBorder(0);
        personalInfoTitleCell.setFixedHeight(18F);
        personalInfoTitleCell.setIndent(10F);
        personalInfoTitleCell.setBackgroundColor(titleBGColor);
        personalInfoTable.addCell(personalInfoTitleCell);
        // END title bar
        /**
         * BEGIN First Row
         */
        // BEGIN Login Name
        PdfPCell personalInfoContentCell = new PdfPCell(new Phrase(this.customer.getLogin_name(), arial_darkblue_8));
        personalInfoContentCell.setColspan(4);
        personalInfoContentCell.setBorder(0);
        personalInfoContentCell.setIndent(20F);
        personalInfoContentCell.setPaddingTop(20F);
        personalInfoTable.addCell(personalInfoContentCell);
        // END Login Name
        // BEGIN Order Date
        personalInfoContentCell = new PdfPCell(new Phrase("Order Date : ", verdana_bold_8));
        personalInfoContentCell.setColspan(2);
        personalInfoContentCell.setBorder(0);
        personalInfoContentCell.setPaddingTop(20F);
        personalInfoContentCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        personalInfoTable.addCell(personalInfoContentCell);
        personalInfoContentCell = new PdfPCell(new Phrase(TMUtils.dateFormatYYYYMMDD(this.customerOrder.getOrder_create_date()), arial_darkblue_8));
        personalInfoContentCell.setColspan(2);
        personalInfoContentCell.setBorder(0);
        personalInfoContentCell.setPaddingTop(20F);
        personalInfoContentCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        personalInfoTable.addCell(personalInfoContentCell);
        // END Order Date
        /**
         * END First Row
         */
        /**
         * BEGIN Second Row
         */
        // BEGIN First&Last Name
        personalInfoContentCell = new PdfPCell(new Phrase(this.customer.getFirst_name()+" "+this.customer.getLast_name(), arial_darkblue_8));
        personalInfoContentCell.setColspan(4);
        personalInfoContentCell.setBorder(0);
        personalInfoContentCell.setIndent(20F);
        personalInfoTable.addCell(personalInfoContentCell);
        // END First&Last Name
        
        // Increase totalPrice
        List<CustomerOrderDetail> cods = this.customerOrder.getCustomerOrderDetails();
        for (CustomerOrderDetail cod : cods) {
            BigDecimal price = new BigDecimal(cod.getDetail_price());
            BigDecimal unit = new BigDecimal(cod.getDetail_unit());
            totalPrice += price.multiply(unit).doubleValue();
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
        
        // BEGIN Total Price
        personalInfoContentCell = new PdfPCell(new Phrase("Total Price : ", verdana_bold_8));
        personalInfoContentCell.setColspan(2);
        personalInfoContentCell.setBorder(0);
        personalInfoContentCell.setPaddingTop(4F);
        personalInfoContentCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        personalInfoTable.addCell(personalInfoContentCell);
        personalInfoContentCell = new PdfPCell(new Phrase("NZD$ "+TMUtils.fillDecimal(String.valueOf(this.customerOrder.getOrder_total_price())), arial_darkblue_8));
        personalInfoContentCell.setColspan(2);
        personalInfoContentCell.setBorder(0);
        personalInfoContentCell.setPaddingTop(4F);
        personalInfoContentCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        personalInfoTable.addCell(personalInfoContentCell);
        // END Total Price
        /**
         * END Second Row
         */
        /**
         * BEGIN Third Row
         */
        // BEGIN Address Name
        personalInfoContentCell = new PdfPCell(new Phrase(this.customer.getEmail(), arial_lightblue_8));
        personalInfoContentCell.setColspan(8);
        personalInfoContentCell.setBorder(0);
        personalInfoContentCell.setIndent(20F);
        personalInfoContentCell.setPaddingTop(4F);
        personalInfoTable.addCell(personalInfoContentCell);
        // END Address Name
        /**
         * END Third Row
         */
        /**
         * BEGIN Fourth Row
         */
        // BEGIN Cellphone Name
        personalInfoContentCell = new PdfPCell(new Phrase(this.customer.getCellphone(), arial_darkblue_8));
        personalInfoContentCell.setColspan(8);
        personalInfoContentCell.setBorder(0);
        personalInfoContentCell.setIndent(20F);
        personalInfoContentCell.setPaddingTop(4F);
        personalInfoTable.addCell(personalInfoContentCell);
        // END Cellphone Name
        /**
         * END Fourth Row
         */
        /**
         * BEGIN Fifth Row
         */
        // BEGIN Address Name
        personalInfoContentCell = new PdfPCell(new Phrase(this.customer.getAddress(), arial_darkblue_8));
        personalInfoContentCell.setColspan(8);
        personalInfoContentCell.setBorder(0);
        personalInfoContentCell.setIndent(20F);
        personalInfoContentCell.setPaddingTop(4F);
        personalInfoContentCell.setPaddingBottom(50F);
        personalInfoTable.addCell(personalInfoContentCell);
        // END Address Name
        /**
         * END Fifth Row
         */
        // END title seperator
        // add cell to table
        return personalInfoTable;
    }
    
    public PdfPTable createOrderDetailTable(){
        PdfPTable orderDetailTable = new PdfPTable(10);
        orderDetailTable.setWidthPercentage(98);
        // set cells
        // BEGIN title seperator
        PdfPCell orderDetailTitleCell = new PdfPCell(new Phrase("Order Detail List", arial_white_bold_12));
        orderDetailTitleCell.setColspan(10);
        orderDetailTitleCell.setBorder(0);
        orderDetailTitleCell.setFixedHeight(18);
        orderDetailTitleCell.setIndent(10F);
        orderDetailTitleCell.setBackgroundColor(titleBGColor);
        orderDetailTable.addCell(orderDetailTitleCell);
        // END title seperator
        /**
         * BEGIN detail title
         */
        // BEGIN params
        Float titelPaddingTop = 20F;
        Float titelPaddingBottom = 10F;
        Float titelIndent = 10F;
        // END params
        // BEGIN First col 4
        orderDetailTitleCell = new PdfPCell(new Phrase("Service / Product", verdana_bold_8));
        orderDetailTitleCell.setColspan(6);
        orderDetailTitleCell.setBorder(0);
        orderDetailTitleCell.setIndent(titelIndent);
        orderDetailTitleCell.setPaddingTop(titelPaddingTop);
        orderDetailTitleCell.setPaddingBottom(titelPaddingBottom);
        orderDetailTable.addCell(orderDetailTitleCell);
        // END First col 4
        // BEGIN Second col 2
        orderDetailTitleCell = new PdfPCell(new Phrase("Unit Price", verdana_bold_8));
        orderDetailTitleCell.setColspan(2);
        orderDetailTitleCell.setBorder(0);
        orderDetailTitleCell.setIndent(titelIndent);
        orderDetailTitleCell.setPaddingTop(titelPaddingTop);
        orderDetailTitleCell.setPaddingBottom(titelPaddingBottom);
        orderDetailTable.addCell(orderDetailTitleCell);
        // END Second col 2
        // BEGIN Third col 2
//        orderDetailTitleCell = new PdfPCell(new Phrase("Discount", arial_51_51_51_bold_10));
//        orderDetailTitleCell.setColspan(2);
//        orderDetailTitleCell.setBorder(0);
//        orderDetailTitleCell.setIndent(titelIndent);
//        orderDetailTitleCell.setPaddingTop(titelPaddingTop);
//        orderDetailTable.addCell(orderDetailTitleCell);
        // END Third col 2
        // BEGIN Fourth col 2
        orderDetailTitleCell = new PdfPCell(new Phrase("Qty", verdana_bold_8));
        orderDetailTitleCell.setColspan(1);
        orderDetailTitleCell.setBorder(0);
        orderDetailTitleCell.setPaddingTop(titelPaddingTop);
        orderDetailTitleCell.setPaddingBottom(titelPaddingBottom);
        orderDetailTable.addCell(orderDetailTitleCell);
        // END Fourth col 2
        // BEGIN Fifth col 2
        orderDetailTitleCell = new PdfPCell(new Phrase("Subtotal", verdana_bold_8));
        orderDetailTitleCell.setColspan(1);
        orderDetailTitleCell.setBorder(0);
        orderDetailTitleCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        orderDetailTitleCell.setPaddingTop(titelPaddingTop);
        orderDetailTitleCell.setPaddingBottom(titelPaddingBottom);
        orderDetailTable.addCell(orderDetailTitleCell);
        // END Fifth col 2
        /**
         * END detail title
         */
        /**
         * BEGIN Detail Content
         */
        Float contentPaddingTop = 4F;
        List<CustomerOrderDetail> cods = this.customerOrder.getCustomerOrderDetails();
        for (CustomerOrderDetail cod : cods) {
        	// BEGIN First col 4
            PdfPCell orderDetailContentCell = new PdfPCell(new Phrase(cod.getDetail_name(),arial_normal_8));
            orderDetailContentCell.setColspan(6);
            orderDetailContentCell.setBorder(0);
            orderDetailContentCell.setIndent(titelIndent);
            orderDetailContentCell.setPaddingTop(contentPaddingTop);
            orderDetailTable.addCell(orderDetailContentCell);
        	// END First col 4
        	// BEGIN Second col 2
            orderDetailContentCell = new PdfPCell(new Phrase(TMUtils.fillDecimal(String.valueOf(cod.getDetail_price())),arial_normal_8));
            orderDetailContentCell.setColspan(2);
            orderDetailContentCell.setBorder(0);
            orderDetailContentCell.setIndent(titelIndent);
            orderDetailContentCell.setPaddingTop(contentPaddingTop);
            orderDetailTable.addCell(orderDetailContentCell);
        	// END Second col 2
        	// BEGIN Third col 2
//            orderDetailContentCell = new PdfPCell(new Phrase(""),arial_normal_10));
//            orderDetailTitleCell.setColspan(2);
//            orderDetailTitleCell.setBorder(0);
//            orderDetailTitleCell.setIndent(titelIndent);
//            orderDetailTitleCell.setPaddingTop(contentPaddingTop);
//            orderDetailTable.addCell(orderDetailTitleCell);
        	// END Third col 2
        	// BEGIN Fourth col 2
            orderDetailContentCell = new PdfPCell(new Phrase(String.valueOf(cod.getDetail_unit()),arial_normal_8));
            orderDetailContentCell.setColspan(1);
            orderDetailContentCell.setBorder(0);
            orderDetailContentCell.setIndent(titelIndent);
            orderDetailContentCell.setPaddingTop(contentPaddingTop);
            orderDetailTable.addCell(orderDetailContentCell);
        	// END Fourth col 2
        	// BEGIN Fifth col 2
            BigDecimal price = new BigDecimal(cod.getDetail_price());
            BigDecimal unit = new BigDecimal(cod.getDetail_unit());
            orderDetailContentCell = new PdfPCell(new Phrase(TMUtils.fillDecimal(String.valueOf(price.multiply(unit))),arial_normal_8));
            orderDetailContentCell.setColspan(1);
            orderDetailContentCell.setBorder(0);
            orderDetailContentCell.setIndent(titelIndent);
            orderDetailContentCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            orderDetailContentCell.setPaddingTop(contentPaddingTop);
            orderDetailTable.addCell(orderDetailContentCell);
        	// END Fifth col 2
		}
        /**
         * End Detail Content
         */
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
        orderDetailTotalCell = new PdfPCell(new Phrase("Total before GST", verdana_bold_9));
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
        orderDetailTotalCell = new PdfPCell(new Phrase("GST at 15%", verdana_bold_9));
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
        orderDetailTotalCell = new PdfPCell(new Phrase("Order Total", verdana_bold_9));
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

	
	
}
