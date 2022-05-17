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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class HomepageAdminController implements Initializable {
    public AnchorPane Anchorpane;
    @FXML
    private ImageView hotelImageView;
    @FXML
    private ImageView userImageView;
    @FXML
    private ImageView roomImageView;
    @FXML
    private ImageView poolImageView;
    @FXML
    private ImageView lobbyImageView;


    public void initialize(URL url, ResourceBundle resourceBundle){
        File registerFile =new File("images/download.png");
        Image hotelImage= new Image(registerFile.toURI().toString());
        hotelImageView.setImage(hotelImage);

        File registerFile1=new File("images/user.png");
        Image userImage=new Image(registerFile1.toURI().toString());
        userImageView.setImage(userImage);

        File registerFile2=new File("images/room.jpg");
        Image roomImage=new Image(registerFile2.toURI().toString());
        roomImageView.setImage(roomImage);

        File registerFile3=new File("images/pool.jpg");
        Image poolImage=new Image(registerFile3.toURI().toString());
        poolImageView.setImage(poolImage);

        File registerFile4=new File("images/lobby.jpg");
        Image lobbyImage=new Image(registerFile4.toURI().toString());
        lobbyImageView.setImage(lobbyImage);

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
    private AnchorPane holdPane;
    @FXML
    private Button roomButton;
    @FXML
    private Button billButton;

    @FXML
    private Button dashButton;

    @FXML
    private Button checkinButton;

    @FXML
    private Button checkoutButton;

    @FXML
    private AnchorPane Pane;
    @FXML
    private BorderPane borderpane;

    public void createDashboard(javafx.event.ActionEvent actionEvent){
        goToDashboard();
        Stage stage=(Stage) dashButton.getScene().getWindow();
        stage.close();
    }
    public void createRoomManagement(javafx.event.ActionEvent actionEvent){
        goToRoomManagement();
        Stage stage=(Stage) roomButton.getScene().getWindow();
        stage.close();
    }
    public void goToDashboard(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 720, 675));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void goToRoomManagement(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("room.fxml"));
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
