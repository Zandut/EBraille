package com.ebraille.models;

public class Braille {
	private int Id;
	private String Huruf;
	
	
	private int[] state = {0,0,0,0,0,0};
	
	
	
	public void setHuruf(String huruf) {
		Huruf = huruf;
	}
	
	public String getHuruf() {
		return Huruf;
	}
	
	public void setId(int id) {
		Id = id;
	}
	
	public int getId() {
		return Id;
	}
	
	public int[] getState() {
		return state;
	}
	
	public void changeState(int a, int b, int c, int d, int e, int f)
	{
		this.state[0] = a;
		this.state[1] = b;
		this.state[2] = c;
		this.state[3] = d;
		this.state[4] = e;
		this.state[5] = f;
		
	}
	
	
	

}
