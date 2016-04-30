package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.CancelExpenseResponse;
import services.CancelExpenseService;

@RestController
@Data
public class CancelExpenseController {
	
	private CancelExpenseService cancelExpenseService;
	
	public CancelExpenseController(){
		cancelExpenseService = new CancelExpenseService();
	}
	
	@RequestMapping(value = "/cancelExpense", method=RequestMethod.POST)
	public CancelExpenseResponse cancelExpense(
			@RequestParam(value="email") String email,
    		@RequestParam(value="password") String password,
    		@RequestParam(value="id") int id){
		
		return cancelExpenseService.cancelExpense(email, password, id);
	}
}
