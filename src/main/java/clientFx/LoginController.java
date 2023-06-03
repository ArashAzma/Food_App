package clientFx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class LoginController {
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private TextField nameBox;
    @FXML
    private PasswordField passBox;
    @FXML
    private Label passError;
    @FXML
    private Label nameLabel;
    @FXML
    private Label passwordLabel;

    @FXML
    protected void login(ActionEvent e) throws IOException {
        String name = nameBox.getText();
        String password = passBox.getText();
        try(BufferedReader file = new BufferedReader(new FileReader("usernames"))){
            String line;
            boolean findUser = false;
            while ((line = file.readLine()) != null) {
                String[] parts = line.split(",");
//                System.out.println(Arrays.toString(parts));
                if(parts[0].equals(name)  && parts[1].equals(password)){
                    Admin admin = Admin.getInstace(parts[0], parts[1], parts[2], parts[3], parts[4]);
//                    System.out.println(admin);
                    System.out.println("Found it!!!");
                    findUser = true;
                    nameLabel.setText("Name");
                    passwordLabel.setText("Password");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantView.fxml"));
                    root = loader.load();
                    switchToScene(e, "restaurantView.fxml", root);
                }
            }
            if(!findUser){
                passError.setTextFill(Color.RED);
                passError.setText("Couldnt find youe account! ");
            }
        }catch(IOException error){
            System.out.println("error");
            error.printStackTrace();
        }
    }

    @FXML
    protected void signUp(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        root = loader.load();
        switchToScene(e, "mainView.fxml", root);
    }
    public static void switchToScene(ActionEvent event, String Scene, Parent root)  throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource(Scene));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
