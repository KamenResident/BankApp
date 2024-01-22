import javax.swing.SwingUtilities;

/**
 * Main launcher used to launch the bank application.
 */
public class MainLauncher {
 
    /**
     * Main method for running the bank application.
     * 
     * @param args is a collection of arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Bank bank = new Bank("BankApp");
                // A test account that can be used to demonstrate some of 
                // the application's functionalities.
                
                Account testAccount = new Account("John", 
                                                    "Doe", 
                                                    "jdoe05", 
                                                    "Password1!", 
                                                    "1234567890", 
                                                    "jdoe@mail.com", 
                                                    "Sacramento, CA 98765", 
                                                    0);
                Account testAccount2 = new Account("Mary", 
                                                    "Lee", 
                                                    "mlee07", 
                                                    "Password2!", 
                                                    "5557778888", 
                                                    "mlee@mail.com", 
                                                    "Harlem, NY 98567", 
                                                    1);
                bank.addNewAccount(testAccount);
                bank.addNewAccount(testAccount2);
            }
        });
    }
}