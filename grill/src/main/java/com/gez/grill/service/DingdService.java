package com.gez.grill.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gez.grill.entity.Caippl;
import com.gez.grill.entity.CantBasic;
import com.gez.grill.entity.Dingd;
import com.gez.grill.entity.DingdBasic;
import com.gez.grill.entity.Dingddp;
import com.gez.grill.entity.Dingdmx;
import com.gez.grill.entity.Cantpl;
import com.gez.grill.entity.GukBasic;
import com.gez.grill.entity.PagenateArgs;
import com.gez.grill.entity.Pingltj;
import com.gez.grill.mapper.CaipMapper;
import com.gez.grill.mapper.CaipplMapper;
import com.gez.grill.mapper.CantMapper;
import com.gez.grill.mapper.DingdMapper;
import com.gez.grill.mapper.CantplMapper;
import com.gez.grill.util.FunctionData;
import com.gez.grill.util.FunctionSerializerJson;

@Service
public class DingdService {

	@Autowired
	private DingdMapper dingdInstance;

	@Autowired
	private GukService gukInstance;

	@Autowired
	private CantMapper cantInstance;

	@Autowired
	private CantplMapper cantplInstance;

	@Autowired
	private CaipMapper caipInstance;

	@Autowired
	private CaipplMapper caipplInstance;

	@Autowired
	private TuisService tuisInstance;

	public ArrayList<DingdBasic> getOrdersList(int pageIndex, int pageSize, String gukId) {
		PagenateArgs pager = new PagenateArgs(pageIndex, pageSize);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pageStart", pager.getPageStart());
		params.put("pageEnd", pager.getPageEnd());
		params.put("gukId", gukId);

		return dingdInstance.getOrdersList(params);
	}

	public Dingddp getOrdersCommentList(String dingdId) {
		Dingd dingdxx = dingdInstance.getOrderBase(dingdId);
		CantBasic cantxx = dingdInstance.getRestaurantBase(dingdId);
		ArrayList<Dingdmx> orderItems = dingdInstance.getOrdersItems(dingdId);

		Dingddp orderCommentList = new Dingddp();
		orderCommentList.setCantId(cantxx.getId());
		orderCommentList.setCantbstp(cantxx.getCantbstp());
		orderCommentList.setCantMc(cantxx.getCantmc());
		orderCommentList.setGukdh(dingdxx.getGukdh());
		orderCommentList.setGukdz(dingdxx.getGukscdz());
		orderCommentList.setDingdze(dingdxx.getZongjg());
		orderCommentList.setCaipxx(orderItems);

		return orderCommentList;
	}

	public String getSerial(String cantId) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		int total = dingdInstance.getOrdersToday(cantId) + 1;

		String serial = cantInstance.getRestaurantSerial(cantId);
		serial += formater.format(new Date());
		serial += String.format("%04d", total);

