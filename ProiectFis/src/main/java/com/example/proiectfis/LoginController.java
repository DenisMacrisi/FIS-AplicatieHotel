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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;

    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile =new File("images/4.jpg");
        Image brandingImage= new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile =new File("images/lock.jpg");
        Image lockImage= new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }
    public void loginButtonOnAction(ActionEvent event){

        loginMessageLabel.setText("You tried to login");
        if(usernameTextField.getText().isBlank()==false && enterPasswordField.getText().isBlank()==false){
            validateLogin();
        }else {
            loginMessageLabel.setText("Please enter your surname and password");
        }
    }
    public void cancelButtonOnAction(ActionEvent event){
        Stage stage=(Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void regButtonOnAction(ActionEvent event){
        createAccountForm();
    }
    public void validateLogin(){
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();
        String verifyLogin="SELECT count(1) FROM user_account WHERE userName='"+usernameTextField.getText()+"' and password ='"+enterPasswordField.getText()+"'";

        try {
            Statement statement =connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(verifyLogin);
            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                    loginMessageLabel.setText("You are logged in");
                }else {
                    loginMessageLabel.setText("Invalid login");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void createAccountForm(){
        try{
            Parent root= FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage=new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root,520,554));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    }
