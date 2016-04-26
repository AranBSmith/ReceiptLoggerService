package model;

import java.util.LinkedList;

public class ExpenseRetrievalResponse extends Response{
	
	LinkedList<Expense> expenses;
	
	public ExpenseRetrievalResponse(){
		super();
		expenses = new LinkedList<>();
	}
	
	public void addExpense(Expense expense){
		expenses.add(expense);
	}
}
