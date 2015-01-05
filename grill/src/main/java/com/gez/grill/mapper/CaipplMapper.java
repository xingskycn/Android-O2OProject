package com.gez.grill.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.gez.grill.entity.Caippl;
import com.gez.grill.entity.Pingl;

public interface CaipplMapper {
	/*
	 * 菜品评论2014-3-18
	 */
	ArrayList<Pingl> getCommentForDishes(HashMap<String, Object> params);

	/*
	 * 添加餐厅评论2014-3-24
	 */
	void addCommentsForDishes(Caippl caippl);

	/*
	 * 好评增加2014-3-28
	 */
	void addGoodComments(String caipId);

	/*
	 * 好评增加2014-3-28
	 */
	void addBadComments(String caipId);
}