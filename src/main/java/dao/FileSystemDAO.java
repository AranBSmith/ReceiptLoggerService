package dao;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Data Access Object class for accessing and deleting files and data stored on
 * the filesystem.
 * 
 * @author Aran
 *
 */
public class FileSystemDAO {
	
	public FileSystemDAO(){}
	
	/**
	 * takes a byte array and uses it to write a PNG image to the filesystem 
	 * under /images/
	 * 
	 * @param expenseImageData
	 * @param id
	 * @return true if successful write
	 */
	public boolean writeExpenseImageData(byte[] expenseImageData, int id){
		try{
			File imageFilePath = new File("/var/lib/ReceiptLogger/images/" + id + ".png");
			BufferedImage writeImage = ImageIO.read(new ByteArrayInputStream(expenseImageData));
			ImageIO.write(writeImage, "png", imageFilePath);
			expenseImageData = null;
		} catch(IOException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * takes a String and uses it to write a txt file to the filesystem 
	 * under /descriptions/
	 * 
	 * @param description
	 * @param id
	 * @return true if successful write
	 */
	public boolean writeExpenseDescription(String description, int id){		
		try{
			File descriptionFilePath = new File("/var/lib/ReceiptLogger/descriptions/" + id + ".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(descriptionFilePath));
			writer.write(description);
			writer.close();
		} catch(IOException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public String readExpenseImageData(int id){
		
		return "";
	}
	
	/**
	 * reads a text file containing an expense description identified by the 
	 * passed unique identifier
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public String readExpenseDescription(int id) throws IOException{
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
	
	/**
	 * deletes an expense image and description file corresponding to the passed
	 * unique identifier.
	 * 
	 * @param id
	 * @return true if successful, otherwise false
	 */
	public boolean delete(int id){
		if(deleteExpenseDescription(id) && deleteExpenseImageData(id)){
			return true;
		} else return false;
	}
	
	/**
	 * deletes an image on the filesystem under /images/
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteExpenseImageData(int id){
		File file = new File("/var/lib/ReceiptLogger/images/" + id + ".png");
		if(file.delete()){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * deletes description text file on the filesystem under /descriptions/
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteExpenseDescription(int id){
		File file = new File("/var/lib/ReceiptLogger/descriptions/" + id + ".png");
		if(file.delete()){
			return true;
		} else {
			return false;
		}
	}
}
