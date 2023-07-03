package clientFx;

import common.Food;
import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;

public class RestaurantPageController extends Main{
    private static int index;
    private FXMLLoader loader = new FXMLLoader(getClass().getResource("cartView.fxml"));
    private Parent root;
    {
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private CartController cartController = loader.getController();
    @FXML
    private FlowPane flowPane = new FlowPane();
    private ArrayList<Restaurant> restaurants;
    private static ArrayList<Food> selectedItems = new ArrayList<>();
    @FXML
    private Label add;

//    @FXML
    public void init(){
       try{
            outputStream.flush();
            outputStream.writeUTF("list");
            outputStream.flush();
            restaurants = (ArrayList<Restaurant>) inputStream.readObject();
            System.out.println("received restauarants ");

        }catch(IOException error ){
            error.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally{
            System.out.println("closing...");
        }
        cartController.initialize();
        Restaurant rest = restaurants.get(index);
        ArrayList<Food> menu = rest.getMenu();
        for(Food food: menu){
            String imagePath = RestaurantPageController.class.getResource(food.getImgPath()).toExternalForm();
            Image image = new Image(imagePath);
            Label name = new Label(food.getName());
            Label isAvailable = new Label("Availability: "+food.isAvailable());

            ImageView imageView = new ImageView(image);
            name.setMinWidth(150);
            isAvailable.setMinWidth(150);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);

            ChoiceBox<Integer> add = new ChoiceBox<>();
            if(food.isAvailable().equals("no")) {
                add.setDisable(true);
            }
            for (int i = 1; i <= 4; i++) {
                add.getItems().add(i);
            }
            add.setOnAction((ActionEvent e) -> {
                int quantity = add.getValue();
                for (int i = 0; i < quantity; i++) {
                    selectedItems.add(food);
                }
                System.out.println("Selected " + quantity + " " + food.getName());
            });

            HBox hbox = new HBox(add, name);
            VBox vbox = new VBox(imageView, hbox, isAvailable);
            vbox.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            vbox.getStyleClass().add("glassmorphism-vbox");
            vbox.setAlignment(Pos.TOP_CENTER);
            vbox.setPadding(new Insets(10));
            vbox.setSpacing(15);

            name.getStyleClass().add("info-label");
            isAvailable.getStyleClass().add("info-label");
            imageView.setPreserveRatio(false);
            add.getStyleClass().add("choice-box")
            ;
            hbox.setSpacing(5);
            hbox.getStyleClass().add("glassmorphism-vbox");
            hbox.setAlignment(Pos.CENTER);
            flowPane.getChildren().add(0, vbox);
        }
    }

    @FXML
    private void infoButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoView.fxml"));
        root = loader.load();
        switchToScene(e, "InfoView.fxml", root);
    }
    @FXML
    private void restaurants(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantsView.fxml"));
        root = loader.load();
        switchToScene(e, "restaurantsView.fxml", root);
    }
    @FXML
    public void cartButton(ActionEvent e) throws IOException {
        cartController.initialize();
        cartController.setIndex(index);
        System.out.println(index);
        switchToScene(e, "cartView.fxml", root);
    }
    @FXML
    private void addToCartButton(ActionEvent e){
        cartController.addItems(selectedItems);
        cartController.initialize();
        add.setOpacity(1);
    }
    public void setIndex(int index) throws IOException {
        this.index = index;
        init();
    }
}
