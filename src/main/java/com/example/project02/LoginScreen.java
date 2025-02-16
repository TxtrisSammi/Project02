package com.example.project02;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class LoginScreen extends EncryptionMethods {

    @FXML
    public Button loginButton;

    @FXML
    public PasswordField passwordField;

    @FXML
    public javafx.scene.control.TextField emailField;

    public void onLoginButtonPressed(javafx.event.ActionEvent event) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        File file = new File("src/main/resources/UserData.txt");
        Scanner read = new Scanner(file);

        String email = emailField.getText();
        String password = passwordField.getText();

        String encryptedEmail = encrypt(email, secret);


        read.useDelimiter(encryptedEmail);

        read.useDelimiter(",");

        while (read.hasNext()) {
            String line = read.next();
            if (line.equals(encryptedEmail)) {
                String salt = read.next();
                String name = read.next();
                nameString = decrypt(name, secret);

                String passwordHash = hashPassword(password, salt);
                System.out.println(passwordHash);
                String storedPassword = read.next();
                System.out.println("\n" + storedPassword);
                if (passwordHash.equals(storedPassword)) {
                    System.out.println("login Successful");
                    loginCase = "login Successful";
                    switchToStart(event);
                } else {
                    System.out.println("Incorrect Password");
                    loginCase = "Incorrect Password";
                    switchToStart(event);
                }
            }
        }
        System.out.println("Incorrect Email");
        loginCase = "Incorrect Email";
        switchToStart(event);
    }
}



