package com.tm.broadband.util.itext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class SimplePDFCell {

	// BEGIN MEMBER VARIABLE
	
	// BEGIN PRIVATE VARIABLES
	private PdfPTable table; private PdfPCell cell; private String phrase;
	// END PRIVATE VARIABLES
	
	// BEGIN GLOBAL VARIABLES
	private Integer globalBorderWidth;
	private BaseColor globalBorderColor;
	// END GLOBAL VARIABLES
	
	// END MEMBER VARIABLE
	// BEGIN INITIALIZATION
	public SimplePDFCell() {}
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
	
	/*******************************************************
	 * 		BEGIN Col METHODS
	 */
	
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
	 * @param alignHTo r=right, l=left, c=center
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
		case "c":
			this.getCell().setHorizontalAlignment(Element.ALIGN_CENTER);
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
	 * @param width
	 * @return this
	 */
	public SimplePDFCell border(Float width){
		this.getCell().setBorderWidthTop(width);
		this.getCell().setBorderWidthRight(width);
		this.getCell().setBorderWidthBottom(width);
		this.getCell().setBorderWidthLeft(width);
        return this;
	}
	
	
	/**
	 * 
	 * @param width
	 * @return this
	 */
	public SimplePDFCell borderZoom(Float width){
		this.getCell().setUseBorderPadding(true);
		this.getCell().setBorderWidthTop(width);
		this.getCell().setBorderWidthRight(width);
        this.getCell().setBorderWidthBottom(width);
        this.getCell().setBorderWidthLeft(width);
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
	
	/**
	 * 
	 * @param orientation
	 * @param borderColor
	 * @return this
	 */
	public SimplePDFCell borderColor(String orientation, BaseColor borderColor){
		switch (orientation) {
		case "t":
			this.getCell().setBorderColorTop(borderColor);
			break;
		case "r":
			this.getCell().setBorderColorRight(borderColor);
			break;
		case "b":
			this.getCell().setBorderColorBottom(borderColor);
			break;
		case "l":
			this.getCell().setBorderColorLeft(borderColor);
			break;
		}
		return this;
	}
	
	/**
	 * 
	 * @param borderWidth
	 * @return this
	 */
	public SimplePDFCell borderWidth(Float borderWidth){
		this.getCell().setBorderWidth(borderWidth);
		return this;
	}
	
	/**
	 * 
	 * @param orientation
	 * @param borderWidth
	 * @return this
	 */
	public SimplePDFCell borderWidth(String orientation, Float borderWidth){
		switch (orientation) {
		case "t":
			this.getCell().setBorderWidthTop(borderWidth);
			break;
		case "r":
			this.getCell().setBorderWidthRight(borderWidth);
			break;
		case "b":
			this.getCell().setBorderWidthBottom(borderWidth);
			break;
		case "l":
			this.getCell().setBorderWidthLeft(borderWidth);
			break;
		}
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
	 * 
	 * @param rowspan
	 * @return this
	 */
	public SimplePDFCell rowspan(Integer rowspan){
		this.getCell().setRowspan(rowspan);
		return this;
	}
	
	/**
	 * 
	 * @param fixedHeight
	 * @return this
	 */
	public SimplePDFCell fixedHeight(Float fixedHeight){
		this.getCell().setFixedHeight(fixedHeight);
		return this;
	}
	
	/**
	 * 
	 * @param bgColor
	 * @return this
	 */
	public SimplePDFCell bgColor(BaseColor bgColor){
		this.getCell().setBackgroundColor(bgColor);
		return this;
	}
	
	/**
	 * 
	 * @param image
	 * @return this
	 */
	public SimplePDFCell image(Image image){
		this.getCell().setImage(image);
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
		if(this.getGlobalBorderWidth() != null){ this.getCell().setBorder(this.getGlobalBorderWidth()); }
		if(this.getGlobalBorderColor() != null){ this.getCell().setBorderColor(this.getGlobalBorderColor()); }
	}
	protected PdfPTable getTable() {
		return table;
	}
	protected void setTable(PdfPTable table) {
		this.table = table;
	}
	protected Integer getGlobalBorderWidth() {
		return globalBorderWidth;
	}
	protected void setGlobalBorderWidth(Integer globalBorderWidth) {
		this.globalBorderWidth = globalBorderWidth;
	}
	protected BaseColor getGlobalBorderColor() {
		return globalBorderColor;
	}
	protected void setGlobalBorderColor(BaseColor globalBorderColor) {
		this.globalBorderColor = globalBorderColor;
	}
	// END GETTER AND SETTER
	

}
