package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.EarlyTerminationRefund;
import com.tm.broadband.model.Page;

public interface EarlyTerminationRefundMapper {

/**
 * mapping tm_early_termination_refund, earlyTerminationRefund DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<EarlyTerminationRefund> selectEarlyTerminationRefund(EarlyTerminationRefund etr);
	List<EarlyTerminationRefund> selectEarlyTerminationRefundsByPage(Page<EarlyTerminationRefund> page);
	int selectEarlyTerminationRefundsSum(Page<EarlyTerminationRefund> page);
	EarlyTerminationRefund selectEarlyTerminationRefundById(int id);
	String selectEarlyTerminationRefundPDFPathById(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertEarlyTerminationRefund(EarlyTerminationRefund etr);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateEarlyTerminationRefund(EarlyTerminationRefund etr);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteEarlyTerminationRefundById(int id);
	
	/* // END DELETE AREA */

}
