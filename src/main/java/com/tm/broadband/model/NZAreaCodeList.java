package com.tm.broadband.model;

import java.io.Serializable;

public class NZAreaCodeList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nz_auckland_local_area_code;
	private String nz_auckland_nine_non_local_area_code;

	public String getNz_auckland_local_area_code() {
		return nz_auckland_local_area_code;
	}

	public void setNz_auckland_local_area_code(String nz_auckland_local_area_code) {
		this.nz_auckland_local_area_code = nz_auckland_local_area_code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNz_auckland_nine_non_local_area_code() {
		return nz_auckland_nine_non_local_area_code;
	}

	public void setNz_auckland_nine_non_local_area_code(
			String nz_auckland_nine_non_local_area_code) {
		this.nz_auckland_nine_non_local_area_code = nz_auckland_nine_non_local_area_code;
	}

}
