package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.Expense;

public class ExpenseDAO extends DAO {

	private DataSource dataSource;
	
	public ExpenseDAO(){
		this.dataSource = super.getMySQLDataSource();
	}
	
	public boolean insertExpense(Expense expense) {
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
			throw new RuntimeException(e);
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(count > 0)
					return true;
			} 
		}
		return false;
	}

}
