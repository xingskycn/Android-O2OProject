package com.gez.grill.entity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Dingddp {
	private String cantId;
	
	private String cantbstp;
	
	private String cantMc;

	private String gukdz;

	private String gukdh;

	private BigDecimal dingdze;

	private ArrayList<Dingdmx> caipxx = new ArrayList<Dingdmx>();

	public String getCantId() {
		return cantId;
	}

	public void setCantId(String cantId) {
		this.cantId = cantId;
	}

	public String getCantbstp() {
		return cantbstp;
	}

	public void setCantbstp(String cantbstp) {
		this.cantbstp = cantbstp;
	}

	public String getCantMc() {
		return cantMc;
	}

	public void setCantMc(String cantMc) {
		this.cantMc = cantMc;
	}

	public String getGukdz() {
		return gukdz;
	}

	public void setGukdz(String gukdz) {
		this.gukdz = gukdz;
	}

	public String getGukdh() {
		return gukdh;
	}

	public void setGukdh(String gukdh) {
		this.gukdh = gukdh;
	}

	public BigDecimal getDingdze() {
		return dingdze;
	}

	public void setDingdze(BigDecimal dingdze) {
		this.dingdze = dingdze;
	}

	public ArrayList<Dingdmx> getCaipxx() {
		return caipxx;
	}

	public void setCaipxx(ArrayList<Dingdmx> caipxx) {
		this.caipxx = caipxx;
	}
}