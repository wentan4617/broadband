package com.tm.broadband.util;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ITextUtils {

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
	public static void addPDFTitle(PdfPTable table, String title, Font font, Float paddingBottom, Integer border, Integer align, Integer colspan){
        PdfPCell cell = new PdfPCell(new Phrase(title, font));
        cell.setColspan(colspan);
        cell.setBorder(border);
        cell.setPaddingBottom(paddingBottom);
        cell.setHorizontalAlignment(align);
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
	public static void addImage(PdfWriter writer, String resource, Float width, Float height, Float positionX, Float positionY) throws DocumentException, MalformedURLException, IOException{
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
	public static void addTitleBar(PdfPTable table, String title, Font font, BaseColor bgColor, BaseColor borderColor, Integer colspan){
        // BEGIN TITLE BAR
        PdfPCell cell = new PdfPCell(new Phrase(title, font));
        cell.setColspan(colspan);
        cell.setBorder(PdfPCell.TOP);
        cell.setBorderColor(borderColor);
        cell.setIndent(10F);
        cell.setPaddingTop(4F);
        cell.setPaddingBottom(6F);
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
	// ENCAPSULATE EMPTY ROW
	public static void addEmptyRow(PdfPTable table, Float height, Integer colspan){
		PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(colspan);
        cell.setBorder(0);
        cell.setFixedHeight(height);
        table.addCell(cell);
	}

	/**
	 * 
	 * @param table
	 * @param colspan
	 */
	// ENCAPSULATE EMPTY ROW
	public static void addEmptyRow(PdfPTable table, Integer colspan){
		PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(colspan);
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
	// ENCAPSULATE LINE ROW
	public static void addLineRow(PdfPTable table, Integer colspan, BaseColor color, Float height){
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
}
