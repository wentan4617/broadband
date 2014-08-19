package com.tm.broadband.wholesale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.TMSComboDetailMapper;
import com.tm.broadband.mapper.TMSComboMapper;
import com.tm.broadband.mapper.TMSComboWholesalerMapper;
import com.tm.broadband.mapper.TMSMaterialCategoryMapper;
import com.tm.broadband.mapper.TMSMaterialGroupMapper;
import com.tm.broadband.mapper.TMSMaterialMapper;
import com.tm.broadband.mapper.TMSMaterialTypeMapper;
import com.tm.broadband.mapper.TMSMaterialWholesalerMapper;
import com.tm.broadband.mapper.TMSWholesalerMapper;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.TMSCombo;
import com.tm.broadband.model.TMSComboDetail;
import com.tm.broadband.model.TMSComboWholesaler;
import com.tm.broadband.model.TMSMaterial;
import com.tm.broadband.model.TMSMaterialCategory;
import com.tm.broadband.model.TMSMaterialGroup;
import com.tm.broadband.model.TMSMaterialType;
import com.tm.broadband.model.TMSMaterialWholesaler;

@Service
public class TMSMaterialService {
	
	private TMSMaterialMapper tmsMaterialMapper;
	private TMSMaterialGroupMapper tmsMaterialGroupMapper;
	private TMSMaterialTypeMapper tmsMaterialTypeMapper;
	private TMSMaterialCategoryMapper tmsMaterialCategoryMapper;
	private TMSMaterialWholesalerMapper tmsMaterialWholesalerMapper;
	private TMSWholesalerMapper tmsWholesalerMapper;
	private TMSComboMapper tmsComboMapper;
	private TMSComboDetailMapper tmsComboDetailMapper;
	private TMSComboWholesalerMapper tmsComboWholesalerMapper;
	
	@Autowired
	public TMSMaterialService(TMSMaterialMapper tmsMaterialMapper,
			TMSMaterialGroupMapper tmsMaterialGroupMapper,
			TMSMaterialTypeMapper tmsMaterialTypeMapper,
			TMSMaterialCategoryMapper tmsMaterialCategoryMapper,
			TMSMaterialWholesalerMapper tmsMaterialWholesalerMapper,
			TMSWholesalerMapper tmsWholesalerMapper,
			TMSComboMapper tmsComboMapper,
			TMSComboDetailMapper tmsComboDetailMapper,
			TMSComboWholesalerMapper tmsComboWholesalerMapper) {
		this.tmsMaterialMapper = tmsMaterialMapper;
		this.tmsMaterialGroupMapper = tmsMaterialGroupMapper;
		this.tmsMaterialTypeMapper = tmsMaterialTypeMapper;
		this.tmsMaterialWholesalerMapper = tmsMaterialWholesalerMapper;
		this.tmsWholesalerMapper = tmsWholesalerMapper;
		this.tmsComboMapper = tmsComboMapper;
		this.tmsComboDetailMapper = tmsComboDetailMapper;
		this.tmsComboWholesalerMapper = tmsComboWholesalerMapper;
		this.tmsMaterialCategoryMapper = tmsMaterialCategoryMapper;
	}
	
	/**
	 * BEGIN TMSMaterial
	 */
	public List<TMSMaterial> queryTMSMaterial(TMSMaterial tmsm){
		return this.tmsMaterialMapper.selectTMSMaterial(tmsm);
	}
	
	public Page<TMSMaterial> queryTMSMaterialByPage(Page<TMSMaterial> page){
		page.setResults(this.tmsMaterialMapper.selectTMSMaterialsByPage(page));
		page.setTotalRecord(this.tmsMaterialMapper.selectTMSMaterialsSum(page));
		return page;
	}
	
	@Transactional
	public void editTMSMaterial(TMSMaterial tmsm){
		this.tmsMaterialMapper.updateTMSMaterial(tmsm);
	}
	
	@Transactional
	public void removeTMSMaterialById(int id){
		this.tmsMaterialMapper.deleteTMSMaterialById(id);
	}
	
	@Transactional
	public void createTMSMaterial(TMSMaterial tmsm){
		this.tmsMaterialMapper.insertTMSMaterial(tmsm);
	}
	/**
	 * END TMSMaterial
	 */
	
