package services;

import dao.ExpenseDAO;
import dao.FileSystemDAO;
import lombok.Data;
import model.Expense;
import model.ExpenseSubmissionResponse;

@Data
public class ExpenseSubmissionService {

	private ExpenseDAO expenseDAO;
	private FileSystemDAO fileSystemDAO;
	private ExpenseSubmissionResponse expenseSubmissionResponse;
	
	public ExpenseSubmissionService(){
		expenseDAO = new ExpenseDAO();
		fileSystemDAO = new FileSystemDAO();
		expenseSubmissionResponse = new ExpenseSubmissionResponse();
	}
	
	public ExpenseSubmissionResponse submitExpense(Expense expense) {
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
