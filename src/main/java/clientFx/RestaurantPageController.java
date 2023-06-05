package clientFx;

import common.Food;
import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class RestaurantPageController extends Main{
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    private int index;
    @FXML
    private FlowPane flowPane = new FlowPane();
    private ArrayList<Restaurant> restaurants;

//    @FXML
    public void init(){
        try {
            // Connect to the server and receive the restaurants list
            InetAddress addr = InetAddress.getByName(null);
//            System.out.println("addr = " + addr);
            Socket socket = new Socket(addr, PORT);
            System.out.println("Connected to server.");

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            restaurants = (ArrayList<Restaurant>) inputStream.readObject();
            inputStream.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Restaurant rest = restaurants.get(index);
        ArrayList<Food> menu = rest.getMenu();
        for(Food food: menu){
//            System.out.println(food);
            Image image = new Image(food.getImgPath());
            Label name = new Label(food.getName());
            Label isAvailable = new Label(food.isAvailable());

            ImageView imageView = new ImageView(image);
            name.setMinWidth(180);
            isAvailable.setMinWidth(180);
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);
            VBox vbox = new VBox(imageView, name, isAvailable);
            flowPane.getChildren().add(0, vbox);
        }
    }

    @FXML
    private void infoButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoView.fxml"));
        root = loader.load();
        switchToScene(e, "InfoView.fxml", root);
    }
    public void setIndex(int index) {
        this.index = index;
        init();
    }
}
