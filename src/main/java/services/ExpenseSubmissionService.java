package services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dao.ExpenseDAO;
import lombok.Data;
import model.Expense;
import model.ExpenseSubmissionResponse;

@Data
public class ExpenseSubmissionService {

	private ExpenseDAO expenseDAO;
	private ExpenseSubmissionResponse expenseSubmissionResponse;
	
	public ExpenseSubmissionService(){
		expenseDAO = new ExpenseDAO();
		expenseSubmissionResponse = new ExpenseSubmissionResponse();
	}
	
	public ExpenseSubmissionResponse submitExpense(Expense expense) {
		if(isValid(expense)){
			expenseSubmissionResponse.appendMessage("details are valid");
			// write the expense image data to images/
			byte[] imageData = expense.getExpenseImageData();
			try {
				expenseSubmissionResponse.appendMessage("writing to test.png");
				File filePath = new File("/usr/share/tomcat7/webapps/images/test.png");
				BufferedImage writeImage = ImageIO.read(new ByteArrayInputStream(imageData));
				ImageIO.write(writeImage, "png", filePath);
				
				expenseSubmissionResponse = expenseDAO.insertExpense(expense);
				expenseSubmissionResponse.appendMessage("Made it past the service.");
				return expenseSubmissionResponse;
			
			} catch(IOException e){
				expenseSubmissionResponse.appendMessage("There was an ioexception");
				expenseSubmissionResponse.appendMessage(e.getMessage());
				return expenseSubmissionResponse;
			} catch(Exception e){
				e.printStackTrace();
				expenseSubmissionResponse.appendMessage("There was someother exception");
				expenseSubmissionResponse.appendMessage(e.getMessage());
				expenseSubmissionResponse.appendMessage(e.toString());
				return expenseSubmissionResponse;
			}
		} else {
			expenseSubmissionResponse.appendMessage("There was an invalid field: Null or Invalid");
			return expenseSubmissionResponse;
		}
	}
	
	private ExpenseSubmissionResponse returnErrorMessage(Exception e){
		expenseSubmissionResponse.appendMessage(e.getMessage());
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
