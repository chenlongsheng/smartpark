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
public interface RoomManagerDao {

    List<MapEntity> getRoomManagerList(MapEntity entity); // 获取


    //   发送微信短信类型
    void updateElecWarningByType(@Param(value = "id") String id, @Param(value = "messageType") String messageType);


    //   1,2,3级余额报警
    void updateElecWarningByWarn(@Param(value = "id") String id, @Param(value = "first") String first,
                                 @Param(value = "second") String second, @Param(value = "third") String third);


    List<MapEntity> getRechargeDetailList(MapEntity entity);


    void updatePayBalanceByUnitId(@Param(value = "electricityUnitId") String electricityUnitId, @Param(value = "money") String money,
                                  @Param(value = "remark") String remark, @Param(value = "isOnline")String isOnline, @Param(value = "type")Integer type);


    Integer getPasswordCount(@Param(value = "password") String password, @Param(value = "electricityUnitId") String electricityUnitId);


}
