package com.tm.broadband.mapper;

import com.tm.broadband.model.CompanyDetail;

/**
 * mapping tm_customer_invoice
 * 
 * @author DON CHEN


/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface CompanyDetailMapper {
	
	/* SELECT AREA */
	
	CompanyDetail selectCompanyDetail();
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updateCompanyDetail(CompanyDetail companyDetail);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA *//* // END DELETE AREA */

}
