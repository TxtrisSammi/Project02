package com.example.project02;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SignupScreen extends EncryptionMethods {

    @FXML
    public Button SignupButton;

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField nameTextField;

    @FXML
    public PasswordField passwordTextField;


    public void onSignupButtonPressed(javafx.event.ActionEvent event) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FileWriter writer = new FileWriter("src/main/resources/UserData.txt", true);
        File file = new File("src/main/resources/UserData.txt");
        Scanner read = new Scanner(file);

        String emailInput = emailTextField.getText();
        String passwordInput = passwordTextField.getText();
        String nameInput = nameTextField.getText();

        String encryptedEmail = encrypt(emailInput, secret);
        String encryptedName = encrypt(nameInput, secret);
        String salt = getSalt();
        String line;

            if (read.hasNext()) {
                line = read.nextLine();
            } else {
                line = "";
            }

        if (line.contains(encryptedEmail)) {
            System.out.println("Email Already In Use");
            loginCase = "Email Already In Use";
            switchToStart(event);
        } else {
            writer.write(encryptedEmail);
            writer.write(",");

            writer.write(salt);
            writer.write(",");

            writer.write(encryptedName);
            writer.write(",");

            String passwordHashed = hashPassword(passwordInput, salt);
            writer.write(passwordHashed);
            writer.write(",");

            System.out.println("Signup Successful");
            loginCase = "Signup Successful";
            switchToStart(event);
        }




        writer.flush();
        writer.close();
    }
}
