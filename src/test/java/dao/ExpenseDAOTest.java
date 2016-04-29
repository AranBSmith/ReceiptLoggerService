package dao;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Expense;

public class ExpenseDAOTest {
	ExpenseDAO expenseDAO;
	Expense expense;
	String email, date, currency, category, description, card;
	byte[] expenseImageData;
	boolean approved;
	double price;
	
	@Before
	public void setup(){
		expenseDAO = new ExpenseDAO();
		
		email = "aran.smith47@mail.dcu.ie";
		price = 100.00;
		date = "01/01/2016";
		currency = "usd";
		category = "Dinner";
		card = "1234";
		description = "A description of this expense";
		expenseImageData = "Random".getBytes();
		approved = false;
		expense = 
				new Expense(email, price, currency, card, category, date, description, expenseImageData, approved);
	}
	
	@Test
	public void testInsertion(){
		assertTrue(expenseDAO.insertExpense(expense).isSuccess());
	}
}
