package clientFx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.FileInputStream;
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
        Restaurant aceBurger = new AceBurger("AceBurger", "khone ma", "hamishe", true, 10);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
        root = loader.load();
        RestaurantPageController rpc = loader.getController();

        rpc.displayName(aceBurger.getName());
        Image burger = new Image(new FileInputStream("C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\clientFx\\images\\aceBurger burger.jpg"));
        rpc.displayImage(burger, 0);
        rpc.displayLabel("boob", 0);

        rpc.displayImage(burger, 1);
        rpc.displayLabel("BUH BUH", 1);

        rpc.displayImage(burger, 2);
        rpc.displayLabel("ENDMYSUFFERING", 2);

        switchToScene(e, "restaurantPageView.fxml", root);
    }
    @FXML
    protected void restaurant2(MouseEvent e) throws IOException {
        Restaurant sachi = new Sachi("Sachi", "khone bahar", "shaba", true, 6);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
        root = loader.load();
        RestaurantPageController rpc = loader.getController();
        rpc.displayName(sachi.getName());
        Image burger = new Image(new FileInputStream("C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\clientFx\\images\\bahar burger.jpg"));
        rpc.displayImage(burger, 0);
        rpc.displayLabel("AAAAA", 0);

        rpc.displayImage(burger, 1);
        rpc.displayLabel("AAAAA", 1);

        rpc.displayImage(burger, 2);
        rpc.displayLabel("AAAAA", 2);

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