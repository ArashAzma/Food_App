package Admin;

import clientFx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static Admin.Main.in;
import static Admin.Main.out;

public class LoginController extends Main {
    private Parent root;
    @FXML
    private Button loginButton;
    @FXML
    private TextField nameBox;
    @FXML
    private PasswordField passBox;
    @FXML
    private Label passError;
    @FXML
    private void login(ActionEvent e)throws IOException{
        out.flush();

        String name = nameBox.getText();
        String password = passBox.getText();
        out.writeUTF("login");
        out.flush();
        out.writeUTF(name);
        out.flush();
        out.writeUTF(password);
        out.flush();
        String foundUser = in.readUTF();
        if(foundUser.equals("Found")) {
            System.out.println("found user");
            root = FXMLLoader.load(getClass().getResource("/AdminFx/restaurant.fxml"));
            Stage window = (Stage) loginButton.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        else {
            System.out.println("Did not find user");
            passError.setText("Did not find user!");
        }
    }
}
