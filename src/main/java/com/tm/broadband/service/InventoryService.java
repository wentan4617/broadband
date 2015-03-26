package com.tm.broadband.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tm.broadband.mapper.EquipLogMapper;
import com.tm.broadband.mapper.EquipMapper;
import com.tm.broadband.mapper.EquipPatternMapper;
import com.tm.broadband.model.Equip;
import com.tm.broadband.model.EquipLog;
import com.tm.broadband.model.EquipPattern;
import com.tm.broadband.model.Page;

/**
 * CRM service
 * 
 * @author Don Chen
 * 
 */
@Service
public class InventoryService {
	
	private EquipMapper equipMapper;
	private EquipLogMapper equipLogMapper;
	private EquipPatternMapper equipPatternMapper;

	public InventoryService() { }
	
	@Autowired
	public InventoryService(EquipMapper equipMapper,
			EquipLogMapper equipLogMapper,
			EquipPatternMapper equipPatternMapper){
		this.equipMapper = equipMapper;
		this.equipLogMapper = equipLogMapper;
		this.equipPatternMapper = equipPatternMapper;
	}
	
	
	/**
	 * BEGIN Equip
	 */
	
	public List<Equip> queryEquips(Equip e){
		return this.equipMapper.selectEquip(e);
	}
	
	public Equip queryEquip(Equip e){
		List<Equip> es = this.queryEquips(e);
		return es!=null && es.size()>0 ? es.get(0) : null;
	}
	
	public Page<Equip> queryEquipsByPage(Page<Equip> page){
		page.setTotalRecord(this.equipMapper.selectEquipsSum(page));
		page.setResults(this.equipMapper.selectEquipsByPage(page));
		return page;
	}
	
	public int queryEquipsSumByPage(Page<Equip> page){
		return this.equipMapper.selectEquipsSum(page);
	}

	@Transactional
	public void editEquip(Equip e){
		this.equipMapper.updateEquip(e);
	}

	@Transactional
	public void removeEquipById(int id){
		this.equipMapper.deleteEquipById(id);
	}

	@Transactional
	public void createEquip(Equip e){
		this.equipMapper.insertEquip(e);
	}
	
	/**
	 * END Equip
	 */
	
	
	/**
	 * BEGIN EquipLog
	 */
	
	public List<EquipLog> queryEquipLogs(EquipLog el){
		return this.equipLogMapper.selectEquipLog(el);
	}
	
	public EquipLog queryEquipLog(EquipLog el){
		List<EquipLog> els = this.queryEquipLogs(el);
		return els!=null && els.size()>0 ? els.get(0) : null;
	}
	
	public Page<EquipLog> queryEquipLogsByPage(Page<EquipLog> page){
		page.setTotalRecord(this.equipLogMapper.selectEquipLogsSum(page));
		page.setResults(this.equipLogMapper.selectEquipLogsByPage(page));
		return page;
	}

	@Transactional
	public void editEquipLog(EquipLog el){
		this.equipLogMapper.updateEquipLog(el);
	}

	@Transactional
	public void removeEquipLogById(int id){
		this.equipLogMapper.deleteEquipLogById(id);
	}

	@Transactional
	public void createEquipLog(EquipLog el){
		this.equipLogMapper.insertEquipLog(el);
	}
	
	/**
	 * END EquipLog
	 */
	
	
	/**
	 * BEGIN EquipPattern
	 */
	
	public List<EquipPattern> queryEquipPatterns(EquipPattern ep){
		return this.equipPatternMapper.selectEquipPattern(ep);
	}
	
	public EquipPattern queryEquipPattern(EquipPattern ep){
		List<EquipPattern> eps = this.queryEquipPatterns(ep);
		return eps!=null && eps.size()>0 ? eps.get(0) : null;
	}
	
	public Page<EquipPattern> queryEquipPatternsByPage(Page<EquipPattern> page){
		page.setTotalRecord(this.equipPatternMapper.selectEquipPatternsSum(page));
		page.setResults(this.equipPatternMapper.selectEquipPatternsByPage(page));
		return page;
	}

	@Transactional
	public void editEquipPattern(EquipPattern ep){
		this.equipPatternMapper.updateEquipPattern(ep);
	}

	@Transactional
	public void removeEquipPatternById(int id){
		this.equipPatternMapper.deleteEquipPatternById(id);
	}

	@Transactional
	public void createEquipPattern(EquipPattern ep){
		this.equipPatternMapper.insertEquipPattern(ep);
	}
	
	/**
	 * END EquipPattern
	 */
}
