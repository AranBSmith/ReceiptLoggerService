package services;

import java.util.HashMap;

import dao.ExpenseDAO;
import lombok.Data;

@Data
public class CredentialsService {

	ExpenseDAO cassandra;
	
	public boolean submitCredentials(HashMap<String, String> userCredentials) {
		
		return true;
	}

}
