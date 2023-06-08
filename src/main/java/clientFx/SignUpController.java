package clientFx;

import common.Admin;
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
import java.net.InetAddress;
import java.net.Socket;

public class SignUpController extends Main{
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private TextField nameBox;
    @FXML
    private Label nameLabel;
    @FXML
    private PasswordField passwordBox;
    @FXML
    private Label passLabel;
    @FXML
    private TextField phoneBox;
    @FXML
    private Label phoneLabel;
    @FXML
    private TextField emailBox;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField addressBox;
    @FXML
    private Label addressLabel;
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
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeUTF("signup");
            try{

                Admin admin = new Admin(name, password, phone, email, address);
                outputStream.writeObject(admin);
                outputStream.flush();
                System.out.println("Sent Admin");

                String errorCode = inputStream.readUTF();
                if(!errorCode.equals("true")){
                    int checkName = Integer.parseInt(errorCode.charAt(0)+"");
                    int checkPass = Integer.parseInt(errorCode.charAt(1)+"");
                    int checkNumber = Integer.parseInt(errorCode.charAt(2)+"");
                    int checkEmail = Integer.parseInt(errorCode.charAt(3)+"");

                    switch (checkName){
                        case(1):
                            nameLabel.setText("Name is already in use");
                            break;
                        case (2):
                            nameLabel.setText("Name is too short");
                            break;
                        default:break;
                    }
                    switch (checkPass){
                        case(1):
                            passLabel.setText("Password is too weak!");
                            break;
                        case (2):
                            passLabel.setText("Password is too short");
                            break;
                        default:break;
                    }
                    if(checkNumber==1)phoneLabel.setText("invalid number");
                    if(checkEmail==1)emailLabel.setText("invalid Email");
                }
                else{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantsView.fxml"));
                    root = loader.load();
                    switchToScene(e, "restaurantsView.fxml", root);
                }
                outputStream.close();
                inputStream.close();
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