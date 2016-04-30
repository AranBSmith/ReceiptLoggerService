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

public class FileSystemDAO {
	
	public FileSystemDAO(){}
	
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
	
	public boolean delete(int id){
		if(deleteExpenseDescription(id) && deleteExpenseImageData(id)){
			return true;
		} else return false;
	}
	
	public boolean deleteExpenseImageData(int id){
		File file = new File("/var/lib/ReceiptLogger/images/" + id + ".png");
		if(file.delete()){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteExpenseDescription(int id){
		File file = new File("/var/lib/ReceiptLogger/descriptions/" + id + ".png");
		if(file.delete()){
			return true;
		} else {
			return false;
		}
	}
}
