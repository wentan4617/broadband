package com.tm.broadband.util.itext;

import com.itextpdf.text.pdf.PdfPTable;

public class SimplePDFTable {

	// BEGIN MEMBER VARIABLE
	private PdfPTable table;
	private Integer colspan;

	// END MEMBER VARIABLE

	// BEGIN INITIALIZATION
	public SimplePDFTable() {
		if (colspan != null) {
			this.setTable(new PdfPTable(colspan));
		}
	}

	// END INITIALIZATION

	/**
	 * 
	 * @return table
	 */
	public PdfPTable o() {
		return this.getTable();
	}

	/**
	 * 
	 * @param width
	 *            percentage
	 * @return this
	 */
	public SimplePDFTable widthPercentage(Float percentage) {
		this.getTable().setWidthPercentage(percentage);
		return this;
	}
	
	/**
	 * 
	 * @param totalWidth
	 * @return this
	 */
	public SimplePDFTable totalWidth(Float totalWidth){
		this.getTable().setTotalWidth(totalWidth);
		return this;
	}

	/**
	 * 
	 * @param new table assign columns
	 * @return this
	 */
	public SimplePDFTable columns(Integer columns) {
		this.setTable(new PdfPTable(columns));
		return this;
	}

	protected PdfPTable getTable() {
		return table;
	}

	protected void setTable(PdfPTable table) {
		this.table = table;
	}

	protected Integer getColspan() {
		return colspan;
	}

	protected void setColspan(Integer colspan) {
		this.colspan = colspan;
	}
}
