package com.example.proiectfis;

import javafx.event.ActionEvent;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label avaRoom;

    @FXML
    private Label bookedRoom;

    @FXML
    private Label earning;

    @FXML
    private Label pending;

    @FXML
    private Label totalRoom;

    private Connection connection;

    private DatabaseConnection dbConnection;

    private PreparedStatement pst;
    @FXML
    private ImageView bedImageView;
    @FXML
    private ImageView notImageView;
    @FXML
    private ImageView yesImageView;
    @FXML
    private ImageView paidImageView;
    @FXML
    private ImageView pendingImageView;

    @FXML
    private Button logoutButton;
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File registerFile = new File("images/bed.png");
        Image bedImage = new Image(registerFile.toURI().toString());
        bedImageView.setImage(bedImage);

        File registerFile1 = new File("images/notavb.jpg");
        Image notavbImage = new Image(registerFile1.toURI().toString());
        notImageView.setImage(notavbImage);

        File registerFile2 = new File("images/avb.jpg");
        Image yesImage = new Image(registerFile2.toURI().toString());
        yesImageView.setImage(yesImage);

        File registerFile3 = new File("images/hai.jpg");
        Image paidImage = new Image(registerFile3.toURI().toString());
        paidImageView.setImage(paidImage);

        File registerFile4 = new File("images/rtvf.png");
        Image pendingImage = new Image(registerFile4.toURI().toString());
        pendingImageView.setImage(pendingImage);


        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
        String query = "SELECT COUNT(roomNumber) AS totalRooms, a.totalNotBooked, booked.totalBooked FROM rooms, " +
                "(SELECT COUNT(roomNumber) AS totalBooked FROM rooms WHERE status = 'Booked') AS booked, " +
                "(SELECT COUNT(roomNumber) AS totalNotBooked FROM rooms WHERE status = 'Not Booked') AS a";
        String query2 = "SELECT SUM(b.billAmount) AS totalEarnings, (SELECT SUM((r.price * DATEDIFF(res.checkOutDate, res.checkInDate))) AS Pending FROM reservations res \n" +
                "INNER JOIN rooms r ON r.roomNumber = res.roomNumber \n" +
                "WHERE res.status = 'Checked In') AS totalPendings FROM bills b \n" +
                "INNER JOIN reservations res ON res.reservationID = b.reservationID;";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                totalRoom.setText(rs.getString("totalRooms"));
                bookedRoom.setText(rs.getString("totalBooked"));
                avaRoom.setText(rs.getString("totalNotBooked"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            pst = connection.prepareStatement(query2);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                earning.setText(rs.getString("totalEarnings"));
                pending.setText(rs.getString("totalPendings"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    Stage stage;

    public void logoutButtonOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) logoutButton.getScene().getWindow();
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
    public void backButtonOnAction(ActionEvent event){
        stage=(Stage) backButton.getScene().getWindow();
        stage.close();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("homepageAdmin.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 1000, 692));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}