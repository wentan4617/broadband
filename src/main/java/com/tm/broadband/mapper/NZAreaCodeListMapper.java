package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.NZAreaCodeList;
import com.tm.broadband.model.Page;

public interface NZAreaCodeListMapper {

/**
 * mapping tm_nz_area_code_list, nZAreaCodeList DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<NZAreaCodeList> selectNZAreaCodeList(NZAreaCodeList nzacl);
	List<NZAreaCodeList> selectNZAreaCodeListsByPage(Page<NZAreaCodeList> page);
	int selectNZAreaCodeListsSum(Page<NZAreaCodeList> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertNZAreaCodeList(NZAreaCodeList nzacl);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateNZAreaCodeList(NZAreaCodeList nzacl);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */
	/* // END DELETE AREA */

}
