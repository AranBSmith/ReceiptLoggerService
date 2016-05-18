package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.Expense;
import model.ExpenseRetrievalResponse;
import services.CompressionUtils;
import services.ExpenseRetrievalService;

/**
 * Rest Controller used to retrieve expenses by various different means.
 * 
 * @author Aran
 *
 */
@Data
@RestController
public class RetrieveExpenseController {
	
	ExpenseRetrievalService expenseRetrievalService;
	
	/**
	 * Initialises an instance of the ExpenseRetrievalService used for
	 * retrieving expense information from the DB and the filesystem.
	 */
	public RetrieveExpenseController(){
		this.expenseRetrievalService = new ExpenseRetrievalService();
	}
	
	/**
	 * Used to get expenses according to a user's email, given that the credentials
	 * are valid.
	 * 
	 * @param email A valid user email.
	 * @param password A valid user's password.
	 * @return ExpenseRetrievalResponse containing all of the user's expenses,
	 * if the credentials submitted were valid. Will not return the user's 
	 * expenses if the credentials were wrong. Will return exceptions in the 
	 * webservice if something went wrong upon trying to retrieve user expenses.
	 */
	@RequestMapping(value="/userExpenseRetrieval", method=RequestMethod.POST)
	public ExpenseRetrievalResponse userExpenseRetrieval(
			@RequestParam("email") String email,
			@RequestParam("password") String password
			){
		
		return expenseRetrievalService.getUserExpenses(email, password);
	}
	
	
	@RequestMapping(value="retrieveExpensesByEmail", method=RequestMethod.POST)
	public ExpenseRetrievalResponse retrieveExpenseByEmail(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("targetEmail") String targetEmail
			){
		
		return null;
	}
	
	@RequestMapping(value="/retrieveExpensesByID", method=RequestMethod.POST)
	public ExpenseRetrievalResponse retrieveExpenseById(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("recordID") int recordID
			
			){
		
		ExpenseRetrievalResponse expenseRetrievalResponse = expenseRetrievalService.getUserExpenseByID(email, password, recordID);
		byte[] expenseImageData = expenseRetrievalResponse.getExpenses().element().getExpenseImageData();
		
		// compress and convert to base64
		
		try {
			expenseImageData = CompressionUtils.compress(expenseImageData);
		} catch (IOException e) {
			e.printStackTrace();
			expenseRetrievalResponse.appendMessage(e.getMessage());
			return expenseRetrievalResponse;
		}
		
		String converted = Base64Utils.encodeToString(expenseImageData);
		
		expenseRetrievalResponse.addCompressedData(converted);
		
		Expense expense = expenseRetrievalResponse.getExpenses().getFirst();
		expense.setExpenseImageData(null);
		LinkedList<Expense> expenses = new LinkedList<>();
		expenses.push(expense);
		
		expenseRetrievalResponse.setExpenses(expenses);
		
		expenseImageData = null;
		expense = null;
		expenses = null;
		
		return expenseRetrievalResponse;
	}
	
	@RequestMapping(value="retrieveAllExpenses", method=RequestMethod.POST)
	public ExpenseRetrievalResponse retrieveAllExpenses(
			@RequestParam("email") String email,
			@RequestParam("password") String password
			){
		
		return null;
	}
	
	@RequestMapping(value="retrieveExpensesByDate", method=RequestMethod.POST)
	public ExpenseRetrievalResponse retrieveExpenseByDate(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("date") Date date
			){
		
		return null;
	}
	
	@RequestMapping(value="retrieveExpensesAfterDate", method=RequestMethod.POST)
	public ExpenseRetrievalResponse retrieveExpenseAfterDate(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("date") Date date
			){
		
		return null;
	}
}
