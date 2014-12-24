package com.ebraille.controller;

public class KotakKeluar {
	private int id;
	private String nomer;
	private String pesan;
	private String status;
	private String waktu;
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setWaktu(String waktu) {
		this.waktu = waktu;
	}
	
	public String getWaktu() {
		return waktu;
	}
	
	public void setNomer(String nomer) {
		this.nomer = nomer;
	}
	
	public void setPesan(String pesan) {
		this.pesan = pesan;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getNomer() {
		return nomer;
	}
	
	public String getPesan() {
		return pesan;
	}
	
	public String getStatus() {
		return status;
	}
}
