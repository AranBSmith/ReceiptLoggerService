package model;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class ExpenseTest {

	String email, category, currency, date, description;
	Boolean approved;
	Double price;
	byte[] expenseImageData;
	
	Expense expense;
	
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
	}
	
	@Test
	public void testContainsNoNullField(){
		assertFalse(expense.containsNullField());
	}
	
	@Test
	public void testContainsNullField(){
		expense.setExpenseImageData(null);
		assertTrue(expense.containsNullField());
	}
	
	@Test
	public void testContainsNoInvalidField(){
		assertFalse(expense.containsInvalidValue());
	}
	
	@Test
	public void testContainsInvalidField(){
		// TODO
	}
}
