package clientFx;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RestaurantPageController {
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private Label restaurantName;
    @FXML
    private ImageView image0;
    public void displayName(String username) {
        restaurantName.setText(username);
    }
    public void displayImage(Image image, int num){
        switch (num){
            case 0:
                image0.setImage(image);
                break;
            case 1:
                image0.setImage(image);
                break;
            case 2:
                image0.setImage(image);
                break;
            case 3:
                image0.setImage(image);
                break;
        }
    }
}
