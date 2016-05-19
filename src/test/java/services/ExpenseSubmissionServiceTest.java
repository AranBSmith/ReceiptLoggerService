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
	
	String email, category, currency, date, description, card, password;
	Boolean approved;
	Double price;
	byte[] expenseImageData;
	Image image;
	
	File file;
	BufferedImage bImage;
	ByteArrayOutputStream baos;
	byte[] bytes;
	
	@Before
	public void setup(){
		
		file = new File("/usr/share/tomcat7/webapps/images/Lenna.png");
		
		try {
		    image = ImageIO.read(file);
		    bImage = toBufferedImage(image);
			baos = new ByteArrayOutputStream();
			ImageIO.write(bImage, "png", baos);
			bytes = baos.toByteArray();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		email = "aran.smith47@mail.dcu.ie";
		password = "apassword";
		
		category = "taxi";
		currency = "eur";
		card = "1234";
		date = "01/10/2016";
		description = "Travel fare when I hailed a taxi.";
		expenseImageData = "Random".getBytes();
		approved = false;
		price = 23.00;
		
		expense = new Expense(email, price, currency, card, category, date, description, bytes, approved);
		invalidExpense = new Expense(email, price, currency, card, category, date, description, expenseImageData, approved);
		
		invalidExpense.setEmail(null);
		invalidExpense.setExpenseImageData(null);
		
		validExpenseSubmissionResponse = new ExpenseSubmissionResponse();
		validExpenseSubmissionResponse.setSuccess();
		
		invalidExpenseSubmissionResponse = new ExpenseSubmissionResponse();
		
		expenseDAO = mock(ExpenseDAO.class);
		when(expenseDAO.insertExpense(null)).thenReturn(invalidExpenseSubmissionResponse);
		when(expenseDAO.insertExpense(expense)).thenReturn(validExpenseSubmissionResponse);
		when(expenseDAO.insertExpense(invalidExpense)).thenReturn(invalidExpenseSubmissionResponse);
		when(expenseDAO.getId()).thenReturn(0);
		
		expenseSubmissionService = new ExpenseSubmissionService();
		expenseSubmissionService.setExpenseDAO(expenseDAO);
	}
	
	@Test
	public void testValidExpenseSubmission(){
		expenseSubResponse = expenseSubmissionService.submitExpense(expense, password);
		System.out.println(expenseSubResponse.getResponse());
		assertTrue(expenseSubResponse.isSuccess());
	}
	
	@Test
	public void testInvalidExpenseSubmission(){
		expenseSubResponse = expenseSubmissionService.submitExpense(invalidExpense, password);
		assertFalse(expenseSubResponse.isSuccess());
	}
	
	@Test
	public void testNullSubmission(){
		expenseSubResponse = expenseSubmissionService.submitExpense(null, password);
		assertFalse(expenseSubResponse.isSuccess());
	}
	
	@Test
	public void testConversion(){
		try {
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
