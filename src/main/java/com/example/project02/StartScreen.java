package com.example.project02;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class StartScreen extends EncryptionMethods {
    private Stage stage;
    private Scene scene;

    @FXML
    public Label nameLabel;

    @FXML
    public Label loginStatusLabel;


    public void displayName(String nameString) {
        nameLabel.setText("Hello: " + nameString);
        loginStatusLabel.setText("Login Successful!");
    }

    public void switchToLogin(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-screen.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignUp(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("signup-screen.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Signup");
        stage.setScene(scene);
        stage.show();
    }
}