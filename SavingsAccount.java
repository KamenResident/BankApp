public class SavingsAccount extends Account {
    
    public SavingsAccount() {
        super();
    }

    public SavingsAccount(String firstName, String lastName, String username, String password, String phoneNumber, int id) {
        super(firstName, lastName, username, password, phoneNumber, id);
    }

    public void withdraw(double withdrawAmount) {
        if (withdrawAmount < getBalance()) {
            setBalance(getBalance() - withdrawAmount);
        } else {
            System.out.println("Savings account overdrawn transaction rejected");
        }
    }
}
