package services;

import dao.ExpenseDAO;
import model.ExpenseRetrievalResponse;
import model.LoginResponse;

public class ExpenseRetrievalService {
	
	LoginService loginService;
	ExpenseDAO expenseDAO;
	
	public ExpenseRetrievalService(){
		this.loginService = new LoginService();
		this.expenseDAO = new ExpenseDAO();
	}
	
	public ExpenseRetrievalResponse getUserExpenses(String email, String password) {
		
		// check credentials against db
		LoginResponse loginResponse = loginService.checkCredentials(email, password);
		
		// if valid, retrieve all expenses associated with the email.
		if(loginResponse.isSuccess()){
			return expenseDAO.getAllExpensesByEmail(email);
		}
		// if not valid return null
		else {
			ExpenseRetrievalResponse expenseRetrievalResponse = new ExpenseRetrievalResponse();
			expenseRetrievalResponse.appendMessage("Invalid login credentials");
			return expenseRetrievalResponse;
		}
	}

}
