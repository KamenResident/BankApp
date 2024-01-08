import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {

    private Bank bank;

    public LoginWindow(Bank bank) {
        this.bank = bank;
        init();
        createComponents();
    }

    private void init() {
        setTitle("BankApp");
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(500, 500);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createComponents() {
        JPanel loginPanel = new JPanel();
        GridBagLayout loginLayout = new GridBagLayout();
        loginPanel.setLayout(loginLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        loginPanel.setBackground(Color.YELLOW);
        loginPanel.setMaximumSize(new Dimension(350,  350));

        JLabel loginLabel = new JLabel("", JLabel.CENTER);
        loginLabel.setText("Log in with your credentials.");
        Font header = new Font("serif", Font.PLAIN, 24);
        loginLabel.setFont(header);
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();    
        usernameField.setMaximumSize(new Dimension(200, 30));
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JTextField();
        passwordField.setMaximumSize(new Dimension(200, 30));
        JButton loginButton = new JButton("Login");
        loginButton.setMaximumSize(new Dimension(100, 40));
        loginButton.setBackground(Color.GREEN);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginSuccess = bank.login(usernameField.getText(),
                                                passwordField.getText());
                if (loginSuccess) {
                    setVisible(false);
                    bank.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(loginButton, "Wrong credentials.", 
                                                                "Error", 1);
                }
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setMaximumSize(new Dimension(100, 40));
        signUpButton.setBackground(Color.ORANGE);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                bank.manageWindows(0);
            }
        });

        Font mainText = new Font("serif", Font.PLAIN, 18);
        usernameLabel.setFont(mainText);
        usernameField.setFont(mainText);
        passwordLabel.setFont(mainText);
        passwordField.setFont(mainText);
        loginButton.setFont(mainText);
        signUpButton.setFont(mainText);

        add(loginLabel);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;       
        addComponent(loginPanel, usernameLabel, 0, 0, gbc);
        addComponent(loginPanel, usernameField, 1, 0, gbc);
        addComponent(loginPanel, passwordLabel, 0,  1, gbc);
        addComponent(loginPanel, passwordField, 1,  1, gbc);
        addComponent(loginPanel, loginButton,  0, 2, gbc);
        addComponent(loginPanel, signUpButton, 1,  2, gbc);

        add(loginPanel);
    }

    private void addComponent(Container parent, Component child, int x, int y, 
                                GridBagConstraints constraints) {
        // constraints.weightx = 1;
        // constraints.weighty = 1;
        constraints.ipadx = 50;
        constraints.ipady = 50;
        constraints.gridx = x;
        constraints.gridy = y;
        parent.add(child, constraints);
    }
}