		return serial;
	}

	public GukBasic modifyCustomers(String gukId, String gukdh, String gukscdz,
			int zongfs, float feiy, boolean duojf, boolean shaojf,
			boolean duofl, boolean shaofl, boolean bufc, boolean bufs,
			boolean bufj, boolean buykz) {
		GukBasic customer = gukInstance.getCustomerInfo(gukId);

		String mordz = customer.getMordz();
		String mordh = customer.getMordh();
		String dianh1 = customer.getDianh1();
		String dianh2 = customer.getDianh2();
		String diz1 = customer.getDiz1();
		String diz2 = customer.getDiz2();
		String diz3 = customer.getDiz3();
		String diz4 = customer.getDiz4();
		String diz5 = customer.getDiz5();

		if (mordz != gukscdz) {
			mordz = gukscdz;
		}
		if (mordh != gukdh) {
			mordh = gukdh;
		}

		customer.setId(gukId);
		customer.setMordh(mordh);
		customer.setMordz(mordz);
		customer.setZongdcfs(zongfs);
		customer.setZongdcje(feiy);
		customer.setDuojf(duojf);
		customer.setShaojf(shaojf);
		customer.setDuofl(duofl);
		customer.setShaofl(shaofl);
		customer.setBufc(bufc);
		customer.setBufs(bufs);
		customer.setBufj(bufj);
		customer.setBuykz(buykz);

		ArrayList<String> address = new ArrayList<String>();
		if (diz1 != null && !diz1.equals("") && !diz1.equals(gukscdz)) {
			address.add(diz1);
		}
		if (diz2 != null && !diz2.equals("") && !diz2.equals(gukscdz)) {
			address.add(diz2);
		}
		if (diz3 != null && !diz3.equals("") && !diz3.equals(gukscdz)) {
			address.add(diz3);
		}
		if (diz4 != null && !diz4.equals("") && !diz4.equals(gukscdz)) {
			address.add(diz4);
		}
		if (diz5 != null && !diz5.equals("") && !diz5.equals(gukscdz)) {
			address.add(diz5);
		}
		address.add(0, gukscdz);

		int ALength = address.size();
		if (ALength > 5) {
			ALength = 5;
		}
		for (int j = 0; j < ALength; j++) {
			customer.setMarkAddress(j, address.get(j));
		}

		ArrayList<String> phones = new ArrayList<String>();
		if (dianh1 != null && !dianh1.equals("") && !dianh1.equals(gukdh)) {
			phones.add(dianh1);
		}
		if (dianh2 != null && !dianh2.equals("") && !dianh2.equals(gukdh)) {
			phones.add(dianh2);
		}
		phones.add(0, gukdh);

		int PLength = phones.size();
		if (PLength > 2) {
			PLength = 2;
		}
		for (int k = 0; k < PLength; k++) {
			customer.setMarkPhones(k, phones.get(k));
		}

		return customer;
	}

	@Transactional
	public boolean submitOrders(String gukdh, String gukscdz, String gukbz,
			String songcsjxz, String songcsj, String peisf, String zhiffs,
			boolean duojf, boolean shaojf, boolean duofl, boolean shaofl,
			boolean bufc, boolean bufs, boolean bufj, boolean buykz,
			String gukId, String cantId, String dingdmx) {
		String cantzt = cantInstance.getRestaurantStatus(cantId);
		if (cantzt.equals("4") || cantzt.equals("5")) {
			return false;
		}

		ArrayList<Dingdmx> dingdmxtj = FunctionSerializerJson.deserialize(dingdmx, new TypeReference<ArrayList<Dingdmx>>() {});

		String dingdId = UUID.randomUUID().toString();
		Date yonghxzsj = null;
		float zongj = 0;
		float feiy = 0;
		int zongfs = 0;

		/* 送餐时间类型选择 1位立即送达 2为指定时间 */
		if (songcsjxz.equals("1")) {
			yonghxzsj = new Date();
		} else if (songcsjxz.equals("2") && (!songcsj.equals(null) || !songcsj.equals(""))) {
			yonghxzsj = FunctionData.parseDate(songcsj);
		}

		/* 构造订单类 */
		Dingd dingd = new Dingd();
		dingd.setId(dingdId);
		dingd.setBianh(getSerial(cantId));
		dingd.setDingdzt("1");
		dingd.setDianpzt("1");
		dingd.setGukdh(gukdh);
		dingd.setGukscdz(gukscdz);
		dingd.setGukbz(gukbz);
		dingd.setCantbz("无");
		dingd.setSongcsjxz(songcsjxz);
		dingd.setSongcsj(yonghxzsj);
		dingd.setZhiffs(zhiffs);
		dingd.setCuidcs(0);
		dingd.setDuojf(duojf);
		dingd.setShaojf(shaojf);
		dingd.setDuofl(duofl);
		dingd.setShaofl(shaofl);
		dingd.setBufc(bufc);
		dingd.setBufs(bufs);
		dingd.setBufj(bufj);
		dingd.setBuykz(buykz);
		dingd.setGukId(gukId);
		dingd.setCantId(cantId);

		/* 构造订单内容类 */
		int tiCount = dingdmxtj.size();
		for (int i = 0; i < tiCount; i++) {
			Dingdmx mxtj = dingdmxtj.get(i);

			if (mxtj.getFens() > 0) {
				zongj = mxtj.getDanj() * mxtj.getFens();

				mxtj.setDingdId(dingdId);
				mxtj.setCantId(dingd.getCantId());
				mxtj.setZongj(zongj);

				/* 总价格，总份数增加 */
				zongfs = zongfs + mxtj.getFens();
				feiy = feiy + zongj;
			}
		}

		feiy = feiy + Float.parseFloat(peisf);

		/* 顾客数据更新 */
		GukBasic customerData = modifyCustomers(gukId, gukdh, gukscdz, zongfs, feiy, duojf, shaojf, duofl, shaofl, bufc, bufs, bufj, buykz);

		/* 餐厅数据更新 */
		HashMap<String, Object> restaurantData = new HashMap<String, Object>();
		restaurantData.put("cantId", cantId);
		restaurantData.put("dianczgs", zongfs);

		/* 完善主订单内容 */
		dingd.setZongfs(zongfs);
		dingd.setZongjg(new BigDecimal(feiy));

		dingdInstance.addOrder(dingd);

		/* 插入订单明细 */
		for (int j = 0; j < tiCount; j++) {
			dingdInstance.addOrdersItem(dingdmxtj.get(j));
			caipInstance.updateDishData(dingdmxtj.get(j));
		}

		cantInstance.updateRestaurantData(restaurantData);
		gukInstance.updateCustomerData(customerData);

		tuisInstance.push2BusinessForWeix(gukdh, gukscdz, cantId, dingdmxtj);
		return true;
	}

	@Transactional
	public void addCommentsForOrder(String dingdId, String gukId, String pingl) {
		Pingltj suoypl = FunctionSerializerJson.deserialize(pingl, new TypeReference<Pingltj>() {});

		Cantpl shangjpl = suoypl.getCantpl();
		shangjpl.setGukId(gukId);
		cantplInstance.addCommentsForRestaurant(shangjpl);

		String cantId = shangjpl.getCantId();
		if (shangjpl.getPingj()) {
			cantplInstance.addGoodComments(cantId);
		} else {
			cantplInstance.addBadComments(cantId);
		}

		ArrayList<Caippl> caippList = suoypl.getCaippl();
		int pinglsl = caippList.size();
		for (int i = 0; i < pinglsl; i++) {
			Caippl caippl = caippList.get(i);
			caippl.setCantId(cantId);
			caippl.setGukId(gukId);

			caipplInstance.addCommentsForDishes(caippl);

			String caipId = caippl.getCaipId();
			if (caippl.getPingj()) {
				caipplInstance.addGoodComments(caipId);
			} else {
				caipplInstance.addBadComments(caipId);
			}
		}

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("dingdId", dingdId);
		params.put("dianpzt", "2");

		dingdInstance.changeOrderStatus(params);
	}

	@Transactional
	public void getHurry(String dingdId) {
		dingdInstance.getHurry(dingdId);
		dingdInstance.hurryUp(dingdId);
		
		tuisInstance.push2BusinessForHurry(dingdId);
	}
}