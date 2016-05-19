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

/**
 * Rest Controller required for submitting expenses to the Receipt Logger
 * system.
 * 
 * @author Aran
 *
 */
@RestController
@Data
public class SubmitExpenseController {
	private ExpenseSubmissionService expenseSubmissionService;
	private ExpenseSubmissionResponse expenseSubmissionResponse;
	private Expense expense;
	byte[] b, decompressedImage;
	
	/**
	 * Creates an instance of the ExpenseSubmissionService class required for
	 * submitting and storing expenses. It will also create its own 
	 * ExpenseSubmissionResponse object for any exceptions that may occur
	 * within this class.
	 */
	public SubmitExpenseController(){
		expenseSubmissionService = new ExpenseSubmissionService();
		expenseSubmissionResponse = new ExpenseSubmissionResponse();
	}
	
	/**
	 * Upon posting to /submitExpense with the parameters specified this
	 * function will firstly convert the base64 expenseImageData string
	 * to bytes, and then uncompress that data using the CompressionUtils
	 * service decompress function. 
	 * 
	 * @param email A user's email.
	 * @param price double Price of expense.
	 * @param currency A string of length 3.
	 * @param card A string of length 4.
	 * @param category Category selected for the expense.
	 * @param date Date of the expense in the format ...
	 * @param description Description of the expense.
	 * @param expenseImageData A base64 string representing a compressed PNG 
	 * image byte array.
	 * @param approved A boolean indicating whether the expense is approved(true)
	 * or not (false)
	 * @return ExpenseSubmissionRespons object specifying the status of the 
	 * submission, if it was unsuccessful the response will contain the messages
	 * of any exceptions thrown upon trying to submit user credentials.
	 */
	@RequestMapping(value = "/submitExpense", method=RequestMethod.POST)
	public ExpenseSubmissionResponse expenseSubmission(
			@RequestParam(value="email") String email,
			@RequestParam(value="password") String password,
			@RequestParam(value="price") double price,
			@RequestParam(value="currency") String currency,
			@RequestParam(value="card") String card,
			@RequestParam(value="category") String category,
			@RequestParam(value="date") String date,
			@RequestParam(value="description") String description,
			@RequestParam(value="expenseimage") String expenseImageData,
			@RequestParam(value="approved") boolean approved){
			
			try {
				// convert to bytes
				b = Base64Utils.decodeFromString(expenseImageData);
				// decompress bytes
				decompressedImage = CompressionUtils.decompress(b);
				// unallocate un-needed byte reference
				b = null;
				// prepare an expense object for submission to the expense submission service
				expense = new Expense(email, price, currency, card, category, date, description, decompressedImage, approved);
				// de reference this
				decompressedImage = null;
			} catch (IOException e) {
				e.printStackTrace();
				expenseSubmissionResponse.appendMessage(e.getMessage());
				return expenseSubmissionResponse;
			} catch (DataFormatException e) {
				e.printStackTrace();
				expenseSubmissionResponse.appendMessage(e.getMessage());
				return expenseSubmissionResponse;
			} catch(NullPointerException e) {
				e.printStackTrace();
				expenseSubmissionResponse.appendMessage(e.getMessage());
				return expenseSubmissionResponse;
			}
			
			expenseSubmissionResponse = expenseSubmissionService.submitExpense(expense, password);
			expenseSubmissionResponse.appendMessage("Made it past the controller.");
			expenseSubmissionResponse.appendMessage("byte length after submiss, base64 decoding, and decompression: " + expense.getExpenseImageData().length);
			// dereference this
			expense = null;
			return expenseSubmissionResponse;
		}
}
