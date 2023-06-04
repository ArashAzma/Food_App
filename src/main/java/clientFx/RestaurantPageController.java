package clientFx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class RestaurantPageController extends Main{
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private Label restaurantName;
    @FXML
    private ImageView image0;
    @FXML
    private Label imageLabel0;
    @FXML
    private ImageView image1;
    @FXML
    private Label imageLabel1;
    @FXML
    private ImageView image2;
    @FXML
    private Label imageLabel2;
    public void displayName(String username) {
        restaurantName.setText(username);
    }
    public void displayLabel(String name, int num){
        switch (num){
            case 0:
                imageLabel0.setText(name);
                break;
            case 1:
                imageLabel1.setText(name);
                break;
            case 2:
                imageLabel2.setText(name);
                break;
        }
    }
    public void displayImage(Image image, int num){
        switch (num){
            case 0:
                image0.setImage(image);
                break;
            case 1:
                image1.setImage(image);
                break;
            case 2:
                image2.setImage(image);
                break;
        }
    }
    @FXML
    private void infoButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoView.fxml"));
        root = loader.load();
        switchToScene(e, "InfoView.fxml", root);
    }
}
