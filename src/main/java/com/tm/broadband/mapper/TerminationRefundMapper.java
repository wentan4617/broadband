package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.TerminationRefund;
import com.tm.broadband.model.Page;

public interface TerminationRefundMapper {

/**
 * mapping tm_termination_refund, terminationRefund DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<TerminationRefund> selectTerminationRefund(TerminationRefund tr);
	List<TerminationRefund> selectTerminationRefundsByPage(Page<TerminationRefund> page);
	int selectTerminationRefundsSum(Page<TerminationRefund> page);
	TerminationRefund selectTerminationRefundById(int id);
	String selectTerminationRefundPDFPathById(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertTerminationRefund(TerminationRefund tr);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateTerminationRefund(TerminationRefund tr);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTerminationRefundById(int id);
	
	/* // END DELETE AREA */

}
