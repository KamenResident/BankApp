import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
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

    private List<Account> accounts;
    private Account currentAccount;
    private int uid;
    private LoginWindow loginWindow;
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
        currentAccount = null;
    }

    private void init() {
        setTitle("BankApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setResizable(false); 
        setVisible(false);      
    }

    private void createComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(950, 950));
        // mainPanel.setOpaque(false);     
        Dimension sidePanelDimension = new Dimension(450, 750);      

        JPanel leftPanel = createLeftMainPanel(sidePanelDimension);
        JPanel rightPanel = createRightMainPanel(sidePanelDimension);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(900, 200));
        bottomPanel.setBackground(Color.PINK);
        JButton logoutButton = new JButton("Log out");
        logoutButton.setBackground(Color.RED);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentAccount = null;
                setVisible(false);
                manageWindows(1);
            }
        });
        logoutButton.setPreferredSize(new Dimension(100, 30));
        bottomPanel.add(logoutButton);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Main", mainPanel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);     

        add(tabbedPane);
    }

    private JPanel createLeftMainPanel(Dimension panelDimension) {
        JPanel leftPanel = new JPanel();
        BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS);
        leftPanel.setLayout(leftLayout);
        leftPanel.setPreferredSize(panelDimension);
        leftPanel.setBackground(Color.YELLOW);

        Dimension compSize = new Dimension(200, 30);
        JLabel withdrawLabel = new JLabel("Withdraw");
        withdrawLabel.setPreferredSize(compSize);
        JLabel depositLabel = new JLabel("Deposit");
        depositLabel.setPreferredSize(compSize);
        JTextField withdrawField = new JTextField();
        withdrawField.setPreferredSize(compSize);
        Action withdrawAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentAccount.withdraw(Double.parseDouble(withdrawField.getText()))) {
                    JOptionPane.showMessageDialog(withdrawField, "Withdraw successful!");
                } else {
                    JOptionPane.showMessageDialog(withdrawField, "Insufficient funds. Withdraw failed");
                }
            }
        };
        withdrawField.addActionListener(withdrawAction);
        JTextField depositField = new JTextField();
        depositField.setPreferredSize(compSize);
        Action depositAction = new AbstractAction() {
           @Override
            public void actionPerformed(ActionEvent e) {
                if (currentAccount.deposit(Double.parseDouble(depositField.getText()))) {
                    JOptionPane.showMessageDialog(depositField, "Deposit successful!");
                } else {
                    JOptionPane.showMessageDialog(depositField, "Invalid funds. Deposit failed.");
                }                
            }
        };
        depositField.addActionListener(depositAction);

        leftPanel.add(withdrawLabel);
        leftPanel.add(withdrawField);
        leftPanel.add(depositLabel);
        leftPanel.add(depositField);

        return leftPanel;
    }

    private JPanel createRightMainPanel(Dimension panelDimension) {
        JPanel rightPanel = new JPanel();
        BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS);
        rightPanel.setLayout(rightLayout);
        rightPanel.setPreferredSize(panelDimension);
        rightPanel.setBackground(Color.GREEN);

        Dimension compSize = new Dimension(200, 30);
        JLabel transferLabel = new JLabel("Enter recipient's username for transfer:");
        transferLabel.setPreferredSize(compSize);
        JTextField transferField = new JTextField();
        Action transferAction = new AbstractAction () {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account recipient = findAccount(transferField.getText());
                if (recipient != null) {
                    String transfer = JOptionPane.showInputDialog("Enter amount to transfer");
                    double transferAmount = Double.parseDouble(transfer);
                    if (moneyTransfer(currentAccount, recipient, transferAmount)) {
                        JOptionPane.showMessageDialog(null, "Transfer successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient funds. Transfer failed.");
                    }
                } else {
                    JOptionPane.showMessageDialog(transferField, "Cannot find user.");
                }               
            }
        };
        transferField.addActionListener(transferAction);
        transferField.setPreferredSize(compSize);

        JLabel statementLabel = new JLabel("View Bank Statement");
        statementLabel.setPreferredSize(compSize);
        JButton statementButton = new JButton("Generate");
        statementButton.setPreferredSize(new Dimension(100, 30));
        statementButton.setBackground(Color.CYAN);
        statementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(statementButton, currentAccount.getTransactionHistory());
            }
        });
       
        rightPanel.add(transferLabel);
        rightPanel.add(transferField);
        rightPanel.add(statementLabel);
        rightPanel.add(statementButton);

        return rightPanel;
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

    private Account findAccount(String username) {
        Account searchedAccount = null;
        boolean found = false;
        int i = 0;
        if (!accounts.isEmpty() && username.length() > 5) {
            while (!found && i < accounts.size()) {
                if (accounts.get(i).getUsername().compareTo(username) == 0) {
                    found = true;
                    searchedAccount = accounts.get(i);
                } else {
                    i++;
                }
            }
        }
        return searchedAccount;
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
        if (sender.getBalance() > transferAmount && sender.withdraw(transferAmount)) {
            recipient.deposit(transferAmount);
            Transaction transferTransaction = new Transaction("Transfer", 
                                                                transferAmount, 
                                                                LocalDateTime.now());
            currentAccount.addTransaction(transferTransaction);
            sufficientFunds = true;
        }

        return sufficientFunds;
    }

    protected void setCurrentAccount(Account user) {
        currentAccount = user;
    }

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

    protected List<Account> getAccounts() {
        return accounts;
    }

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
