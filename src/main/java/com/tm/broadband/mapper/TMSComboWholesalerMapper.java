package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TMSComboWholesaler;
import com.tm.broadband.model.Page;

public interface TMSComboWholesalerMapper {

/**
 * mapping tms_combo_wholesaler, tMSComboWholesaler DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TMSComboWholesaler> selectTMSComboWholesaler(TMSComboWholesaler tmscw);
	List<TMSComboWholesaler> selectTMSComboWholesalersByPage(Page<TMSComboWholesaler> page);
	int selectTMSComboWholesalersSum(Page<TMSComboWholesaler> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTMSComboWholesaler(TMSComboWholesaler tmscw);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTMSComboWholesaler(TMSComboWholesaler tmscw);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTMSComboWholesalerById(int id);

	/* // END DELETE AREA */

}
