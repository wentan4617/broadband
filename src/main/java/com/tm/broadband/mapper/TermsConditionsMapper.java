package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TermsConditions;
import com.tm.broadband.model.Page;

public interface TermsConditionsMapper {

/**
 * mapping tm_terms_conditions, termsConditions DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<TermsConditions> selectTermsConditions(TermsConditions tc);
	List<TermsConditions> selectTermsConditionssByPage(Page<TermsConditions> page);
	int selectTermsConditionssSum(Page<TermsConditions> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTermsConditions(TermsConditions tc);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTermsConditions(TermsConditions tc);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	/* // END DELETE AREA */

}
