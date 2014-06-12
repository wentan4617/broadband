package com.tm.broadband.mapper;

import com.tm.broadband.model.EarlyTerminationChargeParameter;

public interface EarlyTerminationChargeParameterMapper {

/**
 * mapping tm_early_termination_charge_parameter, earlyTerminationChargeParameter DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	EarlyTerminationChargeParameter selectEarlyTerminationChargeParameter();
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertEarlyTerminationChargeParameter(EarlyTerminationChargeParameter etcp);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateEarlyTerminationChargeParameter(EarlyTerminationChargeParameter etcp);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteEarlyTerminationChargeParameter();
	
	/* // END DELETE AREA */

}
