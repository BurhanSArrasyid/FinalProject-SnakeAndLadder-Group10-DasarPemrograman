/**
 * -----------------------------------------------------
 * ES234211 - Programming Fundamental
 * Genap - 2023/2024
 * Group Capstone Project: Snake and Ladder Game
 * -----------------------------------------------------
 * Class    : D
 * Group    : 10
 * Members  :
 * 1. 5026231074 - Burhan Shidqi Arrasyid
 * 2. 5026231106 - Nailah Qonitah Firdausa
 * 3. 5026231231 - Sinta Dewi Rahmawati
 * ------------------------------------------------------
 */

import db.MyJDBC;
import guis.LoginFormGUI;
import guis.RegisterFormGUI;

import javax.swing.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the login form and set the callback for successful login
                LoginFormGUI loginForm = new LoginFormGUI();
                RegisterFormGUI registerForm = new RegisterFormGUI();

                loginForm.setOnLoginSuccess(new Runnable() {
                    @Override
                    public void run() {
                        loginForm.dispose();
                        startGame();
                    }
                });
                loginForm.setOnSwitchToRegister(new Runnable() {
                    @Override
                    public void run() {
                        loginForm.setVisible(false);
                        registerForm.setVisible(true);
                    }
                });
                loginForm.setVisible(true);

                registerForm.setOnRegisterSuccess(new Runnable() {
                    @Override
                    public void run() {
                        registerForm.dispose();
                        startGame();
                    }
                });
                registerForm.setOnSwitchToLogin(new Runnable() {
                    @Override
                    public void run() {
                        registerForm.setVisible(false);
                        loginForm.setVisible(true);
                    }
                });
            }
        });
    }

    private static void startGame() {
        // Game Initialization
        SnakeAndLadder g1 = new SnakeAndLadder(100);
        g1.play();
    }
}
