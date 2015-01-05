package com.gez.grill.mapper;

import java.util.HashMap;
import java.util.List;

import com.gez.grill.entity.Guk;
import com.gez.grill.entity.GukBasic;
import com.gez.grill.entity.Banb;

public interface GukMapper {
	/*
	 * 判断用户是否存在 2014-3-17
	 */
	int isCustomerExist(String gukId);

	/*
	 * 判断用户是否綁定 2014-4-1
	 */
	String isCustomerBind(String Id);

	/*
	 * 插入新用户 2014-3-17
	 */
	void newCustomerComming(Guk newCustomer);

	/*
	 * 查找用户 2014-3-17
	 */
	GukBasic getCustomerInfo(String gukId);
	
	/*
	 * 查询用户openId
	 */
	List<String> getCustomerOpenId(String cantId);

	/*
	 * 绑定用户 2014-4-1
	 */
	void bindAccount(GukBasic newAccount);

	/*
	 * 标记僵尸 2014-4-1
	 */
	void markZombie(String Id);

	/*
	 * 合并数据2014-4-2
	 */
	void mergerData(HashMap<String, String> params);

	/*
	 * 顾客记录更新
	 */
	void updateCustomerData(GukBasic info);

	/*
	 * 顾客反馈2014-4-9
	 */
	void feedback(HashMap<String, String> params);
	
	/*
	 * 版本2014-4-28
	 */
	Banb getVersion();
}