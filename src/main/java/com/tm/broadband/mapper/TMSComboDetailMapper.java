package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TMSComboDetail;
import com.tm.broadband.model.Page;

public interface TMSComboDetailMapper {

/**
 * mapping tms_combo_detail, tMSComboDetail DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TMSComboDetail> selectTMSComboDetail(TMSComboDetail tmscd);
	List<TMSComboDetail> selectTMSComboDetailsByPage(Page<TMSComboDetail> page);
	int selectTMSComboDetailsSum(Page<TMSComboDetail> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTMSComboDetail(TMSComboDetail tmscd);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTMSComboDetail(TMSComboDetail tmscd);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTMSComboDetailById(int id);

	/* // END DELETE AREA */

}
