package com.gez.grill.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gez.grill.entity.GukBasic;
import com.gez.grill.entity.Result;
import com.gez.grill.entity.Banb;
import com.gez.grill.service.GukService;

/**
 * 顾客控制器类
 * 
 * @author Zhaowei
 * 
 */
@Controller
@RequestMapping(value = "/guk")
public class GukController {

	private final static Logger logger = Logger.getLogger(GukController.class);

	@Autowired
	private GukService gukInstance;

	/**
	 * 顾客登录2014-3-17
	 */
	@RequestMapping(value = "/getCustomerInfo", method = RequestMethod.GET)
	@ResponseBody
	public GukBasic getCustomerInfo(@RequestParam(value = "gukId") String gukId) {
		GukBasic customerLogin = new GukBasic();
		try {
			if (gukInstance.isCustomerExist(gukId) < 1) {
				gukInstance.newCustomerComming(gukId);
			}
			customerLogin = gukInstance.getCustomerInfo(gukId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return customerLogin;
	}

	/**
	 * 绑定账号2014-4-1
	 */
	@RequestMapping(value = "/bindAccount", method = RequestMethod.POST)
	@ResponseBody
	public GukBasic bindAccount(
			@RequestParam(value = "gukId") String gukId,
			@RequestParam(value = "weixh", required = false) String weixh,
			@RequestParam(value = "weixm", required = false) String weixm,
			@RequestParam(value = "dianh1", required = false) String dianh1,
			@RequestParam(value = "dianh2", required = false) String dianh2,
			@RequestParam(value = "diz1", required = false) String diz1,
			@RequestParam(value = "diz2", required = false) String diz2,
			@RequestParam(value = "diz3", required = false) String diz3,
			@RequestParam(value = "diz4", required = false) String diz4,
			@RequestParam(value = "diz5", required = false) String diz5) {
		GukBasic customerInfo = new GukBasic();
		try {
			customerInfo = gukInstance.bindAccount(gukId, weixh, weixm, dianh1, dianh2, diz1, diz2, diz3, diz4, diz5);
		} catch (Exception e) {
			logger.error(e);
		}
		return customerInfo;
	}

	/**
	 * 用户意见建议2014-4-9 司马懿大招！
	 */
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	@ResponseBody
	public Result bindAccount(
			@RequestParam(value = "gukId") String gukId,
			@RequestParam(value = "gukfk") String gukfk) {
		Result kyResult = new Result();
		try {
			gukInstance.feedback(gukId, gukfk);
			
			kyResult.setSuccess(true);
			kyResult.setMessage("");
		} catch (Exception e) {
			logger.error(e);
			
			kyResult.setSuccess(false);
			kyResult.setMessage("反馈失败了 :;(∩´﹏`∩);:");
		}
		return kyResult;
	}
	
	/**
	 * 最新版本2014-4-28
	 */
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	@ResponseBody
	public Banb getVersion() {
		Banb current = new Banb();
		try {
			current = gukInstance.getVersion();
		} catch (Exception e) {
			logger.error(e);
		}
		return current;
	}
}