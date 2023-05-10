/**
 *
 */
package com.jeeplus.modules.smartpark.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.IdGenSnowFlake;
import com.jeeplus.common.utils.ServletUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.settings.service.TChannelService;
import com.jeeplus.modules.smartpark.service.BalanceService;
import com.jeeplus.modules.smartpark.service.RoomManagerService;
import com.jeeplus.modules.sys.entity.Area;

/**
 * @author admin
 */
@Controller
@RequestMapping(value = "manager")
public class RoomManagerController extends BaseController {

    @Autowired
    private RoomManagerService roomManagerService;

    @RequestMapping(value = {"getRoomManagerList"})
    @ResponseBody
    public String getRoomManagerList(HttpServletRequest request, HttpServletResponse response, String bureauId,String arrState,String phone,
                                     String customerName, String roomName) { // 根据用户电话获取所有消息

        Page<MapEntity> page = null;
        MapEntity entity = new MapEntity();
        entity.put("bureauId", bureauId);
        entity.put("customerName", customerName);
        entity.put("roomName", roomName);
        entity.put("arrState", arrState);
        entity.put("phone", phone);

        try {
            page = roomManagerService.findPage(new Page<MapEntity>(request, response), entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServletUtils.buildRs(true, "", page);
    }

    /**
     * @param id
     * @param messageType
     * @return
     */
    @RequestMapping(value = {"updateElecWarningByType"})
    @ResponseBody
    public String updateElecWarningByType(String id, String messageType) {
        try {

            roomManagerService.updateElecWarningByType(id, messageType);
        } catch (Exception e) {
            // TODO: handle exception
            return ServletUtils.buildRs(false, "修改失败", null);
        }

        return ServletUtils.buildRs(true, "修改成功", null);
    }

    /*
     *
     * 更新1,2,3级电量报警推送
     */
    @RequestMapping(value = {"updateElecWarningByWarn"})
    @ResponseBody
    public String updateElecWarningByWarn(String id, String first, String second, String third) {
        try {
            roomManagerService.updateElecWarningByWarn(id, first, second, third);

        } catch (Exception e) {
            // TODO: handle exception
            return ServletUtils.buildRs(false, "修改失败", null);
        }
        return ServletUtils.buildRs(true, "修改成功", null);

    }

    @RequestMapping(value = {"getRechargeDetailList"})
    @ResponseBody
    public String getRechargeDetailList(HttpServletRequest request, HttpServletResponse response, String bureauId,
                                        String roomName, String customerName,String arrState,
                                        String phone, String isOnline, String rechargeStatus, String type,
                                        String beginTime, String endTime) { // 根据用户电话获取所有消息

        Page<MapEntity> page = null;

        MapEntity entity = new MapEntity();
        entity.put("bureauId", bureauId);
        entity.put("roomName", roomName);

        entity.put("phone", phone);
        entity.put("isOnline", isOnline);
        entity.put("rechargeStatus", rechargeStatus);
        entity.put("type", type);
        entity.put("arrState", arrState);

        entity.put("beginTime", beginTime);
        entity.put("endTime", endTime);

        try {
            page = roomManagerService.findPageRecharge(new Page<MapEntity>(request, response), entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ServletUtils.buildRs(false, "失败", page);
        }
        return ServletUtils.buildRs(true, "成功", page);
    }

    @RequestMapping(value = {"refundBalanceByUnitId"})
    @ResponseBody
    public String refundBalanceByUnitId(String electricityUnitId, String money, String remark, String isOnline, Integer type) {

        try {
            roomManagerService.updatePayBalanceByUnitId(electricityUnitId, money, remark, isOnline, type);
        } catch (Exception e) {
            e.printStackTrace();
            return ServletUtils.buildRs(false, "失败", null);
        }
        return ServletUtils.buildRs(true, "成功", null);
    }

    //断电
    @RequestMapping(value = {"baeakElectricity"})
    @ResponseBody
    public String baeakElectricity(String electricityUnitId, String password) {

        try {

            Integer passwordCount = roomManagerService.getPasswordCount(password, electricityUnitId);

            if (passwordCount == 0) {
                return ServletUtils.buildRs(false, "密码错误", null);
            } else {
                return ServletUtils.buildRs(true, "断电成功", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServletUtils.buildRs(false, "失败", null);
        }

    }

}
