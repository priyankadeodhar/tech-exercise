package com.data;

public class Sales {

	private int noOfSales=0;
	private Product product=null;
	

	public Sales(int noOfSales,Product product)
	{
		this.noOfSales=noOfSales;
		this.product=new Product(product.getName(),product.getPrice());
		
	}
	public int getNoOfSales() {
		return noOfSales;
	}
	public void setNoOfSales(int noOfSales) {
		this.noOfSales = noOfSales;
	}
	public Product getProduct() {
		return product;
	}
	
	public String toString()
	{
		return noOfSales+" "+ product.getName() +" at £ "+product.getPrice() ;
	}
	
}
