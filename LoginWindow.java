import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Window that facilitates the interactions with the user
 * for logging in.
 * Users also have to option of switch over to signing up.
 */
public class LoginWindow extends JFrame {

    private Bank bank;

    /**
     * Constructor used to create the window.
     * 
     * @param bank is the main bank application using this window.
     */
    protected LoginWindow(Bank bank, String title) {
        super(title);
        this.bank = bank;
        init();
        createComponents();
    }

    private void init() {       
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(500, 500);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        getContentPane().setBackground(Color.GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createComponents() {
        // Create the main panel for the window.
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Border panelBorder = BorderFactory.createLineBorder(Color.BLACK);
        loginPanel.setBackground(Color.YELLOW);
        loginPanel.setMaximumSize(new Dimension(350,  350));
        loginPanel.setBorder(panelBorder);

        // Create the labels, fields, and buttons for logging in.
        JLabel loginLabel = new JLabel("Log in with your credentials.", JLabel.CENTER);
        loginLabel.setForeground(Color.WHITE);
        Font header = new Font("serif", Font.PLAIN, 24);
        loginLabel.setFont(header);
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();    
        usernameField.setPreferredSize(new Dimension(200, 30));
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(200, 30));

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.setBackground(Color.GREEN);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginSuccess = login(usernameField.getText(),
                                                passwordField.getText());
                if (loginSuccess) {
                    usernameField.setText("");
                    passwordField.setText("");
                    setVisible(false);
                    bank.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(loginButton, "Wrong credentials.", 
                                                                "Error", 1);
                }
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setPreferredSize(new Dimension(100, 40));
        signUpButton.setBackground(Color.ORANGE);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                bank.manageWindows(0);
            }
        });

        // Create fonts for the components for better visibility.
        Font mainText = new Font("serif", Font.PLAIN, 18);
        usernameLabel.setFont(mainText);
        usernameField.setFont(mainText);
        passwordLabel.setFont(mainText);
        passwordField.setFont(mainText);
        Font buttonText = new Font("serif", Font.PLAIN, 25);
        loginButton.setFont(buttonText);
        signUpButton.setFont(buttonText);

        // Add the components to the panel in a grid bag configuration.
        gbc.fill = GridBagConstraints.HORIZONTAL;      
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);
        gbc.ipadx = 25;
        gbc.ipady = 25;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(loginButton, gbc);
        gbc.ipadx = 25;
        gbc.ipady = 25;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(signUpButton, gbc);

        add(loginLabel);
        add(loginPanel);
    }

    private boolean login(String username, String password) {       
        int index = 0;
        boolean found = false;

        // Check if this account is in the database.
        List<Account> accounts = bank.getAccounts();
        while (!found && index < accounts.size()) {
            Account current = accounts.get(index);
            if (current.getUsername().compareTo(username) == 0 &&
                current.getPassword().compareTo(password) == 0) {
                found = true;
                bank.setCurrentAccount(current);
                bank.getBankUtils().updateProfile(current);
            } else {
                index++;
            }
        }

        return found;
    }

}
