package dao;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

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
		assertTrue(expenseDAO.insertExpense(expense));
	}
	
	@Test
	public void testSelection(){
        
		DataSource ds = new ExpenseDAO().getMySQLDataSource();
		
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from Users");
            while(rs.next()){
                System.out.println(rs.getString(1)+ " " + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
                try {
                    if(rs != null) rs.close();
                    if(stmt != null) stmt.close();
                    if(con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}
