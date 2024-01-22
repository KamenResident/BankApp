import java.time.LocalDateTime;

/**
 * Represents a transaction that a user has made.
 * This transaction can either be a withdrawal, deposit, or transfer of money.
 */
public class Transaction {

    private LocalDateTime dateOfTransaction;
    private String transactionType;
    private double transactionAmount;

    /**
     * Constructor for a transaction.
     * 
     * @param transactionType is the type of transaction being made.
     * @param transactionAmount is the amount of money involved in the transaction.
     * @param dateOfTransaction is the date of when the transaction was made.
     */
    public Transaction(String transactionType, double transactionAmount, LocalDateTime dateOfTransaction) {
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.dateOfTransaction = dateOfTransaction;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public LocalDateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    /**
     * Returns the transaction's information by listing its type,
     * amount, and date.
     * 
     * @return the transaction's information.
     */
    @Override
    public String toString() {
        return String.format("%s : $%,.2f : %s", transactionType, 
                                                    transactionAmount, 
                                                    dateOfTransaction);
    }
    
}
