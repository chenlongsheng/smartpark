/**
 * 
 */
package com.jeeplus.modules.sys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jeeplus.modules.homepage.dao.StatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.service.TreeService;
import com.jeeplus.common.utils.StringUtils;

import com.jeeplus.modules.sys.dao.BureauDao;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * @author admin
 *
 */
@Service
@Transactional(readOnly = true)
public class BureauService extends TreeService<BureauDao, Area> {

	@Autowired
	BureauDao bureauDao;

	StatisticsDao statisticsDao;

	public MapEntity getBureauList() {

		MapEntity mapEntity = new MapEntity();
		Set<MapEntity> bureauList = new HashSet<MapEntity>();
		System.out.println("");
		List<MapEntity> getBureauIds = bureauDao.getBureauIds(UserUtils.getUser().getId());// 获取所有用户的供电所
		if (UserUtils.getUser().getId().equals("1")) {
			mapEntity.put("bureauList", getBureauIds);
			mapEntity.put("bureaus", getBureauIds);
			return mapEntity;
		}
		for (MapEntity entity : getBureauIds) {// 补全非admin下所有的供电单位树形结构
			String pIds = (String) entity.get("pIds");
			System.out.println(pIds + "=================");
			List<MapEntity> orgListByPId = bureauDao.getOrgListByPId(pIds);
			bureauList.addAll(orgListByPId);
		}
		mapEntity.put("bureauList", bureauList);
		mapEntity.put("bureaus", getBureauIds);

		return mapEntity;
	}

	@Transactional(readOnly = false)
	public void updatePassById(String id, String password) {
		bureauDao.updatePassById(id, password);
	}

	public MapEntity selectCharges() {

		MapEntity en = new MapEntity();

		en.put("selectChargePrice", bureauDao.selectChargePrice());
		en.put("selectCharge", bureauDao.selectCharges());

		return en;

	}

	@Transactional(readOnly = false)
	public void modifyCharges(JSONArray ja, JSONArray jaPrice) {

		bureauDao.deleteChanger();
		for (int i = 0; i < ja.size(); i++) {
			MapEntity entity = JSONObject.parseObject(ja.get(i).toString(), MapEntity.class);
			bureauDao.modifyCharges(entity.get("startTime").toString(), entity.get("endTime").toString(),
					entity.get("chargeUnitId").toString());
		}

		for (int i = 0; i < jaPrice.size(); i++) {
			MapEntity entity = JSONObject.parseObject(jaPrice.get(i).toString(), MapEntity.class);
			bureauDao.updateChargeUnit(entity.get("id").toString(), entity.get("price").toString());
		}
	}
	
	@Transactional(readOnly = false)
	public void deleteBureauById(String bureauId) {
		bureauDao.deleteBureauById(bureauId);
	}

	
	@Transactional(readOnly = false)
	public void saveBureauById(String bureauId, String parentId, String floorNum, String roomNum, String name) {
		MapEntity entity = new MapEntity();
		entity.put("floorNum", floorNum);
		entity.put("name", name);
		entity.put("roomNum", roomNum);
		entity.put("bureauId", bureauId);
		entity.put("parentId", parentId);

		if (StringUtils.isBlank(bureauId)) {    // 添加
			bureauDao.insertBureau(entity);
			entity.clear();
		} else {

			bureauDao.updateBureau(entity);
			entity.clear();
		}
	}

	@Transactional(readOnly = false)
	public void updateOrderNo(JSONArray ja) {// 修改同级排序
		for (int i = 0; i < ja.size(); i++) {
			MapEntity mapEntity = JSONObject.parseObject(ja.get(i).toString(), MapEntity.class);
			Integer orderNo = (Integer) mapEntity.get("orderNo");
			String bureauId = (String) mapEntity.get("bureauId");
			bureauDao.updateOrderNo(orderNo, bureauId);
		}
	}

	
	public List<MapEntity> getBureauListByUserId() {

		return bureauDao.getBureauListByUserId(UserUtils.getUser().getId());

	}

}
