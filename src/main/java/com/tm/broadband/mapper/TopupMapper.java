package com.tm.broadband.mapper;

import java.util.List;

import com.tm.broadband.model.Page;
import com.tm.broadband.model.Topup;

public interface TopupMapper {

	void insertTopup(Topup topup);

	void updateTopup(Topup topup);

	Topup selectTopupById(int id);

	List<Topup> selectTopups();

	List<Topup> selectTopupsByPage(Page<Topup> page);

	int selectTopupsSum(Page<Topup> topup);
}
