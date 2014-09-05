package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.SEO;
import com.tm.broadband.model.Page;

public interface SEOMapper {

	/* SELECT AREA */

	List<SEO> selectSEOs(SEO seo);
	List<SEO> selectSEOsByPage(Page<SEO> page);
	int selectSEOsSum(Page<SEO> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertSEO(SEO seo);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateSEO(SEO seo);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteSEOById(int id);

	/* // END DELETE AREA */

}
