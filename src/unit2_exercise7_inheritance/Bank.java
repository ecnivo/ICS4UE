package unit2_exercise7_inheritance;

public class Bank {
	public static void main(String[] args) {
		SavingsAccount savingsAccount = new SavingsAccount();

		for (int i = 0; i < 500; i++) {
			savingsAccount.deposit(1.0601);
		}

		System.out.println(savingsAccount.getBalance());
	}
}
