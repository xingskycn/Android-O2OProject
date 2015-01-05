package com.gez.grill.controller;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.gez.grill.entity.Guktz;
import com.gez.grill.entity.Gukxc;
import com.gez.grill.entity.Result;
import com.gez.grill.entity.Shouy;
import com.gez.grill.entity.ShouyBasic;
import com.gez.grill.entity.Taocmx;
import com.gez.grill.entity.UploadFileResult;
import com.gez.grill.service.ShouyService;
import com.gez.grill.service.UploadService;

/**
 * 首页控制器类
 * 
 * @author 徐芝伟
 * 
 */
@Controller
@RequestMapping(value = "/shouy")
public class ShouyController {

	private final static Logger logger = Logger.getLogger(ShouyController.class);

	@Autowired
	private ShouyService shouyInstance;
	
	@Autowired
	private UploadService uploadInstence;
	
	
	/**
	 * 首页列表2014-10-8
	 */
	
	@RequestMapping(value = "/getShouyList", method = RequestMethod.GET)
	@ResponseBody
	public Shouy getShouyList(
			@RequestParam(value = "guk_id") String guk_id
			) {
		Shouy shouy = new Shouy();
		ArrayList<Taocmx> gettaocmxList = new ArrayList<Taocmx>();
		ArrayList<Gukxc> getGukxcList = new ArrayList<Gukxc>();
		ArrayList<Guktz> getGuktzList = new ArrayList<Guktz>();
	    ArrayList<ShouyBasic> shouyBasic = null;
		
		try {
			gettaocmxList = shouyInstance.gettaocmxList(guk_id);	//正确
			getGuktzList = shouyInstance.getguktzList(guk_id);	//本地验证正确
			getGukxcList = shouyInstance.getGukxcList(guk_id);	//没拍照不知道
			shouyBasic = shouyInstance.getShouyBasicList(guk_id);
			ShouyBasic shouybasic = shouyBasic.get(0);
			shouy.setKaissy(shouybasic.getKaissy());
			shouy.setJiessy(shouybasic.getJiessy());
			shouy.setDingd_id(shouybasic.getDingd_id());
			shouy.setCaip_id(shouybasic.getCaip_id());
			shouy.setGuktz(getGuktzList);
			shouy.setGukxc(getGukxcList);
			shouy.setTaocmx(gettaocmxList);
		
		} catch (Exception e) {
			logger.error(e);
		}
		return shouy;
	}
	
	/**
	 * 套餐明细列表2014-9-30
	 */
	@RequestMapping(value = "/getTaocmxList", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Taocmx> gettaocmxList(
			@RequestParam(value = "guk_id") String guk_id
			) {
		ArrayList<Taocmx> getTaocmxList = new ArrayList<Taocmx>();
		
		try {
			getTaocmxList = shouyInstance.gettaocmxList(guk_id);
		} catch (Exception e) {
			logger.error(e);
		}
		return getTaocmxList;
	}

	/**
	 * 顾客体重列表2014-9-30
	 */
	@RequestMapping(value = "/getGuktzList", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Guktz> getGuktzList(
			@RequestParam(value = "guk_id") String guk_id
		) {
		ArrayList<Guktz> getGuktzList = new ArrayList<Guktz>();
		try {
			getGuktzList = shouyInstance.getguktzList(guk_id);
		} catch (Exception e) {
			logger.error(e);
		}
		return getGuktzList;
	}
	

	/**
	 * 顾客相册列表2014-9-30
	 */
	@RequestMapping(value = "/getGukxcList", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Gukxc> getGukxcList(
			@RequestParam(value = "guk_id") String guk_id
		) {
		ArrayList<Gukxc> getGukxcList = new ArrayList<Gukxc>();
		try {
			getGukxcList = shouyInstance.getGukxcList(guk_id);
		} catch (Exception e) {
			logger.error(e);
		}
		return getGukxcList;
	}
	
	
	/**
	 * 顾客添加体重数据2014-9-30
	 */
	@RequestMapping(value = "/insertGuktz", method = RequestMethod.POST)
	@ResponseBody
	public Result submitGuktz(
			@RequestParam(value = "riq") String riq,
			@RequestParam(value = "zhuangt") String zhuangt,
			@RequestParam(value = "tiz") float tiz,
			@RequestParam(value = "guk_id") String guk_id
			) {
		Result result = new Result();
		try {
			shouyInstance.insertGuktz(riq, zhuangt, tiz, guk_id);
			result.setSuccess(true); 
			result.setMessage("录入成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("对不起，录入错误！");
			logger.error(e);
		}
		return result;
	}
	
	/**
	 * 顾客添加相册数据2014-9-30
	 */ 
	@RequestMapping(value = "/insertGukxc", method = RequestMethod.POST)
	@ResponseBody
	public Result submitGukxc(
			@RequestParam(value = "riq") String riq,
			MultipartHttpServletRequest zhaop,
			@RequestParam(value = "guk_id") String guk_id
			) {
		Result result = new Result();
		try {
			UploadFileResult ufr = uploadInstence.uploadFile(zhaop);
			
			shouyInstance.insertGukxc(riq, ufr.getFileName(), guk_id);
			result.setSuccess(true); 
			result.setMessage("录入成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("对不起，录入错误！");
			logger.error(e);
		}
		return result;
	}
	
}
