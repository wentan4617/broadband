package com.tm.broadband.util.itext;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ITextUtils {
	
	// BEGIN MEMBER VARIABLE
	private SimplePDFCell simplePDFCell;
	private SimplePDFTable simplePDFTable;
	// END MEMBER VARIABLE
	

	// BEGIN INITIALIZATION
	public ITextUtils() {
		this.simplePDFCell=new SimplePDFCell();
		this.simplePDFTable=new SimplePDFTable();
	}
	// END INITIALIZATION
	
	// BEGIN GETTER AND SETTER
	private SimplePDFCell getSimplePDFCell() {
		return simplePDFCell;
	}
	private SimplePDFTable getSimplePDFTable() {
		return simplePDFTable;
	}
	protected void setGlobalBorderWidth(Integer globalBorderWidth) {
		this.getSimplePDFCell().setGlobalBorderWidth(globalBorderWidth);
	}
	protected void setGlobalBorderColor(BaseColor globalBorderColor) {
		this.getSimplePDFCell().setGlobalBorderColor(globalBorderColor);
	}
	// END GETTER AND SETTER
	


	/**
	 * 
	 * @param table
	 * @param title
	 * @param font
	 * @param paddingBottom
	 * @param border
	 * @param align
	 * @param colspan
	 */
	// ENCAPSULATE PDF TITLE
	public void addPDFTitle(PdfPTable table, String title, Font font, Float paddingBottom, Integer border, Integer align, Integer colspan){
        PdfPCell cell = new PdfPCell(new Phrase(title, font));
        cell.setColspan(colspan);
        cell.setBorder(border);
        cell.setPaddingBottom(paddingBottom);
        if(align!=null){
            cell.setHorizontalAlignment(align);
        }
        table.addCell(cell);
	}
	
	/**
	 * 
	 * @param writer
	 * @param resource
	 * @param width
	 * @param height
	 * @param positionX
	 * @param positionY
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	// ENCAPSULATE IMAGE
	public void addImage(PdfWriter writer, String resource, Float width, Float height, Float positionX, Float positionY) throws DocumentException, MalformedURLException, IOException{
		Image logo = Image.getInstance(resource);
		logo.scaleAbsolute(width, height);
		logo.setAbsolutePosition(positionX, positionY);
		writer.getDirectContent().addImage(logo);
	}

	/**
	 * 
	 * @param table
	 * @param title
	 * @param font
	 * @param bgColor
	 * @param borderColor
	 * @param colspan
	 */
	// ENCAPSULATE TITLE BAR
	public void addTitleBar(PdfPTable table, String title, Font font, BaseColor bgColor, BaseColor borderColor, Integer colspan, Float zoom){
        // BEGIN TITLE BAR
        PdfPCell cell = new PdfPCell(new Phrase(title, font));
        cell.setColspan(colspan);
        cell.setBorder(PdfPCell.TOP);
        cell.setBorderColor(borderColor);
        cell.setIndent(10F);
        cell.setPaddingTop(zoom);
        cell.setPaddingBottom(zoom+2F);
        cell.setBackgroundColor(bgColor);
        table.addCell(cell);
        // BOTTOM BORDER
        cell = new PdfPCell(new Phrase(""));
        cell.setColspan(colspan);
        cell.setBorder(PdfPCell.TOP);
        cell.setBorderColor(borderColor);
        table.addCell(cell);
        // END TITLE BAR
	}
	
	/**
	 * 
	 * @param table
	 * @param height
	 * @param colspan
	 */
	// ENCAPSULATE EMPTY COLUMN(S)
	public void addEmptyCol(PdfPTable table, Float height, Integer colspan){
		PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(colspan);
        cell.setBorder(0);
        if(height!=null){
            cell.setFixedHeight(height);
        }
        table.addCell(cell);
	}

	/**
	 * 
	 * @param table
	 * @param colspan
	 */
	// ENCAPSULATE EMPTY COLUMN(S)
	public void addEmptyCol(PdfPTable table, Integer colspan){
		PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(colspan);
        cell.setBorder(0);
        table.addCell(cell);
	}

	/**
	 * 
	 * @param table
	 * @param rowspan
	 */
	// ENCAPSULATE EMPTY COLUMN(S)
	public void addEmptyRow(PdfPTable table, Integer rowspan){
		PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setRowspan(rowspan);
        cell.setBorder(0);
        table.addCell(cell);
	}

	/**
	 * 
	 * @param table
	 * @param colspan
	 * @param color
	 * @param height
	 */
	// ENCAPSULATE LINE COLUMN(S)
	public void addLineCol(PdfPTable table, Integer colspan, BaseColor color, Float height){
		PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(colspan);
        cell.setBorder(0);
        cell.setBackgroundColor(color);
        cell.setFixedHeight(height);
        table.addCell(cell);
	}

	/**
	 * 
	 * @param table
	 * @param phrase
	 * @param colspan
	 * @param indent
	 * @param font
	 * @param paddingTop
	 * @param paddingBottom
	 * @param align
	 */
	// ENCAPSULATE COLUMN
	public void addCol(PdfPTable table, String phrase, Integer colspan, Float indent, Font font, Float paddingTop, Float paddingBottom, Integer align){
		PdfPCell cell = null;
		if(font != null){
			cell = new PdfPCell(new Phrase(phrase, font));
		} else {
			cell = new PdfPCell(new Phrase(phrase));
		}
        cell.setColspan(colspan);
        cell.setBorder(0);
        cell.setIndent(indent);
        cell.setPaddingTop(paddingTop);
        cell.setPaddingBottom(paddingBottom);
        if(align!=null){
            cell.setHorizontalAlignment(align);
        }
        table.addCell(cell);
	}

	/**
	 * 
	 * @param table
	 * @param phrase
	 * @param colspan
	 * @param indent
	 * @param font
	 * @param paddingTop
	 * @param paddingBottom
	 * @param align
	 * @param color
	 */
	// ENCAPSULATE COLUMN WITH BORDER
	public void addColBottomBorder(PdfPTable table, String phrase, Integer colspan, Float indent, Font font, Float paddingTop, Float paddingBottom, Integer align, BaseColor color){
		PdfPCell cell = null;
		if(font != null){
			cell = new PdfPCell(new Phrase(phrase, font));
		} else {
			cell = new PdfPCell(new Phrase(phrase));
		}
        cell.setColspan(colspan);
        cell.setBorder(PdfPCell.BOTTOM);
        cell.setBorderColor(color);
        cell.setIndent(indent);
        cell.setPaddingTop(paddingTop);
        cell.setPaddingBottom(paddingBottom);
        if(align!=null){
            cell.setHorizontalAlignment(align);
        }
        table.addCell(cell);
	}
	
	/**
	 * 
	 * @param writer
	 * @param text
	 * @param font
	 * @param x
	 * @param y
	 */
	// ENCAPSULATE TEXT
	public void addText(PdfWriter writer, String text, Font font, Float x, Float y){
		PdfContentByte canvas = writer.getDirectContentUnder();
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(text, font), x, y, 0);
	}
	
	/**
	 * 
	 * @param outerTable
	 * @param innerTable
	 * @param colspan
	 * @param borderColor
	 */
	// ENCAPSULATE TABLE IN CELL
	public void addTableInCol(PdfPTable outerTable, PdfPTable innerTable, Integer colspan, BaseColor borderColor, Float borderWidth){
        PdfPCell cell = new PdfPCell();
        cell.setColspan(colspan);
        cell.setBorderWidth(borderWidth);
        cell.setBorderColor(borderColor);
        cell.addElement(innerTable);
        outerTable.addCell(cell);
	}
	
	/**
	 * 
	 * @param colspan
	 * @param percentage
	 * @return
	 */
	public PdfPTable newTable(Integer colspan, Float percentage){
        PdfPTable table = new PdfPTable(colspan);
        table.setWidthPercentage(percentage);
		return table;
	}
	
	/*******************************************************
	 * 		BEGIN SimplePDFCell METHODS
	 */

	/**
	 * 
	 * @param table
	 * @param phrase
	 * @return
	 */
	// ENCAPSULATE COLUMN
	public SimplePDFCell addCol(PdfPTable table, String phrase){
		this.getSimplePDFCell().setPhrase(phrase);
        this.getSimplePDFCell().setTable(table);
        this.getSimplePDFCell().setCell(new PdfPCell(new Phrase(this.getSimplePDFCell().getPhrase())));
        return this.getSimplePDFCell();
	}
	
	/**
	 * 
	 * @param outerTable
	 * @param innerTable
	 * @return
	 */
	// ENCAPSULATE TABLE IN CELL
	public SimplePDFCell addTableInCol(PdfPTable outerTable, PdfPTable innerTable){
        this.getSimplePDFCell().setCell(new PdfPCell(new Phrase()));
        this.getSimplePDFCell().getCell().addElement(innerTable);
        this.getSimplePDFCell().setTable(outerTable);
        return this.getSimplePDFCell();
	}
	
	/*******************************************************
	 * 		END SimplePDFCell METHODS
	 */
	
	
	// **************************************************************************************************************
	// BEGIN ANOTHER UTILITY
	// **************************************************************************************************************
	
	
	/*******************************************************
	 * 		BEGIN SimplePDFTable METHODS
	 */
	
	/**
	 * 
	 * @param colspan
	 * @return
	 */
	public SimplePDFTable newTable(Integer colspan){
		this.getSimplePDFTable().setColspan(colspan);
		return this.getSimplePDFTable();
	}
	
	/**
	 * 
	 * @return
	 */
	public SimplePDFTable newTable(){
		return this.getSimplePDFTable();
	}
	
	/*******************************************************
	 * 		END SimplePDFTable METHODS
	 */

}
