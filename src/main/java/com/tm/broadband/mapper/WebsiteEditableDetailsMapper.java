package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.WebsiteEditableDetails;
import com.tm.broadband.model.Page;

public interface WebsiteEditableDetailsMapper {

/**
 * mapping tm_website_editable_details, websiteEditableDetails DAO component
 * 
 * @author CyberPark
 * 
  */

	/* SELECT AREA */

	List<WebsiteEditableDetails> selectWebsiteEditableDetails(WebsiteEditableDetails wed);
	List<WebsiteEditableDetails> selectWebsiteEditableDetailssByPage(Page<WebsiteEditableDetails> page);
	int selectWebsiteEditableDetailssSum(Page<WebsiteEditableDetails> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertWebsiteEditableDetails(WebsiteEditableDetails wed);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateWebsiteEditableDetails(WebsiteEditableDetails wed);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	/* // END DELETE AREA */

}
