import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
// import java.util.Scanner;

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

    private JFrame loginWindow;

    private JFrame signUpWindow;

    /**
     * Constructor for the banking program.
     */
    public Bank() {
        loginWindow = new JFrame("Login");
        signUpWindow = new JFrame("Sign Up");
        accounts = new ArrayList<Account>();
        uid = 0; 
        init();
        createComponents();
    }

    private void init() {
        setTitle("BankApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
    }

    private void createComponents() {
        createLoginWindow();
        createSignUpWindow();
    }

    private void createLoginWindow() {
        loginWindow.setResizable(false);
        loginWindow.setLocationRelativeTo(null);
        loginWindow.setSize(500, 500);
        loginWindow.setBackground(Color.BLACK);
        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginWindow.setVisible(true);

        JPanel loginPanel = new JPanel();
        GroupLayout loginLayout = new GroupLayout(loginPanel);
        loginPanel.setSize(500, 500);
        loginPanel.setLayout(loginLayout);
        loginPanel.setOpaque(false);
        loginLayout.setAutoCreateGaps(true);
        loginLayout.setAutoCreateContainerGaps(true);

        JTextField usernameField = new JTextField("Username");
        JTextField passwordField = new JTextField("Password");
        JTextArea failMessage = new JTextArea();

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginSuccess = login(usernameField.getText(),
                                                passwordField.getText());
                if (loginSuccess) {
                    loginWindow.setVisible(false);
                    setVisible(true);
                } else {
                    failMessage.setText("Wrong credentials. Try again");
                }
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(Color.ORANGE);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginWindow.setVisible(false);
                signUpWindow.setVisible(true);
            }
        });

        loginLayout.setHorizontalGroup(
            loginLayout.createSequentialGroup()
            .addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING))
                .addComponent(failMessage)
                .addComponent(usernameField)
                .addComponent(passwordField)
                .addComponent(loginButton)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createSequentialGroup()
            .addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(failMessage))
            .addComponent(usernameField)
            .addComponent(passwordField)
            .addComponent(loginButton)
        );

        loginWindow.add(loginPanel);
    }

    private void createSignUpWindow() {
        signUpWindow.setResizable(false);
        signUpWindow.setLocationRelativeTo(null);
        signUpWindow.setSize(500, 500);
        signUpWindow.setBackground(Color.BLACK);
        signUpWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signUpWindow.setVisible(true);

        JPanel signUpPanel = new JPanel();
        GroupLayout signUpLayout = new GroupLayout(signUpPanel);
        signUpPanel.setSize(500, 500);
        signUpPanel.setLayout(signUpLayout);
        signUpPanel.setOpaque(false);
        signUpLayout.setAutoCreateGaps(true);
        signUpLayout.setAutoCreateContainerGaps(true);

        JTextArea failMessage = new JTextArea();
        JTextField firstNameField = new JTextField("First Name");
        JTextField lastNameField = new JTextField("Last Name");
        JTextField usernameField = new JTextField("Username");
        JTextField passwordField = new JTextField("Password");
        JTextField numberField = new JTextField("Phone Number");
        JButton checkingsButton = new JButton("Checking");
        JButton savingsButton = new JButton("Savings");

        boolean signUpSuccess = signUp(firstNameField.getText(), 
                                        lastNameField.getText(), 
                                        usernameField.getText(), 
                                        passwordField.getText(), 
                                        numberField.getText());
        
        checkingsButton.setBackground(Color.RED);
        checkingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (signUpSuccess) {
                    CheckingAccount checkingAccount = new CheckingAccount(firstNameField.getText(),
                                                                            lastNameField.getText(),
                                                                            usernameField.getText(),
                                                                            passwordField.getText(),
                                                                            numberField.getText(),
                                                                            uid++);
                    accounts.add(checkingAccount);
                }
            }
        });

        savingsButton.setBackground(Color.BLUE);
        savingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (signUpSuccess) {
                    SavingsAccount savingsAccount = new SavingsAccount(firstNameField.getText(),
                                                                            lastNameField.getText(),
                                                                            usernameField.getText(),
                                                                            passwordField.getText(),
                                                                            numberField.getText(),
                                                                            uid++);
                    accounts.add(savingsAccount);
                } else {
                    failMessage.setText("");
                }
            }
        });

        signUpWindow.add(signUpPanel);
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
                            String password, String phoneNumber) {
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

    // public void signUp(Scanner scan) {
    //     scan.nextLine();
    //     boolean loop = true;
    //     boolean loop2 = true;
    //     boolean validName, validCreds, uniqueUsername, validNum;
    //     String firstName = "";
    //     String lastName = "";
    //     String username = "";
    //     String password = "";
    //     String phoneNumber = "";

    //     while (loop) {
    //         System.out.print("First Name: ");
    //         firstName = scan.nextLine();
    //         System.out.print("Last name: ");
    //         lastName = scan.nextLine();
    //         System.out.print("Username : ");
    //         username = scan.nextLine();
    //         System.out.print("Password: ");
    //         password = scan.nextLine();
    //         System.out.print("Phone Number: ");
    //         phoneNumber = scan.nextLine();

    //         validName = firstName.length() > 1 && 
    //                             lastName.length() > 1;
    //         validCreds = username.length() > 5 &&
    //                              password.length() > 5;
    //         uniqueUsername = !findAccount(username);
    //         validNum = phoneNumber.length() == 10;
    //         if (validName && validCreds && uniqueUsername && validNum) {
    //             loop = false;
    //         } else {
    //             System.out.println("Invalid credentials. Try again.");
    //         }
    //     }

    //     // User chooses to either make a checking account or a savings account.       
    //     Account newAccount = new Account();
    //     char choice;      
    //     while (loop2) {
    //         System.out.print("Checking (c) or Savings (s)? : ");
    //         choice = scan.next().charAt(0);
    //         switch (choice) {
    //             case 'c':
    //                 loop2 = false;
    //                 newAccount = new CheckingAccount(firstName, lastName, username, password, phoneNumber, uid++);
    //                 break;
    //             case 's':
    //                 loop2 = false;
    //                 newAccount = new SavingsAccount(firstName, lastName, username, password, phoneNumber, uid++);
    //                 break;
    //             default:
    //                 System.out.println("Please enter a valid choice.");
    //                 break;
    //         }
    //     }
        
    //     accounts.add(newAccount);
    //     System.out.println("Signup successful!");
    // }

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

    /**
     * 
     * 
     * @return
     */
    public String createLineBreak() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            sb.append("-");
        }

        return sb.toString();
    }
    
    /**
     * 
     * 
     * @param message
     * @param message2
     * @return
     */
    public String generateMessage(boolean message, boolean message2) {
        StringBuilder sb = new StringBuilder();
        if (message) {
            sb.append("Login (l)\n");
            sb.append("Sign up (s)\n");
        } else {
            sb.append("Login as a different user (l)\n");
            sb.append("Sign up as a different user (s)\n");      
        }

        if (message2) {
            sb.append("Withdraw (w)\n");
            sb.append("Deposit (d)\n");
        }

        sb.append("Exit (x): ");
        return sb.toString();
    }
}
