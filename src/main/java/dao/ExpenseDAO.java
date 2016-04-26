package dao;

import lombok.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.Expense;
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

	private static void setId(int id) {
		ExpenseDAO.id = id;
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

	

}
