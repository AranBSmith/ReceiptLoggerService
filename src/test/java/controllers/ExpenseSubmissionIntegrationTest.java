package controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import model.ExpenseSubmissionResponse;

public class ExpenseSubmissionIntegrationTest {
	
	
	
	
	SubmitExpenseController controller;
	ExpenseSubmissionResponse expenseSubmissionResponse;
	
	String email, date, currency, category, description;
	byte[] expenseImageData;
	boolean approved;
	double price;
	
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
		
		controller = new SubmitExpenseController();
		
		email = "aran.smith47@mail.dcu.ie";
		price = 100.00;
		date = "01/01/2016";
		currency = "usd";
		category = "Dinner";
		description = "A description of this expense";
		expenseImageData = "Random".getBytes();
		approved = false;
	}
	
	@Test
	public void testIntegratedValidExpenseSubmission(){
		expenseSubmissionResponse = controller.expenseSubmission(email, price, currency, category, date, description, expenseImageData, approved);
		assertTrue(expenseSubmissionResponse.isSuccess());
	}
	
	@Test
	public void testIntegratedInvalidSubmission(){
		expenseSubmissionResponse = controller.expenseSubmission(null, price, currency, category, date, description, null, approved);
		assertFalse(expenseSubmissionResponse.isSuccess());
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
	
	/*@Test
	public void testNullSubmission(){
		expenseSubmissionResponse = controller.expenseSubmission(null, null, null, null, null, null, null, null);
		assertFalse(expenseSubmissionResponse.isSuccess());
	}*/

}
