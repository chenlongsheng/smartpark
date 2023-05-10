/**
 * 
 */
package com.jeeplus.modules.sys.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.utils.ServletUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.service.BureauService;


/**
 * @author admin
 *
 */
@Controller
@RequestMapping(value = "sys/bureau")
public class BureauController extends BaseController {

	@Autowired
	private BureauService bureauService;

	/*
	 * 用户归属所有供电所和配电房
	 */
	@RequestMapping(value = { "getBureauList" })
	@ResponseBody
	public String getBureauList() {
		return ServletUtils.buildRs(true, "用户归属供电所集合", bureauService.getBureauList());
	}

	@ResponseBody
	@RequestMapping(value = "deleteBureauById")
	public String deleteBureauById(String bureauId) {

		try {
			bureauService.deleteBureauById(bureauId);
		} catch (Exception e) {
			e.printStackTrace();
			return ServletUtils.buildRs(false, "删除失败", "");
		}
		return ServletUtils.buildRs(true, "成功删除", "");
	}

	@ResponseBody
	@RequestMapping(value = "saveBureauById")
	public String saveBureauById(String bureauId, String parentId, String floorNum, String roomNum, String name) {
		try {
			bureauService.saveBureauById(bureauId, parentId, floorNum, roomNum, name);

		} catch (Exception e) {

			return ServletUtils.buildRs(false, "修改失败", "");
		}
		return ServletUtils.buildRs(true, "修改成功", "");
	}

	@ResponseBody
	@RequestMapping(value = "updatePassById")
	public String updatePassById(String id, String password) {
		try {
			bureauService.updatePassById(id, password);

		} catch (Exception e) {

			return ServletUtils.buildRs(false, "修改失败", "");
		}
		return ServletUtils.buildRs(true, "修改成功", "");
	}

	@RequestMapping(value = "updateOrderNo")
	@ResponseBody
	public String updateOrderNo(String orderList) {

		String list = orderList.replace("&quot;", "'");
		JSONArray ja = JSONArray.parseArray(list);
		try {
			bureauService.updateOrderNo(ja);
		} catch (Exception e) {
			e.printStackTrace();
			return ServletUtils.buildRs(false, "修改失败", "");
		}
		return ServletUtils.buildRs(true, "修改成功", "");
	}
	
	/*
	 * 
	 * 獲取付費率
	 */

	@RequestMapping("/selectCharges")
	@ResponseBody
	public String selectCharges() {
		
		System.out.println("ppppp");
 
		MapEntity selectCharges = null;
		try {
			selectCharges = bureauService.selectCharges();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ServletUtils.buildRs(true, "查询费率", selectCharges);
	}

	/*
	 * 付费率设置
	 */
	@RequestMapping(value = { "insertStarEnergy" })
	@ResponseBody
	public String insertStarEnergy(String jsonChanger, String jsonChangerPrice) {

//		jsonChanger = "[{'startTime':'00:00:00','endTime':'05:30:00','state':'0','price':'0.85'},{'startTime':'05:30:00','endTime':'15:30:00','state':'0','price':'0.55'},{'startTime':'15:30:00','endTime':'23:59:59','state':'0','price':'0.66'}]";
		System.out.println("jsonChanger+=======" + jsonChanger);
		jsonChanger = jsonChanger.replace("&quot;", "'");
		JSONArray ja = JSONArray.parseArray(jsonChanger);

		jsonChangerPrice = jsonChangerPrice.replace("&quot;", "'");
		JSONArray jaPrice = JSONArray.parseArray(jsonChangerPrice);

		try {
			bureauService.modifyCharges(ja, jaPrice);
		} catch (Exception e) {
			e.printStackTrace();
			return ServletUtils.buildRs(false, "付费率设置", "");
		}
		return ServletUtils.buildRs(true, "付费率设置", "");
	}

}
