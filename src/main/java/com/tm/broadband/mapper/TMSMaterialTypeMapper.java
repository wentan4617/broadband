package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TMSMaterialType;
import com.tm.broadband.model.Page;

public interface TMSMaterialTypeMapper {

/**
 * mapping tms_material_type, tmsmt DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TMSMaterialType> selectTMSMaterialType(TMSMaterialType tmsmt);
	List<TMSMaterialType> selectTMSMaterialTypesByPage(Page<TMSMaterialType> page);
	int selectTMSMaterialTypesSum(Page<TMSMaterialType> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTMSMaterialType(TMSMaterialType tmsmt);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTMSMaterialType(TMSMaterialType tmsmt);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTMSMaterialTypeById(int id);

	/* // END DELETE AREA */

}
