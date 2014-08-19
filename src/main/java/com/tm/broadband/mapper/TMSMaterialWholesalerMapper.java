package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TMSMaterialWholesaler;
import com.tm.broadband.model.Page;

public interface TMSMaterialWholesalerMapper {

/**
 * mapping tms_material_wholesaler, tMSMaterialWholesaler DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TMSMaterialWholesaler> selectTMSMaterialWholesaler(TMSMaterialWholesaler tmsmw);
	List<TMSMaterialWholesaler> selectTMSMaterialWholesalersByPage(Page<TMSMaterialWholesaler> page);
	int selectTMSMaterialWholesalersSum(Page<TMSMaterialWholesaler> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTMSMaterialWholesaler(TMSMaterialWholesaler tmsmw);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTMSMaterialWholesaler(TMSMaterialWholesaler tmsmw);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTMSMaterialWholesalerById(int id);

	/* // END DELETE AREA */

}
