package bank;
import java.util.Random;

public class account {
	private String name;
	private String fingerprint;
	private Integer balance;
	private Integer savings_balance;
	private Integer verification_code;
	private Random ran = new Random();
	private String account_statement;
	private String account_add_statement;
	private String account_take_statement;
	
	
	public account() {
		name = "Default";
		fingerprint = "Default_FingerPrint";

		Integer balancex = ran.nextInt(90000) + 10000;
		if(balancex < 0)
			balancex *= -1;
		balancex = balancex/100;
		
		Integer balanceSaving = ran.nextInt(90000) + 10000;
		if(balanceSaving < 0)
			balanceSaving *= -1;
		balanceSaving = balanceSaving/100;
		
		setSaving(balanceSaving);
		setBalance(balancex);
	}
	
	public void setName(String namer) {
		name = namer;
	}
	
	public void setFingerPrint(String fingerprinter) {
		fingerprint = fingerprinter;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFingerPrint() {
		return fingerprint;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balancer) {
		balance = balancer;
	}
	
	public void setSaving(Integer balancer) {
		savings_balance = balancer;
	}

	public Integer getSavings() {
		return savings_balance;
	}
	public void setAccountStatement() {
		account_statement = ("Hello " + name + "!" + " You have " + "$" + getBalance() +
				" in your checking account and $" + getSavings() + " in your savings account");
	}
	
	public void setAccountAdd(Integer amount) {
		account_add_statement = ("Ok " + name + " you have added " + amount + " to your checking account from "
				+ "your savings account.");
	}
	
	public void showAddStatement() {
		System.out.println(account_add_statement);
		System.out.println("Your new checking balance is $" + getBalance() + "."
				+ " Your new savings balance is $" + getSavings() + ".");
	}
	
	public void takeOut(Integer amount) {
		balance -= amount;
		savings_balance += amount;
	}
	public String getAccountStatement() {
		setAccountStatement();
		return account_statement;
	}
	
	public void showAccountStatement() {
		System.out.println(account_statement);
	}
	
	public void mealsToSave() {
		Integer meals = getBalance()*10;
		if(meals < 0)
			meals = 0;
		System.out.println("You provide 10 meals per dollar by donating to Feeding America. That means you "
				+ "can provide up " + (getBalance()*10) + " meals to families in need!");
	}

	public void addIn(Integer amount) {
		balance += amount;		
		savings_balance -= amount;
	}

	public Integer getVerification_code() {
		return verification_code;
	}

	public void setVerification_code(Integer verification_coder) {
		verification_code = verification_coder;
	}

	public void setAccountTake(Integer amount) {
		account_take_statement = ("Ok " + name + " you have subtracted $" + amount + " from your checking account"
				+ "back into your savings account.");
	}

	public void showTakeStatement() {
		System.out.println(account_take_statement);
		System.out.println("Your new balance is $" + getBalance() + ".");
	}	
	
}
