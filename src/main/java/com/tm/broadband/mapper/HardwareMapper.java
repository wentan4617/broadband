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

	List<Hardware> selectHardwaresByPage(Page<Hardware> page);
	List<Hardware> selectHardwaresBySome(Hardware hardware);
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
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA *//* // END DELETE AREA */

}
