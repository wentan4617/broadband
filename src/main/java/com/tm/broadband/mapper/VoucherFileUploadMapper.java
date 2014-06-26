package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.Voucher;
import com.tm.broadband.model.VoucherFileUpload;

public interface VoucherFileUploadMapper {

/**
 * mapping tm_voucher_file_upload, voucherFileUpload DAO component
 * 
 * @author Dong Chen
 *
 */

	/* SELECT AREA */
	
	String selectVoucherFilePathById(int id);
	
	List<VoucherFileUpload> selectVoucherFileUpload(VoucherFileUpload v);
	List<VoucherFileUpload> selectVoucherFileUploadsByPage(Page<VoucherFileUpload> page);
	int selectVoucherFileUploadsSum(Page<VoucherFileUpload> page);
	Voucher selectVoucherFileUploadById(int id);
	
	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */
	
	void insertVoucherFileUpload(VoucherFileUpload v);
	
	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */
	
	void updateVoucherFileUpload(VoucherFileUpload v);
	
	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteVoucherFileUploadById(int ic);
	
	/* // END DELETE AREA */

}
