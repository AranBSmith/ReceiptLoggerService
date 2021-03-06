package controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import model.Expense;
import model.ExpenseSubmissionResponse;
import services.ExpenseSubmissionService;

public class ExpenseSubmissionControllerTest {
	SubmitExpenseController controller;
	ExpenseSubmissionResponse expenseSubmissionResponse, validExpenseSubmissionResponse, invalidExpenseSubmissionResponse;
	ExpenseSubmissionService expenseSubmissionService;
	Expense validExpense, invalidExpense, nullExpense;
	
	String email, date, currency, category, description, dataAsString, card, password;
	byte[] expenseImageData;
	boolean approved;
	double price;
	
	@Before
	public void setup(){
		controller = new SubmitExpenseController();
		
		email = "aran.smith47@mail.dcu.ie";
		password = "apassword";
		
		price = 100.00;
		date = "01/01/2016";
		currency = "usd";
		card = "1234";
		category = "Dinner";
		description = "A description of this expense";
		dataAsString = "Random";
		expenseImageData = "Random".getBytes();
		approved = false;
		
		validExpense = new Expense(email, price, currency, card, category, date, description, expenseImageData, approved);
		invalidExpense = new Expense(email, price, null, card, category, date, description, null, approved);
		// nullExpense = new Expense(null, null, null, null, null, null, null, null);
		
		validExpenseSubmissionResponse = new ExpenseSubmissionResponse();
		validExpenseSubmissionResponse.setSuccess();
		
		invalidExpenseSubmissionResponse = new ExpenseSubmissionResponse();
		
		expenseSubmissionService = mock(ExpenseSubmissionService.class);
		when(expenseSubmissionService.submitExpense(validExpense, password)).thenReturn(validExpenseSubmissionResponse);
		when(expenseSubmissionService.submitExpense(invalidExpense, password)).thenReturn(invalidExpenseSubmissionResponse);
		// when(expenseSubmissionService.submitExpense(nullExpense)).thenReturn(invalidResponse);
		
		controller.setExpenseSubmissionService(expenseSubmissionService);
	}
	
	@Test
	public void testValidExpenseSubmission(){
		expenseSubmissionResponse = controller.expenseSubmission(email, password, price, currency, card, category, date, description, dataAsString, approved);
		assertTrue(validExpenseSubmissionResponse.isSuccess());
	}
	
	@Test
	public void testInvalidSubmission(){
		expenseSubmissionResponse = controller.expenseSubmission(email, password, price, null, card, category, date, description, null, approved);
		assertFalse(expenseSubmissionResponse.isSuccess());
	}
	
	/*@Test
	public void testNullSubmission(){
		expenseSubmissionResponse = controller.expenseSubmission(null, null, null, null, null, null, null, null);
		assertFalse(validExpenseSubmissionResponse.isSuccess());
	}*/
}
