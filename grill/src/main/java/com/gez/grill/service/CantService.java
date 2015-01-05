package com.gez.grill.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gez.grill.entity.CantBasic;
import com.gez.grill.entity.Cantsc;
import com.gez.grill.entity.Cantxq;
import com.gez.grill.entity.PagenateArgs;
import com.gez.grill.entity.Pingl;
import com.gez.grill.mapper.CantMapper;
import com.gez.grill.mapper.CantplMapper;

@Service
public class CantService {

	@Autowired
	private CantMapper cantInstance;
	
	@Autowired
	private CantplMapper cantplInstance;

	public ArrayList<CantBasic> getRestaurantList(int pageIndex, int pageSize, float jingd, float weid) {
		PagenateArgs pager = new PagenateArgs(pageIndex, pageSize);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageStart", pager.getPageStart());
		params.put("pageEnd", pager.getPageEnd());
		params.put("jingd", jingd);
		params.put("weid", weid);

		return cantInstance.getRestaurantList(params);
	}

	public ArrayList<CantBasic> getMyFavoriteRestaurant(int pageIndex, int pageSize, String gukId, float jingd, float weid) {
		PagenateArgs pager = new PagenateArgs(pageIndex, pageSize);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageStart", pager.getPageStart());
		params.put("pageEnd", pager.getPageEnd());
		params.put("gukId", gukId);
		params.put("jingd", jingd);
		params.put("weid", weid);

		return cantInstance.getMyFavoriteRestaurant(params);
	}

	public Cantxq getRestaurantDetail(String cantId, String gukId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cantId", cantId);
		params.put("gukId", gukId);

		return cantInstance.getRestaurantDetail(params);
	}
	
	public Cantxq getRestaurantDetailForSingel(String cantId, String gukId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cantId", cantId);
		params.put("gukId", gukId);

		return cantInstance.getRestaurantDetailForSingel(params);
	}

	public int isRestaurantCollected(String cantId, String gukId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cantId", cantId);
		params.put("gukId", gukId);

		return cantInstance.isRestaurantCollected(params);
	}

	@Transactional
	public void collectRestaurant(String cantId, String gukId) {
		Cantsc cantsc = new Cantsc();
		cantsc.setCantId(cantId);
		cantsc.setGukId(gukId);

		cantInstance.collectRestaurant(cantsc);
	}

	public ArrayList<Pingl> getCommentForRestaurant(int pageIndex, int pageSize, String cantId) {
		PagenateArgs pager = new PagenateArgs(pageIndex, pageSize);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageStart", pager.getPageStart());
		params.put("pageEnd", pager.getPageEnd());
		params.put("cantId", cantId);

		return cantplInstance.getCommentForRestaurant(params);
	}
}
