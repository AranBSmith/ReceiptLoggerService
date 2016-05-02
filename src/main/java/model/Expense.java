package model;

import lombok.Data;


/**
 * Expense Model class containing data on a user expense.
 * 
 * @author Aran
 *
 */
@Data
public class Expense {
	private int id;
	private String email;
	private Double price;
	private String currency;
	private String card;
	private String category;
	private String date;
	private String description;
	private byte[] expenseImageData;
	private Boolean approved;
	
	public Expense(
			String email, double price, String currency, String card, String category, 
			String date, String description, byte[] expenseImageData,
			boolean approved
			){
		
		this.id = -1;
		this.email = email;
		this.price = price;
		this.currency = currency;
		this.card = card;
		this.category = category;
		this.date = date;
		this.description = description;
		this.expenseImageData = expenseImageData;
		this.approved = approved;
	}
	
	public Expense(
			int id, String email, double price, String currency, String card, String category, 
			String date, String description, byte[] expenseImageData,
			boolean approved
			){
		
		this.id = id;
		this.email = email;
		this.price = price;
		this.currency = currency;
		this.card = card;
		this.category = category;
		this.date = date;
		this.description = description;
		this.expenseImageData = expenseImageData;
		this.approved = approved;
	}
	
	/**
	 * Check if the expense is approved.
	 * 
	 * @return true if approved, false if not approved.
	 */
	public boolean isApproved(){
		return approved.booleanValue();
	}
	

	/**
	 * checks fields in expense for any nulls.
	 * 
	 * @return true if contains a null field, false otherwise.
	 */
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
	
	/**
	 * checks for invalid fields
	 * 
	 * @return true if contains an invalid field, false if otherwise.
	 */
	public boolean containsInvalidValue(){
		//TODO 
		return false;
	}
}
