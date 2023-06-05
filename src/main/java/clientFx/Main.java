package clientFx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import common.Restaurant;

public class Main extends Application {
    public static final int PORT = 8080;
//    protected  static ArrayList<Restaurant> restaurants;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BUH BUH!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException{
        launch();
    }
    public static void switchToScene(ActionEvent event, String Scene, Parent root)  throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}