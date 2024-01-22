import java.time.LocalDateTime;
import java.util.List;

public class BankUtility {
    
    private Bank bankApp;

    public BankUtility(Bank theBank) {
        bankApp = theBank;
    }

    /**
     * Checks to ensure the password meets the requirements.
     * The password must have more than 6 characters and at least
     * one of the following: a digit, special character, uppercase letter,
     *                       and a lowercase letter.
     * 
     * @param password is the password the user intends to use for their account.
     * @return true if the password meets all the requirements, false otherwise.
     */
    protected boolean checkPassword(String password) {
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

        /**
     * Used to help transfer money between two accounts.
     * 
     * @param sender is the account sending the money.
     * @param recipient is the account receiving the money.
     * @param transferAmount is the amount of money being transferred.
     * @return true if the transfer is successful, false otherwise.
     */
    protected boolean moneyTransfer(Account sender, Account recipient, double transferAmount) {
        boolean sufficientFunds = false;
        if (!sender.equals(recipient) 
            && sender.getBalance() > transferAmount 
            && sender.withdraw(transferAmount)) {
            recipient.deposit(transferAmount);
            Transaction transferTransaction = new Transaction("Transfer", 
                                                                transferAmount, 
                                                                LocalDateTime.now());
            sender.addTransaction(transferTransaction);
            recipient.addTransaction(transferTransaction);
            sufficientFunds = true;
        }

        return sufficientFunds;
    }

    /**
     * 
     * 
     * @param username
     * @return
     */
    protected Account findAccount(String username) {
        Account searchedAccount = null;
        boolean found = false;
        int i = 0;
        List<Account> accounts = bankApp.getAccounts();
        if (!accounts.isEmpty() && username.length() > 5) {
            while (!found && i < accounts.size()) {
                if (accounts.get(i).getUsername().compareTo(username) == 0) {
                    found = true;
                    searchedAccount = accounts.get(i);
                } else {
                    i++;
                }
            }
        }
        return searchedAccount;
    }
}
