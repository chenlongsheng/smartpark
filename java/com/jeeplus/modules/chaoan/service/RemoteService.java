/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.chaoan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.chaoan.dao.EnvironmentalDao;
import com.jeeplus.modules.chaoan.dao.RemoteDao;
import com.jeeplus.modules.machine.dao.TDeviceMachineDao;
import com.jeeplus.modules.machine.entity.TDeviceMachine;

/**
 * 数据配置Service
 * 
 * @author long
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class RemoteService extends CrudService<TDeviceMachineDao, TDeviceMachine> {
	@Autowired
	private EnvironmentalDao environmentalDao;
	
	@Autowired
	private RemoteDao remoteDao;

	public List<MapEntity> powerCodes(String status) {

		List<MapEntity> powerCodes = environmentalDao.powerCodes(status);

		return powerCodes;
	}
	public List<MapEntity> getDeviceById(String devType) {

		List<MapEntity> powerCodes = remoteDao.getDeviceById(devType);

		return powerCodes;
	}
	
 
	
	public Page<MapEntity> findPageMessage(Page<MapEntity> page, MapEntity entity) {
		try {
			entity.setPage(page);
			page.setList(remoteDao.getHistoryValue(entity));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
 
	}
	

}