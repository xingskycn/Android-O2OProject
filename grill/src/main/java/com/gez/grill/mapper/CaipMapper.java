package com.gez.grill.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gez.grill.entity.CaipBasic;
import com.gez.grill.entity.Caipsc;
import com.gez.grill.entity.Caipxq;
import com.gez.grill.entity.Dingdmx;
import com.gez.grill.entity.Taocmx;

public interface CaipMapper {
	
	/*
	 * 套餐明细 2014-10-21
	 */
	ArrayList<Taocmx> getDishesDetailList(HashMap<String, Object> params);
	
	/*
	 * 菜品列表 2014-3-17
	 */
	ArrayList<CaipBasic> getDishesList(HashMap<String, Object> params);

	/*
	 * 特色菜品列表 2014-3-19
	 */

	ArrayList<CaipBasic> getSpecialDishesList(HashMap<String, Object> params);

	/*
	 * 菜品详情 2014-3-17
	 */
	Caipxq getDishesDetail(HashMap<String, String> params);

	/*
	 * 用户收藏的菜品2014-3-17
	 */
	ArrayList<CaipBasic> getMyFavoriteDishes(HashMap<String, Object> params);
	
	/*
	 * 获取菜品名称2014-7-9
	 */
	List<String> getDishesName(List<String> Ids);

	/*
	 * 查看菜品是否已被收藏2014-3-24
	 */
	int isDishesCollected(HashMap<String, String> params);

	/*
	 * 收藏菜品2014-3-24
	 */
	void collectDishes(Caipsc caipsc);

	/*
	 * 菜皮记录更新
	 */
	void updateDishData(Dingdmx caip);
}