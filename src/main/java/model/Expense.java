package model;

import java.util.Date;

import lombok.Data;

@Data
public class Expense {
	private String email;
	private String category;
	private Date date;
}
