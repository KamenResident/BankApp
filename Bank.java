import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * An application that represents a banking program.
 */
public class Bank extends JFrame {

    private List<Account> accounts;
    private Account currentAccount;
    private int uid;
    private LoginWindow loginWindow;
    private SignUpWindow signUpWindow;
    private JPanel profilePanel;
    private BankUtility bankUtils;

    /**
     * Constructor for the banking program.
     * 
     * @param title is the title for the application.
     */
    public Bank(String title) {
        super(title);
        bankUtils = new BankUtility(this);  
        accounts = new ArrayList<Account>();
        uid = 0; 
        init();
        profilePanel = null;
        createComponents();
        loginWindow = new LoginWindow(this, "Log In");
        signUpWindow = new SignUpWindow(this, "Sign Up");
        currentAccount = null;              
    }

    private void init() {
        setTitle("BankApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false); 
        setVisible(false);      
    }

    private void createComponents() {
        // Create the tabbed pane for the application.
        JTabbedPane tabbedPane = new JTabbedPane();

        // Main panel for housing the inner panels and components.
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(450, 300));
        mainPanel.setBackground(Color.BLACK);  
        Dimension sidePanelDimension = new Dimension(250, 250);      

        // Create the left, right, and bottom panels.
        // Bottom panel will simply house the logout button.
        JPanel leftPanel = createLeftMainPanel(sidePanelDimension);
        JPanel rightPanel = createRightMainPanel(sidePanelDimension);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(450, 50));
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

        profilePanel = bankUtils.createProfilePanel();

        tabbedPane.addTab("Main", mainPanel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.addTab("Profile", profilePanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_1);     

        add(tabbedPane);
    }

    private JPanel createLeftMainPanel(Dimension panelDimension) {
        // Create the left panel and its components.
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(panelDimension);
        leftPanel.setBackground(Color.YELLOW);

        JLabel withdrawLabel = new JLabel("Withdraw");
        JLabel depositLabel = new JLabel("Deposit");
        JTextField withdrawField = new JTextField();
        Action withdrawAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double withdrawalAmount = Double.parseDouble(withdrawField.getText());
                if (currentAccount.withdraw(withdrawalAmount)) {
                    bankUtils.updateProfileBalance(currentAccount.getBalance());
                    Transaction withdrawTransaction = new Transaction("Withdraw", 
                                                                withdrawalAmount,
                                                                LocalDateTime.now());
                    currentAccount.addTransaction(withdrawTransaction);
                    currentAccount.updateTransactionCount();
                    JOptionPane.showMessageDialog(withdrawField, "Withdraw successful!");
                } else {
                    JOptionPane.showMessageDialog(withdrawField, "Insufficient funds. Withdraw failed");
                }
                withdrawField.setText("");
            }
        };
        withdrawField.addActionListener(withdrawAction);
        JTextField depositField = new JTextField();
        Action depositAction = new AbstractAction() {
           @Override
            public void actionPerformed(ActionEvent e) {
                double depositAmount = Double.parseDouble(depositField.getText());
                if (currentAccount.deposit(depositAmount)) {
                    bankUtils.updateProfileBalance(currentAccount.getBalance());
                    Transaction depositTransaction = new Transaction("Deposit", 
                                                            depositAmount, 
                                                            LocalDateTime.now());
                    currentAccount.addTransaction(depositTransaction);
                    currentAccount.updateTransactionCount();
                    JOptionPane.showMessageDialog(depositField, "Deposit successful!");                    
                } else {
                    JOptionPane.showMessageDialog(depositField, "Invalid funds. Deposit failed.");
                } 
                depositField.setText("");               
            }
        };
        depositField.addActionListener(depositAction);

        List<Component> comps = new ArrayList<Component>();
        comps.add(withdrawLabel);
        comps.add(withdrawField);
        comps.add(depositLabel);
        comps.add(depositField);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(1, 1, 1, 1);
        Dimension compSize = new Dimension(300, 30);
        for (int i = 0; i < comps.size(); i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            comps.get(i).setPreferredSize(compSize);
            leftPanel.add(comps.get(i), gbc);
        }

        return leftPanel;
    }

    private JPanel createRightMainPanel(Dimension panelDimension) {
        // Create the right panel and its components.
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(panelDimension);
        rightPanel.setBackground(Color.GREEN);

        Dimension compSize = new Dimension(300, 30);
        JLabel transferLabel = new JLabel("Transfer (Enter username)");
        transferLabel.setPreferredSize(compSize);
        JTextField transferField = new JTextField();
        Action transferAction = new AbstractAction () {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account recipient = bankUtils.findAccount(transferField.getText());
                if (recipient != null) {
                    String transfer = JOptionPane.showInputDialog("Enter amount to transfer");
                    double transferAmount = Double.parseDouble(transfer);
                    if (bankUtils.moneyTransfer(currentAccount, recipient, transferAmount)) {
                        bankUtils.updateProfileBalance(currentAccount.getBalance());
                        JOptionPane.showMessageDialog(transferField, "Transfer successful!");
                    } else {
                        JOptionPane.showMessageDialog(transferField, "Insufficient funds. Transfer failed.");
                    }
                } else {
                    JOptionPane.showMessageDialog(transferField, "Cannot find user.");
                }
                transferField.setText("");               
            }
        };
        transferField.addActionListener(transferAction);
        transferField.setMaximumSize(compSize);

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

        List<Component> comps = new ArrayList<Component>();
        comps.add(transferLabel);
        comps.add(transferField);
        comps.add(statementLabel);
        comps.add(statementButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(1, 1, 1, 1);
        for (int i = 0; i < comps.size(); i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            rightPanel.add(comps.get(i), gbc);
        }

        return rightPanel;
    }

    /**
     * Used to add a new bank account to the system.
     * 
     * @param firstName is the new user's first name.
     * @param lastName is the new user's last name.
     * @param username is the new user's username.
     * @param password is the new user's password.
     * @param phoneNumber is the new user's phone number.
     * @param email is the new user's email address.
     * @param address is the new user's physical address.
     * @param type is the type of account being created (0 for checking, 1 for savings).
     */
    protected void addNewAccount(String firstName, String lastName,
                                    String username, String password,
                                    String phoneNumber, String email,
                                    String address, int type) {
        Account newAccount;
        if (type == 0) {
            newAccount = new Account(firstName, lastName,
                                        username, password, 
                                        phoneNumber, email,
                                        address, getUID());   
        } else {
            newAccount = new SavingsAccount(firstName, lastName,
                                            username, password, 
                                            phoneNumber, email,
                                            address, getUID());
        }
        
        accounts.add(newAccount);
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

    protected int getUID() {
        return uid++;
    }

    protected JPanel getProfilePanel() {
        return profilePanel;
    }

    protected List<Account> getAccounts() {
        return accounts;
    }

    protected Account getCurrentAccount() {
        return currentAccount;
    }

    protected void setCurrentAccount(Account user) {
        currentAccount = user;
    }

    protected BankUtility getBankUtils() {
        return bankUtils;
    }

}
