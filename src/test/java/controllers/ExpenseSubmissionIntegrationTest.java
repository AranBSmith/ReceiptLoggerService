package controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.ExpenseSubmissionResponse;

public class ExpenseSubmissionIntegrationTest {
	
	SubmitExpenseController controller;
	ExpenseSubmissionResponse expenseSubmissionResponse;
	
	String email, date, currency, category, description;
	byte[] expenseImageData;
	boolean approved;
	double price;
	
	@Before
	public void setup(){
		controller = new SubmitExpenseController();
		
		email = "aran.smith47@mail.dcu.ie";
		price = 100.00;
		date = "01/01/2016";
		currency = "usd";
		category = "Dinner";
		description = "A description of this expense";
		expenseImageData = "Random".getBytes();
		approved = false;
	}
	
	@Test
	public void testIntegratedValidExpenseSubmission(){
		expenseSubmissionResponse = controller.expenseSubmission(email, price, currency, category, date, description, expenseImageData, approved);
		assertTrue(expenseSubmissionResponse.isSuccess());
	}
	
	@Test
	public void testIntegratedInvalidSubmission(){
		expenseSubmissionResponse = controller.expenseSubmission(null, price, currency, category, date, description, null, approved);
		assertFalse(expenseSubmissionResponse.isSuccess());
	}
	
	/*@Test
	public void testNullSubmission(){
		expenseSubmissionResponse = controller.expenseSubmission(null, null, null, null, null, null, null, null);
		assertFalse(expenseSubmissionResponse.isSuccess());
	}*/

}
