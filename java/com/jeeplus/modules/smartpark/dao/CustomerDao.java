/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.smartpark.dao;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.persistence.TreeDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.smartpark.entity.Customer;
import com.jeeplus.modules.sys.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域DAO接口
 *
 * @author jeeplus
 * @version 2014-05-16
 */
@MyBatisDao
public interface CustomerDao extends TreeDao<Area> {


    // 区域user下的集合
    public List<MapEntity> getCustomerList(MapEntity entity);

    void insertCustomer(Customer customer);
    Integer getCustomerById(@Param(value = "unitId") String unitId);

    void updateCustomerById(Customer customer);

    void deleteCustomerById(@Param(value = "id") String id);

    void insertHistorytimeByunitId(@Param(value = "unitId") String unitId);
}
