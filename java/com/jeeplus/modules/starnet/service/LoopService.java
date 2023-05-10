/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.starnet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.starnet.dao.LoopDao;
import com.jeeplus.modules.sys.dao.AreaDao;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 数据配置Service
 * 
 * @author long
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class LoopService extends TreeService<AreaDao, Area> {
    @Autowired
    private LoopDao loopDao;

    @Autowired
    private AreaDao areaDao;

    public List<MapEntity> loopListBypdfId(String pdfId) {

        List<MapEntity> list = null;
        List<MapEntity> loopListBypdfId = loopDao.loopListBypdfId(pdfId);// 查询配电房回路树形

        List<MapEntity> realvalueByOrgId = loopDao.getRealvalueByOrgId(pdfId);// 查询配电房下所有数据
        for (MapEntity entity : loopListBypdfId) {

            list = new ArrayList<MapEntity>();
            String id = entity.get("id").toString();

            for (MapEntity realValueEntity : realvalueByOrgId) {
                String orgId = realValueEntity.get("orgId").toString();
                String logicOrgId = (String) realValueEntity.get("logicOrgId");

                if (id.equals(logicOrgId)) {
                    list.add(realValueEntity);
                }
            }
            entity.put("data", list);
        }

        return loopListBypdfId;

    }

    public List<MapEntity> loopListByBureauId(String bureauId) {

        return loopDao.loopListByBureauId(bureauId);

    }

    public List<MapEntity> loopCodes() {

        return loopDao.loopCodes();
    }

    // 保存配电房
    @Transactional(readOnly = false)
    public void save(Area area, Integer delType) {

        super.save(area);

        if (delType != 0) {//编辑时需要type字段,如果type有变动
            loopDao.updateTypes(delType, area.getId());
        }
        //父级修改,,子集修改code替换原来旧的code
        if (StringUtils.isNotBlank(area.getOldCode())) {
            loopDao.updateCodes(area.getOldCode(), area.getCode());
        }

        UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);

    }

    @Transactional(readOnly = false)
    public String insertBureauLoop(String loopOrgIds, String bureauId) {
        try {

            loopDao.deleteBureauLoop(bureauId);
            String[] orgIds = loopOrgIds.split(",");
            for (int i = 0; i < orgIds.length; i++) {
                loopDao.insertBureauLoop(orgIds[i], bureauId);
            }
        } catch (Exception e) {
            return "false";
        }
        return "true";
    }

    public List<MapEntity> getLoopOrgId(String bureauId) {

        return loopDao.getLoopOrgId(bureauId);
    }

    @Transactional(readOnly = false)
    public void updateLoopOrgImage(String loopId, String image) {
        loopDao.updateLoopOrgImage(loopId, image);
    }

    @Transactional(readOnly = false)
    public String modifyOrgOrderNo(JSONArray jaNVR) {
        if (jaNVR != null) {
            for (int i = 0; i < jaNVR.size(); i++) {
                MapEntity en = JSONObject.parseObject(jaNVR.get(i).toString(), MapEntity.class);
         
                loopDao.updateOrderNo((String) en.get("loopId"), (String) en.get("orderNo"), areaDao.selectCode((String) en.get("loopId")));
            }
        }
        return "";
    }

    public List<MapEntity> loopPdfList(String bureauId) {

        return loopDao.loopPdfList(bureauId);
    }

}