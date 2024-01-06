import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Account {

    private String name;

    private String username;

    private String password;

    private double balance;

    private String phoneNumber;

    private String email;

    private String address;

    private int id;

    private double annualInterestRate;

    private Date dateOfCreation;

    private List<Transaction> transactions;

    public Account() {
        name = "";
        username = "";
        password = "";
        phoneNumber = "";
        email = "";
        address = "";
        balance = 0;
        id = 0;
        annualInterestRate = 0;
        dateOfCreation = new Date();
        transactions =  new ArrayList<Transaction>();
    }

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
        annualInterestRate = 0;
        transactions =  new ArrayList<Transaction>();
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

    public double getMonthlyInterestRate() {
        return annualInterestRate / 12;
    }

    public double getMonthlyInterest() {
        return balance * (getMonthlyInterestRate() / 100);
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
        System.out.println("Deposited " + depositAmount);
    }

    public void withdraw(double withdrawalAmount) {
        this.balance -= withdrawalAmount;
        System.out.println("Withdrew " + withdrawalAmount);
    }

    public String getTransactionHistory() {
        StringBuilder sb = new StringBuilder();
        String history;
        if (!transactions.isEmpty()) {
            sb.append("Type  :  Amount  :  Date\n");
            for (int i = 0; i < transactions.size(); i++) {
                sb.append(transactions.get(i).toString());
                sb.append("\n");
            }
            history = sb.toString();
        } else {
            history = "You have made no transactions yet.";
        }
        return history;
    }


}
