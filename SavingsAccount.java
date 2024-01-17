import javax.swing.JOptionPane;

/**
 * Represents a savings account for a bank.
 */
public class SavingsAccount extends Account {

    private boolean activeState;
   
    /**
     * Parameter-less constructor for a savings account.
     */
    public SavingsAccount() {
        super();
    }

    /**
     * Paramterized constructor for a savings account.
     * 
     * @param firstName is the owner's first name.
     * @param lastName is the owner's last name.
     * @param username is the owner's username.
     * @param password is the owner's password.
     * @param phoneNumber is the owner's phone number.
     * @param email is the owner's email.
     * @param address is the owner's physical address.
     * @param id is the owner's user ID
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
     * Used to manage withdrawals from this account.
     * Makes the account no longer active if the owner's balance falls under $20.
     * 
     * @param withdrawalFunds is the amount of money being withdrawn.
     * @return true if the withdrawal is successful; false otherwise.
     */
    public boolean withdraw(double withdrawalFunds) {
        boolean sufficientFunds = false;
        if (super.withdraw(withdrawalFunds) && activeState) {
            sufficientFunds = true;
            if (super.getBalance() < 20) {
                activeState = false;
                JOptionPane.showMessageDialog(null, "Account no longer active.");
            }
        } else if (!activeState) {
            JOptionPane.showMessageDialog(null, 
                                            "Account not active yet");
        }

        return sufficientFunds;
    }

    /**
     * Used to deposit money into the owner's account.
     * Activates the account if the balance becomes at least $20.
     * 
     * @param depositAmount is the amount of money being deposited.
     */
    public boolean deposit(double depositAmount) {
        boolean depositSuccess = false;
        if (super.deposit(depositAmount)) {
            depositSuccess = true;
        }

        if (super.getBalance() >= 20 && !activeState) {
            activeState = true;
            JOptionPane.showMessageDialog(null, "Account is now active.");
        }

        return depositSuccess;
    }

    /**
     * Retrieves the account's active state, which tells whether or not
     * the account is fit for withdrawals.
     * 
     * @return the account's active state
     */
    public boolean getActiveState() {
        return activeState;
    }
}
