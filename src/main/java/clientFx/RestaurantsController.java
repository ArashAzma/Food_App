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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
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
    private FlowPane flowPane = new FlowPane();
//    private ArrayList<ImageView> imageViews;
    @FXML
    public void initialize(){
        ArrayList<Restaurant> list = restaurants;
        for(Restaurant res: restaurants){
            Image image = new Image(res.getImgPath());
            Button button = new Button(res.getName());
            Label address = new Label(res.getAddress());
            ImageView imageView = new ImageView(image);
            button.setMinWidth(180);
            address.setMinWidth(180);
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);

            button.setOnAction((ActionEvent event) -> {
                // Perform action for this button
                System.out.println("Button clicked: " + res.getName());
                // Add your custom logic here
            });
            VBox vbox = new VBox(imageView, button, address);
            flowPane.getChildren().add(0, vbox);
        }
    }
}