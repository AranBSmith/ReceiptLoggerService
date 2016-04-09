package dao;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Expense;

public class ExpenseDAOTest {
	ExpenseDAO expenseDAO;
	Expense expense;
	String email, date, currency, category, description;
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
		description = "A description of this expense";
		expenseImageData = null;
		approved = false;
		expense = 
				new Expense(email, price, currency, category, date, description, expenseImageData, approved);
	}
	
	@Test
	public void testInsertion(){
		assertTrue(expenseDAO.insertExpense(expense).isSuccess());
	}
}
