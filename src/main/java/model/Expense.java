package model;

import java.util.Date;

import lombok.Data;

@Data
public class Expense {
	private String email;
	private String category;
	private Date date;
	private byte[] expenseImageData;
	
	public Expense(String email, String category, Date date, byte[] expenseImageData){
		this.email = email;
		this.category = category;
		this.date = date;
		this.expenseImageData = expenseImageData;
	}
	
}
