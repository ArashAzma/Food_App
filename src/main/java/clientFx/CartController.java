package clientFx;

import common.Admin;
import common.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CartController extends Main{
    private Parent root;
    private int index;
    private Admin admin;
    private static double sum;
    private  static ArrayList<Food> items = new ArrayList<>();
    @FXML
    private ListView<Food> listView = new ListView<>();
    @FXML
    private Label priceLabel;
    @FXML
    private Label mojodi;
    @FXML
    private Label errorMojodi;
    @FXML
    public void initialize(){
        try{
            outputStream.flush();
            outputStream.writeUTF("getAdmin");
            outputStream.flush();
            admin = (Admin) inputStream.readObject();
            System.out.println("received  Admin ");
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        mojodi.setText("Mojodi : $"+admin.getMojodi());
        ObservableList<Food> observableItems = FXCollections.observableArrayList(items);
        System.out.println(Arrays.toString(items.toArray()));
        listView.setItems(observableItems);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        sum= 0;
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
        rpc.setIndex(index);
//        rpc.init();
        switchToScene(e, "RestaurantPageView.fxml", root);
    }
    @FXML
    private void depositButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bankView.fxml"));
        root = loader.load();
        switchToScene(e, "bankView.fxml", root);
    }
    @FXML
    private void purchaseButton(ActionEvent e) throws IOException {
        if(sum<=admin.getMojodi()){
            admin.setMojodi(admin.getMojodi()-sum);
            try{
                outputStream.flush();
                outputStream.writeUTF("changeMojodi");
                outputStream.flush();
                outputStream.writeObject(admin);
                outputStream.flush();
                System.out.println("Sent Admin");
            }catch (IOException error){
                error.printStackTrace();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("purchaseView.fxml"));
            root = loader.load();
            switchToScene(e, "purchaseView.fxml", root);
            errorMojodi.setOpacity(0);
        }
        else{
            errorMojodi.setOpacity(1);
        }

    }

    public void setIndex(int index) {
        this.index = index;
    }
}
