import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank {

    private List<Account> accounts;

    private Account currentAccount;

    private int uid;

    public Bank() {
        accounts = new ArrayList<Account>();
        uid = 0;   
    }

    // public void exampleMethod() {
    //     Scanner sc = new Scanner(System.in);
    //     System.out.print("First method: ");
    //     String word = sc.nextLine();
    //     exampleMethod2(sc, word);
    //     System.out.println("Closing the scanner");
    //     sc.close();
    // }

    // private void exampleMethod2(Scanner sc, String s) {
    //     System.out.print("Second method: ");
    //     String str = sc.nextLine();
    //     System.out.println(str + " in second method.");
    // }

    // Work on this further later.
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Login (l)\nSign up (s)\nExit (x): ");
        char c = scanner.next().charAt(0);
        boolean loop = true;
        while (loop) {
            if (c == 'l') {

                // User enters their username and password.
                scanner.nextLine();
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                boolean found = login(username, password);

                // Check if they entered the correct username and password.
                if (found) {
                    System.out.println("Welcome, " + currentAccount.getFirstName() + " " + currentAccount.getLastName() + "!");
                    System.out.print("Login as a different user (l)\nSign up as a different user(s)\nExit (x): ");     
                } else {
                    System.out.println("Incorrect credentials. Please try again.");
                    System.out.print("Login (l)\nSign up (s)\nExit (x): ");                  
                }
                
            } else if (c == 's') {
                // Sign up as a new user.
                signUp(scanner);
                System.out.print("Login (l)\nSign up (s)\nExit (x): ");
            } else if (c == 'x') {
                // Close the application.
                // This should be your ONLY exit point.
                loop = false;
                System.out.println("Goodbye!");
                scanner.close();
            } else if (c == 'w' && currentAccount != null) {
             
            } else if (c == 'd' && currentAccount != null) {   

            } else {
                // User did not enter any of the listed choices, so they must try again.
                System.out.print("Please enter a valid answer.\nLogin (l)\nSign up (s)\nExit (x): ");
            }

            // Receive user input once more.
            if (loop) {
                c = scanner.next().charAt(0);
            }            
        }
    }

    // Login
    private boolean login(String username, String password) {       
        int index = 0;
        boolean found = false;

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

    // Sign up
    private void signUp(Scanner scan) {
        scan.nextLine();
        System.out.print("First Name: ");
        String firstName = scan.nextLine();
        System.out.print("Last name: ");
        String lastName = scan.nextLine();
        System.out.print("Username : ");
        String username = scan.nextLine();
        System.out.print("Password: ");
        String password = scan.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scan.nextLine();
        boolean loop = true;      
        // while (loop) {
        //     System.out.print("Checking (c) or Savings (s)? : ");
        //     char choice = scan.next().charAt(0);
        //     if (choice == 'c') {
        //         loop = false;
        //     } else if (choice == 's') {
        //         loop = false;
        //     } else {
        //         System.out.println("Please enter a valid choice.");
        //     }
        // }
        Account newAccount = new Account(firstName, lastName, username, password, phoneNumber, uid++);
        accounts.add(newAccount);
        System.out.println("Signup successful!");
    }

    // Used to transfer money between two different accounts.
    public boolean moneyTransfer(Account sender, Account recipient, double transferAmount) {
        boolean sufficientFunds = false;
        if (sender.getBalance() > 0) {
            recipient.setBalance(recipient.getBalance() + transferAmount);
            sender.setBalance(sender.getBalance() - transferAmount);
            sufficientFunds = true;
        } else {
            System.out.println("Insufficient funds");
        }

        return sufficientFunds;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
}
