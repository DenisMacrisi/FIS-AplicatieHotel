package com.example.proiectfis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookRoomController implements Initializable {

    @FXML
    private TextField cReservationDate;

    @FXML
    private TextField cEmail;


    @FXML
    private TextField cName;


    @FXML
    private TextField cPhone;

    @FXML
    private Button submit;

    @FXML
    private Button backUserPageButton;

    @FXML
    private ComboBox<String> rNo;

    @FXML
    private ComboBox<String> rType;

    private Connection connection;

    private DatabaseConnection dbConnection;

    private PreparedStatement pst;

    private String insertCustomerFields = "";
    private String insertCustomerValues = "";
    private String insertReservationFields = "";
    private String insertReservationValues = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
        insertRoomType();
    }

    private void insertRoomType() {
        rType.getItems().removeAll(rType.getItems());
        String query = "SELECT DISTINCT roomType FROM rooms";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String room_type = rs.getString("roomType");
                rType.getItems().add(room_type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertRoomNo() {
        rNo.getItems().removeAll(rNo.getItems());
        String type = rType.getSelectionModel().getSelectedItem();
        String query = "SELECT roomNumber FROM rooms WHERE roomType=? AND status='Not Booked'";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, type);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String room_no = rs.getString("roomNumber");
                rNo.getItems().add(room_no);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleSelectRoomType(javafx.event.ActionEvent actionEvent) {
        if (!rType.getSelectionModel().getSelectedItem().equals("")) {
            insertRoomNo();
        }
    }

    public void handleSelectRoomNumber(javafx.event.ActionEvent actionEvent) {
        String priceVal = "Price: ";
        String no = rNo.getSelectionModel().getSelectedItem();
        String priceQuery = "SELECT price FROM rooms WHERE roomNumber=?";
        try {
            pst = connection.prepareStatement(priceQuery);
            pst.setString(1, no);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                priceVal = priceVal + rs.getString("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void handleCheckInPick(javafx.event.ActionEvent actionEvent) {

    }
/*
    public void handleCheckOutPick(javafx.event.ActionEvent actionEvent) {
        int x = outDate.getValue().compareTo(inDate.getValue());
        days.setText("Total days: " + x);
        int p = Integer.parseInt(price.getText().replace("Price: ", ""));
        amount.setText("Total Amount: " + (p * x));
    }
*/
    public void handleSubmitAction(javafx.event.ActionEvent actionEvent) {
        String reservationDate = cReservationDate.getText();
        String name = cName.getText();
        String email = cEmail.getText();
        String phone = cPhone.getText();
        String roomNo = rNo.getSelectionModel().getSelectedItem();
        if (name.equals("") || email.equals("") || phone.equals("") || roomNo.equals("") || reservationDate.equals("") ) {
            OptionPane("Every Field is required", "Error Message");
        } else {
            /*String insertCustomer = "INSERT INTO customers(customerIDNumber, customerName, customerNationality, customerGender, customerPhoneNo, customerEmail)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";

             */

            insertCustomerFields = "INSERT INTO customers(username, roomNumber, name, email, phone, reservation_date) VALUES('";
            insertCustomerValues = LoginController.getUsername() + "','" + roomNo + "','" + name + "','" + email + "','" + phone + "','" + reservationDate + "')";

            String insertCustomer = insertCustomerFields + insertCustomerValues;

            //String insertReservation = "INSERT INTO reservations(customerIDNumber, roomNumber, checkInDate, checkOutDate) VALUES (?, ?, ?, ?)";


            insertReservationFields = "INSERT INTO reservations(username, roomNumber, status,reservation_date) VALUES('";
            insertReservationValues = LoginController.getUsername() + "','" + roomNo + "','" + " " + "','" + reservationDate + "')";
            String insertReservation = insertReservationFields + insertReservationValues;

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String updateRoom = "UPDATE rooms SET status=\"Booked\" WHERE roomNumber=?";

            Statement statement = null;
            try {
                statement = connectDB.createStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                statement.executeUpdate(insertCustomer);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                statement.executeUpdate(insertReservation);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                pst = connection.prepareStatement(updateRoom);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst.setString(1, roomNo);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            OptionPane("Check In Successful", "Message");
        }
    }

    private void OptionPane(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    Stage stage;
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

    public void backUserPageButtonOnAction(ActionEvent actionEvent) {
        stage=(Stage) backUserPageButton.getScene().getWindow();
        stage.close();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("homepageUser.fxml"));
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