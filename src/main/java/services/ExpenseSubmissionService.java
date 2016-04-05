package services;

import dao.ExpenseDAO;
import model.Expense;

public class ExpenseSubmissionService {

	ExpenseDAO expenseDAO;
	
	public boolean submitExpense(Expense expense) {
		return expenseDAO.insertExpense(expense);
	}
	
}
