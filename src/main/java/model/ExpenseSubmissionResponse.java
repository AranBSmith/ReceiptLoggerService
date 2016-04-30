package model;

import lombok.Data;

@Data
public class ExpenseSubmissionResponse extends Response{
	private int id;
	
	public ExpenseSubmissionResponse(){
		super();
	}
}