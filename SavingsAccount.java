/**
 * Represents a savings account for a bank.
 */
public class SavingsAccount extends Account {

    private boolean activeState;
    private String errorMessage;
   
    /**
     * Parameter-less constructor for a savings account.
     */
    public SavingsAccount() {
        super();
    }

    /**
     * Paramterized constructor for a savings account.
     * 
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param phoneNumber
     * @param email
     * @param address
     * @param id
     */
    public SavingsAccount(String firstName, String lastName, String username, 
                                                                String password, 
                                                                String phoneNumber, 
                                                                String email, 
                                                                String address, 
                                                                int id) {
        super(firstName, lastName, username, password, phoneNumber, email, address, id);
        activeState = false;
    }

    /**
     * 
     * 
     * @param
     */
    public boolean withdraw(double withdrawalFunds) {
        boolean sufficientFunds = false;
        if (super.withdraw(withdrawalFunds) && activeState) {
            sufficientFunds = true;
        }

        return sufficientFunds;
    }

    /**
     * 
     * 
     * @param
     */
    public void deposit(double depositAmount) {
        super.deposit(depositAmount);
        if (super.getBalance() >= 20) {
            activeState = true;
        }
    }

    public boolean getActiveState() {
        return activeState;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
