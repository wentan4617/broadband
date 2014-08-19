package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TMSMaterialGroup;
import com.tm.broadband.model.Page;

public interface TMSMaterialGroupMapper {

/**
 * mapping tms_material_group, tmsmg DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TMSMaterialGroup> selectTMSMaterialGroup(TMSMaterialGroup tmsmg);
	List<TMSMaterialGroup> selectTMSMaterialGroupsByPage(Page<TMSMaterialGroup> page);
	int selectTMSMaterialGroupsSum(Page<TMSMaterialGroup> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTMSMaterialGroup(TMSMaterialGroup tmsmg);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTMSMaterialGroup(TMSMaterialGroup tmsmg);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTMSMaterialGroupById(int id);

	/* // END DELETE AREA */

}
