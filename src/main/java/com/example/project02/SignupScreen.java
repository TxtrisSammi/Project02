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

public class SignupScreen extends EncryptionMethods {

    @FXML
    public Button SignupButton;

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField nameTextField;

    @FXML
    public PasswordField passwordTextField;

    public void switchToStart(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-screen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome to The Application!");
        stage.setScene(scene);
        stage.show();
    }

    public void onSignupButtonPressed(javafx.event.ActionEvent event) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FileWriter writer = new FileWriter("src/main/resources/UserData.txt");
        System.out.println("Signup");
        String emailInput = emailTextField.getText();
        String passwordInput = passwordTextField.getText();
        String nameInput = nameTextField.getText();

        String secret = ")@#&HDFUSDYF()";
        String encrypted = encrypt(emailInput, secret);



        writer.write(encrypted);
        writer.write(",");


        String salt = getSalt();
        writer.write(salt);
        writer.write(",");

        String passwordHashed = hashPassword(passwordInput, salt);
        writer.write(passwordHashed);
        writer.write(",");


        writer.write(nameInput);
        writer.write("\n");

        writer.flush();
        writer.close();
    }
}
