package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.ManualDefrayLog;
import com.tm.broadband.model.Page;

/**
 * mapping tm_manual_defray_log, manualDefrayLog DAO component
 * 
 * @author Steven Chen
 * 
 */

/* SELECT AREA *//* // END SELECT AREA */
/* =================================================================================== */
/* INSERT AREA *//* // END INSERT AREA */
/* =================================================================================== */
/* UPDATE AREA *//* // END UPDATE AREA */
/* =================================================================================== */
/* DELETE AREA *//* // END DELETE AREA */

public interface ManualDefrayLogMapper {

	/* SELECT AREA */
	
	public List<ManualDefrayLog> selectManualDefrayLog(ManualDefrayLog manualDefrayLog);
	
	List<ManualDefrayLog> selectManualDefrayLogsByPage(Page<ManualDefrayLog> page);
	int selectManualDefrayLogsSum(Page<ManualDefrayLog> page);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	public void insertManualDefrayLog(ManualDefrayLog manualDefrayLog);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	public void updateManualDefrayLog(ManualDefrayLog manualDefrayLog);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	
	public void deleteManualDefrayLog(ManualDefrayLog manualDefrayLog);
	
	/* // END DELETE AREA */

}
