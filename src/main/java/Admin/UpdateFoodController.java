package Admin;

import common.Food;
import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateFoodController extends Main{
    private static Restaurant restaurant;

    private static Food clicked;
    @FXML
    private TextField nameText;
    String name;
    @FXML
    private TextField typeText;
    String type;
    @FXML
    private TextField priceText;
    double price;
    @FXML
    private TextField availableText;
    boolean available;
    @FXML
    private TextField imgpathText;
    String imgPath;
    @FXML
    public void updateFood(ActionEvent e) throws IOException {
        try{
            if(nameText.getText().equals("")) {
                this.name = clicked.getName();
            }
            else {
                this.name = nameText.getText();
            }

            if(priceText.getText().equals("")) {
                this.price = clicked.getPrice();
            }
            else {
                this.price = Double.parseDouble(priceText.getText());
            }
            if(typeText.getText().equals("")) {
                this.type = clicked.getType();
            }
            else {
                this.type = typeText.getText();
            }
            if(availableText.getText().equals("")) {
                this.available = clicked.getIsAvailable();
            }
            else {
                this.available = Boolean.parseBoolean(availableText.getText());
            }
            if(imgpathText.getText().equals("")) {
                this.imgPath = clicked.getImgPath();
            }
            else {
                this.imgPath = imgpathText.getText();
            }

        }catch (Exception ignored){
        }

        String line = restaurant.getName()+","+name+","+type+","+price+","+available+","+imgPath;

        out.flush();
        out.writeUTF("change food");
        out.flush();
        out.writeUTF(restaurant.getName());
        out.flush();
        out.writeUTF(clicked.getName());
        out.flush();

        out.writeUTF(line);
        out.flush();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFx/food.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setClicked(Food clicked) {
        this.clicked = clicked;
    }
    public static void setRestaurant(Restaurant restaurant) {
        UpdateFoodController.restaurant = restaurant;
    }
    @FXML
    public void switchScene(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFx/food.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
