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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserHistoryController implements Initializable {


    @FXML
    private TableColumn<reservation, String> reservationID;

    @FXML
    private TableView<reservation> reservationTable;

    @FXML
    private TableColumn<reservation, String> reservation_date;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<reservation, String> roomNumber;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;


    private Connection connection;

    private DatabaseConnection dbConnection;

    private PreparedStatement pst;

    public static final ObservableList<reservation> reservations = FXCollections.observableArrayList();

    public static final List<reservation> reservationList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
        reservationID.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        reservation_date.setCellValueFactory(new PropertyValueFactory<reservation, String>("ReservationDate"));

        try {
            initReservationList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reservationTable.setItems(reservations);

    }

    public void handleViewAction(javafx.event.ActionEvent actionEvent) throws IOException {

    }

    public void initReservationList() throws IOException {
        reservationList.clear();
        reservations.clear();
        String query = "SELECT * FROM reservations WHERE username = '" + LoginController.getUsername() + "'";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int reservation_ID = Integer.parseInt(rs.getString("reservationID"));
                String username= rs.getString("username");
                int room_number = Integer.parseInt(rs.getString("roomNumber"));
                String room_status = rs.getString("status");
                String reservation_date = rs.getString("reservation_date");

                reservationList.add(new reservation(reservation_ID, username, room_number, room_status, reservation_date));
                reservations.add(new reservation(reservation_ID, username, room_number, room_status, reservation_date));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void Search(ObservableList<reservation> reservations, String d) {
        reservations.clear();
        for (int i = 0; i < reservationList.size(); i++) {
            if (String.valueOf(reservationList.get(i).getReservationDate()).indexOf(d) == 0) {
                reservations.add(reservationList.get(i));
            }
        }
    }

    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String s = search.getText();
            Search(reservations, s);
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
    public void backButtonOnAction1(ActionEvent event){
        stage=(Stage) backButton.getScene().getWindow();
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
