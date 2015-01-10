package com.gez.grill.entity;

public class GukBasic {
	private String id;
	
	private String openid;

	private String xingm;

	private String zhucrq;

	private String mordz;

	private String mordh;

	private String weixh;
	
	private String weixm;
	
	private String dianh1;

	private String dianh2;

	private String diz1;

	private String diz2;

	private String diz3;

	private String diz4;

	private String diz5;

	private int zongdcfs;
	
	private int zongdccs;

	private float zongdcje;
    
    public String getWeixm() {
		return weixm;
	}

	public void setWeixm(String weixm) {
		this.weixm = weixm;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getXingm() {
		return xingm;
	}

	public void setXingm(String xingm) {
		this.xingm = xingm;
	}

	public String getZhucrq() {
		return zhucrq;
	}

	public void setZhucrq(String zhucrq) {
		this.zhucrq = zhucrq;
	}

	public String getWeixh() {
		return weixh;
	}

	public void setWeixh(String weixh) {
		this.weixh = weixh;
	}

	public int getZongdccs() {
		return zongdccs;
	}

	public void setZongdccs(int zongdccs) {
		this.zongdccs = zongdccs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMordz() {
		return mordz;
	}

	public void setMordz(String mordz) {
		this.mordz = mordz;
	}

	public String getMordh() {
		return mordh;
	}

	public void setMordh(String mordh) {
		this.mordh = mordh;
	}

	public String getDianh1() {
		return dianh1;
	}

	public void setDianh1(String dianh1) {
		this.dianh1 = dianh1;
	}

	public String getDianh2() {
		return dianh2;
	}

	public void setDianh2(String dianh2) {
		this.dianh2 = dianh2;
	}

	public String getDiz1() {
		return diz1;
	}

	public void setDiz1(String diz1) {
		this.diz1 = diz1;
	}

	public String getDiz2() {
		return diz2;
	}

	public void setDiz2(String diz2) {
		this.diz2 = diz2;
	}

	public String getDiz3() {
		return diz3;
	}

	public void setDiz3(String diz3) {
		this.diz3 = diz3;
	}

	public String getDiz4() {
		return diz4;
	}

	public void setDiz4(String diz4) {
		this.diz4 = diz4;
	}

	public String getDiz5() {
		return diz5;
	}

	public void setDiz5(String diz5) {
		this.diz5 = diz5;
	}

	public int getZongdcfs() {
		return zongdcfs;
	}

	public void setZongdcfs(int zongdcfs) {
		this.zongdcfs = zongdcfs;
	}

	public float getZongdcje() {
		return zongdcje;
	}

	public void setZongdcje(float zongdcje) {
		this.zongdcje = zongdcje;
	}

	public void setMarkAddress(int i, String diz) {
		switch (i) {
		case 0:
			setDiz1(diz);
			break;
		case 1:
			setDiz2(diz);
			break;
		case 2:
			setDiz3(diz);
			break;
		case 3:
			setDiz4(diz);
			break;
		case 4:
			setDiz5(diz);
			break;
		}
	}

	public void setMarkPhones(int i, String dianh) {
		switch (i) {
		case 0:
			setDianh1(dianh);
			break;
		case 1:
			setDianh2(dianh);
			break;
		}
	}
}