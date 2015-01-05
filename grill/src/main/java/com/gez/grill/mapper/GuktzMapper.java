package com.gez.grill.mapper;

import java.util.ArrayList;
import com.gez.grill.entity.Guktz;

public interface GuktzMapper {
	/*
	 * 顾客体重列表 2014-9-28
	 */
	ArrayList<Guktz> getGuktzList(String params);
	
	/*
	 * 插入顾客体重列表 2014-9-28
	 */
	void insertGuktz(Guktz guktz);
}