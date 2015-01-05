package com.gez.grill.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gez.grill.entity.CaipBasic;
import com.gez.grill.entity.Caipxq;
import com.gez.grill.entity.Pingl;
import com.gez.grill.entity.Result;
import com.gez.grill.entity.Taocmx;
import com.gez.grill.service.CaipService;

/**
 * 菜品控制器类
 * 
 * @author Zhaowei
 * 
 */
@Controller
@RequestMapping(value = "/caip")
public class CaipController {

	private final static Logger logger = Logger.getLogger(CaipController.class);

	@Autowired
	private CaipService caipInstance;

	@Autowired
	private CacheViewsAndVisits vavInstance;

	/**
	 * 菜品列表2014-3-17
	 */
	@RequestMapping(value = "/getDishesList", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<CaipBasic> getDishesList(
			@RequestParam(value = "pageIndex") int pageIndex,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "cantId") String cantId) {
		ArrayList<CaipBasic> caipList = new ArrayList<CaipBasic>();
		try {
			caipList = caipInstance.getDishesList(pageIndex, pageSize, cantId);

			vavInstance.addDishView(caipList);
		} catch (Exception e) {
			logger.error(e);
		}
		return caipList;
	}
	
	/**
	 * 菜品套餐明细2014-10-21
	 */
	@RequestMapping(value = "/getDishesDetailList", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Taocmx> getDishesDetailList(
			@RequestParam(value = "caip_id") String caip_id) {
		ArrayList<Taocmx> taocmxList = new ArrayList<Taocmx>();
		try {
			taocmxList = caipInstance.getDishesDetailList(caip_id);
		} catch (Exception e) {
			logger.error(e);
		}
		return taocmxList;
	}

	/**
	 * 获取特色菜品列表2014-3-17
	 */
	@RequestMapping(value = "/getSpecialDishesList", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<CaipBasic> getSpecialDishesList(
			@RequestParam(value = "pageIndex") int pageIndex,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "cantId") String cantId) {
		ArrayList<CaipBasic> caipList = new ArrayList<CaipBasic>();
		try {
			caipList = caipInstance.getSpecialDishesList(pageIndex, pageSize, cantId);

			vavInstance.addDishView(caipList);
		} catch (Exception e) {
			logger.error(e);
		}
		return caipList;
	}

	/**
	 * 菜品详情2014-3-17
	 */
	@RequestMapping(value = "/getDishesDetail", method = RequestMethod.GET)
	@ResponseBody
	public Caipxq getDishesDetail(
			@RequestParam(value = "caipId") String caipId,
			@RequestParam(value = "gukId") String gukId) {
		Caipxq caipDetail = new Caipxq();
		try {
			caipDetail = caipInstance.getDishesDetail(caipId, gukId);

			vavInstance.addDishVisit(caipId);
		} catch (Exception e) {
			logger.error(e);
		}
		return caipDetail;
	}

	/**
	 * 菜品评价2014-3-17
	 */
	@RequestMapping(value = "/getCommentForDishes", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Pingl> getCommentForDishes(
			@RequestParam(value = "pageIndex") int pageIndex,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "caipId") String caipId) {
		ArrayList<Pingl> caipplList = new ArrayList<Pingl>();
		try {
			caipplList = caipInstance.getCommentForDishes(pageIndex, pageSize, caipId);
		} catch (Exception e) {
			logger.error(e);
		}
		return caipplList;
	}

	/**
	 * 顾客收藏的菜品2014-3-18
	 */
	@RequestMapping(value = "/getMyFavoriteDishes", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<CaipBasic> getMyFavoriteDishes(
			@RequestParam(value = "pageIndex") int pageIndex,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "cantId") String cantId,
			@RequestParam(value = "gukId") String gukId) {
		ArrayList<CaipBasic> favoriteList = new ArrayList<CaipBasic>();
		try {
			favoriteList = caipInstance.getMyFavoriteDishes(pageIndex, pageSize, cantId, gukId);

			vavInstance.addDishView(favoriteList);
		} catch (Exception e) {
			logger.error(e);
		}
		return favoriteList;
	}

	/**
	 * 收藏菜品2014-3-24
	 */
	@RequestMapping(value = "/collectDishes", method = RequestMethod.POST)
	@ResponseBody
	public Result collectDishes(
			@RequestParam(value = "caipId") String caipId,
			@RequestParam(value = "gukId") String gukId) {
		Result kyResult = new Result();
		try {
			if (caipInstance.isDishesCollected(caipId, gukId) > 0) {
				kyResult.setSuccess(false);
				kyResult.setMessage("已经收藏了这个菜品 \\(￣∀￣)");
			} else {
				caipInstance.collectDishes(caipId, gukId);

				kyResult.setSuccess(true);
				kyResult.setMessage("");
			}
		} catch (Exception e) {
			logger.error(e);

			kyResult.setSuccess(false);
			kyResult.setMessage("收藏失败了 :;(∩´﹏`∩);:");
		}
		return kyResult;
	}
}
