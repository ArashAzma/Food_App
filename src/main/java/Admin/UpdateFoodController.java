package Admin;

import common.Food;
import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static Admin.restController.isBoolean;
import static Admin.restController.isNumeric;

public class UpdateFoodController extends Main{
    private static Restaurant restaurant;

    private static Food clicked;
    @FXML
    private Label error_lable;
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
    private TextField weightText;
    String weigth;
    @FXML
    public void updateFood(ActionEvent e) throws IOException {
        boolean sw = true;
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
            else if (!isNumeric(priceText.getText())){
                error_lable.setText("Invalid Input!");
                sw = false;
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
            else if(!isBoolean(availableText.getText())){
                error_lable.setText("Invalid Input!");
                sw = false;
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
            if(weightText.getText().equals("")){
                this.weigth = clicked.getWeight()+"";
            }
            else if(!isNumeric(weightText.getText())) {
                error_lable.setText("Invalid Input!");
                sw = false;
            }
            else {
                this.weigth = weightText.getText();
            }

        }catch (Exception ignored){
        }
        if(sw){
            Food temp = new Food(name, type, price, available, imgPath, Double.parseDouble(weigth));
            out.flush();
            out.writeUTF("Change food");
            out.flush();
            out.writeUTF(clicked.getName());
            out.flush();

            out.writeObject(temp);
            out.flush();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFx/food.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
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
