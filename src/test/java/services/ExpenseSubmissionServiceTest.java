package services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dao.ExpenseDAO;
import model.Expense;
import model.ExpenseSubmissionResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpenseSubmissionServiceTest {
	
	ExpenseSubmissionService expenseSubmissionService;
	ExpenseDAO expenseDAO;
	Expense expense, invalidExpense;
	ExpenseSubmissionResponse expenseSubResponse, validExpenseSubmissionResponse, invalidExpenseSubmissionResponse;
	
	String email, category, currency, date, description;
	Boolean approved;
	Double price;
	byte[] expenseImageData;
	
	@Before
	public void setup(){
		
		email = "aran.smith47@mail.dcu.ie";
		category = "taxi";
		currency = "eur";
		date = "01/10/2016";
		description = "Travel fare when I hailed a taxi.";
		expenseImageData = "Random".getBytes();
		approved = false;
		price = 23.00;
		
		expense = new Expense(email, price, currency, category, date, description, expenseImageData, approved);
		invalidExpense = new Expense(email, price, currency, category, date, description, expenseImageData, approved);
		
		invalidExpense.setEmail(null);
		invalidExpense.setExpenseImageData(null);
		
		validExpenseSubmissionResponse = new ExpenseSubmissionResponse();
		validExpenseSubmissionResponse.setSuccess();
		
		invalidExpenseSubmissionResponse = new ExpenseSubmissionResponse();
		
		expenseDAO = mock(ExpenseDAO.class);
		when(expenseDAO.insertExpense(null)).thenReturn(invalidExpenseSubmissionResponse);
		when(expenseDAO.insertExpense(expense)).thenReturn(validExpenseSubmissionResponse);
		when(expenseDAO.insertExpense(invalidExpense)).thenReturn(invalidExpenseSubmissionResponse);
		
		expenseSubmissionService = new ExpenseSubmissionService();
		expenseSubmissionService.setExpenseDAO(expenseDAO);
	}
	
	@Test
	public void testValidExpenseSubmission(){
		expenseSubResponse = expenseSubmissionService.submitExpense(expense);
		assertTrue(expenseSubResponse.isSuccess());
	}
	
	@Test
	public void testInvalidExpenseSubmission(){
		expenseSubResponse = expenseSubmissionService.submitExpense(invalidExpense);
		assertFalse(expenseSubResponse.isSuccess());
	}
	
	@Test
	public void testNullSubmission(){
		expenseSubResponse = expenseSubmissionService.submitExpense(null);
		assertFalse(expenseSubResponse.isSuccess());
	}
}
