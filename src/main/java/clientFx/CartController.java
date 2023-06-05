package clientFx;

import common.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CartController extends Main{
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    private ArrayList<Food> items = new ArrayList<>();
    @FXML
    private ListView<Food> listView = new ListView<>();
    @FXML
    public void initialize(){
        ObservableList<Food> observableItems = FXCollections.observableArrayList(items);
        System.out.println(Arrays.toString(items.toArray()));
        listView.setItems(observableItems);
    }
    public void addItem(Food food){
        items.add(food);
//        listView.getItems().add(food);
        initialize();
    }
    @FXML
    private void removeButtonClicked(ActionEvent event) {
        Food selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            items.remove(selectedItem);
        }
        initialize();
    }
    @FXML
    private void infoButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoView.fxml"));
        root = loader.load();
        switchToScene(e, "InfoView.fxml", root);
    }
}
