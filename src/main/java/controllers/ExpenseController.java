package controllers;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.ExpenseSubmissionResponse;

@RestController
public class ExpenseController {
	
	@RequestMapping("/submitExpense")
	public ExpenseSubmissionResponse expenseSubmission(
			@RequestParam(value="email") String email,
			@RequestParam(value="category") String category,
			@RequestParam(value="date") Date date){
		
		return null;
	}
}
