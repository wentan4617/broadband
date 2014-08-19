package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TMSMaterial;
import com.tm.broadband.model.Page;

public interface TMSMaterialMapper {

/**
 * mapping tms_material, tMSMaterial DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TMSMaterial> selectTMSMaterial(TMSMaterial tmsm);
	List<TMSMaterial> selectTMSMaterialsByPage(Page<TMSMaterial> page);
	int selectTMSMaterialsSum(Page<TMSMaterial> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTMSMaterial(TMSMaterial tmsm);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTMSMaterial(TMSMaterial tmsm);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTMSMaterialById(int id);

	/* // END DELETE AREA */

}
