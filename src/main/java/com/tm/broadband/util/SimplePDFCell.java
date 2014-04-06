package com.tm.broadband.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class SimplePDFCell {

	// BEGIN MEMBER VARIABLE
	private PdfPTable table; private PdfPCell cell; private String phrase;
	// END MEMBER VARIABLE
	// BEGIN INITIALIZATION
	public SimplePDFCell() {
		this.setCell(new PdfPCell(new Phrase(phrase)));
	}
	public SimplePDFCell(PdfPTable table) {
		this.setCell(new PdfPCell(new Phrase(phrase)));
		this.setTable(table);
	}
	// END INITIALIZATION
	
	
	/**
	 * BEGIN CORE METHODS
	 */
	
	/**
	 * 	final step, add cell into table
	 */
	public void o(){
		this.getTable().addCell(this.getCell());
	}
	
	/**
	 * 
	 * @param orientation t=top, r=right, b=bottom, l=left
	 * @param padding
	 * @return this
	 */
	public SimplePDFCell paddingTo(String orientation, Float padding){
		switch (orientation) {
		case "t":
			this.getCell().setPaddingTop(padding);
			break;
		case "r":
			this.getCell().setPaddingRight(padding);
			break;
		case "b":
			this.getCell().setPaddingBottom(padding);
			break;
		case "l":
			this.getCell().setPaddingLeft(padding);
			break;
		}
		return this;
	}
	
	/**
	 * 
	 * @param paddingLeftRight
	 * @return this
	 */
	public SimplePDFCell paddingH(Float paddingLeftRight){
		this.getCell().setPaddingRight(paddingLeftRight);
		this.getCell().setPaddingLeft(paddingLeftRight);
		return this;
	}
	
	/**
	 * 
	 * @param paddingTopBottom
	 * @return this
	 */
	public SimplePDFCell paddingV(Float paddingTopBottom){
		this.getCell().setPaddingTop(paddingTopBottom);
		this.getCell().setPaddingBottom(paddingTopBottom);
		return this;
	}
	
	/**
	 * 
	 * @param indent
	 * @return this
	 */
	public SimplePDFCell indent(Float indent){
        this.getCell().setIndent(indent);
        return this;
	}
	
	/**
	 * 
	 * @param alignHTo l=left, r=right
	 * @return this
	 */
	public SimplePDFCell alignH(String alignHTo){
		switch (alignHTo) {
		case "r":
			this.getCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			break;
		case "l":
			this.getCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			break;
		}
        return this;
	}
	
	/**
	 * 
	 * @param alignVTo t=top, b=bottom
	 * @return this
	 */
	public SimplePDFCell alignV(String alignVTo){
		switch (alignVTo) {
		case "t":
			this.getCell().setVerticalAlignment(Element.ALIGN_TOP);
			break;
		case "b":
			this.getCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
			break;
		}
        return this;
	}
	
	/**
	 * 
	 * @param width
	 * @return this
	 */
	public SimplePDFCell border(Integer width){
		this.getCell().setBorder(width);
        return this;
	}
	
	/**
	 * 
	 * @param borderFor t=top, r=right, b=bottom, l=left
	 * @param width
	 * @return this
	 */
	public SimplePDFCell border(String borderFor, Float width){
		switch (borderFor) {
		case "t":
			this.getCell().setBorderWidthTop(width);
			break;
		case "r":
			this.getCell().setBorderWidthRight(width);
			break;
		case "b":
			this.getCell().setBorderWidthBottom(width);
			break;
		case "l":
			this.getCell().setBorderWidthLeft(width);
			break;
		}
        return this;
	}
	
	/**
	 * 
	 * @param borderColor
	 * @return this
	 */
	public SimplePDFCell borderColor(BaseColor borderColor){
		this.getCell().setBorderColor(borderColor);
		return this;
	}
	
	public SimplePDFCell borderWidth(Float borderWidth){
		this.getCell().setBorderWidth(borderWidth);
		return this;
		
	}
	
	/**
	 * 
	 * @param font
	 * @return this
	 */
	public SimplePDFCell font(Font font){
		this.getCell().setPhrase(new Phrase(phrase, font));
		return this;
	}
	
	/**
	 * 
	 * @param colspan
	 * @return this
	 */
	public SimplePDFCell colspan(Integer colspan){
		this.getCell().setColspan(colspan);
		return this;
	}

	/**
	 * END CORE METHODS
	 */
	
	

	// BEGIN GETTER AND SETTER
	protected String getPhrase() {
		return phrase;
	}
	protected void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	protected PdfPCell getCell() {
		return cell;
	}
	protected void setCell(PdfPCell cell) {
		this.cell = cell;
	}
	protected PdfPTable getTable() {
		return table;
	}
	protected void setTable(PdfPTable table) {
		this.table = table;
	}
	// END GETTER AND SETTER
	
	

}
