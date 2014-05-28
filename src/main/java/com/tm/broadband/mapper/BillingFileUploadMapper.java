package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.BillingFileUpload;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;

/**
 * mapping tm_plan, plan DAO component
 * 
 * @author Cook1fan
 * 
 */

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface BillingFileUploadMapper {
	
	/* SELECT AREA */
	
	BillingFileUpload selectBillingFileUpload(BillingFileUpload billingFileUpload);
	List<BillingFileUpload> selectBillingFileUploads(BillingFileUpload billingFileUpload);
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	void insertBillingFileUpload(BillingFileUpload billingFileUpload);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updateBillingFileUpload(BillingFileUpload billingFileUpload);
	
	/* // END UPDATE AREA */
	
	/* =================================================================================== */
	
	/* DELETE AREA */
	
	void deleteBillingFileUpload(BillingFileUpload billingFileUpload);
	
	/* // END DELETE AREA */
	
}
