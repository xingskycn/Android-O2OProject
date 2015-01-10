package com.gez.grill.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gez.grill.entity.Guk;
import com.gez.grill.entity.GukBasic;
import com.gez.grill.entity.Banb;
import com.gez.grill.mapper.GukMapper;

@Service
public class GukService {

	@Autowired
	private GukMapper gukInstance;

	public int isCustomerExist(String gukId) {
		return gukInstance.isCustomerExist(gukId);
	}

	public GukBasic getCustomerInfo(String gukId) {
		return gukInstance.getCustomerInfo(gukId);
	}

	/*
	 * 数据库中没有当前gukid时
	 */
	@Transactional
	public void newCustomerComming(String gukId) {
		Guk newCommer = new Guk();
		// 给guk表以下7个字段赋初始值
		newCommer.setId(gukId);
		newCommer.setXingm("新用户");
		newCommer.setZhuangt("正常");
		newCommer.setZongdccs(0);
		newCommer.setZongdcfs(0);
		newCommer.setZongdcje(new BigDecimal("0"));
		newCommer.setZhuangt("未绑定");
		gukInstance.newCustomerComming(newCommer);
	}

	@Transactional
	public GukBasic bindAccount(String gukId, String weixh, String weixm, String dianh1,
			String dianh2, String diz1, String diz2, String diz3, String diz4,
			String diz5) {
		String weix_id = "";

		/* 获取当前用户的信息 */
		GukBasic customerInfo = gukInstance.getCustomerInfo(gukId);
		customerInfo.setDianh1(dianh1);
		customerInfo.setDianh2(dianh2);
		customerInfo.setDiz1(diz1);
		customerInfo.setDiz2(diz2);
		customerInfo.setDiz3(diz3);
		customerInfo.setDiz4(diz4);
		customerInfo.setDiz5(diz5);

		/* 搜索该微博或qq曾经是否被绑定过 */
		if (weixh != null && !weixh.equals("")) {
			weix_id = gukInstance.isCustomerBind(weixh);
		}

		/* 如果被绑定过，则合并数据 */
		if ((weix_id != null && !weix_id.equals(""))) {
			HashMap<String, String> paramsForMerger = new HashMap<String, String>();
			paramsForMerger.put("oldId", gukId);

			String newId = "";
			if (weix_id != null && !weix_id.equals("")) {
				newId = weix_id;
			}

			paramsForMerger.put("newId", newId);

			if (!newId.equals(gukId)) {
				gukInstance.mergerData(paramsForMerger);

				gukInstance.markZombie(gukId);

				ArrayList<String> address = new ArrayList<String>();
				ArrayList<String> phones = new ArrayList<String>();

				if (dianh1 != null && !dianh1.equals("")
						&& !phones.contains(dianh1)) {
					phones.add(dianh1);
				}
				if (dianh2 != null && !dianh2.equals("")
						&& !phones.contains(dianh2)) {
					phones.add(dianh2);
				}
				if (diz1 != null && !diz1.equals("") && !address.contains(diz1)) {
					address.add(diz1);
				}
				if (diz2 != null && !diz2.equals("") && !address.contains(diz2)) {
					address.add(diz2);
				}
				if (diz3 != null && !diz3.equals("") && !address.contains(diz3)) {
					address.add(diz3);
				}
				if (diz4 != null && !diz4.equals("") && !address.contains(diz4)) {
					address.add(diz4);
				}
				if (diz5 != null && !diz5.equals("") && !address.contains(diz5)) {
					address.add(diz5);
				}

				/* 如果之前有一些用户信息，也压入数组中来 */
				GukBasic usedInfo = gukInstance.getCustomerInfo(newId);
				String olddianh1 = usedInfo.getDianh1();
				String olddianh2 = usedInfo.getDianh2();
				String olddiz1 = usedInfo.getDiz1();
				String olddiz2 = usedInfo.getDiz2();
				String olddiz3 = usedInfo.getDiz3();
				String olddiz4 = usedInfo.getDiz4();
				String olddiz5 = usedInfo.getDiz5();

				if (olddianh1 != null && !olddianh1.equals("")
						&& !phones.contains(olddianh1)) {
					phones.add(olddianh1);
				}
				if (olddianh2 != null && !olddianh2.equals("")
						&& !phones.contains(olddianh2)) {
					phones.add(olddianh2);
				}
				if (olddiz1 != null && !olddiz1.equals("")
						&& !address.contains(olddiz1)) {
					address.add(olddiz1);
				}
				if (olddiz2 != null && !olddiz2.equals("")
						&& !address.contains(olddiz2)) {
					address.add(olddiz2);
				}
				if (olddiz3 != null && !olddiz3.equals("")
						&& !address.contains(olddiz3)) {
					address.add(olddiz3);
				}
				if (olddiz4 != null && !olddiz4.equals("")
						&& !address.contains(olddiz4)) {
					address.add(olddiz4);
				}
				if (olddiz5 != null && !olddiz5.equals("")
						&& !address.contains(olddiz5)) {
					address.add(olddiz5);
				}

				int ALength = address.size();
				if (ALength > 5) {
					ALength = 5;
				}
				for (int j = 0; j < ALength; j++) {
					customerInfo.setMarkAddress(j, address.get(j));
				}

				int PLength = phones.size();
				if (PLength > 2) {
					PLength = 2;
				}
				for (int k = 0; k < PLength; k++) {
					customerInfo.setMarkPhones(k, phones.get(k));
				}
			}

			customerInfo.setId(newId);
		}
		
		/* 如果没有，更新他的微博或qq */
		if (weixm != null && !weixm.equals("")) {
			customerInfo.setWeixm(weixm);
		}
		if (weixh != null && !weixh.equals("")) {
			customerInfo.setWeixh(weixh);
		}

		/* 修改默认电话 */
		if (customerInfo.getDianh1() != null
				&& !customerInfo.getDianh1().equals("")) {
			customerInfo.setMordh(customerInfo.getDianh1());
		}
		/* 修改默认地址 */
		if (customerInfo.getDiz1() != null
				&& !customerInfo.getDiz1().equals("")) {
			customerInfo.setMordz(customerInfo.getDiz1());
		}

		gukInstance.bindAccount(customerInfo);

		return customerInfo;
	}

	@Transactional
	public void mergerData(HashMap<String, String> params) {
		gukInstance.mergerData(params);
	}

	@Transactional
	public void markZombie(String Id) {
		gukInstance.markZombie(Id);
	}

	@Transactional
	public void updateCustomerData(GukBasic info) {
		gukInstance.updateCustomerData(info);
	}

	@Transactional
	public void feedback(String gukId, String gukfk) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("gukId", gukId);
		params.put("gukfk", gukfk);
		gukInstance.feedback(params);
	}

	public Banb getVersion() {
		return gukInstance.getVersion();
	}

	public List<String> getCustomerOpenId(String cantId) {
		return gukInstance.getCustomerOpenId(cantId);
	}
}
