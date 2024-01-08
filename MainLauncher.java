import javax.swing.SwingUtilities;

/**
 * Main launcher used for launching the bank application.
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
                Bank bank = new Bank();
                System.out.println(String.format("%s is now running", bank.getTitle()));
            }
        });
    }
}