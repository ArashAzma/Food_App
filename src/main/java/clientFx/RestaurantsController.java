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
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class RestaurantsController extends Main{
    private Admin admin = Admin.getInstace(null, null, null, null, null);
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private FlowPane flowPane = new FlowPane();
    private ArrayList<Restaurant> restaurants;

    @FXML
    public void initialize(){
        try {
            // Connect to the server and receive the restaurants list
            InetAddress addr = InetAddress.getByName(null);
            System.out.println("addr = " + addr);
            Socket socket = new Socket(addr, PORT);
            System.out.println("Connected to server.");

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            restaurants = (ArrayList<Restaurant>) inputStream.readObject();
            inputStream.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        int i=0;
        for(Restaurant res: restaurants){
            Image image = new Image(res.getImgPath());
            Button button = new Button(res.getName());
            Label address = new Label(res.getAddress());
            ImageView imageView = new ImageView(image);
            button.setMinWidth(180);
            address.setMinWidth(180);
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);
            final int index = i;

            button.setOnAction((ActionEvent e) -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
                try {
                    root = loader.load();
                    RestaurantPageController rpc = loader.getController();
                    rpc.setIndex(index);
//                    System.out.println(index);
//                    System.out.println("Button clicked: " + res.getName());
                    switchToScene(e, "restaurantPageView.fxml", root);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            VBox vbox = new VBox(imageView, button, address);
            flowPane.getChildren().add(i, vbox);
            i++;
        }
    }
    @FXML
    private void infoButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoView.fxml"));
        root = loader.load();
        switchToScene(e, "InfoView.fxml", root);
    }
}