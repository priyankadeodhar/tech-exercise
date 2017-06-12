package com.data;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class OutputData {
	
	private LinkedHashMap<String,ProductInfo> outputDataObj =null;
	
	private ArrayList<Message> messageList=null;
 	private LinkedHashMap<Operation,ArrayList<Sales>> after50thMessageReport=null;//Message here is the afftected message in the input messages
	private int counter=0;
	private int counterForEvery10thMessage=0;
	private boolean pauseApplication=false;
	
	public boolean isPauseApplication() {
		return pauseApplication;
	}
	
	public void readFile()
	{
		String line=null;
		try
		{

			String currentDir=System.getProperty("user.dir");
			FileReader fr= new FileReader(currentDir+"\\inputData.txt");
			Scanner s= new Scanner(fr);
			Message msg=null;
			
			while(s.hasNext() )
			{
				line=s.next();

				
				String[] lineStr= line.split(",");
				if(lineStr!=null && lineStr.length!=0)
				{
					msg=Message.createMessagePerLine(lineStr);
					messageProcessor(msg);
					if(pauseApplication)
					{
						return;
					}
				}
			}
			s.close();
		}

		catch(FileNotFoundException f)
		{
			f.printStackTrace();
		} 
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		catch(ParseException pe)
		{
			pe.printStackTrace();

		}
		
	}

	
	
	public void messageProcessor(Message msg)
	{
		
		if(msg!=null )
		{
			if(outputDataObj==null)
			{
				outputDataObj =new LinkedHashMap<String,ProductInfo>();
			}
			if(messageList==null)
			{
				messageList = new ArrayList<Message>();
			}
			if(after50thMessageReport == null )
			{
				after50thMessageReport = new LinkedHashMap<Operation,ArrayList<Sales>>();
			}
		
			messageList.add(msg);
		
			
				if(counter%10==0 && !outputDataObj.isEmpty())
				{
					counterForEvery10thMessage++;
					displayOutputofProcessedMessages();
				}
				
				if(counter == 50)
				{
					
					pauseApplication=true;
					return;
				}
				
				counter++;
				
				ProductInfo pi=new ProductInfo();
				Product p=msg.getSale().getProduct();
				String productName=p.getName();// key 
				//value
				pi.setTotalSale(msg.getSale().getNoOfSales());
				pi.setTotalPrice(msg.getSale().getNoOfSales()*p.getPrice());
				
							
							if(outputDataObj.containsKey(productName))
							{
								//to update existing key's value
								
								ProductInfo existingProductInfo = outputDataObj.get(productName);
								//here pi is new productInfo to be added to existing one
								existingProductInfo.setTotalPrice(existingProductInfo.getTotalPrice()+pi.getTotalPrice());
								existingProductInfo.setTotalSale(existingProductInfo.getTotalSale()+pi.getTotalSale());
								
								outputDataObj.replace(productName, existingProductInfo);
								
								
							}
							else
							{
								outputDataObj.put(productName, pi);
							}
						
								if(msg.getType() == MessageType.Type3)
								{
									
									Operation operation=msg.getOperation();
									//get the product name from operation object for adjustment
									String operateOnProduct = operation.getOperateOnProduct().getName();
									float adjustPrice = operation.getOperateOnProduct().getPrice();
									if(outputDataObj.containsKey(operateOnProduct))
									{
										ProductInfo temp=outputDataObj.get(operateOnProduct);//get the productInfo for a product key 
										float currentUnitPrice = temp.getTotalPrice()/temp.getTotalSale();
										float newUnitPrice= performOperationOnUnitPrice(currentUnitPrice,adjustPrice,operation.getOp());
										temp.setTotalPrice(newUnitPrice*temp.getTotalSale());
										
										
										outputDataObj.replace(operateOnProduct, temp);
										
									}
									else
									{
										System.out.println("No such Product "+operateOnProduct+ " found");
									}
							//		after50thMessageReport.put(operation, msg.getSale());
									ArrayList<Sales> sale= new ArrayList<Sales>();
									Iterator<Message> it=messageList.iterator();
									while(it.hasNext())
									{
										Message msg1=it.next();
										
										if(operateOnProduct.equals(msg1.getSale().getProduct().getName()))
										{
											//System.out.println("Message=="+msg1);
											sale.add(msg1.getSale());
										}
									}
									
									after50thMessageReport.put(operation, sale);
								}
					}
}
	public static float performOperationOnUnitPrice(float currentUnitPrice,float adjustPrice, Operator op )
	{
		float newUnitPrice=0.0f;
		switch(op)
		{
		case ADD :newUnitPrice = currentUnitPrice + adjustPrice;
				
			break;
		case SUBTRACT:newUnitPrice = currentUnitPrice - adjustPrice;
			break;
		case MULTIPLY:newUnitPrice = currentUnitPrice * adjustPrice;
			break;
		}
		return newUnitPrice;
	}
	
	public void displayOutputofProcessedMessages()
	{
		
		if(outputDataObj!=null && !outputDataObj.isEmpty()){
			 System.out.println("===========================");
			 System.out.println("<---------------------Processed Messages after "+counterForEvery10thMessage +"0th Message--------> " );
			
			Set<Map.Entry<String,ProductInfo>> entrySet = outputDataObj.entrySet();
			
			
			for(Map.Entry<String, ProductInfo> me : entrySet)
			{
				 System.out.println("===========================");
				 System.out.println("Product Type:"+me.getKey() );
			      System.out.println("Product Details ----->"+me.getValue());
			}
			
		}
	}
	public void get50MessageReport()
	{
		System.out.println("----------------------------");
		System.out.println("Data Report after 50th Message");
		System.out.println("----------------------------");
		if(after50thMessageReport!=null && !after50thMessageReport.isEmpty())
		{
			
			Set<Map.Entry<Operation, ArrayList<Sales>>> entrySet = after50thMessageReport.entrySet();
			
			for(Map.Entry<Operation,  ArrayList<Sales>> me:entrySet)
			{
			
				 System.out.println("Operation :"+me.getKey() );
			      System.out.println("Sale Details -----> ");
			      ArrayList<Sales> tempList = me.getValue();
			      Iterator<Sales> it=tempList.iterator();
			      while(it.hasNext())
			      {
			    	  System.out.println(it.next());
			      }
			      
			}
		}
	}

	
	public static void main(String args[])
	{
		OutputData outputData = new OutputData();
		outputData.readFile();
		
		
	if(outputData.isPauseApplication())
		{
		
			outputData.get50MessageReport();
			System.out.println("Now the application is pausing");
			while(true){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
