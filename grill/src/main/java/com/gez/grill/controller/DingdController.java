package com.gez.grill.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gez.grill.entity.DingdBasic;
import com.gez.grill.entity.Dingddp;
import com.gez.grill.entity.Result;
import com.gez.grill.service.DingdService;
import com.gez.grill.service.GukService;

/**
 * 订单控制器类
 * 
 * @author Zhaowei
 * 
 */
@Controller
@RequestMapping(value = "/dingd")
public class DingdController {

	private final static Logger logger = Logger.getLogger(DingdController.class);

	@Autowired
	private DingdService dingdInstance;

	@Autowired
	private GukService gukInstance;

	/**
	 * 订单列表2014-3-19
	 */
	@RequestMapping(value = "/getOrdersList", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<DingdBasic> getOrdersList(
			@RequestParam(value = "pageIndex") int pageIndex,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "gukId") String gukId) {
		ArrayList<DingdBasic> orderList = new ArrayList<DingdBasic>();
		try {
			orderList = dingdInstance.getOrdersList(pageIndex, pageSize, gukId);
		} catch (Exception e) {
			logger.error(e);
		}
		return orderList;
	}

	/**
	 * 订单点评列表2014-3-28
	 */
	@RequestMapping(value = "/getOrdersCommentList", method = RequestMethod.GET)
	@ResponseBody
	public Dingddp getOrdersCommentList(
			@RequestParam(value = "dingdId") String dingdId) {
		Dingddp ordersCommentList = new Dingddp();
		try {
			ordersCommentList = dingdInstance.getOrdersCommentList(dingdId);
		} catch (Exception e) {
			logger.error(e);
		}
		return ordersCommentList;
	}

	/**
	 * 提交订单2014-3-25
	 */
	@RequestMapping(value = "/submitOrders", method = RequestMethod.POST)
	@ResponseBody
	public Object submitOrder(@RequestParam(value = "gukdh") String gukdh,
			@RequestParam(value = "gukscdz") String gukscdz,
			@RequestParam(value = "gukbz") String gukbz,
			@RequestParam(value = "songcsjxz") String songcsjxz,
			@RequestParam(value = "songcsj", required = false) String songcsj,
			@RequestParam(value = "peisf") String peisf,
			@RequestParam(value = "zhiffs") String zhiffs,
			@RequestParam(value = "duojf") boolean duojf,
			@RequestParam(value = "shaojf") boolean shaojf,
			@RequestParam(value = "duofl") boolean duofl,
			@RequestParam(value = "shaofl") boolean shaofl,
			@RequestParam(value = "bufc") boolean bufc,
			@RequestParam(value = "bufs") boolean bufs,
			@RequestParam(value = "bufj") boolean bufj,
			@RequestParam(value = "buykz") boolean buykz,
			@RequestParam(value = "gukId") String gukId,
			@RequestParam(value = "cantId") String cantId,
			@RequestParam(value = "dingdmx") String dingdmx) {
		Object kyResult = null;
		boolean teResult = true;
		try {
			teResult = dingdInstance.submitOrders(gukdh, gukscdz, gukbz, songcsjxz,
					songcsj, peisf, zhiffs, duojf, shaojf, duofl, shaofl, bufc,
					bufs, bufj, buykz, gukId, cantId, dingdmx);
		} catch (Exception e) {
			logger.error(e);

			teResult = false;
		}
		if (teResult) {
			//向微信商家推送信息
			kyResult = gukInstance.getCustomerInfo(gukId);
		} else {
			kyResult = new Result(teResult, "提交订单失败了 :;(∩´﹏`∩);:");
		}
		return kyResult;
	}

	/**
	 * 点评2014-3-28
	 */
	@RequestMapping(value = "/addCommentsForOrder", method = RequestMethod.POST)
	@ResponseBody
	public Result addCommentsForOrder(
			@RequestParam(value = "dingdId") String dingdId,
			@RequestParam(value = "gukId") String gukId,
			@RequestParam(value = "pingl") String pingl) {
		Result kyResult = new Result();
		try {
			dingdInstance.addCommentsForOrder(dingdId, gukId, pingl);

			kyResult.setSuccess(true);
			kyResult.setMessage("");
		} catch (Exception e) {
			logger.error(e);

			kyResult.setSuccess(false);
			kyResult.setMessage("点评失败了 :;(∩´﹏`∩);:");
		}
		return kyResult;
	}

	/**
	 * 催单2014-3-24
	 */
	@RequestMapping(value = "/getHurry", method = RequestMethod.POST)
	@ResponseBody
	public Result getHurry(@RequestParam(value = "dingdId") String dingdId) {
		Result kyResult = new Result();
		try {
			dingdInstance.getHurry(dingdId);

			kyResult.setSuccess(true);
			kyResult.setMessage("");
		} catch (Exception e) {
			logger.error(e);

			kyResult.setSuccess(false);
			kyResult.setMessage("催单失败了 :;(∩´﹏`∩);:");
		}
		return kyResult;
	}
}