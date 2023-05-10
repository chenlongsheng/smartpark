/**
 *
 */
package com.jeeplus.modules.smartpark.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.util.TimeoutController.TimeoutException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.mq.rabbitmq.RabbitMQConnection;
import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.utils.JedisUtils;
import com.jeeplus.common.utils.ServletUtils;
import com.jeeplus.modules.maintenance.dao.PdfMaintenanceDao;

import com.jeeplus.modules.qxz.wx.util.HttpUtils;
import com.jeeplus.modules.settings.entity.TChannel;
import com.jeeplus.modules.settings.entity.TDeviceDetail;
import com.jeeplus.modules.settings.service.TChannelService;
import com.jeeplus.modules.settings.service.TDeviceDetailService;

import com.jeeplus.modules.smartpark.service.PubService;

import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.warm.util.SendMessage;
import com.rabbitmq.client.Channel;

import cn.hutool.http.HttpUtil;

/**
 * @author admin
 *
 */
@Controller
@RequestMapping(value = "pub")
public class PubControl {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 小程序配置
	public static String wxAppId = "wx677131086993671a";

	public static String secret = "dee4c5e579a07a254e802d9ee7dcfb56";

	public static String mchId = "1612926601";

	// public static String mchKey = "ccxs4949cxs4949cxs4949cxs4949cxs"; v3

	// v2
	public static String mchKey = "cxs4949cxs4949cxs4949cxs4949ccxs";

	@Autowired
	PubService pubService;

	@Autowired
	RabbitMQConnection rabbitMQProducer;

	@Autowired
	private TChannelService tChannelService;
	@Autowired
	private TDeviceDetailService tDeviceDetailService;
	
	private static MapEntity entity;

	@Autowired
	private RestTemplate restTemplate;
	
	

	/**
	 * description 微信授权登录接口 param [] return common.enums.ResultVO author createTime
	 * 2021/11/22 16:28
	 **/
	@RequestMapping(value = { "getCode" })
	@ResponseBody
	public String getCode(String url) throws Exception {
		System.out.println(url);
//		url = "http://192.168.3.200:80/";
		String api = "http://wx.cdsoft.cn/index.php/api/v1/cb_openid?cb_uri=" + url + "&scope=snsapi_userinfo";
		String token = HttpUtils.sendGet(api, null, null);
		System.out.println("哈哈" + token);
		return ServletUtils.buildRs(true, "获取openId详情", token);
	}

	@RequestMapping(value = { "sendMobileMessage" })
	@ResponseBody
	public void sendMobileMessage(String phone) {
		int a = (int) (Math.random() * 900000) + 100000;
		String sendMobileMessage = SendMessage.sendMobileMessage("18259005635", a + "");

		JedisUtils.set("18259005635", a + "", 0);

	}

	@RequestMapping(value = { "loginApp" })
	@ResponseBody
	public String loginApp(String phone, String code) {

		String str = JedisUtils.get("18259005635");

		if (str != null && str.equals(code)) {
			return ServletUtils.buildRs(true, "成功", "");
		}
		return ServletUtils.buildRs(false, "失败", "");

	}

	@RequestMapping(value = { "getPhone" })
	@ResponseBody
	public String getPhone(String code) {
		System.out.println(code);
		// 获取token
		String token_url = String.format(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", wxAppId,
				secret);
		JSONObject token = JSON.parseObject(HttpUtil.get(token_url));

		// 使用前端code获取手机号码 参数为json格式
		String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token="
				+ token.getString("access_token");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("code", code);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(paramMap, headers);
		System.out.println(httpEntity);
		ResponseEntity<Object> response = restTemplate.postForEntity(url, httpEntity, Object.class);

		System.out.println(response.getBody());
		return ServletUtils.buildRs(true, "手機號", response.getBody());

	}

	@RequestMapping(value = { "wxcallback" }) 
	@ResponseBody
	public String wxcallback(String code) {
		System.out.println(code);
		// 第二步：通过code换取网页授权access_token
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxAppId + "&secret=" + secret
				+ "&code=" + code + "&grant_type=authorization_code";
		ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
		JSONObject jsonObject = JSONObject.parseObject(res.getBody());
				
 		return jsonObject.toString();
		
	}
	
	
}
