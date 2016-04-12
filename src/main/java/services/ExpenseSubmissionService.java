package services;

import dao.ExpenseDAO;
import model.Expense;
import model.ExpenseSubmissionResponse;

import lombok.Data;

@Data
public class ExpenseSubmissionService {

	private ExpenseDAO expenseDAO;
	private ExpenseSubmissionResponse expenseSubmissionResponse;
	
	public ExpenseSubmissionService(){
		expenseDAO = new ExpenseDAO();
		expenseSubmissionResponse = new ExpenseSubmissionResponse();
	}
	
	public ExpenseSubmissionResponse submitExpense(Expense expense) {
		if(isValid(expense))
			return expenseDAO.insertExpense(expense);
		else return expenseSubmissionResponse;
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
