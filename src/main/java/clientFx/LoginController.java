package clientFx;

import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class LoginController extends Main {
    protected static ArrayList<Restaurant> restaurants;

    private Parent root;
    private String name;
    private String password;
    @FXML
    private TextField nameBox;
    @FXML
    private PasswordField passBox;
    @FXML
    private Label passError;
    @FXML
    private void login(ActionEvent e){
        try {
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
            outputStream.flush();

            String name = nameBox.getText();
            String password = passBox.getText();
            outputStream.writeUTF("Login");
            outputStream.flush();
            outputStream.writeUTF(name);
            outputStream.flush();
            outputStream.writeUTF(password);
            outputStream.flush();
            String foundUser = inputStream.readUTF();
            System.out.println(foundUser);
            if (foundUser.equals("Found")) {
                System.out.println("found user");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantsView.fxml"));
                root = loader.load();
                switchToScene(e, "restaurantsView.fxml", root);
            } else {
                System.out.println("Did not find user");
                passError.setText("Did not find user!");
            }
        } catch (IOException error) {
            error.printStackTrace();
        } finally {
            System.out.println("Login completed ...");
        }
    }
    @FXML
    protected void signUp(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signUpView.fxml"));
        root = loader.load();
        switchToScene(e, "signUpView.fxml", root);
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}