package guis;

import com.mysql.cj.log.Log;
import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFormGUI extends Form {
    private Runnable onLoginSuccess;
    private Runnable onSwitchToRegister;

    public LoginFormGUI() {
        super("Login");
        addGuiComponents();
    }

    private void addGuiComponents() {
        JLabel loginLabel = new JLabel("Login");

        loginLabel.setBounds(0, 25, 520, 100);

        loginLabel.setForeground(CommonConstants.TEXT_COLOR);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(30, 150, 400, 25);
        usernameLabel.setForeground(CommonConstants.TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JTextField usernameField = new JTextField();
        usernameField.setBounds(30, 185, 450, 55);
        usernameField.setBackground(CommonConstants.SECONDARY_COLOR);
        usernameField.setForeground(CommonConstants.TEXT_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 24));

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(30, 335, 400, 25);
        passwordLabel.setForeground(CommonConstants.TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(30, 365, 450, 55);
        passwordField.setBackground(CommonConstants.SECONDARY_COLOR);
        passwordField.setForeground(CommonConstants.TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(passwordLabel);
        add(passwordField);

        add(usernameLabel);
        add(usernameField);


        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 18));

        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setBackground(CommonConstants.TEXT_COLOR);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get usn
                String username = usernameField.getText();

                // get pass
                String password = new String(passwordField.getPassword());

                // check database
                if (MyJDBC.validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(LoginFormGUI.this,
                            "Login SuccessFul!");

                    // Login successful, invoke success callback
                    if (onLoginSuccess != null) {
                        onLoginSuccess.run();
                    }

                } else {
                    // login failed
                    JOptionPane.showMessageDialog(LoginFormGUI.this,
                            "Login failed, try again.");
                }
            }
        });
        loginButton.setBounds(125, 520, 250, 50);
        add(loginButton);

        JLabel registerLabel = new JLabel("Not a user? Register Here");
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.setForeground(CommonConstants.TEXT_COLOR);

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFormGUI.this.dispose();
                new RegisterFormGUI().setVisible(true);
            }
        });
        registerLabel.setBounds(125, 600, 250, 30);
        add(registerLabel);
    }

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    public void setOnSwitchToRegister(Runnable onSwitchToRegister) {
        this.onSwitchToRegister = onSwitchToRegister;
    }
}
