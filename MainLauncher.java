import java.util.Scanner;

public class MainLauncher {
 
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Create our console messages for the program.
        String bankMessage1 = bank.generateMessage(false, true);
        String bankMessage2 = bank.generateMessage(true, false);
        String lineBreak = bank.createLineBreak();  

        // Start accepting user input.
        Scanner scanner = new Scanner(System.in);
        System.out.println(lineBreak);
        System.out.print(bankMessage2);
        char c = scanner.next().charAt(0);

        // Input loop to keep the program going.
        boolean loop = true;
        while (loop) {
            System.out.println(lineBreak);
            // lsxwd
            switch (c) {
                case 'l':
                    // User enters their username and password.
                    scanner.nextLine();
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.println(lineBreak);
                    boolean found = bank.login(username, password);
                    System.out.println(lineBreak);

                    // Check if they entered the correct username and password.
                    if (found) {
                        System.out.println("Welcome, " + 
                                            bank.getCurrentAccount().getFirstName() + 
                                            " " + bank.getCurrentAccount().getLastName() + "!");
                        System.out.print(bankMessage1);   
                    } else {
                        System.out.println("Incorrect credentials. Please try again.");
                        System.out.print(bankMessage2);                 
                    }
                    break;
                case 's':
                    // Sign up as a new user.
                    bank.signUp(scanner);
                    System.out.println(lineBreak);
                    System.out.print(bankMessage2);
                    break;
                case 'x':
                    // Close the application.
                    // This should be your ONLY exit point.
                    loop = false;
                    System.out.println(lineBreak);
                    System.out.println("Goodbye!");
                    scanner.close();
                    break;
                case 'w':
                    // Withdraw money from your account.
                    if (bank.getCurrentAccount() != null) {
                        System.out.print("Enter the amount you want to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        bank.getCurrentAccount().withdraw(withdrawAmount);
                        System.out.print(bankMessage1); 
                    } else {
                        System.out.print("Please enter a valid response.\n" + bankMessage2);
                    }
                    break;
                case 'd':
                    // Deposit money into your account.  
                    if (bank.getCurrentAccount() != null) {
                        System.out.print("Enter the amount you want to depsosit: ");
                        double depositAmount = scanner.nextDouble();
                        bank.getCurrentAccount().deposit(depositAmount);
                        System.out.print(bankMessage1);
                    } else {
                        System.out.print("Please enter a valid response.\n" + bankMessage2);
                    }
                    break;
                default:
                    if (bank.getCurrentAccount() != null) {
                        // Logged in user did not enter any of the listed choices, so they must try again.
                        System.out.print("Please enter a valid response.\n" + bankMessage1);
                    } else {
                        // User trying to log in did not enter any of the listed choices, so they must try again.
                        System.out.print("Please enter a valid response.\n" + bankMessage2);
                    }
                    break;
            }

            // Receive user input once more.
            if (loop) {
                c = scanner.next().charAt(0);
            }                       
        }

    }
}