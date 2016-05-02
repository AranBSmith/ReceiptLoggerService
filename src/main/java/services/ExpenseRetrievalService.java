package services;

import dao.ExpenseDAO;
import model.ExpenseRetrievalResponse;
import model.LoginResponse;

/**
 * Service class for validating expense retrieval requests and obtaining that 
 * information from the ExpenseDAO class.
 * 
 * @author Aran
 *
 */
public class ExpenseRetrievalService {
	
	LoginService loginService;
	ExpenseDAO expenseDAO;
	
	/**
	 * Creates an instance of the LoginService as well the ExpenseDAO
	 */
	public ExpenseRetrievalService(){
		this.loginService = new LoginService();
		this.expenseDAO = new ExpenseDAO();
	}
	
	/**
	 * Used to get user expenses according to a provided email.
	 * @param email
	 * @param password
	 * @return ExpenseRetrievalResponse containing all of the user's expenses,
	 * if the credentials submitted were valid. Will not return the user's 
	 * expenses if the credentials were wrong. Will return exceptions in the 
	 * webservice if something went wrong upon trying to retrieve user expenses.
	 */
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
