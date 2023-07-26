public class CheckingAccount extends Account {

    private double overdraftLimit;

    public CheckingAccount(String firstName, String lastName, String username, String password, String phoneNumber, int id) {
        super(firstName, lastName, username, password, phoneNumber, id);
        overdraftLimit = 300;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double newLimit) {
        overdraftLimit = newLimit;
    }

    public void withdraw(double withdrawAmount) {
        if ((getBalance() - withdrawAmount) > overdraftLimit) {
            setBalance(getBalance() - withdrawAmount);
            System.out.println("Withdrew " + withdrawAmount);
        } else {
            System.out.println("Withdrawal amount exceeds overdraft limit.");
        }
    }

    
    
}
