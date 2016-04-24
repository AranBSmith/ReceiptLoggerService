package model;

import lombok.Data;

@Data
public class Expense {
	private String email;
	private Double price;
	private String currency;
	private String category;
	private String date;
	private String description;
	private byte[] expenseImageData;
	private Boolean approved;
	
	public Expense(
			String email, double price, String currency, String category, 
			String date, String description, byte[] expenseImageData,
			boolean approved
			){
		
		this.email = email;
		this.price = price;
		this.currency = currency;
		this.category = category;
		this.date = date;
		this.description = description;
		this.expenseImageData = expenseImageData;
		this.approved = approved;
	}
	
	public boolean isApproved(){
		return approved.booleanValue();
	}
	
	// checks if any of the fields within Expense is null
	public boolean containsNullField(){
		if((	email == null 
				|| price == null 
				|| currency == null
				|| category == null
				|| date == null
				|| description == null
				|| expenseImageData == null
				|| approved == null )){
			
			return true;
		}
		
		else return false;
	}
	
	// checks if any of the fields within Expense are invalid
	public boolean containsInvalidValue(){
		//TODO 
		return false;
	}
}
