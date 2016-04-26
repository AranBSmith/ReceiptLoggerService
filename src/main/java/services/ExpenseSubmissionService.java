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
			int id = ExpenseDAO.getId();
			try {
				// write the expense image data to images/
				byte[] imageData = expense.getExpenseImageData();
				expenseSubmissionResponse.appendMessage("writing to new .png file");
				
				File filePath = new File("/var/lib/ReceiptLogger/images/" + id + ".png");
				// expenseSubmissionResponse.appendMessage("opened directory with file.");
				
				// if(filePath.exists()){
					// expenseSubmissionResponse.appendMessage("file does exist");
					// if(filePath.canRead()){
						// expenseSubmissionResponse.appendMessage("Can read file");
						if(filePath.canWrite()){
							expenseSubmissionResponse.appendMessage("Can write to file.");
							BufferedImage writeImage = ImageIO.read(new ByteArrayInputStream(imageData));
							expenseSubmissionResponse.appendMessage("managed to open test.png");
			
							ImageIO.write(writeImage, "png", filePath);
							expenseSubmissionResponse.appendMessage("managed to write to filepath");
							
							expenseSubmissionResponse = expenseDAO.insertExpense(expense);
							expenseSubmissionResponse.appendMessage("Made it past the service.");
							expense = null;
							return expenseSubmissionResponse;
						} else {
							expenseSubmissionResponse.appendMessage("cannot write to file.");
						}
					// }
				// }
				
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
