package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TMSMaterialCategory;
import com.tm.broadband.model.Page;

public interface TMSMaterialCategoryMapper {

/**
 * mapping tms_material_category, tMSMaterialCategory DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TMSMaterialCategory> selectTMSMaterialCategory(TMSMaterialCategory tmsmc);
	List<TMSMaterialCategory> selectTMSMaterialCategorysByPage(Page<TMSMaterialCategory> page);
	int selectTMSMaterialCategorysSum(Page<TMSMaterialCategory> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTMSMaterialCategory(TMSMaterialCategory tmsmc);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTMSMaterialCategory(TMSMaterialCategory tmsmc);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTMSMaterialCategoryById(int id);

	/* // END DELETE AREA */

}
