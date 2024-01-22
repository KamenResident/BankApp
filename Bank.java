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
 * A program that represents a banking program.
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
     */
    public Bank(String title) {
        super(title);
        accounts = new ArrayList<Account>();
        uid = 0; 
        init();
        profilePanel = null;
        createComponents();
        loginWindow = new LoginWindow(this, "Log In");
        signUpWindow = new SignUpWindow(this, "Sign Up");
        currentAccount = null;  
        bankUtils = new BankUtility(this);      
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

        profilePanel = createProfilePanel();

        tabbedPane.addTab("Main", mainPanel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.addTab("Profile", profilePanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_1);     

        add(tabbedPane);
    }

    private JPanel createProfilePanel() {
        // Create the panel for the secondary tab for the user's profile.
        JPanel profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setPreferredSize(new Dimension(450, 450));
        profilePanel.setBackground(Color.ORANGE);
        GridBagConstraints gbc = new GridBagConstraints();
        String[] names = new String[] {"Name:", "Email:", "Number:", "Address:", "Username:", "ID:", "Balance:"};
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        for (int i = 0; i < names.length; i++) {
            JLabel newLabel = new JLabel(names[i], JLabel.CENTER);
            newLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weighty = 0.5;
            newLabel.setForeground(Color.GRAY);
            profilePanel.add(newLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.weighty = 0.5;
            profilePanel.add(new JLabel(), gbc);
        }     

        return profilePanel;
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
                if (currentAccount.withdraw(Double.parseDouble(withdrawField.getText()))) {
                    updateProfileBalance(currentAccount.getBalance());
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
                if (currentAccount.deposit(Double.parseDouble(depositField.getText()))) {
                    updateProfileBalance(currentAccount.getBalance());
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
                        updateProfileBalance(currentAccount.getBalance());
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
     * Updates the profile depending on at least one credential being changed.
     * 
     * @param account is the user's account whose credentials are being updated.
     */
    protected void updateProfile(Account account) {
        String[] accountCreds = new String[] {account.getName(),
                                            account.getEmail(),
                                            account.getPhoneNumber(),
                                            account.getAddress(), 
                                            account.getUsername(), 
                                            String.format("%d", 
                                                        account.getID()),
                                            String.format("$%,.2f", 
                                                        account.getBalance())};
        GridBagLayout profileLayout = (GridBagLayout) profilePanel.getLayout();
        GridBagConstraints gbc = profileLayout.getConstraints(profilePanel);
        gbc.gridx = 1;
        int index = 1;
        for (int i = 0; i < accountCreds.length; i++) {
            gbc.gridy = i;
            Component label = profilePanel.getComponent(index);
            profilePanel.remove(label);
            profilePanel.add(new JLabel(accountCreds[i]), gbc, index);
            index += 2;
        }     
    }

    private void updateProfileBalance(double balance) {
        // Simply update the account's balance listed in the profile tab.
        GridBagLayout profileLayout = (GridBagLayout) profilePanel.getLayout();
        GridBagConstraints gbc = profileLayout.getConstraints(profilePanel);
        gbc.gridx = 1;
        gbc.gridy = 6;
        Component balanceLabel = profilePanel.getComponent(13);
        profilePanel.remove(balanceLabel);
        profilePanel.add(new JLabel(String.format("$%,.2f", balance)), gbc, 13);
    }

    /**
     * Used to add a new bank account to the system.
     * 
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param phoneNumber
     * @param email
     * @param address
     * @param type
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

    protected void setCurrentAccount(Account user) {
        currentAccount = user;
    }

    protected BankUtility getBankUtils() {
        return bankUtils;
    }

}
