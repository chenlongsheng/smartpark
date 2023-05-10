/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.smartpark.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctc.wstx.util.StringUtil;
import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.service.TreeService;
import com.jeeplus.common.utils.IdGenSnowFlake;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.maintenance.dao.PdfUserDao;
import com.jeeplus.modules.settings.dao.TDeviceDao;
import com.jeeplus.modules.smartpark.dao.AreaFloorDao;
import com.jeeplus.modules.sys.dao.AreaDao;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 区域Service
 *
 * @author jeeplus
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaFloorService extends TreeService<AreaDao, Area> {

    @Autowired
    private AreaFloorDao areaFloorDao;


    // 保存配电房
    @Transactional(readOnly = false)
    public void save(Area area) {

        area.setId(IdGenSnowFlake.uuid().toString());
        areaFloorDao.insertFloor(area);

    }


    // 保存配电房
    @Transactional(readOnly = false)
    public void updateFloor(String id, String name) {

        areaFloorDao.updateFloor(id, name);

    }

    public List<MapEntity> getFloorList(String parentId) {

        return areaFloorDao.getFloorList(parentId);

    }


    //
    @Transactional(readOnly = false)
    public void deleteFloor(String id) {

        areaFloorDao.deleteFloor(id);

    }


}
