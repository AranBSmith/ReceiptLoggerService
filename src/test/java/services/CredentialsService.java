package services;

import java.util.HashMap;

import dao.Cassandra;
import lombok.Data;

@Data
public class CredentialsService {

	Cassandra cassandra;
	
	public boolean submitCredentials(HashMap<String, String> userCredentials) {
		
		return true;
	}

}
