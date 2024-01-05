import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A program that represents a banking program.
 */
public class Bank extends JFrame {

    /**
     * List of all registered accounts.
     */
    private List<Account> accounts;

    /**
     * The account that is currently logged in.
     */
    private Account currentAccount;

    /**
     * Unique user ID for each new account created.
     */
    private int uid;

    private LoginWindow loginWindow;

    private SignUpWindow signUpWindow;

    /**
     * Constructor for the banking program.
     */
    public Bank() {
        loginWindow = new LoginWindow(this);
        signUpWindow = new SignUpWindow(this);
        accounts = new ArrayList<Account>();
        uid = 0; 
        init();
        createComponents();
    }

    private void init() {
        setTitle("BankApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        GroupLayout mainLayout = new GroupLayout(mainPanel);
        mainPanel.setSize(1000, 1000);
        mainPanel.setLayout(mainLayout);
        mainPanel.setOpaque(false);
        mainLayout.setAutoCreateGaps(true);
        mainLayout.setAutoCreateContainerGaps(true);        
    }

    /**
     * 
     * 
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password) {       
        int index = 0;
        boolean found = false;

        // Check if this account is in the database.
        while (!found && index < accounts.size()) {
            Account current = accounts.get(index);
            if (current.getUsername().compareTo(username) == 0 &&
                current.getPassword().compareTo(password) == 0) {
                found = true;
                currentAccount = current;
                System.out.println("Login successful!");
            } else {
                index++;
            }
        }

        return found;
    }

    public boolean signUp(String firstName, String lastName, String username, 
                            String password, String phoneNumber, int choice) {
        boolean signedIn = false;
        boolean validNameLength = firstName.length() > 1 && lastName.length() > 1;
        boolean validUsernameLength = username.length() > 5;
        boolean validPassword = checkPassword(password);
        boolean validPhoneNumber = phoneNumber.length() == 10;
        boolean uniqueAccount = !(checkName(firstName, lastName) 
                                    && checkUsername(username));
        signedIn = validNameLength 
                    && validUsernameLength 
                    && validPassword 
                    && validPhoneNumber 
                    && uniqueAccount;
        
        if (signedIn) {
            Account account;
            if (choice == 0) {
                account = new CheckingAccount(firstName,
                                            lastName,
                                            username, 
                                            password, 
                                            phoneNumber, 
                                            uid++);
            } else {
                account = new SavingsAccount(firstName,
                                            lastName,
                                            username, 
                                            password, 
                                            phoneNumber, 
                                            uid++); 
            }
            accounts.add(account);
        }
        return signedIn;
    }

    public boolean checkName(String firstName, String lastName) {
        boolean found = false;
        String accountName = firstName + " " + lastName;
        int i = 0;
        if (!accounts.isEmpty()) {
            while (!found && i < accounts.size()) {
                if (accounts.get(i).getName().compareTo(accountName) == 0) {
                    found = true;
                } else {
                    i++;
                }
            }
        }
        return found;
    }

    public boolean checkUsername(String username) {
        boolean found = false;
        int i = 0;
        if (!accounts.isEmpty()) {
            while (!found && i < accounts.size()) {
                if (accounts.get(i).getUsername().compareTo(username) == 0) {
                    found = true;
                } else {
                    i++;
                }
            }
        }
        return found;

    }

    public boolean checkPassword(String password) {
        boolean validLength = password.length() > 6;
        boolean numbers = false;
        boolean specialChars = false;
        boolean upperCase = false;
        boolean lowerCase = false;
        char currentChar;
        if (validLength) {
            for (int i = 0; i < password.length(); i++) {
                currentChar = password.charAt(i);

                if (Character.isDigit(currentChar)) {
                    numbers = true;
                }
                if (Character.isUpperCase(currentChar)) {
                    upperCase = true;
                }
                if (Character.isLowerCase(currentChar)) {
                    lowerCase = true;
                }
                if(!Character.isDigit(currentChar)
                    && !Character.isLetter(currentChar)
                    && !Character.isWhitespace(currentChar)) {
                    specialChars = true;
                }
            }
        }

        boolean ready = validLength && numbers && specialChars && upperCase && lowerCase;
        return ready;
    }

    /**
     * 
     * 
     * @param sender
     * @param recipient
     * @param transferAmount
     * @return
     */
    public boolean moneyTransfer(Account sender, Account recipient, double transferAmount) {
        boolean sufficientFunds = false;
        if (sender.getBalance() > transferAmount) {
            recipient.setBalance(recipient.getBalance() + transferAmount);
            sender.setBalance(sender.getBalance() - transferAmount);
            sufficientFunds = true;
        } else {
            System.out.println("Insufficient funds");
        }

        return sufficientFunds;
    }

    /**
     * 
     * 
     * @param username
     * @return
     */
    public boolean findAccount(String username) {
        boolean found = false;
        int i = 0;
        if (!accounts.isEmpty()) {
            while (!found && i < accounts.size()) {
                if (accounts.get(i).getUsername().compareTo(username) == 0) {
                    found = true;
                } else {
                    i++;
                }
            }
        }
        return found;
    }

    /**
     * 
     * 
     * @return
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * 
     * 
     * @return
     */
    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void manageWindows(int choice) {
        if (choice == 0) {
            signUpWindow.setVisible(true);
        } else {
            loginWindow.setVisible(true);
        }
    }

}
