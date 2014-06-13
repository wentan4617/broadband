package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.EarlyTerminationCharge;
import com.tm.broadband.model.Page;

public interface EarlyTerminationChargeMapper {

/**
 * mapping tm_early_termination_charge, earlyTerminationCharge DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<EarlyTerminationCharge> selectEarlyTerminationCharge(EarlyTerminationCharge etc);
	List<EarlyTerminationCharge> selectEarlyTerminationChargesByPage(Page<EarlyTerminationCharge> page);
	int selectEarlyTerminationChargesSum(Page<EarlyTerminationCharge> page);
	EarlyTerminationCharge selectEarlyTerminationChargeById(int id);
	String selectEarlyTerminationChargePDFPathById(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertEarlyTerminationCharge(EarlyTerminationCharge etc);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateEarlyTerminationCharge(EarlyTerminationCharge etc);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteEarlyTerminationChargeById(int id);
	
	/* // END DELETE AREA */

}
