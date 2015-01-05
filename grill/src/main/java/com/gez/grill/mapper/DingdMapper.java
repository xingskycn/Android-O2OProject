package com.gez.grill.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.gez.grill.entity.CantBasic;
import com.gez.grill.entity.Dingd;
import com.gez.grill.entity.DingdBasic;
import com.gez.grill.entity.Dingdmx;

public interface DingdMapper {
	/*
	 * 订单列表2014-3-19
	 */
	ArrayList<DingdBasic> getOrdersList(HashMap<String, Object> params);

	/*
	 * 订单中的餐厅信息2014-3-14
	 */
	CantBasic getRestaurantBase(String dingdId);

	/*
	 * 订单信息2014-4-9
	 */
	Dingd getOrderBase(String dingdId);

	/*
	 * 订单信息2014-7-9
	 */
	Dingd getOrderBaseAgain(String dingdId);

	/*
	 * 查看用户是否绑定2014-4-28
	 */
	String isCustomerBind(String dingdId);

	/*
	 * 订单详情2014-3-19
	 */
	ArrayList<Dingdmx> getOrdersItems(String dingdId);

	/*
	 * 获取餐厅编号和当日订单量
	 */
	int getOrdersToday(String cantId);

	/*
	 * 订单明细
	 */
	void addOrdersItem(Dingdmx dingdmx);

	/*
	 * 订单
	 */
	void addOrder(Dingd dingd);

	/*
	 * 催单2014-3-24
	 */
	void getHurry(String dingdId);

	/*
	 * 增加订单主表的催单次数
	 */
	void hurryUp(String dingdId);

	/*
	 * 更新订单状态
	 */
	void changeOrderStatus(HashMap<String, String> params);
}