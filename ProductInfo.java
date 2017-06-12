package com.data;

public class ProductInfo {
	//private float unitPrice=0.0f;
	private float totalPrice=0.0f;
	private int totalSale=0;
	
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(int totalSale) {
		this.totalSale = totalSale;
	}
	public String toString()
	{
		return ("Total Sales : "+totalSale +"\nTotal Price : £ "+totalPrice);
	}
			

}
