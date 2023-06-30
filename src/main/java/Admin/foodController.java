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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class foodController extends restController {

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
    private TextField foodnameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField isAvailableField;

    @FXML
    private TextField imgpathField;

    @FXML
    private TextField typeFeild;

    @FXML
    private TableView<Food> foods;
    private static Restaurant r;
    public void setR(Restaurant r){
        this.r = r;
    }
    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        isAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
        imgpathColumn.setCellValueFactory(new PropertyValueFactory<>("imgPath"));
        // give restaurant
        this.r = restController.giveRestaurant();
        ObservableList<Food> data = FXCollections.observableArrayList(r.getFoodsArray());
        //data.add(new Food("fb","evf"));
        foods.getItems().addAll(data);
    }

    @FXML
    void add_food(ActionEvent event) throws IOException {
        Food food = new Food(foodnameField.getText(), typeFeild.getText(),Double.valueOf(priceField.getText()),Boolean.parseBoolean(isAvailableField.getText()),imgpathField.getText());
        foods.getItems().add(food);
       /* InetAddress addr =
                InetAddress.getByName(null);
        System.out.println("addr = " + addr);
        Socket socket =
                new Socket(addr, 3030);
        */try {
            out.writeUTF("add food");
            out.flush();
            out.writeObject(r);
            out.flush();
            out.writeObject(food);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
}
