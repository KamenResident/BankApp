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
                // Two test accounts that can be used to demonstrate some of 
                // the application's functionalities.
                
                bank.addNewAccount("John", 
                                    "Doe", 
                                    "jdoe05", 
                                    "Password1!", 
                                    "1234567890", 
                                        "jdoe@mail.com", 
                                        "Sacramento, CA 98765", 
                                                0);
                bank.addNewAccount("Mary", 
                                    "Lee", 
                                    "mlee07", 
                                    "Password2!", 
                                    "5557778888", 
                                        "mlee@mail.com", 
                                        "Harlem, NY 98567", 
                                                1);
                bank.getBankUtils().findAccount("mlee07").setBalance(1000);
            }
        });
    }
}