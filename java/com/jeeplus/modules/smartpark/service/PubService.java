/**
 * 
 */
package com.jeeplus.modules.smartpark.service;

import java.util.List;
import java.util.UUID;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.Encodes;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.machine.dao.TDeviceReportDao;
import com.jeeplus.modules.maintenance.dao.PdfMaintenanceDao;
import com.jeeplus.modules.maintenance.dao.PdfMaintenanceDetailDao;
import com.jeeplus.modules.maintenance.entity.PdfMaintenanceDetail;
import com.jeeplus.modules.smartpark.dao.MunicipalDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.security.UsernamePasswordToken;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.jeeplus.modules.sys.security.exception.NoAccountException;
import com.jeeplus.modules.sys.security.exception.OutLoginTimeException;

/**
 * @author admin
 *
 */
@Service
@Transactional(readOnly = true)
public class PubService extends CrudService<PdfMaintenanceDetailDao, PdfMaintenanceDetail> {

	@Autowired
	MunicipalDao municipalDao;

	public List<MapEntity> getDeviceListByApp(String unionId) {

		return municipalDao.getDeviceListByApp();

	}	

	public MapEntity getDevChannelsyDevId(String unionId) {

		MapEntity entity = new MapEntity();

		entity.put("getchannelByDevId", municipalDao.getchannelByDevId());
		entity.put("getDevdetailByDevId", municipalDao.getDevdetailByDevId()); 
		return entity;

	}
	


}
