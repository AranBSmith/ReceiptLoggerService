package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.CancelExpenseResponse;
import services.CancelExpenseService;

/**
 * Rest Controller used for canceling expenses according to the user email and 
 * the expense selected, identified with a unique number.
 * 
 * @author Aran
 *
 */
@RestController
@Data
public class CancelExpenseController {
	private CancelExpenseService cancelExpenseService;
	
	/**
	 * Creates a new instance of the CancelExpenseService.
	 */
	public CancelExpenseController(){
		cancelExpenseService = new CancelExpenseService();
	}
	
	/**
	 * Upon posting to /cancelExpense with email, password and id this function
	 * will be called to initiate the expense cancellation.
	 * 
	 * @param email A valid user email.
	 * @param password A valid user password.
	 * @param id Unique expense ID.
	 * @return CancelExpenseResponse object which specifying the status of the 
	 * expense cancellation.
	 */
	@RequestMapping(value = "/cancelExpense", method=RequestMethod.POST)
	public CancelExpenseResponse cancelExpense(
			@RequestParam(value="email") String email,
    		@RequestParam(value="password") String password,
    		@RequestParam(value="id") int id){
		
		return cancelExpenseService.cancelExpense(email, password, id);
	}
}
