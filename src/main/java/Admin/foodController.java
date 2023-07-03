package Admin;

import common.Food;
import common.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import static Admin.restController.isBoolean;
import static Admin.restController.isNumeric;

public class foodController extends Main {
    private Food clicked;

    @FXML
    private Button add;

    @FXML
    private TableColumn<Food, ?> nameColumn;

    @FXML
    private TableColumn<Food, ?> typeColumn;

    @FXML
    private TableColumn<Food, ?> priceColumn;

    @FXML
    private TableColumn<Food, ?> isAvailableColumn;

    @FXML
    private TableColumn<Food, ?> imgpathColumn;

    @FXML
    private TableColumn<Food, ?> weightColumn;

    @FXML
    private TextField foodnameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField isAvailableField;

    @FXML
    private TextField imgpathField;
    @FXML
    private TextField weightField;

    @FXML
    private TextField typeFeild;
    @FXML
    private Label error_lable ;
    @FXML
    private TableView<Food> foods;
    private static Restaurant r;
    public void setR(Restaurant r){
        this.r = r;
    }
    @FXML
    public void initialize() throws Exception {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        isAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
        imgpathColumn.setCellValueFactory(new PropertyValueFactory<>("imgPath"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        error_lable.setText("");
        // give restaurant
        this.r = restController.giveRestaurant();
        ObservableList<Food> data = FXCollections.observableArrayList(r.getFoodsArray());
        foods.getItems().addAll(data);
    }
    @FXML
    void add_food(ActionEvent event) throws IOException {
        boolean add = true;
        String name = foodnameField.getText();
        String type = typeFeild.getText();
        String price = priceField.getText();
        String isAvailable = isAvailableField.getText();
        String weight = weightField.getText();
        String imgPath = imgpathField.getText();
        // check if text fields are null
        add = add && !name.equals("") && !type.equals("") && price.equals("") && ! isAvailable.equals("") && ! weight.equals("") && ! imgPath.equals("");
        add = add && isNumeric(price) && isNumeric(weight); // check if price and weight are not number
        add = add && isBoolean(isAvailable);                // check if isAvailable is not boolean
        if ( add ) {
            Food food = new Food(foodnameField.getText(), typeFeild.getText(), Double.valueOf(priceField.getText()), Boolean.parseBoolean(isAvailableField.getText()), imgpathField.getText(), Double.parseDouble(weightField.getText()));
            foods.getItems().add(food);
            try {
                out.writeUTF("add food");
                out.flush();
                out.writeObject(r);
                out.flush();
                out.writeObject(food);
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            error_lable.setText("");
        }else {
            error_lable.setText("invalid input");
        }
    }
    @FXML
    void rowClicked(MouseEvent event) {
        this.clicked = foods.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void updateFood(ActionEvent e) throws IOException{
        if (foods.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFx/updateFood.fxml"));
            Parent root = loader.load();

            UpdateFoodController ufc = loader.getController();
            ufc.setClicked(clicked);
            ufc.setRestaurant(r);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void restaurantScene(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFx/restaurant.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void remove() throws Exception {
        this.clicked = foods.getSelectionModel().getSelectedItem();
        out.flush();
        out.writeUTF("remove food");
        out.flush();
        out.writeUTF(r.getName());
        out.flush();
        out.writeUTF(clicked.getName());
        out.flush();
        foods.getItems().clear();
        initialize();
    }
}
