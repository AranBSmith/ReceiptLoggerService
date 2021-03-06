package services;

import dao.ExpenseDAO;
import dao.FileSystemDAO;
import lombok.Data;
import model.Expense;
import model.ExpenseSubmissionResponse;

/**
 * Service Class used for submitting expenses.
 * 
 * @author Aran
 */
@Data
public class ExpenseSubmissionService {

	private ExpenseDAO expenseDAO;
	private FileSystemDAO fileSystemDAO;
	private ExpenseSubmissionResponse expenseSubmissionResponse;
	private LoginService loginService;
	
	/**
	 * Creates an instance of ExpenseDAO, FileSystemDAO and 
	 * ExpenseSubmissionResponse
	 */
	public ExpenseSubmissionService(){
		expenseDAO = new ExpenseDAO();
		fileSystemDAO = new FileSystemDAO();
		expenseSubmissionResponse = new ExpenseSubmissionResponse();
		loginService = new LoginService();
	}
	
	/**
	 * This method verifies an expense object passed to it and writes image and 
	 * expense description data to the file system, then it will insert the 
	 * appropriate information to the database.
	 * 
	 * @param expense Expense object.
	 * @return ExpenseSubmissionResponse object which specifying the status of the 
	 * expense submission, and any exceptions thrown in the process of submitting 
	 * an expense.
	 */
	public ExpenseSubmissionResponse submitExpense(Expense expense, String password) {
		if( expense != null && 
			password != null && 
			loginService.checkCredentials(expense.getEmail(), password).isSuccess()){
			
			if(isValid(expense)){
				expenseSubmissionResponse.appendMessage("details are valid");
				int id = expenseDAO.getId();
				try {
					
					// write the expense image data to ReceiptLogger/images/
					byte[] imageData = expense.getExpenseImageData();
					expenseSubmissionResponse.appendMessage("writing to new .png file");
					if(fileSystemDAO.writeExpenseImageData(imageData, id)){
						expenseSubmissionResponse.appendMessage("managed to write image data to filepath");
					} else {
						expenseSubmissionResponse.appendMessage("was not able to write image data to filepath");
					}
					
					// write the description to a text file
					String description = expense.getDescription();
					if(fileSystemDAO.writeExpenseDescription(description, id)){
						expenseSubmissionResponse.appendMessage("managed to write image description to filepath");
					} else {
						expenseSubmissionResponse.appendMessage("was not able to write description to filepath");
					}
					
					expenseSubmissionResponse = expenseDAO.insertExpense(expense);
					expenseSubmissionResponse.appendMessage("Made it past the service.");
					expense = null;
					
					return expenseSubmissionResponse;
					
				} catch(IllegalArgumentException e){
					expense = null;
					expenseSubmissionResponse.appendMessage("There was an illegalArgumentException");
					expenseSubmissionResponse.appendMessage(e.getMessage());
					return expenseSubmissionResponse;
				} catch(Exception e){
					expense = null;
					e.printStackTrace();
					expenseSubmissionResponse.appendMessage("There was someother exception");
					expenseSubmissionResponse.appendMessage(e.getMessage());
					expenseSubmissionResponse.appendMessage(e.toString());
					return expenseSubmissionResponse;
				}
			} else {
				expenseSubmissionResponse.appendMessage("There was an invalid field: Null or Invalid");
				expense = null;
				return expenseSubmissionResponse;
			}	
		}
		
		expenseSubmissionResponse.appendMessage("invalid login details");
		return expenseSubmissionResponse;
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
