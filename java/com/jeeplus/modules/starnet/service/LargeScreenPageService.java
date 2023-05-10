/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.starnet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.modules.starnet.dao.LargeScreenPageDao;

/**
 * 数据配置Service
 * 
 * @author long
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class LargeScreenPageService {

	@Autowired
	private LargeScreenPageDao largeScreenPageDao;

	public List<MapEntity> getChNames() {

		return largeScreenPageDao.getChNames();

	}

	public List<MapEntity> getHistoryByChId(String chId, String time) {

		return largeScreenPageDao.getHistoryByChId(chId, time);

	}

	public List<MapEntity> getOrgNums() {

		return largeScreenPageDao.getOrgNums();

	}

}