package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.ContactUs;
import com.tm.broadband.model.Page;

public interface ContactUsMapper {

/**
 * mapping tm_contact_us, contactUs DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	ContactUs selectContactUs(ContactUs contactUs);
	List<ContactUs> selectContactUssByPage(Page<ContactUs> page);
	int selectContactUssSum(Page<ContactUs> page);
	ContactUs selectContactUsById(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertContactUs(ContactUs contactUs);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateContactUs(ContactUs contactUs);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteContactUsById(int id);
	
	/* // END DELETE AREA */

}
