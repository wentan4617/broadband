package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.VoucherBannedList;

public interface VoucherBannedListMapper {

/**
 * mapping tm_voucher_banned_list, voucherBannedList DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	List<VoucherBannedList> selectVoucherBannedList(VoucherBannedList vbl);
	List<VoucherBannedList> selectVoucherBannedListsByPage(Page<VoucherBannedList> page);
	int selectVoucherBannedListsSum(Page<VoucherBannedList> page);
	VoucherBannedList selectVoucherBannedListById(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertVoucherBannedList(VoucherBannedList vbl);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateVoucherBannedList(VoucherBannedList vbl);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteVoucherBannedListById(int id);
	
	/* // END DELETE AREA */

}
