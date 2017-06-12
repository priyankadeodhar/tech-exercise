package com.data;

import java.text.ParseException;


enum MessageType{Type1,Type2,Type3};

public class Message {
 
	private MessageType type=null;
	private Sales sale=null;
	private Operation operation=null;
	public Sales getSale() {
		return sale;
	}
	
	public Operation getOperation() {
		return operation;
	}
	
	public MessageType getType() {
		return type;
	}
	
	
	public Message(){}
	
	public Message(MessageType type,Sales sale,Operation op)
	{
		this.type=type;
		this.sale=sale;
		this.operation=op;
	}
	@Override
	public String toString()
	{
		String msg=null;
		if(type == MessageType.Type1)
		{
		msg= type+"--"+sale.getNoOfSales()+"--"+sale.getProduct().getName()+"--"+sale.getProduct().getPrice()
				;
		}
		else if (type == MessageType.Type2)
		{
			msg= type+"--"+sale.getNoOfSales()+"--"+sale.getProduct().getName()+"--"+sale.getProduct().getPrice()
				;
		}
		else if(type== MessageType.Type3)
		{
			msg= type+"--"+sale.getNoOfSales()+"--"+sale.getProduct().getName()+"--"+sale.getProduct().getPrice()
					+"--"+operation.getOp()+"--"+operation.getOperateOnProduct().getName()+"--"+operation.getOperateOnProduct().getPrice();
		}
		return msg;
	}
	public static Message createMessagePerLine(String[] lineStr ) throws ParseException
	{
		/*Message Format in the line*/
		//type(0),no.Of Sales(1), product name(2), price(3),operation type(4), operation on product(5), operate by price(6)
		
		Message msg=null;
		int noOfSales=0;
		Sales  s=null;
		Product p=null;
		String productName=null;
		float price=0.0f;
		switch(Integer.parseInt(lineStr[0]))
		{
		case  1 : 	noOfSales=1;
					productName =lineStr[2];
					price =Float.parseFloat(lineStr[3]);
					p=new Product(productName,price);
					s=new Sales(noOfSales,p);
					msg = new Message(MessageType.Type1,s,null);
					break;
		
		case 2 :	noOfSales = Integer.parseInt(lineStr[1]);
					productName =lineStr[2];
					price =Float.parseFloat(lineStr[3]);
					p=new Product(productName,price);
					s=new Sales(noOfSales,p);
					msg = new Message(MessageType.Type2,s,null);
					break;
		
		case 3: 	noOfSales = Integer.parseInt(lineStr[1]);
					productName =lineStr[2];
					price =Float.parseFloat(lineStr[3]);
					s=new Sales(noOfSales,new Product(productName,price));
					String operationType = lineStr[4] ;
					Operation operation=new Operation();
					String operateOnProduct=lineStr[5];
					float operateOnPrice =Float.parseFloat(lineStr[6]);
					p= new Product(operateOnProduct,operateOnPrice);
					if(operationType.equalsIgnoreCase("ADD"))
					{
						operation=new Operation(Operator.ADD,p);
					}
					else if (operationType.equalsIgnoreCase("SUBTRACT"))
					{
						operation=new Operation(Operator.SUBTRACT,p);
					}
					else if(operationType.equalsIgnoreCase("MULTIPLY"))
					{
						operation=new Operation(Operator.MULTIPLY,p);
					}
					msg=new Message(MessageType.Type3,s,operation);
					break;
			default: System.out.println("Invalid Message type");
					
			}
		return msg;
	}	

}
