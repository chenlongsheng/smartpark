/**
 * 
 */
package com.jeeplus.modules.smartpark.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.utils.ServletUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.settings.service.TChannelService;
import com.jeeplus.modules.smartpark.dao.BalanceDao;
import com.jeeplus.modules.smartpark.service.BalanceService;

/**
 * @author admin
 *
 */
@Controller
@RequestMapping(value = "banlance")
public class BanlanceController extends BaseController {

	@Autowired
	private BalanceService balanceService;

	@Autowired
	private BalanceDao balanceDao;

	@RequestMapping(value = { "getElecNumByMinute" })
	@ResponseBody
	private void getElecNumByMinute() {

		balanceService.updateData();

	}

	@RequestMapping(value = { "balancesByCustomer" })
	@ResponseBody
	public String balancesByCustomer(String phone) { // 根据用户电话获取所有消息
		List<MapEntity> balancesByCustomer = null;
		try {

			balancesByCustomer = balanceService.balancesByCustomer(phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ServletUtils.buildRs(true, "", balancesByCustomer);

	}

	@RequestMapping(value = { "getElecNum" })
	@ResponseBody
	public String getElecNum(String bureauId) {

		return ServletUtils.buildRs(true, "", balanceService.elecNum(bureauId));

	}//

	@RequestMapping(value = { "homepages" })
	@ResponseBody
	public String homepages(String bureauId) { // 根据用户电话获取所有消息

		return ServletUtils.buildRs(true, "", balanceService.homepages(bureauId));

	}

}
