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

/**
 * Expense Data Access Object Class. Manipulates the Expense table in the data
 * base.
 * 
 * @author Aran
 *
 */
public class ExpenseDAO extends DAO {
	private DataSource dataSource;
	private ExpenseSubmissionResponse expenseSubResponse;
	private CancelExpenseResponse cancelExpenseResponse;
	private FileSystemDAO fileSystemDAO;
	private static int id;
	
	/**
	 * Gets the details regarding the systems MySql database.
	 */
	public ExpenseDAO(){
		this.dataSource = super.getMySQLDataSource();
		generateExpenseID();
	}
	
	/**
	 * Gets the most recently used ID in the expense table.
	 * 
	 * @return the latest integer unique identifier.
	 */
	public int getId() {
		return id;
	}

	private void setId(int tid) {
		id = tid;
	}
	
	/**
	 * Gets an identifier value that this class will use to label new expenses.
	 * Required in case there are already expenses in the database, upon deployment
	 * we don't want to over write any pre-existing Expenses.
	 */
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
	
	/**
	 * Used to write expense information into the expense table of the database.
	 * 
	 * @param expense
	 * @return ExpenseSubmissionResponse object which specifying the status of the 
	 * expense submission, and any exceptions thrown in the process of submitting 
	 * an expense.
	 */
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

	
	/**
	 * Retrieves expenses from the database based on the email provided, this
	 * class also gets the expense descriptions and image data from the file
	 * system.
	 * 
	 * @param email
	 * @return ExpenseRetrievalResponse containing the status of the request, 
	 * if successful this object will contain all the expenses corresponding 
	 * to the email. If any exceptions were thrown during when getting the
	 * user's expenses, they will be found the in the response.
	 */
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
				String card = resultSet.getString("card");
				
				// get description based off id.
				try {
					fileSystemDAO = new FileSystemDAO();
					description = fileSystemDAO.readExpenseDescription(id-1);
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
	
	public ExpenseRetrievalResponse getExpenseByID(int expenseID){
		if(expenseID <= id){
			ExpenseRetrievalResponse expenseRetrievalResponse = new ExpenseRetrievalResponse();
			String sql = "select * from Expenses where id = ?";
			Connection conn = null;
			
			try {
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, expenseID);
				ResultSet resultSet = ps.executeQuery();
				
				Expense expense = null;
				
				if(resultSet.next()){
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
						description = fileSystemDAO.readExpenseDescription(expenseID-1);
						fileSystemDAO = null;
					} catch (IOException e) {
						e.printStackTrace();
						expenseRetrievalResponse.appendMessage(e.getMessage());
					}
					
					byte[] expenseImageData = null;
					
					try {
						fileSystemDAO = new FileSystemDAO();
						expenseImageData = fileSystemDAO.readExpenseImageData(expenseID-1);
						fileSystemDAO = null;
					} catch (IOException e) {
						e.printStackTrace();
						expenseRetrievalResponse.appendMessage(e.getMessage());
					}
					
					expense = new Expense(expenseID, "", price, currency, card, category, expenseDate, description, expenseImageData, approved);
					expenseRetrievalResponse.addExpense(expense);
					
					expense = null;
					expenseImageData = null;
				}
				
			} catch(SQLException e){
				expenseRetrievalResponse.appendMessage(e.getMessage());
				e.printStackTrace();
				return expenseRetrievalResponse;
			}
			
			expenseRetrievalResponse.setSuccess();
			return expenseRetrievalResponse;
		}
		
		ExpenseRetrievalResponse expenseRetrievalResponse = new ExpenseRetrievalResponse();
		expenseRetrievalResponse.appendMessage("Invalid identifier");
		return expenseRetrievalResponse;
	}
	
	/**
	 * Remove an expense based on the ID provided from the expense table 
	 * in the MySQL database.
	 * 
	 * @param expenseID
	 * @return CancelExpenseResponse containing the status of the request,
	 * as well as exceptions thrown during this process.
	 */
	public CancelExpenseResponse removeExpense(int expenseID) {
		String sql = "DELETE FROM Expenses WHERE id = ?";
		Connection conn = null;
		CancelExpenseResponse cancelExpenseResponse = new CancelExpenseResponse();
		
		try{
			 // if(fileSystemDAO.delete(expenseID)){
				cancelExpenseResponse.appendMessage("successfully deleted files from system");
				
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, expenseID+1);
				int count = ps.executeUpdate();
				if(count > 0) cancelExpenseResponse.setSuccess();
				else cancelExpenseResponse.appendMessage("deletion failed");
				
				ps.close();
				
				return cancelExpenseResponse;
			/*} else {
				cancelExpenseResponse.appendMessage("there was an issue with deleting files from the filesystem");
				return cancelExpenseResponse;
			}*/
		} catch(SQLException e){
			 e.printStackTrace();
			 cancelExpenseResponse.appendMessage(e.getMessage());
			 return cancelExpenseResponse;
		}
	}
}
