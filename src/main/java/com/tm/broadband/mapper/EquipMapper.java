package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.Equip;
import com.tm.broadband.model.Page;

public interface EquipMapper {

/**
 * mapping tm_equip, equip DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<Equip> selectEquip(Equip e);
	List<Equip> selectEquipsByPage(Page<Equip> page);
	int selectEquipsSum(Page<Equip> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertEquip(Equip e);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateEquip(Equip e);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteEquipById(int id);

	/* // END DELETE AREA */

}
