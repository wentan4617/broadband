package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.CallInternationalRate;
import com.tm.broadband.model.Page;

public interface CallInternationalRateMapper {

/**
 * mapping tm_call_internation_rate, callInternationalRate DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<CallInternationalRate> selectCallInternationalRate(CallInternationalRate ccr);
	List<CallInternationalRate> selectCallInternationalRatesByPage(Page<CallInternationalRate> page);
	int selectCallInternationalRatesSum(Page<CallInternationalRate> page);
	List<CallInternationalRate> selectCallInternationalRateByAreaPrefix(String area_prefix);
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertCallInternationalRate(CallInternationalRate ccr);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateCallInternationalRate(CallInternationalRate ccr);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteCallInternationalRate();
	
	/* // END DELETE AREA */

}
