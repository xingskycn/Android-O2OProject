package com.gez.cookery.jiaoshou.model;

import java.util.ArrayList;

public class Pingltj {
	private Shangjpl cantpl;
	private ArrayList<Caippl> caippl = new ArrayList<Caippl>();

	public Shangjpl getCantpl() {
		return this.cantpl;
	}

	public void setCantpl(Shangjpl cantpl) {
		this.cantpl = cantpl;
	}

	public ArrayList<Caippl> getCaippl() {
		return this.caippl;
	}

	public void setCaippl(ArrayList<Caippl> caippl) {
		this.caippl = caippl;
	}
}