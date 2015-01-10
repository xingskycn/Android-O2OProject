package com.gez.grill.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gez.grill.entity.Guktz;
import com.gez.grill.entity.Gukxc;
import com.gez.grill.entity.ShouyBasic;
import com.gez.grill.entity.Taocmx;
import com.gez.grill.mapper.ShouyMapper;

@Service
public class ShouyService {

	@Autowired
	private ShouyMapper shouyInstance;

	/*
	 * 订单列表
	 */
	public ShouyBasic getShouyBasicList(String guk_id) {
		return shouyInstance.getShouyBasicList(guk_id);

	}

	/*
	 * 首页套餐明细
	 */
	public ArrayList<Taocmx> gettaocmxList(String guk_id) {
		return shouyInstance.gettaocmxList(guk_id);

	}

	/*
	 * 首页顾客体重
	 */
	public ArrayList<Guktz> getguktzList(String guk_id) {
		return shouyInstance.getguktzList(guk_id);

	}

	/*
	 * 首页顾客相册
	 */
	public ArrayList<Gukxc> getGukxcList(String guk_id) {
		return shouyInstance.getGukxcList(guk_id);

	}

	/*
	 * 首页插入顾客体重
	 */
	@Transactional
	public void insertGuktz(String riq, String zhuangt, float tiz, String guk_id) {
		Guktz guktz = new Guktz();
		String newid = UUID.randomUUID().toString();
		
		//上次顾客体重
		HashMap<String, String> Lastguktz = new HashMap<String, String>();
		
		//体重差值总人数
		HashMap<String, String> guktzczzrs = new HashMap<String, String>();
		
		//体重差值击败人数
		HashMap<String, String> guktzczjbrs = new HashMap<String, String>();
		
		Lastguktz.put("riq", riq);
		Lastguktz.put("guk_id", guk_id);		
		String lastWeight = shouyInstance.getLastguktz(Lastguktz);
    
		//判断上次体重是否存在
		float guktzcz = 0;
		if(lastWeight != null){
			guktzcz=tiz-Float.parseFloat(lastWeight);
			
		}
				
		guktzczzrs.put("riq", riq);
		guktzczzrs.put("guk_id", guk_id);
		guktzczjbrs.put("riq", riq);
		guktzczjbrs.put("chaz", String.valueOf(guktzcz));
		guktzczjbrs.put("guk_id", guk_id);
		
		float  zongrs = shouyInstance.getNumberOfGuktz(guktzczzrs);
		float  jibrs = shouyInstance.getBeatNumberOfGuktz(guktzczjbrs);
		
		//判断总人数是否为0
		if(zongrs==0){
			zongrs=1;
			jibrs=1;
		}
		
		float baifb=jibrs/zongrs;
		
		guktz.setId(newid);
		guktz.setRiq(riq);
		guktz.setZhuangt(zhuangt);
		guktz.setTiz(tiz);
		guktz.setChaz(guktzcz);
		guktz.setBaifb(baifb);
		guktz.setGuk_id(guk_id);
		shouyInstance.insertGuktz(guktz);
			
	}

	/*
	 * 首页插入顾客相册
	 */
	@Transactional
	public void insertGukxc(String riq, String zhaop, String guk_id) {
		Gukxc gukxc = new Gukxc();
		String newid = UUID.randomUUID().toString();

		gukxc.setId(newid);
		gukxc.setRiq(riq);
		gukxc.setZhaop(zhaop);
		gukxc.setGuk_id(guk_id);
		shouyInstance.insertGukxc(gukxc);
	}

}
