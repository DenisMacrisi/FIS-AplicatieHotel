package com.example.proiectfis;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
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

import static com.example.proiectfis.seeReservationController.reservationList;
import static com.example.proiectfis.seeReservationController.reservations;

public class cancelReservationController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private TextField reservationID;

    @FXML
    private TextField reservationDate;

    @FXML
    private TextField roomNumber;

    private Connection connection;

    private DatabaseConnection dbConnection;

    private PreparedStatement pst;
    @FXML
    private ImageView roomnr;
    @FXML
    private ImageView roomtype;
    @FXML
    private ImageView priceimg;
    @FXML
    private Button cancel;


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

    public void handleCancelAction(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Reservation");
        alert.setHeaderText("You are about to cancel this reservation");
        if (alert.showAndWait().get() == ButtonType.OK) {
            String query = "DELETE FROM reservations WHERE reservationID=? AND roomNumber=? AND reservation_date=?";
            try {
                pst = connection.prepareStatement(query);
                pst.setString(1, reservationID.getText());
                pst.setString(2, roomNumber.getText());
                pst.setString(3, reservationDate.getText());
                String s = " ";
                String v = " ";
                reservation x = new reservation(Integer.parseInt(reservationID.getText()), s, Integer.parseInt(roomNumber.getText()), v, reservationDate.getText());
                reservationList.remove(x);
                reservations.remove(x);
                pst.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            getBacktoRoomManagement();
        }
    }
    public void getBacktoRoomManagement(){
        Stage stage=(Stage) cancel.getScene().getWindow();
        stage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("seeReservation.fxml"));
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

