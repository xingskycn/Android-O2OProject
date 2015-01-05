package com.gez.grill.entity;

import java.util.ArrayList;

public class Pingltj {
	private Cantpl cantpl;

	private ArrayList<Caippl> caippl = new ArrayList<Caippl>();

	public Cantpl getCantpl() {
		return this.cantpl;
	}

	public void setCantpl(Cantpl cantpl) {
		this.cantpl = cantpl;
	}

	public ArrayList<Caippl> getCaippl() {
		return this.caippl;
	}

	public void setCaippl(ArrayList<Caippl> caippl) {
		this.caippl = caippl;
	}
}