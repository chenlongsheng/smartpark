package com.jeeplus.modules.smartpark.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.maintenance.entity.PdfMaintenanceCode;
import com.jeeplus.modules.maintenance.entity.PdfMaintenanceDetail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018-12-25.
 */
@MyBatisDao
public interface MunicipalDao extends CrudDao<PdfMaintenanceDetail> {

	List<MapEntity> getDeviceListByApp();
	
	
	List<MapEntity> getchannelByDevId();
	
	
	List<MapEntity>  getDevdetailByDevId();
	
	
	
	
	
}
