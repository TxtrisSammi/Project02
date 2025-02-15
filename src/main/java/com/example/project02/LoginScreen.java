package com.example.project02;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
        read.useDelimiter(",");

        String email = emailField.getText();
        String password = passwordField.getText();

        String secret = ")@#&HDFUSDYF()";
        String encryptedEmail = encrypt(email, secret);

        do {
            String line = read.next();
            if (line.contains(encryptedEmail)) {
                String salt = read.next();
                String passwordHash = hashPassword(password, salt);

                if (passwordHash.equals(read.next())) {
                    System.out.println("login Successful");
                    loginStatus = true;
                    nameString = read.next();

                    switchToStart(event);
                } else {
                    System.out.println("Incorrect Password");
                    System.exit(0);
                }
            } else {
                System.out.println("Incorrect Email");
                System.exit(0);
            }
        } while (read.hasNextLine());
    }


    public void switchToStart(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-screen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome to The Application!");
        stage.setScene(scene);
        stage.show();
        if (loginStatus) {
            StartScreen startScreenController = fxmlLoader.getController();
            startScreenController.loginStatusLabel.setText("Login Successful");
            startScreenController.nameLabel.setText("Hello: " + nameString);
        }

    }
}
