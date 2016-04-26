package services;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
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
			int id = expenseDAO.getId();
			try {
				// write the expense image data to ReceiptLogger/images/
				byte[] imageData = expense.getExpenseImageData();
				expenseSubmissionResponse.appendMessage("writing to new .png file");
				
				File imageFilePath = new File("/var/lib/ReceiptLogger/images/" + id + ".png");
				BufferedImage writeImage = ImageIO.read(new ByteArrayInputStream(imageData));
				ImageIO.write(writeImage, "png", imageFilePath);
				expenseSubmissionResponse.appendMessage("managed to write to filepath");
				
				// write the description to a text file
				String description = expense.getDescription();
				File descriptionFilePath = new File("/var/lib/ReceiptLogger/descriptions/" + id + ".txt");
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(descriptionFilePath));
				writer.write(description);
				
				writer.close();
				
				expenseSubmissionResponse = expenseDAO.insertExpense(expense);
				expenseSubmissionResponse.appendMessage("Made it past the service.");
				expense = null;
				
				return expenseSubmissionResponse;
				
			} catch(IOException e){
				expense = null;
				expenseSubmissionResponse.appendMessage("There was an ioexception");
				expenseSubmissionResponse.appendMessage(e.getMessage());
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
	
/*	private ExpenseSubmissionResponse returnErrorMessage(Exception e){
		expenseSubmissionResponse.appendMessage(e.getMessage());
		return expenseSubmissionResponse;
	}*/
	
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
