package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.PDFResources;
import com.tm.broadband.model.Page;

public interface PDFResourcesMapper {

/**
 * mapping tm_pdf_resources, pDFResources DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<PDFResources> selectPDFResources(PDFResources pdfr);
	List<PDFResources> selectPDFResourcessByPage(Page<PDFResources> page);
	int selectPDFResourcessSum(Page<PDFResources> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertPDFResources(PDFResources pdfr);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updatePDFResources(PDFResources pdfr);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	/* // END DELETE AREA */

}
