/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebraille.controller;

import java.util.ArrayList;

/**
 *
 * @author Zandut
 */
public class Kalimat {
    private ArrayList<String> kalimat;
   // private speech play;
    
    public Kalimat() {
		kalimat = new ArrayList<String>();
	}
    
    public void addKata(String k)
    {
    	
        kalimat.add(k);
        
        
    }

    public ArrayList<String> getKalimat() {
        return kalimat;
    }
    
    
    
    
	
    
    
    
}
