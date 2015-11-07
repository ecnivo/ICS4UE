package unit2_exercise7_inheritance;


public class SavingsAccount extends BankAccount{
	
	private double interestRate;
	
	public SavingsAccount() {
		super();
		interestRate = 2.5;
	}
	
	public SavingsAccount(double rate) {
		super();
		interestRate = rate;
	}

	public SavingsAccount(double startingAmount, double rate) {
		super(startingAmount);
		interestRate = rate;
	}
	
	public void setInterestRate(double rate) {
		interestRate = rate;
	}
	
	public void addInterest() {
		deposit(getBalance() * interestRate);
	}
}
