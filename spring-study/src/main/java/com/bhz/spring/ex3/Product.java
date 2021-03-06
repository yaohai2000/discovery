package com.bhz.spring.ex3;

import java.math.BigDecimal;

public class Product implements java.io.Serializable{
	private BigDecimal price;
	private String id;
	private String name;
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "Product ID [ " + this.getId() + " ] Product Name [ " + this.getName() + " ] Product Price [ " + this.getPrice().toString() + " ]";
	}
	
}
