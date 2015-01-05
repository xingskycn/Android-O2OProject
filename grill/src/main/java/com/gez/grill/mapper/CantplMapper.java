package com.gez.grill.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.gez.grill.entity.Pingl;
import com.gez.grill.entity.Cantpl;

public interface CantplMapper {
	/*
	 * 餐厅评论2014-3-18
	 */
	ArrayList<Pingl> getCommentForRestaurant(HashMap<String, Object> params);

	/*
	 * 添加餐厅评论2014-3-24
	 */
	void addCommentsForRestaurant(Cantpl shangjpl);

	/*
	 * 好评增加2014-3-28
	 */
	void addGoodComments(String cantId);

	/*
	 * 好评增加2014-3-28
	 */
	void addBadComments(String cantId);
}