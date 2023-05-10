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
public interface BalanceDao  {

	List<MapEntity> getDeviceListByApp();
	
	
	List<MapEntity> getchannelByDevId();
	
	
	List<MapEntity>  getDevdetailByDevId();
	

	List<MapEntity>  balancesByCustomer(@Param(value = "phone") String phone);   //获取 用户缴费管理
	
	List<MapEntity>  balanceDetailByCustomer(@Param(value = "payId") String payId );  //获取 用户缴费详情
	
	
	
	//五分鐘查詢一會
	List<MapEntity>	getElecNumByMinute();



	
	void  deleteHisTime();
		
	void updateHisTime(@Param(value = "chId") String chId,@Param(value = "maxtime") String maxtime);
	
	void updateParkPay(@Param(value = "balance") String balance,@Param(value = "electricityUnitId") String electricityUnitId);
		
	List<MapEntity>	homepages(@Param(value = "bureauId") String bureauId);
	
	List<MapEntity> elecNum(@Param(value = "state") String state,@Param(value = "bureauId") String bureauId);
	
	
	
	
}
