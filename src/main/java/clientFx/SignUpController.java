package clientFx;

import common.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SignUpController extends Main{
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private TextField nameBox;
    @FXML
    private PasswordField passwordBox;
    @FXML
    private TextField phoneBox;
    @FXML
    private TextField emailBox;
    @FXML
    private TextField addressBox;
    @FXML
    protected void signup(ActionEvent e){
        String name = nameBox.getText();
        String password = passwordBox.getText();
        String phone = phoneBox.getText();
        String email = emailBox.getText();
        String address = addressBox.getText();
        try{
            InetAddress addr = InetAddress.getByName(null);
            System.out.println("addr = " + addr);
            Socket socket = new Socket(addr, PORT);
            try{
                System.out.println("Connected to server.");
                System.out.println("socket = " + socket);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                out.println("signup");
                Admin admin = new Admin(name, password, phone, email, address);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(admin);
                outputStream.close();
                System.out.println("Sent Admin");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantsView.fxml"));
                root = loader.load();
                switchToScene(e, "restaurantsView.fxml", root);

            }catch(IOException error){
                System.out.println("error");
                error.printStackTrace();
            }
        }catch (IOException E){
            E.printStackTrace();
        }
    }
    @FXML
    protected void login(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
        root = loader.load();
        switchToScene(e, "loginView.fxml", root);
    }
}