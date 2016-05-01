package services;

import dao.ExpenseDAO;
import model.CancelExpenseResponse;

public class CancelExpenseService {
	private LoginService loginService;
	private ExpenseDAO expenseDAO;
	private CancelExpenseResponse cancelExpenseResponse;
	
	public CancelExpenseService(){
		loginService = new LoginService();
		expenseDAO = new ExpenseDAO();
		cancelExpenseResponse = new CancelExpenseResponse();
	}
	
	public CancelExpenseResponse cancelExpense(String email, String password, int id){
		if(loginService.checkCredentials(email, password).isSuccess()){
			// remove specified expense from the DB and file system
			return expenseDAO.removeExpense(id);
		} else {
			cancelExpenseResponse.appendMessage("Credentials were invalid.");
			return cancelExpenseResponse;
		}
	}
}
