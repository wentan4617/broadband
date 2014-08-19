package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TMSWholesaler;
import com.tm.broadband.model.Page;

public interface TMSWholesalerMapper {

/**
 * mapping tms_wholesaler, customer DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TMSWholesaler> selectTMSWholesaler(TMSWholesaler tmsw);
	List<TMSWholesaler> selectTMSWholesalersByPage(Page<TMSWholesaler> page);
	int selectTMSWholesalersSum(Page<TMSWholesaler> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTMSWholesaler(TMSWholesaler tmsw);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTMSWholesaler(TMSWholesaler tmsw);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTMSWholesalerById(int id);

	/* // END DELETE AREA */

}
