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
			// write the expense image data to images/
			byte[] imageData = expense.getExpenseImageData();
			try {
				File filePath = new File("/usr/share/tomcat7/webapps/images/test.png");
				BufferedImage writeImage = ImageIO.read(new ByteArrayInputStream(imageData));
				ImageIO.write(writeImage, "png", filePath);
				
				return expenseDAO.insertExpense(expense);
			
			} catch(IOException e){
				e.printStackTrace();
				return returnErrorMessage(e);

			} catch(Exception e){
				e.printStackTrace();
				return returnErrorMessage(e);
			}
		}
		
		else return expenseSubmissionResponse;
	}
	
	private ExpenseSubmissionResponse returnErrorMessage(Exception e){
		ExpenseSubmissionResponse expenseSubResponse = new ExpenseSubmissionResponse();
		expenseSubResponse.appendMessage(e.getMessage());
		return expenseSubResponse;
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
