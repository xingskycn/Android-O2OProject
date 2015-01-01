package com.gez.cookery.jiaoshou.model;

import java.util.Date;

public class Banb extends JsonModel {
	private String banbmc;

	private Date gengxrq;
	
	private String gengxzt;

	private String xiazdz;
	
	private String gengxnr;

	public String getBanbmc() {
		return banbmc;
	}

	public void setBanbmc(String banbmc) {
		this.banbmc = banbmc;
	}

	public Date getGengxrq() {
		return gengxrq;
	}

	public void setGengxrq(Date gengxrq) {
		this.gengxrq = gengxrq;
	}

	public String getGengxzt() {
		return gengxzt;
	}

	public void setGengxzt(String gengxzt) {
		this.gengxzt = gengxzt;
	}

	public String getXiazdz() {
		return xiazdz;
	}

	public void setXiazdz(String xiazdz) {
		this.xiazdz = xiazdz;
	}

	public String getGengxnr() {
		return gengxnr;
	}

	public void setGengxnr(String gengxnr) {
		this.gengxnr = gengxnr;
	}
}
