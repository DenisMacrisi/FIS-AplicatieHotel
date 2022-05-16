package com.example.proiectfis;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
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

}
