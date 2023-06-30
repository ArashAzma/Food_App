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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class restController extends Main {

    @FXML
    private Button add;
    @FXML
    private Button updateButton;
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
    private Restaurant clicked;
    //Connection conn = null;
    private static Restaurant restaurant;
    protected static ArrayList<Restaurant> list;
    public static Restaurant giveRestaurant(){
        return restaurant;
    }
    String name;
    String address;
    String time;
    String imgPath;
    int table;
    int courier;
    boolean take;
    boolean available;
    private int index = -1;
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
    void rowClicked(MouseEvent event) {
        this.clicked = restaurants.getSelectionModel().getSelectedItem();
        int index = restaurants.getSelectionModel().getFocusedIndex();
        this.index = index;
        textfeildName.setText(nameColumn.getCellData(index).toString());
        textfeildAddress.setText(surnameColumn.getCellData(index).toString());
        textfeildTime.setText(timeColumn.getCellData(index).toString());
        textFieldImgPath.setText(imgPathColumn.getCellData(index).toString());
        textfeildCourier_count.setText(courierColumn.getCellData(index).toString());
        textfeildTable_count.setText(tableColumn.getCellData(index).toString());
        textfeildTake_away.setText(takeColumn.getCellData(index).toString());
        textfieldIs_able.setText(is_ableColumn.getCellData(index).toString());
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        try{
            if(textfeildName.getText() == null){
                this.name = clicked.getName();
            }
            this.name = textfeildName.getText();
            if(textfeildAddress.getText() == null){
                this.address = clicked.getAddress();
            }
            this.address = textfeildAddress.getText();
            if(textfeildTime.getText() == null){
                this.time = clicked.getTime();
            }
            this.time = textfeildTime.getText();
            if(textFieldImgPath.getText() == null){
                this.imgPath = clicked.getImgPath();
            }
            this.imgPath = textFieldImgPath.getText();
            if(textfeildTable_count.getText() == null){
                this.table = clicked.getTableCount();
            }
            this.table = Integer.parseInt(textfeildTable_count.getText());
            if(textfeildCourier_count.getText() == null){
                this.courier = clicked.getCourierCount();
            }
            this.courier = Integer.parseInt(textfeildCourier_count.getText());
            if(textfeildTake_away.getText() == null){
                this.take = clicked.isTake_away();
            }
            this.take = Boolean.parseBoolean(textfeildTake_away.getText());
            if(textfieldIs_able.getText() == null){
                this.available = clicked.getIs_able();
            }
            this.available = Boolean.parseBoolean(textfieldIs_able.getText());

        }catch (Exception ignored){
        }
        restaurants.getItems().clear();
        clicked.setName(name);
        clicked.setAddress(address);
        clicked.setTime(time);
        clicked.setImgPath(imgPath);
        clicked.setTableCount(table);
        clicked.setCourierCount(courier);
        clicked.setIs_takeAway(take);
        clicked.setIs_able(available);

        initialize();
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
