package unit2_exercise7_inheritance;

public class BankAccount {

	private double balance;

	BankAccount() {
		balance = 0;
	}

	BankAccount(double startingAmount) {
		balance = startingAmount;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		balance -= amount;
	}

	public double getBalance() {
		return balance;
	}

}
