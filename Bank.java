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

    /**
     * 
     * 
     * @param scan
     */
    public void signUp(Scanner scan) {
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
            uniqueUsername = !findAccount(username);
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
            switch (choice) {
                case 'c':
                    loop2 = false;
                    newAccount = new CheckingAccount(firstName, lastName, username, password, phoneNumber, uid++);
                    break;
                case 's':
                    loop2 = false;
                    newAccount = new SavingsAccount(firstName, lastName, username, password, phoneNumber, uid++);
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
                    break;
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
