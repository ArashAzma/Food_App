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
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
//        if(password.length()<8){
//            passError.setText("Password should atleast be 8 charactors! ");
//            passBox.clear();
//        }
        try{
            File file = new File("C:\\Users\\10\\IdeaProjects\\ClientFx\\usernames");
            Scanner scan = new Scanner(file);
            boolean findUser = false;

            while (scan.hasNextLine()){

                boolean nextLine = false;
                String line = scan.nextLine();
                Scanner lineScan = new Scanner(line);
                lineScan.useDelimiter(",");

                while(lineScan.hasNext()){

                    if(nextLine) continue;
                    if(lineScan.next() == name){
                        System.out.println(name);
                        if(lineScan.next() == password){
                            System.out.println(password);
//                            System.out.println("Logged in successfully");
//                            passError.setTextFill(Color.GREEN);
//                            passError.setText("Logged in successfully");
//                            findUser = true;
//                            break;
                        }
                        else{
                            nextLine = true;
                            continue;
                        }
                    }
                    else{
                        nextLine = true;
                        continue;
                    }
                }
            }
            if(!findUser){
                passError.setTextFill(Color.RED);
                passError.setText("There wasn't any match ");
                passBox.clear();
            }
            else{
                nameLabel.setText("Name");
                passwordLabel.setText("Password");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantView.fxml"));
                root = loader.load();
                switchToScene(e, "restaurantView.fxml", root);
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
