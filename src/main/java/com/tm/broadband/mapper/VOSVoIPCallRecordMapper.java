package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.VOSVoIPCallRecord;
import com.tm.broadband.model.Page;

public interface VOSVoIPCallRecordMapper {

/**
 * mapping tm_vos_voip_call_record, vOSVoIPCallRecord DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<VOSVoIPCallRecord> selectVOSVoIPCallRecord(VOSVoIPCallRecord vosvipcr);
	List<VOSVoIPCallRecord> selectVOSVoIPCallRecordsByPage(Page<VOSVoIPCallRecord> page);
	int selectVOSVoIPCallRecordsSum(Page<VOSVoIPCallRecord> page);
	List<VOSVoIPCallRecord> selectVOSVoIPCallRecordsByCallStart(VOSVoIPCallRecord vvcr);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertVOSVoIPCallRecord(VOSVoIPCallRecord vosvipcr);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateVOSVoIPCallRecord(VOSVoIPCallRecord vosvipcr);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteVOSVoIPCallRecordById(int id);

	/* // END DELETE AREA */

}
