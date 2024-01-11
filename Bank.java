import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
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
    public Bank(String title) {
        super(title);
        accounts = new ArrayList<Account>();
        uid = 0; 
        init();
        createComponents();
        loginWindow = new LoginWindow(this, "Log In");
        signUpWindow = new SignUpWindow(this, "Sign Up");
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
        setVisible(false);      
    }

    /**
     * Create the necessary components for the application.
     */
    private void createComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.setSize(1000, 1000);
        mainPanel.setOpaque(false);
        
        JPanel leftPanel = new JPanel();
        BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS);
        leftPanel.setLayout(leftLayout);
        leftPanel.setPreferredSize(new Dimension(500, 750));

        JLabel withdrawLabel = new JLabel("Withdraw", Label.CENTER);
        JLabel depositLabel = new JLabel("Deposit", Label.CENTER);
        JTextField withdrawField = new JTextField();
        Action withdrawAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        };
        withdrawField.addActionListener(withdrawAction);
        JTextField depositField = new JTextField();
        Action depositAction = new AbstractAction() {
           @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
        depositField.addActionListener(depositAction);

        tabbedPane.addTab("Main", mainPanel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);     

        add(tabbedPane);
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
    protected boolean checkUsername(String username) {
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
    protected boolean checkPassword(String password) {
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

    private boolean withdraw(double withdrawAmount) {
        boolean withdraw = false;
        if (currentAccount.withdraw(withdrawAmount)) {
            withdraw = true;
        }
        return withdraw;       
    }

    /**
     * Used to help transfer money between two accounts.
     * 
     * @param sender is the account sending the money.
     * @param recipient is the account receiving the money.
     * @param transferAmount is the amount of money being transferred.
     * @return true if the transfer is successful, false otherwise.
     */
    private boolean moneyTransfer(Account sender, Account recipient, double transferAmount) {
        boolean sufficientFunds = false;
        if (sender.getBalance() >= transferAmount) {
            recipient.setBalance(recipient.getBalance() + transferAmount);
            sender.setBalance(sender.getBalance() - transferAmount);
            sufficientFunds = true;
        }

        return sufficientFunds;
    }

    /**
     * Used to set the current account that is logged in.
     * 
     * @param user is the account that is logged in.
     */
    protected void setCurrentAccount(Account user) {
        currentAccount = user;
    }

    /**
     * Used to add a new account for the application upon successful
     * sign up.
     * 
     * @param newAccount is the new account being registered.
     */
    protected void addNewAccount(Account newAccount) {
        accounts.add(newAccount);
    }

    /**
     * Generates a unique user ID for a new account.
     * 
     * @return a unique user ID
     */
    protected int getUID() {
        return uid++;
    }

    /**
     * Retrieves the list of currently registered accounts.
     * 
     * @return the list of currently registered accounts.
     */
    protected List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Retrieves the current account that is logged in.
     * 
     * @return the account currently logged in.
     */
    protected Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Used to toggle the visibility of both the login and signup windows.
     * 
     * @param choice is a flag for toggling the visilibity of either the login or signup window.
     */
    protected void manageWindows(int choice) {
        if (choice == 0) {
            signUpWindow.setVisible(true);
        } else {
            loginWindow.setVisible(true);
        }
    }

}
