package com.example.proiectfis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class HomepageUserController implements Initializable {
    @FXML
    private ImageView hotelImageView;
    @FXML
    private ImageView userImageView;


    public void initialize(URL url, ResourceBundle resourceBundle){
        File registerFile =new File("images/download.png");
        Image hotelImage= new Image(registerFile.toURI().toString());
        hotelImageView.setImage(hotelImage);
        File registerFile1=new File("images/user.png");
        Image userImage=new Image(registerFile1.toURI().toString());
        userImageView.setImage(userImage);
    }
    Stage stage;
    private AnchorPane scenePane;
    @FXML
    private Button logoutButton;

    Stage primaryStage;
    public void logoutButtonOnAction(ActionEvent event){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        if(alert.showAndWait().get()== ButtonType.OK){
            stage=(Stage) logoutButton.getScene().getWindow();
            System.out.println("You successfully logged out");
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
    }

    @FXML
    private Button availableButton;

    public void createAvailableRooms(javafx.event.ActionEvent actionEvent){
        goToAvailableRooms();
        Stage stage=(Stage) availableButton.getScene().getWindow();
        stage.close();
    }
    public void goToAvailableRooms(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("availableRooms.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 720, 731));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }


    }
}
