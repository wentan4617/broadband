package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CallChargeRate;
import com.tm.broadband.model.Page;

public interface CallChargeRateMapper {

/**
 * mapping tm_call_charge_rate, callChargeRate DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<CallChargeRate> selectCallChargeRate(CallChargeRate ccr);
	List<CallChargeRate> selectCallChargeRatesByPage(Page<CallChargeRate> page);
	int selectCallChargeRatesSum(Page<CallChargeRate> page);
	CallChargeRate selectCallChargeRateById(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertCallChargeRate(CallChargeRate ccr);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateCallChargeRate(CallChargeRate ccr);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCallChargeRateById(int id);
	
	/* // END DELETE AREA */

}
