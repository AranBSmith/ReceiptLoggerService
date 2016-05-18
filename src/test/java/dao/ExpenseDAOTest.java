package dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.apache.hadoop.mapred.gethistory_jsp;
import org.junit.Before;
import org.junit.Test;

import model.Expense;
import model.ExpenseRetrievalResponse;

public class ExpenseDAOTest {
	ExpenseDAO expenseDAO;
	Expense expense, expenseForImage;
	String email, date, currency, category, description, card;
	byte[] expenseImageData;
	boolean approved;
	double price;
	int id;
	
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
		id = 1;
		expense = 
				new Expense(email, price, currency, card, category, date, description, expenseImageData, approved);

		
		
	}
	
	@Test
	public void testInsertion(){
		assertTrue(expenseDAO.insertExpense(expense).isSuccess());
	}
	
	/*@Test
	public void testRetrievalByID(){
		ExpenseRetrievalResponse expenseRetrievalResponse = expenseDAO.getExpenseByID(id);
		LinkedList<Expense> expenses = expenseRetrievalResponse.getExpenses();
		assertTrue(expenses.size() == 1);
		assertNotNull(expenses.get(0).getExpenseImageData());
	}*/
}
