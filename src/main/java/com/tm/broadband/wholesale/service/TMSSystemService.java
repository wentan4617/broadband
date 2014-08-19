package com.tm.broadband.wholesale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.TMSWholesalerMapper;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.TMSWholesaler;

@Service
public class TMSSystemService {
	
	private TMSWholesalerMapper tmsWholesalerMapper;

	@Autowired
	public TMSSystemService(TMSWholesalerMapper tmsWholesalerMapper) {
		this.tmsWholesalerMapper = tmsWholesalerMapper;
	}
	
	/**
	 * BEGIN TMSWholesaler
	 */
	public List<TMSWholesaler> queryTMSWholesaler(TMSWholesaler tmsw){
		return this.tmsWholesalerMapper.selectTMSWholesaler(tmsw);
	}
	
	public Page<TMSWholesaler> queryTMSWholesalerByPage(Page<TMSWholesaler> page){
		page.setResults(this.tmsWholesalerMapper.selectTMSWholesalersByPage(page));
		page.setTotalRecord(this.tmsWholesalerMapper.selectTMSWholesalersSum(page));
		return page;
	}
	
	@Transactional
	public void editTMSWholesaler(TMSWholesaler tmsw){
		this.tmsWholesalerMapper.updateTMSWholesaler(tmsw);
	}
	
	@Transactional
	public void removeTMSWholesalerById(int id){
		this.tmsWholesalerMapper.deleteTMSWholesalerById(id);
	}
	
	@Transactional
	public void createTMSWholesaler(TMSWholesaler tmsw){
		this.tmsWholesalerMapper.insertTMSWholesaler(tmsw);
	}
	/**
	 * END TMSWholesaler
	 */
	
}
