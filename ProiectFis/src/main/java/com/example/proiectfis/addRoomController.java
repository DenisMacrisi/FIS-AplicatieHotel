package com.example.proiectfis;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.proiectfis.roomController.roomList;
import static com.example.proiectfis.roomController.rooms;

public class addRoomController implements Initializable {

    @FXML
    private Button add;
    @FXML
    private Button remove;

    @FXML
    private TextField number;

    @FXML
    private TextField price;

    @FXML
    private TextField type;

    private Connection connection;

    private DatabaseConnection dbConnection;

    private PreparedStatement pst;
    @FXML
    private ImageView roomnr;
    @FXML
    private ImageView roomtype;
    @FXML
    private ImageView priceimg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();

        File registerFile = new File("images/roomnr.jpg");
        Image roomnr = new Image(registerFile.toURI().toString());
        this.roomnr.setImage(roomnr);
        File registerFile1 = new File("images/bed.png");
        Image roomtype = new Image(registerFile1.toURI().toString());
        this.roomtype.setImage(roomtype);
        File registerFile2 = new File("images/price.png");
        Image priceimg = new Image(registerFile2.toURI().toString());
        this.priceimg.setImage(priceimg);
    }

    public void handleAddAction(javafx.event.ActionEvent actionEvent) {
        String query = "INSERT INTO rooms (roomNumber, roomType, price) VALUES (?,?,?)";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, number.getText());
            pst.setString(2, type.getText());
            pst.setString(3, price.getText());
            roomList.add(new room(Integer.parseInt(number.getText()), Integer.parseInt(price.getText()), type.getText(), "Not Booked"));
            rooms.add(new room(Integer.parseInt(number.getText()), Integer.parseInt(price.getText()), type.getText(), "Not Booked"));
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getBacktoRoomManagement();
    }
    public void getBacktoRoomManagement(){
        Stage stage=(Stage) add.getScene().getWindow();
        stage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("room.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 731, 720));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
   
