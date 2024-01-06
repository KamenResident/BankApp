public class SavingsAccount extends Account {
    
    public SavingsAccount() {
        super();
    }

    public SavingsAccount(String firstName, String lastName, String username, 
                                                                String password, 
                                                                String phoneNumber, 
                                                                String email, 
                                                                String address, 
                                                                int id) {
        super(firstName, lastName, username, password, phoneNumber, email, address, id);
    }

    public void withdraw(double withdrawAmount) {
        if (withdrawAmount < getBalance()) {
            setBalance(getBalance() - withdrawAmount);
        } else {
            System.out.println("Savings account overdrawn transaction rejected");
        }
    }
}
