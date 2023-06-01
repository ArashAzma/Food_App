package clientFx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RestaurantsController {
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private ImageView r1;
    @FXML
    private ImageView r2;
    @FXML
    private ImageView r3;
    @FXML
    protected void restaurant1(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
        root = loader.load();
        switchToScene(e, "restaurantPageView.fxml", root);
    }
    @FXML
    protected void restaurant2(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
        root = loader.load();
        switchToScene(e, "restaurantPageView.fxml", root);
    }
    @FXML
    protected void restaurant3(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
        root = loader.load();
        switchToScene(e, "restaurantPageView.fxml", root);
    }
    private static void switchToScene(MouseEvent event, String Scene, Parent root)  throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource(Scene));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}