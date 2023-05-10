/**
 * 
 */
package com.jeeplus.modules.smartpark.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.service.CrudService;

import com.jeeplus.modules.maintenance.dao.PdfMaintenanceDetailDao;
import com.jeeplus.modules.maintenance.entity.PdfMaintenanceDetail;
import com.jeeplus.modules.smartpark.dao.BalanceDao;
import com.jeeplus.modules.smartpark.dao.MunicipalDao;

/**
 * @author admin
 *
 */
@Service
@Transactional(readOnly = true)
public class BalanceService {

	@Autowired
	MunicipalDao municipalDao;

	@Autowired
	BalanceDao balanceDao;

	public List<MapEntity> getDeviceListByApp(String unionId) {

		return municipalDao.getDeviceListByApp();

	}

	public MapEntity getDevChannelsyDevId(String unionId) {

		MapEntity entity = new MapEntity();

		entity.put("getchannelByDevId", municipalDao.getchannelByDevId());
		entity.put("getDevdetailByDevId", municipalDao.getDevdetailByDevId());
		return entity;

	}

	public List<MapEntity> balancesByCustomer(String phone) {

		// 获取 用户缴费管理

		List<MapEntity> balancesByCustomer = balanceDao.balancesByCustomer(phone);

		for (MapEntity mapEntity : balancesByCustomer) {

			List<MapEntity> balanceDetailByCustomer = balanceDao
					.balanceDetailByCustomer(mapEntity.get("id").toString());

			mapEntity.put("datail", balanceDetailByCustomer);
		}
		return balancesByCustomer;

	}

	public List<MapEntity> homepages(String brueauId) {

		List<MapEntity> homepages = balanceDao.homepages(brueauId);

		return homepages;
	}


	public MapEntity elecNum(String brueauId) {

		MapEntity entity = new MapEntity();

		List<MapEntity> elecNum0 = balanceDao.elecNum("0", brueauId); // 昨天

		List<MapEntity> elecNum1 = balanceDao.elecNum("1", brueauId); // 今天

		entity.put("yesterday", elecNum0);
		entity.put("today", elecNum1);
//nihao 你是口袋可我们来了开导开导可打开的了打底裤的方法可怜
		return entity;

	}

	@Transactional(readOnly = false)
	public void updateData() {

		List<MapEntity> getElecNumByMinute = balanceDao.getElecNumByMinute();

//		balanceDao.deleteHisTime();

		for (MapEntity entity : getElecNumByMinute) {

			String phone = (String) entity.get("phone");
			String balanceCut = (String) entity.get("balanceCut");
			String chIds = (String) entity.get("chIds");
			System.out.println("chIds= " + chIds);
			String maxtime = entity.get("maxtime").toString();
			String first = (String) entity.get("first");
			String second = (String) entity.get("second");
			String third = (String) entity.get("third");
			String messageType = (String) entity.get("message_type");
			String electricityUnitId = entity.get("electricity_unit_id").toString();



			String[] ch = chIds.split(",");
			for (int i = 0; i < ch.length; i++) {
				String c = ch[i];
				balanceDao.updateHisTime(c, maxtime);
			}
			balanceDao.updateParkPay(balanceCut, electricityUnitId);

		}
	}

}
