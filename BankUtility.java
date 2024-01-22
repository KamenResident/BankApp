import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Utility class for the bank application.
 */
public class BankUtility {
    
    private Bank bankApp;

    /**
     * Constructor for the utility class.
     * 
     * @param theBank is the main bank application for interaction.
     */
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
     * Used to find an account in the bank's database based on the username provided.
     * 
     * @param username is the username provided for searching the user.
     * @return the account with said username, null otherwise.
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

    /**
     * Creates the panel for housing the user's credentials in the secondary tab.
     * 
     * @return a panel for displaying the user's credentials.
     */
    protected JPanel createProfilePanel() {
        // Create the panel for the secondary tab for the user's profile.
        JPanel profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setPreferredSize(new Dimension(450, 450));
        profilePanel.setBackground(Color.ORANGE);
        GridBagConstraints gbc = new GridBagConstraints();
        String[] names = new String[] {"Name:", "Email:", "Number:", "Address:", "Username:", "ID:", "Balance:"};
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        for (int i = 0; i < names.length; i++) {
            JLabel newLabel = new JLabel(names[i], JLabel.CENTER);
            newLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weighty = 0.5;
            newLabel.setForeground(Color.GRAY);
            profilePanel.add(newLabel, gbc);
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.weighty = 0.5;
            profilePanel.add(new JLabel(), gbc);
        }     

        return profilePanel;
    }

    /**
     * Updates the profile depending on at least one credential being changed.
     * 
     * @param account is the user's account whose credentials are being updated.
     */
    protected void updateProfile(Account account) {
        String[] accountCreds = new String[] {account.getName(),
                                            account.getEmail(),
                                            account.getPhoneNumber(),
                                            account.getAddress(), 
                                            account.getUsername(), 
                                            String.format("%d", 
                                                        account.getID()),
                                            String.format("$%,.2f", 
                                                        account.getBalance())};
        JPanel profilePanel = bankApp.getProfilePanel();
        GridBagLayout profileLayout = (GridBagLayout) profilePanel.getLayout();
        GridBagConstraints gbc = profileLayout.getConstraints(profilePanel);
        gbc.gridx = 1;
        int index = 1;
        for (int i = 0; i < accountCreds.length; i++) {
            gbc.gridy = i;
            Component label = profilePanel.getComponent(index);
            profilePanel.remove(label);
            profilePanel.add(new JLabel(accountCreds[i]), gbc, index);
            index += 2;
        }     
    }

    /**
     * Used to update the balance information listed on the profile tab.
     * 
     * @param balance is the newly updated balance.
     */
    protected void updateProfileBalance(double balance) {
        JPanel profilePanel = bankApp.getProfilePanel();
        GridBagLayout profileLayout = (GridBagLayout) profilePanel.getLayout();
        GridBagConstraints gbc = profileLayout.getConstraints(profilePanel);
        gbc.gridx = 1;
        gbc.gridy = 6;
        Component balanceLabel = profilePanel.getComponent(13);
        profilePanel.remove(balanceLabel);
        profilePanel.add(new JLabel(String.format("$%,.2f", balance)), gbc, 13);
    }
}
