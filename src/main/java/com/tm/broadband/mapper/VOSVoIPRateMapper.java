package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.VOSVoIPRate;

public interface VOSVoIPRateMapper {

/**
 * mapping tm_vos_voip_rates, vOSVoIPRates DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<VOSVoIPRate> selectVOSVoIPRate(VOSVoIPRate vosvipr);
	List<VOSVoIPRate> selectVOSVoIPRatesByPage(Page<VOSVoIPRate> page);
	int selectVOSVoIPRatesSum(Page<VOSVoIPRate> page);
	List<VOSVoIPRate> selectVOSVoIPRatesGroupBy();

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertVOSVoIPRate(VOSVoIPRate vosvipr);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateVOSVoIPRate(VOSVoIPRate vosvipr);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteVOSVoIPRates();

	/* // END DELETE AREA */

}
