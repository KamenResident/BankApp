public class SavingsAccount extends Account {

    private double overdraftLimit;

    private boolean activeState;

    private String errorMessage;
    
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
        activeState = false;
    }

    public boolean withdraw(double withdrawalFunds) {
        boolean sufficientFunds = false;
        if (super.withdraw(withdrawalFunds) && withdrawalFunds <= overdraftLimit && activeState) {
            sufficientFunds = true;
        }
        if (activeState) {
        }

        return sufficientFunds;
    }

    public void deposit(double depositAmount) {
        super.deposit(depositAmount);
        if (super.getBalance() >= 20) {
            activeState = true;
        }
    }

    public boolean getActiveState() {
        return activeState;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double newLimit) {
        overdraftLimit = newLimit;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
