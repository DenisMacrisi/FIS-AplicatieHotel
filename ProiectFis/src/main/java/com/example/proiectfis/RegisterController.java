package com.example.proiectfis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
public class RegisterController implements Initializable {
    @FXML
    private ImageView registerImageView;
    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel1;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label firsttNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label userNameLabel;

    public void initialize(URL url, ResourceBundle resourceBundle){
        File registerFile =new File("images/reg2.png");
        Image registerImage= new Image(registerFile.toURI().toString());
        registerImageView.setImage(registerImage);

    }
    public void registerButtonOnAction(ActionEvent event){
        registerUser();
        if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            confirmPasswordLabel.setText("");


        }else{
            confirmPasswordLabel.setText("Passwords do not match");
        }

    }
    public void closeButtonOnAction(ActionEvent event){
        Stage stage=(Stage) closeButton.getScene().getWindow();
        stage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 546, 407));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }
    @FXML
    private Button registerButton;
    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();
        String type="user";
        String insertFields = "INSERT INTO user_account(firstName, lastName, userName, password,account_type) VALUES('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "','" + type + "')";
        String insertToRegister = insertFields + insertValues;
        if(firstnameTextField.getText().isBlank()==true){
            firsttNameLabel.setText("Please enter your first name.");
        }
        if(lastnameTextField.getText().isBlank()==true){
            lastNameLabel.setText("Please enter your last name.");
        }
        if(usernameTextField.getText().isBlank()==true){
            userNameLabel.setText("Please enter your username.");
        }
        if(setPasswordField.getText().isBlank()==true){
            passwordLabel.setText("Please enter your password.");
        }
        if(confirmPasswordField.getText().isBlank()==true){
            confirmPasswordLabel.setText("Please confirm your password.");
        }

        if (setPasswordField.getText().equals(confirmPasswordField.getText()) && firstnameTextField.getText().isBlank()==false && lastnameTextField.getText().isBlank()==false && usernameTextField.getText().isBlank()==false
        && setPasswordField.getText().isBlank()==false && confirmPasswordField.getText().isBlank()==false) {
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertToRegister);
                if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
                    registrationMessageLabel1.setText("User registered successfully");
                    Stage stage=(Stage) registerButton.getScene().getWindow();
                    stage.close();
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                        Stage registerStage = new Stage();
                        registerStage.initStyle(StageStyle.UNDECORATED);
                        registerStage.setScene(new Scene(root, 546, 407));
                        registerStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getCause();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();

            }
        }

    }
}

