package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.CancelExpenseResponse;
import model.Expense;
import model.ExpenseRetrievalResponse;
import model.ExpenseSubmissionResponse;

public class ExpenseDAO extends DAO {
	private DataSource dataSource;
	private ExpenseSubmissionResponse expenseSubResponse;
	private CancelExpenseResponse cancelExpenseResponse;
	private FileSystemDAO fileSystemDAO;
	private static int id;
	
	public ExpenseDAO(){
		this.dataSource = super.getMySQLDataSource();
		generateExpenseID();
	}
	
	public int getId() {
		return id;
	}

	private void setId(int tid) {
		id = tid;
	}
	
	// assigns ID a value, which is used to identify expenses on the file system
	public void generateExpenseID(){
		// SELECT TOP 1 column_name FROM table_name ORDER BY column_name DESC;
		expenseSubResponse = new ExpenseSubmissionResponse();
		
		String sql = "SELECT TOP 1 id FROM Expenses ORDER BY id DESC";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()){
				setId(resultSet.getInt("id"));
			} 
			// there is no result, so this is an empty table
			else {
				setId(0);
			}
			ps.close();
		} catch (SQLException e) {
			expenseSubResponse.appendMessage(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					expenseSubResponse.appendMessage(e.getMessage());
				}
			}
		}
	}
	
	public ExpenseSubmissionResponse insertExpense(Expense expense) {
		expenseSubResponse = new ExpenseSubmissionResponse();
		
		String sql = "INSERT INTO Expenses " +
				"(email, price, expenseDate, currency, card, category_fk, "
				+ "imageDirectory, descriptionDirectory, approval) "
				+ "VALUES (?, ?, STR_TO_DATE(?, '%m/%d/%Y'), ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		int count = 0;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, expense.getEmail());
			ps.setDouble(2, expense.getPrice());
			ps.setString(3, expense.getDate());
			ps.setString(4, expense.getCurrency());
			ps.setString(5, expense.getCard());
			ps.setString(6, expense.getCategory());
			ps.setString(7, "somedirectoryforimages");
			ps.setString(8, "somedirectoryfortextfiles");
			ps.setBoolean(9, expense.isApproved());
			count = ps.executeUpdate();
			ps.close();
			expenseSubResponse.setId(id);
			id++;
			
		} catch (SQLException e) {
			expenseSubResponse.appendMessage(e.getMessage());
			
		} finally {
			expense = null;
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					expenseSubResponse.appendMessage(e.getMessage());
				}
				if(count > 0){
					expenseSubResponse.setSuccess();
				}
			} 
		}
		
		expenseSubResponse.appendMessage("Made it past the dao.");
		return expenseSubResponse;
	}

	
	public ExpenseRetrievalResponse getAllExpensesByEmail(String email) {
		
		ExpenseRetrievalResponse expenseRetrievalResponse = new ExpenseRetrievalResponse();
		String sql = "select * from Expenses where email = ?";
		Connection conn = null;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet resultSet = ps.executeQuery();
			
			Expense expense = null;
			// while we there is another expense from the query, add it to the contained within the response
			while(resultSet.next()){
				int id = resultSet.getInt("id")-1;
				boolean approved = resultSet.getBoolean("approval");
				double price = resultSet.getDouble("price");
				String expenseDate = resultSet.getString("expenseDate");
				String currency = resultSet.getString("currency");
				String category = resultSet.getString("category_fk");
				String description = "";
				String card = resultSet.getString("card");
				
				// get description based off id.
				try {
					fileSystemDAO = new FileSystemDAO();
					description = fileSystemDAO.readExpenseDescription(id);
					fileSystemDAO = null;
				} catch (IOException e) {
					e.printStackTrace();
					expenseRetrievalResponse.appendMessage(e.getMessage());
				}
				expense = new Expense(id, email, price, currency, card, category, expenseDate, description, null, approved);
				
				// add this expense to the response
				expenseRetrievalResponse.addExpense(expense);
			}
			
		} catch(SQLException e){
			expenseRetrievalResponse.appendMessage(e.getMessage());
			e.printStackTrace();
			return expenseRetrievalResponse;
		}
		
		expenseRetrievalResponse.setSuccess();
		return expenseRetrievalResponse;
	}
	
	public CancelExpenseResponse removeExpense(int expenseID) {
		String sql = "DELETE FROM Expenses WHERE id = ?";
		Connection conn = null;
		cancelExpenseResponse = new CancelExpenseResponse();
		
		try{
			if(fileSystemDAO.delete(expenseID)){
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, expenseID+1);
				ps.executeUpdate();
				cancelExpenseResponse.setSuccess();
				return cancelExpenseResponse;
			} else {
				cancelExpenseResponse.appendMessage("there was an issue with deleting files from the filesystem");
				return cancelExpenseResponse;
			}
		} catch(SQLException e){
			 e.printStackTrace();
			 cancelExpenseResponse.appendMessage(e.getMessage());
			 return cancelExpenseResponse;
		}
	}
}
