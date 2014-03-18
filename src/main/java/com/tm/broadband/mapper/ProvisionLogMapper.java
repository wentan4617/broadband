package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.ProvisionLog;

/**
 * mapping tm_provision_log
 * 
 * @author Cook1fan
 * 
 */
public interface ProvisionLogMapper {


	/* SELECT AREA */
	int selectProvisionLogsSum(Page<ProvisionLog> page);
	ProvisionLog selectProvisionLogById(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertProvisionLog(ProvisionLog log);
	List<ProvisionLog> selectProvisionLogsByPage(Page<ProvisionLog> page);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateProvisionLog(ProvisionLog provisionLog);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA *//* // END DELETE AREA */
	
}
