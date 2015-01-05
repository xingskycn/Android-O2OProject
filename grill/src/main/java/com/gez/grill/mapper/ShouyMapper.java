package com.gez.grill.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.gez.grill.entity.Guktz;
import com.gez.grill.entity.Gukxc;
import com.gez.grill.entity.ShouyBasic;
import com.gez.grill.entity.Taocmx;

public interface ShouyMapper {
	
	
	/*
	 * 首页基本信息 2014-9-29
	 */
	ArrayList<ShouyBasic> getShouyBasicList(String params);
	
	/*
	 * 套餐明细列表 2014-9-29
	 */
	
	ArrayList<Taocmx> gettaocmxList(String params);
	
	/*
	 * 顾客体重列表 2014-9-29
	 */
	
	ArrayList<Guktz> getguktzList(String params);
	
	/*
	 * 顾客相册列表 2014-9-29
	 */
	
	ArrayList<Gukxc> getGukxcList(String params);
	
	/*
	 * 获取顾客体重差值总人数 2014-9-29
	 */
	
	int getNumberOfGuktz(HashMap<String, String> params);
	
	/*
	 * 获取顾客体重击败总人数 2014-9-29
	 */
	
	int getBeatNumberOfGuktz(HashMap< String, String> params);
		
	/*
	 * 插入顾客体重 2014-9-29
	 */
	
	String getLastguktz(HashMap<String, String> params);
	
	/*
	 * 插入顾客体重 2014-9-29
	 */
	
	void insertGuktz(Guktz guktz);

	/*
	 * 插入顾客相册 2014-9-29
	 */
	
	void insertGukxc(Gukxc gukxc);
	

}