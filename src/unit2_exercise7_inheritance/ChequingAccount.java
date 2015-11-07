package unit2_exercise7_inheritance;


public class ChequingAccount extends BankAccount{
	
	private double monthlyFee;
	private double minimumBalance;
	
	public ChequingAccount() {
		super();
		monthlyFee = 1;
		minimumBalance = 100;
	}
	
	public ChequingAccount(double startingAmount) {
		super (startingAmount);
		monthlyFee = 1;
		minimumBalance = 100;
	}
	
	public void withdraw(double amount) {
		if (getBalance() < minimumBalance)
			super.withdraw(amount + monthlyFee);
	}
	
	public void setMonthlyFee(double fee) {
		this.monthlyFee = fee;
	}
	
	public void setMinimumBalance(double minimum) {
		this.minimumBalance = minimum;
	}
}
