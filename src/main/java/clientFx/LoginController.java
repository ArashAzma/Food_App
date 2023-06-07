package clientFx;

import common.Restaurant;
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

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginController extends Main{
    protected static ArrayList<Restaurant> restaurants;
    private static Stage stage;
    private static Scene scene;
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
    private Label nameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private void login(ActionEvent e)throws IOException{
        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, PORT);
        try {
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            out.println("login");
            String name = nameBox.getText();
            String password = passBox.getText();
            out.println(name);
            out.println(password);
            String foundUser = in.readLine();
            System.out.println(foundUser);
            if(foundUser.equals("Found")) {
                System.out.println("found user");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantsView.fxml"));
                root = loader.load();
                switchToScene(e, "restaurantsView.fxml", root);
                socket.close();
            }
            else {
                System.out.println("Did not find user");
                passError.setText("Did not find user!");
            }
        } catch(IOException error){
            error.printStackTrace();
        }finally{
            System.out.println("closing...");
            socket.close();
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
