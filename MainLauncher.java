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
                // A test account you can run to demonstrate some of 
                // the application's functionalities.
                
                // Account testAccount = new CheckingAccount("John", 
                //                                     "Doe", 
                //                                     "jdoe05", 
                //                                     "Password1!", 
                //                                     "1234567890", 
                //                                     "jdoe@mail.com", 
                //                                     "CA, Sacramento 98765", 
                //                                     0);
                // bank.addNewAccount(testAccount);

                System.out.println(String.format("%s is now running", bank.getTitle()));
            }
        });
    }
}