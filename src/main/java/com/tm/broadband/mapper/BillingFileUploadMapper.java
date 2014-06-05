package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.BillingFileUpload;
import com.tm.broadband.model.Page;

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
	
	String selectBillingFilePathById(int id);
	
	BillingFileUpload selectBillingFileUpload(BillingFileUpload bfu);
	List<BillingFileUpload> selectBillingFileUploads(BillingFileUpload bfu);
	List<BillingFileUpload> selectBillingFileUploadsByPage(Page<BillingFileUpload> page);
	int selectBillingFileUploadsSum(Page<BillingFileUpload> page);
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */
	
	void insertBillingFileUpload(BillingFileUpload bfu);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */
	
	void updateBillingFileUpload(BillingFileUpload bfu);
	
	/* // END UPDATE AREA */
	
	/* =================================================================================== */
	
	/* DELETE AREA */
	
	void deleteBillingFileUpload(int id);
	
	/* // END DELETE AREA */
	
}
