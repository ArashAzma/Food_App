package Admin;

import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static Admin.restController.isBoolean;
import static Admin.restController.isNumeric;

public class UpdateRestaurantController extends Main{
    private Restaurant clicked;
    String name;
    String address;
    String time;
    String imgPath;
    int table;
    int courier;
    boolean take;
    boolean available;
    @FXML
    private Label error_lable;
    @FXML
    private TextField textFieldImgPath;
    @FXML
    private TextField textfeildCourier_count;

    @FXML
    private TextField textfeildName;

    @FXML
    private TextField textfeildTable_count;

    @FXML
    private TextField textfeildTime;

    @FXML
    private TextField textfeildTake_away;

    @FXML
    private TextField textfeildAddress;

    @FXML
    private TextField textfieldIs_able;
    @FXML
    void update(ActionEvent event) throws IOException{
        boolean sw = true;
        try{
            if(textfeildName.getText().equals("")) {
                this.name = clicked.getName();
            }
            else {
                this.name = textfeildName.getText();
            }

            if(textfeildAddress.getText().equals("")) {
                this.address = clicked.getAddress();
            }
            else {
                this.address = textfeildAddress.getText();
            }
            if(textfeildTime.getText().equals("")) {
                this.time = clicked.getTime();
            }
            else {
                this.time = textfeildTime.getText();
            }
            if(textFieldImgPath.getText().equals("")) {
                this.imgPath = clicked.getImgPath();
            }
            else {
                this.imgPath = textFieldImgPath.getText();
            }
            if(textfeildTable_count.getText().equals("")) {
                this.table = clicked.getTableCount();
            }
            else if(!isNumeric(textfeildTable_count.getText())){
                error_lable.setText("Invalid Input! ");
                sw = false;
            }
            else {
                this.table = Integer.parseInt(textfeildTable_count.getText());
            }
            if(textfeildCourier_count.getText() .equals("")) {
                this.courier = clicked.getCourierCount();
            }
            else if(!isNumeric(textfeildCourier_count.getText())){
                error_lable.setText("Invalid Input! ");
                sw = false;
            }
            else {
                this.courier = Integer.parseInt(textfeildCourier_count.getText());
            }
            if(textfeildTake_away.getText().equals("")) {
                this.take = clicked.isTake_away();
            }
            else if(!isBoolean(textfeildTake_away.getText())){
                error_lable.setText("Invalid Input! ");
                sw = false;
            }
            else {
                this.take = Boolean.parseBoolean(textfeildTake_away.getText());
            }
            if(textfieldIs_able.getText().equals("")) {
                this.available = clicked.getIs_able();
            }
            else if(!isBoolean(textfieldIs_able.getText())){
                error_lable.setText("Invalid Input! ");
                sw = false;
            }
            else {
                this.available = Boolean.parseBoolean(textfieldIs_able.getText());
            }

        }catch (Exception ignored){
        }
        if(sw){
            out.flush();
            out.writeUTF("Change restaurant");
            out.flush();
            out.writeUTF(clicked.getName());
            out.flush();
            Restaurant temp = new Restaurant(name, address, time, take, table, courier, imgPath, available);
//            String line = name+","+address+","+time+","+take+","+courier+","+table+","+imgPath+","+available;
            out.writeObject(temp);
            out.flush();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFx/restaurant.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    public void setClicked(Restaurant clicked) {
        this.clicked = clicked;
    }
    @FXML
    public void restaurantScene(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminFx/restaurant.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
