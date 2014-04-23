package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Hardware;
import com.tm.broadband.model.Page;

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

public interface HardwareMapper {
	
	/* SELECT AREA */
	Hardware selectHardware(Hardware hardware);
	List<Hardware> selectHardwares(Hardware hardware);
	List<Hardware> selectHardwaresByPage(Page<Hardware> page);
	int selectHardwaresSum(Page<Hardware> page);
	
	Hardware selectHardwareById(int id);
	int selectExistHardwareByName(String hardware_name);
	
	/* // END SELECT AREA */
	
	/* =================================================================================== */
	
	/* INSERT AREA */

	void insertHardware(Hardware hardware);
	
	/* // END INSERT AREA */
	
	/* =================================================================================== */
	
	/* UPDATE AREA */

	void updateHardware(Hardware hardware);
	void updateHardwarePicById(Hardware hardware);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	
	void deleteHardwareById(int id);
	
	/* // END DELETE AREA */

}
