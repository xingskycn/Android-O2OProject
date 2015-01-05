package com.gez.grill.mapper;

import com.gez.grill.entity.Yanz;

public interface TuisMapper {
	/*
	 * 获取验证信息
	 */
	Yanz getToken(String leix);
	
	/*
	 * 更新验证信息
	 */
	void updateToken(Yanz yanz);
}