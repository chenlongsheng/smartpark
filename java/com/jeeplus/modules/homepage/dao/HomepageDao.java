package com.jeeplus.modules.homepage.dao;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.persistence.annotation.MyBatisDao;

import java.util.List;

/**
 * Created by Administrator on 2018-12-20.
 */
@MyBatisDao
public interface HomepageDao {


	List<MapEntity> getTypeList();

	List<MapEntity> getDeviceNumList();



	
	
	
}
