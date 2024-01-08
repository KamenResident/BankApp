import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

    /**
     * Window used for handling logins.
     */
    private LoginWindow loginWindow;

    /**
     * Window used for handling sign ups.
     */
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

    /**
     * Used to initialize the application's elements.
     */
    private void init() {
        setTitle("BankApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setResizable(false);       
    }

    /**
     * Create the necessary components for the application.
     */
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
     * Checks any users with the name being searched.
     * Also serves as a way to prevent multiple users with the same name
     * to preserve identity.
     * 
     * @param firstName is the first name of the user being searched.
     * @param lastName is the last name of the user being searched.
     * @return true if a user with the same name exists, false otherwise.
     */
    private boolean checkName(String firstName, String lastName) {
        String name = String.format("%s %s", firstName, lastName);
        boolean found = false;
        int i = 0;
        if (!accounts.isEmpty()) {
            while (!found && i < accounts.size()) {
                if (accounts.get(i).getName().compareTo(name) == 0) {
                    found = true;
                } else {
                    i++;
                }
            }
        }
        return found;
    }

    /**
     * Checks any users with the username being searched and ensures
     * the username meets the required length.
     * Also serves as a way to prevent new users using a username that
     * has already been used.
     * 
     * @param username is the username being searched.
     * @return true if there exists a user with the username, false otherwise.
     */
    public boolean checkUsername(String username) {
        boolean found = false;
        int i = 0;
        if (!accounts.isEmpty() && username.length() > 5) {
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
     * Checks to ensure the password meets the requirements.
     * The password must have more than 6 characters and at least
     * one of the following: a digit, special character, uppercase letter,
     *                       and a lowercase letter.
     * 
     * @param password is the password the user intends to use for their account.
     * @return true if the password meets all the requirements, false otherwise.
     */
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
     * Used to help transfer money between two accounts.
     * 
     * @param sender is the account sending the money.
     * @param recipient is the account receiving the money.
     * @param transferAmount is the amount of money being transferred.
     * @return true if the transfer is successful, false otherwise.
     */
    public boolean moneyTransfer(Account sender, Account recipient, double transferAmount) {
        boolean sufficientFunds = false;
        if (sender.getBalance() > transferAmount) {
            recipient.setBalance(recipient.getBalance() + transferAmount);
            sender.setBalance(sender.getBalance() - transferAmount);
            sufficientFunds = true;
        } else {
            
        }

        return sufficientFunds;
    }

    /**
     * 
     * @param user
     */
    public void setCurrentAccount(Account user) {
        currentAccount = user;
    }

    /**
     * 
     * 
     * @param newAccount
     */
    public void addNewAccount(Account newAccount) {
        accounts.add(newAccount);
    }

    /**
     * 
     * 
     * @return
     */
    public int getUID() {
        return uid++;
    }

    /**
     * Retrieves the list of currently registered accounts.
     * 
     * @return the list of currently registered accounts.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Retrieves the current account that is logged in.
     * 
     * @return the account currently logged in.
     */
    public Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Used to toggle the visibility of both the login and signup windows.
     * 
     * @param choice is a flag for toggling the visilibity of either the login or signup window.
     */
    public void manageWindows(int choice) {
        if (choice == 0) {
            signUpWindow.setVisible(true);
        } else {
            loginWindow.setVisible(true);
        }
    }

}
