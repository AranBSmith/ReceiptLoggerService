package controllers;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.Expense;
import model.ExpenseRetrievalResponse;
import services.ExpenseRetrievalService;

@Data
@RestController
public class RetrieveExpenseController {
	
	ExpenseRetrievalService expenseRetrievalService;
	
	public RetrieveExpenseController(){
		this.expenseRetrievalService = new ExpenseRetrievalService();
	}
	
	@RequestMapping(value="userExpenseRetrieval", method=RequestMethod.POST)
	public ExpenseRetrievalResponse userExpenseRetrieval(
			@RequestParam("email") String email,
			@RequestParam("password") String password
			){
		
		return expenseRetrievalService.getUserExpenses(email, password);
	}
	
	
	@RequestMapping(value="retrieveExpensesByEmail", method=RequestMethod.POST)
	public LinkedList<Expense> retrieveExpenseByEmail(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("targetEmail") String targetEmail
			){
		
		return null;
	}
	
	@RequestMapping(value="retrieveExpensesByID", method=RequestMethod.POST)
	public LinkedList<Expense> retrieveExpenseById(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("recordId") String recordId
			){
		
		return null;
	}
	
	@RequestMapping(value="retrieveAllExpenses", method=RequestMethod.POST)
	public LinkedList<Expense> retrieveAllExpenses(
			@RequestParam("email") String email,
			@RequestParam("password") String password
			){
		
		return null;
	}
	
	@RequestMapping(value="retrieveExpensesByDate", method=RequestMethod.POST)
	public LinkedList<Expense> retrieveExpenseByDate(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("date") Date date
			){
		
		return null;
	}
	
	@RequestMapping(value="retrieveExpensesAfterDate", method=RequestMethod.POST)
	public LinkedList<Expense> retrieveExpenseAfterDate(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("date") Date date
			){
		
		return null;
	}
}
