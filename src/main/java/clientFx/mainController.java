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
import javafx.stage.Stage;
import java.io.FileWriter;

import java.io.IOException;

public class mainController extends Main{
    private String Name;
    private String Pass;
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
    protected void signUp(ActionEvent e) throws IOException {
        String name = nameBox.getText();
        String password = passBox.getText();
        if(password.length()<8){
            passError.setText("Password should atleast be 8 charactors! ");
            passBox.clear();
        }
        else{
            try{
                Name = name;
                Pass = password;
                FileWriter file = new FileWriter("usernames", true);
                file.write(name+",");
                file.write(password+",");
                file.close();
                nameLabel.setText("Name");
                passwordLabel.setText("Password");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("signUpView.fxml"));
                root = loader.load();
                switchToScene(e, "signUpView.fxml", root);
            }catch(IOException error){
                System.out.println("error");
                error.printStackTrace();
            }
        }
    }
    @FXML
    protected void login(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
        root = loader.load();
        switchToScene(e, "loginView.fxml", root);
    }
    public String getName() {
        return Name;
    }
    public String getPass() {
        return Pass;
    }
}