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
//        imageViews = new ArrayList<>();
        for(Restaurant res: restaurants){
            Image image = new Image(res.getImgPath());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200); // Set the desired width
            imageView.setFitHeight(200); // Set the desired height
            VBox vbox = new VBox(imageView);
            flowPane.getChildren().add(0, vbox);
//            imageViews.add(imageView);
        }

    }

//    public void handleImageClick(ImageView imageView) {
//        System.out.println("Image clicked: ");
//    }
}