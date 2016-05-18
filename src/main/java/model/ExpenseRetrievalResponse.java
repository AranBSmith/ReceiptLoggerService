package model;

import java.util.LinkedList;
import lombok.Data;

@Data
public class ExpenseRetrievalResponse extends Response{
	
	private LinkedList<Expense> expenses;
	private LinkedList<String> compressedImageData;
	
	public ExpenseRetrievalResponse(){
		super();
		expenses = new LinkedList<>();
		compressedImageData = new LinkedList<>();
	}
	
	public void addExpense(Expense expense){
		expenses.add(expense);
	}
	
	public void addCompressedData(String data){
		compressedImageData.add(data);
	}
}
