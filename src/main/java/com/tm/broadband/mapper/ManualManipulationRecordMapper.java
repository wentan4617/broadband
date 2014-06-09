package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.ManualManipulationRecord;
import com.tm.broadband.model.Page;

public interface ManualManipulationRecordMapper {

/**
 * mapping tm_manual_manipulation_record, manualManipulationRecord DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<ManualManipulationRecord> selectManualManipulationRecords(ManualManipulationRecord mmr);
	List<ManualManipulationRecord> selectManualManipulationRecordsByPage(Page<ManualManipulationRecord> page);
	int selectManualManipulationRecordsSum(Page<ManualManipulationRecord> page);
	List<ManualManipulationRecord> selectManualManipulationRecordsByManipulationType(String manipulation_type);
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertManualManipulationRecord(ManualManipulationRecord mmr);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateManualManipulationRecord(ManualManipulationRecord mmr);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteManualManipulationRecordById(int id);
	
	/* // END DELETE AREA */

}
