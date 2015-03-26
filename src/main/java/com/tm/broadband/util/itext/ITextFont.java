package com.tm.broadband.util.itext;

import java.io.File;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

public class ITextFont {
	
	public static final Font arial_normal_4 = new Font(createBaseFont("Arial.ttf"), 4, Font.NORMAL);
	public static final Font arial_normal_6 = new Font(createBaseFont("Arial.ttf"), 6, Font.NORMAL);
	public static final Font arial_normal_7 = new Font(createBaseFont("Arial.ttf"), 7, Font.NORMAL);
	public static final Font arial_normal_8 = new Font(createBaseFont("Arial.ttf"), 8, Font.NORMAL);
	public static final Font arial_normal_white_8 = new Font(createBaseFont("Arial.ttf"), 8, Font.NORMAL, BaseColor.WHITE);
	public static final Font arial_normal_green_8 = new Font(createBaseFont("Arial.ttf"), 8, Font.NORMAL, new BaseColor(92,184,92));
	public static final Font arial_normal_9 = new Font(createBaseFont("Arial.ttf"), 9, Font.NORMAL);
	public static final Font arial_normal_10 = new Font(createBaseFont("Arial.ttf"), 10, Font.NORMAL);
	public static final Font arial_normal_14 = new Font(createBaseFont("Arial.ttf"), 14, Font.NORMAL);
	public static final Font arial_colored_normal_8 = new Font(createBaseFont("Arial.ttf"), 8, Font.NORMAL, new BaseColor(61, 184, 185));
	public static final Font arial_colored_normal_11 = new Font(createBaseFont("Arial.ttf"), 11, Font.NORMAL, new BaseColor(61, 184, 185));
	public static final Font arial_colored_bold_11 = new Font(createBaseFont("arialbd.ttf"), 11, Font.NORMAL, new BaseColor(61, 184, 185));
	public static final Font arial_colored_bold_18 = new Font(createBaseFont("arialbd.ttf"), 18, Font.NORMAL, new BaseColor(61, 184, 185));
	public static final Font arial_colored_bold_23 = new Font(createBaseFont("arialbd.ttf"), 23, Font.NORMAL, new BaseColor(61, 184, 185));
	public static final Font arial_bold_8 = new Font(createBaseFont("arialbd.ttf"), 8, Font.NORMAL);
	public static final Font arial_bold_9 = new Font(createBaseFont("arialbd.ttf"), 9, Font.NORMAL);
	public static final Font arial_bold_10 = new Font(createBaseFont("arialbd.ttf"), 10, Font.NORMAL);
	public static final Font arial_bold_red_10 = new Font(createBaseFont("arialbd.ttf"), 10, Font.NORMAL, BaseColor.RED);
	public static final Font arial_bold_red_120 = new Font(createBaseFont("arialbd.ttf"), 120, Font.NORMAL, BaseColor.RED);
	public static final Font arial_bold_green_10 = new Font(createBaseFont("arialbd.ttf"), 10, Font.NORMAL, new BaseColor(92,184,92));
	public static final Font arial_bold_white_8 = new Font(createBaseFont("arialbd.ttf"), 8, Font.NORMAL, BaseColor.WHITE);
	public static final Font arial_bold_white_9 = new Font(createBaseFont("arialbd.ttf"), 9, Font.NORMAL, BaseColor.WHITE);
	public static final Font arial_bold_white_10 = new Font(createBaseFont("arialbd.ttf"), 10, Font.NORMAL, BaseColor.WHITE);
	public static final Font arial_bold_12 = new Font(createBaseFont("arialbd.ttf"), 12, Font.NORMAL);
	public static final Font arial_bold_20 = new Font(createBaseFont("arialbd.ttf"), 20, Font.NORMAL);
	public static final Font arial_bold_green_20 = new Font(createBaseFont("arialbd.ttf"), 20, Font.NORMAL, new BaseColor(92,184,92));
	public static final Font lucida_sans_unicode_9 = new Font(createBaseFont("Lucida Sans Unicode.ttf"), 9, Font.NORMAL);

	public static final Font arial_bold_italic_10 = new Font(createBaseFont("arialbd.ttf"), 10, Font.ITALIC);
	public static final Font arial_coral_bold_11 = new Font(createBaseFont("arialbd.ttf"), 11, Font.NORMAL, new BaseColor(240,128,128));
	public static final Font arial_coral_bold_18 = new Font(createBaseFont("arialbd.ttf"), 18, Font.NORMAL, new BaseColor(240,128,128));
	
	public static BaseFont createBaseFont(String fontFile){
		try {
			return BaseFont.createFont("pdf" + File.separator + "font-family" + File.separator + fontFile, BaseFont.WINANSI, BaseFont.EMBEDDED);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		} 
		return null;
	}

}
