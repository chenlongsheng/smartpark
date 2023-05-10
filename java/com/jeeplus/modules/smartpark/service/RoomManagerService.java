/**
 * 
 */
package com.jeeplus.modules.smartpark.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.modules.smartpark.dao.RoomManagerDao;
import com.jeeplus.modules.sys.entity.Area;

/**
 * @author admin
 *
 */
@Service
@Transactional(readOnly = true)
public class RoomManagerService {

	@Autowired
	RoomManagerDao roomManagerDao;

	public Page<MapEntity> findPage(Page<MapEntity> page, MapEntity entity) {
		entity.setPage(page);
		List<MapEntity> list = roomManagerDao.getRoomManagerList(entity);
		page.setList(list);
		return page;
	}

	@Transactional(readOnly = false)
	public void updateElecWarningByType(String id, String messageType) {

		roomManagerDao.updateElecWarningByType(id, messageType);

	}

	@Transactional(readOnly = false)
	public void updateElecWarningByWarn(String id, String first, String second, String third) {

		roomManagerDao.updateElecWarningByWarn(id, first, second, third);

	}

	public Page<MapEntity> findPageRecharge(Page<MapEntity> page, MapEntity entity) {
		entity.setPage(page);
		List<MapEntity> list = roomManagerDao.getRechargeDetailList(entity);
		page.setList(list);
		return page;
	}

	@Transactional(readOnly = false)
	public void updatePayBalanceByUnitId(String electricityUnitId,String money,String remark,String isOnline,Integer type) {


		if (type ==1) {
			money = "-"+money; //退电
		}

		roomManagerDao.updatePayBalanceByUnitId(electricityUnitId,money,remark,isOnline,type);

	}

	public Integer getPasswordCount(String password, String electricityUnitId) {

		return roomManagerDao.getPasswordCount(password, electricityUnitId);
	}

}
