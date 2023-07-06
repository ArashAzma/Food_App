package Admin;

import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class restController extends Main {

    @FXML
    private Button add;
    @FXML
    private Button updateButton;
    @FXML
    private Button editFoodButton;
    @FXML
    private Button removeRest;
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
    private Restaurant clicked;
    private static Restaurant restaurant;
    protected static ArrayList<Restaurant> list;
    private int index = -1;
    @FXML
    private Label error_label;
    @FXML
    public void initialize() throws IOException {
        restaurants.getItems().clear();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        takeColumn.setCellValueFactory(new PropertyValueFactory<>("is_takeAway"));
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("tableCount"));
        courierColumn.setCellValueFactory(new PropertyValueFactory<>("courierCount"));
        imgPathColumn.setCellValueFactory(new PropertyValueFactory<>("imgPath"));
        is_ableColumn.setCellValueFactory(new PropertyValueFactory<>("is_able"));
        error_label.setText("");
        try {
            // receive restaurants arraylist
            out.writeUTF("restaurants");
            out.flush();
            list = (ArrayList<Restaurant>) in.readObject();
            for(Restaurant r : list){
                System.out.println(r);
            }

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
    void rowClicked(MouseEvent event) {
        this.clicked = restaurants.getSelectionModel().getSelectedItem();
        int index = restaurants.getSelectionModel().getFocusedIndex();
        this.index = index;
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        if (restaurants.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFx/updateRestaurant.fxml"));
            Parent root = loader.load();
            UpdateRestaurantController upc = loader.getController();
            upc.setClicked(clicked);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void add(ActionEvent event) throws IOException {
        String name = textfeildName.getText();
        String address = textfeildAddress.getText();
        String time = textfeildTime.getText();
        String table = textfeildTable_count.getText();
        String courier = textfeildCourier_count.getText();
        String takAway = textfeildTake_away.getText();
        String isAble = textfieldIs_able.getText();
        String imgPath = textFieldImgPath.getText();
        boolean add = true;
        add = isNumeric(table) && isNumeric(courier);  // check if table and courier counts not number
        add = add && isBoolean(isAble) && isBoolean(takAway); // check if isTakeAway and isAble counts not boolean
        // check if text fields are null
        add = add && !name.equals("") && !address.equals("") && !time.equals("") && !table.equals("") && !courier.equals("") && !takAway.equals("") && !isAble.equals("") && !imgPath.equals("");

        if ( add ) {
            Restaurant restaurant = new Restaurant(name, address, time, Boolean.parseBoolean(takAway), Short.parseShort(table), Short.parseShort(courier), imgPath, Boolean.parseBoolean(isAble));
//            list.add(restaurant);
//            restaurants.getItems().add(restaurant);
            try {
                out.writeUTF("Add restaurant");
                out.flush();
                out.writeObject(restaurant);
                out.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            error_label.setText("");
        } else {
            error_label.setText("invalid input!");
        }
        initialize();
    }
    public static Restaurant giveRestaurant() throws Exception {
        updateRestaurant();
        return restaurant;
    }

    public static void updateRestaurant() throws Exception {
        out.flush();
        out.writeUTF("restaurants");
        out.flush();
        ArrayList<Restaurant> rests = (ArrayList<Restaurant>)in.readObject();
        for(Restaurant i : rests){
            if (i.getName().equals(restaurant.getName())){
                restaurant = i;
            }
        }
    }

    public void removeRest() throws IOException {
        this.clicked = restaurants.getSelectionModel().getSelectedItem();
        out.flush();
        out.writeUTF("Remove restaurant");
        out.flush();
        out.writeUTF(clicked.getName());
        out.flush();
        restaurants.getItems().clear();
        initialize();
    }

    public static boolean isNumeric(String str) {
        return str.chars().allMatch( Character::isDigit );  //match a number with optional '-' and decimal.
    }
    public static boolean isBoolean(String str) {
        return str.toLowerCase().equals("true") || str.toLowerCase().equals("false");
    }
}
