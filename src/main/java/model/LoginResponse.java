package model;

import lombok.Data;

@Data
public class LoginResponse {
	
	private String response;
	
	public LoginResponse(String response){
		this.response = "test";
	}
	
	public String getResponse(){
		return response;
	}
}
