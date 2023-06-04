package clientFx;

import common.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RestaurantsController extends Main{
    private Admin admin = Admin.getInstace(null, null, null, null, null);
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private ListView<Restaurant> listView;
    @FXML
    public void initialize(){
        ArrayList<Restaurant> list = restaurants;
        for(Restaurant i: list){
            System.out.println(i);
        }
        ObservableList<Restaurant> observableList = FXCollections.observableArrayList(list);
        listView.setItems(observableList);


    }
}