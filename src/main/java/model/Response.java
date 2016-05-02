package model;

import lombok.Data;

/**
 * Response for using functions on this webservice.
 * 
 * @author Aran
 *
 */
@Data
public abstract class Response {

private String response;

	
	/**
	 * By default on instantiation, the Response is a failure, until the 
	 * setSuccess() function is called, the response will remain a failure.
	 * 
	 */
	public Response(){
		setFail();
	}
	
	
	/**
	 * make this response a failure response.
	 */
	public void setFail(){
		this.response = "fail";
	}
	
	/**
	 * make this response a successful response.
	 */
	public void setSuccess(){
		this.response = "success";
	}
	
	/**
	 * check if this response was successful.
	 * 
	 * @return boolean, true if successful otherwise false.
	 */
	public boolean isSuccess(){
		try{
			return response.substring(0,7).equals("success");
		} catch (StringIndexOutOfBoundsException e){
			return false;
		}
	}
	
	/**
	 * check if this response was a failure.
	 * 
	 * @param message
	 */
	public void appendMessage(String message){
		this.response = this.response + " " + message;
	}
	
}
