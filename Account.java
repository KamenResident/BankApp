import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a user's bank account, which is typically a checking account.
 */
public class Account {

    private String name; 
    private String username;
    private String password;
    private double balance;
    private double overdraftLimit;
    private String phoneNumber;
    private String email;
    private String address;
    private int id;
    private int transactionCount;
    private double annualInterestRate;
    private Date dateOfCreation;
    private List<Transaction> transactions;
    private boolean exceedLimit;

    /**
     * Parameter-less constructor for a bank account.
     */
    public Account() {
        name = "";
        username = "";
        password = "";
        phoneNumber = "";
        email = "";
        address = "";
        balance = 0;
        id = 0;
        overdraftLimit = 50;
        annualInterestRate = 0;
        dateOfCreation = new Date();
        transactions =  new ArrayList<Transaction>();
        exceedLimit = false;
    }

    /**
     * Paramterized constructor for a bank account.
     * 
     * @param firstName is the user's first name.
     * @param lastName is the user's last name.
     * @param username is the user's username.
     * @param password is the user's password.
     * @param phoneNumber is the user's phone number.
     * @param email is the user's email.
     * @param address is the user's physical address.
     * @param id is the user's ID.
     */
    public Account(String firstName, String lastName, String username, 
                                                        String password, 
                                                        String phoneNumber,
                                                        String email,
                                                        String address, 
                                                        int id) {
        name = String.format("%s %s", firstName, lastName);
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.balance = 0;
        this.id = id;
        overdraftLimit = 50;
        annualInterestRate = 0;
        transactionCount = 0;
        transactions =  new ArrayList<Transaction>();
        exceedLimit = false;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public boolean getExceedLimit() {
        return exceedLimit;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double newRate) {
        annualInterestRate = newRate;
    }

    /**
     * Retrieves the monthly interest rate based on the
     * annual interest rate.
     * 
     * @return the monthly interest rate.
     */
    public double getMonthlyInterestRate() {
        return annualInterestRate / 12;
    }

    /**
     * Retrieves the monthly interest based on the monthly interest rate.
     * 
     * @return the monthly interest.
     */
    public double getMonthlyInterest() {
        return balance * (getMonthlyInterestRate() / 100);
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void addTransaction(Transaction newTransaction) {
        transactions.add(newTransaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double depositAmount) {
        this.balance += depositAmount;
        Transaction depositTransaction = new Transaction("Deposit", 
                                                            depositAmount, 
                                                            LocalDateTime.now());
        transactions.add(depositTransaction);
        transactionCount++;
    }

    /**
     * Used to withdraw money from the account owner's balance.
     * The withdrawal amount must be less than the current balance and
     * not exceed the overdraft limit for a successful transaction.
     * 
     * @param withdrawalAmount is the amount being withdrawn.
     * @return true if the transaction is successful, false otherwise.
     */
    public boolean withdraw(double withdrawalAmount) {
        boolean sufficientFunds = false;
        double difference = this.balance - withdrawalAmount;
        if (withdrawalAmount < this.balance && difference > overdraftLimit) {
            this.balance -= withdrawalAmount;
            sufficientFunds = true;
            Transaction withdrawTransaction = new Transaction("Withdraw", 
                                                                withdrawalAmount,
                                                                LocalDateTime.now());
            transactions.add(withdrawTransaction);
            transactionCount++;
        } else if (difference <= overdraftLimit) {
            exceedLimit = true;
        }   

        return sufficientFunds;
    }

    /**
     * Retrieves a history of all the transactions the account owner has made.
     * All the transactions will be listed with account type, transaction amount,
     * and date of transaction.
     * 
     * @return a record of all transactions made by the account owner.
     */
    public String getTransactionHistory() {
        StringBuilder sb = new StringBuilder();
        String history;
        if (!transactions.isEmpty()) {
            sb.append(name + "\n");
            sb.append("Type  :  Amount  :  Date\n");
            for (int i = 0; i < transactions.size(); i++) {
                sb.append(transactions.get(i).toString());
                sb.append("\n");
            }
            history = sb.toString();
        } else {
            history = "No transactions made yet.";
        }
        return history;
    }

}
