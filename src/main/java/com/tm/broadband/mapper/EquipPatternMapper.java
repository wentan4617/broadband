package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.EquipPattern;
import com.tm.broadband.model.Page;

public interface EquipPatternMapper {

/**
 * mapping tm_equip_pattern, equipPattern DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<EquipPattern> selectEquipPattern(EquipPattern ep);
	List<EquipPattern> selectEquipPatternsByPage(Page<EquipPattern> page);
	int selectEquipPatternsSum(Page<EquipPattern> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertEquipPattern(EquipPattern ep);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateEquipPattern(EquipPattern ep);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteEquipPatternById(int id);

	/* // END DELETE AREA */

}
