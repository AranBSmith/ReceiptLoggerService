package services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import dao.ExpenseDAO;
import model.Expense;
import model.ExpenseSubmissionResponse;

public class ExpenseSubmissionServiceTest {
	
	ExpenseSubmissionService expenseSubmissionService;
	ExpenseDAO expenseDAO;
	Expense expense, invalidExpense;
	ExpenseSubmissionResponse expenseSubResponse, validExpenseSubmissionResponse, invalidExpenseSubmissionResponse;
	
	String email, category, currency, date, description;
	Boolean approved;
	Double price;
	byte[] expenseImageData;
	
	File file;
	
	@Before
	public void setup(){
		
		email = "aran.smith47@mail.dcu.ie";
		category = "taxi";
		currency = "eur";
		date = "01/10/2016";
		description = "Travel fare when I hailed a taxi.";
		expenseImageData = "Random".getBytes();
		approved = false;
		price = 23.00;
		
		expense = new Expense(email, price, currency, category, date, description, expenseImageData, approved);
		invalidExpense = new Expense(email, price, currency, category, date, description, expenseImageData, approved);
		
		invalidExpense.setEmail(null);
		invalidExpense.setExpenseImageData(null);
		
		validExpenseSubmissionResponse = new ExpenseSubmissionResponse();
		validExpenseSubmissionResponse.setSuccess();
		
		invalidExpenseSubmissionResponse = new ExpenseSubmissionResponse();
		
		expenseDAO = mock(ExpenseDAO.class);
		when(expenseDAO.insertExpense(null)).thenReturn(invalidExpenseSubmissionResponse);
		when(expenseDAO.insertExpense(expense)).thenReturn(validExpenseSubmissionResponse);
		when(expenseDAO.insertExpense(invalidExpense)).thenReturn(invalidExpenseSubmissionResponse);
		
		expenseSubmissionService = new ExpenseSubmissionService();
		expenseSubmissionService.setExpenseDAO(expenseDAO);
		
	}
	
	@Test
	public void testValidExpenseSubmission(){
		expenseSubResponse = expenseSubmissionService.submitExpense(expense);
		assertTrue(expenseSubResponse.isSuccess());
	}
	
	@Test
	public void testInvalidExpenseSubmission(){
		expenseSubResponse = expenseSubmissionService.submitExpense(invalidExpense);
		assertFalse(expenseSubResponse.isSuccess());
	}
	
	@Test
	public void testNullSubmission(){
		expenseSubResponse = expenseSubmissionService.submitExpense(null);
		assertFalse(expenseSubResponse.isSuccess());
	}
	
	@Test
	public void testConversion(){
		try {
			// turn file to bytes 
			File file = new File("/usr/share/tomcat7/webapps/images/Lenna.png");
		    Image image = ImageIO.read(file);
		    BufferedImage bImage = toBufferedImage(image);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bImage, "png", baos);
			byte[] bytes = baos.toByteArray();
			
			// convert bytes to bufferedImage
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
		    File outputfile = new File("/usr/share/tomcat7/webapps/images/Test.png");
		    ImageIO.write(img,"jpg",outputfile);
		    		    
		} catch(Exception e){
			e.printStackTrace();
		}
		
	    assertTrue(new File("/usr/share/tomcat7/webapps/images/Test.png").exists());
	}
	
	private BufferedImage toBufferedImage(Image img) {
	    if (img instanceof BufferedImage) {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
