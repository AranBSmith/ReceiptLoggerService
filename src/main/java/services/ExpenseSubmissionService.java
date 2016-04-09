package services;

import dao.ExpenseDAO;
import model.Expense;
import model.ExpenseSubmissionResponse;

public class ExpenseSubmissionService {

	ExpenseDAO expenseDAO;
	
	public ExpenseSubmissionResponse submitExpense(Expense expense) {
		return expenseDAO.insertExpense(expense);
	}
	
}
