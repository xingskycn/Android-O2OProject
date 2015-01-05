package com.gez.grill.mapper;

import com.gez.grill.entity.ViewsAndVisits;

public interface LiulfwMapper {
	/*
	 * 餐厅浏览量
	 */
	void updateRestaurantView(ViewsAndVisits record);

	/*
	 * 餐厅访问量
	 */
	void updateRestaurantVisit(ViewsAndVisits record);

	/*
	 * 讲hashmpa中的数据写入数据库
	 */
	void updateDishView(ViewsAndVisits record);

	/*
	 * 讲hashmpa中的数据写入数据库
	 */
	void updateDishVisit(ViewsAndVisits record);
}