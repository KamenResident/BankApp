public class SavingsAccount extends Account {

    private double overdraftLimit;

    private int withdrawCount;

    private boolean activeState;
    
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
        withdrawCount = 0;
        activeState = false;
    }

    public boolean withdraw(double withdrawalFunds) {
        boolean sufficientFunds = false;
        if (super.withdraw(withdrawalFunds) && withdrawalFunds <= overdraftLimit) {
            sufficientFunds = true;
            withdrawCount++;
        }
        return sufficientFunds;
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
    
}
