package controllers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.Expense;
import model.ExpenseSubmissionResponse;
import services.ExpenseSubmissionService;

@RestController
@Data
public class SubmitExpenseController {
	private ExpenseSubmissionService expenseSubmissionService;
	private Expense expense;
	byte[] b;
	
	public SubmitExpenseController(){
		expenseSubmissionService = new ExpenseSubmissionService();
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
		try{
			b = expenseImageData.getBytes("ISO-8859-1");
		} catch(NullPointerException e){
			return new ExpenseSubmissionResponse();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			expense = new Expense(email, price, currency, category, date, description, b, approved);
				
		return expenseSubmissionService.submitExpense(expense);
	}
}
