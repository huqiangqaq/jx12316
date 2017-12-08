package com.nxt.ott.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class SerializableObject implements Serializable {
	   
	  private Map<String,Object> map;
	  private HashMap<String,Object> hmap;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public HashMap<String, Object> getHmap() {
		return hmap;
	}

	public void setHmap(HashMap<String, Object> hmap) {
		this.hmap = hmap;
	}

	

	
	    
	   
}
