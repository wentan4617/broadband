package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.EquipLog;
import com.tm.broadband.model.Page;

public interface EquipLogMapper {

/**
 * mapping tm_equip_log, equipLog DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<EquipLog> selectEquipLog(EquipLog el);
	List<EquipLog> selectEquipLogsByPage(Page<EquipLog> page);
	int selectEquipLogsSum(Page<EquipLog> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertEquipLog(EquipLog el);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateEquipLog(EquipLog el);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteEquipLogById(int id);

	/* // END DELETE AREA */

}
