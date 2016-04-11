package services;

import dao.ExpenseDAO;
import model.Expense;
import model.ExpenseSubmissionResponse;

import lombok.Data;

@Data
public class ExpenseSubmissionService {

	private ExpenseDAO expenseDAO;
	
	public ExpenseSubmissionService(){
		expenseDAO = new ExpenseDAO();
	}
	
	public ExpenseSubmissionResponse submitExpense(Expense expense) {
		if(isValid(expense))
			return expenseDAO.insertExpense(expense);
		else return new ExpenseSubmissionResponse();
	}
	
	private boolean isValid(Expense expense){
		if(	   expense == null
			|| expense.containsNullField()
			|| expense.containsInvalidValue()
				){
			return false;
		}
		
		else return true;
	}
}
