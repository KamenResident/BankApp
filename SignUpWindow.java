import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SignUpWindow extends JFrame {

    private Bank bank;

    public SignUpWindow(Bank bank) {
        this.bank = bank;
        init();
        createComponents();
    }

    private void init() {
        setTitle("Sign Up");
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(500, 500);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void createComponents() {
        JPanel signUpPanel = new JPanel();
        GridBagLayout signUpLayout = new GridBagLayout();
        Border panelBorder = BorderFactory.createLineBorder(Color.BLACK);
        signUpPanel.setLayout(signUpLayout);
        signUpPanel.setMaximumSize(new Dimension(350,  350));
        signUpPanel.setBackground(Color.YELLOW);
        signUpPanel.setBorder(panelBorder);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextArea failMessage = new JTextArea();

        String[] names = new String[] { "First Name", "Last Name", "Username", 
                                                                    "Password", 
                                                                    "Phone Number", 
                                                                    "Email", 
                                                                    "State", 
                                                                    "City", 
                                                                    "ZIP" };
        JLabel[] labels = new JLabel[9];
        JTextField[] fields = new JTextField[9];
        String[] credentials = new String[9];
        gbc.fill = GridBagConstraints.HORIZONTAL;   
        for (int i = 0; i < labels.length; i++) {
            JLabel newLabel = new JLabel(names[i], JLabel.CENTER);
            newLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            labels[i] = newLabel;
            gbc.gridx = 0;
            gbc.gridy = i;
            signUpPanel.add(labels[i], gbc);
            JTextField newField = new JTextField();
            newField.setPreferredSize(new Dimension(200, 30));
            fields[i] = newField;
            gbc.gridx = 1;
            gbc.gridy = i;
            signUpPanel.add(fields[i], gbc);
            credentials[i] = fields[i].getText();
        }

        JPanel buttonPanel = new JPanel();
        FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER);
        buttonPanel.setLayout(buttonLayout);
        buttonPanel.setMaximumSize(new Dimension(350, 50));
        buttonPanel.setBackground(Color.ORANGE);
        buttonPanel.setBorder(panelBorder);

        JButton checkingsButton = new JButton("Checking");
        JButton savingsButton = new JButton("Savings");

        addButtonListener(checkingsButton, credentials, 0,
                                            Color.GREEN,
                                            failMessage);
        addButtonListener(savingsButton, credentials, 1,
                                            Color.YELLOW,
                                            failMessage);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.RED);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                bank.manageWindows(1);
            }
        });

        buttonPanel.add(checkingsButton);
        buttonPanel.add(savingsButton);
        buttonPanel.add(backButton);
        JLabel signUpLabel = new JLabel("Sign up as a new user.", JLabel.CENTER);
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font header = new Font("serif", Font.PLAIN, 24);
        signUpLabel.setFont(header);

        add(signUpLabel);
        add(signUpPanel);
        add(buttonPanel);
    }

    private void addButtonListener(JButton button, 
                                    String[] userCredentials, 
                                    int choice, 
                                    Color color, 
                                    JTextArea textArea) {
        button.setBackground(color);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean signUpSuccess = bank.signUp(userCredentials, choice);
                if (signUpSuccess) {
                    setVisible(false);
                    bank.manageWindows(1);
                } else {
                    textArea.setText("Incorrect Credentials");
                }
            }

        });
    }
    
}
