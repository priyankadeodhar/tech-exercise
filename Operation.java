package com.data;
enum Operator{ADD,SUBTRACT,MULTIPLY};
public class Operation {
	
	private Operator op;
	private Product operateOnProduct=null;
	
	public Product getOperateOnProduct() {
		return operateOnProduct;
	}

	public Operation()
	{
		
	}

	public Operation(Operator op,Product product)
	{
		this.op=op;
		this.operateOnProduct=new Product(product.getName(),product.getPrice());
		
	}

	public Operator getOp() {
		return op;
	}

	public void setOp(Operator op) {
		this.op = op;
	}

	public String toString()
	{
		return "Operation is : "+ op + "  £"+ operateOnProduct.getPrice()+ " "+operateOnProduct.getName();
	}
	
}
