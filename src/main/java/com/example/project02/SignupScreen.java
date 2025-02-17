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

    //Call FXMl for Button, TextFields, and PasswordField
    @FXML
    public Button SignupButton;

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField nameTextField;

    @FXML
    public PasswordField passwordTextField;

    //Runs whenever the "Cancel" button is pressed
    public void onSignupButtonPressed(javafx.event.ActionEvent event) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        /* Creates a FileWriter to write to UserData.txt we have "Append" set to true so that FileWriter
        does not overwrite UserData.txt everytime, meaning that you can open the app, store an email
        and then login with that email even after closing and re-opening the app. */
        FileWriter writer = new FileWriter("src/main/resources/UserData.txt", true);
        File file = new File("src/main/resources/UserData.txt"); //Create a File variable for UserData.txt
        Scanner read = new Scanner(file); //Create a scanner to read File

        //Store the email, password, and name from the TextFields
        String emailInput = emailTextField.getText();
        String nameInput = nameTextField.getText();
        String passwordInput = passwordTextField.getText();

        //Encrypt name and email
        String encryptedEmail = encrypt(emailInput, secret);
        String encryptedName = encrypt(nameInput, secret);
        String salt = getSalt(); //Get salt (stored as a Base64 encoded String)
        String line = " "; //Initialize line variable

        /*If there is text in UserData.txt set line equal to that line
        Since all the information is stored in one line this is essentially setting
        line equal to all the user data. */
        if (read.hasNext()) {
            line = read.nextLine();
        }


        if (line.contains(encryptedEmail)) { //If the email in the TextField is found in the document
            loginCase = "Email Already In Use"; //Set loginCase to "Email Already in Use"
        } else {
            /* Writes encryptedEmail, salt, encryptedName, and hashedPassword to UserData.txt
            Separating each value by a comma */
            writer.write(encryptedEmail);
            writer.write(",");

            writer.write(salt);
            writer.write(",");

            writer.write(encryptedName);
            writer.write(",");

            String hashedPassword = hashPassword(passwordInput, salt);
            writer.write(hashedPassword);
            writer.write(",");

            loginCase = "Signup Successful"; //Set loginCase to "Signup Successful"
        }
        System.out.println(loginCase); //Console log loginCase
        switchToStart(event); //Goes to Start-Screen scene

        writer.close();
    }
}
