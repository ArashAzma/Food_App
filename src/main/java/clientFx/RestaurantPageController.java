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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class RestaurantPageController extends LoginController{
    private static Stage stage;
    private static Scene scene;
    private int index;
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

//    @FXML
    public void init() throws IOException {
        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, PORT);
       try{
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            out.println("foundUser");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            restaurants = (ArrayList<Restaurant>) inputStream.readObject();
            System.out.println("received restauarants ");
            inputStream.close();

        }catch(IOException error ){
            error.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally{
            System.out.println("closing...");
            socket.close();
        }
        cartController.initialize();
        Restaurant rest = restaurants.get(index);
        ArrayList<Food> menu = rest.getMenu();
        for(Food food: menu){
//            System.out.println(food);
            System.out.println(food.getImgPath());
            String imagePath = "file://" + System.getProperty("user.dir") + food.getImgPath();
            Image image = new Image(imagePath);

//            Image image = new Image(food.getImgPath());
            Label name = new Label(food.getName());
            Label isAvailable = new Label(food.isAvailable());

            ImageView imageView = new ImageView(image);
            name.setMinWidth(180);
            isAvailable.setMinWidth(180);
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);

            ChoiceBox<Integer> add = new ChoiceBox<>();
            for (int i = 0; i <= 4; i++) {
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
    private void cartButton(ActionEvent e) throws IOException {
        cartController.initialize();
        cartController.setIndex(index);
        switchToScene(e, "cartView.fxml", root);
    }
    @FXML
    private void addToCartButton(ActionEvent e) throws IOException {
        cartController.addItems(selectedItems);
        cartController.initialize();
    }
    public void setIndex(int index) throws IOException {
        this.index = index;
        init();
    }
}
