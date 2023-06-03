package clientFx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class SignUpController {
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private TextField phoneBox;
    @FXML
    private TextField emailBox;
    @FXML
    private TextField addressBox;
    @FXML
    protected void signup(ActionEvent e){
        String phone = phoneBox.getText();
        String email = emailBox.getText();
        String address = addressBox.getText();
        try{
            FileWriter file = new FileWriter("usernames", true);
            BufferedReader in = new BufferedReader(new FileReader("C:/Users/10/IdeaProjects/ClientFx/usernames"));
            file.write(phone+",");
            file.write(email+",");
            file.write(address+",\n");
            file.close();
            String line;
            String last = null;
            while ((line = in.readLine()) != null) {
                if (line != null) {
                    last = line;
                }
            }
            String[] parts = last.split(",");
            Admin admin = Admin.getInstace(parts[0], parts[1], parts[2], parts[3], parts[4]);
//            System.out.println(admin);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantView.fxml"));
            root = loader.load();
            switchToScene(e, "restaurantView.fxml", root);

        }catch(IOException error){
            System.out.println("error");
            error.printStackTrace();
        }
    }
    @FXML
    protected void login(ActionEvent e) throws IOException {
        RandomAccessFile f = new RandomAccessFile("usernames", "rw");
        long length = f.length() - 1;
        byte b;
        do {
            length -= 1;
            f.seek(length);
            b = f.readByte();
        } while(b != 10);
        f.setLength(length+1);
        f.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
        root = loader.load();
        switchToScene(e, "loginView.fxml", root);
    }
    private static void switchToScene(ActionEvent event, String Scene, Parent root)  throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource(Scene));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}