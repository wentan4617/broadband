package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TMSCombo;
import com.tm.broadband.model.Page;

public interface TMSComboMapper {

/**
 * mapping tms_combo, tMSCombo DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TMSCombo> selectTMSCombo(TMSCombo tmsc);
	List<TMSCombo> selectTMSCombosByPage(Page<TMSCombo> page);
	int selectTMSCombosSum(Page<TMSCombo> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTMSCombo(TMSCombo tmsc);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTMSCombo(TMSCombo tmsc);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTMSComboById(int id);

	/* // END DELETE AREA */

}
