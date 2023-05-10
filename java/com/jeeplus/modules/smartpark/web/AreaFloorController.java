/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.smartpark.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ctc.wstx.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.utils.ServletUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.settings.dao.TDeviceDao;
import com.jeeplus.modules.smartpark.service.AreaFloorService;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.service.AreaService;
import com.jeeplus.modules.sys.utils.UserUtils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 区域Controller
 * 
 * @author jeeplus
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "areaFloor")
public class AreaFloorController extends BaseController {

	@Autowired
	private AreaService areaService;

	@Autowired
	private AreaFloorService areaFloorService;

	@Autowired
	private TDeviceDao tDeviceDao;

	// 首页树形所有集合 和配电房树形查询
	@RequestMapping(value = { "getFloorList" })
	@ResponseBody
	public String getFloorList(String parentId) {

		List<MapEntity> floorList = null;

		try {
			floorList = areaFloorService.getFloorList(parentId);
		} catch (Exception e) {
			e.printStackTrace();
			return ServletUtils.buildRs(false, "所有集合查报错", null);
		}
		return ServletUtils.buildRs(true, "所有集合查询", floorList);
	}

	// 配电房管理添加
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(Area area, Model model, RedirectAttributes redirectAttributes) {

		if (StringUtils.isNotBlank(area.getParentId())) {
			Area a = new Area();
			a.setId(area.getParentId());
			area.setParent(a);
		}
		System.out.println(area.getParent().getId() + "------区域父id-----------");
		// 获取区域底下最大code
		String addCode = "";
		String str = "";
		String code = "10";
		String maxCode = "10";
		if (!"0".equals(area.getParentId())) {
			code = areaService.selectCode(area.getParentId());
			maxCode = areaService.maxCode(area.getParentId());
		}
		if (maxCode != null) {
			str = maxCode.substring(maxCode.length() - 2);
			System.out.println(str);
		}
		if (maxCode == null) {
			addCode = code + "01";
		} else {
			addCode = code + String.format("%02d", (Long.parseLong(str) + 1));
		}
		// 修改区域时候变更父id改变code
		if (area.getId() != null) {
			Area a = areaService.get(area.getId());
			System.out.println("加入area---" + area.getParentId());
			System.out.println("加入area---" + a.getParentId());
			if (!a.getParentId().equals(area.getParentId())) {
				area.setCode(addCode);
				area.setParentIds(a.getParentIds());
			}
		} else {
			Area a = areaService.get(area.getParentId());
			area.setType("5");
			area.setCode(addCode);
			area.setParentIds(a.getParentIds() + area.getParentId() + ",");
		}
		try {
			areaFloorService.save(area);

		} catch (Exception e) {
			e.printStackTrace();
			return ServletUtils.buildRs(false, "保存区域'" + area.getName() + "'失败", null);
		}
		return ServletUtils.buildRs(true, "保存区域'" + area.getName() + "'成功", null);
	}

	// 配电房管理添加
	@RequestMapping(value = "updateFloor")
	@ResponseBody
	public String updateFloor(String id, String name) {

		areaFloorService.updateFloor(id, name);

		return ServletUtils.buildRs(true, "", null);

	}

	@RequestMapping(value = "deleteFloor")
	@ResponseBody
	public String deleteFloor(String id) {

		try {
			areaFloorService.deleteFloor(id);

		} catch (Exception e) {
			return ServletUtils.buildRs(false, "删除失败！", null);
		}
		return ServletUtils.buildRs(true, "删除成功", null);
	}

}
