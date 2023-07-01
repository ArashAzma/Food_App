package clientFx;

import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;

public class RestaurantsController extends Main{
    private Parent root;
    @FXML
    private FlowPane flowPane = new FlowPane();
    private ArrayList<Restaurant> restaurants;

    @FXML
    public void initialize(){
        try{
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
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
            Label info = new Label(res.getAddress()+"\nTime: "+res.getTime()+"   Take away: "+res.isIs_takeAway());
            ImageView imageView = new ImageView(image);
            button.setMinWidth(290);
            info.setMinWidth(290);
            info.setMinHeight(60);
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
            VBox vbox = new VBox(imageView, button, info);
            vbox.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            vbox.getStyleClass().add("glassmorphism-vbox");
            vbox.setAlignment(Pos.TOP_CENTER);
            vbox.setPadding(new Insets(10));
            vbox.setSpacing(15);

            button.getStyleClass().add("res-button");
            info.getStyleClass().add("info-label");
            flowPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            flowPane.getStyleClass().add("flowpane-background");


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