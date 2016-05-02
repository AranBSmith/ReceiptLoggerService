package services;

import dao.ExpenseDAO;
import model.CancelExpenseResponse;
import lombok.Data;

/**
 * Service class used for canceling expenses.
 * 
 * @author Aran
 *
 */
@Data
public class CancelExpenseService {
	private LoginService loginService;
	private ExpenseDAO expenseDAO;
	private CancelExpenseResponse cancelExpenseResponse;
	
	/**
	 * Creates an instance of the LoginService, ExpenseDAO and CancelExpenseResponse
	 * classes
	 */
	public CancelExpenseService(){
		loginService = new LoginService();
		expenseDAO = new ExpenseDAO();
		cancelExpenseResponse = new CancelExpenseResponse();
	}
	
	/**
	 * Verifies that the user credentials and id provided are valid. Submits
	 * the information provided for expense cancellation in the ExpenseDAO.
	 * 
	 * @param email
	 * @param password
	 * @param id
	 * @return CancelExpenseResponse object which specifying the status of the 
	 * expense cancellation.
	 */
	public CancelExpenseResponse cancelExpense(String email, String password, int id){
		if(loginService.checkCredentials(email, password).isSuccess() && id >= 0){
			// remove specified expense from the DB and file system
			return expenseDAO.removeExpense(id);
		} else {
			cancelExpenseResponse.appendMessage("Credentials were invalid.");
			return cancelExpenseResponse;
		}
	}
}
