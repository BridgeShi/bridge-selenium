package com.bridge.dao;

public class PreOrderList {
	
	private String url = "";
	private String firstOption = "";
	private String secondOption = "";
	private String qty = "";
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return this.url;
	}
	
	public void setFirstOption(String firstOption){
		this.firstOption = firstOption;
	}
	
	public String getFirstOption(){
		return this.firstOption;
	}
	
	public void setSecondOption(String secondOption){
		this.secondOption = secondOption;
	}
	
	public String getSecondOption(){
		return this.secondOption;
	}
	
	public void setQty(String qty){
		this.qty = qty;
	}
	
	public String getQty(){
		return this.qty;
	}

}
