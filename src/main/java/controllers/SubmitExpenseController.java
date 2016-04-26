package controllers;

import java.io.IOException;
import java.util.zip.DataFormatException;

import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.Expense;
import model.ExpenseSubmissionResponse;
import services.CompressionUtils;
import services.ExpenseSubmissionService;

@RestController
@Data
public class SubmitExpenseController {
	private ExpenseSubmissionService expenseSubmissionService;
	private ExpenseSubmissionResponse expenseSubmissionResponse;
	private Expense expense;
	byte[] b, decompressedImage;
	
	public SubmitExpenseController(){
		expenseSubmissionService = new ExpenseSubmissionService();
		expenseSubmissionResponse = new ExpenseSubmissionResponse();
	}
	
	@RequestMapping(value = "/submitExpense", method=RequestMethod.POST)
	public ExpenseSubmissionResponse expenseSubmission(
			@RequestParam(value="email") String email,
			@RequestParam(value="price") double price,
			@RequestParam(value="currency") String currency,
			@RequestParam(value="category") String category,
			@RequestParam(value="date") String date,
			@RequestParam(value="description") String description,
			@RequestParam(value="expenseimage") String expenseImageData,
			@RequestParam(value="approved") boolean approved){
		try {
			b = Base64Utils.decodeFromString(expenseImageData);
			decompressedImage = CompressionUtils.decompress(b);
			b = null;
			expense = new Expense(email, price, currency, category, date, description, decompressedImage, approved);
			decompressedImage = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			expenseSubmissionResponse.appendMessage(e.getMessage());
			return expenseSubmissionResponse;
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			expenseSubmissionResponse.appendMessage(e.getMessage());
			return expenseSubmissionResponse;
		} catch(NullPointerException e){
			e.printStackTrace();
			expenseSubmissionResponse.appendMessage(e.getMessage());
			return expenseSubmissionResponse;
		}
		
		expenseSubmissionResponse = expenseSubmissionService.submitExpense(expense);
		expenseSubmissionResponse.appendMessage("Made it past the controller.");
		expenseSubmissionResponse.appendMessage("byte length after submiss, base64 decoding, and decompression: " + expense.getExpenseImageData().length);
		expense = null;
		return expenseSubmissionResponse;
		}
}
