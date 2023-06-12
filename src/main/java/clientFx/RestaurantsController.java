package clientFx;

import common.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import static clientFx.LoginController.restaurants;

public class RestaurantsController extends Main{
//    private Admin admin = Admin.getInstace(null, null, null, null, null);
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private FlowPane flowPane = new FlowPane();
    private ArrayList<Restaurant> restaurants;

    @FXML
    public void initialize() throws IOException {
        try{
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
//            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.flush();

            outputStream.writeUTF("list");
            outputStream.flush();
            restaurants = (ArrayList<Restaurant>) inputStream.readObject();
            System.out.println("received restauarants ");

        }catch(IOException error ){
            System.out.println("\n IOException\n");
            error.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("\nobject doesnt match\n");
            throw new RuntimeException(e);
        } finally{
            System.out.println("RESTAURANTS FINISHED...");
        }
        int i=0;
        for(Restaurant res: restaurants){
            String imagePath = RestaurantPageController.class.getResource(res.getImgPath()).toExternalForm();
            Image image = new Image(imagePath);
            Button button = new Button(res.getName());
            Label address = new Label(res.getAddress());
            ImageView imageView = new ImageView(image);
            button.setMinWidth(290);
            address.setMinWidth(290);
            address.setMinHeight(60);
            imageView.setFitWidth(290);
            imageView.setFitHeight(120);
            final int index = i;

            button.setOnAction((ActionEvent e) -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
                try {
                    root = loader.load();
                    RestaurantPageController rpc = loader.getController();
                    rpc.setIndex(index);
                    switchToScene(e, "restaurantPageView.fxml", root);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            button.setStyle("-fx-background-color: white; -fx-font-weight: bold; -fx-border-width: 2 2 2 2; -fx-border-color: #116D6E; -fx-border-radius: 50%");
            button.setCursor(Cursor.OPEN_HAND);
            address.setStyle("-fx-background-color: white;");
            imageView.setStyle("-fx-border-radius: 50%;");
            VBox vbox = new VBox(imageView, button, address);
            vbox.setStyle("-fx-border-radius: 10px; -fx-border-color: white; -fx-border-width: 1px;");
            vbox.setAlignment(Pos.TOP_CENTER);
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