	/**
	 * BEGIN TMSMaterialType
	 */
	public List<TMSMaterialType> queryTMSMaterialType(TMSMaterialType tmsmt){
		return this.tmsMaterialTypeMapper.selectTMSMaterialType(tmsmt);
	}
	
	public Page<TMSMaterialType> queryTMSMaterialTypeByPage(Page<TMSMaterialType> page){
		page.setResults(this.tmsMaterialTypeMapper.selectTMSMaterialTypesByPage(page));
		page.setTotalRecord(this.tmsMaterialTypeMapper.selectTMSMaterialTypesSum(page));
		return page;
	}
	
	@Transactional
	public void editTMSMaterialType(TMSMaterialType tmsmt){
		this.tmsMaterialTypeMapper.updateTMSMaterialType(tmsmt);
	}
	
	@Transactional
	public void removeTMSMaterialTypeById(int id){
		this.tmsMaterialTypeMapper.deleteTMSMaterialTypeById(id);
	}
	
	@Transactional
	public void createTMSMaterialType(TMSMaterialType tmsmt){
		this.tmsMaterialTypeMapper.insertTMSMaterialType(tmsmt);
	}
	/**
	 * END TMSMaterialType
	 */
	
	/**
	 * BEGIN TMSMaterialGroup
	 */
	public List<TMSMaterialGroup> queryTMSMaterialGroup(TMSMaterialGroup tmsmg){
		return this.tmsMaterialGroupMapper.selectTMSMaterialGroup(tmsmg);
	}
	
	public Page<TMSMaterialGroup> queryTMSMaterialGroupByPage(Page<TMSMaterialGroup> page){
		page.setResults(this.tmsMaterialGroupMapper.selectTMSMaterialGroupsByPage(page));
		page.setTotalRecord(this.tmsMaterialGroupMapper.selectTMSMaterialGroupsSum(page));
		return page;
	}
	
	@Transactional
	public void editTMSMaterialGroup(TMSMaterialGroup tmsmg){
		this.tmsMaterialGroupMapper.updateTMSMaterialGroup(tmsmg);
	}
	
	@Transactional
	public void removeTMSMaterialGroupById(int id){
		this.tmsMaterialGroupMapper.deleteTMSMaterialGroupById(id);
	}
	
	@Transactional
	public void createTMSMaterialGroup(TMSMaterialGroup tmsmg){
		this.tmsMaterialGroupMapper.insertTMSMaterialGroup(tmsmg);
	}
	/**
	 * END TMSMaterialGroup
	 */
	
	/**
	 * BEGIN TMSMaterialCategory
	 */
	public List<TMSMaterialCategory> queryTMSMaterialCategory(TMSMaterialCategory tmsmg){
		return this.tmsMaterialCategoryMapper.selectTMSMaterialCategory(tmsmg);
	}
	
	public Page<TMSMaterialCategory> queryTMSMaterialCategoryByPage(Page<TMSMaterialCategory> page){
		page.setResults(this.tmsMaterialCategoryMapper.selectTMSMaterialCategorysByPage(page));
		page.setTotalRecord(this.tmsMaterialCategoryMapper.selectTMSMaterialCategorysSum(page));
		return page;
	}
	
	@Transactional
	public void editTMSMaterialCategory(TMSMaterialCategory tmsmg){
		this.tmsMaterialCategoryMapper.updateTMSMaterialCategory(tmsmg);
	}
	
	@Transactional
	public void removeTMSMaterialCategoryById(int id){
		this.tmsMaterialCategoryMapper.deleteTMSMaterialCategoryById(id);
	}
	
	@Transactional
	public void createTMSMaterialCategory(TMSMaterialCategory tmsmg){
		this.tmsMaterialCategoryMapper.insertTMSMaterialCategory(tmsmg);
	}
	/**
	 * END TMSMaterialCategory
	 */
	
	/**
	 * BEGIN TMSMaterialWholesaler
	 */
	public List<TMSMaterialWholesaler> queryTMSMaterialWholesaler(TMSMaterialWholesaler tmsmw){
		return this.tmsMaterialWholesalerMapper.selectTMSMaterialWholesaler(tmsmw);
	}
	
	public Page<TMSMaterialWholesaler> queryTMSMaterialWholesalerByPage(Page<TMSMaterialWholesaler> page){
		page.setResults(this.tmsMaterialWholesalerMapper.selectTMSMaterialWholesalersByPage(page));
		page.setTotalRecord(this.tmsMaterialWholesalerMapper.selectTMSMaterialWholesalersSum(page));
		return page;
	}
	
