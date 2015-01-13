package com.gez.grill.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gez.grill.entity.CaipBasic;
import com.gez.grill.entity.Caipsc;
import com.gez.grill.entity.Caipxq;
import com.gez.grill.entity.PagenateArgs;
import com.gez.grill.entity.Pingl;
import com.gez.grill.entity.Taocmx;
import com.gez.grill.mapper.CaipMapper;
import com.gez.grill.mapper.CaipplMapper;

@Service
public class CaipService {

	@Autowired
	private CaipMapper caipInstance;
	
	@Autowired
	private CaipplMapper caipplInstance;

	public ArrayList<CaipBasic> getDishesList(int pageIndex, int pageSize, String cantId) {
		PagenateArgs pager = new PagenateArgs(pageIndex, pageSize);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageStart", pager.getPageStart());
		params.put("pageEnd", pager.getPageEnd());
		params.put("cantId", cantId);

		return caipInstance.getDishesList(params);
	}
	
	/*
	 * 获取套餐明细
	 * 2014-10-21
	 */
	public ArrayList<Taocmx> getDishesDetailList(String caip_id) {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("caip_id", caip_id);

		return caipInstance.getDishesDetailList(params);
	}

	public ArrayList<CaipBasic> getSpecialDishesList(int pageIndex, int pageSize, String cantId) {
		PagenateArgs pager = new PagenateArgs(pageIndex, pageSize);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageStart", pager.getPageStart());
		params.put("pageEnd", pager.getPageEnd());
		params.put("cantId", cantId);

		return caipInstance.getSpecialDishesList(params);
	}
	/*
	 * 获取菜品详情
	 * 2014-10-21
	 */
	public Caipxq getDishesDetail(String caipId, String gukId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("caipId", caipId);
		params.put("gukId", gukId);

		return caipInstance.getDishesDetail(params);
	}

	public ArrayList<CaipBasic> getMyFavoriteDishes(int pageIndex, int pageSize, String cantId, String gukId) {
		PagenateArgs pager = new PagenateArgs(pageIndex, pageSize);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageStart", pager.getPageStart());
		params.put("pageEnd", pager.getPageEnd());
		params.put("cantId", cantId);
		params.put("gukId", gukId);

		return caipInstance.getMyFavoriteDishes(params);
	}

	public int isDishesCollected(String caipId, String gukId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("caipId", caipId);
		params.put("gukId", gukId);

		return caipInstance.isDishesCollected(params);
	}

	@Transactional
	public void collectDishes(String caipId, String gukId) {
		Caipsc caipsc = new Caipsc();
		caipsc.setCaipId(caipId);
		caipsc.setGukId(gukId);

		caipInstance.collectDishes(caipsc);
	}

	public ArrayList<Pingl> getCommentForDishes(int pageIndex, int pageSize, String caipId) {
		PagenateArgs pager = new PagenateArgs(pageIndex, pageSize);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("caipId", caipId);
		params.put("pageStart", pager.getPageStart());
		params.put("pageEnd", pager.getPageEnd());

		return caipplInstance.getCommentForDishes(params);
	}
}
