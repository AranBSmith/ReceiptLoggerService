package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.Expense;
import model.ExpenseSubmissionResponse;

public class ExpenseDAO extends DAO {
	private DataSource dataSource;
	
	public ExpenseDAO(){
		this.dataSource = super.getMySQLDataSource();
	}
	
	public ExpenseSubmissionResponse insertExpense(Expense expense) {
		ExpenseSubmissionResponse expenseSubResponse = new ExpenseSubmissionResponse();
		
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
