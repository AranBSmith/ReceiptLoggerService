package services;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import model.ExpenseRetrievalResponse;

public class ExpenseRetrievalServiceTest {
	ExpenseRetrievalService expenseRetrievalService;
	
	@Before
	public void setup(){
		expenseRetrievalService = new ExpenseRetrievalService();
	}
	
	@Test
	public void testExpenseRetrieval(){
		ExpenseRetrievalResponse expenseRetrievalResponse = expenseRetrievalService.getUserExpenses("aran.smith47@mail.dcu.ie", "apassword");
		System.out.println(expenseRetrievalResponse.getResponse());
		assertNotNull(expenseRetrievalResponse);
	}
}
