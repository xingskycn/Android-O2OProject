package com.gez.cookery.jiaoshou.model;

public class User {

	private String id;

	private String weibm;

	private String qqh;
	
	private String zhangh;

	private String zhuangt;

	private String mordz;

	private String mordh;

	private String dianh1;
	
	private String dianh2;
	
	private String diz1;

	private String diz2;

	private String diz3;

	private String diz4;

	private String diz5;
	
	//纬度
	private double latitude = 0.0;
	
	//经度
	private double longitude = 0.0;

	//城市
	private String city = "";
	
	private boolean duojf;

	private boolean shaojf;

	private boolean duofl;

	private boolean shaofl;

	private boolean bufc;

	private boolean bufs;

	private boolean bufj;

	private boolean buykz;
	
	
	public boolean isDuojf() {
		return duojf;
	}

	public void setDuojf(boolean duojf) {
		this.duojf = duojf;
	}

	public boolean isShaojf() {
		return shaojf;
	}

	public void setShaojf(boolean shaojf) {
		this.shaojf = shaojf;
	}

	public boolean isDuofl() {
		return duofl;
	}

	public void setDuofl(boolean duofl) {
		this.duofl = duofl;
	}

	public boolean isShaofl() {
		return shaofl;
	}

	public void setShaofl(boolean shaofl) {
		this.shaofl = shaofl;
	}

	public boolean isBufc() {
		return bufc;
	}

	public void setBufc(boolean bufc) {
		this.bufc = bufc;
	}

	public boolean isBufs() {
		return bufs;
	}

	public void setBufs(boolean bufs) {
		this.bufs = bufs;
	}

	public boolean isBufj() {
		return bufj;
	}

	public void setBufj(boolean bufj) {
		this.bufj = bufj;
	}

	public boolean isBuykz() {
		return buykz;
	}

	public void setBuykz(boolean buykz) {
		this.buykz = buykz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZhangh() {
		return zhangh;
	}

	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}

	public String getZhuangt() {
		return zhuangt;
	}

	public void setZhuangt(String zhuangt) {
		this.zhuangt = zhuangt;
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
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
	
	public String getWeibm() {
		return weibm;
	}

	public void setWeibm(String weibm) {
		this.weibm = weibm;
	}

	public String getQqh() {
		return qqh;
	}

	public void setQqh(String qqh) {
		this.qqh = qqh;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
