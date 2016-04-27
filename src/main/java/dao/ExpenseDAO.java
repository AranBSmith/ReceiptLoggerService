package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import lombok.Data;
import model.Expense;
import model.ExpenseRetrievalResponse;
import model.ExpenseSubmissionResponse;

@Data
public class ExpenseDAO extends DAO {
	private DataSource dataSource;
	private ExpenseSubmissionResponse expenseSubResponse;
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
		if(id == 0) id = 1;
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
				setId(1);
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
				"(email, price, expenseDate, currency, category_fk, "
				+ "imageDirectory, descriptionDirectory, approval) "
				+ "VALUES (?, ?, STR_TO_DATE(?, '%m/%d/%Y'), ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		int count = 0;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, expense.getEmail());
			ps.setDouble(2, expense.getPrice());
			ps.setString(3, expense.getDate());
			ps.setString(4, expense.getCurrency());
			ps.setString(5, expense.getCategory());
			ps.setString(6, "somedirectoryforimages");
			ps.setString(7, "somedirectoryfortextfiles");
			ps.setBoolean(8, expense.isApproved());
			count = ps.executeUpdate();
			ps.close();
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
				int id = resultSet.getInt("id");
				boolean approved = resultSet.getBoolean("approval");
				double price = resultSet.getDouble("price");
				String expenseDate = resultSet.getString("expenseDate");
				String currency = resultSet.getString("currency");
				String category = resultSet.getString("category_fk");
				String description = "";
				
				// get description based off id.
				try {
					description = readTextFile(id);
				} catch (IOException e) {
					e.printStackTrace();
					expenseRetrievalResponse.appendMessage(e.getMessage());
				}
				expense = new Expense(email, price, currency, category, expenseDate, description, null, approved);
				
				// add this expense to the response
				expenseRetrievalResponse.addExpense(expense);
			}
			
		} catch(SQLException e){
			expenseRetrievalResponse.appendMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return expenseRetrievalResponse;
	}
	
	private String readTextFile(int id) throws IOException {
		File file = new File("/var/lib/ReceiptLogger/descriptions/" + id + ".txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String everything = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		} finally {
		    br.close();
		}
		return everything;
	}
}
