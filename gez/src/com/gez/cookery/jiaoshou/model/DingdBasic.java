package com.gez.cookery.jiaoshou.model;

public class DingdBasic extends JsonModel {
	private String id;

	private String dingdzt;

	private String dianpzt;

	private String zongfs;

	private String zongjg;
	
	private String bianh;
	
	private String cantbz;

	public String getCantbz() {
		return cantbz;
	}

	public void setCantbz(String cantbz) {
		this.cantbz = cantbz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDingdzt() {
		return dingdzt;
	}

	public void setDingdzt(String dingdzt) {
		this.dingdzt = dingdzt;
	}

	public String getDianpzt() {
		return dianpzt;
	}

	public void setDianpzt(String dianpzt) {
		this.dianpzt = dianpzt;
	}

	public String getZongfs() {
		return zongfs;
	}

	public void setZongfs(String zongfs) {
		this.zongfs = zongfs;
	}

	public String getZongjg() {
		return zongjg;
	}

	public void setZongjg(String zongjg) {
		this.zongjg = zongjg;
	}

	public String getBianh() {
		return bianh;
	}

	public void setBianh(String bianh) {
		this.bianh = bianh;
	}
}