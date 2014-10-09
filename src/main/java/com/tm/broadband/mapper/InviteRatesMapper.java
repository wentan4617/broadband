package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.InviteRates;
import com.tm.broadband.model.Page;

public interface InviteRatesMapper {

	/* SELECT AREA */

	List<InviteRates> selectInviteRates(InviteRates ir);
	List<InviteRates> selectInviteRatessByPage(Page<InviteRates> page);
	int selectInviteRatessSum(Page<InviteRates> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertInviteRates(InviteRates ir);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateInviteRates(InviteRates ir);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteInviteRatesById(int id);

	/* // END DELETE AREA */

}
