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

public class roomController implements Initializable {

    @FXML
    private TableColumn<room, String> price;

    @FXML
    private TableColumn<room, String> roomNumber;

    @FXML
    private TableView<room> roomTable;

    @FXML
    private TableColumn<room, String> roomType;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<room, String> status;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;
    @FXML
    private Button add;
    @FXML
    private Button remove;

    private Connection connection;

    private DatabaseConnection dbConnection;

    private PreparedStatement pst;

    public static final ObservableList<room> rooms = FXCollections.observableArrayList();

    public static final List<room> roomList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        roomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        status.setCellValueFactory(new PropertyValueFactory<room, String>("status"));
        try {
            initRoomList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        roomTable.setItems(rooms);
    }

    public void handleViewAction(javafx.event.ActionEvent actionEvent) throws IOException {

    }

    public void initRoomList() throws IOException {
        roomList.clear();
        rooms.clear();
        String query = "SELECT * FROM rooms";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int room_price = Integer.parseInt(rs.getString("price"));
                String room_type = rs.getString("roomType");
                String room_status = rs.getString("status");
                int room_num = Integer.parseInt(rs.getString("roomNumber"));
                roomList.add(new room(room_num, room_price, room_type, room_status));
                rooms.add(new room(room_num, room_price, room_type, room_status));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void Search(ObservableList<room> rooms, String s) {
        rooms.clear();
        for (int i = 0; i < roomList.size(); i++) {
            if (Integer.toString(roomList.get(i).getNumber()).indexOf(s) == 0) {
                rooms.add(roomList.get(i));
            }
        }
    }

    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String s = search.getText();
            Search(rooms, s);
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
    public void handleAddAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage=(Stage) remove.getScene().getWindow();
        stage.close();
        Stage add = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addroom.fxml"));
        Scene scene = new Scene(root);
        add.setScene(scene);
        add.show();
    }
    public void handleRemoveAction(javafx.event.ActionEvent actionEvent) throws IOException{
        Stage stage=(Stage) remove.getScene().getWindow();
        stage.close();
        Stage remove=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("removeRoom.fxml"));
        Scene scene=new Scene(root);
        remove.setScene(scene);
        remove.show();
    }
}
