package com.gez.grill.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.gez.grill.entity.CantBasic;
import com.gez.grill.entity.Cantsc;
import com.gez.grill.entity.Cantxq;

public interface CantMapper {
	/*
	 * 餐厅列表 2014-3-14
	 */
	ArrayList<CantBasic> getRestaurantList(HashMap<String, Object> params);

	/*
	 * 收藏的餐厅列表2014-3-17
	 */
	ArrayList<CantBasic> getMyFavoriteRestaurant(HashMap<String, Object> params);

	/*
	 * 餐厅详情2014-3-18
	 */
	Cantxq getRestaurantDetail(HashMap<String, String> params);
	
	/*
	 * 餐厅详情单餐厅版本2014-3-18
	 */
	Cantxq getRestaurantDetailForSingel(HashMap<String, String> params);

	/*
	 * 获取餐厅编号和当日订单量
	 */
	String getRestaurantSerial(String cantId);
	
	/*
	 * 获取餐厅当前状态
	 */
	String getRestaurantStatus(String cantId);

	/*
	 * 顾客是否收藏了餐厅2014-3-24
	 */
	int isRestaurantCollected(HashMap<String, String> params);

	/*
	 * 收藏餐厅2014-3-24
	 */
	void collectRestaurant(Cantsc cantsc);

	/*
	 * 餐厅记录更新
	 */
	void updateRestaurantData(HashMap<String, Object> params);
}