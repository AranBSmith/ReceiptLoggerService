package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Expense;
import model.ExpenseSubmissionResponse;
import services.ExpenseSubmissionService;

@RestController
public class SubmitExpenseController {
	
	ExpenseSubmissionService expenseSubmissionService;
	Expense expense;
	
	@RequestMapping(value = "/submitExpense", method=RequestMethod.POST)
	public ExpenseSubmissionResponse expenseSubmission(
			@RequestParam(value="email") String email,
			@RequestParam(value="price") int price,
			@RequestParam(value="currency") String currency,
			@RequestParam(value="category") String category,
			@RequestParam(value="date") String date,
			@RequestParam(value="description") String description,
			@RequestParam(value="expenseimage") byte[] expenseImageData,
			@RequestParam(value="approved") boolean approved){
		
		expense = new Expense(email, price, currency, category, date, description, expenseImageData, approved);
		
		ExpenseSubmissionResponse expenseSubmissionResponse = new ExpenseSubmissionResponse();
		
		if(expenseSubmissionService.submitExpense(expense)) {
			expenseSubmissionResponse.setResponse("valid");
		} else {
			expenseSubmissionResponse.setResponse("invalid");
		}
		return expenseSubmissionResponse;
	}
}
