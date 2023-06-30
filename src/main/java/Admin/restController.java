package Admin;

import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class restController extends Main {

    @FXML
    private Button add;

    @FXML
    private Button editFoodButton;

    @FXML
    private TextField textFieldImgPath;

    @FXML
    private TextField textfeildCourier_count;

    @FXML
    private TextField textfeildName;

    @FXML
    private TextField textfeildTable_count;

    @FXML
    private TextField textfeildTime;

    @FXML
    private TextField textfeildTake_away;

    @FXML
    private TextField textfeildAddress;
    @FXML
    private TextField textfieldIs_able;
    @FXML
    private TableView<Restaurant> restaurants;

    @FXML
    private TableColumn<Restaurant, ?> surnameColumn;
    @FXML
    private TableColumn<Restaurant, ?> timeColumn;
    @FXML
    private TableColumn<Restaurant, ?> takeColumn;
    @FXML
    private TableColumn<Restaurant, ?> nameColumn;
    @FXML
    private TableColumn<Restaurant, ?> tableColumn;
    @FXML
    private TableColumn<Restaurant, ?> courierColumn;
    @FXML
    private TableColumn<Restaurant, ?> imgPathColumn;
    @FXML
    private TableColumn<Restaurant, ?> is_ableColumn;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static Restaurant restaurant;
    protected static ArrayList<Restaurant> list;
    public static Restaurant giveRestaurant(){
        return restaurant;
    }
    @FXML
    public void initialize() throws IOException {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        takeColumn.setCellValueFactory(new PropertyValueFactory<>("is_takeAway"));
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("tableCount"));
        courierColumn.setCellValueFactory(new PropertyValueFactory<>("courierCount"));
        imgPathColumn.setCellValueFactory(new PropertyValueFactory<>("imgPath"));
        is_ableColumn.setCellValueFactory(new PropertyValueFactory<>("is_able"));
        try {
            // giving restaurants arraylist

            out.writeUTF("restaurants");
            out.flush();
            list = (ArrayList<Restaurant>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        restaurants.getItems().addAll(list);
    }
    @FXML
    public void switchToFood(ActionEvent event) throws Exception {
        if (restaurants.getSelectionModel().getSelectedItem() != null) {
            Restaurant selected = restaurants.getSelectionModel().getSelectedItem();
            this.restaurant = selected;
            root = FXMLLoader.load(getClass().getResource("/AdminFx/food.fxml"));
            Stage window = (Stage) editFoodButton.getScene().getWindow();
            window.setScene(new Scene(root));
            }
        }

    @FXML
    void add(ActionEvent event) {
        Restaurant restaurant = new Restaurant(textfeildName.getText(), textfeildAddress.getText(),textfeildTime.getText(),Boolean.parseBoolean(textfeildTake_away.getText()), Short.parseShort(textfeildTable_count.getText()), Short.parseShort(textfeildCourier_count.getText()),textFieldImgPath.getText(),Boolean.parseBoolean(textfieldIs_able.getText()));
        list.add(restaurant);
        restaurants.getItems().add(restaurant);
        try {
            //out.flush();
            out.writeUTF("add restaurant");
            out.flush();
            System.out.print("jhg");
            out.writeObject(restaurant);
            out.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