	@Transactional
	public void editTMSMaterialWholesaler(TMSMaterialWholesaler tmsmw){
		this.tmsMaterialWholesalerMapper.updateTMSMaterialWholesaler(tmsmw);
	}
	
	@Transactional
	public void removeTMSMaterialWholesalerById(int id){
		this.tmsMaterialWholesalerMapper.deleteTMSMaterialWholesalerById(id);
	}
	
	@Transactional
	public void createTMSMaterialWholesaler(TMSMaterialWholesaler tmsmw){
		this.tmsMaterialWholesalerMapper.insertTMSMaterialWholesaler(tmsmw);
	}
	/**
	 * END TMSMaterialWholesaler
	 */
	
	/**
	 * BEGIN TMSCombo
	 */
	public List<TMSCombo> queryTMSCombo(TMSCombo tmsc){
		return this.tmsComboMapper.selectTMSCombo(tmsc);
	}
	
	public Page<TMSCombo> queryTMSComboByPage(Page<TMSCombo> page){
		page.setResults(this.tmsComboMapper.selectTMSCombosByPage(page));
		page.setTotalRecord(this.tmsComboMapper.selectTMSCombosSum(page));
		return page;
	}
	
	@Transactional
	public void editTMSCombo(TMSCombo tmsc){
		this.tmsComboMapper.updateTMSCombo(tmsc);
	}
	
	@Transactional
	public void removeTMSComboById(int id){
		this.tmsComboMapper.deleteTMSComboById(id);
	}
	
	@Transactional
	public void createTMSCombo(TMSCombo tmsc){
		this.tmsComboMapper.insertTMSCombo(tmsc);
	}
	/**
	 * END TMSCombo
	 */
	
	/**
	 * BEGIN TMSComboDetail
	 */
	public List<TMSComboDetail> queryTMSComboDetail(TMSComboDetail tmscd){
		return this.tmsComboDetailMapper.selectTMSComboDetail(tmscd);
	}
	
	public Page<TMSComboDetail> queryTMSComboDetailByPage(Page<TMSComboDetail> page){
		page.setResults(this.tmsComboDetailMapper.selectTMSComboDetailsByPage(page));
		page.setTotalRecord(this.tmsComboDetailMapper.selectTMSComboDetailsSum(page));
		return page;
	}
	
	@Transactional
	public void editTMSComboDetail(TMSComboDetail tmscd){
		this.tmsComboDetailMapper.updateTMSComboDetail(tmscd);
	}
	
	@Transactional
	public void removeTMSComboDetailById(int id){
		this.tmsComboDetailMapper.deleteTMSComboDetailById(id);
	}
	
	@Transactional
	public void createTMSComboDetail(TMSComboDetail tmscd){
		this.tmsComboDetailMapper.insertTMSComboDetail(tmscd);
	}
	/**
	 * END TMSComboDetail
	 */
	
	/**
	 * BEGIN TMSComboWholesaler
	 */
	public List<TMSComboWholesaler> queryTMSComboWholesaler(TMSComboWholesaler tmscw){
		return this.tmsComboWholesalerMapper.selectTMSComboWholesaler(tmscw);
	}
	
	public Page<TMSComboWholesaler> queryTMSComboWholesalerByPage(Page<TMSComboWholesaler> page){
		page.setResults(this.tmsComboWholesalerMapper.selectTMSComboWholesalersByPage(page));
		page.setTotalRecord(this.tmsComboWholesalerMapper.selectTMSComboWholesalersSum(page));
		return page;
	}
	
	@Transactional
	public void editTMSComboWholesaler(TMSComboWholesaler tmscw){
		this.tmsComboWholesalerMapper.updateTMSComboWholesaler(tmscw);
	}
	
	@Transactional
	public void removeTMSComboWholesalerById(int id){
		this.tmsComboWholesalerMapper.deleteTMSComboWholesalerById(id);
	}
	
	@Transactional
	public void createTMSComboWholesaler(TMSComboWholesaler tmscw){
		this.tmsComboWholesalerMapper.insertTMSComboWholesaler(tmscw);
	}
	/**
	 * END TMSComboWholesaler
	 */

}
