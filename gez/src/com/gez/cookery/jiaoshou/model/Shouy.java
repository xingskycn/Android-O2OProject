package com.gez.cookery.jiaoshou.model;

import java.util.ArrayList;

public class Shouy extends JsonModel {

	private String kaissy;

	private String jiessy;

	private String dingd_id;
	
	private String caip_id;
	
	private ArrayList<Taocmx> taocmx = new ArrayList<Taocmx>();

	private ArrayList<Guktz> guktz = new ArrayList<Guktz>();

	private ArrayList<Gukxc> gukxc = new ArrayList<Gukxc>();

	public String getKaissy() {
		return kaissy;
	}

	public void setKaissy(String kaissy) {
		this.kaissy = kaissy;
	}

	public String getJiessy() {
		return jiessy;
	}

	public void setJiessy(String jiessy) {
		this.jiessy = jiessy;
	}

	public String getDingd_id() {
		return dingd_id;
	}

	public void setDingd_id(String dingd_id) {
		this.dingd_id = dingd_id;
	}

	public ArrayList<Taocmx> getTaocmx() {
		return taocmx;
	}

	public void setTaocmx(ArrayList<Taocmx> taocmx) {
		this.taocmx = taocmx;
	}

	public ArrayList<Guktz> getGuktz() {
		return guktz;
	}

	public void setGuktz(ArrayList<Guktz> guktz) {
		this.guktz = guktz;
	}

	public ArrayList<Gukxc> getGukxc() {
		return gukxc;
	}

	public void setGukxc(ArrayList<Gukxc> gukxc) {
		this.gukxc = gukxc;
	}

	public String getCaip_id() {
		return caip_id;
	}

	public void setCaip_id(String caip_id) {
		this.caip_id = caip_id;
	}
	
}
