import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A program that represents a banking program.
 */
public class Bank {

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
     * Constructor for the banking program.
     */
    public Bank() {
        accounts = new ArrayList<Account>();
        uid = 0; 
    }

    /**
     * Used to start the program.
     */
    public void start() {
        // Create our console messages for the program.
        String bankMessage1 = generateMessage(false, true);
        String bankMessage2 = generateMessage(true, false);
        String lineBreak = createLineBreak();  

        // Start accepting user input.
        Scanner scanner = new Scanner(System.in);
        System.out.println(lineBreak);
        System.out.print(bankMessage2);
        char c = scanner.next().charAt(0);

        // Input loop to keep the program going.
        boolean loop = true;
        while (loop) {
            System.out.println(lineBreak);
            if (c == 'l') {

                // User enters their username and password.
                scanner.nextLine();
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                System.out.println(lineBreak);
                boolean found = login(username, password);
                System.out.println(lineBreak);

                // Check if they entered the correct username and password.
                if (found) {
                    System.out.println("Welcome, " + currentAccount.getFirstName() + " " + currentAccount.getLastName() + "!");
                    System.out.print(bankMessage1);   
                } else {
                    System.out.println("Incorrect credentials. Please try again.");
                    System.out.print(bankMessage2);                 
                }
                
            } else if (c == 's') {
                // Sign up as a new user.
                signUp(scanner);
                System.out.println(lineBreak);
                System.out.print(bankMessage2);
            } else if (c == 'x') {
                // Close the application.
                // This should be your ONLY exit point.
                loop = false;
                System.out.println(lineBreak);
                System.out.println("Goodbye!");
                scanner.close();
            } else if (c == 'w' && currentAccount != null) {
                // Withdraw money from your account.
                System.out.print("Enter the amount you want to withdraw: ");
                double withdrawAmount = scanner.nextDouble();
                currentAccount.withdraw(withdrawAmount);
                System.out.print(bankMessage1);             
            } else if (c == 'd' && currentAccount != null) {
                // Deposit money into your account.  
                System.out.print("Enter the amount you want to depsosit: ");
                double depositAmount = scanner.nextDouble();
                currentAccount.deposit(depositAmount);
                System.out.print(bankMessage1);   
            } else if (currentAccount != null) {
                // Logged in user did not enter any of the listed choices, so they must try again.
                System.out.print("Please enter a valid response.\n" + bankMessage1);
            } else {
                // User trying to log in did not enter any of the listed choices, so they must try again.
                System.out.print("Please enter a valid response.\n" + bankMessage2);
            }

            // Receive user input once more.
            if (loop) {
                c = scanner.next().charAt(0);
            }            
        }
    }

    /**
     * 
     * 
     * @param username
     * @param password
     * @return
     */
    private boolean login(String username, String password) {       
        int index = 0;
        boolean found = false;

        // Check if this account is in the database.
        while (!found && index < accounts.size()) {
            Account current = accounts.get(index);
            if (current.getUsername().compareTo(username) == 0 &&
                current.getPassword().compareTo(password) == 0) {
                found = true;
                currentAccount = current;
            } else {
                index++;
            }
        }
        System.out.println("Login successful!");

        return found;
    }

    /**
     * 
     * 
     * @param scan
     */
    private void signUp(Scanner scan) {
        scan.nextLine();
        boolean loop = true;
        boolean loop2 = true;
        boolean validName, validCreds, uniqueUsername, validNum;
        String firstName = "";
        String lastName = "";
        String username = "";
        String password = "";
        String phoneNumber = "";

        while (loop) {
            System.out.print("First Name: ");
            firstName = scan.nextLine();
            System.out.print("Last name: ");
            lastName = scan.nextLine();
            System.out.print("Username : ");
            username = scan.nextLine();
            System.out.print("Password: ");
            password = scan.nextLine();
            System.out.print("Phone Number: ");
            phoneNumber = scan.nextLine();

            validName = firstName.length() > 1 && 
                                lastName.length() > 1;
            validCreds = username.length() > 5 &&
                                 password.length() > 5;
            uniqueUsername = checkUniqueUsername(username);
            validNum = phoneNumber.length() == 10;
            if (validName && validCreds && uniqueUsername && validNum) {
                loop = false;
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        }

        // User chooses to either make a checking account or a savings account.       
        Account newAccount = new Account();
        char choice;      
        while (loop2) {
            System.out.print("Checking (c) or Savings (s)? : ");
            choice = scan.next().charAt(0);
            if (choice == 'c') {
                loop2 = false;
                newAccount = new CheckingAccount(firstName, lastName, username, password, phoneNumber, uid++);
            } else if (choice == 's') {
                loop2 = false;
                newAccount = new SavingsAccount(firstName, lastName, username, password, phoneNumber, uid++);
            } else {
                System.out.println("Please enter a valid choice.");
            }
        }
        
        accounts.add(newAccount);
        System.out.println("Signup successful!");
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
     * @param username
     * @return
     */
    private boolean checkUniqueUsername(String username) {
        boolean unique = true;
        for (int i = 0; i < accounts.size(); i++) {
            if (username.compareTo(accounts.get(i).getUsername()) == 0) {
                unique = false;
            }
        }
        return unique;
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
    private String generateMessage(boolean message, boolean message2) {
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
