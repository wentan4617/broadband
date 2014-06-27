package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.Voucher;

public interface VoucherMapper {
	
	/* SELECT AREA */
	
	List<Voucher> selectVouchers(Voucher v);
	List<Voucher> selectVouchersByPage(Page<Voucher> page);
	int selectVouchersSum(Page<Voucher> page);
	Voucher selectVoucherBySerialNumber(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertVoucher(Voucher v);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateVoucher(Voucher v);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteVoucherBySerialNumber(int serial_number);
	
	/* // END DELETE AREA */

}
