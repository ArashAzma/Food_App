package clientFx;

import common.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
    private Label priceLabel;
    @FXML
    public void initialize(){
        ObservableList<Food> observableItems = FXCollections.observableArrayList(items);
        System.out.println(Arrays.toString(items.toArray()));
        listView.setItems(observableItems);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        double sum= 0;
        for(Food i:items){
            sum += i.getPrice();
        }
        priceLabel.setText("Sum: "+sum);
    }
    public void addItems(ArrayList foods){
        items = foods;
        initialize();
    }
    @FXML
    private void removeButtonClicked(ActionEvent event) {
        ObservableList<Integer> indices =  listView.getSelectionModel().getSelectedIndices().sorted();
        for (int k = indices.size() - 1; k >= 0; k--) {
            int index = indices.get(k);
            listView.getItems().remove(index);
            items.remove(index);
        }
        initialize();
    }
    @FXML
    private void infoButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoView.fxml"));
        root = loader.load();
        switchToScene(e, "InfoView.fxml", root);
    }
    @FXML
    private void backButton(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RestaurantPageView.fxml"));
        root = loader.load();
        RestaurantPageController rpc = loader.getController();
        rpc.init();
        switchToScene(e, "RestaurantPageView.fxml", root);
    }
}
