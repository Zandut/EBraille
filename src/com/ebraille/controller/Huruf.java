package com.ebraille.controller;

public class Huruf {
	private String huruf;
	private Integer[] state = new Integer[6];
	
	public Huruf(String huruf, int state1, int state2, int state3, int state4, int state5, int state6) {
		this.huruf = huruf;
		this.state[0] = state1;
		this.state[1] = state2;
		this.state[2] = state3;
		this.state[3] = state4;
		this.state[4] = state5;
		this.state[5] = state6;
		
	}
	
	public String getHuruf() {
		return huruf;
	}
	
	public Integer[] getState() {
		return state;
	}
	
	public Huruf() {
		// TODO Auto-generated constructor stub
	}
	
	public void setHuruf(String huruf) {
		this.huruf = huruf;
	}

}
