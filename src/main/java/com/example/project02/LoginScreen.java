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

    //call FXML for fields and button
    @FXML
    public Button loginButton;

    @FXML
    public PasswordField passwordField;

    @FXML
    public javafx.scene.control.TextField emailField;

    public void onLoginButtonPressed(javafx.event.ActionEvent event) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        File file = new File("src/main/resources/UserData.txt"); //Create a File variable for UserData.txt
        Scanner read = new Scanner(file); //Scanner to read File

        //Get email and password input from the TextFields
        String emailInput = emailField.getText();
        String passwordInput = passwordField.getText();

        //Encrypt email input
        String encryptedEmailInput = encrypt(emailInput, secret);

        //Since the data is stored seperated by commas
        //We can use "," as a delimiter to parse through the different stored values
        read.useDelimiter(",");

        //Loops until there is no more data stored
        while (read.hasNext()) {
            String line = read.next(); //Stores current read as variable "Line"
            //Makes the reader keep reading until it comes across the encrypted email
            if (line.equals(encryptedEmailInput)) {
                String salt = read.next(); //Stores salt in the file as a variable
                String name = read.next(); //Stores name in the file as a variable
                nameString = decrypt(name, secret); //Decrypts the name value to get the name as a clearText String

                //Since hashes can't be reversed we hash the passwordInput using the salt
                //associated with the email typed in
                String passwordHash = hashPassword(passwordInput, salt);
                String storedPassword = read.next(); //Stores hashed password in the file as a variable
                //If the hashed password input matches the stored hashed password, login successfully
                if (passwordHash.equals(storedPassword)) {
                    loginCase = "Login Successful"; //Sets loginCase to "Login Successful"
                } else {
                    loginCase = "Incorrect Password"; //Sets the loginCase variable to "Incorrect Password"

                }
            }
        }
        /*
        Put in an if-loop to avoid a NullPointerException
        This code block runs after the Scanner has read everything in the file
        If the code reads everything and login has not been successful
        Then the email was incorrect because the email was not in the
        UserData.txt file.
        */
        if (!(loginCase.equals("Login Successful") || loginCase.equals("Incorrect Password"))) {
            loginCase = "Incorrect Email"; //Set loginCase to "Incorrect email"
        }

        System.out.println(loginCase); //Console log loginCase
        switchToStart(event); //Goes to Start-Screen scene

    }
}



