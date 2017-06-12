package com.data;

public class Product {
	private String name=null;
	private float price=0.0f;
	public Product(String name,float price)
	{
		this.name=name;
		this.price=price;
	}
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
