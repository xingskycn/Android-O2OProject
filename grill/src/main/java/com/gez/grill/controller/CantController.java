package com.gez.grill.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gez.grill.entity.CantBasic;
import com.gez.grill.entity.Cantxq;
import com.gez.grill.entity.Pingl;
import com.gez.grill.entity.Result;
import com.gez.grill.service.CantService;

/**
 * 餐厅控制器类
 * 
 * @author Zhaowei
 * 
 */
@Controller
@RequestMapping(value = "/cant")
public class CantController {

	private final static Logger logger = Logger.getLogger(CantController.class);

	@Autowired
	private CantService cantInstance;

	@Autowired
	private CacheViewsAndVisits vavInstance;

	/**
	 * 餐厅列表2014-3-14
	 */
	@RequestMapping(value = "/getRestaurantList", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<CantBasic> getRestaurantList(
			@RequestParam(value = "pageIndex") int pageIndex,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "jingd") float jingd,
			@RequestParam(value = "weid") float weid) {
		ArrayList<CantBasic> cantList = new ArrayList<CantBasic>();
		try {
			cantList = cantInstance.getRestaurantList(pageIndex, pageSize, jingd, weid);

			vavInstance.addRestaurantView(cantList);
		} catch (Exception e) {
			logger.error(e);
		}

		return cantList;
	}

	/**
	 * 顾客收藏的餐厅2014-3-18
	 */
	@RequestMapping(value = "/getMyFavoriteRestaurant", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<CantBasic> getMyFavoriteDishes(
			@RequestParam(value = "pageIndex") int pageIndex,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "gukId") String gukId,
			@RequestParam(value = "jingd") float jingd,
			@RequestParam(value = "weid") float weid) {
		ArrayList<CantBasic> favoriteList = new ArrayList<CantBasic>();
		try {
			favoriteList = cantInstance.getMyFavoriteRestaurant(pageIndex, pageSize, gukId, jingd, weid);

			vavInstance.addRestaurantView(favoriteList);
		} catch (Exception e) {
			logger.error(e);
		}
		return favoriteList;
	}

	/**
	 * 餐厅详情2014-3-18
	 */
	@RequestMapping(value = "/getRestaurantDetail", method = RequestMethod.GET)
	@ResponseBody
	public Cantxq getRestaurantDetail(
			@RequestParam(value = "cantId") String cantId,
			@RequestParam(value = "gukId") String gukId) {
		Cantxq restaurantDetail = new Cantxq();
		try {
			restaurantDetail = cantInstance.getRestaurantDetail(cantId, gukId);

			vavInstance.addRestaurantVisit(cantId);
		} catch (Exception e) {
			logger.error(e);
		}
		return restaurantDetail;
	}
	
	/**
	 * 餐厅详情但餐厅版本2014-4-28
	 */
	@RequestMapping(value = "/getRestaurantDetailForSingel", method = RequestMethod.GET)
	@ResponseBody
	public Cantxq getRestaurantDetailForSingel(
			@RequestParam(value = "cantId") String cantId,
			@RequestParam(value = "gukId") String gukId) {
		Cantxq restaurantDetail = new Cantxq();
		try {
			restaurantDetail = cantInstance.getRestaurantDetailForSingel(cantId, gukId);

			vavInstance.addRestaurantVisit(cantId);
		} catch (Exception e) {
			logger.error(e);
		}
		return restaurantDetail;
	}

	/**
	 * 菜厅评价2014-3-18
	 */
	@RequestMapping(value = "/getCommentForRestaurant", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Pingl> getCommentForRestaurant(
			@RequestParam(value = "pageIndex") int pageIndex,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "cantId") String cantId) {
		ArrayList<Pingl> cantplList = new ArrayList<Pingl>();
		try {
			cantplList = cantInstance.getCommentForRestaurant(pageIndex, pageSize, cantId);
		} catch (Exception e) {
			logger.error(e);
		}
		return cantplList;
	}

	/**
	 * 收藏餐厅2014-3-24
	 */
	@RequestMapping(value = "/collectRestaurant", method = RequestMethod.POST)
	@ResponseBody
	public Result collectDishes(
			@RequestParam(value = "cantId") String cantId,
			@RequestParam(value = "gukId") String gukId) {
		Result kyResult = new Result();
		try {
			if (cantInstance.isRestaurantCollected(cantId, gukId) > 0) {
				kyResult.setSuccess(false);
				kyResult.setMessage("已经收藏了这个餐厅 \\(￣∀￣)");
			} else {
				cantInstance.collectRestaurant(cantId, gukId);

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