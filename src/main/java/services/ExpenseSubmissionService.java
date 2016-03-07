package services;

import dao.Cassandra;
import model.Expense;
import model.ExpenseSubmissionResponse;

public class ExpenseSubmissionService {

	Cassandra cassandra;
	
	public ExpenseSubmissionResponse submitExpense(Expense expense) {
		return cassandra.submitExpense(expense);
	}
	
}
