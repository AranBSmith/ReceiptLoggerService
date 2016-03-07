package controllers;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Expense;
import model.ExpenseSubmissionResponse;
import services.ExpenseSubmissionService;

@RestController
public class ExpenseController {
	
	ExpenseSubmissionService expenseSubmissionService;
	Expense expense;
	
	@RequestMapping("/submitExpense")
	public ExpenseSubmissionResponse expenseSubmission(
			@RequestParam(value="email") String email,
			@RequestParam(value="category") String category,
			@RequestParam(value="date") Date date,
			@RequestParam(value="expenseimage") byte[] expenseImageData){
		
		expense = new Expense(email, category, date, expenseImageData);
		
		return expenseSubmissionService.submitExpense(expense);
	}
}
