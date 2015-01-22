package com.gez.cookery.jiaoshou.model;

public class Pingl extends JsonModel{
	private String id;

	private int zongdccs;

	private int zongdcfs;

	private float zongdcje;

	private boolean pingj;

//	private Date pinglsj;

	private String pinglnr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getZongdccs() {
		return zongdccs;
	}

	public void setZongdccs(int zongdccs) {
		this.zongdccs = zongdccs;
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

	public boolean isPingj() {
		return pingj;
	}

	public void setPingj(boolean pingj) {
		this.pingj = pingj;
	}

//	public Date getPinglsj() {
//		return pinglsj;
//	}
//
//	public void setPinglsj(Date pinglsj) {
//		this.pinglsj = pinglsj;
//	}

	public String getPinglnr() {
		return pinglnr;
	}

	public void setPinglnr(String pinglnr) {
		this.pinglnr = pinglnr;
	}

}