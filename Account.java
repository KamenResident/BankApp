import java.util.Date;
import java.util.List;
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
    private double totalInterest;
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
        totalInterest = 0;
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

    public double getTotalInterest() {
        return totalInterest;
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

    public int getID() {
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
     * Calculates the accumulation of the account owner's monthly interest.
     */
    public void accrueMonthlyInterest() {
        double monthlyInterest = balance * getMonthlyInterestRate();
        totalInterest += monthlyInterest;
        balance += monthlyInterest;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void updateTransactionCount() {
        transactionCount++;
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

    /**
     * Used to deposit money into the account owner's balance.
     * As long as the amount is greater than $0, the deposit will be processed.
     * However, if the owner has exceeded the overdraft limit, then a $25
     * penalty fee will be applied until their balance is under the limit.
     * 
     * @param depositAmount is the amount of money being deposited.
     * @return true if the deposit is successful, false otherwise.
     */
    public boolean deposit(double depositAmount) {
        boolean depositSuccess = false;
        if (exceedLimit) {
            depositAmount -= 25;
        }

        if (depositAmount > 0) {
            depositSuccess = true;           
            this.balance += depositAmount;
            if (this.balance > overdraftLimit) {
                exceedLimit = false;
            }
        }

        return depositSuccess;
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
        if (withdrawalAmount > 0 && !exceedLimit) {
            this.balance -= withdrawalAmount;
            sufficientFunds = true;
        }
        
        if (difference <= overdraftLimit) {
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
